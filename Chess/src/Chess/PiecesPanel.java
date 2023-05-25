package Chess;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

//panel to print the taken pieces
public class PiecesPanel extends JPanel{
	
	private static final int PANEL_WIDTH = 200;											// panel dimensions
	private static final int PANEL_HEIGHT = 400;
	
	boolean reset;																		// reset flag
	
	Board board;
	
	Image image;
	
	Graphics graphics;
	
	//constructor
	public PiecesPanel(){
		//initializing reset to false
		reset = false;
		this.setLayout(null);
		this.setSize(PANEL_WIDTH, PANEL_HEIGHT);
		this.setFocusable(true);	
		this.setVisible(true);
	
	}
	
	//paint method, panel will act as a canvas
	public void paint(Graphics g) {
		image = createImage(getWidth(), getHeight());
		graphics = image.getGraphics();
		draw(graphics);																	// calling draw method
		g.drawImage(image,0,0,this);
	}
	
	//draw method, iterate through the linked lists of taken pieces and display the contents accordingly
	public void draw(Graphics g) {
		
		if(reset || board == null) {
			return;
		}
		
		int y = 0;
		
		int x = 10;
		
		//iterate through taken white pieces linked list
		for(int i = 0; i < board.takenWhitePiecesLen(); i++) {
			
			if (i != 0 && i % 4 == 0) {
				y += 50;
				x = 10;
			}

			g.drawImage(board.getTakenWhitePieces(i).sprite, x, y, 40, 40, null);
			
			x += 48;
		}
		
		x = 10;
		y += 50;
		
		//iterate through taken black pieces linked list
		for(int i = 0; i < board.takenBlackPiecesLen(); i++) {
			
			if (i != 0 && i % 4 == 0) {
				y += 50;
				x = 10;
			}
			
			g.drawImage(board.getTakenBlackPieces(i).sprite, x, y, 40, 40, null);
			
			x += 48;
				
		}
	}
	
	
	public int getPanelWidth() {
		return PANEL_WIDTH;
	}
	
	public int getPanelHeight() {
		return PANEL_HEIGHT;
	}
	
}
