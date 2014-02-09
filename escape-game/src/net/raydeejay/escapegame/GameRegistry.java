package net.raydeejay.escapegame;

import java.util.HashMap;
import java.util.Map;

public class GameRegistry {
	private static Map<String, Reactor> objects = new HashMap<String, Reactor>();
	private static Map<String, Room> rooms = new HashMap<String, Room>();
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
	
    public void registerRoom(String name, Room aRoom) {
        rooms.put(name, aRoom);
    }

    public Room getRoom(String name) {
        return rooms.get(name);
    }
}
