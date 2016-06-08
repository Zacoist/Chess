import java.util.ArrayList;

public class Queen extends Piece {
	public Queen(char colour){
		super(colour);
	}

	@Override
	public String getName() {
		return "Queen";
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub
		
	}
	public ArrayList<Pair> getLegalMoves(Pair p, Piece[][] b, int direction) {
		ArrayList<Pair> legalMoves = new ArrayList<>();
		//Moving forward
		if (0 <= p.x - direction && p.x - direction < 8) {
			//Calculate the top part
			for(int t = 0; t < (8 - (p.y + 1)); t++){
				legalMoves.add(new Pair(p.x - t, p.y));
			}
			//Calculate the bottom part
			for(int t = 0; t < (p.y); t++){
				legalMoves.add(new Pair(p.x + t, p.y));
			}
		}
		//Moving diagonally (Right)
		if (0 <= p.x - direction && p.x - direction < 8 && 0 <= p.y + direction && p.y + direction < 8) {
			
		}
		//Moving diagonally (Left)
		if (0 <= p.x - direction && p.x - direction < 8 && 0 <= p.y - direction && p.y - direction < 8) {
			//for (int t = 0; t < 8; t++){
			//	legalMoves.add(new Pair(p.x - t * direction, p.y - t * direction));
			//}
		}
		
		return legalMoves;
	}
}
