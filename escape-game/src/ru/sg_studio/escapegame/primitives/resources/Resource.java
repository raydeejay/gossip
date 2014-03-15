package ru.sg_studio.escapegame.primitives.resources;

import ru.sg_studio.RootObject;
import ru.sg_studio.escapegame.IProxiedObject;

public class Resource extends RootObject {

	
	
	//TODO: Needs unification with GE Proxy
	//Resource as father maybe?
	protected IProxiedObject bindedProxy;
	public IProxiedObject getCommonBindedProxy(){
		return bindedProxy;
	}
	
}
