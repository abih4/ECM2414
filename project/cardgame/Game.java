package cardgame;

import java.util.LinkedList;
import java.util.ArrayList;
import java.io.*;

/**
 * Game is a class that, given the number of players and a pack of card, starts a game.
 * 
 * @author Abigail Hinton
 * @author Giulia Brown
 * @version 1.0.0
 */
public class Game{
    //attributes
    private int numberOfPlayers;
    private LinkedList<Card> packOfCards;
    private ArrayList<Player> listOfPlayers;
    private ArrayList<CardDeck> listOfInitialHandCardDecks;
    private ArrayList<CardDeck> listOfCardDecks;
    private WinnerSignal winnerSignalObject;
    private IStartGameSignal startGameSignal;
    private ReadyToPlaySignal readyToPlaySignal;
    private int initialNumberOfCards;

    //constructor
    public Game(){}

    public Game(int numberOfPlayers, LinkedList<Card> packOfCards){
        //assign numberOfPlayers and packOfCards
        this.numberOfPlayers = numberOfPlayers;
        this.packOfCards = packOfCards;
        //create players and add them to the list of players
        this.listOfPlayers = new ArrayList<Player>();
        //create list of initial hand card decks to pass into each player
        this.listOfInitialHandCardDecks = new ArrayList<CardDeck>();
        //create list of card decks to then pass into each player
        this.listOfCardDecks = new ArrayList<CardDeck>();
        //create winner signal object to pass to all the players
        this.winnerSignalObject = new WinnerSignal();
        //reset initial number of cards to hand
        this.initialNumberOfCards = 4;
        //create start game object
        this.startGameSignal = new StartGameSignal();
        //create ready to play object
        this.readyToPlaySignal = new ReadyToPlaySignal(numberOfPlayers);
    }

    /**
     * This method plays the game.
     * It creates the players and the card decks, it distributes the cards, and it assigns the correct
     * card decks to each player.
     * Then it starts all the threads, deals with the signals, and then stops all the threads.
     * Lastly, it creates the printouts of the decks.
     * 
     */
    public void playGame(){
        //create players, and card decks
        this.createAllPlayersAndDecks();

        //distribute cards to players and card decks
        this.distributeCards();

        //assign the correct decks to each player
        this.setCardDecksIntoPlayers();

        //start all the player threads
        for(int i=0; i < this.numberOfPlayers; i++){
            this.listOfPlayers.get(i).start();
        }

        try{
            //wait for all the players to be ready
            synchronized(this.readyToPlaySignal){
                this.readyToPlaySignal.wait();
            }
        }catch(InterruptedException e){}

        //if a player has won with their initial hand then interrupt all the other player threads
        if(this.winnerSignalObject.getWinnersListSize() != 0){
            //tell the players to stop done = true
            for(int i=0; i<numberOfPlayers; i++){
                listOfPlayers.get(i).interrupt();
            }
        }else{ 
            //if there are no winners yet, then notify all the player threads to start to play
            this.startGameSignal.notifyToStartGame();
            try {
                //wait on the winner signal object
                synchronized(this.winnerSignalObject){
                    this.winnerSignalObject.wait();
                }
            } catch (InterruptedException e) {}
        }

        //stop all the player threads
        for(int i=0; i < this.numberOfPlayers; i++){
            this.listOfPlayers.get(i).stopThread();
        }

        //create the deck files
        for(int i=0; i < this.numberOfPlayers; i++){
            this.createAndWriteCardDeckFile(this.listOfCardDecks.get(i), this.listOfCardDecks.get(i).getCardDeckIndex());
        }
    }

    /** 
     * This method returns the list of players.
     * 
     * @return list of players
     */
    public ArrayList<Player> getListOfPlayers(){
        return this.listOfPlayers;
    }

    /** 
     * This method returns the list of initial hand card decks.
     * 
     * @return list of initial hand card decks
     */
    public ArrayList<CardDeck> getListOfInitialHandCardDecks(){
        return this.listOfInitialHandCardDecks;
    }

    /** 
     * This method returns the list of card decks.
     * 
     * @return list of card decks
     */
    public ArrayList<CardDeck> getListOfCardDecks(){
        return this.listOfCardDecks;
    }

    /** 
     * This method returns the winner signal object
     * 
     * @return WinnerSignal
     */
    public WinnerSignal getWinnerSignal(){
        return this.winnerSignalObject;
    }

