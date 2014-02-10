package net.raydeejay.escapegame;

import java.util.HashMap;
import java.util.Map;

import net.raydeejay.escapegame.screens.GameScreen;

public class GameRegistry {
	private Map<String, Reactor> objects = new HashMap<String, Reactor>();
	private Map<String, Room> rooms = new HashMap<String, Room>();
	private GameScreen gameScreen;
	
	private static GameRegistry instance;
	public static GameRegistry instance() {
		if (instance == null) {
			instance = new GameRegistry();
		}
		
		return instance;
	}
	
    public void registerReactor(String name, Reactor aReactor) {
        objects.put(name, aReactor);
    }

    public Reactor getReactor(String name) {
        return objects.get(name);
    }
	
    public Reactor newReactor(String name) {
        return new Reactor(name, gameScreen);
    }
	
    public Room newRoom(String name) {
        return new Room(name, gameScreen);
    }
	
    public void setScreen(GameScreen screen) {
    	gameScreen = screen;
    }
    
    public void registerRoom(String name, Room aRoom) {
        rooms.put(name, aRoom);
    }

    public Room getRoom(String name) {
        return rooms.get(name);
    }
}
