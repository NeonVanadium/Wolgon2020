package game;

import java.awt.Color;

// a label for the user to type in. A text box, functionally. There should only be one of this on screen at
// any given time.
public class UserTypeLabel extends Label{
	
	// the text displayed by default, which will be immediately deleted upon input from the user
	String defaultText;
	
	public UserTypeLabel(String name, String text, Color color, float fontSize, AlignmentLocation horz,
			AlignmentLocation vert, String zoneName, AWolgonPanel panel) {
		super(name, text, color, fontSize, horz, vert, zoneName, panel);
		constructorHelper(text, panel);
	}
	
	public UserTypeLabel(String name, String text, Color color, float fontSize, String otherLabelName,
			AWolgonPanel panel) {
		super(name, text, color, fontSize, otherLabelName, panel);
		constructorHelper(text, panel);
	}
	
	// abstraction helper for constructors
	private void constructorHelper(String text, AWolgonPanel panel) {
		defaultText = text;
		panel.typeBox = this;
	}
	
	public void handleTyping(String keycode) {
		if (getText().equals(defaultText)) {
			setText("");
		}
		
		if (!getText().isEmpty() && keycode == "Backspace") {
			setText(getText().substring(0, getText().length() - 1));
		}
		else if (keycode.length() == 1) {
			setText(getText() + keycode);
		}
	}
}
