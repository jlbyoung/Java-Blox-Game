import java.util.*;

public class Blox 
{
	/* the next several lines create constants local to THIS java file
	 *		with the same names/values as those present in the BloxLibrary
	 *		file, to make them easier to refer to (so you can do map[Y][X]
	 *		instead of map[BloxLibrary.Y][BloxLibrary.X], for example).
	 */
	public static final int X = BloxLibrary.X, Y = BloxLibrary.Y;
	public static final char BLOCK = BloxLibrary.BLOCK, HOLE = BloxLibrary.HOLE,
			EMPTY = BloxLibrary.EMPTY;
	
	// constants for user input and map output. Make more as needed!
	public static final char PLAYER = 'P', BORDER = 'X', EASY = 'E',
			MEDIUM = 'M', HARD = 'H', QUIT = 'Q';
	
	/* Controls the main logic of this program, calling most of the other
	 *		methods directly to accomplish subtasks of the overall program.
	 *
	 * @param  args  Command-line arguments. Unused by this program.
	 */
	public static void main(String[] args)
	{
		char difficulty;
		boolean endProgram = false; //will always be false since the program will always repeat unless the user quits
		int[] player = new int[2];
		
		displayInstructions();
		
		while(endProgram == false)
		{
			difficulty = getDifficulty();
			if(difficulty == EASY)
			{
				//Creates 2D array of dimension height 6 and width 7
				char[][] map = new char[6][7];
				//Since this is easy mode, passes 1 combination of blox/hole and width of 7 and height of 6
				map = BloxLibrary.randomizeMap(1, 7, 6);
				//Places the player on the map
				player = BloxLibrary.findAvailablePosition(map);
				playGame(map, player, 1);
			}
			else if(difficulty == MEDIUM)
			{
				//2D array of height 7 and width 10
				char[][]map = new char[7][10];
				//Since this is medium mode, passes 3 combination of blox/hole and width of 10 and height of 7
				map = BloxLibrary.randomizeMap(3, 10, 7);
				player = BloxLibrary.findAvailablePosition(map);
				playGame(map, player, 3);
			}
			else if(difficulty == HARD)
			{
				//2D array of height 10 and width 12
				char[][]map = new char[10][12];
				//Since this is hard mode, passes 10 combination of blox/hole and width of 12 and height of 10
				map = BloxLibrary.randomizeMap(10, 12, 10);
				player = BloxLibrary.findAvailablePosition(map);
				playGame(map, player, 10);
			}
		}
	}
	
	/* Displays instructions on how to play this game.
	 */
	public static void displayInstructions() 
	{
		System.out.println("You control the player (P) and must push the blox (*) into any hole(O)! But you are restricted by the border X.");
		System.out.println("You win when every block has been pushed into a hole. However, you will lose if you walk into a hole!");
		System.out.println("Difficulties are as follows : " +
						"\n\t Easy = 5x4 grid ; 1 * and 1 O" +
						"\n\t Medium = 8x5 grid ; 3 * and 3 O" +
						"\n\t Hard = 10x8 grid ; 10 * and 10 O");
	}
	
	/* Prints the 2D character map passed in as a parameter, including the player
	 *		whose position is specified by the int array parameter. The player
	 *		position array should be in the same format as that returned by the
	 *		findAvailablePosition method provided in the BloxLibrary file.
	 *
	 * @param  map  The 2D character array map of the current game.
	 * @param  player  An array containing the x and y coordinates of the player.
	 */
	public static void printMap(char[][] map, int[] player) 
	{
		//Places P as the player onto the map 
		if(player[Y] < map[0].length && player[X] < map[1].length)
		{
			map[player[Y]][player[X]] = PLAYER;
			for(int row = 0; row < map.length; ++row)
			{
				for(int col = 0; col < map[row].length; ++col)
				{
					//If the coordinates row and col are along the margin, prints out an X to denote the border
					if(row == 0 || row == map.length - 1|| col == 0 || col == map[row].length - 1)
					{
						map[row][col] = 'X';
					}
					System.out.print(map[row][col]);
				}
				System.out.println();
			}
		}
	}
	
