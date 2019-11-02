package game;

public class Room {
	
	private String name;
	private String desc; //description
	private Exit[] exits;
	
	private class Exit {
		
		private String name; //name of the exit
		private int destinationId; //the ID of the target room
		
		//string to create exit is one of the lines of a the formated room text.
		public Exit(String text)
		{
			int commaIndex = text.indexOf(',');
			
			name = text.substring(0, commaIndex);
			destinationId = Integer.parseInt(text.substring(commaIndex + 1, text.length()));
		}
		
		public String getName() {
			return name;
		}
		
		public int getDestinationId() {
			return destinationId;
		}
		
	}
	
	//rooms are made by parsing text from the rooms file. The string passed into room should therefore be parsed from that file.
	public Room(String text) {
		
		String[] lines = text.split("\n");
		int id = Integer.parseInt( lines[0].substring( lines[0].indexOf(" ") + 1, lines[0].length()));
		name = lines[1];
		desc = lines[2];
		
		String[] exitStrings = lines[3].split(";");
		exits = new Exit[exitStrings.length];
		
		for(int i = 0; i < exits.length; i++) {
			exits[i] = new Exit(exitStrings[i]);
		}
		
		Main.rooms.put(id, this);
		
	}
	
	public String getName() {
		return name;
	}
	
	public String getDesc() {
		return desc;
	}
	
	public String getExitNames() {
		String s = "";
		
		for(int i = 0; i < exits.length; i++) {
			s += exits[i].getName();
			
			if(i != exits.length - 1) {
				s += ", ";
			}
		}
		
		return s;
	}

}
