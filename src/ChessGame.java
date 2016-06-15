
/** 
 *  The ChessGame class runs the game. Properties and settings for all JPanels, JFrame, JLabels and JButtons are set here except for things in the Board panel.
 *	@author Zacoist
 *	@version 6/9/2016
 */

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

public class ChessGame {

	private JList<String> lstNames;
	private JList<Integer> lstScores;
	private DefaultListModel<String> namesModel;
	private DefaultListModel<Integer> scoresModel;
	private List<Score> highScores;

	// The entirety of the chess game
	private JFrame frmChessGame;

	// Initialize JPanels
	private JPanel panelTitleScreen;
	private JPanel panelBoardGame;
	private JPanel panelInstructions;
	private JPanel panelInstructions2;
	private JPanel panelInstructions3;
	private JPanel panelInstructions4;
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
		resetGame();
	}// End of ChessGame()
	/* END OF CONSTRUCTOR */
	/* METHODS */

	private void resetGame() {
		// This panel contains the chess game itself (Chess board, 2 buttons, 2
		// timers)
		panelBoardGame = new JPanel();
		frmChessGame.getContentPane().add(panelBoardGame);

		b = new Board(this);
		bgui = new BoardGUI(this, panelBoardGame);

		bgui.update(b);
	}

	// Updates the GUI and the logic of the board
	public void updateBoard(int x, int y) {

		b.update(x, y);
		bgui.update(b);
		// Check if someone has won message
		if (b.getGameState() == Board.State.CHECK_MATE_STATE) {
			String username;
			if (b.getState() == Board.State.WHITE_TURN_STATE && b.isInCheck()) {
				username = JOptionPane.showInputDialog(frmChessGame,
						"Congratulations! Black wins! Enter your name here:", "Winner!", JOptionPane.DEFAULT_OPTION);
			} else {
				username = JOptionPane.showInputDialog(frmChessGame,
						"Congratulations! White wins! Enter your name here:", "Winner!", JOptionPane.DEFAULT_OPTION);
			}

			if (username == null || username.isEmpty()) {
				// Switch to main screen
				frmChessGame.setSize(300, 300);
				switchPanel(panelBoardGame, panelTitleScreen);
			} else {
				// add new score
				Score s;
				boolean found = false;
				for (int i = 0; i < highScores.size() && !found; i++) {
					s = highScores.get(i);
					if (s.getName().equals(username)) {
						s.setScore(s.getScore() + 1);
						found = true;
					}
				}
				
				if (!found) {
					highScores.add(new Score(username, 1));
				}
				
				saveHighScores();
				
				// Switch to leader boards screen
				switchPanel(panelBoardGame, panelHighScore);
				putScoresIntoList();
			}

			resetGame();
		}
		// Stalemate message
		else if (b.getGameState() == Board.State.STALE_MATE_STATE) {
			JOptionPane.showMessageDialog(frmChessGame, "Stalemate.");

			resetGame();
		}

	}// End of updateBoard()

	// The settings and layout for each panel
	public void initialize() {
		// Instantiate icon image
		final String HELPCHESSBOARD = "src/Chess/board_instructions.png";
		final String WPAWN = "src/Chess/white_pawn.png";
		final String BPAWN = "src/Chess/black_pawn.png";
		final String WBISHOP = "src/Chess/white_bishop.png";
		final String BBISHOP = "src/Chess/black_bishop.png";
		final String WKNIGHT = "src/Chess/white_knight.png";
		final String BKNIGHT = "src/Chess/black_knight.png";
		final String WROOK = "src/Chess/white_rook.png";
		final String BROOK = "src/Chess/black_rook.png";
		final String WQUEEN = "src/Chess/white_queen.png";
		final String BQUEEN = "src/Chess/black_queen.png";
		final String WKING = "src/Chess/white_king.png";
		final String BKING = "src/Chess/black_king.png";
		final String FAVICON = "src/Chess/black_knight.png";
		final String PAWNMOVEMENT = "src/Chess/pawn_movement.png";
		final String KNIGHTMOVEMENT = "src/Chess/knight_movement.png";
		final String BISHOPMOVEMENT = "src/Chess/bishop_movement.png";
		final String ROOKMOVEMENT = "src/Chess/rook_movement.png";
		final String QUEENMOVEMENT = "src/Chess/queen_movement.png";
		final String KINGMOVEMENT = "src/Chess/king_movement.png";

		Image im = Toolkit.getDefaultToolkit().getImage(FAVICON);

		// Initialize frame
		frmChessGame = new JFrame();

		// Frame Settings
		frmChessGame.setTitle("Chess Game");
		frmChessGame.setSize(300, 300);
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
		panelTitleScreen.setLayout(null);
		frmChessGame.getContentPane().add(panelTitleScreen);

		// Title
		JLabel lblTitle = new JLabel("Classic Chess");
		lblTitle.setFont(new Font("Centaur", Font.PLAIN, 36));
		lblTitle.setBounds(60, 25, 450, 50);
		panelTitleScreen.add(lblTitle);

		// Play Game Button
		JButton btnStartButton = new JButton("Start Game");
		// btnStartButton.setFont(new Font("Times New Roman", Font.BOLD, 14));

		btnStartButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmChessGame.setBounds(250, 200, 518, 540);
				switchPanel(panelTitleScreen, panelBoardGame);
			}
		});

		btnStartButton.setBounds(60, 80, 175, 30);
		panelTitleScreen.add(btnStartButton);

		// Instruction button
		JButton btnToInstruction = new JButton("Instructions");

		btnToInstruction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmChessGame.setBounds(575, 250, 700, 500);
				switchPanel(panelTitleScreen, panelInstructions);
			}
		});

		btnToInstruction.setBounds(60, 130, 175, 30);
		panelTitleScreen.add(btnToInstruction);

		// LeaderBoard Button
		JButton btnLeaderboard = new JButton("Leaderboard");

		btnLeaderboard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmChessGame.setBounds(575, 300, 720, 480);
				switchPanel(panelTitleScreen, panelHighScore);
				putScoresIntoList();
			}
		});

		btnLeaderboard.setBounds(60, 180, 175, 30);
		panelTitleScreen.add(btnLeaderboard);

		// Exit Button
		JButton btnExit = new JButton("Exit");

		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		btnExit.setBounds(60, 230, 175, 30);
		panelTitleScreen.add(btnExit);

		// This panel contains information on each piece (3 buttons)
		panelInstructions = new JPanel();

		JLabel boardInstruction;

		panelInstructions.setBackground(Color.WHITE);
		panelInstructions.setVisible(false);
		panelInstructions.setLayout(null);
		frmChessGame.getContentPane().add(panelInstructions);

		// General description from wikipedia
		JLabel lblChessDes = new JLabel(
				"\"Chess is a two-player strategy board game played on a chessboard, a checkered gameboard with 64 squares");
		lblChessDes.setFont(new Font("Centaur", Font.PLAIN, 16));
		lblChessDes.setBounds(20, 25, 700, 50);
		panelInstructions.add(lblChessDes);

		JLabel lblChessDes2 = new JLabel(
				"arranged in an eight-by-eight grid. Chess is played by millions of people worldwide, both amateurs and");
		lblChessDes2.setFont(new Font("Centaur", Font.PLAIN, 16));
		lblChessDes2.setBounds(20, 45, 700, 50);
		panelInstructions.add(lblChessDes2);

		JLabel lblChessDes3 = new JLabel("professionals.\" -Wikipedia");
		lblChessDes3.setFont(new Font("Centaur", Font.PLAIN, 16));
		lblChessDes3.setBounds(20, 65, 700, 50);
		panelInstructions.add(lblChessDes3);

		// Content Information
		JLabel lblTitle2 = new JLabel("Basic Chess Information");
		lblTitle2.setFont(new Font("Centaur", Font.BOLD, 28));
		lblTitle2.setBounds(350, 100, 300, 50);
		panelInstructions.add(lblTitle2);

		JLabel lbltip1 = new JLabel("• Objective: \"Checkmate\" the opposing king");
		lbltip1.setFont(new Font("Centaur", Font.PLAIN, 14));
		lbltip1.setBounds(350, 140, 700, 50);
		panelInstructions.add(lbltip1);

		JLabel lbltip3 = new JLabel("• White moves first. Proceed by alternating turns.");
		lbltip3.setFont(new Font("Centaur", Font.PLAIN, 14));
		lbltip3.setBounds(350, 160, 700, 50);
		panelInstructions.add(lbltip3);

		JLabel lbltip4 = new JLabel("• A chessboard is an 8x8 grid. A naming convention is employed ");
		lbltip4.setFont(new Font("Centaur", Font.PLAIN, 14));
		lbltip4.setBounds(350, 180, 700, 50);
		panelInstructions.add(lbltip4);

		JLabel lbltip5 = new JLabel("to see where every piece moves.");
		lbltip5.setFont(new Font("Centaur", Font.PLAIN, 14));
		lbltip5.setBounds(360, 195, 700, 50);
		panelInstructions.add(lbltip5);

		JLabel lbltip6 = new JLabel("X Axis: a-h");
		lbltip6.setFont(new Font("Centaur", Font.PLAIN, 14));
		lbltip6.setBounds(390, 210, 700, 50);
		panelInstructions.add(lbltip6);

		JLabel lbltip7 = new JLabel("Y Axis: 1-8");
		lbltip7.setFont(new Font("Centaur", Font.PLAIN, 14));
		lbltip7.setBounds(390, 225, 700, 50);
		panelInstructions.add(lbltip7);

		JLabel lbltip8 = new JLabel("• Each player begins the game with 16 pieces:");
		lbltip8.setFont(new Font("Centaur", Font.PLAIN, 14));
		lbltip8.setBounds(350, 245, 700, 50);
		panelInstructions.add(lbltip8);

		JLabel lbltip9 = new JLabel("- one king");
		lbltip9.setFont(new Font("Centaur", Font.PLAIN, 14));
		lbltip9.setBounds(390, 260, 700, 50);
		panelInstructions.add(lbltip9);

		JLabel lbltip10 = new JLabel("- one queen");
		lbltip10.setFont(new Font("Centaur", Font.PLAIN, 14));
		lbltip10.setBounds(390, 275, 700, 50);
		panelInstructions.add(lbltip10);

		JLabel lbltip11 = new JLabel("- two rooks");
		lbltip11.setFont(new Font("Centaur", Font.PLAIN, 14));
		lbltip11.setBounds(390, 290, 700, 50);
		panelInstructions.add(lbltip11);

		JLabel lbltip12 = new JLabel("- two knights");
		lbltip12.setFont(new Font("Centaur", Font.PLAIN, 14));
		lbltip12.setBounds(390, 305, 700, 50);
		panelInstructions.add(lbltip12);

		JLabel lbltip13 = new JLabel("- two bishops");
		lbltip13.setFont(new Font("Centaur", Font.PLAIN, 14));
		lbltip13.setBounds(390, 320, 700, 50);
		panelInstructions.add(lbltip13);

		JLabel lbltip14 = new JLabel("- eight pawns");
		lbltip14.setFont(new Font("Centaur", Font.PLAIN, 14));
		lbltip14.setBounds(390, 335, 700, 50);
		panelInstructions.add(lbltip14);

		// Image
		boardInstruction = new JLabel(new ImageIcon(HELPCHESSBOARD));
		boardInstruction.setBounds(0, 150, 300, 305);
		panelInstructions.add(boardInstruction);

		// Add navigation buttons
		// Main Menu Button
		JButton btnToMainMenu = new JButton("Main Menu");

		btnToMainMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmChessGame.setSize(300, 300);
				switchPanel(panelInstructions, panelTitleScreen);
			}
		});

		btnToMainMenu.setBounds(530, 400, 100, 30);
		panelInstructions.add(btnToMainMenu);

		// Right Button
		JButton btnToPage2 = new JButton(">");

		btnToPage2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmChessGame.setBounds(575, 250, 700, 500);
				switchPanel(panelInstructions, panelInstructions2);
			}
		});

		btnToPage2.setBounds(640, 400, 42, 30);
		panelInstructions.add(btnToPage2);

		// PanelInstructions2
		panelInstructions2 = new JPanel();

		JLabel blackpawn;
		JLabel whitepawn;
		JLabel blackknight;
		JLabel whiteknight;
		JLabel pawnmoves;
		JLabel knightmoves;

		panelInstructions2.setBackground(Color.WHITE);
		panelInstructions2.setVisible(false);
		panelInstructions2.setLayout(null);
		frmChessGame.getContentPane().add(panelInstructions2);

		// Content Information CONT.
		JLabel lbltip15 = new JLabel("Movement and Standard Valuations");
		lbltip15.setFont(new Font("Centaur", Font.BOLD, 28));
		lbltip15.setBounds(10, 5, 700, 50);
		panelInstructions2.add(lbltip15);
		// Pawn
		JLabel lbltip16 = new JLabel("Pawn - Value : 1");
		lbltip16.setFont(new Font("Centaur", Font.PLAIN, 16));
		lbltip16.setBounds(128, 60, 700, 50);
		panelInstructions2.add(lbltip16);

		JLabel lbltip17 = new JLabel(
				"Movement: May advance one square but intially can move either one or two square.");
		lbltip17.setFont(new Font("Centaur", Font.PLAIN, 14));
		lbltip17.setBounds(250, 60, 700, 50);
		panelInstructions2.add(lbltip17);

		JLabel lbltip18 = new JLabel("Captures diagonally facing forward either left or right.");
		lbltip18.setFont(new Font("Centaur", Font.PLAIN, 14));
		lbltip18.setBounds(313, 75, 700, 50);
		panelInstructions2.add(lbltip18);

		JLabel lbltip19 = new JLabel("*NOTE: Pawns can NEVER move backwards.");
		lbltip19.setFont(new Font("Centaur", Font.PLAIN, 14));
		lbltip19.setBounds(313, 90, 700, 50);
		panelInstructions2.add(lbltip19);

		// Knight
		JLabel lbltip20 = new JLabel("Knight - Value : 3");
		lbltip20.setFont(new Font("Centaur", Font.PLAIN, 16));
		lbltip20.setBounds(128, 250, 700, 50);
		panelInstructions2.add(lbltip20);

		JLabel lbltip21 = new JLabel("Movement: Two squares horizontally and one square vertically, or two squares ");
		lbltip21.setFont(new Font("Centaur", Font.PLAIN, 14));
		lbltip21.setBounds(250, 250, 700, 50);
		panelInstructions2.add(lbltip21);

		JLabel lbltip22 = new JLabel("vertically and one square horizontally.");
		lbltip22.setFont(new Font("Centaur", Font.PLAIN, 14));
		lbltip22.setBounds(313, 265, 700, 50);
		panelInstructions2.add(lbltip22);

		JLabel lbltip23 = new JLabel("Captures in an \"L\" shape");
		lbltip23.setFont(new Font("Centaur", Font.PLAIN, 14));
		lbltip23.setBounds(313, 280, 700, 50);
		panelInstructions2.add(lbltip23);

		JLabel lbltip24 = new JLabel("*NOTE: Can \"jump\" over any piece.");
		lbltip24.setFont(new Font("Centaur", Font.PLAIN, 14));
		lbltip24.setBounds(313, 295, 700, 50);
		panelInstructions2.add(lbltip24);

		// Pawn & Knight Images
		whitepawn = new JLabel(new ImageIcon(WPAWN));
		whitepawn.setBounds(0, 50, 64, 64);
		panelInstructions2.add(whitepawn);

		blackpawn = new JLabel(new ImageIcon(BPAWN));
		blackpawn.setBounds(64, 50, 64, 64);
		panelInstructions2.add(blackpawn);

		pawnmoves = new JLabel(new ImageIcon(PAWNMOVEMENT));
		pawnmoves.setBounds(220, 110, 150, 151);
		panelInstructions2.add(pawnmoves);

		whiteknight = new JLabel(new ImageIcon(WKNIGHT));
		whiteknight.setBounds(0, 240, 64, 64);
		panelInstructions2.add(whiteknight);

		blackknight = new JLabel(new ImageIcon(BKNIGHT));
		blackknight.setBounds(64, 240, 64, 64);
		panelInstructions2.add(blackknight);

		knightmoves = new JLabel(new ImageIcon(KNIGHTMOVEMENT));
		knightmoves.setBounds(220, 320, 150, 151);
		panelInstructions2.add(knightmoves);

		// Add navigation buttons
		// Left Button
		JButton btnToPage1 = new JButton("<");

		btnToPage1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmChessGame.setBounds(575, 250, 700, 500);
				switchPanel(panelInstructions2, panelInstructions);
			}
		});

		btnToPage1.setBounds(478, 400, 42, 30);
		panelInstructions2.add(btnToPage1);

		// Main Menu Button
		JButton btnToMainMenu2 = new JButton("Main Menu");

		btnToMainMenu2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmChessGame.setSize(300, 300);
				switchPanel(panelInstructions2, panelTitleScreen);
			}
		});

		btnToMainMenu2.setBounds(530, 400, 100, 30);
		panelInstructions2.add(btnToMainMenu2);

		// Right Button
		JButton btnToPage3 = new JButton(">");

		btnToPage3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmChessGame.setBounds(575, 250, 700, 500);
				switchPanel(panelInstructions2, panelInstructions3);
			}
		});

		btnToPage3.setBounds(640, 400, 42, 30);
		panelInstructions2.add(btnToPage3);
		// PanelInstructions3
		panelInstructions3 = new JPanel();

		JLabel blackbishop;
		JLabel whitebishop;
		JLabel blackrook;
		JLabel whiterook;
		JLabel bishopmoves;
		JLabel rookmoves;

		panelInstructions3.setBackground(Color.WHITE);
		panelInstructions3.setVisible(false);
		panelInstructions3.setLayout(null);
		frmChessGame.getContentPane().add(panelInstructions3);

		// Content Information CONT2.
		JLabel lbltip25 = new JLabel("Movement and Standard Valuations");
		lbltip25.setFont(new Font("Centaur", Font.BOLD, 28));
		lbltip25.setBounds(10, 5, 700, 50);
		panelInstructions3.add(lbltip25);
		// Bishop
		JLabel lbltip26 = new JLabel("Bishop - Value : 3");
		lbltip26.setFont(new Font("Centaur", Font.PLAIN, 16));
		lbltip26.setBounds(128, 60, 700, 50);
		panelInstructions3.add(lbltip26);

		JLabel lbltip27 = new JLabel("Movement: May advance diagonally in any direction, through any number of");
		lbltip27.setFont(new Font("Centaur", Font.PLAIN, 14));
		lbltip27.setBounds(250, 60, 700, 50);
		panelInstructions3.add(lbltip27);

		JLabel lbltip33 = new JLabel("of unoccupied squares.");
		lbltip33.setFont(new Font("Centaur", Font.PLAIN, 14));
		lbltip33.setBounds(313, 75, 700, 50);
		panelInstructions3.add(lbltip33);

		JLabel lbltip28 = new JLabel("*NOTE: Because they are two bishops, only one can capture pieces on");
		lbltip28.setFont(new Font("Centaur", Font.PLAIN, 14));
		lbltip28.setBounds(313, 90, 700, 50);
		panelInstructions3.add(lbltip28);

		JLabel lbltip29 = new JLabel("white checkers while the other one can only capture on black checkers");
		lbltip29.setFont(new Font("Centaur", Font.PLAIN, 14));
		lbltip29.setBounds(313, 105, 700, 50);
		panelInstructions3.add(lbltip29);

		// Rook
		JLabel lbltip30 = new JLabel("Rook - Value : 5");
		lbltip30.setFont(new Font("Centaur", Font.PLAIN, 16));
		lbltip30.setBounds(128, 250, 700, 50);
		panelInstructions3.add(lbltip30);

		JLabel lbltip31 = new JLabel("Movement: May advance horizontally or vertically in any direction, through");
		lbltip31.setFont(new Font("Centaur", Font.PLAIN, 14));
		lbltip31.setBounds(250, 250, 700, 50);
		panelInstructions3.add(lbltip31);

		JLabel lbltip32 = new JLabel("any number of unoccupied squares ");
		lbltip32.setFont(new Font("Centaur", Font.PLAIN, 14));
		lbltip32.setBounds(313, 265, 700, 50);
		panelInstructions3.add(lbltip32);

		// Pawn & Knight Images
		whitebishop = new JLabel(new ImageIcon(WBISHOP));
		whitebishop.setBounds(0, 50, 64, 64);
		panelInstructions3.add(whitebishop);

		blackbishop = new JLabel(new ImageIcon(BBISHOP));
		blackbishop.setBounds(64, 50, 64, 64);
		panelInstructions3.add(blackbishop);

		bishopmoves = new JLabel(new ImageIcon(BISHOPMOVEMENT));
		bishopmoves.setBounds(220, 120, 150, 151);
		panelInstructions3.add(bishopmoves);

		whiterook = new JLabel(new ImageIcon(WROOK));
		whiterook.setBounds(0, 240, 64, 64);
		panelInstructions3.add(whiterook);

		blackrook = new JLabel(new ImageIcon(BROOK));
		blackrook.setBounds(64, 245, 64, 64);
		panelInstructions3.add(blackrook);

		rookmoves = new JLabel(new ImageIcon(ROOKMOVEMENT));
		rookmoves.setBounds(220, 295, 150, 151);
		panelInstructions3.add(rookmoves);

		// Add navigation buttons
		// Left Button
		JButton btnToPageTwo = new JButton("<");

		btnToPageTwo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmChessGame.setBounds(575, 250, 700, 500);
				switchPanel(panelInstructions3, panelInstructions2);
			}
		});

		btnToPageTwo.setBounds(478, 400, 42, 30);
		panelInstructions3.add(btnToPageTwo);

		// Main Menu Button
		JButton btnToMainMenu3 = new JButton("Main Menu");

		btnToMainMenu3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmChessGame.setSize(300, 300);
				switchPanel(panelInstructions3, panelTitleScreen);
			}
		});

		btnToMainMenu3.setBounds(530, 400, 100, 30);
		panelInstructions3.add(btnToMainMenu3);

		// Right Button
		JButton btnToPage4 = new JButton(">");

		btnToPage4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmChessGame.setBounds(575, 250, 700, 500);
				switchPanel(panelInstructions3, panelInstructions4);
			}
		});

		btnToPage4.setBounds(640, 400, 42, 30);
		panelInstructions3.add(btnToPage4);
		// PanelInstructions4
		panelInstructions4 = new JPanel();

		JLabel blackqueen;
		JLabel whitequeen;
		JLabel blackking;
		JLabel whiteking;
		JLabel queenmoves;
		JLabel kingmoves;

		panelInstructions4.setBackground(Color.WHITE);
		panelInstructions4.setVisible(false);
		panelInstructions4.setLayout(null);
		frmChessGame.getContentPane().add(panelInstructions4);

		// Content Information CONT.
		JLabel lbltip43 = new JLabel("Movement and Standard Valuations");
		lbltip43.setFont(new Font("Centaur", Font.BOLD, 28));
		lbltip43.setBounds(10, 5, 700, 50);
		panelInstructions4.add(lbltip43);
		// Queen
		JLabel lbltip34 = new JLabel("Queen - Value : 9");
		lbltip34.setFont(new Font("Centaur", Font.PLAIN, 16));
		lbltip34.setBounds(128, 60, 700, 50);
		panelInstructions4.add(lbltip34);

		JLabel lbltip35 = new JLabel("Movement: May advance any number of unoccupied squares in a straight line");
		lbltip35.setFont(new Font("Centaur", Font.PLAIN, 14));
		lbltip35.setBounds(250, 60, 700, 50);
		panelInstructions4.add(lbltip35);

		JLabel lbltip36 = new JLabel(" vertically, horizontally, or diagonally");
		lbltip36.setFont(new Font("Centaur", Font.PLAIN, 14));
		lbltip36.setBounds(313, 75, 700, 50);
		panelInstructions4.add(lbltip36);

		JLabel lbltip37 = new JLabel("*NOTE: The Queen is the most powerful, dynamic, versatile piece");
		lbltip37.setFont(new Font("Centaur", Font.PLAIN, 14));
		lbltip37.setBounds(313, 90, 700, 50);
		panelInstructions4.add(lbltip37);

		// King
		JLabel lbltip38 = new JLabel("King - Value : \u221e");
		lbltip38.setFont(new Font("Centaur", Font.PLAIN, 16));
		lbltip38.setBounds(128, 250, 700, 50);
		panelInstructions4.add(lbltip38);

		JLabel lbltip39 = new JLabel("Movement: May advance one square in any direction");
		lbltip39.setFont(new Font("Centaur", Font.PLAIN, 14));
		lbltip39.setBounds(250, 250, 700, 50);
		panelInstructions4.add(lbltip39);

		JLabel lbltip42 = new JLabel("*NOTE: Because you lose if this piece is captured, the value of this piece");
		lbltip42.setFont(new Font("Centaur", Font.PLAIN, 14));
		lbltip42.setBounds(313, 265, 700, 50);
		panelInstructions4.add(lbltip42);

		JLabel lbltip40 = new JLabel("is \u221e");
		lbltip40.setFont(new Font("Centaur", Font.PLAIN, 14));
		lbltip40.setBounds(363, 280, 700, 50);
		panelInstructions4.add(lbltip40);

		// Pawn & Knight Images
		whitequeen = new JLabel(new ImageIcon(WQUEEN));
		whitequeen.setBounds(0, 50, 64, 64);
		panelInstructions4.add(whitequeen);

		blackqueen = new JLabel(new ImageIcon(BQUEEN));
		blackqueen.setBounds(64, 50, 64, 64);
		panelInstructions4.add(blackqueen);

		queenmoves = new JLabel(new ImageIcon(QUEENMOVEMENT));
		queenmoves.setBounds(220, 110, 150, 151);
		panelInstructions4.add(queenmoves);

		whiteking = new JLabel(new ImageIcon(WKING));
		whiteking.setBounds(0, 240, 64, 64);
		panelInstructions4.add(whiteking);

		blackking = new JLabel(new ImageIcon(BKING));
		blackking.setBounds(64, 240, 64, 64);
		panelInstructions4.add(blackking);

		kingmoves = new JLabel(new ImageIcon(KINGMOVEMENT));
		kingmoves.setBounds(220, 300, 150, 151);
		panelInstructions4.add(kingmoves);

		// Add navigation buttons
		// Left Button
		JButton btnToPageThree = new JButton("<");

		btnToPageThree.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmChessGame.setBounds(575, 250, 700, 500);
				switchPanel(panelInstructions4, panelInstructions3);
			}
		});

		btnToPageThree.setBounds(478, 400, 42, 30);
		panelInstructions4.add(btnToPageThree);

		// Main Menu Button
		JButton btnToMainMenu4 = new JButton("Main Menu");

		btnToMainMenu4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmChessGame.setSize(300, 300);
				switchPanel(panelInstructions4, panelTitleScreen);
			}
		});

		btnToMainMenu4.setBounds(530, 400, 100, 30);
		panelInstructions4.add(btnToMainMenu4);

		// This panel contain the high scores from read from a text file (1
		// button, 2 text fields)
		panelHighScore = new JPanel();

		panelHighScore.setVisible(false);
		panelHighScore.setBackground(Color.WHITE);
		panelHighScore.setLayout(null);
		frmChessGame.getContentPane().add(panelHighScore);

		JLabel lblUsername = new JLabel("Username");
		lblUsername.setFont(new Font("Centaur", Font.PLAIN, 36));
		lblUsername.setBounds(21, 20, 200, 50);
		panelHighScore.add(lblUsername);

		// Score (Time)
		JLabel lblScore = new JLabel("# of Wins");
		lblScore.setFont(new Font("Centaur", Font.PLAIN, 36));
		lblScore.setBounds(244, 20, 200, 50);
		panelHighScore.add(lblScore);

		// Main Menu Button
		JButton btnMainMenu2 = new JButton("Main Menu");
		btnMainMenu2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmChessGame.setSize(300, 300);
				switchPanel(panelHighScore, panelTitleScreen);
			}
		});
		btnMainMenu2.setBounds(515, 207, 112, 37);
		panelHighScore.add(btnMainMenu2);

		// User name list for high scores
		namesModel = new DefaultListModel<String>();
		lstNames = new JList<String>(namesModel);
		lstNames.setEnabled(false);
		lstNames.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		lstNames.setBackground(Color.WHITE);
		lstNames.setBounds(21, 81, 200, 333);
		panelHighScore.add(lstNames);

		// Scores list for high scores
		scoresModel = new DefaultListModel<Integer>();
		lstScores = new JList<Integer>(scoresModel);
		lstScores.setEnabled(false);
		lstScores.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		lstScores.setBackground(Color.WHITE);
		lstScores.setBounds(244, 81, 200, 333);
		panelHighScore.add(lstScores);

		highScores = new ArrayList<>();
		loadHighscore();
	}// End of initialize()

	// This methods will allow buttons to switch between panels
	private void switchPanel(JPanel from, JPanel to) {
		from.setVisible(false);
		to.setVisible(true);

		frmChessGame.setLocationRelativeTo(null);
	} // End of switchPanel()
	
	private void saveHighScores() {
		PrintWriter writer;
		try {
			writer = new PrintWriter(new BufferedWriter(new FileWriter("src/highScore")));
			
			for (Score s : highScores) {
				writer.printf("%s %d", s.getName(), s.getScore());
				writer.println();
			}
			
			writer.close();
		} catch (IOException e) {
			System.err.println(e);
		}
	}	//End of HighScores()
	
	//Imports the high scores from its text file
	private void loadHighscore() {
		try {
			Scanner read = new Scanner(new File("src/highScore"));
			while (read.hasNext()) {
				highScores.add(new Score(read.next(), read.nextInt()));
			}
			
			read.close();
		} catch(Exception e) {
			System.err.println(e);
		}	
	}	//End of loadHighscore()
	
	private void putScoresIntoList() {
		namesModel.clear();
		scoresModel.clear();
		
		Collections.sort(highScores);
		for (Score s : highScores) {
			namesModel.addElement(s.getName());
			scoresModel.addElement(s.getScore());
		}
	}

}// End of chess game class
