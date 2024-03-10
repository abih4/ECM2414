package testcardgame;

import org.junit.Test;

import cardgame.*;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;

import static org.junit.Assert.*;

public class TestGame {
    @Test
    public void testCreateAllPlayersAndDecks(){
        //set the number of players to 4
        int numberOfPlayers = 4;
        //create new game with 4 players and no pack of cards
        Game cardGame = new Game(numberOfPlayers, null);
        //create players and decks
        cardGame.createAllPlayersAndDecks();
        //4 players and 4 decks should have been created
        assertTrue(cardGame.getListOfPlayers().size() == numberOfPlayers);
        assertTrue(cardGame.getListOfCardDecks().size() == numberOfPlayers);
        assertTrue(cardGame.getListOfInitialHandCardDecks().size() == numberOfPlayers);
    }

    @Test
    public void testDistributeCards(){
        //set the number of players to 2
        int numberOfPlayers = 2;
        //create a pack of cards with 4 cards
        LinkedList<Card> packOfCards = new LinkedList<Card>();
        packOfCards.add(new Card(1));
        packOfCards.add(new Card(2));
        packOfCards.add(new Card(3));
        packOfCards.add(new Card(4));
        //now create a new game with these two attributes
        Game cardGame = new Game(numberOfPlayers, packOfCards);
        //set up the players and decks
        cardGame.setInitialNumberOfCards(1);
        cardGame.createAllPlayersAndDecks();
        //distribute all the card, i.e. only one card each
        cardGame.distributeCards();
        //check all the cards were distributed correctly
        assertTrue(cardGame.getListOfInitialHandCardDecks().get(0).getCard(0).getCardValue() == 1);
        assertTrue(cardGame.getListOfInitialHandCardDecks().get(1).getCard(0).getCardValue() == 2);
        assertTrue(cardGame.getListOfCardDecks().get(0).getCard(0).getCardValue() == 3);
        assertTrue(cardGame.getListOfCardDecks().get(1).getCard(0).getCardValue() == 4);
    }

    @Test
    public void testSetCardDecksIntoPlayers(){
        //set the number of players to 2
        int numberOfPlayers = 2;
        //create a pack of cards with 4 cards
        LinkedList<Card> packOfCards = new LinkedList<Card>();
        packOfCards.add(new Card(1));
        packOfCards.add(new Card(2));
        packOfCards.add(new Card(3));
        packOfCards.add(new Card(4));
        //now create a new game with these two attributes
        Game cardGame = new Game(numberOfPlayers, packOfCards);
        //set up the players and decks
        cardGame.setInitialNumberOfCards(1);
        cardGame.createAllPlayersAndDecks();
        //distribute all the card, i.e. only one card each
        cardGame.distributeCards();
        //set the decks correctly
        cardGame.setCardDecksIntoPlayers();
        //check that the decks were placed correctly
        assertTrue(cardGame.getListOfPlayers().get(0).getPreferredCardDeck().getCard(0).getCardValue() == 1);
        assertTrue(cardGame.getListOfPlayers().get(1).getPreferredCardDeck().getCard(0).getCardValue() == 2);
        assertTrue(cardGame.getListOfPlayers().get(0).getLeftCardDeck().getCard(0).getCardValue() == 3);
        assertTrue(cardGame.getListOfPlayers().get(0).getRightCardDeck().getCard(0).getCardValue() == 4);
        assertTrue(cardGame.getListOfPlayers().get(1).getLeftCardDeck().getCard(0).getCardValue() == 4);
        assertTrue(cardGame.getListOfPlayers().get(1).getRightCardDeck().getCard(0).getCardValue() == 3);
    }

    @Test
    public void testCreateAndWriteCardDeckFile() {
        //create a new game (with no parameters)
        Game cardGame = new Game();
        //create a card deck with three cards
        CardDeck cardDeck = new CardDeck(10);
        cardDeck.setCard(new Card(1));
        cardDeck.setCard(new Card(2));
        cardDeck.setCard(new Card(3));
        //create the output file of this deck
        cardGame.createAndWriteCardDeckFile(cardDeck, 10);
        String text = new String();
        try{
            File file = new File("deck10_output.txt");
            Scanner fileReader = new Scanner(file);
            while(fileReader.hasNextLine()){
                String line = fileReader.nextLine();
                text = text + line + "\n";
            }
            fileReader.close();

        }catch (FileNotFoundException e){
            System.out.println("File not found, please try again");
        }
        //check the contents are correct
        assertTrue(text.equals("deck10 contents: 1 2 3\n"));
    
    }

    @Test
    public void testCreatePlayer() {
        //create a new game
        Game cardGame = new Game();
        //create player with index 2, and check that it was created correctly
        assertTrue(cardGame.createPlayer(2).getPlayerIndex() == 2);
    }

