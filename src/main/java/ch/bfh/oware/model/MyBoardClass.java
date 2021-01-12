
package ch.bfh.oware.model;

import java.util.ArrayList;
import java.util.List;

public class MyBoardClass implements Board {

	int seeds; 				// Amount of seends per pit at the start of the game 
	int size;				// Row size
	int game[];				// Array for the gameboard
	int scorePlayerOne;		// Score Player A
	int ScorePlayerTwo;		// Score Player B
	int turn;				// Variable to decide whos turn it is
	int arraySize;			// same as size*2 but got used to often it deserved its own variable

	public MyBoardClass(int size, int seeds) {
		/*
		 * Constructor -> takes the row size and amount of seeds as input
		 */
		this.size = size;
		this.seeds = seeds;
		arraySize = size*2;

		// Creates an array with the size of both rows and populates all pits with seeds
		game = new int[arraySize]; 				
		for (int i = 0; i < arraySize; i++) {
			game[i] = seeds;
		}

		// Turn one begins. If the turn is an uneven Number it's Player one's turn. Of its even it's player two's turn.
		turn = 1;
	}

	@Override
	public int getSize() {
		/*
		 * Return the row size of the game
		 */
		return size;
	}

	@Override
	public int getInitialSeeds() {
		/*
		 * Returns the amount of seeds that were selected to populate the pits at the start of the game
		 */
		return seeds;
	}

	@Override
	public int getScore(int row) {
		/*
		 * Returns the score
		 */
		if (row == 1) {
			return scorePlayerOne;
		}
		if (row == 2) {
			return ScorePlayerTwo;
		}
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean gameOver() {
		/*
		 * Adds all the seeds in the pits together. If it's <= 2, the game is over.
		 */
		int notEnoughSeeds = 0;
		for (int i = 0; i < arraySize; i++) {
			notEnoughSeeds += game[i];
		}
		if (notEnoughSeeds <= 2) {
			return true;
		}
		return false;
		//throw new UnsupportedOperationException();
	}

	@Override
	public int countSeeeds(int row, int pit) {
		/*
		 * Counts the amount of seeds for a given pit
		 */
		if (row == 1) {
			return game[pit-1];
		}
		if (row == 2) {
			return game[size+pit-1];
		}
		throw new UnsupportedOperationException();
	}

	@Override
	public void play(int row, int pit) {
		/*
		 * This method allows for a game move to be made
		 */
		int numberOfSeeds = countSeeeds(row, pit);

		if (row == 1) {
			pit = pit - 1; 					// adjust for array starts at 0
		}
		if (row == 2) {
			pit = pit+size-1; 				// adjust for array starts at 0
		}

		game[pit] = 0; 						// empty the pit that was played

		if (pit+1 == arraySize) { 			// if last pit was played, start at beginning of the array
			pit = -1;
		}

		// while loop to distibute all the seeds from the pit that was played
		while (numberOfSeeds != 0) {
			game[pit+1] += 1;
			
			/*
			 * If The pit after playing it now holds 2 seeds and if it belongs to
			 * the player whos turn it wasn't, remove the seeds and add 2 points
			 * the player whos turn it was
		 	 */
			if (pit+1 > size-1 && turn % 2 != 0 && game[pit+1] == 2) {
				game[pit+1] = 0;
				scorePlayerOne += 2;
			}
			if (pit+1 < size && turn % 2 == 0 && game[pit+1] == 2) {
				game[pit+1] = 0;
				ScorePlayerTwo += 2;
			}

			pit++;
			numberOfSeeds--;



			if (pit+1 == arraySize) { // if last pit was played start at  the beginning
				pit = -1;
			}
		}

		turn++;

		// If the next player doesnt have a turn to make then skip his turn
		if (hasMoves(1) == false) {
			if (turn % 2 != 0) {
				turn ++;
			}
		}
		if (hasMoves(2) == false) {
			if (turn % 2 == 0) {
				turn ++;
			}
		}

	}

	@Override
	public List<Integer> getMoves(int row) {
		/*
		 * returns a list of pits with seeds in them
		 */
		List<Integer> list = new ArrayList<Integer>();
		if (row == 1) {
			for (int i = 0; i < size; i++) {
				if (game[i] != 0) {
					list.add(i+1);
				}
			}
			return list;
		}
		if (row == 2) {
			for (int i = 0; i < size; i++) {
				if (game[i+size] != 0) {
					list.add(i+1);
				}
			}
			return list;
		}
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean hasMoves(int row) {
		/*
		 * Checks if a player has a pit with a seed in it
		 */
		if (row == 1) {
			for (int i = 0; i < size; i++) {
				if (game[i] != 0) {
					return true;
				}
				
			}
			return false;
		}
		if (row == 2) {
			for (int i = size; i < size*2; i++) {
				if (game[i] != 0) {
					return true;
				}
			}
			return false;
			
		}
		
		throw new UnsupportedOperationException();
	}

	@Override
	public String toString() {
		/*
		 * Displays the game board and score for the console app
		 */
		List<String> row1 = new ArrayList<String>(size-1);
		List<String> row2 = new ArrayList<String>(size-1);
		
        for (int i = 0; i < size; i++) {
			row1.add(String.valueOf(game[i]));
			row2.add(String.valueOf(game[i+size]));
		}
		

		String rowOne = String.join("| ", row1);
		String rowTwo = String.join("| ", row2);
		return "Player A: " + "| " + rowOne + "|\n" 
			+ "Player B: " + "| " + rowTwo + "|\n" 
			+ "Score " + scorePlayerOne + "/" + ScorePlayerTwo;
	}

	public int getCurrentPlayer() {
		/*
		 * returns the player who's turn it currently is
		 */
		if (turn % 2 != 0) {
			return 1;
		}
		if (turn % 2 == 0) {
			return 2;
		}

		throw new UnsupportedOperationException();		
	}
}