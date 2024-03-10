package testcardgame;

import org.junit.Test;

import cardgame.*;

import static org.junit.Assert.*;

public class TestWinnerSignal {

    @Test
    public void testGetWinnersListSize() {
        //create a winner signal object 
        WinnerSignal winnerSignalObject = new WinnerSignal();
        //check it is empty at the start
        assertTrue(winnerSignalObject.getWinnersListSize() == 0);
        //then add three winners
        winnerSignalObject.addWinner(4);
        winnerSignalObject.addWinner(6);
        winnerSignalObject.addWinner(3);
        //check that now the size is 3
        assertTrue(winnerSignalObject.getWinnersListSize() == 3);
    }

    @Test
    public void testGetWinnerList(){
        //create a winner signal object 
        WinnerSignal winnerSignalObject = new WinnerSignal();
        //then add three winners
        winnerSignalObject.addWinner(4);
        winnerSignalObject.addWinner(6);
        winnerSignalObject.addWinner(3);
        //check that the winners have been place correctly
        assertTrue(winnerSignalObject.getWinnersList().get(0) == 4);
        assertTrue(winnerSignalObject.getWinnersList().get(1) == 6);
        assertTrue(winnerSignalObject.getWinnersList().get(2) == 3); 
    }

    @Test
    public void testAddWinner() {
        //create a winner signal object 
        WinnerSignal winnerSignalObject = new WinnerSignal();
        //then add three winners
        winnerSignalObject.addWinner(3);
        winnerSignalObject.addWinner(5);
        winnerSignalObject.addWinner(1);
        //check that the size is 3
        assertTrue(winnerSignalObject.getWinnersListSize() == 3);
        //add a winner
        winnerSignalObject.addWinner(2);
        //now check that the size is 4
        assertTrue(winnerSignalObject.toString().equals(" 3 5 1 2"));
        assertTrue(winnerSignalObject.getWinnersListSize() == 4);
    }

    @Test
    public void testGetWinner1() {
        //create a winner signal object 
        WinnerSignal winnerSignalObject = new WinnerSignal();
        //add three winners
        winnerSignalObject.addWinner(4);
        winnerSignalObject.addWinner(6);
        winnerSignalObject.addWinner(3);
        //check that the first winner is player 4
        assertTrue(winnerSignalObject.getWinner() == 4); 
    }

    @Test
    public void testGetWinner2(){
        //create an empty winner signal object
        WinnerSignal winnerSignalObject = new WinnerSignal();
        //check that if you try to get a winner it will return -1, and it will not throw an exception
        assertTrue(winnerSignalObject.getWinner() == -1);
    }

    @Test
    public void testToString() {
        //check that the toString() method returns the expected string
        WinnerSignal winnerSignalObject = new WinnerSignal();
        assertTrue(winnerSignalObject.toString().equals(""));
        winnerSignalObject.addWinner(4);
        winnerSignalObject.addWinner(6);
        winnerSignalObject.addWinner(3);
        assertTrue(winnerSignalObject.toString().equals(" 4 6 3"));
    }
}
