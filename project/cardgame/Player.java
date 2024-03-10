package cardgame;
import java.io.*;
import java.util.LinkedList;

/**
 * A player has a unique numerical identifier (playerIndex), a signal object, a fine name,
 * and a few card decks: preferredCardDeck, notPreferredCardDeck, initialHandCardDeck,
 * leftCardDeck, and rightCardDeck.
 * A player will also contain all the necessary signals to make sure the game works correctly.
 * A player thread will implement the player logic of drawing and discarding cards, and 
 * checking if it had won.
 * 
 * @author Abigail Hinton
 * @author Giulia Brown
 * @version 1.0.0
 * 
 */
public class Player extends Thread{
    //attributes
    private int playerIndex;
    private CardDeck preferredCardDeck;
    private CardDeck notPreferredCardDeck;
    private CardDeck initialHandCardDeck;
    private CardDeck leftCardDeck;
    private CardDeck rightCardDeck;
    private String playerFileName;
    private WinnerSignal winnerSignalObject;
    private IStartGameSignal startGameSignal;
    private ReadyToPlaySignal readyToPlaySignal;
    private volatile boolean done = false;

    //constructors
    public Player(int playerIndex){
        this.playerIndex = playerIndex;
    }

    public Player(int playerIndex, WinnerSignal winnerSignalObject, IStartGameSignal startGameSignal, ReadyToPlaySignal readyToPlaySignal){
        this.playerIndex = playerIndex;
        this.winnerSignalObject = winnerSignalObject;
        this.startGameSignal = startGameSignal;
        this.readyToPlaySignal = readyToPlaySignal;
    }

    //getter and setter methods    
    /** 
     * This method returns the player index.
     * 
     * @return player index
     */
    public int getPlayerIndex(){
        return this.playerIndex;
    }

    /** 
     * This method sets the left card deck.
     * 
     * @param leftCardDeck
     */
    public void setLeftCardDeck(CardDeck leftCardDeck){
        this.leftCardDeck = leftCardDeck;
    }
    
    /** 
     * This  method returns the left card deck.
     *      **used for testing only**
     * @return CardDeck
     */
    public CardDeck getLeftCardDeck(){
        return this.leftCardDeck;
    }

    /** 
     * This method sets the right card deck.
     * 
     * @param rightCardDeck
     */
    public void setRightCardDeck(CardDeck rightCardDeck){
        this.rightCardDeck = rightCardDeck;
    }
    
    /** 
     * This method returns the right card deck.
     *      **used for testing only**
     * @return CardDeck
     */
    public CardDeck getRightCardDeck(){
        return this.rightCardDeck;
    }

    /**
     * This method adds the player's index to the winner signal object once the player has won.
     */
    public void addToWinnerSignalObject(){
        this.winnerSignalObject.addWinner(this.playerIndex);
    }

    /** 
     * This method sets the preferred card deck.
     * 
     * @param preferredCardDeck
     */
    public void setPreferredCardDeck(CardDeck preferredCardDeck){
        this.preferredCardDeck = preferredCardDeck;
    }
    
    /** 
     * This method returns the preferred card deck.
     *      **used for testing only**
     * 
     * @return CardDeck
     */
    public CardDeck getPreferredCardDeck(){
        return this.preferredCardDeck;
    }
    
    /** 
     * This method sets the not preferred card deck.
     * 
     * @param notPreferredCardDeck
     */
    public void setNotPreferredCardDeck(CardDeck notPreferredCardDeck){
        this.notPreferredCardDeck = notPreferredCardDeck;
    }
    
    /** 
     * This method returns the not preferred card deck.
     *      **used for testing only**
     * 
     * @return CardDeck
     */
    public CardDeck getNotPreferredCardDeck(){
        return this.notPreferredCardDeck;
    }
    
    /** 
     * This method sets the initial hand card deck.
     * 
     * @param initialCardDeck
     */
    public void setInitialHandCardDeck(CardDeck initialCardDeck){
        this.initialHandCardDeck = initialCardDeck;
    }
    
    /** 
     * This method returns the initial hand card deck.
     *      **used for testing only**
     * @return CardDeck
     */
    public CardDeck getInitialHandCardDeck(){
        return this.initialHandCardDeck;
    }

