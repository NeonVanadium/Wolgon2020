package game;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class WolgonPanel extends JPanel {

	private String text = "";
	private String lastKey = "Press a key.";
	
    public WolgonPanel() {
        this.setBackground(Color.BLACK);
        
        addKeyListener( new KeyListener() {

			public void keyTyped(KeyEvent e) {
				
			}

			public void keyPressed(KeyEvent e) {
				lastKey = "" + e.getKeyChar();
			}

			public void keyReleased(KeyEvent e) {
				
				
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
    	
    	text = Main.getCurTime();
    	this.setBackground(Main.getCurColor()); 
    	repaint();
    	
    }

    

    public void paintComponent(Graphics g) {
        super.paintComponent(g);       

        g.setFont(g.getFont().deriveFont(30f));
        g.setColor(Color.WHITE);
        
        g.drawString(text, getAlignedCoordinate(text, AlignmentLocation.Left), getAlignedCoordinate(text, AlignmentLocation.Top));
        g.drawString(lastKey, getAlignedCoordinate(lastKey, AlignmentLocation.Left), getAlignedCoordinate(lastKey, AlignmentLocation.Top) + 30);
    }  
    
    
    enum AlignmentLocation {
    	Top, Bottom, VCenter, HCenter, Left, Right
    }
    
    // Given a piece of text and a AlignmentLocation, gets the coordinate that would place the text at that location
    private int getAlignedCoordinate(String txt, AlignmentLocation a) {
    	
    	int buffer = 20; //margin around edges, in pixels
    	int halfLineHeight = getGraphics().getFont().getSize() / 2;
    	
    	switch(a) {
    	
    	case Top:
    		return buffer + halfLineHeight + buffer;
    	case Bottom:
    		return Main.HEIGHT - (halfLineHeight + buffer);
    	case VCenter:
    		return (Main.HEIGHT / 2) - halfLineHeight;
    	case HCenter:
    		return (Main.WIDTH / 2) - (StringLengthInPixels(txt) / 2);
    	case Left:
    		return buffer;
    	case Right:
    		return Main.WIDTH - StringLengthInPixels(txt) - buffer;
    	default:
    		return 0;
    	
    	}
    	
    }
    
    //how wide the given string would be given the current graphics settings
    private int StringLengthInPixels(String s) {
    	return s.length() * getGraphics().getFont().getSize();
    }
    
    
}
