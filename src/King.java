import java.util.ArrayList;

public class King extends Piece {
	// CONSTRUCTOR
	public King(char colour, Pair p) {
		super(colour, p);
	}

	public King(char colour, int x, int y) {
		super(colour, x, y);
	}

	@Override
	public String getName() {
		return "King";
	}

	@Override
	public void move() {

	}

	@Override
	public ArrayList<Pair> getLegalMoves(Piece p, Board b) {
		ArrayList<Pair> legalMoves = new ArrayList<>();
		int tempX, tempY;

		// Moving forwards
		tempX = p.getX() + p.getDirection();
		tempY = p.getY();
		if (b.isWithinBounds(tempX, tempY) && (b.isCellEmpty(tempX, tempY) || b.isCapturable(p, tempX, tempY))) {
			legalMoves.add(new Pair(tempX, tempY));
		}

		// Moving backwards
		tempX = p.getX() - p.getDirection();
		tempY = p.getY();
		if (b.isWithinBounds(tempX, tempY) && (b.isCellEmpty(tempX, tempY) || b.isCapturable(p, tempX, tempY))) {
			legalMoves.add(new Pair(tempX, tempY));
		}

		// Moving right
		tempX = p.getX();
		tempY = p.getY() + p.getDirection();
		if (b.isWithinBounds(tempX, tempY) && (b.isCellEmpty(tempX, tempY) || b.isCapturable(p, tempX, tempY))) {
			legalMoves.add(new Pair(tempX, tempY));
		}

		// Moving left
		tempX = p.getX();
		tempY = p.getY() - p.getDirection();
		if (b.isWithinBounds(tempX, tempY) && (b.isCellEmpty(tempX, tempY) || b.isCapturable(p, tempX, tempY))) {
			legalMoves.add(new Pair(tempX, tempY));
		}

		// Moving north-east
		tempX = p.getX() - p.getDirection();
		tempY = p.getY() + p.getDirection();
		if (b.isWithinBounds(tempX, tempY) && (b.isCellEmpty(tempX, tempY) || b.isCapturable(p, tempX, tempY))) {
			legalMoves.add(new Pair(tempX, tempY));
		}

		// Moving south-east
		tempX = p.getX() + p.getDirection();
		tempY = p.getY() + p.getDirection();
		if (b.isWithinBounds(tempX, tempY) && (b.isCellEmpty(tempX, tempY) || b.isCapturable(p, tempX, tempY))) {
			legalMoves.add(new Pair(tempX, tempY));
		}

		// Moving north-west
		tempX = p.getX() - p.getDirection();
		tempY = p.getY() - p.getDirection();
		if (b.isWithinBounds(tempX, tempY) && (b.isCellEmpty(tempX, tempY) || b.isCapturable(p, tempX, tempY))) {
			legalMoves.add(new Pair(tempX, tempY));
		}

		// Moving south-west
		tempX = p.getX() + p.getDirection();
		tempY = p.getY() - p.getDirection();
		if (b.isWithinBounds(tempX, tempY) && (b.isCellEmpty(tempX, tempY) || b.isCapturable(p, tempX, tempY))) {
			legalMoves.add(new Pair(tempX, tempY));
		}

		return legalMoves;
	}
}
