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
		this.items.add(anItem);

	}

	public void removeItem(Item anItem) {
		if (this.getSelectedItem() == anItem) {
			this.setSelectedItem(null);
		}
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

	public void clearSelectedItem() {
		setSelectedItem(null);
	}

}
