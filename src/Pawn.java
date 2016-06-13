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
	
	public Pawn(Pawn p) {
		super(p);

		startState = p.getStartState();
	}

	@Override
	public String getName() {
		return "Pawn";
	}
	
	public boolean getStartState() {
		return startState;
	}

	@Override
	public void moveTo(int x, int y) {
		super.moveTo(x, y);
		
		startState = false;
	}// End of moveTo()

	@Override
	public ArrayList<Pair> getLegalMoves(Piece p, Board b) {
		ArrayList<Pair> legalMoves = new ArrayList<>();
		int tempX, tempY;

		// The user can move a pawn piece up if it hasn't been moved
		if (startState == true) {
			tempX = p.getX() + (p.getDirection() * 2);
			tempY = p.getY();
			if (b.isWithinBounds(tempX, tempY) && (b.isCellEmpty(tempX, tempY))) {
				legalMoves.add(new Pair(tempX, tempY));
			}
		}
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
