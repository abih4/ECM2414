# **To Test:**

After unzipping the *cardsTest.zip* and the *project.zip*, you just need to run the *test.zsh* in the project directory (*cardsTest/project*) through the terminal (command: **./test.zsh**). This will compile and run the TestSuite class correctly, and should pass all the tests.  
If this does not work, you can also run the commands in *test.zsh* straight into the terminal, to give the same results: 
 
**javac -cp lib/junit-4.13.2.jar:cards.jar testcardgame/TestCard.java 
testcardgame/TestCardDeck.java testcardgame/TestGame.java 
testcardgame/TestPlayer.java testcardgame/TestWinnerSignal.java 
TestCardGame.java TestSuite.java**
 
**java -cp lib/junit-4.13.2.jar:lib/hamcrest-core-1.3.jar:cards.jar:. 
org.junit.runner.JUnitCore TestSuite**
 
 
 
 
# **To Run:**
 
Compile: (put .class files into ./bin): 
**javac CardGame.java -d ./bin**
 
Run: 
**java -classpath ./bin CardGame**
