import java.util.ArrayList;

public class Queen extends Piece {
	public Queen(char colour, Pair p) {
		super(colour, p);
	}

	public Queen(char colour, int x, int y) {
		super(colour, x, y);
	}
	
	public Queen(Queen q) {
		super(q);
	}

	@Override
	public String getName() {
		return "Queen";
	}

	private void helper(ArrayList<Pair> moves, Piece p, Board b, int x, int y, int x_inc, int y_inc) {
		while (b.isWithinBounds(x, y)) {
			if (!b.isCellEmpty(x, y)) {
				if (b.isCapturable(p, x, y)) {
					moves.add(new Pair(x, y));
				}

				break;
			} else {
				moves.add(new Pair(x, y));
			}

			x += x_inc * p.getDirection();
			y += y_inc * p.getDirection();
		}

	}

	public ArrayList<Pair> getLegalMoves(Piece p, Board b) {
		ArrayList<Pair> legalMoves = new ArrayList<>();

		// Moving forwards
		helper(legalMoves, p, b, p.getX() + p.getDirection(), p.getY(), +1, 0);

		// Moving forwards
		helper(legalMoves, p, b, p.getX() - p.getDirection(), p.getY(), -1, 0);

		// Moving right
		helper(legalMoves, p, b, p.getX(), p.getY() + p.getDirection(), 0, +1);

		// Moving right
		helper(legalMoves, p, b, p.getX(), p.getY() - p.getDirection(), 0, -1);

		// Moving north-east
		helper(legalMoves, p, b, p.getX() - p.getDirection(), p.getY() + p.getDirection(), -1, +1);

		// Moving south-east
		helper(legalMoves, p, b, p.getX() + p.getDirection(), p.getY() + p.getDirection(), +1, +1);

		// Moving north-west
		helper(legalMoves, p, b, p.getX() - p.getDirection(), p.getY() - p.getDirection(), -1, -1);

		// Moving south-west
		helper(legalMoves, p, b, p.getX() + p.getDirection(), p.getY() - p.getDirection(), +1, -1);

		return legalMoves;
	}
}
