package testcardgame;

import java.util.LinkedList;
import org.junit.Test;

import cardgame.*;

import static org.junit.Assert.*;

public class TestCardDeck {
    
    @Test
    public void testGetCardDeckIndex() {
        //create a card deck with index 3
        CardDeck cardDeck = new CardDeck(3);
        //getCardDeckIndex() should return 3, nothing else
        assertTrue(cardDeck.getCardDeckIndex() == 3);
        assertFalse(cardDeck.getCardDeckIndex() == 4);
    }

    @Test
    public void testSetCard() {
        //create an empty card deck
        CardDeck cardDeck = new CardDeck();
        //add a card with value 5
        cardDeck.setCard(new Card(5));
        //now the card deck should contain 5
        assertTrue(cardDeck.getCard(0).getCardValue() == 5);
    }

     @Test
    public void testGetCard() {
        //create an empty card deck
        CardDeck cardDeck = new CardDeck();
        //add three cards to the card deck
        cardDeck.setCard(new Card(5));
        cardDeck.setCard(new Card(6));
        cardDeck.setCard(new Card(4));
        //check these card are all present in the card deck and in the correct order
        assertTrue(cardDeck.getCard(0).getCardValue() == 5);
        assertTrue(cardDeck.getCard(1).getCardValue() == 6);
        assertTrue(cardDeck.getCard(2).getCardValue() == 4);
    }

    @Test
    public void testGetCardDeck() {
        //create a list with 4 cards
        LinkedList<Card> cardDeckList = new LinkedList<Card>();
        cardDeckList.add(new Card(5));
        cardDeckList.add(new Card(3));
        cardDeckList.add(new Card(4));
        cardDeckList.add(new Card(6));
        //create a card deck with the same four cards
        CardDeck cardDeck = new CardDeck();
        cardDeck.setCard(new Card(5));
        cardDeck.setCard(new Card(3));
        cardDeck.setCard(new Card(4));
        cardDeck.setCard(new Card(6));
        //now check that the contents of the two decks are the same
        for(int i = 0; i < 4; i++){
            assertTrue(cardDeck.getCardDeck().get(i).getCardValue() == cardDeckList.get(i).getCardValue());
        }
    }

    @Test
    public void testSetFirstCard() {
        //create an empty card deck
        CardDeck cardDeck = new CardDeck();
        //add two cards in it
        cardDeck.setCard(new Card(1));
        cardDeck.setCard(new Card(3));
        //then add a card at the top of the deck
        cardDeck.setFirstCard(new Card(5));
        //check it's been put in the right position
        assertTrue(cardDeck.getCard(0).getCardValue() == 5);
    }

    @Test
    public void testSetLastCard() {
        //create a card deck with one card
        CardDeck cardDeck = new CardDeck();
        cardDeck.setCard(new Card(5));
        //then add a card at the bottom of the deck
        cardDeck.setLastCard(new Card(3));
        //check it's been placed correctly
        assertTrue(cardDeck.getCard(1).getCardValue() == 3);
    }

    @Test
    public void testGetFirstCard() {
        //create a card deck with 3 cards (5, 2, 1)
        CardDeck cardDeck = new CardDeck();
        cardDeck.setCard(new Card(5));
        cardDeck.setCard(new Card(2));
        cardDeck.setCard(new Card(1));
        //assert that the first value is 5
        assertTrue(cardDeck.getFirstCard().getCardValue() == 5);
    }

    @Test
    public void testGetLastCard() {
        //create a card deck with 3 cards (5, 2, 1)
        CardDeck cardDeck = new CardDeck();
        cardDeck.setCard(new Card(5));
        cardDeck.setCard(new Card(2));
        cardDeck.setCard(new Card(1));
        //check that the last card is 1
        assertTrue(cardDeck.getLastCard().getCardValue() == 1);
    }

   @Test
    public void testRemoveFirstCard() {
        //create a card deck with three cards (5, 2, 1)
        CardDeck cardDeck = new CardDeck();
        cardDeck.setCard(new Card(5));
        cardDeck.setCard(new Card(2));
        cardDeck.setCard(new Card(1));
        //remove the first card
        cardDeck.removeFirstCard();
        //check that the two remaining cards are 2 and 1
        assertTrue(cardDeck.getCard(0).getCardValue() == 2);
        assertTrue(cardDeck.getCard(1).getCardValue() == 1);
    }

    @Test
    public void testRemoveLastCard() {
        //create card deck with three cards
        CardDeck cardDeck = new CardDeck();
        cardDeck.setCard(new Card(5));
        cardDeck.setCard(new Card(2));
        cardDeck.setCard(new Card(1));
        //remove the last card
        cardDeck.removeLastCard();
        //check that the remaining cards are 5 and 2
        assertTrue(cardDeck.getCard(0).getCardValue() == 5);
        assertTrue(cardDeck.getCard(1).getCardValue() == 2);
    }

    @Test
    public void testGetCardDeckSize() {
        //create an empty card deck
        CardDeck cardDeck = new CardDeck();
        //the size is 0
        assertTrue(cardDeck.getCardDeckSize() == 0);
        //add three cards
        cardDeck.setCard(new Card(5));
        cardDeck.setCard(new Card(2));
        cardDeck.setCard(new Card(1));
        //check the size now is 3
        assertTrue(cardDeck.getCardDeckSize() == 3);
    }

    @Test
    public void testToString() {
        //create a card deck
        CardDeck cardDeck = new CardDeck();
        //check that the string returned is empty
        assertTrue(cardDeck.toString().equals(""));
        //then add three cards
        cardDeck.setCard(new Card(5));
        cardDeck.setCard(new Card(2));
        cardDeck.setCard(new Card(1));
        //check that the string returned now contains the correct card values
        assertTrue(cardDeck.toString().equals(" 5 2 1"));
    }
}
