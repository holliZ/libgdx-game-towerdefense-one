package com.tower.defense.one.game.demo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tower.defense.one.game.Const;
import com.tower.defense.one.game.TowerDefense;

public class MainMenuScreen implements Screen {
	
	final TowerDefense game;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private BitmapFont font;
	
	public MainMenuScreen(final TowerDefense game) {
		this.game = game;
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Const.WIDTH, Const.HEIGHT);
		
		batch = new SpriteBatch();
		font = new BitmapFont();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camera.update();
		
		batch.setProjectionMatrix(camera.combined);
		
		batch.begin();
		font.draw(batch, "Welcome to Drop!", 100, 150);
		font.draw(batch, "Tap anywhere to begin!" , 100, 100);
		batch.end();
		
		if(Gdx.input.isTouched()){
			game.setScreen(new GameScreen(game));
			dispose();
		}
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		batch.dispose();
		font.dispose();
	}
}
