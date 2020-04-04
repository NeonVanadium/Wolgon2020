package game;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;

// TODO: Allow setting of right and bottom limits instead of just top and left. Will help center text.
class Zone implements IPositioner {

	private Zone parent;
	private double XFraction; // at what fraction of the parent zone's width from the left does this zone start
	private double YFraction; // at what fraction of the parent zone's height from the top does this zone start

	public Zone(double XFraction, double YFraction, String parentName, AWolgonPanel panel) {
		if(parentName != null) {
			this.parent = panel.getZone(parentName);
		}
		this.XFraction = XFraction;
		this.YFraction = YFraction;
	}

	public Zone(double XFraction, double YFraction) {
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
			return AWolgonPanel.BUFFER + (int) (Main.getBounds().width * XFraction);
		}
		else {
			return (int) (parent.getX() + (parent.getWidth() * XFraction));
		}

	}

	public int getY() {
		if(parent == null) {
			return AWolgonPanel.BUFFER + (int) (Main.getBounds().height * YFraction);
		}
		else {
			return (int) (parent.getY() + (parent.getHeight() * YFraction));
		}
	}

}


