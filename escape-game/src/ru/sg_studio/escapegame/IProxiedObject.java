package ru.sg_studio.escapegame;

import ru.sg_studio.RootObject;
import ru.sg_studio.escapegame.primitives.GraphicalEntity;

public interface IProxiedObject {

	//This is a proxy interface
	public RootObject getBinded();
	
	
	public void trySyncGraphicalObject();
	
}
