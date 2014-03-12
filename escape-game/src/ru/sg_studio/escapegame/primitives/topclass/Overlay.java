package ru.sg_studio.escapegame.primitives.topclass;

import net.raydeejay.escapegame.GameRegistry;
import net.raydeejay.escapegame.Reactor;
import net.raydeejay.escapegame.Room;
import ru.sg_studio.escapegame.primitives.GraphicalEntity;

public class Overlay extends GraphicalEntity {

	//DUMMY CLASS AS COMMON POINT FOR ALL OVERLAYS
	
	
	public Overlay addToRoom(Room aRoom) {
		aRoom.addGraphicalEntity(this);
		return this;
	}
	
	public Overlay addToRoomNamed(String aRoomName) {
		Room aRoom = GameRegistry.instance().getRoom(aRoomName.toString());
		return this.addToRoom(aRoom);
	}
	
	
	
	
	
}
