/** 
 *  The BoardGUI class handles and process the interaction between the game and the user.  This class select and sets images on the board.
 *	@author Zacoist
 *	@version 6/9/2016
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class BoardGUI {
	// Instantiate board images
	final String BOARD_IMAGE = "src/Chess/chessboard.png";
	final String GRAVEYARD_IMAGE = "src/Chess/chessboard.png";
	final ImageIcon MARKED_ICON = new ImageIcon("src/Chess/marked.png");
	final ImageIcon OUTLINE_ICON = new ImageIcon("src/Chess/outline.png");
	final ImageIcon CLICKED_ICON = new ImageIcon("src/Chess/clicked.png");
	final ImageIcon EMPTY_ICON = null;

	// Initialize game object
	ChessGame game;

	// Initialize boardPanel and board
	JPanel boardPanel;
	JLabel board;
	JLabel whiteGraveYard;
	JLabel blackGraveYard;

	// Instantiate 2D array of cells (for pieces icon) & tiles (for outline and
	// markers)
	JLabel[][] cells = new JLabel[8][8];
	JLabel[][] tiles = new JLabel[8][8];

	// Initialize variables of where the user clicked
	int clickedX;
	int clickedY;

	/* CONSTRUCTOR */
	// Set up what the user will see on the board
	public BoardGUI(ChessGame game, JPanel boardPanel) {
		this.game = game;
		this.boardPanel = boardPanel;

		// Panel settings
		boardPanel.setVisible(false);
		boardPanel.setBackground(Color.WHITE);
		boardPanel.setPreferredSize(new Dimension(100, 100));
		boardPanel.setLayout(null);

		// Declare and instantiate board & graveyard
		board = new JLabel(new ImageIcon(BOARD_IMAGE));
		board.setBounds(0, 0, 512, 512);
		whiteGraveYard = new JLabel(new ImageIcon(GRAVEYARD_IMAGE));
		whiteGraveYard.setBounds(540, 0, 512, 128);
		blackGraveYard = new JLabel(new ImageIcon(GRAVEYARD_IMAGE));
		blackGraveYard.setBounds(540, 386, 512, 128);

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
		boardPanel.add(whiteGraveYard);
		boardPanel.add(blackGraveYard);
	}// End of BoardGUI constructor
	/* END OF CONSTRUCTOR */

	/* METHODS */
	// Update the GUI on the board
	public void update(Board b) {
		
		// 2D array of pieces on every cell
		Piece[][] pieces = b.getBoard();
		for (int i = 0; i < pieces.length; i++) {
			for (int j = 0; j < pieces[i].length; j++) {
				// If the following cell is empty, set its image to null
				if (pieces[i][j] == null) {
					cells[i][j].setIcon(EMPTY_ICON);
				} else {
					// Put the following cell with either a Black or White,
					// King, Queen, Bishop, Rook, Knight and Pawn image
					cells[i][j].setIcon(
							new ImageIcon("src/Chess/" + ((pieces[i][j].getColour() == 'w') ? "white" : "black") + "_"
									+ pieces[i][j].getName().toLowerCase() + ".png"));
				}
			}
		}
		
		// Boolean array to decide to whether or not to put a maker or a blank
		// for the piece's possible move
		boolean[][] legalCells = b.getLegalCells();
		for (int i = 0; i < legalCells.length; i++) {
			for (int j = 0; j < legalCells[i].length; j++) {
				// Display the possible moves of the piece with a marker
				if (legalCells[i][j] == true) {
					tiles[i][j].setIcon(MARKED_ICON);
				} else {
					tiles[i][j].setIcon(EMPTY_ICON);
				}
			}
		}
		
		if (b.getGameState() == Board.State.CHECK_STATE) {
			if ((b.getState() == Board.State.BLACK_TURN_STATE || b.getState() == Board.State.BLACK_CLICKED_STATE)) {
				Pair kingCoord = b.findBlackKing();
				tiles[kingCoord.x][kingCoord.y].setIcon(OUTLINE_ICON);
			} else {
				Pair kingCoord = b.findWhiteKing();
				tiles[kingCoord.x][kingCoord.y].setIcon(OUTLINE_ICON);
			}
		} else {
			Pair kingCoord = b.findBlackKing();
			tiles[kingCoord.x][kingCoord.y].setIcon(EMPTY_ICON);
			
			kingCoord = b.findWhiteKing();
			tiles[kingCoord.x][kingCoord.y].setIcon(EMPTY_ICON);
		}
				
				if (b.getState() == Board.State.BLACK_CLICKED_STATE || b.getState() == Board.State.WHITE_CLICKED_STATE) {
					tiles[clickedX][clickedY].setIcon(CLICKED_ICON);
				} else {
					tiles[clickedX][clickedY].setIcon(EMPTY_ICON);
				}
	}// End of updating the GUI

	// Mouse logic class
	private class MyMouseListener implements MouseListener {
		/* METHODS CONT. */
		@Override
		public void mouseClicked(MouseEvent arg0) {
			// Save the coordinates of where the user has clicked
			clickedX = arg0.getY() / 64;
			clickedY = arg0.getX() / 64;
			
			game.updateBoard(clickedX, clickedY);
		}// End of mouseClicked()

		@Override
		public void mouseEntered(MouseEvent arg0) {
		}// End of mouseEntered()

		@Override
		public void mouseExited(MouseEvent arg0) {
		}// End of mouseExited()

		@Override
		public void mousePressed(MouseEvent arg0) {
		}// End of mousePressed()

		@Override
		public void mouseReleased(MouseEvent arg0) {
		}// End of mouseReleased

	}// End of MyMouseListener()
	
	
}// End of BoardGUI class

