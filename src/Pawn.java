import java.util.ArrayList;

public class Pawn extends Piece {
	public Pawn(char colour){
		super(colour);
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
	public ArrayList<Pair> getLegalMoves(Pair p, Piece[][] b, int direction) {
		ArrayList<Pair> legalMoves = new ArrayList<>();
		if (0 <= p.x - direction && p.x - direction < 8) {
			legalMoves.add(new Pair(p.x - direction, p.y));
		}
		if (0 <= p.x - direction && p.x - direction < 8 && 0 <= p.y + direction && p.y + direction < 8) {
			legalMoves.add(new Pair(p.x - direction, p.y + direction));
		}
		if (0 <= p.x - direction && p.x - direction < 8 && 0 <= p.y - direction && p.y - direction < 8) {
			legalMoves.add(new Pair(p.x - direction, p.y - direction));
		}
		
		return legalMoves;
	}
}
