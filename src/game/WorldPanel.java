package game;

import java.awt.*;
import java.awt.event.KeyEvent;

import game.Label;

public class WorldPanel extends AWolgonPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int lastRoomId;
	
    public WorldPanel() { 	
    	super();
    	
    	addZone("Main", new Zone(0, 1.0/3, "Whole", this));
    	addZone("Right side", new Zone(1/2.0, 0, "Whole", this));
    	
    	new Label("CLOCK", "", Color.WHITE, 30f, AlignmentLocation.Left, AlignmentLocation.Top, "Whole", this);
    	new UserTypeLabel("TYPE_BOX", "(Press a key)", Color.WHITE, 30f, "CLOCK", this);
    	new TypewriterLabel("NAME", "name", Color.WHITE, 30f, AlignmentLocation.Left, AlignmentLocation.Top, 
    			"Main", this);
    	new TypewriterLabel("DESC", "desc", Color.WHITE, 20f, "NAME", this);
    	new TypewriterLabel("OBJECTTAG", "You see no objects of interest.", Color.WHITE, 20f, "DESC", this);
    	new TypewriterLabel("EXITS", "You see the following exits:", Color.WHITE, 20f, "OBJECTTAG", this);
    	
    	new Label("HOVER_TEXT", "", Color.WHITE, 30f, AlignmentLocation.Left, AlignmentLocation.VCenter, 
				"Right side", this);
    	
    	this.hoverTextBox = getLabel("HOVER_TEXT");
    	
    	// initialized as -1 so updateRoomData() is called on the first update()
    	lastRoomId = -1;
    	
    	System.out.println(typeBox != null);
    }
    
    
    public void update() {	
    	setLabelText("CLOCK", Main.getCurTime());
    	
    	this.setBackground(Main.getCurColor()); 
    	
    	if (lastRoomId != Main.player.curRoom) {
    		
    		
    		int curRoom = Main.player.curRoom;
    		updateRoomData(curRoom);
    		lastRoomId = curRoom;
    	}
    	
    	repaint();
    }
    
	public void keyHandler(KeyEvent e) {
		String keyText = KeyEvent.getKeyText(e.getKeyCode());

		
		typeBox.handleTyping(keyText);

		if(keyText == "Escape") {
			Main.togglePause();
		}
		else if(keyText == "Enter") {
			Main.getCurRoom().checkExit(typeBox.getText());
		}
	}

    
    public void updateRoomData(int id) {
    	Room r = Main.getRoom(id);
    	
    	setLabelText("NAME", r.getName());
    	setLabelText("DESC", r.getDesc());
    	
    	//TODO Update when objects could possibly not be empty
    	setLabelText("OBJECTTAG", "You see no objects of interest.");
    	
    	r.addExitButtonsTo(this);
    }
    
    public void clearTypeBox() {
    	setLabelText("TYPE_BOX", "");
    }
}
