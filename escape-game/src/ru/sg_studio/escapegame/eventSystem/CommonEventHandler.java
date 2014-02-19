package ru.sg_studio.escapegame.eventSystem;

import ru.sg_studio.RootObject;
import ru.sg_studio.escapegame.IProxiedObject;
import net.raydeejay.escapegame.Reactor;
import net.raydeejay.gossip.engine.interpreter.SmallInterpreter.ActionThread;

public abstract class CommonEventHandler extends RootObject implements IProxiedObject {

	//private Object bindedProxy;
	//public Object getCommonBindedProxy(){
	//	return bindedProxy;
	//}	
	@Override
	public CommonEventHandler getBinded(){
		return this;
	}
	
	
	private ActionThread threadToExec;
	
	private EventType type;
	public EventType getType() {
		return this.type;
	}

	public CommonEventHandler(EventType type, Reactor target, ActionThread thread){
		System.out.println("Attempting to attach -"+type.toString()+"- listener to: "+target.getName());
		
		this.threadToExec=thread;
		this.type=type;
		attachMonopolisticListener(target);
	}
	
	protected abstract void attachMonopolisticListener(Reactor target);
	
	protected void execute(){
		threadToExec.run();
	}
	
	public enum EventType{
		unspecified,
		onClick
	}
}