    @Test
    public void testCreateCardDeck() {
        //create a new game 
        Game cardGame = new Game();
        //check that the game has created a card deck with index 3
        assertTrue(cardGame.createCardDeck(3).getCardDeckIndex() == 3);
    }

    @Test
    public void testCreateCardDeckNoIndex() {
        //create game
        Game cardGame = new Game();
        //create a deck in the game
        CardDeck cardDeckTest = cardGame.createCardDeckNoIndex();
        //set a card
        cardDeckTest.setFirstCard(new Card(2));
        //check that the deck contains this value
        assertTrue(cardDeckTest.getFirstCard().getCardValue() == 2);
    }   

    @Test
    public void testGetNextCard() {
        //set the number of players to 4
        int numberOfPlayers = 4;
        //create a pack of cards with three cards
        LinkedList<Card> packOfCards = new LinkedList<Card>();
        packOfCards.add(new Card(1));
        packOfCards.add(new Card(2));
        packOfCards.add(new Card(3));
        //create a new game with these attributes
        Game cardGame = new Game(numberOfPlayers, packOfCards);
        //check that getNextCard() is handing the cards correctly
        assertTrue(cardGame.getNextCard().getCardValue() == 1 && packOfCards.size() == 2);
        assertTrue(cardGame.getNextCard().getCardValue() == 2 && packOfCards.size() == 1);
        assertTrue(cardGame.getNextCard().getCardValue() == 3 && packOfCards.size() == 0);
    }

    @Test
    public void testPlayGame1() {
        //this test checks that player 1 wins after drawing and discarding cards properly
        //set the number of players to 2
        int numberOfPlayers = 2;
        //creating a valid pack of cards with 8 * number of players = number of cards = 16 cards
        LinkedList<Card> packOfCards = new LinkedList<Card>();
        packOfCards.add(new Card(13));
        packOfCards.add(new Card(12));
        packOfCards.add(new Card(2));
        packOfCards.add(new Card(1));
        packOfCards.add(new Card(4));
        packOfCards.add(new Card(3));
        packOfCards.add(new Card(1));
        packOfCards.add(new Card(6));
        packOfCards.add(new Card(1));
        packOfCards.add(new Card(1));
        packOfCards.add(new Card(15));
        packOfCards.add(new Card(2));
        packOfCards.add(new Card(13));
        packOfCards.add(new Card(9));
        packOfCards.add(new Card(1));
        packOfCards.add(new Card(1));
        //create a new game with just two players
        Game cardGame = new Game(numberOfPlayers, packOfCards);
        //play game
        cardGame.playGame();
        //Player 1 initial hand: 13 2 4 1, Player 2 initial hand: 12 1 3 6, Deck 1: 1 1 1 1, Deck 2: 1 2 1 2
        //check there is only one winner
        assertTrue(cardGame.getWinnerSignal().toString().equals(" 1"));
        //check all the cards were handed as expected
        assertTrue(cardGame.getListOfPlayers().get(0).getInitialHandCardDeck().toString().equals(" 13 2 4 1"));
        assertTrue(cardGame.getListOfPlayers().get(1).getInitialHandCardDeck().toString().equals(" 12 1 3 6"));
        //check that player 1 has won, and that their preferred hand deck is 1 1 1 1, and that their not preferred hand deck is empty
        assertTrue(cardGame.getListOfPlayers().get(0).getPreferredCardDeck().toString().equals(" 1 1 1 1"));
        assertTrue(cardGame.getListOfPlayers().get(0).getNotPreferredCardDeck().toString().equals(""));
    }

    @Test
    public void testPlayGame2() {
        //testing the game works for one player, as long as the pack of cards contains a winning hand
         int numberOfPlayers = 1;
        //creating a valid pack of cards with 8 * number of players = number of cards = 8 cards
        LinkedList<Card> packOfCards = new LinkedList<Card>();
        packOfCards.add(new Card(13));
        packOfCards.add(new Card(1));
        packOfCards.add(new Card(12));
        packOfCards.add(new Card(14));
        packOfCards.add(new Card(12));
        packOfCards.add(new Card(1));
        packOfCards.add(new Card(1));
        packOfCards.add(new Card(1));
        //create a new game and play the game
        Game cardGame = new Game(numberOfPlayers, packOfCards);
        cardGame.playGame();
        //check that player 1 will eventually win
        assertTrue(cardGame.getWinnerSignal().toString().equals(" 1"));
        assertTrue(cardGame.getWinnerSignal().getWinnersListSize() == 1);
        assertTrue(cardGame.getListOfPlayers().get(0).getInitialHandCardDeck().toString().equals(" 13 1 12 14"));
        assertTrue(cardGame.getListOfPlayers().get(0).getPreferredCardDeck().toString().equals(" 1 1 1 1"));
        assertTrue(cardGame.getListOfPlayers().get(0).getNotPreferredCardDeck().toString().equals(""));
    }
}
