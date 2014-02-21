package ru.sg_studio.escapegame;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptException;

import ru.sg_studio.escapegame.eventSystem.VMExecCycler;
import ru.sg_studio.reports.Reporter;
import net.raydeejay.escapegame.GameRegistry;
import net.raydeejay.gossip.engine.GossipScriptFactory;

public final class GossipVM {

	private static GossipVM me;
	public GossipVM(boolean imageOnlyMode){
		me=this;
		this.imageOnlyMode = imageOnlyMode;
	}
	
	private boolean imageOnlyMode=false;
	
	
	VMExecCycler cycler = new VMExecCycler();
	
	private static final String GOSSIP_IMAGE = "/gossip/Gossip.image";

	public void load(){
		initImage();
		cycler.initCycler(this);
	}
	
	ScriptEngine engine;
	public String directEvaluation(String command) throws ScriptException{
		try{
			return engine.eval(command).toString();
		}catch(Exception e){
			//TODO: Add debugging facilities
			return "------CRITICAL SCRIPT ENGINE FAILURE------\n"+e+"\n------------------------------------------";
		}
	}
	
	private void initImage(){
		// if there is an image in the local directory, load from there
		// otherwise load from internal
		
		File image = new File(GOSSIP_IMAGE);
		boolean fromDisk = image.exists();
		//FileHandle imageFile;
		InputStream is = null;
		if(fromDisk) {
			System.out.println("Out-of-jar operation mode!");

			try {
				is = new FileInputStream(image);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			System.out.println("In-jar operation mode!");

			is = SmallInterpreterInterfacer.class.getResourceAsStream(GOSSIP_IMAGE);
		}
		

		
		ScriptEngineFactory factory = new GossipScriptFactory();
        engine = ((GossipScriptFactory) factory).getScriptEngineWithImage(is);
        
        if(!imageOnlyMode){
	        
	    	// monkeypatch Gdx file access into the image
			if(! fromDisk) {
				System.out.println("Patching reading...");
		        try {
					System.out.println(directEvaluation("File class compileMethod: 'openRead: aName ^ <142 self (aName printString)>'"));
				} catch (ScriptException e1) {
					e1.printStackTrace();
				}
			}
			
			// load code not shipped in the image
			System.out.println("Loading additional script...");
	        try {
	            System.out.println(directEvaluation("File fileIn: 'gossip/Test.st'"));
	        }
	        catch(ScriptException e) { System.err.println("ERROR :" + e); }		
        }else{
        	System.out.println("Executing overriden editor initializers...");
 	        try {
	            System.out.println(directEvaluation("Transcript open"));
	        }
	        catch(ScriptException e) { System.err.println("ERROR :" + e); }	       	
        }
		
	}

	public void provideInitialScreen(GameScreen screen) {
		screen.registerVMHost(this);
		GameRegistry.instance().setScreen(screen);
		
	}

	MessagingQueue queue = new MessagingQueue();
	
	public MessagingQueue getMessagePipe() {
		return queue;
	}
	public void runQueue(){
		Runnable r = queue.pop();
		if(r!=null){
			r.run();
		}
	}

	public static GossipVM GetLastCreated() {
		return me;
	}
	
	
}
