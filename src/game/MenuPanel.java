package game;

import java.awt.Color;
import java.awt.event.KeyEvent;

public class MenuPanel extends AWolgonPanel {

	private static final long serialVersionUID = 1L;

	public MenuPanel() {
		super();
		
		addZone("Info", new Zone(1/2.0, 0, "Whole", this));
		addZone("Sidebar", new Zone(0, 0, "Whole", this));
		
		ButtonFunction startGame = new ButtonFunction() { 
			public void run() { Main.setPanel(new WorldPanel()); }
		};		
		
		new Label("MENU_TITLE", "Main Menu", Color.WHITE, 50f, 
				AlignmentLocation.Left, AlignmentLocation.Top, "Sidebar", this);
		new Button("START_BUTTON", "New Game", 30f, "MENU_TITLE", this, startGame, "Begin a new game.");
		
		new Label("HOVER_TEXT", "", Color.WHITE, 30f, AlignmentLocation.Left, AlignmentLocation.VCenter, 
				"Info", this);
		
		this.hoverTextBox = getLabel("HOVER_TEXT");
	}

	public void update() {
		repaint();
	}

	@Override
	public void keyHandler(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
