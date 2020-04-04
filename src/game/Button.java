package game;

import java.awt.Color;

class Button extends Label {

	public Button(String name, String t, Color c, float f, AlignmentLocation horz, AlignmentLocation vert,
			String zoneName, AWolgonPanel panel) {
		super(name, t, c, f, horz, vert, zoneName, panel);
	}

	public Button(String name, String t, Color c, float f, String otherLabelName, AWolgonPanel panel) {
		super(name, t, c, f, otherLabelName, panel);
	}

	public void hover() {
		this.setTextColor(Color.MAGENTA);
	}

	public void unhover() {
		this.setTextColor(Color.WHITE);
	}
}

