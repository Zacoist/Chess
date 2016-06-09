import java.util.ArrayList;

public class Knight extends Piece {
	public Knight(char colour, Pair p) {
		super(colour, p);
	}

	public Knight(char colour, int x, int y) {
		super(colour, x, y);
	}

	@Override
	public String getName() {
		return "Knight";
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub

	}

	@Override
	public ArrayList<Pair> getLegalMoves(Piece p, Board b) {
		// TODO Auto-generated method stub
		return null;
	}
}
