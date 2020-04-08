package game;

import java.awt.Color;
import java.awt.event.KeyEvent;

public class TitlePanel extends AWolgonPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TitlePanel() {
		
		addZone("Lower", new Zone(0, 3.0/5.0, "Whole", this));
		
		new TypewriterLabel("TITLE", "W O L G O N", Color.MAGENTA, 100f, 
				AlignmentLocation.HCenter, AlignmentLocation.VCenter, "Whole", this);
		new Button("START_BUTTON", "begin", 20f, 
				AlignmentLocation.HCenter, AlignmentLocation.Top, "Lower", this, 
				new ButtonFunction() { public void run() { Main.setPanel(new MenuPanel()); } });
		
	}
	
	public void update() {
		repaint();
	}
	
	public void keyHandler(KeyEvent e) {
		// nothing
	}

}
