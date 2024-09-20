import java.awt.Graphics;
import java.awt.Image;

//parent abstract class from which every piece is inherits
public abstract class Piece {
	
	//properties
	
	private boolean isTaken;
	private boolean isWhite;
	
	char name;
	
	//row and col of the grid and x and y position of the panel
	public int row,col,x,y;
	
	//passing the board to get tile size
	Board board;
		
	Image sprite;
	
	//constructor
	public Piece(boolean isTaken, boolean isWhite, int row, int col, Board board, char name) {
		
		this.isTaken = isTaken;
		this.isWhite = isWhite;
		this.name = name;
		this.row = row;
		this.col = col;
		//poition calculated by multiplying the row and col times the tile size of the board
		x = board.getTilesize() * row;
		y = board.getTilesize() * col;
		this.board = board;
		
	}
	
	//default draw method
	public void draw(Graphics g) {
		g.drawImage(sprite, x, y, null);
	}
	
	//overwritten is valid move method
	public boolean isValidMove(int row, int col) {
		return false;
	}
	
	//overwritten move collided method
	public boolean moveCollided(int row, int col) {
		return false;
	}
	
	//check if two pieces are in the same team
	public boolean sameTeam(Piece piece) {
		
		if(piece.isWhite == this.isWhite) {
			return true;
		}
		return false;
	}
	
	//getters and setters
	
	public void setWhite(boolean a) {
		isWhite = a;
	}
	
	public boolean isTaken() {
		return isTaken;
	}

	public boolean isWhite() {
		return isWhite;
	}

	public Image getSprite() {
		return sprite;
	}

	public void setTaken(boolean a) {
		isTaken = a;
	}
	
}
