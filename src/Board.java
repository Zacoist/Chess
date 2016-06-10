
/** 
 *  The Board class is the logic behind the game. Initial coordinates of all pieces and where they can move is calculated here.
 *	@author Zacoist
 *	@version 6/9/2016
 */
import java.util.Arrays;

import javax.swing.JPanel;

/*
 * This is the logic behind the board and the game of chess
 */
public class Board {
	// Initialize states within the game
	public enum State {
		TURN_STATE, CLICKED_STATE
	}

	// Initialize and instantiate variables
	private int clickCount = 0;
	private int tempX;
	private int tempY;
	private String pieceID;
	private State currentState;

	// Initialize game object
	ChessGame game;

	// Instantiate 2D array of Pieces
	private Piece[][] board = new Piece[8][8];
	private boolean[][] legalMoves = new boolean[8][8];

	/* CONSTRUCTOR */
	// This will initialize the board with the following pieces with their
	// respected colour and coordinates.
	public Board(ChessGame game) {
		this.game = game;

		// Pawns (for both sides)
		for (int i = 0; i < 8; i++) {
			board[1][i] = new Pawn('b', 1, i);
			board[6][i] = new Pawn('w', 6, i);
		}

		// Black Pieces
		board[0][0] = new Rook('b', 0, 0);
		board[0][1] = new Knight('b', 0, 1);
		board[0][2] = new Bishop('b', 0, 2);
		board[0][3] = new Queen('b', 0, 3);
		board[0][4] = new King('b', 0, 4);
		board[0][5] = new Bishop('b', 0, 5);
		board[0][6] = new Knight('b', 0, 6);
		board[0][7] = new Rook('b', 0, 7);

		// White Pieces
		board[7][0] = new Rook('w', 7, 0);
		board[7][1] = new Knight('w', 7, 1);
		board[7][2] = new Bishop('w', 7, 2);
		board[7][3] = new Queen('w', 7, 3);
		board[7][4] = new King('w', 7, 4);
		board[7][5] = new Bishop('w', 7, 5);
		board[7][6] = new Knight('w', 7, 6);
		board[7][7] = new Rook('w', 7, 7);

		currentState = State.TURN_STATE;
	}// End of Board()
	/* END OF CONSTRUCTOR */

	/* METHODS */
	// Where the user clicks,it switches between Click_state and Turn_State
	public void update(int x, int y) {
		System.out.println(currentState.name());
		// Checks if the user click on an empty cell
		if (board[x][y] == null) {
			System.out.println("Clicked empty cell");
		} else {
			// If the user has not clicked on an empty cell, return the piece's
			// name and coordinates
			System.out.printf("Clicked: %s at (%d, %d)\n", board[x][y].getName(), board[x][y].getX(),
					board[x][y].getY());
		}
		// Properties and rules of Turn_state
		if (currentState == State.TURN_STATE) {
			// If the user click's an empty cell, continue to be in Turn_state
			if (board[x][y] == null) {
				return;
			}
			// Store and save the move of the user
			tempX = x;
			tempY = y;
			// Prints out all legal moves of that piece to console
			System.out.println("LEGAL MOVES:");
			for (Pair p : board[x][y].getLegalMoves(board[x][y], this)) {
				legalMoves[p.x][p.y] = true;
			}
			// Change state
			currentState = State.CLICKED_STATE;
		} else {
			// Properties and rules of Clicked_state
			if (x != tempX || y != tempY) {
				movePiece(board[tempX][tempY], x, y);
			}
			// Instantiate boolean array to change images
			legalMoves = new boolean[8][8];
			// Change state
			currentState = State.TURN_STATE;
		}
		System.out.println(currentState.name());
	}// End of updating the logic method

	// Checks if the next move is on the board
	public boolean isWithinBounds(int x, int y) {
		return (0 <= x && x <= 7 && 0 <= y && y <= 7);
	}// End of isWithinBounds()

	// Checks if the next move is on the board is on an empty cell
	public boolean isCellEmpty(int x, int y) {
		return (board[x][y] == null);
	}// End of isCellEmpty

	// Checks if the next has a piece on it
	public boolean isCapturable(Piece p, int x, int y) {
		// Checks if its empty or not
		if (isCellEmpty(x, y)) {
			return false;
		}
		// If the next move has a piece on it that of the same colour, you can
		// not capture it
		return (p.getColour() != board[x][y].getColour());
	}// End of isCapturable();

	// Move the piece on the board
	private void movePiece(Piece p, int x, int y) {
		// Save current coordinates(x,y)
		int oldX = p.getX();
		int oldY = p.getY();
		// Move piece to new cell
		p.moveTo(x, y);
		board[x][y] = board[oldX][oldY];
		// Clear the old cell
		board[oldX][oldY] = null;
	}// End of movePiece ()

	// Returns the state of the game
	public State getState() {
		return currentState;
	}// End of getState()

	// Get the board
	public Piece[][] getBoard() {
		return board;
	}// End of getBoard()

	// Returns which moves are legal on the board
	public boolean[][] getLegalCells() {
		return legalMoves;
	}// End of getLegalCells()
} // End of board class
