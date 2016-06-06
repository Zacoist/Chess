import javax.swing.JPanel;
/*
 * This is the logic behind the board and the game of chess
 */
public class Board {
	ChessGame game;
	
	//Instantiate 2D array of Pieces 
	private Piece[][] board = new Piece[8][8];
	
	//CONSTRUCTOR
	//This will initialize the board with the following pieces and their coordinates.
	public Board(ChessGame game) {
		this.game = game;
		
		board[0][4] = new King();
	}//End of Board()
	
	//METHODS
	//Get the board
	public Piece[][] getBoard() {
		return board;
	}//End of getBoard()
	
	//
	public void update(int x, int y) {
		board[x][y] = new King();
	}//End of updating the logic emthod
}
