package testcardgame;

import org.junit.Test;

import cardgame.Card;

import static org.junit.Assert.*;

public class TestCard {
    @Test
    public void testGetCardValue() {
        //test getCardValue()
        //create card with value 5
        Card card = new Card(5);
        //check it returns the correct value
        assertTrue(card.getCardValue() == 5);
        assertFalse(card.getCardValue() == 4);
    }

    @Test
    public void testIsPreferredCardValue() {
        //test isPreferredCardValue()
        //create card with value 5
        Card card = new Card(5);
        //if you are player 5, then isPreferredCardValue() should return true
        assertTrue(card.isPreferredCardValue(5));
        //if you are player 3, then isPreferredCardValue() should return false
        assertFalse(card.isPreferredCardValue(3));
    }
}
