import java.util.Arrays;

import javax.swing.JPanel;

/*
 * This is the logic behind the board and the game of chess
 */
public class Board {
	public enum State {
		TURN_STATE, CLICKED_STATE
	}

	private int clickCount = 0;
	private int tempX;
	private int tempY;
	private String pieceID;

	private State currentState;
	ChessGame game;

	// Instantiate 2D array of Pieces
	private Piece[][] board = new Piece[8][8];
	private boolean[][] legalMoves = new boolean[8][8];

	// CONSTRUCTOR
	// This will initialize the board with the following pieces and their
	// coordinates.
	public Board(ChessGame game) {
		this.game = game;

		// Pawns (for both sides)
		for (int i = 0; i < 8; i++) {
			board[1][i] = new Pawn('b', 1, i);
			board[6][i] = new Pawn('w', 6, i);

		}
		// Black
		board[0][0] = new Rook('b', 0, 0);
		board[0][1] = new Knight('b', 0, 1);
		board[0][2] = new Bishop('b', 0, 2);
		board[0][3] = new Queen('b', 0, 3);
		board[0][4] = new King('b', 0, 4);
		board[0][5] = new Bishop('b', 0, 5);
		board[0][6] = new Knight('b', 0, 6);
		board[0][7] = new Rook('b', 0, 7);

		// White
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

	// METHODS

	public boolean isWithinBounds(int x, int y) {
		return (0 <= x && x <= 7 && 0 <= y && y <= 7);
	}

	public boolean isCellEmpty(int x, int y) {
		return (board[x][y] == null);
	}

	public boolean isCapturable(Piece p, int x, int y) {
		if (isCellEmpty(x, y)) {
			return false;
		}

		return (p.getColour() != board[x][y].getColour());
	}

	// Where ever the user clicks, it becomes a king
	public void update(int x, int y) {
		System.out.println(currentState.name());
		if (board[x][y] == null) {
			System.out.println("Clicked empty cell");
		} else {
			System.out.printf("Clicked: %s at (%d, %d)\n", board[x][y].getName(), board[x][y].getX(),
					board[x][y].getY());
		}

		if (currentState == State.TURN_STATE) {
			if (board[x][y] == null) {
				return;
			}

			tempX = x;
			tempY = y;

			System.out.println("LEGAL MOVES:");
			for (Pair p : board[x][y].getLegalMoves(board[x][y], this)) {
				legalMoves[p.x][p.y] = true;
			}

			/*
			 * if (board[x][y].getName().equals("Pawn")) {
			 * 
			 * } } if (board[x][y].getName().equals("Queen")) {
			 * System.out.println("LEGAL MOVES:"); int direction =
			 * ((board[x][y].getColour() == 'w') ? +1 : -1); for (Pair p :
			 * board[x][y].getLegalMoves(new Pair(x, y), board, direction)) {
			 * System.out.printf("(%d, %d)\n", p.x, p.y); } }
			 */
			currentState = State.CLICKED_STATE;
		} else {
			if (x != tempX || y != tempY) {
				movePiece(board[tempX][tempY], x, y);
			}

			legalMoves = new boolean[8][8];
			currentState = State.TURN_STATE;
		}
		System.out.println(currentState.name());
	}// End of updating the logic method

	private void movePiece(Piece p, int x, int y) {
		int oldX = p.getX();
		int oldY = p.getY();

		// Move piece to new cell
		p.moveTo(x, y);
		board[x][y] = board[oldX][oldY];

		// Clear the old cell
		board[oldX][oldY] = null;
	}

	public State getState() {
		return currentState;
	}

	// Get the board
	public Piece[][] getBoard() {
		return board;
	}// End of getBoard()

	public boolean[][] getLegalCells() {
		return legalMoves;
	}// End of getLegalCells()
}
