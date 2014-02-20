package net.raydeejay.escapegame;

import ru.sg_studio.escapegame.IProxiedObject;


public class Background extends Reactor {


	public Background(String name,int x, int y, String aFilename, IProxiedObject bindedProxy){
		super(name,bindedProxy);


		setImage(aFilename);
		


		setX(x);
		setY(y);
		
	}


}