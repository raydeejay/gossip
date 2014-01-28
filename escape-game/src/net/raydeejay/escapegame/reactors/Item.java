package net.raydeejay.escapegame.reactors;

import net.raydeejay.escapegame.Inventory;
import net.raydeejay.escapegame.Reactor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

/*
 * An Item is a Reactor in an Inventory, or something like that.
 */

public class Item extends Reactor {

	private Inventory inventory;

	public Item(String name, float x, float y, String aFilename) {
		super(name, x, y, aFilename);
	}

	public Item(String name, String aFilename) {
		super(name, 0, 0, aFilename);
	}

	public Item(Reactor aReactor) {
		super(aReactor.getName() + "Item", 0, 0, aReactor.getImage());
	
	}
	
	@Override
	protected void setUpListeners() {
		this.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				Item item = getInventory().getSelectedItem();
				if (item == null || isSelected()) {
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
		if (this.isSelected()) {
			batch.setColor(Color.rgba8888(0, 0.5f, 0.5f, 0.4f));
		} else {
			batch.setColor(Color.WHITE);
		}
		batch.draw(this.getImage(), this.getX(), this.getY(), 48, 48);
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

	public void setInventory(Inventory anInventory) {
		this.inventory = anInventory;
	}

	@Override
	public void whenClicked() {
		if (!this.isSelected()) {
			this.beSelected();
		} else {
			this.beUnselected();
		}
	}

}
