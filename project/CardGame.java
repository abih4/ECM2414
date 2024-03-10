
import java.util.Scanner;

import cardgame.*;

import java.util.LinkedList;
import java.io.*;

/**
 * CardGame is an executable class that takes as inputs the number of players and the location of a
 * valid input pack.
 * The game won't start until a valid pack is given. 
 * Once the two inputs are valid, the file will be read and saved into a list.
 * Then a new game is created and started.
 */
public class CardGame{
    /** 
     * The main method asks for two input parameters and then starts a new game.
     * 
     * @param args[]
     */
    public static void main(String args[]){
        //ask the user for two inputs: number of players and fileName
        boolean validInput = false;
        String fileName = "";
        int numberOfPlayers = 0;
        //open scanners
        Scanner input1 = new Scanner(System.in);
        Scanner input2 = new Scanner(System.in);
        //ask for inputs until they are valid
        while(!validInput){
            //read numberOfPlayers from user
            numberOfPlayers = scanNumberOfPlayers(input1);

            //read fileName from user
            fileName = scanFileName(input2);

            //if numberOfPlayers && fileName are valid, then validInput = true.
            if(numberOfPlayers > 0){
                if(isFileValid(fileName, numberOfPlayers)){
                    validInput = true;
                }
            }
            //otherwise inform the user to try again
            if (!validInput) {
                System.out.println("Invalid input, please try again.");
            }
        }
        //close scanners
        input1.close();
        input2.close();

        //read file to create pack of cards 
        LinkedList<Card> packOfCards;
        packOfCards = readFile(fileName);

        //create new card game
        Game cardGame = new Game(numberOfPlayers, packOfCards);
        //start new card game
        cardGame.playGame();
    }

    /** 
     * Request number of players to the user.
     * 
     * @param input
     * @return number of players
     */
    public static int scanNumberOfPlayers(Scanner input){
        //request number of players
        System.out.println("Please enter the number of players:");
        int numberOfPlayers = input.nextInt();
        //return number of players
        return numberOfPlayers;
    }

    /** 
     * Request the location of a valid input pack to the user.
     * 
     * @param input
     * @return file name
     */
    public static String scanFileName(Scanner input){
        //request file name
        System.out.println("Please enter location of pack to load:");
        String fileName = input.nextLine();
        //return file name
        return fileName;
    }
    
    /** 
     * Check if the given file is valid.
     * 
     * @param fileName
     * @param numberOfPlayers
     * @return Boolean
     */
    public static Boolean isFileValid(String fileName, int numberOfPlayers){
        //initialise the card counter, and the total number of cards there should be
        int cardCounter = 0;
        int numberOfCards = 8 * numberOfPlayers;
        boolean cardValid = true;
        //read the file and check it
        try{
            File fileOfCards = new File(fileName);
            Scanner fileReader = new Scanner(fileOfCards);
            while(fileReader.hasNextLine() && cardValid){
                String card = fileReader.nextLine();
                //check that there are no empty lines, no spaces, no characters, and no negative numbers
                if(card.equals("") || card.contains(" ")){
                    cardValid = false;
                }else{
                    try{
                        int intCard = Integer.parseInt(card);
                        cardCounter++;
                        if(intCard < 1){
                            cardValid = false;
                        }
                    }catch(NumberFormatException e){
                        cardValid = false;
                    }
                    
                    
                }
            }
            if(cardCounter != numberOfCards || cardValid == false){
                fileReader.close();
                return false;
            }
            fileReader.close();

        }catch (FileNotFoundException e){
            System.out.println("File not found, please try again");
            return false;
        }
        return true;
    }

    /** 
     * This method reads a file given a file name and path, and saves the content into a list of cards.
     * 
     * @param fileName
     * @return pack of cards
     */
    public static LinkedList<Card> readFile(String fileName){
        //create linked list of cards
        LinkedList<Card> packOfCards = new LinkedList<Card>();
        //read the file
        try{
            File fileOfCards = new File(fileName);
            Scanner fileReader = new Scanner(fileOfCards);
            while(fileReader.hasNextLine()){
                String card = fileReader.nextLine();
                //parse into integer
                int intCard = Integer.parseInt(card);
                //adding card to linked list packOfCards
                packOfCards.add(createCard(intCard));
            }
            fileReader.close();

        }catch (FileNotFoundException e){
            System.out.println("File not found");
        }
        return packOfCards;
    }

    /** 
     * This method created a card with a given value.
     * 
     * @param cardValue
     * @return Card
     */
    public static Card createCard(int cardValue){
        Card card = new Card(cardValue);

        return card;
    }
    
}