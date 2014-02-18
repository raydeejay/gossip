package net.raydeejay.escapegame;

import java.util.ArrayList;

public class Inventory {
	ArrayList<Item> items;
	private String backgroundFilename;
	private Item selectedItem;

	public Inventory(String aFilename) {
		this.items = new ArrayList<Item>();
		this.setBackgroundFilename(aFilename);
	}

	public String getBackgroundFilename() {
		return backgroundFilename;
	}

	public void setBackgroundFilename(String backgroundFilename) {
		this.backgroundFilename = backgroundFilename;
	}

	public void addItem(Reactor anItem) {
		anItem.setRoom(null);
		this.items.add((Item) anItem);
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
