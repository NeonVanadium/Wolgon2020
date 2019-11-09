package game;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import javax.swing.*;
import java.util.*;
import javax.swing.Timer;

public class Main {

	public static final Rectangle BOUNDS = new Rectangle(0, 0, 1000, 800); //i'm sure this isn't good form but it is convenient.
	public static final int TICK = 100; // how long a tick is, in milliseconds.
	protected static Hashtable<Integer, Room> rooms = new Hashtable<Integer, Room>();
	
	private static JFrame frame = new JFrame();
	private static WolgonPanel panel = new WolgonPanel();
	
	//start hour (0-23), start minute, ticks-per-minute
	private static GameClock c = new GameClock(7, 00, 10);
	
	private static Timer t = new Timer(TICK, new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				c.incrementTime();
				panel.update();
			}
		});
	
	protected static GameCharacter player = new GameCharacter();
	
	public static void main(String[] args) {
		
		setupFrame();
		//load game (far future)
		loadRooms();
		t.start();
		
	}
	
	//initializes the frame and the panel
	private static void setupFrame() {
		frame.setBounds(BOUNDS);
		frame.setBackground(Color.BLACK);
		frame.setTitle("WOLGON 2020");	
		frame.setMinimumSize(new Dimension(500, 500));
		panel.setMinimumSize(new Dimension(500, 500));
		panel.setFocusable(true);
		frame.add(panel);	
		frame.setVisible(true);
	}
	
	private static void loadRooms() {

		String wholeFile = "";
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File("files\\rooms.txt")));
			
			String curLine = "";
			while((curLine = br.readLine()) != null) { // Reads lines of text until there are no more
				wholeFile += curLine + "\n";
			}
			br.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for(String roomText : wholeFile.split("ROOM")) {
			
			if(!roomText.isEmpty()) {
				new Room(roomText);
			}

		}
		
	}
	
	public static String getCurTime() {
		return c.getTime();
	}
	
	public static Color getCurColor() {
		return c.getSkyColor();
	}
	
	public static void togglePause() {
		if(t.isRunning()) {
			t.stop();
		}
		else {
			t.start();
		}
	}
	
	public static Rectangle getBounds() {
		return frame.getBounds();
	}
	

}
