package ru.sg_studio.escapegame.eventSystem;

import java.util.ArrayList;

import ru.sg_studio.escapegame.GossipVM;

/*
 * This class provides capabilities to run independent VM-sequenced execution loop
 */
public class VMExecCycler {

	private GossipVM myHost;
	private LooperThread looper;
	
	private boolean isThrottledDown=true;
	
	
	private DeltaUpdatersContainer deltas = new DeltaUpdatersContainer();
	
	private void executeDeltas(){
		for(DeltaUpdateHandler duh : deltas.getHandlers()){
			duh.run(myHost);
		}
	}
	
	public void addDUH(DeltaUpdateHandler handler){
		deltas.addHandler(handler);
	}
	
	public void initCycler(GossipVM directExecutionProvider) {
		myHost = directExecutionProvider;
		looper = new LooperThread();
		
		looper.start();
	}
	
	public void switchThrottling(boolean isThrottledDown){
		this.isThrottledDown=isThrottledDown;
	}
	
	private class LooperThread extends Thread{
		private static final int ONESECOND = 1000;
		private static final int BASIC_FRAMERATE = 20;

		public LooperThread(){
			super();
			setDaemon(true);
		}
		private boolean isExecuting=true;
		
		@Override
		public void run() {
			while(isExecuting){
				try {
					//System.out.println("debug: looper iteration");
					if(isThrottledDown){
						sleep(ONESECOND/BASIC_FRAMERATE);
					}else{
						sleep(0);
					}
					executeDeltas();
				} catch (InterruptedException e) {
					// TODO Shut thread down
					e.printStackTrace();
				}
			}
		}
		
	}
	
	
	private class DeltaUpdatersContainer{
		public DeltaUpdatersContainer(){
			rebake();
		}
		private boolean freezed;
		private DeltaUpdateHandler[] bakedHandlers;
		private ArrayList<DeltaUpdateHandler> handlers = new ArrayList<DeltaUpdateHandler>();
		synchronized public void addHandler(DeltaUpdateHandler handler){
			handlers.add(handler);
			rebake();
		}
		private void rebake() {
			//TODO: ugly lock of getHandlers(). Will induce RAREST race condition.
			//Needs threading specialist to look into it
			while(freezed){;}//Kill me please. Much room for improvements
			freezed = true;
			try{
				bakedHandlers = new DeltaUpdateHandler[handlers.size()];
				bakedHandlers = handlers.toArray(bakedHandlers);
			}finally{
				freezed = false;
			}
		}
		public DeltaUpdateHandler[] getHandlers(){
			while(freezed){;}//Kill me please. Much room for improvements
			return bakedHandlers;
		}
	}
	

}
