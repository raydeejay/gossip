package net.raydeejay.escapegame;

import java.util.HashMap;
import java.util.Map;

import ru.sg_studio.escapegame.ContextedFactory;
import ru.sg_studio.escapegame.ContextedObjectProvider.ObjectPrototype;
import ru.sg_studio.escapegame.GameScreen;
import ru.sg_studio.escapegame.GossipVM;
import ru.sg_studio.escapegame.primitives.topclass.ui.TextLabel;
import ru.sg_studio.escapegame.primitives.resources.Sound;

//Total mess... Needs total rewrite...

public class GameRegistry {
	private Map<String, Reactor> objects = new HashMap<String, Reactor>();
	private Map<String, Room> rooms = new HashMap<String, Room>();
	private GameScreen gameScreen;
	private Inventory inventory;
	
	private static GameRegistry instance;
	public static GameRegistry instance() {
		if (instance == null) {
			instance = new GameRegistry();
		}
		
		return instance;
	}
	
    public void setScreen(GameScreen screen) {
    	System.out.println("Setting Screen: "+screen);
    	gameScreen = screen;
    	gameScreen.registerInPipeline();
    }
    
    public GameScreen getScreen() {
    	return gameScreen;
    }
    
    public void registerReactor(String name, Reactor aReactor) {
        objects.put(name, aReactor);
    }

    public void registerRoom(String name, Room aRoom) {
        rooms.put(name, aRoom);
    }

    public Reactor getReactor(String name) {
        return objects.get(name);
    }
	
    public Room getRoom(String name) {
        return rooms.get(name);
    }

    public Inventory getInventory() {
        return inventory;
    }

    public Room getCurrentRoom() {
    	return gameScreen.currentRoom;
    }
    
    public void setInventory(Inventory i) {
        inventory = i;
    }

    // factories
    public Room newRoom(String name) {
        return new Room(name);
    }
	
    public Reactor newReactor(String name) {
        //return new Reactor(name);
    	System.out.println("Instancing new reactor: "+name);
    	return ((Reactor) (ContextedFactory.instance().getContextedItem(ObjectPrototype.Reactor,name)).getBinded());
    }
    public TextLabel newUITextLabel(String name){
    	System.out.println("Instancing new UITextLabel: "+name);
    	return ((TextLabel) (ContextedFactory.instance().getContextedItem(ObjectPrototype.UITextLabel,name)).getBinded());
    }
    
     public Sound newSoundResource(String name){
    	System.out.println("Instancing new SoundResource: "+name);
    	return ((Sound) (ContextedFactory.instance().getContextedItem(ObjectPrototype.SoundResource,name)).getBinded());
    }   
	
    // TODO - remove this once it's not necessary
    public Item getSelectedItem() {
    	return inventory.getSelectedItem();
    }
	
    public void setSelectedItem(Reactor anItem) {
    	inventory.setSelectedItem((Item) anItem);
    }
	
    public void clearSelectedItem() {
    	inventory.clearSelectedItem();
    }
	
    public void addToInventory(Reactor anItem) {
    	gameScreen.addToInventory((Item) anItem);
    }
	
    public void removeItem(Item anItem) {
    	inventory.removeItem(anItem);
    }
	
	public void switchToRoom(final String aRoomName) {
		//Broken
		//gameScreen.getHost().getMessagePipe().push(new Runnable() {
		GossipVM.GetLastCreated().getMessagePipe().push(new Runnable() {
			@Override
			public void run() {
				gameScreen.switchToRoom(aRoomName);
			};
		});


	}
	
//	public void winGame() {
//		try {
//			GossipVM.GetLastCreated().directEvaluation("File fileIn: 'gossip/WinGame.st'");
//		} catch (ScriptException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	}
}
