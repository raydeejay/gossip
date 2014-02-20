package net.raydeejay.escapegame;

import ru.sg_studio.escapegame.IProxiedObject;

/*
 * An Item is a Reactor in an Inventory, or something like that.
 */

public class Item extends Reactor {

	protected boolean isSelected;

	/*
	 * This method creates an item from Reactor(an Obtainable basically)
	 */
	public Item(Reactor aReactor, IProxiedObject proxied) {
		super(aReactor.getName() + "Item", proxied);
		setImageTexture(aReactor.getImage());
	}

	/*
	 * This method creates item from sratch
	 */
	public Item(String aName, IProxiedObject proxied) {
		super(aName,proxied);
		
	}


	


	public boolean getIsSelected(){
		return isSelected;
	}
	public void beSelected() {
		isSelected = true;
	}
	
	public void beUnselected() {
		isSelected = false;
	}
	
}
