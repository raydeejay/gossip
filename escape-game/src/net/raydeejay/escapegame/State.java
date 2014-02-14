package net.raydeejay.escapegame;


public class State {
	private String name;
	
	public State(String name) {
		this.setName(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void onEnter() {}
	public void onExit() {}
	public void whenClicked() {}
	public void whenClickedWith(Item anItem) {}
}
