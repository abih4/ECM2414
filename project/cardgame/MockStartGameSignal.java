package cardgame;

/**
 * MockStartGameSignal is a mock object used for testing the Player methods.
 * Insted of calling wait() and notify(), it does nothing.
 * 
 * @author Abigail Hinton
 * @author Giulia Brown
 * @version 1.0.0
 */
public class MockStartGameSignal implements IStartGameSignal{

    @Override
    public void waitToStartGame() throws InterruptedException {
        //do nothing
    }

    @Override
    public void notifyToStartGame() {
        //do nothing
    }
    
}
