
/** 
 *  The ChessGame class runs the game. Properties and settings for all JPanels, JFrame, JLabels and JButtons are set here except for things in the Board panel.
 *	@author Zacoist
 *	@version 6/9/2016
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

public class ChessGame {
	// The entirety of the chess game
	private JFrame frmChessGame;

	// Initialize JPanels
	private JPanel panelTitleScreen;
	private JPanel panelBoardGame;
	private JPanel panelInstructions;
	private JPanel panelHighScore;

	// Initialize Board and BoardGUI objects
	private Board b;
	private BoardGUI bgui;

	// Runs the chess game
	public static void main(String[] args) {
		try {
			ChessGame window = new ChessGame();
			window.frmChessGame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}// End of main()

	/* CONSTRUCTOR */
	// Instantiate Board and BoardGUI objects Board does the logic of the chess
	// game BoardGui handles the interaction between the game and the user
	public ChessGame() {
		initialize();

		// This panel contains the chess game itself (Chess board, 2 buttons, 2
		// timers)
		panelBoardGame = new JPanel();
		frmChessGame.getContentPane().add(panelBoardGame);

		b = new Board(this);
		bgui = new BoardGUI(this, panelBoardGame);

		bgui.update(b);
	}// End of ChessGame()
	/* END OF CONSTRUCTOR */
	/* METHODS */

	// Updates the GUI and the logic of the board
	public void updateBoard(int x, int y) {
		b.update(x, y);
		bgui.update(b);
	}// End of updateBoard()

	// The settings and layout for each panel
	public void initialize() {
		// Instantiate icon image
		final String FAVICON = "src/Chess/black_knight.png";
		Image im = Toolkit.getDefaultToolkit().getImage(FAVICON);

		// Initialize frame
		frmChessGame = new JFrame();

		// Frame Settings
		frmChessGame.setTitle("Chess Game");
		frmChessGame.setSize(600, 600);
		frmChessGame.setLocationRelativeTo(null);
		frmChessGame.setResizable(true);
		frmChessGame.setIconImage(im);
		frmChessGame.setResizable(false);
		frmChessGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmChessGame.getContentPane().setLayout(new CardLayout(0, 0));

		/*
		 * Initialize panels (4)
		 */

		// This panel is the title screen and the central hub of which the user
		// may connect with other panels (4 buttons)
		panelTitleScreen = new JPanel();
		
		panelTitleScreen.setBackground(Color.WHITE);
		panelTitleScreen.setVisible(true);
		panelTitleScreen.setPreferredSize(new Dimension(640, 480));
		panelTitleScreen.setLayout(null);
		frmChessGame.getContentPane().add(panelTitleScreen);

		// Title
		JLabel lblTitle = new JLabel("Classic Chess");
		lblTitle.setFont(new Font("Centaur", Font.PLAIN, 36));
		lblTitle.setBounds(155, 137, 431, 50);
		panelTitleScreen.add(lblTitle);

		// Play Game Button
		JButton btnStartButton = new JButton("Start Game");
		// btnStartButton.setFont(new Font("Times New Roman", Font.BOLD, 14));

		btnStartButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchPanel(panelTitleScreen, panelBoardGame);
			}
		});

		btnStartButton.setBounds(279, 219, 139, 23);
		panelTitleScreen.add(btnStartButton);

		// Instruction button
		JButton btnToInstruction = new JButton("Instructions");

		btnToInstruction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchPanel(panelTitleScreen, panelInstructions);
			}
		});

		btnToInstruction.setBounds(279, 253, 139, 23);
		panelTitleScreen.add(btnToInstruction);

		// LeaderBoard Button
		JButton btnLeaderboard = new JButton("Leaderboard");

		btnLeaderboard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchPanel(panelTitleScreen, panelHighScore);
			}
		});

		btnLeaderboard.setBounds(279, 287, 139, 23);
		panelTitleScreen.add(btnLeaderboard);

		// Exit Button
		JButton btnExit = new JButton("Exit");

		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		btnExit.setBounds(279, 320, 139, 23);
		panelTitleScreen.add(btnExit);

		// This panel contains information on each piece (3 buttons)
		panelInstructions = new JPanel();

		panelInstructions.setVisible(false);

		// This panel contain the high scores from read from a text file (1
		// button, 2 text fields)
		panelHighScore = new JPanel();
		panelHighScore.setVisible(false);
	}// End of initialize()

	// This methods will allow buttons to switch between panels
	private void switchPanel(JPanel from, JPanel to) {
		from.setVisible(false);
		to.setVisible(true);
	} // End of switchPanel()

}// End of chess game class
