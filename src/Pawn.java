import java.util.ArrayList;

public class Pawn extends Piece {
	boolean startState;

	public Pawn(char colour, Pair p) {
		super(colour, p);

		startState = true;
	}

	public Pawn(char colour, int x, int y) {
		super(colour, x, y);

		startState = true;
	}

	@Override
	public String getName() {
		return "Pawn";
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub

	}

	@Override
	public ArrayList<Pair> getLegalMoves(Piece p, Board b) {
		ArrayList<Pair> legalMoves = new ArrayList<>();
		int tempX, tempY;

		tempX = p.getX() + p.getDirection();
		tempY = p.getY();
		if (b.isWithinBounds(tempX, tempY) && (b.isCellEmpty(tempX, tempY))) {
			legalMoves.add(new Pair(tempX, tempY));
		}

		tempX = p.getX() + p.getDirection();
		tempY = p.getY() + p.getDirection();
		if (b.isWithinBounds(tempX, tempY) && b.isCapturable(p, tempX, tempY)) {
			legalMoves.add(new Pair(tempX, tempY));
		}

		tempX = p.getX() + p.getDirection();
		tempY = p.getY() - p.getDirection();
		if (b.isWithinBounds(tempX, tempY) && b.isCapturable(p, tempX, tempY)) {
			legalMoves.add(new Pair(tempX, tempY));
		}

		return legalMoves;
	}
}
