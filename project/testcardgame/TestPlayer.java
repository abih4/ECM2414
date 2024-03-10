package testcardgame;

import org.junit.Test;

import cardgame.*;

import static org.junit.Assert.*;
import java.util.LinkedList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class TestPlayer {

    @Test
    public void testGetPlayerIndex() {
        //test that by creating a player with index, the index is returned correctly
        Player player = new Player(5);
        assertTrue(player.getPlayerIndex() == 5);
    }

    @Test
    public void testSetLeftCardDeck() {
        //create a player and a card deck
        Player player = new Player(5);
        CardDeck cardDeck = new CardDeck();
        cardDeck.setCard(new Card(5));
        cardDeck.setCard(new Card(6));
        cardDeck.setCard(new Card(4));
        //set the card deck as the left card deck of the player
        player.setLeftCardDeck(cardDeck);
        //check that it was set correctly
        assertTrue(player.getLeftCardDeck() == cardDeck);
    }

    @Test
    public void testSetRightCardDeck() {
        //create a player and a card deck
        Player player = new Player(5);
        CardDeck cardDeck = new CardDeck();
        cardDeck.setCard(new Card(5));
        cardDeck.setCard(new Card(6));
        cardDeck.setCard(new Card(4));
        //set the card deck as the right card deck of the player
        player.setRightCardDeck(cardDeck);
        //check that it was set correctly
        assertTrue(player.getRightCardDeck() == cardDeck);
    }

    @Test
    public void testAddToSignalObject() {
        //create two players with the respective signals
        WinnerSignal winnerSignalObject = new WinnerSignal();
        StartGameSignal startGameSignal = new StartGameSignal();
        ReadyToPlaySignal readyToPlaySignal = new ReadyToPlaySignal(1);
        Player player1 = new Player(1, winnerSignalObject, startGameSignal, readyToPlaySignal);
        Player player2 = new Player(2, winnerSignalObject, startGameSignal, readyToPlaySignal);
        //add their index to the winner signal object
        player2.addToWinnerSignalObject();
        player1.addToWinnerSignalObject();
        //check that these indexes have both been added, and correctly
        assertTrue(winnerSignalObject.getWinner() == 2);
        assertTrue(winnerSignalObject.getWinnersList().get(1) == 1);
        assertTrue(winnerSignalObject.getWinnersListSize() == 2);
    }

    @Test
    public void testSetPreferredCardDeck() {
        //create a player 
        Player player = new Player(5);
        CardDeck cardDeck = new CardDeck();
        cardDeck.setCard(new Card(5));
        cardDeck.setCard(new Card(6));
        cardDeck.setCard(new Card(4));
        //set their preferred card deck, and check it was done correctly
        player.setPreferredCardDeck(cardDeck);
        assertTrue(player.getPreferredCardDeck() == cardDeck);
        assertTrue(player.getPreferredCardDeck().toString().equals(" 5 6 4"));
    }

    @Test
    public void testSetNotPreferredCardDeck() {
        //create player
        Player player = new Player(5);
        CardDeck cardDeck = new CardDeck();
        cardDeck.setCard(new Card(5));
        cardDeck.setCard(new Card(6));
        cardDeck.setCard(new Card(4));
        //set their not preferred card deck, and check it was done correctly
        player.setNotPreferredCardDeck(cardDeck);
        assertTrue(player.getNotPreferredCardDeck() == cardDeck);
        assertTrue(player.getNotPreferredCardDeck().toString().equals(" 5 6 4"));
    }

    @Test
    public void testSetInitialHandCardDeck() {
        //create player
        Player player = new Player(5);
        CardDeck cardDeck = new CardDeck();
        cardDeck.setCard(new Card(5));
        cardDeck.setCard(new Card(6));
        cardDeck.setCard(new Card(4));
        cardDeck.setCard(new Card(3));
        //set their initial hand card deck, and check it was done correctly
        player.setInitialHandCardDeck(cardDeck);
        assertTrue(player.getInitialHandCardDeck() == cardDeck);
        assertTrue(player.getInitialHandCardDeck().toString().equals(" 5 6 4 3"));
    }

    @Test
    public void testSortInitialHandCardDeck() {
        //create player
        Player player = new Player(1);
        CardDeck cardDeck = new CardDeck();
        cardDeck.setCard(new Card(5));
        cardDeck.setCard(new Card(6));
        cardDeck.setCard(new Card(4));
        cardDeck.setCard(new Card(1));
        //set their initial hand card deck
        player.setInitialHandCardDeck(cardDeck);
        //sort their initial hand card deck
        player.sortInitialHandCardDeck(4);
        //check that the outcome is as expected
        assertTrue(player.getPreferredCardDeck().toString().equals(" 1"));
        assertTrue(player.getNotPreferredCardDeck().toString().equals(" 5 6 4"));
        assertTrue(player.getPreferredCardDeck().getCardDeckSize() + player.getNotPreferredCardDeck().getCardDeckSize() == 4);
    }

    @Test
    public void testIsPreferredCardDeckFull() {
        //create player with a hand card deck that is a winning hand with their preferred card values
        Player player = new Player(1);
        CardDeck cardDeck = new CardDeck();
        cardDeck.setCard(new Card(1));
        cardDeck.setCard(new Card(1));
        cardDeck.setCard(new Card(1));
        cardDeck.setCard(new Card(1));
        player.setInitialHandCardDeck(cardDeck);
        //sort the hand card deck
        player.sortInitialHandCardDeck(4);
        //check that their preferred card deck is full, and that the not preferred card deck is empty
        assertTrue(player.isPreferredCardDeckFull());
        assertTrue(player.getNotPreferredCardDeck().getCardDeckSize() == 0);
        //and now check that if a card is removed from the preferred card deck, then it is not full anymore
        player.getPreferredCardDeck().removeFirstCard();
        assertFalse(player.isPreferredCardDeckFull());
    }

    @Test
    public void testIsNotPreferredCardDeckAWin() {
        //create player with an initial hand that is a winning hand but not of their preferred card value
        Player player = new Player(2);
        CardDeck cardDeck = new CardDeck();
        cardDeck.setCard(new Card(1));
        cardDeck.setCard(new Card(1));
        cardDeck.setCard(new Card(1));
        cardDeck.setCard(new Card(1));
        player.setInitialHandCardDeck(cardDeck);
        //sort the hand
        player.sortInitialHandCardDeck(4);
        //check that the not preferred card deck is a winning hand
        assertTrue(player.isNotPreferredCardDeckAWin());
        //and now remove a card and add a new card with different value
        player.getNotPreferredCardDeck().removeFirstCard();
        player.getNotPreferredCardDeck().setFirstCard(new Card(5));
        //check now that this is not a win
        assertFalse(player.isNotPreferredCardDeckAWin());
    }

    @Test
    public void testDrawCardFromLeftDeck() {
        //create player with a left deck, and check that the action of drawing a card is done correctly
        Player player = new Player(2);
        CardDeck cardDeck = new CardDeck();
        cardDeck.setCard(new Card(1));
        cardDeck.setCard(new Card(2));
        cardDeck.setCard(new Card(3));
        cardDeck.setCard(new Card(4));
        player.setLeftCardDeck(cardDeck);
        assertTrue(player.drawCardFromLeftDeck().getCardValue() == 1);
        assertTrue(player.getLeftCardDeck().toString().equals(" 2 3 4"));
    }

    @Test
    public void testDiscardCardToRightDeck() {
        //create player with a hand deck and a right deck, and check that the action of discarding a card is done correctly
        Player player = new Player(2);
        CardDeck cardDeck = new CardDeck();
        cardDeck.setCard(new Card(1));
        cardDeck.setCard(new Card(2));
        cardDeck.setCard(new Card(3));
        cardDeck.setCard(new Card(4));
        player.setInitialHandCardDeck(cardDeck);
        player.sortInitialHandCardDeck(4);
        CardDeck cardDeck2 = new CardDeck();
        cardDeck2.setCard(new Card(6));
        cardDeck2.setCard(new Card(4));
        cardDeck2.setCard(new Card(2));
        player.setRightCardDeck(cardDeck2);
        assertTrue(player.discardCardToRightDeck().getCardValue() == 4);
        assertTrue(player.getNotPreferredCardDeck().toString().equals(" 1 3"));
        assertTrue(player.getRightCardDeck().toString().equals(" 6 4 2 4"));
    }

    @Test
    public void testDrawAndDiscardAction(){
        //now test that the actions of drawing and discarding a card works as expected
        Player player = new Player(4);
        //create left card deck
        CardDeck leftCardDeck = new CardDeck();
        leftCardDeck.setCard(new Card(3));
        leftCardDeck.setCard(new Card(4));
        leftCardDeck.setCard(new Card(2));
        leftCardDeck.setCard(new Card(5));
        //create right card deck
        CardDeck rightCardDeck = new CardDeck();
        rightCardDeck.setCard(new Card(6));
        rightCardDeck.setCard(new Card(1));
        rightCardDeck.setCard(new Card(7));
        rightCardDeck.setCard(new Card(8));
        //create player's hand 
        CardDeck handCardDeck = new CardDeck();
        handCardDeck.setCard(new Card(4));
        handCardDeck.setCard(new Card(7));
        handCardDeck.setCard(new Card(4));
        handCardDeck.setCard(new Card(9));
        //set all the decks
        player.setLeftCardDeck(leftCardDeck);
        player.setRightCardDeck(rightCardDeck);
        player.setInitialHandCardDeck(handCardDeck);
        player.sortInitialHandCardDeck(4);
        //preferredCardDeck: 4 4, notPreferredCardDeck: 7 9
        LinkedList<Card> newAndDiscardCards = player.drawAndDiscardAction();
        assertTrue(newAndDiscardCards.getFirst().getCardValue() == 3);
        assertTrue(newAndDiscardCards.getLast().getCardValue() == 9);
        assertTrue(player.getLeftCardDeck().toString().equals(" 4 2 5"));
        assertTrue(player.getRightCardDeck().toString().equals(" 6 1 7 8 9"));
        assertTrue(player.getPreferredCardDeck().toString().equals(" 4 4"));
        assertTrue(player.getNotPreferredCardDeck().toString().equals(" 3 7"));
        newAndDiscardCards.clear();
    }

    @Test
    public void testCreatePlayerFile() {
        //check that createPlayerFileName() method creates the player file output correctly
        Player player = new Player(10);
        player.createPlayerFileName();
        File file = new File("player10_output.txt");
        //check that the file has been created
        assertTrue(file.exists());
        //check that it is empty
        assertTrue(file.length() == 0);
    }

    @Test
    public void testDeleteFileContent() {
        //test that the file content is deleted correctly
        Player player = new Player(10);
        player.createPlayerFileName();
        player.deleteFileContent();
        File file = new File("player10_output.txt");
        assertTrue(file.exists());
        assertTrue(file.length() == 0);
    }

    @Test
    public void testWriteToPlayerFile() {
        Player player = new Player(10);
        //create a player file and write to it
        player.createPlayerFileName();
        player.writeToPlayerFile("Player 10 initial hand");
        try{
            File file = new File("player10_output.txt");
            Scanner fileReader = new Scanner(file);
            String line = fileReader.nextLine();
            //check that it has written to the file correctly
            assertTrue(line.equals("Player 10 initial hand"));
            fileReader.close();

        }catch (FileNotFoundException e){
            System.out.println("File not found, please try again");
        }
    }

    @Test
    public void testWriteToPlayerFileWin() {
        //set up a player with a winning hand
        Player player = new Player(10);
        CardDeck cardDeck = new CardDeck();
        cardDeck.setCard(new Card(1));
        cardDeck.setCard(new Card(1));
        cardDeck.setCard(new Card(1));
        cardDeck.setCard(new Card(1));
        player.setInitialHandCardDeck(cardDeck);
        player.sortInitialHandCardDeck(4);
        player.createPlayerFileName();
        //write to the file that the player has won
        player.writeToPlayerFileWin();
        String text = new String();
        try{
            File file = new File("player10_output.txt");
            Scanner fileReader = new Scanner(file);
            while(fileReader.hasNextLine()){
                String line = fileReader.nextLine();
                text = text + line + "\n";
            }
            fileReader.close();

        }catch (FileNotFoundException e){
            System.out.println("File not found, please try again");
        }
        //check that is has done so correctly
        assertTrue(text.equals("player 10 wins\nplayer 10 exits\nplayer 10 final hand: 1 1 1 1\n"));
    }

    @Test
    public void testWriteToPlayerFileLose() {
        //set up a player without a winning hand
        Player player = new Player(10);
        CardDeck cardDeck = new CardDeck();
        cardDeck.setCard(new Card(1));
        cardDeck.setCard(new Card(2));
        cardDeck.setCard(new Card(1));
        cardDeck.setCard(new Card(1));
        player.setInitialHandCardDeck(cardDeck);
        player.sortInitialHandCardDeck(4);
        player.createPlayerFileName();
        //write to the file that another player has won
        player.writeToPlayerFileLose(1);
        String text = new String();
        try{
            File file = new File("player10_output.txt");
            Scanner fileReader = new Scanner(file);
            while(fileReader.hasNextLine()){
                String line = fileReader.nextLine();
                text = text + line + "\n";
            }
            fileReader.close();

        }catch (FileNotFoundException e){
            System.out.println("File not found, please try again");
        }
        //check that is has done so correctly
        assertTrue(text.equals("player 1 has informed player 10 that player 1 has won\n" + //
                "player 10 exits\n" + //
                "player 10 hand: 1 2 1 1\n"));
    }

    @Test
    public void testPlayerLogic1(){
        //test that the player has won with their initial hand
        WinnerSignal winnerSignalObject = new WinnerSignal();
        StartGameSignal startGameSignal = new StartGameSignal();
        ReadyToPlaySignal readyToPlaySignal = new ReadyToPlaySignal(1);
        Player player = new Player(10, winnerSignalObject, startGameSignal, readyToPlaySignal);
        //create player's hand such that it is a winning hand
        CardDeck handCardDeck = new CardDeck();
        handCardDeck.setCard(new Card(3));
        handCardDeck.setCard(new Card(3));
        handCardDeck.setCard(new Card(3));
        handCardDeck.setCard(new Card(3));
        //sort the player's hand
        player.setInitialHandCardDeck(handCardDeck);
        player.sortInitialHandCardDeck(4);
        //preferredCardDeck: , notPreferredCardDeck: 3 3 3 3
        //call the player logic and then end it
        player.playerLogic();
        player.endPlayerLogic();
        
        String testText = new String();
        try{
            File file = new File("player10_output.txt");
            Scanner fileReader = new Scanner(file);
            while(fileReader.hasNextLine()){
                String line = fileReader.nextLine();
                testText = testText + line + "\n";
            }
            fileReader.close();

        }catch (FileNotFoundException e){
            System.out.println("File not found, please try again");
        }
        //check that the player should have won correctly
        assertTrue(testText.equals("player 10 initial hand 3 3 3 3\nplayer 10 wins\nplayer 10 exits\nplayer 10 final hand: 3 3 3 3\n"));
        assertTrue(player.getPreferredCardDeck().toString().equals(""));
        assertTrue(player.getNotPreferredCardDeck().toString().equals(" 3 3 3 3"));
        
    }

    @Test
    public void testPlayerLogic2(){
        //Testing player wins after one round
        WinnerSignal winnerSignalObject = new WinnerSignal();
        //to make the player logic work, a mock object is necessary
        //this object makes sure that when player logic calls to wait on the start game signal,
        //the player actually won't wait it will just start playing automatically, 
        //because the mock object had empty methods.
        IStartGameSignal mockStartGameSignal = new MockStartGameSignal();
        ReadyToPlaySignal readyToPlaySignal = new ReadyToPlaySignal(1);
        Player player = new Player(10, winnerSignalObject, mockStartGameSignal, readyToPlaySignal);
        //set up the decks and the players hand such that the player won/t win with their initial hand 
        //but after drawing and discarding a card once
        //create left card deck
        CardDeck leftCardDeck = new CardDeck(1);
        leftCardDeck.setCard(new Card(10));
        leftCardDeck.setCard(new Card(3));
        leftCardDeck.setCard(new Card(2));
        leftCardDeck.setCard(new Card(5));
        //create right card deck
        CardDeck rightCardDeck = new CardDeck(2);
        rightCardDeck.setCard(new Card(6));
        rightCardDeck.setCard(new Card(1));
        rightCardDeck.setCard(new Card(7));
        rightCardDeck.setCard(new Card(8));
        //create player's hand 
        CardDeck handCardDeck = new CardDeck();
        handCardDeck.setCard(new Card(10));
        handCardDeck.setCard(new Card(10));
        handCardDeck.setCard(new Card(3));
        handCardDeck.setCard(new Card(10));
        //set all the decks
        player.setLeftCardDeck(leftCardDeck);
        player.setRightCardDeck(rightCardDeck);
        player.setInitialHandCardDeck(handCardDeck);
        player.sortInitialHandCardDeck(4);
        //preferredCardDeck: 10 10 10, notPreferredCardDeck: 3
        //call the player logic and then end it
        player.playerLogic();
        player.endPlayerLogic();

        String testText = new String();
        try{
            File file = new File("player10_output.txt");
            Scanner fileReader = new Scanner(file);
            while(fileReader.hasNextLine()){
                String line = fileReader.nextLine();
                testText = testText + line + "\n";
            }
            fileReader.close();

        }catch (FileNotFoundException e){
            System.out.println("File not found, please try again");
        }
        //check that the player has won correctly
        String player10Text = "player 10 initial hand 10 10 3 10\nplayer 10 draws a 10 from deck 1\nplayer 10 discards a 3 to deck 2\nplayer 10 current hand is 10 10 10 10\nplayer 10 wins\nplayer 10 exits\nplayer 10 final hand: 10 10 10 10\n";
        assertTrue(testText.equals(player10Text));
        assertTrue(player.getPreferredCardDeck().toString().equals(" 10 10 10 10"));
        assertTrue(player.getNotPreferredCardDeck().toString().equals(""));
    }

    @Test
    public void testRun() {
        //simply test that when the thread is started, that it is alive
        WinnerSignal winnerSignalObject = new WinnerSignal();
        IStartGameSignal mockStartGameSignal = new MockStartGameSignal();
        ReadyToPlaySignal readyToPlaySignal = new ReadyToPlaySignal(1);
        Player player = new Player(10, winnerSignalObject, mockStartGameSignal, readyToPlaySignal);
        //create left card deck
        CardDeck leftCardDeck = new CardDeck(1);
        leftCardDeck.setCard(new Card(5));
        leftCardDeck.setCard(new Card(3));
        leftCardDeck.setCard(new Card(2));
        leftCardDeck.setCard(new Card(5));
        leftCardDeck.setCard(new Card(6));
        leftCardDeck.setCard(new Card(4));
        //create right card deck
        CardDeck rightCardDeck = new CardDeck(2);
        //create player's hand 
        CardDeck handCardDeck = new CardDeck();
        handCardDeck.setCard(new Card(6));
        handCardDeck.setCard(new Card(4));
        handCardDeck.setCard(new Card(3));
        handCardDeck.setCard(new Card(2));
        //set all the decks
        player.setLeftCardDeck(leftCardDeck);
        player.setRightCardDeck(rightCardDeck);
        player.setInitialHandCardDeck(handCardDeck);
        player.sortInitialHandCardDeck(4);
        //preferredCardDeck: , notPreferredCardDeck: 6 4 3 2
        player.start();
        //check that the thread is alive
        assertTrue(player.isAlive());
        //since we have not set up a game with a winner, the the thread is stopped
        //and the player will be looking for a winner, the winner signal will be empty
        //therefore it will return -1, to not throw an exception
        player.stopThread();
        try{
            Thread.sleep(30);
        }catch(InterruptedException e){}
        assertFalse(player.isAlive());
    }

    @Test
    public void testStopThread() {
        //this test just checks that once the thread is stopped, that it is not alive
        WinnerSignal winnerSignalObject = new WinnerSignal();
        IStartGameSignal mockStartGameSignal = new MockStartGameSignal();
        ReadyToPlaySignal readyToPlaySignal = new ReadyToPlaySignal(1);
        Player player = new Player(10, winnerSignalObject, mockStartGameSignal, readyToPlaySignal);
        //create left card deck
        CardDeck leftCardDeck = new CardDeck(1);
        leftCardDeck.setCard(new Card(10));
        leftCardDeck.setCard(new Card(3));
        leftCardDeck.setCard(new Card(2));
        leftCardDeck.setCard(new Card(5));
        //create right card deck
        CardDeck rightCardDeck = new CardDeck(2);
        rightCardDeck.setCard(new Card(6));
        rightCardDeck.setCard(new Card(1));
        rightCardDeck.setCard(new Card(7));
        rightCardDeck.setCard(new Card(8));
        //create player's hand 
        CardDeck handCardDeck = new CardDeck();
        handCardDeck.setCard(new Card(10));
        handCardDeck.setCard(new Card(10));
        handCardDeck.setCard(new Card(3));
        handCardDeck.setCard(new Card(10));
        //set all the decks
        player.setLeftCardDeck(leftCardDeck);
        player.setRightCardDeck(rightCardDeck);
        player.setInitialHandCardDeck(handCardDeck);
        player.sortInitialHandCardDeck(4);
        //start thread
        player.start();
        assertTrue(player.isAlive());
        //stop thread
        player.stopThread();
        //wait for the thread to end
        try{
            Thread.sleep(30);
        }catch(InterruptedException e){}
        //check that is is not alive
        assertFalse(player.isAlive());
    }   
}
