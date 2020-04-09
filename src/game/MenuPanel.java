package game;

import java.awt.Color;
import java.awt.event.KeyEvent;

public class MenuPanel extends AWolgonPanel {

	private static final long serialVersionUID = 1L;

	public MenuPanel() {
		super();
		
		/*addZone("Info", new Zone(1/2.0, 0, "Whole", this));
		addZone("Sidebar", new Zone(0, 0, "Whole", this));*/
		
		getZone("WHOLE").splitVertically(1.0/2.0, "LEFT", "RIGHT");
		getZone("LEFT").splitHorizontally(1.0/5.0, "WHITESPACE", "MAINBAR");
		
		ButtonFunction startGame = new ButtonFunction() { 
			public void run() { Main.setPanel(new WorldPanel()); }
		};		
		
		new Label("MENU_TITLE", "Main Menu", Color.WHITE, 50f, 
				AlignmentLocation.Left, AlignmentLocation.Top, "MAINBAR", this);
		new Button("START_BUTTON", "New Game", 30f, "MENU_TITLE", this, startGame, "Begin a new game.");
		
		new Label("HOVER_TEXT", "", Color.WHITE, 30f, AlignmentLocation.Left, AlignmentLocation.VCenter, 
				"RIGHT", this);
		
		this.hoverTextBox = getLabel("HOVER_TEXT");
	}

	public void update() {
		repaint();
	}

}
