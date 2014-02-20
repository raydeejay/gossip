package ru.sg_studio.escapegame.bindings.libgdx.primitives;

import ru.sg_studio.escapegame.bindings.libgdx.LibGDXTextureWrapper;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;

import net.raydeejay.escapegame.GameRegistry;
import net.raydeejay.escapegame.Item;
import net.raydeejay.escapegame.Reactor;

public class LibGDXItem extends LibGDXReactor {

	public LibGDXItem(Reactor reactor){
		coreObject = new Item(reactor,this);
		
		trySyncGraphicalObject();
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		// calculate coordinates
		int index = GameRegistry.instance().getInventory().getItems().indexOf(this.coreObject);
		int x = 2 + (48 * (index % 2));
		int y = 6 + (60 * (index / 2));

		// set them, with the appropriate offsets
		// TODO - this is extremely ugly, refactor it
		//TODO: Do not refactor. This is not my refactoring
		this.setX(700 + x);
		this.setY(480 - y - 48);

		// add "halo" if the item is selected
		if (((Item)coreObject).getIsSelected()) {
			batch.setColor(Color.rgba8888(0, 0.5f, 0.5f, 0.4f));
		} else {
			batch.setColor(Color.WHITE);
		}
		//TODO: Ugly large
		batch.draw(((LibGDXTextureWrapper)((Reactor)getBinded()).getImage()).getGdxTexture(), this.getX(), this.getY(), 48, 48);
	}	
	
	
	
}
