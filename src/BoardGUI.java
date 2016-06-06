import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class BoardGUI {
	final String BOARD_IMAGE = "src/Chess/chessboard.png";
	
	ChessGame game;
	
	JPanel boardPanel;
	JLabel board;
	
	JLabel[][] cells = new JLabel[8][8];
	
	public BoardGUI(ChessGame game, JPanel boardPanel) {
		this.game = game;
		this.boardPanel = boardPanel;
		
		boardPanel.setVisible(false);
		boardPanel.setBackground(Color.WHITE);
		boardPanel.setPreferredSize(new Dimension(100, 100));
		boardPanel.setLayout(null);
		
		board = new JLabel(new ImageIcon(BOARD_IMAGE));
		board.setBounds(0, 0, 512, 512);
		board.addMouseListener(new MyMouseListener() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int y = arg0.getX() / 64;
				int x = arg0.getY() / 64;
				
				game.updateBoard(x, y);
			}
		});
		boardPanel.add(board);
		
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[i].length; j++) {
				JLabel cell = new JLabel();
				cell.setBounds(i * 64, j * 64, 64, 64);					
				board.add(cell);
				cells[j][i] = cell;
			}
		}
	}
	
	public void update(Board b) {
		Piece[][] pieces = b.getBoard();
		
		for (int i = 0; i < pieces.length; i++) {
			for (int j = 0; j < pieces[i].length; j++) {
				if (pieces[i][j] instanceof King) {
					cells[i][j].setIcon(new ImageIcon("src/Chess/black_king.png"));
				} else {
					cells[i][j].setIcon(new ImageIcon("src/Chess/white_pawn.png"));
				}
			}
		}
	}
	
	private class MyMouseListener implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent arg0) {}

		@Override
		public void mouseEntered(MouseEvent arg0) {}

		@Override
		public void mouseExited(MouseEvent arg0) {}

		@Override
		public void mousePressed(MouseEvent arg0) {}

		@Override
		public void mouseReleased(MouseEvent arg0) {}
		
	}
}
