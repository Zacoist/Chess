import java.util.ArrayList;

public abstract class Piece {
	private char colour;
	private Pair coord;

	public Piece() {
		this.colour = 'w';
		this.coord = null;
	}

	public Piece(char colour, Pair p) {
		this.colour = colour;
		this.coord = p;
	}

	public Piece(char colour, int x, int y) {
		this.colour = colour;
		this.coord = new Pair(x, y);
	}

	// METHODS
	public char getColour() {
		return colour;
	}

	public Pair getPair() {
		return coord;
	}

	public int getX() {
		return coord.x;
	}

	public int getY() {
		return coord.y;
	}

	public void moveTo(int x, int y) {
		coord.x = x;
		coord.y = y;
	}

	public int getDirection() {
		return ((colour == 'b') ? +1 : -1);
	}

	public abstract String getName();

	public abstract ArrayList<Pair> getLegalMoves(Piece p, Board b);

	public abstract void move();
}
