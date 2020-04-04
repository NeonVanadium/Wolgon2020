package game;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

class WorldPanel extends AWolgonPanel {

    public WorldPanel() { 	
    	super();
    	
    	addZone("Main", new Zone(0, 1.0/3, "Whole", this));
    	
    	new Label("CLOCK", "", Color.WHITE, 30f, AlignmentLocation.Left, AlignmentLocation.Top, "Whole", this);
    	new Label("TYPE_BOX", "(Press a key)", Color.WHITE, 30f, "CLOCK", this);
    	new Label("NAME", "name", Color.WHITE, 30f, AlignmentLocation.Left, AlignmentLocation.Top, "Main", this);
    	new Label("DESC", "desc", Color.WHITE, 20f, "NAME", this);
    	new Label("OBJECTTAG", "You see no objects of interest.", Color.WHITE, 20f, "DESC", this);
    	new Label("EXITS", "exits", Color.WHITE, 20f, "OBJECTTAG", this);
    }
    
    
    public void update() {	
    	setLabelText("CLOCK", Main.getCurTime());
    	
    	this.setBackground(Main.getCurColor()); 
    	
    	updateRoomData(Main.player.curRoom);
    	repaint();
    }

    
    public void updateRoomData(int id) {
    	Room r = Main.getRoom(id);
    	
    	setLabelText("NAME", r.getName());
    	setLabelText("DESC", r.getDesc());
    	
    	//TODO Update for when objects could possibly not be empty
    	setLabelText("OBJECTTAG", "You see no objects of interest.");
    	
    	setLabelText("EXITS", r.getExitString());
    }
    
    public void clearTypeBox() {
    	setLabelText("TYPE_BOX", "");
    }
    

    
}
