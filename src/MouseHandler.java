import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

//class handling mouse behavior
public class MouseHandler extends MouseAdapter {
	
	private Board board;
	
	//variables for getting the pressed and released rows and columns
	int PressedC,PressedR, ReleasedC, ReleasedR;
	
	public MouseHandler(Board board) {
		this.board = board;
	}
	
	public void mouseDragged(MouseEvent e) {
		
		//change the pressed piece x and y position to display it next to the mouse while being dragged
		if (board.pieceToMove != null) {
			board.pieceToMove.x = e.getX() - board.getTilesize() / 2;
			board.pieceToMove.y = e.getY() - board.getTilesize() / 2;
		}
		
	}

	public void mousePressed(MouseEvent e) {
		
		//getting the row and col pressed
		PressedR = e.getX() / board.getTilesize();
		PressedC = e.getY() / board.getTilesize();
		
		board.pieceToMove = board.getPiece(PressedR, PressedC);
		
	}

	public void mouseReleased(MouseEvent e) {
		
		//defensive programming to avoid out of bounds exception
		if(e.getX() < 0 || e.getX() > 640 || e.getY() < 0 || e.getY() > 640) {
			
			board.pieceToMove.x = board.pieceToMove.row * board.getTilesize();
			board.pieceToMove.y = board.pieceToMove.col * board.getTilesize();
			board.pieceToMove = null;
			
			return;
		}
		
		//getting the released row and col
		ReleasedR = e.getX() / board.getTilesize();
		ReleasedC = e.getY() / board.getTilesize();
		
		if (board.pieceToMove != null) {
			
			//if the move was valid, make the move
			if(board.isValidMove(board.pieceToMove, ReleasedR, ReleasedC)){
				board.makeMove(board.pieceToMove, ReleasedR, ReleasedC);
			//if not, return the piece to its original position
			}else {
				board.pieceToMove.x = board.pieceToMove.row * board.getTilesize();
				board.pieceToMove.y = board.pieceToMove.col * board.getTilesize();
				board.pieceToMove = null;
			}
		}
		
	}

	
}
