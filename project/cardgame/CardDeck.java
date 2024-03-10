package cardgame;

import java.util.LinkedList;

/**
 * A card deck has a unique numerical identifier (cardDeckIndex), and a Linked List that contain
 * all the cards of the deck.
 * 
 * @author Abigail Hinton
 * @author Giulia Brown
 * @version 1.0.0
 * 
 */
public class CardDeck {
    //attributes
    private LinkedList<Card> cardDeck;
    private int cardDeckIndex;

    //contructor
    /**
     * The constructor creates a cardDeck object by initialising an empty list.
     * 
     */
    public CardDeck(){
        this.cardDeck = new LinkedList<Card>();
    }

    /**
     * The constructor creates a cardDeck object by initialising an empty list and 
     * assigning the given card deck index
     * 
     * @param cardDeckIndex card deck index
     */
    public CardDeck(int cardDeckIndex){
        this.cardDeck = new LinkedList<Card>();
        this.cardDeckIndex = cardDeckIndex;
    }

    //getter and setter methods
    /** 
     * The method return the card deck index.
     * 
     * @return card deck index
     */
    public synchronized int getCardDeckIndex(){
        return this.cardDeckIndex;
    }
    
    /** 
     * The method returns the list of cards in the card deck.
     * 
     * @return card deck
     */
    public synchronized LinkedList<Card> getCardDeck(){
        return this.cardDeck;
    }
    
    /** 
     * The method adds a new card to the card deck.
     * 
     * @param card new card to add
     */
    public synchronized void setCard(Card card){
        this.cardDeck.add(card);
    }
    
    /** 
     * The method adds a new card at the top of the card deck.
     * 
     * @param card new card to add
     */
    public synchronized void setFirstCard(Card card){
        this.cardDeck.addFirst(card);
    }

    /** 
     * The method adds a new card at the bottom of the card deck,
     * 
     * @param card new card to add
     */
    public synchronized void setLastCard(Card card){
        this.cardDeck.addLast(card);
    }

    
    /** 
     * The method returns the first card of the card deck.
     * 
     * @return first card
     */
    public synchronized Card getFirstCard(){
        return this.cardDeck.getFirst();
    }

    
    /** 
     * The method returns the last card of the card deck.
     * 
     * @return last card
     */
    public synchronized Card getLastCard(){
        return this.cardDeck.getLast();
    }

    
    /** 
     * The method returns the card in the given position.
     * 
     * @param positionIndex card position index
     * @return card
     */
    public synchronized Card getCard(int positionIndex){
        return this.cardDeck.get(positionIndex);
    }

    /**
     * The method removes the first card of the card deck.
     */
    public synchronized void removeFirstCard(){
        this.cardDeck.removeFirst();
    }

    /**
     * The method removes the last card of the card deck.
     */
    public synchronized void removeLastCard(){
        this.cardDeck.removeLast();
    }

    /** 
     * The method returns the size of the card deck.
     * 
     * @return card deck size
     */
    public synchronized int getCardDeckSize(){
        return this.cardDeck.size();
    }
    
    /** 
     * The method returns a string with the card deck content (the list of cards).
     * 
     * @return string with card deck content
     */
    public synchronized String toString(){
        String output = "";
        for(int i=0; i < this.getCardDeckSize(); i++){
            output += " " + Integer.toString(this.cardDeck.get(i).getCardValue());
        }

        return output;
    }
}
