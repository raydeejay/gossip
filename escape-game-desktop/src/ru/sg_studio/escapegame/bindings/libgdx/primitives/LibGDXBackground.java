package ru.sg_studio.escapegame.bindings.libgdx.primitives;

import net.raydeejay.escapegame.Background;

public class LibGDXBackground extends LibGDXReactor {

	public LibGDXBackground(String name, int x, int y, String texname) {
		super(name);
		coreObject = new Background(name, x, y, texname, this);
		
		trySyncGraphicalObject();
	}

}