	/* Prompts the user to select a difficulty from the available options.
	 *		Continues to prompt while the selection made by the user does
	 *		not correspond to any of the available options, ensuring that
	 *		a valid difficulty (or quit) is returned.
	 *
	 * @return  The validated difficulty selected by the user.
	 */
	public static char getDifficulty()
	{
		String input = "";
		char choice;
		boolean valid = false;
		
		do
		{
			System.out.print("Please select a difficulty - [E]ASY, [M]EDIUM, [H]ARD, or [Q]UIT: ");
			Scanner scan = new Scanner(System.in);
			input = scan.nextLine();
			input = input.toUpperCase();
			choice = input.charAt(0);
			switch (choice)
			{
				case 'E' :
				{
					valid = true;
					return choice;
				}
				case 'M' :
				{
					valid = true;
					return choice;
				}
				case 'H' :
				{
					valid = true;
					return choice;
				}
				case 'Q':
				{
					System.exit(0);
				}
				default:
				{
					break;
				}
			}
		} while (valid == false);
		
		return choice;
	}
	
	/* }This method indicates if the coordinate specified by the integer parameters
	 *		is a valid location within the 2D character array map specified by the
	 *		remaining parameter.
	 *
	 * @param  map  The 2D character array map of the current game.
	 * @param  x  The horizontal coordinate for the location being tested.
	 * @param  y  The vertical coordinate for the location being tested.
	 * @return  True if the coordinate is within the range of the map,
	 *		false otherwise.
	 */
	public static boolean isOnMap(char[][] map, int x, int y)
	{
		boolean valid;
		if(x > map[1].length - 1 - BloxLibrary.MARGIN)
		{
			valid = false;
		}
		else if(y > map[0].length - 1 - BloxLibrary.MARGIN)
		{
			valid = false;
		}
		else
		{
			valid = true;
		}
		return valid;
	}
	
	/* This method controls the core logic to control the play of a single map.
	 *
	 * @param  map  The 2D character array map of the current game.
	 * @param  player The array containing the location of the player
	 * @param  blox The number of blox in the game
	 */
	public static void playGame(char[][] map, int[] player, int blox)
	{
		char move;
		int status;
		boolean gameStatus = true;

		while(blox > 0)
		{
			while(gameStatus == true)
			{
				printMap(map, player);
				move = getMove();
				status = movePlayer(map, player, move);
				gameStatus = updateMap(map, player[X], player[Y], status, move);
				blox = updateBlox(status, blox, gameStatus);
				System.out.println("There are " + blox + " remaining");
				if(blox == 0)
				{
					gameStatus = false;
				}
			}
		}
		
	}
	
	/* Prompts the user to select a move from the available options.
	 *		Continues to prompt while the selection made by the user does
	 *		not correspond to any of the available options, ensuring that
	 *		a valid move(or quit) is returned.
	 *
	 * @return  The validated move selected by the user.
	 */
	
	public static char getMove()
	{
		String input = "";
		char choice;
		boolean valid = false;
		
		do
		{
			System.out.print("Please enter a move - UP (W), DOWN (S), LEFT (A), RIGHT(D), or QUIT (Q): ");
			Scanner scan = new Scanner(System.in);
			input = scan.nextLine();
			input = input.toUpperCase();
			choice = input.charAt(0);
			switch (choice)
			{
				case 'W' :
				{
					valid = true;
					return choice;
				}
				case 'S' :
				{
					valid = true;
					return choice;
				}
				case 'A' :
				{
					valid = true;
					return choice;
				}
				case 'D':
				{
					valid = true;
					return choice;
				}
				case 'Q':
				{
					System.exit(0);
				}
				default:
				{
					valid = false;
					break;
				}
			}
		} while (valid == false);
		
		return choice;
	}
	
