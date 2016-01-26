package com.tower.defense.one.game.demo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tower.defense.one.game.Assets;
import com.tower.defense.one.game.TowerDefense;

public class GameScreen implements Screen{
	
	private Stage stage;
	private MyStage myStage;
	
	public GameScreen(final TowerDefense gam) {
		stage = new Stage(new ScreenViewport());
		myStage = new MyStage();
		stage.addActor(myStage);
	}
	

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
		Assets.rainMusic.play();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		stage.getViewport().update(width, height, true);
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
		Gdx.input.setInputProcessor(null);
	}

	@Override
	public void dispose() {
		stage.dispose();
	}

}
