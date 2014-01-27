package net.raydeejay.escapegame.reactors;

import net.raydeejay.escapegame.Inventory;
import net.raydeejay.escapegame.Reactor;
import net.raydeejay.escapegame.rooms.Location;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

/*
 * An Item is a Reactor in an Inventory, or something like that.
 */

public class Item extends Reactor {

	private Inventory inventory;

	public Item(String name, int x, int y, String aFilename) {
		super(x, y, aFilename);
		this.setName(name);
	}

	@Override
	protected void setUpListeners() {
		this.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				Item item = getInventory().getSelectedItem();
				if (item == null) {
					whenClicked();
				} else {
					whenClickedWith(item);
				}
				return true;
			}
		});
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.setColor(Color.WHITE);
		batch.draw(this.getImage(), this.getX(), this.getY(), 32, 32);
	}

	@Override
	public void whenClicked() {
		if (!this.isSelected()) {
			this.beSelected();
		} else {
			this.beUnselected();
		}
	}

	private void beUnselected() {
		this.getInventory().setSelectedItem(null);
	}

	private void beSelected() {
		this.getInventory().setSelectedItem(this);
	}

	private boolean isSelected() {
		return (this.getInventory().getSelectedItem() == this);
	}

	// INVENTORY
	public Inventory getInventory() {
		return this.inventory;
	}

	public Location getLocation() {
		return this.getInventory();
	}

	public void setInventory(Inventory anInventory) {
		this.inventory = anInventory;
	}

}
