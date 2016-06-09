import java.util.ArrayList;

public class Bishop extends Piece {
	public Bishop(char colour, Pair p) {
		super(colour, p);
	}

	public Bishop(char colour, int x, int y) {
		super(colour, x, y);
	}

	@Override
	public String getName() {
		return "Bishop";
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
