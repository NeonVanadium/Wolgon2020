package game;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import javax.swing.*;
import java.util.*;
import javax.swing.Timer;

public class Main {

	public static final Rectangle BOUNDS = new Rectangle(0, 0, 1000, 800); // i'm sure this isn't good form but it is convenient.
	public static final Dimension MIN_SIZE = new Dimension(500, 500);
	public static final int TICK = 100; // how long a tick is, in milliseconds.
	
	private static Hashtable<Integer, Room> rooms = new Hashtable<Integer, Room>();
	private static JFrame frame = new JFrame();
	
	//start hour (0-23), start minute, ticks-per-minute
	private static GameClock c = new GameClock(7, 00, 10);
	
	private static Timer t = new Timer(TICK, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				c.incrementTime();
				getCurPanel().update();
			}
		});
	
	protected static GameCharacter player = new GameCharacter();
	
	public static void main(String[] args) {	
		setupFrame();
		//TODO: load game (far future)
		loadRooms();
		t.start();
	}
	
	//initializes the frame and the panel
	private static void setupFrame() {
		frame.setBounds(BOUNDS);
		frame.setBackground(Color.BLACK);
		frame.setTitle("WOLGON 2020");	
		setPanel(new WorldPanel());
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
	
	public static Room getCurRoom() {
		return rooms.get(player.curRoom);
	}
	
	// returns the id of the player's current room
	public static int getCurRoomId() {
		return player.curRoom;
	}
	
	// retrieves the room with the provided id from the room Hashtable
	public static Room getRoom(int id) {
		return rooms.get(id);
	}
	
	// adds a new room to the room Hashtable
	public static void putRoom(int id, Room value) {
		rooms.put(id, value);
	}
	
	//stops or starts the timer
	public static void togglePause() {
		if (t.isRunning()) {
			t.stop();
		}
		else {
			t.start();
		}
	}
	
	public static Rectangle getBounds() {
		return frame.getBounds();
	}
	
	public static void changeRoom(int toID) {
		//things like the room change sfx, time changes (from walking), and checks for random encounters will occur here
		//should also include the blackout-fade-in effect
		
		player.changeRoom(toID);
	}

	public static String handleTyping(String on, String keycode) {
		if (!on.isEmpty() && keycode == "Backspace") {
			return on.substring(0, on.length() - 1);
		}
		else if (keycode.length() == 1) {
			return on + keycode;
		}
		else {
			return on;
		}
		
	}
	
	public static void setPanel(AWolgonPanel p) {
		togglePause();
		frame.getContentPane().removeAll();
		frame.getContentPane().add(p);
		frame.revalidate();
		togglePause();
	}
	
	public static AWolgonPanel getCurPanel() {
		return (AWolgonPanel) frame.getContentPane().getComponent(0);
	}
	

}
