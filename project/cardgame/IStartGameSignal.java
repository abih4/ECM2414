package cardgame;

/**
 * This class is an interface, an abstract class that specifies the behaviour of a
 * class that implements it.
 * This interface will make it possible to create mock objects for testing.
 * 
 * The StartGameSignal interface defines two methods: waitToStartGame and notifyToStartGame.
 * 
 * @author Abigail Hinton
 * @author Giulia Brown
 * @version 1.0.0
 */
public interface IStartGameSignal {
    //methods with empty bodies
    public void waitToStartGame()throws InterruptedException;
    public void notifyToStartGame();
}
