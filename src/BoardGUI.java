import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/*
 * This is the board what the user will see and interact with
 */
public class BoardGUI {
	//Instantiate board image
	final String BOARD_IMAGE = "src/Chess/chessboard.png";
	
	private int clickCount;
	ChessGame game;
	
	//Initialize boardPanel and board
	JPanel boardPanel;
	JLabel board;
	
	//Instantiate 2D array of cells
	JLabel[][] cells = new JLabel[8][8];
	
	//CONSTRUCTOR
	//Set up what the user will see on the board
	public BoardGUI(ChessGame game, JPanel boardPanel) {
		this.game = game;
		this.boardPanel = boardPanel;
		
		//Panel settings
		boardPanel.setVisible(false);
		boardPanel.setBackground(Color.WHITE);
		boardPanel.setPreferredSize(new Dimension(100, 100));
		boardPanel.setLayout(null);
		
		//Declare and instantiate board
		board = new JLabel(new ImageIcon(BOARD_IMAGE));
		board.setBounds(0, 0, 512, 512);
		
		//2D array of cells (64) on the board
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[i].length; j++) {
				JLabel cell = new JLabel();
				cell.setBounds(i * 64, j * 64, 64, 64);					
				board.add(cell);
				cells[j][i] = cell;
			}
		}
		
		//If the user clicks any where on the board, the coordinates are given
		board.addMouseListener(new MyMouseListener());
		boardPanel.add(board);
		
		
	}//End of BoardGUI constructor
	
	//METHODS
	//Update the GUI on the board
	public void update(Board b) {
		//2D array of pieces on every cell
		Piece[][] pieces = b.getBoard();
		for (int i = 0; i < pieces.length; i++) {
			for (int j = 0; j < pieces[i].length; j++) {
				//The piece at the following coordinates(i, j) is a...
				//King
				if (pieces[i][j] instanceof King) {
					//White King
					if (pieces[i][j].getColour() == 'w'){
						cells[i][j].setIcon(new ImageIcon("src/Chess/white_king.png"));
					}
					//Black King
					else if (pieces[i][j].getColour() == 'b'){
						cells[i][j].setIcon(new ImageIcon("src/Chess/black_king.png"));
					}
				} 
				//Queens
				else if (pieces[i][j] instanceof Queen){
					//White Queen
					if (pieces[i][j].getColour() == 'w'){
						cells[i][j].setIcon(new ImageIcon("src/Chess/white_queen.png"));
					//Black Queen
					} else if (pieces[i][j].getColour() == 'b'){
						cells[i][j].setIcon(new ImageIcon("src/Chess/black_queen.png"));
					}
				}
				//Rooks
				else if (pieces[i][j] instanceof Rook){
					//White Rook
					if (pieces[i][j].getColour() == 'w'){
						cells[i][j].setIcon(new ImageIcon("src/Chess/white_rook.png"));
					//Black Rook
					} else if (pieces[i][j].getColour() == 'b'){
						cells[i][j].setIcon(new ImageIcon("src/Chess/black_rook.png"));
					}
				}
				//Knights
				else if (pieces[i][j] instanceof Knight){
					//White Knight
					if (pieces[i][j].getColour() == 'w'){
						cells[i][j].setIcon(new ImageIcon("src/Chess/white_knight.png"));
					//Black Knight
					} else if (pieces[i][j].getColour() == 'b'){
						cells[i][j].setIcon(new ImageIcon("src/Chess/black_knight.png"));
					}
				}
				//Bishops
				else if (pieces[i][j] instanceof Bishop){
					//White Bishop
					if (pieces[i][j].getColour() == 'w'){
						cells[i][j].setIcon(new ImageIcon("src/Chess/white_bishop.png"));
					//Black Bishop
					} else if (pieces[i][j].getColour() == 'b'){
						cells[i][j].setIcon(new ImageIcon("src/Chess/black_bishop.png"));
					}
				}
				//Pawns
				else if (pieces[i][j] instanceof Pawn){
					//White Pawn
					if (pieces[i][j].getColour() == 'w'){
						cells[i][j].setIcon(new ImageIcon("src/Chess/white_pawn.png"));
					//Black Pawn
					} else if (pieces[i][j].getColour() == 'b'){
						cells[i][j].setIcon(new ImageIcon("src/Chess/black_pawn.png"));
					}
				}
				//Empty
				else {
					cells[i][j].setIcon(null);
				}
			}
		}
	}//End of updating the GUI
	
	//Mouse logic
	private class MyMouseListener implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent arg0) {
			int y = arg0.getX() / 64;
			int x = arg0.getY() / 64;
			game.updateBoard(x, y);
		}
		
		@Override
		public void mouseEntered(MouseEvent arg0) {}

		@Override
		public void mouseExited(MouseEvent arg0) {}

		@Override
		public void mousePressed(MouseEvent arg0) {}

		@Override
		public void mouseReleased(MouseEvent arg0) {}
		
	}//End of MyMouseListener()
	
	public int getClickCount(){
		return clickCount;
	}
	
	
}//End of BoardGUI class

