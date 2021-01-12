
package ch.bfh.oware;

import java.util.Random;
import java.util.Scanner;

import ch.bfh.oware.model.MyBoardClass;


public class ConsoleApp {

	public static void main(String[] args) {
		/*
		 * Main function to start the game
		 */
		
		 // Scan for input so the user can choose the game and pit size 
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter the row size: ");
		int size = scanner.nextInt();
		System.out.print("Enter the amount of seeds: ");
		int seeds = scanner.nextInt();
		MyBoardClass temp = new MyBoardClass(size, seeds);

		// Switch case for player vs player, player vs bot and bot vs bot and exit;
		System.out.println("[1] Player vs. Player \n[2] Player vs. Bot \n[3] Bot vs. Bot \n[4] Exit");
		int gametype = scanner.nextInt();

		switch (gametype) {
			case 1: // Player vs. Player
					/*
		 			 * Scan for input to make move untill the game is over
		 			 */
				System.out.println("You chose Player vs. Player");
				while (temp.gameOver() != true) {
					System.out.println(temp.toString());
					int player = temp.getCurrentPlayer();
					if (player == 1) {
						System.out.println("\nPlayer A: Moves = " + temp.getMoves(player));
					}
					if (player == 2) {
						System.out.println("\nPlayer B: Moves = " + temp.getMoves(player));
					}
					scanner = new Scanner(System.in);
					System.out.print("Enter move => ");
					int move = scanner.nextInt();
					temp.play(temp.getCurrentPlayer(), move);
				}

				// Display the winner at the end of the game
				if (temp.getScore(1) > temp.getScore(2)) {
					System.out.println("Player A won with a Score of " + temp.getScore(1) + " to " + temp.getScore(2));
				}
				if (temp.getScore(2) > temp.getScore(1)) {
					System.out.println("Player B won with a Score of " + temp.getScore(2) + " to " + temp.getScore(1));
				}
				if (temp.getScore(2) == temp.getScore(1)) {
					System.out.println("Draw with a final score of " + temp.getScore(2) + " to " + temp.getScore(1));
				}
				break;

			case 2: // Player vs. Bot

				// Player is same as in Player vs Player
				System.out.println("You chose Player vs. Bot");
				while (temp.gameOver() != true) {
					int player = temp.getCurrentPlayer();
					if (player == 1) {
						System.out.println(temp.toString());
						System.out.println("\nPlayer A: Moves = " + temp.getMoves(player));
						scanner = new Scanner(System.in);
						System.out.print("Enter move => ");
						int move = scanner.nextInt();
						temp.play(temp.getCurrentPlayer(), move);
					}

					// Bot plays a random pit with seeds in it
					if (player == 2) {
						Random random = new Random();
						int rand = 0;
						while (true) {
							rand = random.nextInt(size+1);
							if (rand != 0 && temp.countSeeeds(2, rand)  != 0) {
								temp.play(temp.getCurrentPlayer(), rand);
								System.out.println("\nBot played pit " + rand + "\n");
								break;
							}
						}

					}
				}

				// Display the winner at the end of the game
				if (temp.getScore(1) > temp.getScore(2)) {
					System.out.println("Player won with a Score of " + temp.getScore(1) + " to " + temp.getScore(2));
				}
				if (temp.getScore(2) > temp.getScore(1)) {
					System.out.println("Bot won with a Score of " + temp.getScore(2) + " to " + temp.getScore(1));
				}
				if (temp.getScore(2) == temp.getScore(1)) {
					System.out.println("Draw with a final score of " + temp.getScore(2) + " to " + temp.getScore(1));
				}
				break;

			case 3: // Bot vs. Bot
				/*
				 * Both bots play a random valid pit until the game is over
				 */
				System.out.println("You chose Bot vs. Bot");
				while (temp.gameOver() != true) {
					int player = temp.getCurrentPlayer();
					if (player == 1) {
						Random random = new Random();
						int rand = 0;
						while (true) {
							rand = random.nextInt(size+1);
							if (rand != 0 && temp.countSeeeds(1, rand)  != 0) {
								temp.play(player, rand);
								System.out.println("\nBot A played pit " + rand + "\n");

								System.out.println(temp.toString());
								break;
							}
						}
					}
					if (player == 2) {
						Random random = new Random();
						int rand = 0;
						while (true) {
							rand = random.nextInt(size+1);
							if (rand != 0 && temp.countSeeeds(2, rand)  != 0) {
								temp.play(player, rand);
								System.out.println("\nBot B played pit " + rand + "\n");
								System.out.println(temp.toString());
								break;
							}
						}
					}
				}
				// Display the winner at the end of the game
				if (temp.getScore(1) > temp.getScore(2)) {
						System.out.println("Bot A won with a Score of " + temp.getScore(1) + " to " + temp.getScore(2));
					}
				if (temp.getScore(2) > temp.getScore(1)) {
						System.out.println("Bot B won with a Score of " + temp.getScore(2) + " to " + temp.getScore(1));
					}
				if (temp.getScore(2) == temp.getScore(1)) {
						System.out.println("Draw with a final score of " + temp.getScore(2) + " to " + temp.getScore(1));
					}
				break;

			case 4: // Exits the game 
				break;
			
			default:
				System.out.println("Not a valid input. You must choose 1, 2 or 3");
				main(args);
		}
		scanner.close();
	}
}