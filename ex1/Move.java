/**
 * This class represent a move in the Nim game. The move is determind by the
 * chosen row, leftBound and the right bound.
 */
class Move {
	private int rowNumber, leftBound, rightBound;

	/**
	 * Constructor. Initialize a move of the given row, left bound and right
	 * bound.
	 * 
	 * @param Row
	 *            The row that sticks sequence are taken from.
	 * @param Left
	 *            The first stick of the sticks sequence that we want to mark.
	 * @param Right
	 *            The last stick of the sticks sequence that we want to mark.
	 */
	public Move(int Row, int Left, int Right) {
		rowNumber = Row;
		leftBound = Left;
		rightBound = Right;
	}

	/**
	 * @return a string representation of the move
	 */
	public String toString() {
		return rowNumber + ":" + leftBound + "-" + rightBound;
	}

	/**
	 * @return an integer on which the move is performed
	 */
	public int getRow() {
		return rowNumber;
	}

	/**
	 * @return an integer matching the left bound of the stick sequence to mark.
	 */
	public int getLeftBound() {
		return leftBound;
	}

	/**
	 * an integer matching the right bound of the stick sequence to mark.
	 */
	public int getRightBound() {
		return rightBound;
	}

}
