package remoteClient;

public enum Commands {
	PRESS_MOUSE(-1),
	RELEASE_MOUSE(-2),
	PRESS_KEY(-3),
	RELEASE_KEY(-4),
	MOVE_MOUSE(-5);
	
	private int abbrev;
	private Commands(int abbrev) {
		// TODO Auto-generated constructor stub
		this.abbrev = abbrev;
	}
	
	public int getAbbrev() { 
		return abbrev;
	}
}
