#!/bin/zsh
javac -cp lib/junit-4.13.2.jar:cards.jar testcardgame/TestCard.java testcardgame/TestCardDeck.java testcardgame/TestGame.java testcardgame/TestPlayer.java testcardgame/TestWinnerSignal.java TestCardGame.java TestSuite.java
java -cp lib/junit-4.13.2.jar:lib/hamcrest-core-1.3.jar:cards.jar:. org.junit.runner.JUnitCore TestSuite