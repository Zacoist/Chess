import java.util.ArrayList;

/*
 * This is the logic behind the board and the game of chess
 */
public class Board {
	// Initialize states within the game
	public enum State {
		BLACK_TURN_STATE, BLACK_CLICKED_STATE, WHITE_TURN_STATE, WHITE_CLICKED_STATE, 
		CHECK_STATE, PLAYING_STATE, CHECK_MATE_STATE, STALE_MATE_STATE
	}

	// Initialize and instantiate variables
	private Pair blackKingCoord;
	private Pair whiteKingCoord;
	private int tempX;
	private int tempY;
	private State currentState;
	private State gameState;


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

		currentState = State.WHITE_TURN_STATE;
		gameState = State.PLAYING_STATE;
	}// End of Board()
	/* END OF CONSTRUCTOR */

	/* METHODS */
	// Where the user clicks,it switches between Click_state and Turn_State
	public void update(int x, int y) {

		// Properties and rules of Turn_state
		if (currentState == State.BLACK_TURN_STATE || currentState == State.WHITE_TURN_STATE) {
			// If the user click's an empty cell, continue to be in Turn_state
			if (board[x][y] == null) {
				return;
			}
			// Check if the user click's on their pieces
			if (currentState == State.BLACK_TURN_STATE && board[x][y].getColour() == 'w'
					|| currentState == State.WHITE_TURN_STATE && board[x][y].getColour() == 'b') {
				return;
			}

			// Store and save the move of the user
			tempX = x;
			tempY = y;

			// Prints out all legal moves of that piece to console
			ArrayList<Pair> allLegalMoves = board[x][y].getLegalMoves(board[x][y], this);
			ArrayList<Pair> noCheckMoves = removeCheckMoves(allLegalMoves, board[x][y]);

			// If there's any moves in noCheckMoes, there are legalMoves
			for (Pair p : noCheckMoves) {
				legalMoves[p.x][p.y] = true;
			}

			// Change to White_Click_State or Black_Click_State
			currentState = (currentState == State.BLACK_TURN_STATE ? State.BLACK_CLICKED_STATE
					: State.WHITE_CLICKED_STATE);
		} else {
			// Check if you click a same colour piece
			if (currentState == State.BLACK_CLICKED_STATE && board[x][y] != null && board[x][y].getColour() == 'b'
					|| currentState == State.WHITE_CLICKED_STATE && board[x][y] != null
							&& board[x][y].getColour() == 'w') {
				currentState = (currentState == State.BLACK_CLICKED_STATE ? State.BLACK_TURN_STATE
						: State.WHITE_TURN_STATE);

				// Instantiate boolean array to change images
				legalMoves = new boolean[8][8];

				return;
			}

			// If there are legalMoves, move it
			if (legalMoves[x][y] == true) {
				// Properties and rules of Clicked_state
				if (x != tempX || y != tempY) {
					movePiece(board[tempX][tempY], x, y);

					// Instantiate boolean array to change images
					legalMoves = new boolean[8][8];

					// Change state to White_Turn_State
					currentState = (currentState == State.BLACK_CLICKED_STATE ? State.WHITE_TURN_STATE
							: State.BLACK_TURN_STATE);

					// Check for check/stalemate
					ArrayList<Pair> allYourMoves = new ArrayList<>();
					char yourColour = (currentState == State.WHITE_TURN_STATE ? 'w' : 'b');
					for (int i = 0; i < board.length; i++) {
						for (int j = 0; j < board[i].length; j++) {
							// Check if the piece if your colour
							if (board[i][j] != null && board[i][j].getColour() == yourColour) {
								// From all the moves, remove all the moves that
								// put you in check
								allYourMoves.addAll(
										removeCheckMoves(board[i][j].getLegalMoves(board[i][j], this), board[i][j]));
							}
						}
					}
					// If your King is in check and you can't make anymore
					// moves, checkmate
					if (isInCheck() && allYourMoves.isEmpty()) {
						System.out.println("CHECKMATE");
						gameState = State.CHECK_MATE_STATE;
						// If your King is in check and you can't move anything
						// else, stalemate
					} else if (!isInCheck() && allYourMoves.isEmpty()) {
						System.out.println("STALEMATE");
						gameState = State.STALE_MATE_STATE;
						// If an opponent piece is attack your King, warn the
						// user "check"
					} else if (isInCheck()) {
						System.out.println("CHECK");
						gameState = State.CHECK_STATE;
					} else {
						gameState = State.PLAYING_STATE;
					}
				}
			} else {
				// Instantiate boolean array to change images
				legalMoves = new boolean[8][8];

				// Change to White_Turn_State or Black_Turn_State
				currentState = (currentState == State.BLACK_CLICKED_STATE ? State.BLACK_TURN_STATE
						: State.WHITE_TURN_STATE);
			}
		}
	}// End of updating the logic method

	// Removes all moves that put the user in check
	private ArrayList<Pair> removeCheckMoves(ArrayList<Pair> allMoves, Piece piece) {
		ArrayList<Pair> noCheckMoves = new ArrayList<>();
		Piece[][] tempBoard = new Piece[8][8];
		int x = piece.getX();
		int y = piece.getY();
		
		copyBoard(board, tempBoard);
		// Do for all the moves the piece can move
		for (Pair p : allMoves) {
			// Move piece
			movePiece(board[x][y], p.x, p.y);

			// Check if that move doesn't put my king in check
			if (!isInCheck()) {
				noCheckMoves.add(p);
			}

			// Reset the board
			copyBoard(tempBoard, board);
		}

		return noCheckMoves;
	}//End removeCheckMoves()

	// Copies the board
	private void copyBoard(Piece[][] original, Piece[][] copy) {
		for (int i = 0; i < copy.length; i++) {
			for (int j = 0; j < copy[i].length; j++) {
				//Any null pointers are kept to null
				if (original[i][j] == null) {
					copy[i][j] = null;
				} else {
					//Put the pieces with their respected names in their proper place
					switch (original[i][j].getName()) {
					case "Pawn":
						copy[i][j] = new Pawn((Pawn) original[i][j]);
						break;
					case "Rook":
						copy[i][j] = new Rook((Rook) original[i][j]);
						break;
					case "Knight":
						copy[i][j] = new Knight((Knight) original[i][j]);
						break;
					case "Bishop":
						copy[i][j] = new Bishop((Bishop) original[i][j]);
						break;
					case "King":
						copy[i][j] = new King((King) original[i][j]);
						break;
					case "Queen":
						copy[i][j] = new Queen((Queen) original[i][j]);
						break;
					}
				}
			}
		}
	}//End of copyBoard()

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

	// Check if the user is in check
	public boolean isInCheck() {
		//Get the coordinates of both kings
		getWhiteKingCoord();
		getBlackKingCoord();
		//Decides the location of a black king or a white king, depending on whose turn it is
		Pair kingcoord = (currentState == State.WHITE_CLICKED_STATE || currentState == State.WHITE_TURN_STATE)
				? whiteKingCoord : blackKingCoord;

		for (int row = 0; row < board.length; row++) {
			for (int column = 0; column < board[row].length; column++) {
				//If any of the legalMoves from the any pieces matches the coordinates of your king, its in check
				if (board[row][column] != null
						&& (board[row][column].getLegalMoves(board[row][column], this).contains(kingcoord))) {
					return true;
				}
			}
		}
		return false;
	}// End of isInCheck()

	// Find the white king's coordinates
	public void getWhiteKingCoord() {
		for (int row = 0; row < board.length; row++) {
			for (int column = 0; column < board[row].length; column++) {
				if (board[row][column] != null && board[row][column].getName() == "King"
						&& board[row][column].getColour() == 'w') {
					whiteKingCoord = new Pair(row, column);
					return;
				}
			}
		}

		whiteKingCoord = null;
	}// End of getWhiteKingCoord()

	// Find the black king's coordinates
	public void getBlackKingCoord() {
		for (int row = 0; row < board.length; row++) {
			for (int column = 0; column < board[row].length; column++) {
				if (board[row][column] != null && board[row][column].getName() == "King"
						&& board[row][column].getColour() == 'b') {
					blackKingCoord = new Pair(row, column);
					return;
				}
			}
		}

		blackKingCoord = null;
	}// End of getBlackKingCoord()
	
	//Get game state
	public State getGameState(){
		return gameState;
	}//End of getGameState()
	
	public Pair findBlackKing(){
		for (int row = 0; row < board.length; row++) {
			for (int column = 0; column < board[row].length; column++) {
				if (board[row][column] != null && board[row][column].getName() == "King"
						&& board[row][column].getColour() == 'b') {
					blackKingCoord = new Pair(row, column);
					return blackKingCoord ;
				}
			}
		}
		return null;
	}
	
	public Pair findWhiteKing(){
		for (int row = 0; row < board.length; row++) {
			for (int column = 0; column < board[row].length; column++) {
				if (board[row][column] != null && board[row][column].getName() == "King"
						&& board[row][column].getColour() == 'b') {
					whiteKingCoord = new Pair(row, column);
					return whiteKingCoord;
				}
			}
		}
		return null;
	}
	
} // End of board class