    /** 
     * This method sorts the initial hand card deck depending on the card value on the 
     * player index.
     * 
     * @param initialNumberOfCards
     */
    public void sortInitialHandCardDeck(int initialNumberOfCards){
        //creates new preferred and not preferred card decks
        this.preferredCardDeck = new CardDeck();
        this.notPreferredCardDeck = new CardDeck();
        //loop 4 times to go through the whole deck
        for(int i=0; i < initialNumberOfCards; i++){
            //if the card is of preferred value then place it int the preferred card deck
            if(this.initialHandCardDeck.getCard(i).isPreferredCardValue(playerIndex)){
                preferredCardDeck.setCard(this.initialHandCardDeck.getCard(i));
            }else{
                //otherwise place it in the not preferred card deck
                notPreferredCardDeck.setCard(this.initialHandCardDeck.getCard(i));
            }
        }
    }

    /** 
     * This method checks if the player has a winning hand in the preferred card deck.
     * 
     * @return Boolean
     */
    public Boolean isPreferredCardDeckFull() {
        //if the preferred card deck is full, then it means the player has won
        if(this.preferredCardDeck.getCardDeckSize() == 4) {
            return true;
        } 
        return false;
    }

    /** 
     * This method checks if the player has a winning hand in the not preferred card deck.
     * 
     * @return Boolean
     */
    public Boolean isNotPreferredCardDeckAWin() {
        //if all the cards in the not preferred card deck have the same value then the player has won
        for(int i=0; i < this.notPreferredCardDeck.getCardDeckSize() - 1; i++) {
            if (this.notPreferredCardDeck.getCard(i).getCardValue() != this.notPreferredCardDeck.getCard(i+1).getCardValue()) {
                return false;
            }
        }
        return true;
    }

    /** 
     * This method draws a card from the left card deck and returns it.
     * 
     * @return new card
     */
    public Card drawCardFromLeftDeck(){
        //draw card
        Card newCard = this.leftCardDeck.getFirstCard();
        //remove card from left deck
        this.leftCardDeck.removeFirstCard();
        return newCard;
    }

    /** 
     * This method discards the last card from the not preferred card deck to the right 
     * card deck, and returns it.
     * 
     * @return discard card
     */
    public Card discardCardToRightDeck(){
        //find card to discard
        Card discardCard = this.notPreferredCardDeck.getLastCard();
        //add the card to right card deck
        this.rightCardDeck.setLastCard(discardCard);
        //remove card from not preferred card deck
        this.notPreferredCardDeck.removeLastCard();
        return discardCard;
    }
    
    /** 
     * This method both draws and discards a card, and then returns these two card in a 
     * linked list.
     * 
     * @return new and discard cards
     */
    public LinkedList<Card> drawAndDiscardAction(){
        //the list to return
        LinkedList<Card> newAndDiscardCards = new LinkedList<Card>();
        //draw a card from left deck
        Card newCard = this.drawCardFromLeftDeck();
        //place the new card in one of the decks depending on the card value
        if(newCard.isPreferredCardValue(this.playerIndex)){
            this.preferredCardDeck.setFirstCard(newCard);
        }else{
            this.notPreferredCardDeck.setFirstCard(newCard);
        }
        //discard a card to the right deck
        Card discardCard = this.discardCardToRightDeck();
        //add the two cards to the list
        newAndDiscardCards.add(newCard);
        newAndDiscardCards.add(discardCard);
        //return the list
        return newAndDiscardCards;        
    }

    /**
     * This method creates a file name for the player's output.
     */
    public void createPlayerFileName(){
        this.playerFileName = "player"+this.playerIndex+"_output.txt";
        //ensuring empty file
        this.deleteFileContent();
    }

    /**
     * This method deletes all contents of a file.
     * This is needed only to make sure that all the files are empty at the start of 
     * the game.
     */
    public void deleteFileContent(){
        try {
            //create FileWriter not in append mode
            FileWriter playerWriter = new FileWriter(this.playerFileName, false);
            //overwrite
            playerWriter.write("");
            //close writer
            playerWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred");
        }
    }

    /** 
     * This method writes to a player file the given message
     * 
     * @param message
     */
    public void writeToPlayerFile(String message){
        try {
            //create FileWriter in append mode
            FileWriter playerWriter = new FileWriter(this.playerFileName, true);
            //add the message followed by a new line
            playerWriter.write(message + "\n");
            //close the FileWriter
            playerWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred");
        }
    }

    /**
     * This method writes to a player file when that player has won.
     */
    public void writeToPlayerFileWin(){
        //print the winner to the terminal
        System.out.println("Player " + this.playerIndex + " wins");
        //write the details on the player output file
        this.writeToPlayerFile("player " + this.playerIndex + " wins");
        this.writeToPlayerFile("player " + this.playerIndex + " exits");
        //writes the player's final hand
        if (this.preferredCardDeck.getCardDeckSize() == 4) {
            this.writeToPlayerFile("player " + this.playerIndex + " final hand:" + this.preferredCardDeck);
        } else {
            this.writeToPlayerFile("player " + this.playerIndex + " final hand:" + this.notPreferredCardDeck);
        }
    }
    
