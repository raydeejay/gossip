package ru.sg_studio.escapegame.bindings.libgdx;

import net.raydeejay.escapegame.Reactor;
import net.raydeejay.gossip.engine.interpreter.SmallInterpreter.ActionThread;
import ru.sg_studio.escapegame.ContextedObjectProvider;
import ru.sg_studio.escapegame.IProxiedObject;
import ru.sg_studio.escapegame.bindings.libgdx.events.LibGDXAttachableEventListener;
import ru.sg_studio.escapegame.bindings.libgdx.primitives.LibGDXBackground;
import ru.sg_studio.escapegame.bindings.libgdx.primitives.LibGDXItem;
import ru.sg_studio.escapegame.bindings.libgdx.primitives.LibGDXReactor;
import ru.sg_studio.escapegame.bindings.libgdx.primitives.resources.LibGDXSoundResource;
import ru.sg_studio.escapegame.bindings.libgdx.primitives.topclass.ui.LibGDXTextLabel;
import ru.sg_studio.escapegame.eventSystem.CommonEventHandler.EventType;

public class LibGDXObjectProvider extends ContextedObjectProvider {

	@Override
	public IProxiedObject getContextedItem(ObjectPrototype prototype, Object[] params) {
		switch(prototype){
		case Reactor:
			return new LibGDXReactor((String)params[0]);
		case Background:
			return new LibGDXBackground((String)params[0],(Integer)params[1],(Integer)params[2],(String)params[3]);
			//NAME, X,Y, TEXNAME
		case Item:
			if(params.length==1){
				return new LibGDXItem((Reactor)params[0]);
			}else{
				System.out.println("Not Implemented");
			}
		case UITextLabel:
			return new LibGDXTextLabel((String)params[0]);
		case SoundResource:
			return new LibGDXSoundResource((String)params[0]);
						
		case EventListener:
			return  new LibGDXAttachableEventListener((EventType)params[0], (LibGDXReactor)((Reactor)params[1]).getCommonBindedProxy(),(ActionThread)params[2]);
		default:
			return null;
		
		}
		
	}

}
