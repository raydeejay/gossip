package net.raydeejay.escapegame;

import java.util.ArrayList;

public class Room {
	ArrayList<Reactor> reactors;
	private String backgroundFilename;
	
	public ArrayList<Reactor> getReactors() {
		return this.reactors;
	}

	public Room(String aFilename) {
		this.reactors = new ArrayList<Reactor>();
		this.setBackgroundFilename(aFilename);
	}
	
	public void addReactor(Reactor aReactor) {
		aReactor.setRoom(this);
		this.reactors.add(aReactor);
	}

	public String getBackgroundFilename() {
		return backgroundFilename;
	}

	public void setBackgroundFilename(String backgroundFilename) {
		this.backgroundFilename = backgroundFilename;
	}

}
