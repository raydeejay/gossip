package ru.sg_studio.escapegame;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptException;

import net.raydeejay.escapegame.GameRegistry;
import net.raydeejay.gossip.engine.GossipScriptFactory;

public final class GossipVM {

	
	
	
	private static final String GOSSIP_IMAGE = "/gossip/Gossip.image";

	public void load(){
		initImage();
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
			//imageFile = Gdx.files.local("gossip/Gossip.image");
			//^___________^
			try {
				is = new FileInputStream(image);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			System.out.println("In-jar operation mode!");
			//imageFile = Gdx.files.internal("gossip/Gossip.image");
			//^___________^
			is = SmallInterpreterInterfacer.class.getResourceAsStream(GOSSIP_IMAGE);
		}
		
		// initialize Gossip
//		FileHandle imageFile = Gdx.files.internal("gossip/Gossip.image");
		
		//try {
		//	is = imageFile.read();
		//} catch (Exception e1) {
		//	e1.printStackTrace();
		//}
		
		ScriptEngineFactory factory = new GossipScriptFactory();
        ScriptEngine engine = ((GossipScriptFactory) factory).getScriptEngineWithImage(is);
        
    	// monkeypatch Gdx file access into the image
		if(! fromDisk) {
			System.out.println("Patching reading...");
	        try {
				System.out.println(engine.eval("File class compileMethod: 'openRead: aName ^ <142 self (aName printString)>'"));
			} catch (ScriptException e1) {
				e1.printStackTrace();
			}
		}
		
		// load code not shipped in the image
		System.out.println("Loading additional script...");
        try {
            System.out.println(engine.eval("File fileIn: 'gossip/Test.st'"));
        }
        catch(ScriptException e) { System.err.println("ERROR :" + e); }		
		
		
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
	
	
}