    /** 
     * This method sets the initial number of card to hand out to each player and each card deck.
     * 
     * @param initialNumberOfCards
     */
    public void setInitialNumberOfCards(int initialNumberOfCards){
        this.initialNumberOfCards = initialNumberOfCards;
    }

    /**
     * This method creates all th players and all the card decks.
     */
    public void createAllPlayersAndDecks(){
        //create players, and card decks
        for(int i=1; i <= numberOfPlayers; i++){
            //create each player, and add it to the respective list
            listOfPlayers.add(this.createPlayer(i));
            //create each hand card deck and add it to the respective ist
            listOfInitialHandCardDecks.add(this.createCardDeckNoIndex());
            //create each card deck and add it to the respective list
            listOfCardDecks.add(this.createCardDeck(i));
        }
    }

    /**
     * This method distributes all the cards in a round robin fashion 
     * first to the players and the to the card decks.
     */
    public void distributeCards(){
        //first distribute 4 cards to players in a round robin fashion
        for(int i=0; i < this.initialNumberOfCards; i++){
            for(int j=0; j < this.numberOfPlayers; j++){
                Card nextCard = this.getNextCard();
                this.listOfInitialHandCardDecks.get(j).setCard(nextCard);
            }
        }

        //then distribute 4 cards to decks in a round robin fashion
        for(int i=0; i < this.initialNumberOfCards; i++){
            for(int j=0; j < this.numberOfPlayers; j++){
                Card nextCard = this.getNextCard();
                this.listOfCardDecks.get(j).setCard(nextCard);
            }
        }
    }

    /**
     * This method sets the card decks into the players correctly, 
     * and sorts the initial hand card deck of each player.
     */
    public void setCardDecksIntoPlayers(){
        //assign the correct decks to each player
        for(int i=0; i < numberOfPlayers; i++){
            //assign the initial hand deck to player i
            listOfPlayers.get(i).setInitialHandCardDeck(listOfInitialHandCardDecks.get(i));
            //sort the initial hand deck of player i
            listOfPlayers.get(i).sortInitialHandCardDeck(this.initialNumberOfCards);
            //assign the left card deck
            listOfPlayers.get(i).setLeftCardDeck(listOfCardDecks.get(i));
            //assign the right card deck
            listOfPlayers.get(i).setRightCardDeck(listOfCardDecks.get((i+1) % numberOfPlayers));
        }
    }

    /** 
     * This method creates and writes to the card deck file output.
     * 
     * @param cardDeck
     * @param cardDeckIndex
     */
    public void createAndWriteCardDeckFile(CardDeck cardDeck, int cardDeckIndex){
        //create file name
        String cardDeckFileName = "deck" + cardDeckIndex + "_output.txt";
        //delete previous file content
        try {
            //create FileWriter
            FileWriter cardDeckWriter = new FileWriter(cardDeckFileName, false);
            //overwrite to the file
            cardDeckWriter.write("");
            //close FileWriter
            cardDeckWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred");
        }
        //write to the file in append mode
        try {
            //create FileWriter
            FileWriter cardDeckWriter = new FileWriter(cardDeckFileName, true);
            cardDeckWriter.write("deck" + cardDeckIndex + " contents:" + cardDeck);
            //close FileWriter
            cardDeckWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred");
        }
    }

    /** 
     * This method creates a player with an index and all the signal objects.
     * 
     * @param playerIndex
     * @return Player
     */
    public Player createPlayer(int playerIndex){
        Player player = new Player(playerIndex, this.winnerSignalObject, this.startGameSignal, this.readyToPlaySignal);
        
        return player;
    }

    /** 
     * This method creates a card deck with an index.
     * 
     * @param cardDeckIndex
     * @return CardDeck
     */
    public CardDeck createCardDeck(int cardDeckIndex){
        CardDeck cardDeck = new CardDeck(cardDeckIndex);

        return cardDeck;
    }

    /** 
     * This method creates a card deck with no index.
     * 
     * @return CardDeck
     */
    public CardDeck createCardDeckNoIndex(){
        CardDeck cardDeck = new CardDeck();
        
        return cardDeck;
    }

    /** 
     * This method returns the next card from the deck.
     * 
     * @return Card
     */
    public Card getNextCard(){
        Card nextCard = this.packOfCards.getFirst();
        this.packOfCards.removeFirst();
        return nextCard;
    }
}
