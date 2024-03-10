package cardgame;
import java.util.LinkedList;

/**
 * A winner player signal has a list which will contain the indexes of the players that have won.
 * A winner player signal object will be created by the Card Game and passed to all the players.
 * Thanks to this object, the main thread can be notified when a player has won.
 * 
 * @author Abigail Hinton
 * @author Giulia Brown
 * @version 1.0.0
 * 
 */
public class WinnerSignal {
    //attribute
    private LinkedList<Integer> winnersList;

    //constructor
    /**
     * The constructor creates a new object by assigning the given empty list. 
     */
    public WinnerSignal (){
        this.winnersList = new LinkedList<Integer>();
    }

    //methods
    /**
     * The method returns the size of the winners list.
     * 
     * @return size
     */
    public synchronized int getWinnersListSize(){
        return this.winnersList.size();
    }

    /**
     * The method returns the winners list.
     * 
     * @return list of player indexes
     */
    public synchronized LinkedList<Integer> getWinnersList(){
        return this.winnersList;
    }

    /**
     * The method adds a player index to the winners list.
     * 
     * @param playerIndex player's index
     */
    public synchronized void addWinner(int playerIndex){
        this.winnersList.add(playerIndex);
        try{
            synchronized(this){
                Thread.sleep(30);
                //notify any thread waiting on the player signal (the main thread) 
                this.notify();
            }
        }catch(InterruptedException e){}
        
    }

    /**
     * The method returns the player index of the winner or -1 if there is no winner.
     * 
     * @return player index or -1
     */
    public synchronized int getWinner(){
        if(this.winnersList.size() == 0){
            return -1;
        }else{
            return this.winnersList.getFirst();
        }
    }

    /**
     * The method returns a string with the winners list content (list of player indexes).
     * 
     * @return string with winners list content
     */
    public synchronized String toString(){
        String output = "";
        for(int i=0; i < this.getWinnersListSize(); i++){
            output += " " + Integer.toString(this.winnersList.get(i));
        }

        return output;
    }
}