    /** 
     * This method writes to a player file when another player has won.
     * 
     * @param playerIndexWinner
     */
    public void writeToPlayerFileLose(int playerIndexWinner){
        //write the details on the player output file
        this.writeToPlayerFile("player " + playerIndexWinner + " has informed player " + this.playerIndex + " that player " + playerIndexWinner + " has won");
        this.writeToPlayerFile("player " + this.playerIndex + " exits");
        this.writeToPlayerFile("player " + this.playerIndex + " hand:" + this.preferredCardDeck + this.notPreferredCardDeck);
    }
    
    /**
     * This method contains the player's logic, that will be called by the run method.
     * 
     */
    public void playerLogic(){
        //create the player output file
        this.createPlayerFileName();
        //write the initial hand 
        this.writeToPlayerFile("player " + this.playerIndex + " initial hand" + this.getInitialHandCardDeck());
        //check if a player has won with their initial hand
        if (this.isPreferredCardDeckFull()) {
            //if the player has won, then add the player index to the winner signal object
            this.addToWinnerSignalObject();
            //then informs the main thread that it is ready to play
            this.readyToPlaySignal.readyToPlay();
            done = true;
        } else if (this.preferredCardDeck.getCardDeckSize() == 0 && this.isNotPreferredCardDeckAWin()) {
            //if the player has won, then add the player index to the winner signal object
            this.addToWinnerSignalObject();  
            //then informs the main thread that it is ready to play
            this.readyToPlaySignal.readyToPlay();
            done = true;
        }else{
            //if the player hasn't won, it just informs the main thread that it is ready to play
            this.readyToPlaySignal.readyToPlay();
            try{
                //and then it waits on the startGameSignal object
                this.startGameSignal.waitToStartGame();
            }catch(InterruptedException e){
                //if another player has won with their initial hand, 
                //then the main thread will interrupt player thread,
                //which will not enter the while loop and just write their hand to their printout file.
                done = true;
            }
        }
        //if no one has one, then player enters while loop 
        while (!done){
            //check if the player has won
            if (this.isPreferredCardDeckFull()) {
                this.addToWinnerSignalObject();
                done = true;
            } else if (this.preferredCardDeck.getCardDeckSize() == 0 && this.isNotPreferredCardDeckAWin()) {
                this.addToWinnerSignalObject();  
                done = true;
            }else{
                //player hasn't won yet
                //check if the left deck is not empty and if the right deck is not too full
                //this makes sure there are no starvation and deadlock issues
                if(this.leftCardDeck.getCardDeckSize() != 0 && this.rightCardDeck.getCardDeckSize() < 6){
                    //draw a card and discard a card
                    LinkedList<Card> newAndDiscardCards = this.drawAndDiscardAction();
                    //get the card that was drawn and the card that was discarded
                    Card newCard = newAndDiscardCards.getFirst();
                    Card discardCard = newAndDiscardCards.getLast();
                    
                    //write the drawing action to the player file
                    this.writeToPlayerFile("player " + this.playerIndex + " draws a " + newCard.getCardValue() + " from deck " + this.leftCardDeck.getCardDeckIndex());
                    
                    //write the discarding action to the player file
                    this.writeToPlayerFile("player " + this.playerIndex + " discards a " + discardCard.getCardValue() + " to deck " + this.rightCardDeck.getCardDeckIndex());
        
                    //write current hand to the player file
                    this.writeToPlayerFile("player " + this.playerIndex + " current hand is" + this.preferredCardDeck + this.notPreferredCardDeck);
                }else{
                    //if the left deck is empty or the right deck too full the wait and try again
                    try{
                        Thread.sleep(5);
                    }catch(InterruptedException e){}
                }
            }
        }
        //when the game ends write to player output file who has won 
        if(this.winnerSignalObject.getWinner() != this.playerIndex){
            this.writeToPlayerFileLose(this.winnerSignalObject.getWinner());
        }else{
            this.writeToPlayerFileWin(); 
        }
    }

    /**
     * This method sets done to true to end the while loop.
     */
    public void endPlayerLogic(){
        this.done = true;
    }

    /**
     * This method starts the thread by running the player logic method.
     */
    public void run (){
        this.playerLogic();
    }

    /**
     * This method stops the thread by ending the player logic.
     */
    public void stopThread() {
        this.endPlayerLogic();
    }
}
