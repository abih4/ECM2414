package cardgame;

/**
 * StartGameSignal is used to signal all the player threads to start playing the game.
 * 
 * @author Abigail Hinton
 * @author Giulia Brown
 * @version 1.0.0
 */
public class StartGameSignal implements IStartGameSignal{
    //constructor
    public StartGameSignal(){}

    /** 
     * This method makes whoever calls it wait on its object.
     * 
     * @throws InterruptedException
     */
    @Override
    public void waitToStartGame() throws InterruptedException{
        synchronized(this){
            this.wait();
        }
    }

    /**
     * This method notifies all the threads that are waiting on its object. 
     */
    @Override
    public void notifyToStartGame() {
        //notify all the player threads
        synchronized(this){
            this.notifyAll();
        }
    }
}
