package game;

import java.awt.*;
import java.awt.event.KeyEvent;

import game.Label;

public class WorldPanel extends AWolgonPanel {
	
	private static final long serialVersionUID = 1L;
	private int lastRoomId;
	
    public WorldPanel() { 	
    	super();
    	
    	getZone("WHOLE").splitVertically(1.0/2.0, "LEFT", "RIGHT");
    	getZone("LEFT").splitHorizontally(1.0/8.0, "TOPLEFT", "RESTLEFT");
    	getZone("RESTLEFT").splitHorizontally(1.0/2.0, "MIDLEFT", "BOTTOMLEFT");
    	
    	//addZone("Main", new Zone(0, 1.0/3, "Whole", this));
    	//addZone("Right side", new Zone(1/2.0, 0, "Whole", this));
    	
    	new Label("CLOCK", "", Color.WHITE, 30f, AlignmentLocation.Left, AlignmentLocation.Bottom, "TOPLEFT", this);
    	//new UserTypeLabel("TYPE_BOX", "(Press a key)", Color.WHITE, 30f, "CLOCK", this);
    	new TypewriterLabel("NAME", "name", Color.WHITE, 30f, AlignmentLocation.Left, AlignmentLocation.VCenter, 
    			"MIDLEFT", this);
    	new TypewriterLabel("DESC", "desc", Color.WHITE, 20f, "NAME", this);
    	//new TypewriterLabel("OBJECTTAG", "You see no objects of interest.", Color.WHITE, 20f, "DESC", this);
    	new TypewriterLabel("EXITS", "You might easily go in the following directions:", Color.WHITE, 20f, 
    		AlignmentLocation.Left, AlignmentLocation.Top, "BOTTOMLEFT", this);
    	
    	new TypewriterLabel("HOVERTEXT", "", Color.WHITE, 30f, AlignmentLocation.Left, AlignmentLocation.VCenter, 
				"RIGHT", this);
    	
    	this.hoverTextBox = getLabel("HOVERTEXT");
    	
    	// initialized as -1 so updateRoomData() is called on the first update()
    	lastRoomId = -1;
    }
    
    
    public void update() {
    	
    	if (!Main.getCurTime().equals(getLabel("CLOCK").getText())) {
    		setLabelText("CLOCK", Main.getCurTime());
        	
        	this.setBackground(Main.getCurColor()); 
    	}

    	if (lastRoomId != Main.player.curRoom) {

    		this.removeTemporaryLabels();
    		this.getLabel("HOVERTEXT").setText("");
    		
    		int curRoom = Main.player.curRoom;
    		updateRoomData(curRoom);
    		lastRoomId = curRoom;
    	}
    	
    	repaint();
    }
    
    public void updateRoomData(int id) {
    	Room r = Main.getRoom(id);
    	
    	setLabelText("NAME", r.getName());
    	setLabelText("DESC", r.getDesc());
    	
    	//TODO Update when objects could possibly not be empty
    	//setLabelText("OBJECTTAG", "You see no objects of interest.");
    	
    	r.addExitButtonsTo(this);
    }
    
}
