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

	/*
	 * 	could not find a way to name these variables that didn't sound wrong so enjoy.
	 * 	The game updates various parts on LARGE_TICK and SMALL_TICK intervals.
	 * 	a LARGE_TICK is equivalent to one in-game second.
	 * 	a SMALL_TICK is the smallest rate at which the game updates
	 *  ------------------------------------------------------------
	 *  Most tasks should be called on LARGE_TICK to prevent using tons of power.
	 *  SMALL_TICK should mostly be used for things like Typewriter labels, eg effects that need to update more than
	 *  once per second to look 'right.'
	 */

	public static final int SMALL_TICK = 20; // in milliseconds
	public static final int LARGE_TICK = 5; // HOW MANY SMALL_TICKS in a large tick. Should occur ~once every second.
	private static int smallTicks; // how many SMALL_TICKS have passed? Should be reset every time a BIG_TICK passes.

	private static Hashtable<Integer, Room> rooms = new Hashtable<Integer, Room>();

	// the frame which holds an AWolgonPanel object.
	private static final JFrame FRAME = new JFrame();

	//start hour (0-23), start minute, ticks-per-minute
	private static GameClock c = new GameClock(7, 00, 10);

	private static Timer t = new Timer(SMALL_TICK, new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			getCurPanel().update();
			smallTicks += 1;
			if (smallTicks == LARGE_TICK) {
				smallTicks = 0;
				c.incrementTime();
			}

		}
	});

	protected static GameCharacter player = new GameCharacter();

	public static void main(String[] args) {	
		setupFrame();
		//TODO: load game when there is a game to load
		loadRooms();
		smallTicks = 0;
		t.start();
	}

	//initializes the frame and the panel
	private static void setupFrame() {
		FRAME.setBounds(BOUNDS);
		FRAME.setBackground(Color.BLACK);
		FRAME.setTitle("WOLGON 2020");	
		setPanel(new TitlePanel());
		FRAME.setVisible(true);
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
		return FRAME.getBounds();
	}

	public static void changeRoom(int toID) {
		//things like the room change sfx, time changes (from walking), and checks for random encounters will occur here
		//should also include the blackout-fade-in effect

		player.changeRoom(toID);
	}

	public static void setPanel(AWolgonPanel p) {
		togglePause();
		FRAME.getContentPane().removeAll();
		FRAME.getContentPane().add(p);
		FRAME.revalidate();
		togglePause();
	}

	public static AWolgonPanel getCurPanel() {
		return (AWolgonPanel) FRAME.getContentPane().getComponent(0);
	}


}
