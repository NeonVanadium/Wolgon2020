package game;

import java.awt.Color;

public class GameClock {
	
	private int mode = 12; //12 or 24.
	private int hour; // [0, 23]
	private int minute; // [0, 59]
	private int ticks = 0; // [0, ticksPerMinute]
	private int ticksPerMinute; //how many clock ticks per in-game minute
	
	public GameClock(int startHour, int startMinute, int tpm) {	
		hour = startHour;
		minute = startMinute;
		ticksPerMinute = tpm;
	}
	
	//changes the clock mode between 12- and 24-hour time.
	public void setMode(int to) throws IllegalArgumentException {
		if(to == 12 || to == 24) {
			mode = to;
		}
		else {
			throw new IllegalArgumentException("GameClock.setMode() was given an invalid mode (" + to + ").");
		}
	}
	
	public int getHour() {
		return hour;
	}
	
	public int getMinute() {
		return minute;
	}
	
	//ensures minutes are two digits long
	private String formatMinute() {
		String m = minute + "";
		
		if (m.length() < 2) {
			m = "0" + m;
		}
		
		return m;
	}
	
	//returns a string representing the clock.
	public String getTime() {
		
		if(mode == 24) {
			return hour + ":" + formatMinute();
		}
		else {
			String time = ""; 
			
			if (hour % 12 == 0) {
				time += 12;
			}
			else {
				time += (hour % 12);
			}
			
			time += ":" + formatMinute();
			
			if(hour < 12) {
				time += " AM";
			}
			else {
				time += " PM";
			}
			
			return time;
		}
		
	}
	
	//increments time by one
	public void incrementTime() {	
		ticks++;
		
		if (ticks == ticksPerMinute){	
			ticks = 0;
			minute++;
			
			if (minute == 60) {		
				minute = 0;
				hour++;
				
				if (hour == 24) {	
					hour = 0;				
				}		
			}		
		}	
	}
	

	// generates the sky color based on the time of day.
	public Color getSkyColor() {
		
		int R = 0, G = 40, B = 200;
		
		R = getRed();
		G = getGreen();
		B = getBlue();
		
		return new Color(R, G, B);
		
	}
	
	private int getRed() {
		
		double R = -30 * Math.pow(detailedHour((hour % 12), minute, ticks) - 6.5, 2) + 50;
		
		if(R < 0) {
			return 0;
		}
		else {
			return (int) R;
		}
		
	}
	
	private int getGreen() {
		
		return getBlue() / 2;
		
	}
	
	private int getBlue() {
		
		double B = -4.2 * Math.pow(detailedHour() - 13, 2) + 255; 
		
		if(B < 30) {
			return 30;
		}
		else {
			return (int) B;
		}
		
	}
	
	
	
	// similar to getHour() but with minutes and ticks included as decimals. Note: on 24 hour time.
	private double detailedHour(int atHour, int atMinute, int atTicks) {
		
		return atHour + ((double) atMinute / 60) + ((double) ticks / (60 * ticksPerMinute));
		
	}
	
	private double detailedHour() {
		return detailedHour(hour, minute, ticks);
	}

}
