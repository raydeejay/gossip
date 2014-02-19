package net.raydeejay.escapegame;

import ru.sg_studio.escapegame.IProxiedObject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;


public class Background extends Reactor {
	private Texture image;

	public Background(String name,int x, int y, String aFilename, IProxiedObject bindedProxy){
		super(name,bindedProxy);
		Texture aTexture = new Texture(Gdx.files.internal(aFilename));

		this.setImage(aTexture);
		//this.setBounds(x, y, aTexture.getWidth(), aTexture.getHeight());

	}

	public Texture getImage() {
		return image;
	}

	public void setImage(Texture aTexture) {
		this.image = aTexture;
		this.setWidth(aTexture.getWidth());
		this.setHeight(aTexture.getHeight());
	}

	//public void setImage(String aFilename) {
	//	Texture aTexture = new Texture(Gdx.files.internal(aFilename));
	//	this.setImage(aTexture);
	//}

//	@Override
//	public void draw(Batch batch, float parentAlpha) {
//		batch.setColor(Color.WHITE);
//		batch.draw(this.getImage(), this.getX(), this.getY());
//	}

}