

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import testcardgame.TestCard;
import testcardgame.TestCardDeck;
import testcardgame.TestGame;
import testcardgame.TestPlayer;
import testcardgame.TestWinnerSignal;

//import cardgame.*;

@RunWith(Suite.class)

@Suite.SuiteClasses({
    TestCard.class,
    TestCardDeck.class,
    TestGame.class,
    TestPlayer.class,
    TestWinnerSignal.class,
    TestCardGame.class
})

public class TestSuite {
    //the TestSuite will run all the test classes together
}
