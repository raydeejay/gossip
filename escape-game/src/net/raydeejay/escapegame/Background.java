package net.raydeejay.escapegame;

import ru.sg_studio.escapegame.IProxiedObject;


public class Background extends Reactor {


	public Background(String name,int x, int y, String aFilename, IProxiedObject bindedProxy){
		super(name,bindedProxy);
		//Texture aTexture = new Texture(Gdx.files.internal(aFilename));

		setImage(aFilename);
		
		//this.setImage(aTexture);
		//this.setBounds(x, y, aTexture.getWidth(), aTexture.getHeight());

		setX(x);
		setY(y);
		
	}



//	public void setImage(Texture aTexture) {
//		this.image = aTexture;
//		this.setWidth(aTexture.getWidth());
//		this.setHeight(aTexture.getHeight());
//	}

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