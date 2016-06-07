import javax.swing.JPanel;
/*
 * This is the logic behind the board and the game of chess
 */
public class Board {
	private int clickCount = 0;
	private int tempX;
	private int tempY;
	private String pieceID;
	ChessGame game;
	
	//Instantiate 2D array of Pieces 
	private Piece[][] board = new Piece[8][8];
	
	//CONSTRUCTOR
	//This will initialize the board with the following pieces and their coordinates.
	public Board(ChessGame game) {
		this.game = game;
		
		//Pawns (for both sides)
		for (int i = 0; i < 8; i++) {
			board[1][i] = new Pawn('b');
			board[6][i] = new Pawn('w');
		}
		//Black
		board[0][4] = new King('b');
		board[0][3] = new Queen('b');
		board[0][0] = new Rook('b');
		board[0][7] = new Rook('b');
		board[0][1] = new Knight('b');
		board[0][6] = new Knight('b');
		board[0][2] = new Bishop('b');
		board[0][5] = new Bishop('b');
		
		//White
		board[7][4] = new King('w');
		board[7][3] = new Queen('w');
		board[7][0] = new Rook('w');
		board[7][7] = new Rook('w');
		board[7][1] = new Knight('w');
		board[7][6] = new Knight('w');
		board[7][2] = new Bishop('w');
		board[7][5] = new Bishop('w');
		
		//White
	}//End of Board()
	
	//METHODS

	
	//Where ever the user clicks, it becomes a king
	public void update(int x, int y) {
		clickCount++;
		System.out.println(clickCount);
			if (clickCount == 1){
				//Save previous coordinates
				tempX = x;
				tempY = y;
				System.out.println(board[x][y].getName()+": " + tempX + ","+ tempY);
			}
			//If the user clicks on the same JLabel, it should "deselect"
			else if (clickCount == 2 && (board[x][y] == board[tempX][tempY])){
				clickCount = 0;
				System.out.println("Deselected");
				//Clear piece ID
				pieceID = " ";
				board[x][y] = null;
			}
			//If the user clicks two different JLabels, it should identify both Jlabels
			else if (clickCount == 2 && (board[x][y] != board[tempX][tempY])){
				tempX = x;
				tempY = y;
				System.out.println(board[x][y].getName()+": " + tempX + ","+ tempY);
				clickCount = 0;
			}
			else if (board[x][y] == null && (clickCount == 1 || clickCount == 2)) {
				clickCount = 0;
				System.out.println("empty");
			}
				
		/*if (board[x][y] == null) {
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
