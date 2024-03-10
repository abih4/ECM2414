import org.junit.Test;

import cardgame.*;

import static org.junit.Assert.*;
import java.util.LinkedList;

public class TestCardGame {
    @Test
    public void testIsFileValid() {
        int numberOfPlayers = 3;
        //testing with a valid file
        assertTrue(CardGame.isFileValid("testTexts/testTrue.txt", numberOfPlayers));
        //testing with an empty file
        assertFalse(CardGame.isFileValid("testTexts/testFalseEmpty.txt", numberOfPlayers));
        //testing with a file containing characters or spaces
        assertFalse(CardGame.isFileValid("testTexts/testFalseInvalid1.txt", numberOfPlayers));
        //testing with a file with an empty line
        assertFalse(CardGame.isFileValid("testTexts/testFalseInvalid2.txt", numberOfPlayers));
    }

    @Test
    public void testReadFile() {
        //check that the file given by the user is read correctly, and saved into a list
        LinkedList<Card> packOfCards = new LinkedList<Card>();
        packOfCards.add(CardGame.createCard(1));
        packOfCards.add(CardGame.createCard(2));
        packOfCards.add(CardGame.createCard(3));
        assertTrue(CardGame.readFile("testTexts/testCardDeck.txt").getFirst().getCardValue() == packOfCards.getFirst().getCardValue());
        assertTrue(CardGame.readFile("testTexts/testCardDeck.txt").getLast().getCardValue() == packOfCards.getLast().getCardValue());
    }

    @Test
    public void testCreateCard() {
        //check that a card is created correctly
        Card card = new Card(1);
        assertTrue(CardGame.createCard(1).getCardValue() == card.getCardValue());
        assertTrue(CardGame.createCard(1).getCardValue() == 1);
    }
}

