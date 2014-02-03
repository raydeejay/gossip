package net.raydeejay.escapegame;

import java.util.HashMap;
import java.util.Map;

public class GameRegistry {
	private static Map<String, Reactor> objects = new HashMap<String, Reactor>();
	private static Map<String, Room> rooms = new HashMap<String, Room>();
	
    public static void registerReactor(String name, Reactor aReactor) {
        objects.put(name, aReactor);
    }

    public static Reactor getReactor(String name) {
        return objects.get(name);
    }
	
    public static void registerRoom(String name, Room aRoom) {
        rooms.put(name, aRoom);
    }

    public static Room getRoom(String name) {
        return rooms.get(name);
    }
}
