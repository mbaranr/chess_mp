import javax.swing.JFrame;

public class GameFrame extends JFrame{
	
	private static final int FRAME_WIDTH = 855;			// frame
	private static final int FRAME_HEIGHT = 679;		// dimensions
	
	GamePanel panel;
	
	UIPanel uipanel;
	
	PiecesPanel piecespanel;
	
	// constructor
	
	public GameFrame() {
		
		uipanel = new UIPanel();						//creating
		piecespanel = new PiecesPanel();				//the 
		panel = new GamePanel(uipanel, piecespanel);	//3 panels for the frame
		
		//setting panel bounds
		
		panel.setBounds(0, 0, panel.getPanelWidth(), panel.getPanelHeight());				
		uipanel.setBounds(panel.getPanelWidth(), 0, uipanel.getPanelWidth(), uipanel.getPanelHeight());
		piecespanel.setBounds(panel.getPanelWidth(), uipanel.getPanelHeight(), piecespanel.getPanelWidth(), piecespanel.getPanelHeight());;
		
		setSize(FRAME_WIDTH, FRAME_HEIGHT);				//setting the frame to the constant values declared as properties
		setTitle("Chess");								// setting a title to the frame
		setLocationRelativeTo(null);					//setting frame location to the middle of the screen
		setLayout(null);
		add(panel);										// adding a panel
		add(uipanel);
		add(piecespanel);
		setVisible(true);								// making the frame visible
		setResizable(false);							// disabling the frame to be resizable
		setDefaultCloseOperation(EXIT_ON_CLOSE);		
	
	}
		
}