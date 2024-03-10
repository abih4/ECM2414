package cardgame;

/**
 * A card has a unique numerical denomination that defines the value of the card.
 * 
 * @author Abigail Hinton
 * @author Giulia Brown
 * @version 1.0.0
 * 
 */
public class Card {
    //attribute
    private int cardValue;

    //constructor
    /**
     * The card constructor creates a card object assigning the given card value.
     * 
     * @param cardValue value of the card
     * 
     */
    public Card(int cardValue){
        this.cardValue = cardValue;
    }

    //methods
    /** 
     * The method return the value of the card.
     * 
     * @return value of the card
     */
    public synchronized int getCardValue(){
        return this.cardValue;
    }

    /** 
     * The method checks if the value of the card is preferred denomination of the given player.
     * 
     * @param playerIndex index of the player
     * @return true if the card value is equals to the player index, or false otherwise
     */
    public synchronized Boolean isPreferredCardValue(int playerIndex){
        if(this.cardValue == playerIndex){
            return true;
        }
        return false;
    }
}
