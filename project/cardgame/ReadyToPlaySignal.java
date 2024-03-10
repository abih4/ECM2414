package cardgame;

/**
 * ReadyToPlaySignal class is used to wait until all the player threads are ready to play
 * (once they have checked if they have won from their initial hand or not), and then notify 
 * whoever is waiting on the object (in this case the main thread).
 * 
 * @author Abigail Hinton
 * @author Giulia Brown
 * @version 1.0.0
 */
public class ReadyToPlaySignal{
    //attribute
    private int counter;

    //constructor
    public ReadyToPlaySignal(int numberOfPlayers){
        this.counter = numberOfPlayers;
    }

    /**
     * This method decreases the counter every tine it is called by a player.
     * When all the player threads have called this method, the counter will be zero,
     * and the main thread will be notified.
     */
    public void readyToPlay(){
        //decrease counter
        this.counter--;
        //if all the players are ready to play then notify the main thread
        if(this.counter == 0){
            synchronized(this){
                this.notify();
            }
        }
    }

}
