
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
  public abstract void move();
}
