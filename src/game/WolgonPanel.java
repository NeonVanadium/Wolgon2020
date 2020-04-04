package game;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import game.Label;

// a panel designed for a specific portion of gameplay (word, menu, etc)
abstract class AWolgonPanel extends JPanel {

	private static final long serialVersionUID = 1L; //Do not know what this does beyond getting rid of yellow squiggle.
	
	private Hashtable<String, Zone> zones = new Hashtable<String, Zone>();
	private Hashtable<String, Label> labels = new Hashtable<String, Label>();

	private boolean resized = true; // if the panel was resized this frame. Starts true so all positioners are called on startup.
	private Point lastMousePosition; // position of mouse last frame
	private Label hoveredOver; // the label currently hovered over by the mouse, if any.

	public static final int BUFFER = 20; // size of the whitespace buffer around the panel and all subzones
	public static final float DEFAULT_FONT_SIZE = 30f;
	

	public AWolgonPanel() {

		this.setMinimumSize(Main.MIN_SIZE);
		this.setFocusable(true);

		// this zone represents the entire panel
		addZone("Whole", new Zone(0, 0));

		addKeyListener( new KeyListener() {
			public void keyTyped(KeyEvent e) {
				//nothing
			}

			public void keyPressed(KeyEvent e) {

				//TODO: Handle typing on MenuPanel instead, allow this chunk to be overriden (call abstract method?)

				String keyText = KeyEvent.getKeyText(e.getKeyCode());

				Label typeBox = getLabel("TYPE_BOX");

				typeBox.setText(Main.handleTyping(typeBox.getText(), keyText));

				if(keyText == "Escape") {
					Main.togglePause();
				}
				else if(keyText == "Enter") {
					Main.getCurRoom().checkExit(typeBox.getText());
				}

			}

			public void keyReleased(KeyEvent e) {
				//nothing
			}       		
		});

		addMouseListener( new MouseAdapter() {	
			public void mouseClicked(MouseEvent e) {	
				System.out.println("Clicked");
				Main.setPanel(new MenuPanel());
			}		
		});

		this.addMouseMotionListener( new MouseMotionListener() {
			public void mouseMoved(MouseEvent e) {
				setLastMousePosition(e.getPoint());
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

	//redraws the panel and does whatever needs be done each frame
	public abstract void update();

	//draws the panel
	public void paintComponent(Graphics g) {
		super.paintComponent(g);       

		g.setFont(g.getFont().deriveFont(DEFAULT_FONT_SIZE));
		g.setColor(Color.WHITE);

		//displayRoomData(Main.player.curRoom);

		Iterator<Label> labelIterator = labels.elements().asIterator();

		while (labelIterator.hasNext()) {
			labelIterator.next().draw(g);
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
