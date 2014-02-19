package ru.sg_studio.escapegame;

import ru.sg_studio.escapegame.ContextedObjectProvider.ObjectPrototype;
import ru.sg_studio.escapegame.primitives.GraphicalEntity;

public class ContextedFactory {

	private static ContextedFactory me;
	public static ContextedFactory instance(){
		if(me==null){
			me = new ContextedFactory();
		}
		return me;
	}
	
	private ContextedObjectProvider provider;
	public void setContextedObjectProvider(ContextedObjectProvider provider){
		this.provider=provider;
	}
	
	public IProxiedObject getContextedItem(ObjectPrototype prototype, Object ... params){
		return provider.getContextedItemSafely(prototype, params);
	}
	
	
	
	
}
