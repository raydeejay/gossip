package net.raydeejay.escapegame.screens;

import net.raydeejay.escapegame.EscapeGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Scaling;

public class MainMenuScreen implements Screen {

	final EscapeGame game;
	private Stage stage;

	private class TitleText extends Actor {

		public TitleText() {
			setBounds(0, 0, EscapeGame.WIDTH, EscapeGame.HEIGHT);
		}

		@Override
		public void draw(Batch batch, float parentAlpha) {
			batch.setColor(Color.WHITE);
			game.font.setScale(3);
			game.font.draw(batch, "Escape Game Demo", 200, 350);
			game.font.setScale(1);
			game.font.draw(batch, "Tap anywhere to begin!", 100, 100);
		}

		@Override
		public void act(float delta) {
			super.act(delta);
		}
	}

	public MainMenuScreen(final EscapeGame gam) {
		game = gam;
		stage = new Stage();

		TitleText title = new TitleText();
		title.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				game.setScreen(new GameScreen(game));
				dispose();
				return true;
			}
		});

		stage.addActor(title);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		Vector2 size = Scaling.fit.apply(EscapeGame.WIDTH, EscapeGame.HEIGHT,
				width, height);
		int viewportX = (int) (width - size.x) / 2;
		int viewportY = (int) (height - size.y) / 2;
		int viewportWidth = (int) size.x;
		int viewportHeight = (int) size.y;
		Gdx.gl.glViewport(viewportX, viewportY, viewportWidth, viewportHeight);
		stage.setViewport(EscapeGame.WIDTH, EscapeGame.HEIGHT, true, viewportX,
				viewportY, viewportWidth, viewportHeight);
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(this.stage);
	}

	@Override 
    public void hide() {
    	Gdx.input.setInputProcessor(null);
    }
	
	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	}

}
