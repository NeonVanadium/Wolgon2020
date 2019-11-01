package game;

public class GameClock {
	
	private String mode = "12"; //12 or 24.
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
	public void setMode(String to) throws Exception {
		if(to == "12" || to == "24") {
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
		
		if(mode == "24") {
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
				time += "AM";
			}
			else {
				time += "PM";
			}
			
			return time;
		}
		
	}
	
	//increments time by one
	public void incrementTime() {
		
		ticks++;
		
		if(ticks == ticksPerMinute){
			
			ticks = 0;
			minute++;
			
			if(minute == 60) {
				
				minute = 0;
				hour++;
				
				if(hour == 24) {
					
					hour = 0;
					
				}
				
			}
			
		}
		
	}

}
