package net.raydeejay.escapegame.reactors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;

import net.raydeejay.escapegame.Reactor;

/*
 * An Item is a Reactor that can be picked up, put in the inventory,
 * and used and/or combined with another Reactor
 */

public class Item extends Reactor {

	private boolean inInventory = false;
	private boolean selected = false;

	public Item(int x, int y, String aFilename) {
		super(x, y, aFilename);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.setColor(Color.WHITE);
		batch.draw(this.getImage(), 50, 50, 32, 32);
	}

	@Override
	public void whenClicked() {
		if (!this.isInInventory()) {
			this.beInInventory();
		} else if (!this.isSelected()) {
			this.beSelected();
		} else {
			this.beUnselected();
		}
	}

	private void beUnselected() {
		this.selected = false;
	}

	private void beSelected() {
		this.selected = true;
	}

	private boolean isSelected() {
		return this.selected;
	}

	private void beInInventory() {
		this.inInventory = true;
	}

	private boolean isInInventory() {
		return this.inInventory;
	}

}
