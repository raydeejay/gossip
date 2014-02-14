package net.raydeejay.escapegame;

import java.util.HashMap;
import java.util.Map;

import net.raydeejay.escapegame.screens.GameScreen;
import net.raydeejay.escapegame.screens.MainMenuScreen;

import com.badlogic.gdx.Gdx;

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

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory i) {
        inventory = i;
    }

    // factories
    public Room newRoom(String name) {
        return new Room(name, gameScreen);
    }
	
    public Reactor newReactor(String name) {
        return new Reactor(name, gameScreen);
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
	
	public void winGame() {
		EscapeGame game = gameScreen.getGame();
		game.setScreen(new MainMenuScreen(game));
		gameScreen.dispose();
	}
}
