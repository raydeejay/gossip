package net.raydeejay.escapegame;

import java.util.ArrayList;

import net.raydeejay.escapegame.reactors.Item;
import net.raydeejay.escapegame.screens.GameScreen;

public class Inventory {
	ArrayList<Item> items;
	private String backgroundFilename;
	private GameScreen screen;
	private Item selectedItem;

	public Inventory(String aFilename, GameScreen gameScreen) {
		this.items = new ArrayList<Item>();
		this.setBackgroundFilename(aFilename);
		this.setScreen(gameScreen);
	}

	public String getBackgroundFilename() {
		return backgroundFilename;
	}

	public void setBackgroundFilename(String backgroundFilename) {
		this.backgroundFilename = backgroundFilename;
	}

	public GameScreen getScreen() {
		return screen;
	}

	public void setScreen(GameScreen screen) {
		this.screen = screen;
	}

	public void addItem(Item anItem) {
		anItem.setInventory(this);
		this.items.add(anItem);
		
		// calculate coordinates
		int index = this.items.indexOf(anItem);
		int x = 2 + (48 * (index % 2)); 
		int y = 6 + (60 * (index / 2));
		
		// set them, with the appropriate offsets
		// TODO - this is extremely ugly, refactor it
		anItem.setX(700 + x);
		anItem.setY(480 - y - 48);
	}

	public void removeItem(Item anItem) {
		if (this.getSelectedItem() == anItem) {
			this.setSelectedItem(null);
		}
		anItem.setInventory(null);
		this.items.remove(anItem);
		anItem.remove();
	}

	public ArrayList<Item> getItems() {
		return this.items;
	}

	public Item getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(Item selectedItem) {
		this.selectedItem = selectedItem;
	}

}
