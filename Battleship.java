/*Battleship.java: game where the player has to guess the location of the hidden ship on the grid
	
Name: Nicole Issagholian

Date: 11/7/2021

Side notes for grader: 
	Based on the instructions given in class, the dimensions can be a range of 4x4 to a max of 10x10 based on the what the user inputs.
	Also, based on the instructions written on the assignment sheet, the user is the only one playing the game until the ship has been destroyed.
	The code below reflects my understanding, as mentioned above, of the instructions given in class as well as those on the assignment sheet.
*/

import java.util.Scanner;
import java.util.Random;

public class Battleship
{
	public static void main (String[] args)
	{
		//creates a scanner object
		Scanner data = new Scanner(System.in);

		System.out.println("Welcome to the Battleship game");

		//asks for dimensions of board and stores user input in constant variable 
		System.out.print("Enter dimensions for board (range of 4 to 10): ");
		final int SIZE=data.nextInt();

		//creates multidimensional array of the dimensions  
		String[][] board = new String[SIZE][SIZE];
		
		//creates array of letters from A through J for grid 
		char[] letters = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'};

		//sets size of battleship to 2 if the grid has dimensions less than 5 and sets the size to 4 if the grid has dimensions greater than 5
		int battleshipSize=0;
		if (SIZE>5)
		{
			battleshipSize=4;
		}
		else 
		{
			battleshipSize=2;
		}

		//creates array for placement of battleship
		String[] battleshipPlace = new String[battleshipSize];

		//calls setBoard method and playerGuesses method
		setBoard(board, SIZE, letters, battleshipPlace, battleshipSize);
		playerGuesses(board, SIZE, letters, battleshipSize, battleshipPlace);
	}

	//method that sets the board
	public static void setBoard(String[][] board, final int SIZE, char[] letters, String[] battleshipPlace, int battleshipSize)
	{
		//creates random coordinates for placement of ship 
		char letterPlace = (char)(Math.random()*SIZE + 65);
		int numPlace = (int)(Math.random() * SIZE + 1);

		//variables that determine if orientation is vertical or horizontal
		boolean goRight = ((letterPlace-65) + battleshipSize) < SIZE;
		boolean goDown = (numPlace + battleshipSize) < SIZE;

		//variable that randomizes orientation of ship
		int orientation = (int)(Math.random()*2) + 1;

		//variable that holds coordinate (e.g. A1)
		String coordinate = "" + letterPlace + numPlace; 

		battleshipPlace[0] = coordinate;

		//creates coordinate values for ship depending on its orientation
		if (orientation == 1)
		{
			for (int i=1; i<battleshipSize; i++)
			{
				if (goDown == true)
				{
					battleshipPlace[i] = "" + letterPlace + (++numPlace);
				}
				else 
				{
					battleshipPlace[i] = "" + letterPlace + (--numPlace);
				}
			}	
		}	
		else 
		{
			for (int i=1; i<battleshipSize; i++)
			{
				if (goRight == true)
				{
					battleshipPlace[i] = "" + (++letterPlace) + numPlace;
				}	
				else 
				{
					battleshipPlace[i] = "" + (--letterPlace) + numPlace;
				}	
			}	
		}

		for (int repetitions=1; repetitions<=3; repetitions++)
		{
			System.out.print(" ");
		}

		//writes the letters going across on top of grid based on dimensions of grid		
		for (int i=0; i<(board.length); i++)
		{
			for (int j=0; j<((board[i].length)/SIZE); j++)
			{
				System.out.print("" + letters[i]);
				for (int repetitions=1; repetitions<4; repetitions++)
				{
					System.out.print(" ");
				}	
			}		
		}
		System.out.println();
		
		//creates pattern of grid with numbers along side of grid based on dimensions of grid
		for (int rows=0; rows<board.length; rows++)
		{
			for (int cols=0; cols<board[rows].length; cols++)
			{
				board[rows][cols] = " ";
				if (cols == 0)
				{
					System.out.print(" +---+");
					for (int row=0; row<SIZE-1; row++)
					{
						System.out.print("---+");
					}				
					System.out.println();
					System.out.print(rows + "|");

					for (int row=0; row<SIZE; row++)
					{
						System.out.print("   |");
					}				
					System.out.println();
				}
			}			
		}
		System.out.print(" +---+");
		for (int row=0; row<SIZE-1; row++)
		{
			System.out.print("---+");
		}
	}	

