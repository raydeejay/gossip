package ru.sg_studio.escapegame.bindings.libgdx;

import ru.sg_studio.escapegame.rendering.Texture;

public class LibGDXTextureWrapper extends Texture {

	/*
	 * Upcaster
	 */
	public LibGDXTextureWrapper(Texture tex) {
		super(tex);
		this.onTextureLoadedBySide();
		gdxTexture = new com.badlogic.gdx.graphics.Texture(this.getFilename());
	}

	private com.badlogic.gdx.graphics.Texture gdxTexture;

	public com.badlogic.gdx.graphics.Texture getGdxTexture() {
		return gdxTexture;
	}
	
	
}
