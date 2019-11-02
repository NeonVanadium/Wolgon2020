package game;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

class WolgonPanel extends JPanel {

	private String lastKey = "Press a key.";
	private Hashtable<String, Rectangle> zones = new Hashtable<String, Rectangle>();
	private Hashtable<String, Label> labels = new Hashtable<String, Label>();
	private static final float FONT_SIZE = 30f;
	private static final int BUFFER = 20; //the buffer around the panel and all subzones.
	
	private class Label {
		
		private String text;
		private String wrappedText;
		private int numLines = 1; //how many lines is the wrapped string?
		public Label parent = null;
		private AlignmentLocation vert;
		private AlignmentLocation horz;
		public Color color;
		public float fontSize;
		public Rectangle zone;
		
		//absolute positioner. Gives total control over position of label
		public Label(String name, String t, Label p, Color c, float f, String zoneName) {
			text = t;
			parent = p;
			color = c;
			fontSize = f;
			zone = zones.get(zoneName);
			labels.put(name, this);
			wrapText();
		}
		
		//Positions label relative to one of the AlignmentLocations (see enum in Label) of its zone
		public Label(String name, String t, Color c, float f, AlignmentLocation h, AlignmentLocation v, String zoneName) {
			text = t;
			zone = zones.get(zoneName);
			horz = h;
			vert = v;
			color = c;
			fontSize = f;
			labels.put(name, this);
			wrapText();
			
		}
		
		//Positions label below an existing label.
		public Label(String name, String t, Color c, float f, String otherLabelName) {
			text = t;
			parent = labels.get(otherLabelName);
			zone = parent.zone;
			color = c;
			fontSize = f;		
			labels.put(name, this);
			wrapText();
		}
		
		
		private void wrapText() { //given the text and its container, inserts \n at various locations so that the text will all appear in its box. Fills relevant fields on the instance.
			wrappedText = "";
			numLines = 1;
			int availableSpace = zone.width - (2 * BUFFER); //available horizontal space in the zone, in pixels
			int charsThisLine = 0;
			int lastNewLineIndex = 0;
			
			for(int index = 0; index < text.length(); index++) {
				
				if(index == text.length() - 1) {
					wrappedText += text.substring(lastNewLineIndex);
				}
				if(text.charAt(index) == ' ') {
					
					int charsAtNextSpace = charsThisLine + text.substring(index, text.indexOf(' ', index)).length();
					
					if(charsAtNextSpace * fontSize > availableSpace) { //if by the next space character, more than the available number of pixels will have been used
						wrappedText += text.substring(lastNewLineIndex, index) + '\n';
						lastNewLineIndex = index + 1; //this cuts out the space so there's no leading space on the next line
						charsThisLine = 0;
						numLines++;
					}
					
				}
				else {
					charsThisLine++;
				}
				
			}
		
		}
		
		//height of the given label. TODO: update to factor multiple lines (text wrapping)
		public int getHeight() {
			return ((int) fontSize) * numLines;
		}
		
		public void setText(String s) {
			text = s;
			wrapText();
		}
	
		public String getRawText() {
			return text;
		}
		
		public String getWrappedText() {
			return wrappedText;
		}
		
		
		//determines the x position of this label
		public int getX() {
			
			if(parent == null) {
				return getAlignedCoordinate(text, horz, zone);
			}
			else {
				return parent.getX();
			}
			
		}
		
		//determines the y position of this label
		public int getY() {
			
			if(parent == null) {
				return getAlignedCoordinate(text, vert, zone);
			}
			else {
				return parent.getY() + parent.getHeight() + 10;
			}
			
		}
		
	}
	
