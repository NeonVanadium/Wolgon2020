package game;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import game.Label;

// an abstract panel designed for a specific portion of gameplay (world, menu, etc)
abstract class AWolgonPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private HashMap<String, Zone> zones = new HashMap<String, Zone>();
	private HashMap<String, Label> labels = new HashMap<String, Label>();

	private boolean resized = true; // if the panel was resized last frame. Starts true so all positioners are called on startup.
	private Point lastMousePosition; // position of mouse last frame
	private Label hoveredOver; // the label currently hovered over by the mouse, if any.

	public static final int BUFFER = 20; // size of the whitespace buffer for the top w
	public static final float DEFAULT_FONT_SIZE = 30f;
	
	protected UserTypeLabel typeBox; // a pointer to the on-panel typable label, if present
	protected Label hoverTextBox; // a label to hold any text that pops up when a button is hovered over
	

	public AWolgonPanel() {

		this.setMinimumSize(Main.MIN_SIZE);
		this.setFocusable(true);
		this.setBackground(Color.DARK_GRAY);

		// this zone represents the entire panel
		addZone("Whole", new Zone(0, 0));

		this.addKeyListener( new KeyListener() {
			public void keyTyped(KeyEvent e) {
				// nothing
			}

			public void keyPressed(KeyEvent e) {
				System.out.println("1");
				// TODO: find out why this doesnt work on other panels
				if (typeBox != null) {
					System.out.println("Got here.");
					keyHandler(e);
				}

			}

			public void keyReleased(KeyEvent e) {
				// nothing
			}       		
		});

		this.addMouseListener( new MouseAdapter() {	
			public void mouseClicked(MouseEvent e) {	
				if (hoveredOver != null) {
					hoveredOver.runFunction();
				}
			}		
		});

		this.addMouseMotionListener( new MouseMotionListener() {
			public void mouseMoved(MouseEvent e) {
				setLastMousePosition(e.getPoint());
				
				// TODO: this is a temporary, slow solution. Optimize.
				for (Label l : labels.values()) {
					if (l.contains(e.getPoint())) {
						hoveredOver = l;
						l.hover();
					}
					else {
						l.unhover();
					}
				}
			}

			public void mouseDragged(MouseEvent e) {
				//nothing
			}
		});

		this.addComponentListener( new ComponentAdapter() {  	
			public void componentResized(ComponentEvent e) {
				setResized(true);
			}	
		});	
		
		
	}

	// redraws the panel and does whatever needs be done each frame
	public abstract void update();
	
	// allows overriding of the behavior in the keyListener
	public abstract void keyHandler(KeyEvent e);

	//draws the panel
	public void paintComponent(Graphics g) {
		super.paintComponent(g);       

		g.setFont(g.getFont().deriveFont(DEFAULT_FONT_SIZE));
		g.setColor(Color.WHITE);

		for (Label l : labels.values()) {
			l.draw(g);
		}		
	}

	protected void addZone(String key, Zone value) {
		zones.put(key, value);
	}

	protected Zone getZone(String key) {
		return zones.get(key);
	}

	// convenience method that creates a default label
	protected void createSimpleLabel(String name, String content, AlignmentLocation horz, AlignmentLocation vert, 
			String parentZoneName) {
		new Label(name, content, Color.WHITE, 30f, horz, vert, parentZoneName, this);
	}
	
	// adds an existing label to the table (Rhymes!)
	protected void addLabel(String key, Label value) {
		labels.put(key, value);
	}

	// gets a label from the table (Rhymes again!)
	protected Label getLabel(String key) {
		return labels.get(key);
	}

	//for use by subclasses
	protected void setResized(Boolean b) {
		resized = b;
	}

	protected Point getLastMousePosition() {
		return lastMousePosition;
	}

	protected void setLastMousePosition(Point to) {
		lastMousePosition = to;
	}


	public void setLabelText(String labelName, String newText){
		labels.get(labelName).setText(newText);
	}

}
