package game;

public class GameCharacter {

	protected int curRoom = 1;
	
	public void changeRoom(int to) {
		curRoom = to;
		System.out.println("SWITCH");
	}
	
}
