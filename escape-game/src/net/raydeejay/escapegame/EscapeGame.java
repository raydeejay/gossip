package net.raydeejay.escapegame;

import net.raydeejay.escapegame.screens.MainMenuScreen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class EscapeGame extends Game {

	public static final int WIDTH = 800; 
	public static final int HEIGHT = 480;
	
	public BitmapFont font;

	public void create() {
		// we want to load non-power-of-two textures easily
		Texture.setEnforcePotImages(false); 

		// Use LibGDX's default Arial font.
		font = new BitmapFont();
		this.setScreen(new MainMenuScreen(this));
	}

	public void render() {
		super.render(); // important!
	}

	public void dispose() {
		font.dispose();
	}

}