	/* This method returns an integer constant corresponding to the status of the move made
	 * 	by the player. Three possible options : 1 - move blocked, 2 - move not blocked, 
	 *	3 - move not blocked and pushes a block int o a hole
	 *	The return value indicates which of these is the case. 
	 *
	 * @param  map  The 2D character array map of the current game.
	 * @param  x  The horizontal coordinate for the location being tested.
	 * @param  y  The vertical coordinate for the location being tested.
	 * @return  True if the coordinate is within the range of the map,
	 *		false otherwise.
	 */
	public static int movePlayer(char[][] map, int[] player, char move)
	{
		/*
		status will determine which outcome the player has
		1 -  the move is blocked
		2 -  the move is not blocked
		3 -  the move is not blocked AND pushes a block into a hole. 
		*/
		int status = 0;
		boolean valid = false;
		
		//If the player wishes to move up and presses W
		if(move == 'W')
		{
			//The player's y coordinate is set to the space in front
			player[Y] = player[Y] - 1;
			//Checks to see if the coordinate is on the map or not
			valid = isOnMap(map, player[X], player[Y]);
			if(valid)
			{
				/*Checks the status of the move, 1 - move blocked, 2 - move not blocked, 
				3 - move not blocked and pushes a block int o a hole */
				status = checkConditions(map, player[X], player[Y], move);
			}
			else
			{
				//If the move is not valid, set the player's original position again
				player[Y] = player[Y] + 1;
			}
		}
		else if(move == 'S')
		{
			player[Y] = player[Y] + 1;
			valid = isOnMap(map, player[X], player[Y]);
			if(valid)
			{
				status = checkConditions(map, player[X], player[Y], move);
			}
			else
			{
				player[Y] = player[Y] - 1;
			}
		}
		else if(move == 'D')
		{
			player[X] = player[X] + 1;
			valid = isOnMap(map, player[X], player[Y]);
			if(valid)
			{
				status = checkConditions(map, player[X], player[Y], move);
			}
			else
			{
				player[Y] = player[Y] - 1;
			}
		}
		else if(move == 'A')
		{
			player[X] = player[X] - 1;
			valid = isOnMap(map, player[X], player[Y]);
			if(valid)
			{
				status = checkConditions(map, player[X], player[Y], move);
			}
			else
			{
				player[Y] = player[Y] + 1;
			}
		}
		
		return status;
	}
	
	/* This method returns an integer constant corresponding to the status of the move made
	 * 	by the player. Three possible options : 1 - move blocked, 2 - move not blocked, 
	 *	3 - move not blocked and pushes a block int o a hole
	 *	The return value indicates which of these is the case. The method also determines which
	 *	which of them is the case
	 *
	 * @param  map  The 2D character array map of the current game.
	 * @param  x  The horizontal coordinate for the location being tested.
	 * @param  y  The vertical coordinate for the location being tested.
	 * @param move The direction the player wishes to move
	 * @return  status The status of the move, 1 - move blocked, 2 - move not blocked, 
	 *	3 - move not blocked and pushes a block int o a hole
	 */
	public static int checkConditions(char[][] map, int x, int y, char move)
	{
		int status = 1;
		//If user presses W and there's a block in front
		if(map[y][x] == BLOCK && move == 'W')
		{
			status = 2;
			//If there's a hole in front of the block
			if(map[y-1][x] == HOLE)
			{
				status = 3;
			}
			//If there's a block in front of the block
			else if (map[y-1][x] == BLOCK)
			{
				status = 1;
			}
		}
		//If the user presses S and there's a block in front
		else if(map[y][x] == BLOCK && move == 'S')
		{
			status = 2;
			if(map[y+1][x] == HOLE)
			{
				status = 3;
			}
			else if (map[y+1][x] == BLOCK)
			{
				status = 1;
			}
		}
		//If the user presses D and there's a block in front
		else if(map[y][x] == BLOCK && move == 'D')
		{
			status = 2;
			if(map[y][x+1] == HOLE)
			{
				status = 3;
			}
			else if (map[y][x+1] == BLOCK)
			{
				status = 1;
			}
		}
		//If the user presses A and there's a block in front
		else if(map[y][x] == BLOCK && move == 'A')
		{
			status = 2;
			if(map[y][x-1] == HOLE)
			{
				status = 3;
			}
			else if (map[y][x-1] == BLOCK)
			{
				status = 1;
			}
		}
		//If the space is empty in front of the player
		else if(map[y][x] == EMPTY)
		{
			status = 2;
		}
		//If there is a border in front of the player or there is a hole 
		else if(map[y][x] == 'X' || map[y][x] == HOLE)
		{
			status = 1;
		}
		//Any other situation should allow the player to move
		else
		{
			status = 2;
		}
		
		return status;
	}
	
