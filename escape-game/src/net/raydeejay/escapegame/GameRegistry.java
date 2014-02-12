package net.raydeejay.escapegame;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import net.raydeejay.escapegame.reactors.Door;
import net.raydeejay.escapegame.reactors.Item;
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
	
    public void setScreen(GameScreen screen) {
    	gameScreen = screen;
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

    // factories
    public Room newRoom(String name) {
        return new Room(name, gameScreen);
    }
	
    public Reactor newReactor(String name) {
        return new Reactor(name, gameScreen);
    }
	
    public Door newDoor(String name) {
        return new Door(name, gameScreen);
    }
    
    // TODO - remove this once it's not necessary
    public Item getSelectedItem() {
    	return gameScreen.getInventory().getSelectedItem();
    }
	
    public void clearSelectedItem() {
    	gameScreen.getInventory().clearSelectedItem();
    }
	
    public void removeItem(Item anItem) {
    	gameScreen.getInventory().removeItem(anItem);
    }
	
	public void switchToRoom(final String aRoomName) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				// post a Runnable to the rendering thread
				Gdx.app.postRunnable(new Runnable() {
					@Override
					public void run() {
						gameScreen.switchToRoom(aRoomName);
					}
				});
			}
		}).start();
	}
	
}
