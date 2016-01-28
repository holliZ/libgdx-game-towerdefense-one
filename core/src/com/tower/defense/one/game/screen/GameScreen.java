package com.tower.defense.one.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.tower.defense.one.game.Const;
import com.tower.defense.one.game.TowerDefense;
import com.tower.defense.one.game.chapters.ChapterOne;

public class GameScreen implements Screen {
	
	final TowerDefense game;
	private Stage stage;
	
	public GameScreen(final TowerDefense game) {
		this.game = game;
		stage = new Stage(new FitViewport(Const.WIDTH, Const.HEIGHT));
//		stage.getCamera().rotate(stage.getCamera().direction, -10);
		showChapter(new ChapterOne());
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(158f/255, 172f/255, 66f/255, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
	}

	@Override
	public void dispose() {
		stage.dispose();
		Gdx.app.debug("GameScreen", "dispose");
	}
	
	public void showChapter(Actor actor){
		stage.addActor(actor);
	}

}