    public WolgonPanel() {
        
    	zones.put("Whole", new Rectangle(0, 20, Main.BOUNDS.width, Main.BOUNDS.height));
    	zones.put("Main", new Rectangle(0, 20 + (Main.BOUNDS.height / 3), Main.BOUNDS.width, Main.BOUNDS.height - Main.BOUNDS.height / 3));
    	new Label("CLOCK", "", Color.WHITE, 30f, AlignmentLocation.Left, AlignmentLocation.Top, "Whole");
    	new Label("LASTKEY", "(Press a key)", Color.RED, 10f, "CLOCK");
    	new Label("NAME", "name", Color.WHITE, 30f, AlignmentLocation.Left, AlignmentLocation.Top, "Main");
    	new Label("DESC", "desc", Color.WHITE, 15f, "NAME");
    	new Label("EXITS", "exits", Color.WHITE, 20f, "DESC");
    	
        addKeyListener( new KeyListener() {

			public void keyTyped(KeyEvent e) {
				//nothing
			}

			public void keyPressed(KeyEvent e) {
				labels.get("LASTKEY").text = "" + KeyEvent.getKeyText(e.getKeyCode());
				
				if(KeyEvent.getKeyText(e.getKeyCode()) == "Escape") {
					Main.togglePause();
				}
				
			}

			public void keyReleased(KeyEvent e) {
				//nothing
			}
        		
        } );
        
        addMouseListener( new MouseAdapter() {
			
			public void mouseClicked(MouseEvent e) {
				
				System.out.println("hizo click.");
			
			} 
			
		});
        
    }
    
    //redraws the panel and does whatever needs be done each frame
    public void update() {
    	
    	setLabelText("CLOCK", Main.getCurTime());
    	
    	this.setBackground(Main.getCurColor()); 
    	repaint();
    	
    }

    

    public void paintComponent(Graphics g) {
        super.paintComponent(g);       

        g.setFont(g.getFont().deriveFont(FONT_SIZE));
        g.setColor(Color.WHITE);
        
        //g.drawString(text, getAlignedCoordinate(text, AlignmentLocation.Left), getAlignedCoordinate(text, AlignmentLocation.Top));
        //g.drawString(lastKey, getAlignedCoordinate(lastKey, AlignmentLocation.Left), getAlignedCoordinate(lastKey, AlignmentLocation.Top) + 30);
        
        Iterator<Label> labelIterator = labels.elements().asIterator();
        
        while(labelIterator.hasNext()) {
        	drawLabel((Label) labelIterator.next(), g);
        }
        
    }  
    
    
    private enum AlignmentLocation {
    	Top, Bottom, VCenter, HCenter, Left, Right
    }
    
    // Given a piece of text and a AlignmentLocation, gets the coordinate that would place the text at that location in its zone
    private int getAlignedCoordinate(String txt, AlignmentLocation a, Rectangle forZone) {
    	
    	int halfLineHeight = (int) (FONT_SIZE / 2);
    	
    	switch(a) {
    	
    	case Top:
    		return forZone.y + BUFFER;
    	case Bottom:
    		return (forZone.y + forZone.height) - (halfLineHeight + BUFFER);
    	case VCenter:
    		return ((forZone.y + forZone.height) / 2) - halfLineHeight;
    	case HCenter:
    		return ((forZone.x + forZone.width) / 2) - (StringLengthInPixels(txt) / 2);
    	case Left:
    		return forZone.x + BUFFER;
    	case Right:
    		return (forZone.x + forZone.width) - StringLengthInPixels(txt) - BUFFER;
    	default:
    		return 0;
    	
    	}
    	
    }
    
    //how wide the given string would be given the current graphics settings
    private int StringLengthInPixels(String s) {
    	return (int) (s.length() * FONT_SIZE);
    }
  
    private void drawLabel(Label l, Graphics g) {
    	g.setFont(g.getFont().deriveFont(l.fontSize));
    	g.setColor(l.color);
    	
    	String[] lines = l.wrappedText.split("\n");
    	for(int i = 0; i < l.numLines; i++) {
    		g.drawString(lines[i], l.getX(), (int) (l.getY() + (i * l.fontSize)));
    	}
    	
    }
    
    public void setLabelText(String labelName, String newText){
    	labels.get(labelName).setText(newText);
    }
    
    public void displayRoomData(Room r) {
    	setLabelText("NAME", r.getName());
    	setLabelText("DESC", r.getDesc());
    	setLabelText("EXITS", r.getExitNames());
    }
    
}
