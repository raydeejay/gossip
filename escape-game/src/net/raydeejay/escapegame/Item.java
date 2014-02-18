package net.raydeejay.escapegame;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;

/*
 * An Item is a Reactor in an Inventory, or something like that.
 */

public class Item extends Reactor {

	private boolean isSelected;

	public Item(Reactor aReactor) {
		super(aReactor.getName() + "Item");
		setImageTexture(aReactor.getImage());
	}

	public Item(String aName) {
		super(aName);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		// calculate coordinates
		int index = GameRegistry.instance().getInventory().getItems().indexOf(this);
		int x = 2 + (48 * (index % 2));
		int y = 6 + (60 * (index / 2));

		// set them, with the appropriate offsets
		// TODO - this is extremely ugly, refactor it
		this.setX(700 + x);
		this.setY(480 - y - 48);

		// add "halo" if the item is selected
		if (isSelected) {
			batch.setColor(Color.rgba8888(0, 0.5f, 0.5f, 0.4f));
		} else {
			batch.setColor(Color.WHITE);
		}
		
		batch.draw(this.getImage(), this.getX(), this.getY(), 48, 48);
	}

	public void beSelected() {
		isSelected = true;
	}
	
	public void beUnselected() {
		isSelected = false;
	}
	
}
