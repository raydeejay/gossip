package net.raydeejay.escapegame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Background extends Actor {
	private Texture image;

	public Background(int x, int y, String aFilename) {
		Texture aTexture = new Texture(Gdx.files.internal(aFilename));

		this.setImage(aTexture);
		this.setBounds(x, y, aTexture.getWidth(), aTexture.getHeight());

	}

	public Texture getImage() {
		return image;
	}

	public void setImage(Texture aTexture) {
		this.image = aTexture;
		this.setWidth(aTexture.getWidth());
		this.setHeight(aTexture.getHeight());
	}

	public void setImage(String aFilename) {
		Texture aTexture = new Texture(Gdx.files.internal(aFilename));
		this.setImage(aTexture);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.setColor(Color.WHITE);
		batch.draw(this.getImage(), this.getX(), this.getY());
	}

}