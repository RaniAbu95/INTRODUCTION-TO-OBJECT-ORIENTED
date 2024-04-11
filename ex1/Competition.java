import java.util.Scanner;

/**
 * The Competition class represents a Nim competition between two players,
 * consisting of a given number of rounds. It also keeps track of the number of
 * victories of each player.
 */
public class Competition {
	private Player firstPlayer;
	private Player secondPlayer;
	private int player1Victures;
	private int player2Victures;
	private Boolean showMessage;
	private static final int PLAYER1_ID = 1;



	/**
	 * receives two Player objects, representing the two competing opponents,
	 * and a flag determining whether messages should be displayed.
	 * 
	 * @param player1
	 *            The first player in the competition.
	 * @param player2
	 *            The second player in the competition.
	 * @param displayMessage
	 *            A boolean value that indicates whether messages should be
	 *            displayed.
	 */
	public Competition(Player player1, Player player2, boolean displayMessage){
		firstPlayer = player1;
		secondPlayer = player2;
		showMessage = displayMessage;
		player1Victures = 0;
		player2Victures = 0;

	}

	/**
	 * @param playerPosition
	 *            An integer which is 1 or 2, corresponding to the first or the
	 *            second player in the competition.
	 * @return An integer which is the number of victories of a player.
	 */
	public int getPlayerScore(int playerPosition) {
		if (firstPlayer.getPlayerId() == playerPosition)
			return player1Victures;
		else
			return player2Victures;
	}

	/**
	 * This method manage a game between two players that none of them is a
	 * human player.
	 * 
	 * @param board
	 *            A Board object that represent a board in the Nim game in a
	 *            certain situation.
	 * @param currPlayer
	 *            A Player object, which is the player that have to make the
	 *            current move.
	 */
	private void noMessagesGame(Board board, Player currPlayer) {
		while (board.getNumberOfUnmarkedSticks() != 0) {
			Move playerMove = currPlayer.produceMove(board);
			while (board.markStickSequence(playerMove) != 0) {
				playerMove = currPlayer.produceMove(board);
			}

			if (currPlayer.getPlayerId() == PLAYER1_ID){
				currPlayer = secondPlayer;
			}


			else {
				currPlayer = firstPlayer;
			}
		}
		if (currPlayer.getPlayerId() == PLAYER1_ID) {
			player1Victures++;
			currPlayer = firstPlayer;
		} else {
			player2Victures++;
			currPlayer = firstPlayer;
		}


	}

	/**
	 * This method manage a game between two players that one of them is a 
	 * human player.
	 * 
	 * @param board
	 *            A Board object that represent a board in the Nim game in a
	 *            certain situation.
	 * @param currPlayer
	 *            A Player object, which is the player that have to make the
	 *            current move.
	 */
	private void GameWithMessages(Board board, Player currPlayer) {
		while (board.getNumberOfUnmarkedSticks() > 0) {
			System.out.println("Player " + currPlayer.getPlayerId()
					+ ", it is now your turn!");
			Move playerMove = currPlayer.produceMove(board);
			if(playerMove == null){
				return;
			}
			while ((board.markStickSequence(playerMove) != 0) && (currPlayer.getPlayerType() == Player.HUMAN) ) {
				System.out.println("Invalid move.  Enter another:");
				playerMove = currPlayer.produceMove(board);
			}
			System.out.println("Player " + currPlayer.getPlayerId()
					+ " made the move: " + playerMove.toString());

			if (currPlayer.getPlayerId() == 1) {
				currPlayer = secondPlayer;
			}

			else {
				currPlayer = firstPlayer;
			}

		}
		if (currPlayer.getPlayerId() == PLAYER1_ID) {
			System.out.println("Player " + firstPlayer.getPlayerId() + " won!");
			player1Victures++;
		}
		else{
			System.out.println("Player " + secondPlayer.getPlayerId() + " won!");
			player2Victures++;
		}
		currPlayer = firstPlayer;

	}




	/**
	 * This method runs a competition between two players based on the Nim game
	 * rules, for each round of a competition the method declares who is the
	 * winner of the round, and at the end of the competition it declares who 
	 * is the winner of the competition.
	 * 
	 * @param numberOfRounds
	 *            An Integer which represents the rounds number of a
	 *            competition.
	 */
	public void playMultipleRounds(int numberOfRounds) {
		String player1Type = firstPlayer.getTypeName();
		String player2Type = secondPlayer.getTypeName();
		Player currPlayer = firstPlayer;
		System.out.println("Starting a Nim competition of " + numberOfRounds
				+ " rounds between a " + player1Type + " player and a "
				+ player2Type + " player.");
		for (int i = 1; i <= numberOfRounds; i++) {
			Board board = new Board();
			if (showMessage) {
				System.out.println("Welcome to the sticks game!");
				GameWithMessages(board, currPlayer);
			}
			else {
				noMessagesGame(board, currPlayer);
			}

		}
		System.out.println("The results are " + player1Victures + ":"
				+ player2Victures);

	}

	/*
	 * Returns the integer representing the type of the player; returns -1 on
	 * bad input.
	 */
	private static int parsePlayerType(String[] args, int index) {
		try {
			return Integer.parseInt(args[index]);
		} catch (Exception E) {
			return -1;
		}
	}

	/*
	 * Returns the integer representing the type of player 2; returns -1 on bad
	 * input.
	 */
	private static int parseNumberOfGames(String[] args) {
		try {
			return Integer.parseInt(args[2]);
		} catch (Exception E) {
			return -1;
		}
	}

	/**
	 * The method runs a Nim competition between two players according to the
	 * three user-specified arguments. (1) The type of the first player, which
	 * is a positive integer between 1 and 4: 1 for a Random computer player, 2
	 * for a Heuristic computer player, 3 for a Smart computer player and 4 for
	 * a human player. (2) The type of the second player, which is a positive
	 * integer between 1 and 4. (3) The number of rounds to be played in the
	 * competition.
	 * 
	 * @param args
	 *            an array of string representations of the three input
	 *            arguments, as detailed above.
	 */
	public static void main(String[] args) {
		int p1Type = parsePlayerType(args, 0);
		int p2Type = parsePlayerType(args, 1);
		int numGames = parseNumberOfGames(args);
		Scanner scanner = new Scanner(System.in);
		Player player1 = new Player(p1Type, 1, scanner);
		Player player2 = new Player(p2Type, 2, scanner);
		boolean HumanMessage = false;
		if (player1.getPlayerType() == player1.HUMAN || player2.getPlayerType() == player2.HUMAN) {
			HumanMessage = true;
		}
		Competition TheCompetition = new Competition(player1, player2,
																	HumanMessage);
		TheCompetition.playMultipleRounds(numGames);

	}

}
