/** This is the GUI of the chess game.
 *  @author Zachary Tan 
 *  @version 6/13/2016
 */ 

//Imports
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
public class ChessGame {
	//The entirety of the chess game
	private JFrame frmChessGame;
 
	//The panels
	private JPanel panelTitleScreen;
	private JPanel panelBoardGame;
	private JPanel panelInstructions;
	private JPanel panelHighScore;
 
	//Runs the chess game
	public static void main(String[] args) {
		try {
			ChessGame window = new ChessGame();
			window.frmChessGame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}  // End of main method
 
 
 //METHODS
	// Switch to leader boards screen
	public ChessGame(){
		initialize();
	}
 
//The settings  and layout for each panel
	public void initialize(){
		//Initialize frame (1)
		frmChessGame = new JFrame();
		Image im = Toolkit.getDefaultToolkit().getImage("src/Chess/black_knight.png");
   
			frmChessGame.setTitle("Chess Game");
			frmChessGame.setBounds(100, 100, 800, 550);
			frmChessGame.setLocationRelativeTo(null);
			frmChessGame.setIconImage(im);
			frmChessGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frmChessGame.getContentPane().setLayout(new CardLayout(0, 0));
   
		//Initialize panels (4)
   
		//This is the central hub where the user connects with other panels (4 buttons)
		panelTitleScreen = new JPanel();
			panelTitleScreen.setBackground(Color.WHITE);
			panelTitleScreen.setVisible(true);
			panelTitleScreen.setPreferredSize(new Dimension(100, 100));
			panelTitleScreen.setLayout(null);
			frmChessGame.getContentPane().add(panelTitleScreen);
   
		//Title
		JLabel lblTitle = new JLabel("Classic Chess");
			lblTitle.setFont(new Font("Centaur", Font.PLAIN, 36));
			lblTitle.setBounds(155, 137, 431, 50);
			panelTitleScreen.add(lblTitle);
	
		//Play Game Button
		JButton btnStartButton = new JButton("Start Game");
		//btnStartButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
		
		btnStartButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchPanel(panelTitleScreen, panelBoardGame);
			}
		});
   
		btnStartButton.setBounds(279, 219, 139, 23);
		panelTitleScreen.add(btnStartButton);
		
		//Instruction button
		JButton btnToInstruction = new JButton("Instructions");
		
		btnToInstruction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchPanel(panelTitleScreen, panelInstructions);
			}
		});
		
	    btnToInstruction.setBounds(279, 253, 139, 23);
	    panelTitleScreen.add(btnToInstruction);
	    
		//LeaderBoard Button
		JButton btnLeaderboard = new JButton("Leaderboard");
		
		btnLeaderboard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchPanel(panelTitleScreen, panelHighScore);
			}
		});
		
		btnLeaderboard.setBounds(279, 287, 139, 23);
		panelTitleScreen.add(btnLeaderboard);
		
		//Exit Button
		JButton btnExit = new JButton("Exit");
		
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		btnExit.setBounds(279, 320, 139, 23);
		panelTitleScreen.add(btnExit);


   
   //This panel contains the chess game itself (Chess board, 2 buttons, 2 timers)
   panelBoardGame = new JPanel();
   		panelBoardGame.setVisible(false);
   		panelBoardGame.setBackground(Color.WHITE);
		panelBoardGame.setPreferredSize(new Dimension(100, 100));
		panelBoardGame.setLayout(null);
		frmChessGame.getContentPane().add(panelBoardGame);
			
			//Chess Board Test
			ImageIcon iconLogo = new ImageIcon("src/Chess/chessboard.png");
			JLabel chessboard = new JLabel();
			chessboard.setIcon(iconLogo);
			chessboard.setBounds(1, 1, 512, 512);
			panelBoardGame.add(chessboard);
			
		
			//Pawn Test
			ImageIcon iconLogo1 = new ImageIcon("src/Chess/black_pawn.png");
			JLabel pawn = new JLabel();
			pawn.setIcon(iconLogo1);
			pawn.setBounds(400, 137, 64, 64);
			panelBoardGame.add(pawn);
			
   //This panel contains information on each piece (3 buttons)
   panelInstructions = new JPanel();
   		panelInstructions.setVisible(false);
   
   //This panel contain the high scores from read from a text file (1 button, 2 text fields)
   panelHighScore = new JPanel();
   panelHighScore.setVisible(false);
   
  }
	private void switchPanel(JPanel from, JPanel to) {
		from.setVisible(false);
		to.setVisible(true);
	} 	//End of switchPanel()
 
}