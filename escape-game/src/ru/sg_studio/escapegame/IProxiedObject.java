package ru.sg_studio.escapegame;

import ru.sg_studio.RootObject;

public interface IProxiedObject {

	//This is a proxy interface
	public RootObject getBinded();
	
	
	public void trySyncGraphicalObject();


	public void sendLoadSignal();
	
}
