package net.raydeejay.escapegame.reactors;

import java.util.ArrayList;

import net.raydeejay.escapegame.Pair;
import net.raydeejay.escapegame.Reactor;

public class Movable extends Reactor {

	ArrayList<Pair<Integer, Integer>> coords;
	int currentCoordsIndex;

	public Movable(int x, int y, String aFilename) {
		super(x, y, aFilename);
		coords = new ArrayList<Pair<Integer, Integer>>();

		this.addCoords(x, y);
	}

	public void addCoords(int x, int y) {
		this.coords.add(new Pair<Integer, Integer>(x, y));
	}

	@Override
	public void whenClicked() {
		++currentCoordsIndex;
		if(this.currentCoordsIndex == this.coords.size()) {
			this.currentCoordsIndex = 0;
		}
			
		this.setX(coords.get(currentCoordsIndex).getLeft());
		this.setY(coords.get(currentCoordsIndex).getRight());
	}

}
