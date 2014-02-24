package ru.sg_studio.escapegame.eventSystem;

import javax.script.ScriptException;

import ru.sg_studio.escapegame.GossipVM;

public class CycleUpdateHandler {

	//By default CUH is linked to Schedule, so there is no subset yet available
	
	public void run(GossipVM myHost) {
		passed++;
		if(passed>delay){
			passed=0;			
			
			try {
				myHost.directEvaluation(cachedJob);
			} catch (ScriptException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private int callID;
	private String cachedJob;
	public void setCallID(int callID){
		this.callID=callID;
		cachedJob = "Schedule call: "+this.callID;
	}
	private int delay;
	private int passed;
	public void setCyclesDelay(int delay){
		this.delay=delay;
	}
	

}
