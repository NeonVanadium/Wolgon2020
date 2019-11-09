package game;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

class WolgonPanel extends JPanel {

	private String lastKey = "Press a key.";
	private Hashtable<String, Zone> zones = new Hashtable<String, Zone>();
	private Hashtable<String, Label> labels = new Hashtable<String, Label>();
	private static final float FONT_SIZE = 30f;
	private static final int BUFFER = 20; //the buffer around the panel and all subzones.
	private boolean resized = true; //if the panel was resized this frame. Starts true so all positioners are called on startup.
	private Point lastMousePosition; //position of mouse last frame
	
	private class Zone {
		
		private Zone parent;
		private double XFraction; // at what fraction of the parent zone's width from the left does this zone start
		private double YFraction; // at what fraction of the parent zone's height from the top does this zone start
		
		public Zone(double XFraction, double YFraction, String parentName) {
			if(parentName != null) {
				this.parent = zones.get(parentName);
			}
			this.XFraction = XFraction;
			this.YFraction = YFraction;
		}
		
		public int getWidth() {	
			return Main.getBounds().width - getX();	
		}
		
		public int getHeight() {
			return Main.getBounds().height - getY();
		}
		
		public int getX() {
			if(parent == null) {
				return BUFFER + (int) (Main.getBounds().width * XFraction);
			}
			else {
				return (int) (parent.getX() + (parent.getWidth() * XFraction));
			}
			
		}
		
		public int getY() {
			if(parent == null) {
				return BUFFER + (int) (Main.getBounds().height * YFraction);
			}
			else {
				return (int) (parent.getY() + (parent.getHeight() * YFraction));
			}
		}
		
	}
	
	private class Label {
		
		private String text;
		private String wrappedText;
		private int numLines = 1; //how many lines is the wrapped string?
		private Label parent = null;
		private AlignmentLocation vert;
		private AlignmentLocation horz;
		private Color color;
		private float fontSize;
		private int width; //how wide the label is
		private int height;
		private Zone zone;
		private boolean textChanged = true; //has the text changed since the previous wrapping?
		
		//Positions label relative to one of the AlignmentLocations (see enum in Label) of its zone
		public Label(String name, String t, Color c, float f, AlignmentLocation h, AlignmentLocation v, String zoneName) {
			text = t;
			zone = zones.get(zoneName);
			horz = h;
			vert = v;
			color = c;
			fontSize = f;
			labels.put(name, this);
			
		}
		
		//Positions label below an existing label.
		public Label(String name, String t, Color c, float f, String otherLabelName) {
			text = t;
			parent = labels.get(otherLabelName);
			zone = parent.zone;
			color = c;
			fontSize = f;		
			labels.put(name, this);
		}
		
		
		public void wrapText(Graphics g) { //given the text and its container, inserts \n at various locations so that the text will all appear in its box. Fills relevant fields on the instance.
			
			wrappedText = "";
			numLines = 1;
			width = 0;
			height = 0;
			
			FontMetrics m = g.getFontMetrics();
			
			int availableSpace = zone.getWidth() - (2 * BUFFER); //available horizontal space in the zone, in pixels
			int lastNewLineIndex = 0;
			
			String curLine = "";
			String lineAtNextSpace = "";
			
			int lastSpace = 0;
			int nextSpace;
			
			//based on next space and current index.
			
			while(text.indexOf(' ', lastSpace + 1) != -1) {
				
				nextSpace = text.indexOf(' ', lastSpace + 1);
				
				lineAtNextSpace = text.substring(lastNewLineIndex, nextSpace);
				//curLine = text.substring(lastNewLineIndex, lastSpace);
				
				if(m.stringWidth(lineAtNextSpace) + (BUFFER) >= availableSpace) {
					
					if(m.stringWidth(curLine) > width) {
						width = m.stringWidth(curLine);
					}
					height += m.getStringBounds(curLine, g).getHeight();
					wrappedText += curLine + "\n";
					lastNewLineIndex = lastSpace + 1;
					numLines++;
					
				}
				
				//preparation for next
				curLine = lineAtNextSpace;
				lastSpace = nextSpace;
				
			}
			
			height += m.getStringBounds(text.substring(lastNewLineIndex), g).getHeight();
			wrappedText += text.substring(lastNewLineIndex);
			
		
		}
		
		public void setText(String s) {
			text = s;
			textChanged = true;
		}	
		
		//determines the x position of this label
		public int getX() {
			
			if(parent == null) {
				return getAlignedHorizontalPosition();
			}
			else {
				return parent.getX();
			}
			
		}
		
		//determines the y position of this label
		public int getY() {
			
			if(parent == null) {
				return getAlignedVerticalPosition();
			}
			else {
				return parent.getY() + parent.height;
				//TODO: modify get aligned coord to take a label not a string
			}
			
		}
		
		private int getAlignedVerticalPosition() {
	    	
	    	int halfLineHeight = (int) (FONT_SIZE / 2);
	    	
	    	switch(vert) {
	    	
	    	case Top:
	    		return zone.getY() + BUFFER;
	    	case Bottom:
	    		return (zone.getY() + zone.getHeight()) - (halfLineHeight + BUFFER);
	    	case VCenter:
	    		return ((zone.getY() + zone.getHeight()) / 2) - halfLineHeight;
	    	default:
	    		return 0;
	    	
	    	}
	    }
	    