	//method that takes in the player's guesses, determines its validity, and prints the updated board based on whether the input targets the ship or not
	public static void playerGuesses(String[][] board, final int SIZE, char[] letters, int battleshipSize, String[] battleshipPlace)
	{
		System.out.println("\nIt is time to deploy your ships:");

		//creates a scaner object
		Scanner input = new Scanner(System.in);

		//variable that holds number of player's total guesses
		int totalGuesses=0;

		//variable that holds number of player's correct guesses
		int correctGuesses=0;

		//loop that checks for coordinates and marks off the guesses on the grid with a # where they hit the spot but they missed and marks off the guesses with a X where they hit the spot and they hit the ship
		while (correctGuesses<battleshipSize)
		{
			//asks user to input coordinate to guess and takes the input in as a string
			System.out.print("Enter coordinate to target (e.g. A1): ");
			String playerGuess=input.next();

			//creates a char variable storing the letter part of the string the user inputs
			char letter = playerGuess.charAt(0);
			letter = Character.toUpperCase(letter);

			//creates an int variable storing the number part of the string the user inputs
			int number = Character.getNumericValue(playerGuess.charAt(1));

			//series of if and else if statements that check if the user's input is valid or not; if valid, it will mark off the spot with a # if it has not hit the ship and marks it off with a X if it has hit the ship; if not valid, the user will be told it is an invalid input
			if ((letter>('A' + SIZE) || letter<'A') || (number<0 || number>=SIZE))
			{
				System.out.println("You are not allowed to put ships outside of the " + SIZE + " by " + SIZE + " grid.");
			}	
			else if ((letter>='A' && letter<=('A' + SIZE)) && (number>=0 && number<SIZE) && (board[number][letter-65].equals(" "))) 
			{
				boolean correct = false;

				for (int i=0; i<battleshipPlace.length; i++)
				{	
					if (playerGuess.equals(battleshipPlace[i]))
					{
						correct = true;
						board[number][letter-65]="X";
						correctGuesses++;
						System.out.println(correctGuesses);
					}	
				}	
				if (correct == false)
				{
					board[number][letter-65]="#";
				}	

				totalGuesses++;
			}
			else if ((letter>='A' && letter<=('A' + SIZE)) && (number>=0 && number<SIZE) && (board[number][letter-65].equals("#")))
			{
				System.out.println("You are not allowed to put two or more ships on the same spot.");
			}
			//calls the printBoard method after each guess, showing updated board with new marks for guesses made	      
			printBoard(board, SIZE, letters);
			
		}
		//congratulates the user when they have sunk the ship and prints the total number of guesses it took them to win
		System.out.println("Congrats! You won! You sunk the ship!");
		System.out.println("Total number of guesses: " + totalGuesses);

	}

	//method that prints the updated board as the player guesses each spot
	public static void printBoard(String[][] board, final int SIZE, char[] letters)
	{
		for (int repetitions=1; repetitions<=3; repetitions++)
		{
			System.out.print(" ");
		}
	
		//writes the letters going across on top of grid based on dimensions of grid 	
		for (int i=0; i<(board.length); i++)
		{
			for (int j=0; j<((board[i].length)/SIZE); j++)
			{
				System.out.print("" + letters[i]);
				for (int repetitions=1; repetitions<4; repetitions++)
				{
					System.out.print(" ");
				}	
			}		
		}
		System.out.println();
		
		System.out.print(" +---+");
		for (int row=0; row<SIZE-1; row++)
		{
			System.out.print("---+");
		}
		System.out.println();

		//creates pattern of grid with numbers along side of grid based on dimensions of grid
		for (int rows=0; rows<board.length; rows++)
		{
				System.out.print(rows + "|");				
			for (int cols=0; cols<board[rows].length; cols++)
			{
						if (board[rows][cols] == null)
						{
							System.out.print("|");
						}	
						else
						{
							System.out.print(" " + board[rows][cols] + " |");
						}				
					
			}			
			System.out.println();
		
		System.out.print(" +---+");
		for (int row=0; row<SIZE-1; row++)
		{
			System.out.print("---+");
		}
		System.out.println();
		}
	}
}
