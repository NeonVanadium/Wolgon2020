package game;

import java.awt.Color;

public class TitlePanel extends AWolgonPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TitlePanel() {
		
		getZone("WHOLE").splitHorizontally(1.0/2.0, "UPPER", "LOWER");
		
		new TypewriterLabel("TITLE", "W O L G O N", Color.MAGENTA, 100f, 
				AlignmentLocation.HCenter, AlignmentLocation.VCenter, "WHOLE", this);
		new Button("START_BUTTON", "begin", 20f, 
				AlignmentLocation.HCenter, AlignmentLocation.Top, "LOWER", this, 
				new ButtonFunction() { public void run() { Main.setPanel(new MenuPanel()); } });
		
	}
	
	public void update() {
		repaint();
	}

}
