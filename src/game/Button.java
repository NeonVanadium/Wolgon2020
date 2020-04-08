package game;

import java.awt.Color;

class Button extends Label {

	private ButtonFunction function;
	private AWolgonPanel panel; // the panel holding the button
	private String hoverText; 	// when hovered over, if the panel has a designated button hover label, 
								// this text will show there

	// no parent, no hover text
	public Button(String name, String text, float fontSize, AlignmentLocation horz, AlignmentLocation vert,
			String zoneName, AWolgonPanel panel, ButtonFunction function) {
		super(name, text, Color.WHITE, fontSize, horz, vert, zoneName, panel);
		this.function = function;
		this.panel = panel;
	}

	// parent, no hover text
	public Button(String name, String text, float fontSize, String otherLabelName, AWolgonPanel panel,
			ButtonFunction function) {
		super(name, text, Color.WHITE, fontSize, otherLabelName, panel);
		this.function = function;
		this.panel = panel;
	}
	
	// no parent + hoverText
	public Button(String name, String text, float fontSize, AlignmentLocation horz, AlignmentLocation vert,
			String zoneName, AWolgonPanel panel, ButtonFunction function, String hoverText) { 
		this(name, text, fontSize, horz, vert, zoneName, panel, function);
		this.hoverText = hoverText;
	}
	
	// parent + hover text
	public Button(String name, String text, float fontSize, String otherLabelName, AWolgonPanel panel,
			ButtonFunction function, String hoverText) {
		this(name, text, fontSize, otherLabelName, panel, function);
		this.hoverText = hoverText;
	}

	public void hover() {
		this.setTextColor(Color.MAGENTA);
		if (panel.hoverTextBox != null && hoverText != null) {
			panel.hoverTextBox.setText(hoverText);
		}
		
	}

	public void unhover() {
		this.setTextColor(Color.WHITE);
		if (panel.hoverTextBox != null && !panel.hoverTextBox.getText().isEmpty()) {
			panel.hoverTextBox.setText("");
		}
	}
	
	public void runFunction() {
		if (function != null) {
			function.run();
		}
	}
}

