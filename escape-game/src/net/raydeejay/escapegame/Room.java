package net.raydeejay.escapegame;

import java.util.ArrayList;


import ru.sg_studio.escapegame.primitives.GraphicalEntity;


public class Room {
	ArrayList<GraphicalEntity> graphicals;
	private String backgroundFilename;
	private String name;

	public ArrayList<GraphicalEntity> getReactors() {
		return this.graphicals;
	}

	public Room(String name, String aFilename) {
		this.graphicals = new ArrayList<GraphicalEntity>();
		this.setBackgroundFilename(aFilename);
		this.setName(name);
		GameRegistry.instance().registerRoom(name, this);
	}

	public Room(String name) {
		this.graphicals = new ArrayList<GraphicalEntity>();
		this.setName(name);
		GameRegistry.instance().registerRoom(name, this);
	}

	public String getName() {
		return this.name;
	}

	public Room setName(String name) {
		this.name = name;
		return this;
	}
	
	
	public Room addGraphicalEntityNamed(String aName){
		GraphicalEntity ge = GameRegistry.instance().getReactor(aName);
		addGraphicalEntity(ge);
		return this;//ST everywhere...
	}
	public Room addGraphicalEntity(GraphicalEntity ge){
		if(ge instanceof Reactor){((Reactor)ge).setRoom(this);}//HACK!
		
		this.graphicals.add(ge);
		GameRegistry.instance().getScreen().addGraphical(ge);
		
		return this;//ST everywhere...
	}
	
	@Deprecated
	public Room addReactor(Reactor aReactor) {
		aReactor.setRoom(this);
		this.graphicals.add(aReactor);
		GameRegistry.instance().getScreen().addReactor(aReactor);
		return this;
	}
	

	@Deprecated
	public Room addReactorNamed(String aName) {
		Reactor aReactor = GameRegistry.instance().getReactor(aName);
		return this.addReactor(aReactor);
	}

	public Room addToInventory(Item anItem) {
		GameRegistry.instance().getScreen().addToInventory(anItem);
		return this;
	}

	public String getBackgroundFilename() {
		return backgroundFilename;
	}

	public Room setBackgroundFilename(String backgroundFilename) {
		this.backgroundFilename = backgroundFilename;
		return this;
	}

	public Room removeReactor(Reactor aReactor) {
		aReactor.setRoom(null);
		this.graphicals.remove(aReactor);
		aReactor.remove();
		return this;
    }

}
