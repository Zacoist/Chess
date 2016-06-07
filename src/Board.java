import javax.swing.JPanel;
/*
 * This is the logic behind the board and the game of chess
 */
public class Board {
	private int clickCount = 0;
	private String piece;
	ChessGame game;
	
	//Instantiate 2D array of Pieces 
	private Piece[][] board = new Piece[8][8];
	
	//CONSTRUCTOR
	//This will initialize the board with the following pieces and their coordinates.
	public Board(ChessGame game) {
		this.game = game;
		
		for (int i = 0; i < 8; i++) {
			board[1][i] = new Pawn('b');
			board[6][i] = new Pawn('w');
		}
		board[0][4] = new King('b');
		
	}//End of Board()
	
	//METHODS

	
	//Where ever the user clicks, it becomes a king
	public void update(int x, int y) {
		clickCount++;
		System.out.println(clickCount);
			if (clickCount == 1){
				piece = board[x][y].getName();
			}
			if (clickCount == 2  && piece == board[x][y].getName()){
				clickCount-=2;
			}
				
		/*board[x][y] = null;
		if (board[x][y] == null) {
			System.out.println("empty");
		} else {
			System.out.println(board[x][y].getName() + ": " + board[x][y].getColour());
			System.out.println(x + " " + y);
		}
		*/
	}//End of updating the logic method
	
	//Get the board
	public Piece[][] getBoard() {
		return board;
	}//End of getBoard()
}