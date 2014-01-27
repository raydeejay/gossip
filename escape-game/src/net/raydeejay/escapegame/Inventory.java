package net.raydeejay.escapegame;

import java.util.ArrayList;

import net.raydeejay.escapegame.reactors.Item;
import net.raydeejay.escapegame.rooms.Location;
import net.raydeejay.escapegame.screens.GameScreen;

public class Inventory implements Location {
	ArrayList<Item> items;
	private String backgroundFilename;
	private GameScreen screen;
	private Item selectedItem;

	public ArrayList<Item> getItems() {
		return this.items;
	}

	public Inventory(String aFilename, GameScreen gameScreen) {
		this.items = new ArrayList<Item>();
		this.setBackgroundFilename(aFilename);
		this.setScreen(gameScreen);
	}

	public void addItem(Item anItem) {
		anItem.setInventory(this);
		this.items.add(anItem);
//		this.getScreen().addReactor(anItem);
	}

	public String getBackgroundFilename() {
		return backgroundFilename;
	}

	public void setBackgroundFilename(String backgroundFilename) {
		this.backgroundFilename = backgroundFilename;
	}

	public void removeItem(Item anItem) {
		if (this.getSelectedItem() == anItem) {
			this.setSelectedItem(null);
		}
		anItem.setInventory(null);
		this.items.remove(anItem);
		anItem.remove();
	}

	public GameScreen getScreen() {
		return screen;
	}

	public void setScreen(GameScreen screen) {
		this.screen = screen;
	}

	public Item getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(Item selectedItem) {
		this.selectedItem = selectedItem;
	}

}
