public class Pair {
	public int x;
	public int y;

	Pair(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
	
	@Override
	public boolean equals(Object obj) {
		Pair p = (Pair) obj;
		if (x == p.x && y == p.y) {
			return true;
		} else {
			return false;
		}
	}
	
	public int getX(int x, int y){
		return this.x;
	}
}
