package ru.sg_studio.escapegame;

public class CommonResourceProvider {

	private GossipVM VM;

	public void load(GameScreen bindedScreen) {
		VM = new GossipVM(false);//Change to true to load image only!
		VM.load();
		VM.provideInitialScreen(bindedScreen);
	}
	
	public void dispose(){
		
	}
	
}
