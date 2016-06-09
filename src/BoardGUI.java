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
	// Instantiate board image
	final String BOARD_IMAGE = "src/Chess/chessboard.png";
	
	final ImageIcon MARKED_ICON = new ImageIcon("src/Chess/marked.png");
	final ImageIcon OUTLINE_ICON = new ImageIcon("src/Chess/outline.png");
	final ImageIcon EMPTY_ICON = null;

	private int clickCount;
	ChessGame game;

	// Initialize boardPanel and board
	JPanel boardPanel;
	JLabel board;

	// Instantiate 2D array of cells
	JLabel[][] cells = new JLabel[8][8];
	JLabel[][] tiles = new JLabel[8][8];
	
	int clickedX;
	int clickedY;

	// CONSTRUCTOR
	// Set up what the user will see on the board
	public BoardGUI(ChessGame game, JPanel boardPanel) {
		this.game = game;
		this.boardPanel = boardPanel;

		// Panel settings
		boardPanel.setVisible(false);
		boardPanel.setBackground(Color.WHITE);
		boardPanel.setPreferredSize(new Dimension(100, 100));
		boardPanel.setLayout(null);

		// Declare and instantiate board
		board = new JLabel(new ImageIcon(BOARD_IMAGE));
		board.setBounds(0, 0, 512, 512);

		// 2D array of tiles (64) to add outline and markers
		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles[i].length; j++) {
				JLabel tile = new JLabel();
				tile.setBounds(i * 64, j * 64, 64, 64);
				board.add(tile);
				tiles[j][i] = tile;
			}
		}

		// 2D array of cells (64) on the board
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[i].length; j++) {
				JLabel cell = new JLabel();
				cell.setBounds(i * 64, j * 64, 64, 64);
				board.add(cell);
				cells[j][i] = cell;
			}
		}

		// If the user clicks any where on the board, the coordinates are given
		board.addMouseListener(new MyMouseListener());
		boardPanel.add(board);
	}// End of BoardGUI constructor

	// METHODS
	// Update the GUI on the board
	public void update(Board b) {
		// 2D array of pieces on every cell
		Piece[][] pieces = b.getBoard();
		for (int i = 0; i < pieces.length; i++) {
			for (int j = 0; j < pieces[i].length; j++) {
				// The piece at the following coordinates(i, j) is a...
				if (pieces[i][j] == null) {
					cells[i][j].setIcon(EMPTY_ICON);
				} else {
					cells[i][j].setIcon(
							new ImageIcon("src/Chess/" + ((pieces[i][j].getColour() == 'w') ? "white" : "black") + "_"
									+ pieces[i][j].getName().toLowerCase() + ".png"));
				}
			}
		}
		
		
		boolean[][] legalCells = b.getLegalCells();
		for (int i = 0; i < legalCells.length; i++) {
			for (int j = 0; j < legalCells[i].length; j++) {
				if (legalCells[i][j] == true) {
					tiles[i][j].setIcon(MARKED_ICON);
				} else {
					tiles[i][j].setIcon(EMPTY_ICON);
				}
			}
		}
		
		if (b.getState() == Board.State.CLICKED_STATE) {
			tiles[clickedX][clickedY].setIcon(OUTLINE_ICON);
		} else {
			tiles[clickedX][clickedY].setIcon(EMPTY_ICON);
		}
		
	}// End of updating the GUI

	// Mouse logic
	private class MyMouseListener implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent arg0) {
			clickedX = arg0.getY() / 64;
			clickedY = arg0.getX() / 64;
			game.updateBoard(clickedX, clickedY);
			// tiles[x][y].setIcon(new ImageIcon("src/Chess/outline.png" ==
			// board.getState() ?));
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
		}

	}// End of MyMouseListener()

	public int getClickCount() {
		return clickCount;
	}

}// End of BoardGUI class