	/* This method returns a boolean to determine if the game is over, otherwise it updates 
	 * 	the map continuously.
	 *
	 * @param  map  The 2D character array map of the current game.
	 * @param  x  The horizontal coordinate for the location being tested.
	 * @param  y  The vertical coordinate for the location being tested.
	 * @param status The status of the move, 1 - move blocked, 2 - move not blocked, 
	 * 	3 - move not blocked and pushes a block int o a hole
	 * @param move The direction the player wants to move
	 * @return  endGame If the game is over by blox being = 0 or the player falls into a hole.
	 */
	public static boolean updateMap(char[][] map, int x, int y, int status, char move)
	{
		boolean endGame = true;
		//The block goes into a hole following the player's direction of movement
		if(status == 3)
		{
			if(move == 'W')
			{
				//Sets the new position to be the player
				map[y][x] = PLAYER;
				//The space in front will be empty
				map[y+1][x] = EMPTY;
				//The space of the player's original position is set to empty
				map[y-1][x] = EMPTY;
				System.out.println("You got a block in!");
			}
			else if(move == 'S')
			{
				map[y][x] = PLAYER;
				map[y-1][x] = EMPTY;
				map[y+1][x] = EMPTY;
				System.out.println("You got a block in!");
			}
			else if(move == 'D')
			{
				map[y][x] = PLAYER;
				map[y][x+1] = EMPTY;
				map[y][x-1] = EMPTY;
				System.out.println("You got a block in!");
			}
			else if(move == 'A')
			{
				map[y][x] = PLAYER;
				map[y][x-1] = EMPTY;
				map[y][x+1] = EMPTY;
				System.out.println("You got a block in!");
			}
		}
		//If the player moves into an empty space or pushes a block
		else if(status == 2)
		{
			if(move == 'W')
			{
				//If there's a block in front of the player
				if(map[y][x] == BLOCK)
				{
					//The player is set to the block's coorindates
					map[y][x] = PLAYER;
					//The space in front of the block is set to a block
					map[y-1][x] = BLOCK;
					//The original space of the player is set to empty
					map[y+1][x] = EMPTY;
				}
				//If there's an empty space in front of the player
				else
				{
					//The player is set to the empty space's coordinates
					map[y][x] = PLAYER;
					//The player's original coordinates is set to empty
					map[y+1][x] = EMPTY;
				}
			}
			if(move == 'S')
			{
				if(map[y][x] == BLOCK)
				{
					map[y][x] = PLAYER;
					map[y+1][x] = BLOCK;
					map[y-1][x] = EMPTY;
				}
				else
				{
					map[y][x] = PLAYER;
					map[y-1][x] = EMPTY;
				}
			}
			if(move == 'D')
			{
				if(map[y][x] == BLOCK)
				{
					map[y][x] = PLAYER;
					map[y][x+1] = BLOCK;
					map[y][x-1] = EMPTY;
				}
				else
				{
					map[y][x] = PLAYER;
					map[y][x-1] = EMPTY;
				}
			}
			if(move == 'A')
			{
				if(map[y][x] == BLOCK)
				{
					map[y][x] = PLAYER;
					map[y][x-1] = BLOCK;
					map[y][x+1] = EMPTY;
				}
				else
				{
					map[y][x] = PLAYER;
					map[y][x+1] = EMPTY;
				}
			}
		}
		//If the player goes into a hole or hits the border
		else if(status == 1)
		{
			//The player loses when going into a hole
			if(map[y][x] == HOLE)
			{
				System.out.println("You died!");
				endGame = false;
			}
			//The player hits the border
			else
			{
				System.out.println("You cannot go that way");
			}
		}
		return endGame;
	}
	
	/* This method returns an integer to keep track of how many blox there are
	 *
	 * @param status The status of the move
	 * @param blox The amount of blox remaining in the game
	 * @param gameStatus The status of the game. True if there has not been any losing conditions, False when the player falls into a hole
	 * @return blox 
	*/
	public static int updateBlox(int status, int blox, boolean gameStatus)
	{
		if(status == 3)
		{
			blox = blox - 1;
		}
		if(gameStatus == false)
		{
			blox = 0;
		}
		return blox;
	}

}