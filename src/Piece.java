import java.util.ArrayList;

public abstract class Piece {
	// Initialize variables
	private char colour;
	private Pair coord;

	/* CONSTRUCTOR */
	// Default constructor
	public Piece() {
		this.colour = 'w';
		this.coord = null;
	}

	// every piece must have a colour and a pair of coordinates
	public Piece(char colour, Pair p) {
		this.colour = colour;
		this.coord = p;
	}

	// every piece must have a colour and an X or Y coordinate
	public Piece(char colour, int x, int y) {
		this.colour = colour;
		this.coord = new Pair(x, y);
	}
	
	public Piece(Piece p) {
		this.colour = p.getColour();
		this.coord = new Pair(p.getX(), p.getY());
	}
	/* END OF CONSTRUCTOR */

	/* METHODS */
	// Return the colour of the piece
	public char getColour() {
		return colour;
	}// End of getColour()

	// Returns a set of coordinates (x, y)
	public Pair getPair() {
		return coord;
	}// End of getPair()

	// Returns the x-coordinate of a piece
	public int getX() {
		return coord.x;
	}// End of getX()

	// Returns the y-coordinate of a piece
	public int getY() {
		return coord.y;
	}// End of getY()
	
	public void setX(int x) {
		coord.x = x;
	}// End of getX()

	// Returns the y-coordinate of a piece
	public void setY(int y) {
		coord.x = y;
	}// End of getY()

	// Moves the piece to a new x and y coordinate
	public void moveTo(int x, int y) {
		coord.x = x;
		coord.y = y;
	}// End of moveTo()

	// Returns the user a int that changes from positive to negative to allow a
	// change or direction in calculations depending on the colour
	public int getDirection() {
		return ((colour == 'b') ? +1 : -1);
	}// End of getDirection

	// ABSTRACT METHODS - Every derived class must have
	// Every class that inherits piece must be able to return its own name
	public abstract String getName();

	// Every class that inherits piece must have a set of possible moves
	// relative to where they are on the board
	public abstract ArrayList<Pair> getLegalMoves(Piece p, Board b);
}// End of piece class
