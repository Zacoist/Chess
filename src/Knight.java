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
		ArrayList<Pair> legalMoves = new ArrayList<>();
		int tempX, tempY;
		
		//Forward(left)
		tempX = p.getX() + (2 * p.getDirection());
		tempY = p.getY() + p.getDirection();
		if (b.isWithinBounds(tempX, tempY) && b.isCapturable(p, tempX, tempY) && (b.isCellEmpty(tempX, tempY))) {
			legalMoves.add(new Pair(tempX, tempY));
		}
		
		//Forward(right)
		tempX = p.getX() + (2 * p.getDirection());
		tempY = p.getY() - p.getDirection();
		if (b.isWithinBounds(tempX, tempY) && b.isCapturable(p, tempX, tempY)) {
			legalMoves.add(new Pair(tempX, tempY));
		}
		
		//Backward(left)
		tempX = p.getX() - (2 * p.getDirection());
		tempY = p.getY() + p.getDirection();
		if (b.isWithinBounds(tempX, tempY) && b.isCapturable(p, tempX, tempY)) {
			legalMoves.add(new Pair(tempX, tempY));
		}
				
		//Forward(right)
		tempX = p.getX() - (2 * p.getDirection());
		tempY = p.getY() - p.getDirection();
		if (b.isWithinBounds(tempX, tempY) && b.isCapturable(p, tempX, tempY)) {
			legalMoves.add(new Pair(tempX, tempY));
		}
		
		//Left(Up)
		tempX = p.getX() -  p.getDirection();
		tempY = p.getY() - (2 * p.getDirection());
		if (b.isWithinBounds(tempX, tempY) && b.isCapturable(p, tempX, tempY)) {
			legalMoves.add(new Pair(tempX, tempY));
		}
		
		//Left(Down)
		tempX = p.getX() +  p.getDirection();
		tempY = p.getY() - (2 * p.getDirection());
		if (b.isWithinBounds(tempX, tempY) && b.isCapturable(p, tempX, tempY)) {
			legalMoves.add(new Pair(tempX, tempY));
		}
		
		//Right(Up)
		tempX = p.getX() - p.getDirection();
		tempY = p.getY() + (2 * p.getDirection());
		if (b.isWithinBounds(tempX, tempY) && b.isCapturable(p, tempX, tempY)) {
			legalMoves.add(new Pair(tempX, tempY));
		}
				
		//Right(Down)
		tempX = p.getX() + (p.getDirection());
		tempY = p.getY() + (2 * p.getDirection());
		if (b.isWithinBounds(tempX, tempY) && b.isCapturable(p, tempX, tempY)) {
			legalMoves.add(new Pair(tempX, tempY));
		}
		/*
		tempX = p.getX() + p.getDirection();
		tempY = p.getY() - p.getDirection();
		if (b.isWithinBounds(tempX, tempY) && b.isCapturable(p, tempX, tempY)) {
			legalMoves.add(new Pair(tempX, tempY));
		}
		*/

		return legalMoves;
	}

}
