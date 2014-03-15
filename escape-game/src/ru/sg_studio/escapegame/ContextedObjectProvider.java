package ru.sg_studio.escapegame;

import ru.sg_studio.escapegame.exceptions.CriticalNonDefinedContextedEntityException;

public abstract class ContextedObjectProvider {

	public IProxiedObject getContextedItemSafely(ObjectPrototype prototype, Object[] params){
		IProxiedObject entity = getContextedItem(prototype,params);
		if(entity==null){
			throw new CriticalNonDefinedContextedEntityException();
		}else{
			return entity;
		}
	}
	protected abstract IProxiedObject getContextedItem(ObjectPrototype prototype, Object[] params);
	
	
	public enum ObjectPrototype{
		Reactor,
		Background,
		Item,
		
		
		EventListener, 
		
		UITextLabel
	}
	
}
