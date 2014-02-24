package ru.sg_studio.escapegame.eventSystem;

import ru.sg_studio.escapegame.GossipVM;

public class CycleUpdateHandler {

	public void run(GossipVM myHost) {
		// TODO hook to VM execute()
		//Thread marshalling is not requared here
		System.out.println("DUH!");
	}

}
