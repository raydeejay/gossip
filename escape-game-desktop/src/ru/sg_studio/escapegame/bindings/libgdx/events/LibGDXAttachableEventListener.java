package ru.sg_studio.escapegame.bindings.libgdx.events;

import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.Array;

import net.raydeejay.escapegame.Reactor;
import net.raydeejay.gossip.engine.interpreter.SmallInterpreter.ActionThread;
import ru.sg_studio.RootObject;
import ru.sg_studio.escapegame.IProxiedObject;
import ru.sg_studio.escapegame.bindings.libgdx.primitives.LibGDXReactor;
import ru.sg_studio.escapegame.eventSystem.CommonEventHandler;

public class LibGDXAttachableEventListener extends CommonEventHandler implements  EventListener {

	public LibGDXAttachableEventListener(EventType type, LibGDXReactor reactor,ActionThread thread) {
		super(type,(Reactor) reactor.getBinded(),thread);
		
	}

	@Override
	protected void attachMonopolisticListener(Reactor reactor) {
		LibGDXReactor gdxreactor =  (LibGDXReactor) reactor.getCommonBindedProxy();
		ClearListeners(gdxreactor);
		if(gdxreactor.addListener(this)){
			System.out.println("Listener attached to "+reactor.getName());
		}else{
			System.out.println("Listener failed to attach to "+reactor.getName());
		}
	}

	@Override
	public boolean handle(Event event) {
		System.out.println("Event!");
		if(getType()==EventType.onClick){
			if(event instanceof InputEvent){
				
				InputEvent ie = (InputEvent)event;
				if(ie.getType()==InputEvent.Type.touchDown){
					execute();
					return true;
				}
				
			}else{
				return false;
			}
		}
		return false;
	}



	private void ClearListeners(LibGDXReactor reactor){
		// we only want one listener attached at a time
		Array<EventListener> listeners = reactor.getListeners();
		for( EventListener e : listeners) {
			reactor.removeListener(e);
		}
	}

	@Override
	public void trySyncGraphicalObject() {
		return;
		//No need
	}


	
	

}
