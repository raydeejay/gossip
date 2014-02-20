package ru.sg_studio.escapegame;

import ru.sg_studio.escapegame.ContextedObjectProvider.ObjectPrototype;
import net.raydeejay.escapegame.Background;
import net.raydeejay.escapegame.GameRegistry;
import net.raydeejay.escapegame.Inventory;
import net.raydeejay.escapegame.Item;
import net.raydeejay.escapegame.Reactor;
import net.raydeejay.escapegame.Room;


public abstract class GameScreen {

	ReactorDepot depot;
	
	//final EscapeGame game;

	public Room currentRoom;

	public GameScreen() {
		//this.game = gam;

		depot = new ReactorDepot();


		//Removed gossip image loader	
		
		// and go
//        try {
//            System.out.println(engine.eval("GameRegistry instance switchToRoom: #room02"));
//        }
//        catch(ScriptException e) { System.err.println("ERROR :" + e); }
	}
	protected void postInit(){
		GameRegistry.instance().setScreen(this);
		GameRegistry.instance().setInventory(new Inventory("inventory.png"));		
	}

	public void switchToRoom(String destination) {
		depot.clear();
		
		System.out.println("Switching to room: "+destination);
		
//		this.stage.clear();
		this.currentRoom = GameRegistry.instance().getRoom(destination);
//
//		Background background = new Background(0, 0,
//				this.currentRoom.getBackgroundFilename());
//		this.stage.addActor(background);
//
//		Background invBackground = new Background(700, 0, GameRegistry.instance().getInventory()
//				.getBackgroundFilename());
//		this.stage.addActor(invBackground);
//
		
		
		Reactor invBackground = ((Reactor)ContextedFactory.instance().getContextedItem(ObjectPrototype.Background,
				"inventoryBackground",700, 0, GameRegistry.instance().getInventory().getBackgroundFilename()).getBinded());
		depot.addReactor(invBackground);		
		
		Reactor background = ((Reactor) (ContextedFactory.instance().getContextedItem(ObjectPrototype.Background,
				destination+"Background",0, 0,this.currentRoom.getBackgroundFilename()
				)).getBinded());
		depot.addReactor(background);		
		
		// room
		
		if(this.currentRoom!=null){
			for (Reactor r : this.currentRoom.getReactors()) {
				//this.stage.addActor(r);
				depot.addReactor(r);
			}
		}
		
//
		// inventory
		for (Item i : GameRegistry.instance().getInventory().getItems()) {
			depot.addReactor(i);
		}
//
		// navigation
		depot.addReactor(GameRegistry.instance().getReactor("arrowLeft"));
		depot.addReactor(GameRegistry.instance().getReactor("arrowRight"));
//		this.stage.addActor(GameRegistry.instance().getReactor("arrowLeft"));
//		this.stage.addActor(GameRegistry.instance().getReactor("arrowRight"));

		
		pushDepot(depot);
	}

	
	protected Reactor checkForZombifiedReactors(){
		Reactor zombie = depot.check();
		return zombie;
	}
	
	
	public abstract void shutDown();
	
	public void addReactor(Reactor aReactor) {
		System.out.println("Adding "+aReactor.getName());
		depot.addReactor(aReactor);
	}

	public void addToInventory(Item anItem) {
		//TODO: FIXME
		GameRegistry.instance().getInventory().addItem(anItem);
		//this.stage.addActor(anItem);//Ehhm... WHAT???
	}

	public abstract void registerInPipeline();

	protected abstract void pushDepot(ReactorDepot depot);
	
	private GossipVM host;
	public void registerVMHost(GossipVM gossipVM) {
		host=gossipVM;
	}
	public GossipVM getHost(){
		return host;
	}
	
	//public EscapeGame getGame() {
		//TODO: is this called?
		//return this.game;
	//}		
	
}
