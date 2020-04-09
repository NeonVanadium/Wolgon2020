package game;

public class Room {

	private String name;
	private String desc; //description
	private String afarDesc; //the short description describing exits to this room.
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

		public ButtonFunction takeExit() {
			return new ButtonFunction() { 
				public void run() {
					Main.player.changeRoom(destinationId);
				}
			};
		}

	}

	//rooms are made by parsing text from the rooms file. The string passed into room should therefore be parsed from that file.
	public Room(String text) {

		String[] lines = text.split("\n");
		int id = Integer.parseInt( lines[0].substring( lines[0].indexOf(" ") + 1, lines[0].length()));
		name = lines[1];
		desc = lines[2];
		afarDesc = lines[4];

		String[] exitStrings = lines[3].split(";");
		exits = new Exit[exitStrings.length];

		for(int i = 0; i < exits.length; i++) {

			if(!exitStrings[i].isEmpty()) {
				exits[i] = new Exit(exitStrings[i]);
			}
		}

		Main.putRoom(id, this);

	}

	//TODO items and characters are processed after rooms are initialized, read from a save file (or the default file if starting a new game)

	public String getName() {
		return name;
	}

	public String getDesc() {
		return desc;
	}

	public void addExitButtonsTo(WorldPanel p) {
		p.tempLabelNames = new String[exits.length];
		Exit e;

		String curParentName = "EXITS"; // name of the parent button of the next button to be made
		for (int i = 0; i < exits.length; i++) {
			e = exits[i];
			String uppercaseName = e.getName().toUpperCase();
			String hoverText = "You see " + Main.getRoom(e.destinationId).afarDesc + " that way.";
			Button b = new Button(uppercaseName, e.getName(), 20f, curParentName, p, e.takeExit(), hoverText);

			b.makeTemporary();
			curParentName = uppercaseName;
			p.tempLabelNames[i] = curParentName;
		}
	}

	//if any of the exits start with the given sequence, takes that exit.
	public void checkExit(String s) {
		for(Exit e : exits) {
			if(e.name.toLowerCase().startsWith(s.toLowerCase())) {
				Main.changeRoom(e.destinationId);
			}
		}
	}

	// The following commented-out chunk is presently obsolete after implementing addExitButtonsTo(WorldPanel p)
	/*public String getExitNames() {
		String names = "";

		for(int i = 0; i < exits.length; i++) {
			names += exits[i].getName();

			if(i != exits.length - 1) {
				names += ", ";
			}
		}

		return names;
	}



	// gives the string describing the exit, in the format (EXIT NAME), you see (EXIT DESTINATION'S DESCRIPTION).
	public String getExitString() {
		String s = "";

		for(Exit e : exits) {
			s += e.name + ", you see " + Main.getRoom(e.destinationId).afarDesc + ".\n";
		}

		//substringing to fencepost the trailing space
		return s.substring(0, s.length()-1);

	}*/

}
