package game;

import java.awt.Color;

public class MenuPanel extends AWolgonPanel {
	
	public MenuPanel() {
		super();
		
		this.setBackground(Color.DARK_GRAY);
		
		addZone("Main", new Zone(1/6.0, 1/6.0, "Whole", this));
		
		new Label("MENU_TITLE", "It's a whole menu.", Color.WHITE, 30f, 
				AlignmentLocation.Left, AlignmentLocation.Top, "Main", this);
	}

	public void update() {
		repaint();
	}

}
