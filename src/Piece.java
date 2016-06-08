import java.util.ArrayList;

public abstract class Piece {
  private char colour;
  
  public Piece() {
	  this.colour = 'w';
  }
  
  public Piece(char colour) {
	  this.colour = colour;
  }
  
  //METHODS
  public char getColour(){
	  return colour;
  }
  
  public abstract String getName();
  public abstract ArrayList<Pair> getLegalMoves(Pair p, Piece[][] b, int direction);
  public abstract void move();
}
