package net.raydeejay.escapegame.reactors;

import net.raydeejay.escapegame.Reactor;
import net.raydeejay.escapegame.screens.GameScreen;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

/*
 * An Item is a Reactor in an Inventory, or something like that.
 */

public class Item extends Reactor {

	public Item(Reactor aReactor, GameScreen aScreen) {
		super(aReactor.getName() + "Item", aScreen);
		setImage(aReactor.getImage());
	}

	public Item(String aName, GameScreen aScreen) {
		super(aName, aScreen);
	}

	@Override
	protected void setUpListeners() {
		this.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				Item item = GameScreen.getInventory().getSelectedItem();
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
		// calculate coordinates
		int index = GameScreen.getInventory().getItems().indexOf(this);
		int x = 2 + (48 * (index % 2));
		int y = 6 + (60 * (index / 2));

		// set them, with the appropriate offsets
		// TODO - this is extremely ugly, refactor it
		this.setX(700 + x);
		this.setY(480 - y - 48);

		// add "halo" if the item is selected
		if (this.isSelected()) {
			batch.setColor(Color.rgba8888(0, 0.5f, 0.5f, 0.4f));
		} else {
			batch.setColor(Color.WHITE);
		}
		
		batch.draw(this.getImage(), this.getX(), this.getY(), 48, 48);
	}

	private Item beUnselected() {
		GameScreen.getInventory().setSelectedItem(null);
		return this;
	}

	private Item beSelected() {
		GameScreen.getInventory().setSelectedItem(this);
		return this;
	}

	private boolean isSelected() {
		return (GameScreen.getInventory().getSelectedItem() == this);
	}

	// INVENTORY
	public Item removeFromInventory() {
		GameScreen.getInventory().removeItem(this);
		return this;
	}

	@Override
	public void whenClicked() {
		if (!this.isSelected()) {
			this.beSelected();
		} else {
			this.beUnselected();
		}
	}

	public void addToInventory() {
		GameScreen.getInventory().addItem(this);
		
	}

}
