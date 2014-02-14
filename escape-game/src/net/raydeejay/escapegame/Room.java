package net.raydeejay.escapegame;

import java.util.ArrayList;

import net.raydeejay.escapegame.screens.GameScreen;

public class Room {
	ArrayList<Reactor> reactors;
	private String backgroundFilename;
	private GameScreen screen;
	private String name;

	private String exitLeft;
	private String exitRight;
	private String exitTop;
	private String exitBottom;

	public ArrayList<Reactor> getReactors() {
		return this.reactors;
	}

	public Room(String name, String aFilename, GameScreen gameScreen) {
		this.reactors = new ArrayList<Reactor>();
		this.setBackgroundFilename(aFilename);
		this.setScreen(gameScreen);
		this.setName(name);
		GameRegistry.instance().registerRoom(name, this);
	}

	public Room(String name, GameScreen gameScreen) {
		this.reactors = new ArrayList<Reactor>();
		this.setScreen(gameScreen);
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
		this.getScreen().addReactor(aReactor);
		return this;
	}

	public Room addReactorNamed(String aName) {
		Reactor aReactor = GameRegistry.instance().getReactor(aName);
		return this.addReactor(aReactor);
	}

	public Room addToInventory(Item anItem) {
		this.getScreen().addToInventory(anItem);
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

	public GameScreen getScreen() {
		return screen;
	}

	public Room setScreen(GameScreen screen) {
		this.screen = screen;
		return this;
	}

	public String getExitLeft() {
		return this.exitLeft;
	}

	public Room setExitLeft(String exitLeft) {
		this.exitLeft = exitLeft;
		return this;
	}

	public String getExitRight() {
		return this.exitRight;
	}

	public Room setExitRight(String exitRight) {
		this.exitRight = exitRight;
		return this;
	}

	public String getExitTop() {
		return this.exitTop;
	}

	public Room setExitTop(String exitTop) {
		this.exitTop = exitTop;
		return this;
	}

	public String getExitBottom() {
		return this.exitBottom;
	}

	public Room setExitBottom(String exitBottom) {
		this.exitBottom = exitBottom;
		return this;
	}

}
