import javax.swing.JPanel;

public class Board {
	ChessGame game;
	
	private Piece[][] board = new Piece[8][8];
	
	public Board(ChessGame game) {
		this.game = game;
		
		board[0][4] = new King();
	}
	
	Piece[][] getBoard() {
		return board;
	}
	
	public void update(int x, int y) {
		board[x][y] = new King();
	}
}
