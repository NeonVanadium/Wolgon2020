package game;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.util.*;
import javax.swing.Timer;

public class Main {

	
	public static final int WIDTH = 1000;
	public static final int HEIGHT = 800;
	
	private static JFrame frame = new JFrame();
	private static WolgonPanel panel = new WolgonPanel();
	
	private static GameClock c = new GameClock(7, 0, 10);
	
	private static Timer t = new Timer(100, new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				c.incrementTime();
				panel.update();
			}
		}
	);
	
	
	public static void main(String[] args) {
		System.out.print("Hello, world! ");
	
		setupFrame();
		t.start();
		
	}
	
	private static void setupFrame() {
		frame.setBounds(0, 0, 1000, 800);
		frame.setBackground(Color.BLACK);
		
		panel.setFocusable(true);
		
		frame.add(panel);
		
		frame.setVisible(true);
		
	}
	
	public static String getCurTime() {
		return c.getTime();
	}
	

}