	    private int getAlignedHorizontalPosition() {
	    	int halfLineHeight = (int) (FONT_SIZE / 2);
	    	
	    	switch(horz) {
	    	

	    	//case HCenter:
	    		//return ((zone.getX() + zone.getWidth()) / 2) - (StringLengthInPixels(l.text) / 2);
	    	case Left:
	    		return zone.getX() + BUFFER;
	    	//case Right:
	    		//return (zone.getX() + zone.getWidth()) - StringLengthInPixels(txt) - BUFFER;
	    	default:
	    		return 0;
	    	
	    	}
	    }
	    
	    public boolean contains(Point p) {
	    	return (p.x >= getX() && p.x <= getX() + width) && (p.y >= getY() && p.y <= getY() + height);	
	    }
	    
		
	}
	
    public WolgonPanel() {
    	
    	zones.put("Whole", new Zone(0, 0, null));//new Rectangle(0, 20, Main.BOUNDS.width, Main.BOUNDS.height));
    	zones.put("Main", new Zone(0, 1.0/3, "Whole"));//new Rectangle(0, 20 + (Main.BOUNDS.height / 3), Main.BOUNDS.width, Main.BOUNDS.height - Main.BOUNDS.height / 3));
    	new Label("CLOCK", "", Color.WHITE, 30f, AlignmentLocation.Left, AlignmentLocation.Top, "Whole");
    	new Label("LASTKEY", "(Press a key)", Color.RED, 10f, "CLOCK");
    	new Label("NAME", "name", Color.WHITE, 30f, AlignmentLocation.Left, AlignmentLocation.Top, "Main");
    	new Label("DESC", "desc", Color.WHITE, 20f, "NAME");
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
				
				if(KeyEvent.getKeyText(e.getKeyCode()) == "Space") {
					if(Main.player.curRoom == 1) {
						Main.player.curRoom = 2;
						return;
					}
					Main.player.curRoom = 1;
				}
				
			}

			public void keyReleased(KeyEvent e) {
				//nothing
			}
        		
        } );
        
        addMouseListener( new MouseAdapter() {
			
			public void mouseClicked(MouseEvent e) {
				
				System.out.println("Clicked");
			
			}
			
		});
        
        this.addMouseMotionListener( new MouseMotionListener() {
			public void mouseMoved(MouseEvent e) {
				lastMousePosition = e.getPoint();
			}

			public void mouseDragged(MouseEvent e) {
				//nothing
			}
        });
        
        this.addComponentListener( new ComponentAdapter() {
        	
        	public void componentResized(ComponentEvent e) {
        		resized = true;
            }
        	
        });
        
        
        
    }
    
    //redraws the panel and does whatever needs be done each frame
    public void update() {
    	
    	setLabelText("CLOCK", Main.getCurTime());
    	
    	this.setBackground(Main.getCurColor()); 
    	repaint();
    	
    	resized = false;
    	
    }

    

    public void paintComponent(Graphics g) {
        super.paintComponent(g);       

        g.setFont(g.getFont().deriveFont(FONT_SIZE));
        g.setColor(Color.WHITE);
        
        //g.drawString(text, getAlignedCoordinate(text, AlignmentLocation.Left), getAlignedCoordinate(text, AlignmentLocation.Top));
        //g.drawString(lastKey, getAlignedCoordinate(lastKey, AlignmentLocation.Left), getAlignedCoordinate(lastKey, AlignmentLocation.Top) + 30);
        
        displayRoomData(Main.player.curRoom);
        
        Iterator<Label> labelIterator = labels.elements().asIterator();
        
        while(labelIterator.hasNext()) {
        	Label l = (Label) labelIterator.next();
        	if(lastMousePosition != null && l.contains(lastMousePosition)) {
        		System.out.println(l.text.substring(0, 10));
        	}
        	drawLabel(l, g);
        }
        
    }  
    
    
    private enum AlignmentLocation {
    	Top, Bottom, VCenter, HCenter, Left, Right
    }
    

    //TODO: The Y coordinate of labels are actually below their first lines. Correct this to be above the first line. Somehow? Maybe.
    private void drawLabel(Label l, Graphics g) {
    	g.setFont(g.getFont().deriveFont(l.fontSize));
    	g.setColor(l.color);
    	
    	if(resized || l.textChanged) {
    		l.wrapText(g);
    	}
    	
    	g.drawLine(0, l.getY(), Main.getBounds().width, l.getY());
    	
    	String[] lines = l.wrappedText.split("\n");
    	for(int i = 0; i < lines.length; i++) {
    		g.drawString(lines[i], l.getX(), (int) (l.getY() + (i * l.fontSize)));
    	}
    	
    }
    
    public void setLabelText(String labelName, String newText){
    	labels.get(labelName).setText(newText);
    }
    
    public void displayRoomData(int id) {
    	Room r = Main.rooms.get(id);
    	
    	setLabelText("NAME", r.getName());
    	setLabelText("DESC", r.getDesc());
    	setLabelText("EXITS", r.getExitNames());
    }
    
}
