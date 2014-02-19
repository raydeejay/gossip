package ru.sg_studio.escapegame;

import java.io.File;
import java.io.InputStream;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptException;

import net.raydeejay.escapegame.GameRegistry;
import net.raydeejay.gossip.engine.GossipScriptFactory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public final class GossipVM {

	//TODO: Decouple GDX
	
	
	public void load(){
		initImage();
	}
	
	private void initImage(){
		// if there is an image in the local directory, load from there
		// otherwise load from internal
		
		boolean fromDisk = new File("gossip/Gossip.image").exists();
		FileHandle imageFile;
		
		if(fromDisk) {
			System.out.println("Out-of-jar operation mode!");
			imageFile = Gdx.files.local("gossip/Gossip.image");
			
		} else {
			System.out.println("In-jar operation mode!");
			imageFile = Gdx.files.internal("gossip/Gossip.image");
		}
		
		// initialize Gossip
//		FileHandle imageFile = Gdx.files.internal("gossip/Gossip.image");
		InputStream is = null;
		try {
			is = imageFile.read();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
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
		
		GameRegistry.instance().setScreen(screen);
		
	}
	
}
