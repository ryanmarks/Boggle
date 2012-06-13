// The "Boggle" class.
import java.awt.*;
import hsa.Console;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
public class Boggle
{
    static Console c;           // The output console

    public static void main (String[] args)
    {
	c = new Console ();
	int wordRow = 16;
	int wordColumn = 3;
	int score = 0;
	String gottenWord;

	c.clear ();
	char board[] [] = boardGen ();
	drawBoard (board);



	while (true)
	{
	    c.setCursor (wordRow, wordColumn);
	    c.print ("          ");
	    c.setCursor (wordRow, wordColumn);
	    gottenWord = c.readString ();
	    wordRow++;
	    if (spellCheck (gottenWord) && moveCheck (board, gottenWord))
		score++;

	    if (wordRow > 24)
	    {
		c.print ("          ");
		wordRow = 5;
		wordColumn += 30;
	    }
	    c.setCursor (wordRow + 1, wordColumn);
	    c.print ("SCORE: " + score);
	}


    }


    public static void drawBoard (char[] [] board)
    {
	c.setFont (new Font ("Arial", Font.PLAIN, 50));
	c.drawString ("  Boggle", 0, 40);

	for (int row = 0 ; row < 4 ; row++) //prents every element of every row
	{
	    for (int column = 0 ; column < 4 ; column++)   //prints each element of roe i
	    {

		c.drawRoundRect (row * 60, column * 60 + 60, 50, 50, 10, 10);
		c.setFont (new Font ("Arial", Font.PLAIN, 50));
		c.drawString ((board [row] [column] + " "), (column * 60 + 5), (row * 60 + 105));
	    }
	}
    }


    public static char[] [] boardGen ()
    {
	int row, column;
	char board[] [] = {  //Creates array of spaces
		{' ', ' ', ' ', ' '},
		{' ', ' ', ' ', ' '},
		{' ', ' ', ' ', ' '},
		{' ', ' ', ' ', ' '}};
	for (int i = 0 ; i < 16 ; i++) //For each dieid
	{
	    do
	    {
		row = (int) (Math.random () * 4); //generates a random position
		column = (int) (Math.random () * 4);

	    }
	    while (board [row] [column] != ' '); //Runs until positrion is empty
	    board [row] [column] = letterRoll (i); //places the die in that position

	}
	return board;
    }


    public static char letterRoll (int dieId)
    {
	char dice[] [] = {  //2D array of 1D arrays of characters representing each letter on a given die
		{'A', 'A', 'C', 'I', 'O', 'T'},
		{'A', 'H', 'M', 'O', 'R', 'S'},
		{'E', 'G', 'K', 'L', 'U', 'Y'},
		{'A', 'B', 'I', 'L', 'T', 'Y'},
		{'A', 'C', 'D', 'E', 'M', 'P'},
		{'E', 'G', 'I', 'N', 'T', 'V'},
		{'G', 'I', 'L', 'R', 'U', 'W'},
		{'E', 'L', 'P', 'S', 'T', 'U'},
		{'D', 'E', 'N', 'O', 'S', 'W'},
		{'A', 'C', 'E', 'L', 'R', 'S'},
		{'A', 'B', 'J', 'M', 'O', 'Q'},
		{'E', 'E', 'F', 'H', 'I', 'Y'},
		{'E', 'H', 'I', 'N', 'P', 'S'},
		{'D', 'K', 'N', 'O', 'T', 'U'},
		{'A', 'D', 'E', 'N', 'V', 'Z'},
		{'B', 'I', 'F', 'O', 'R', 'X'}};

	return dice [dieId] [(int) (Math.random () * 6)];
    }


    public static boolean spellCheck (String word)
    {
	String filename = "corncob_caps.txt";
	//Uses the corncob caps dictionary file
	//http://www.mieliestronk.com/wordlist.html
	boolean goodSpelling;
	try
	{
	    return readFromFile (filename, word);
	}
	catch (IOException e)
	{

	    return false;
	}


    }


    //This code taken from alexmcchessers on Stack Overflow
    //http://stackoverflow.com/questions/9913/java-file-io-compendium

    public static boolean readFromFile (String filename, String word) throws /*FileNotFoundException,*/IOException
    {
	StringBuffer readBuffer = new StringBuffer ();
	BufferedReader fileReader = new BufferedReader (new FileReader (filename));
	String sRead = null;


	do
	{
	    if (word.equalsIgnoreCase (sRead))
	    {
		return true;
	    }
	    sRead = fileReader.readLine ();
	}
	while (sRead != null);
	return false;
    }


    public static boolean moveCheck (char[] [] board, String word)
    {
	boolean wordValidity;
	int x;

	//Fill location array with dummies
	int location[] = new int [16];
	for (int i = 0 ; i < 16 ; i++)
	    location [i] = -100;

	//Fill wordArray with letters from the word
	int wordArray[] = new int [word.length ()];
	for (int i = 0 ; i < word.length () ; i++)
	    wordArray [i] = word.charAt (i);

	//Run through word array
	for (int z = 0 ; z < wordArray.length ; z++)
	{
	    //Checking by row
	    for (x = 0 ; x < 4 ; x++)
	    {
		//And by column
		for (int y = 0 ; y < 4 ; y++)
		{
		    //Once the letter is found
		    if (wordArray [z] == board [x] [y])
		    {
			//Note its location
			location [z] = x * 4 + y;
			//And exit out of the two searching loops
			break;
		    }
		}
		if (location [z] != -100)
		    break;
	    }
	    //If you run through the whole board and the letter's not there
	    if (x == 4)
		//The word's invalid
		return false;
	}

	//Run through the location array
	for (int i = 1 ; i < word.length () ; i++)
	{
	    //Checking that the location value of each letter, excepting the first, is within the 3x3 square centered on the previous letter
	    if (location [i] == location [i - 1] - 5 || location [i] == location [i - 1] - 4 || location [i] == location [i - 1] - 3 || location [i] == location [i - 1] - 1 || location [i] == location [i - 1] + 1 || location [i] == location [i - 1] + 3 || location [i] == location [i - 1] + 4 || location [i] == location [i - 1] + 5)
	    {
	    }
	    //Or if it's a dummy
	    else if (location [i] == -100)
	    {
	    }
	    //If the locations don't match up
	    else
		//The word is invalid
		return false;
	}
	//If the locations are all fine
	return true;
    }
}



