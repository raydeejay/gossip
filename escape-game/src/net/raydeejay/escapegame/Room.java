package net.raydeejay.escapegame;

import java.util.ArrayList;

public class Room {
	ArrayList<Reactor> reactors;
	private String backgroundFilename;
	private String name;

	public ArrayList<Reactor> getReactors() {
		return this.reactors;
	}

	public Room(String name, String aFilename) {
		this.reactors = new ArrayList<Reactor>();
		this.setBackgroundFilename(aFilename);
		this.setName(name);
		GameRegistry.instance().registerRoom(name, this);
	}

	public Room(String name) {
		this.reactors = new ArrayList<Reactor>();
		this.setName(name);
		GameRegistry.instance().registerRoom(name, this);
	}

	public String getName() {
		return this.name;
	}

	public Room setName(String name) {
		this.name = name;
		return this;
	}

	public Room addReactor(Reactor aReactor) {
		aReactor.setRoom(this);
		this.reactors.add(aReactor);
		GameRegistry.instance().getScreen().addReactor(aReactor);
		return this;
	}

	public Room addReactorNamed(String aName) {
		Reactor aReactor = GameRegistry.instance().getReactor(aName);
		return this.addReactor(aReactor);
	}

	public Room addToInventory(Item anItem) {
		GameRegistry.instance().getScreen().addToInventory(anItem);
		return this;
	}

	public String getBackgroundFilename() {
		return backgroundFilename;
	}

	public Room setBackgroundFilename(String backgroundFilename) {
		this.backgroundFilename = backgroundFilename;
		return this;
	}

	public Room removeReactor(Reactor aReactor) {
		aReactor.setRoom(null);
		this.reactors.remove(aReactor);
		aReactor.remove();
		return this;
    }

}
