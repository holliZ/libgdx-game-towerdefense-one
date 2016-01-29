package com.tower.defense.one.game.screen;

import static com.tower.defense.one.game.Const.GAME_OVER;
import static com.tower.defense.one.game.Const.GAME_PAUSED;
import static com.tower.defense.one.game.Const.GAME_RUNNING;
import static com.tower.defense.one.game.Const.HEIGHT;
import static com.tower.defense.one.game.Const.WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.tower.defense.one.game.TowerDefense;
import com.tower.defense.one.game.actor.bg.PausePanel;
import com.tower.defense.one.game.actor.bg.SummaryPanel;
import com.tower.defense.one.game.actor.button.PauseActor;
import com.tower.defense.one.game.chapters.ChapterOne;

public class GameScreen implements Screen {
	
	final TowerDefense game;
	private Stage stage;
	SummaryPanel summaryTable;
	PausePanel pauseTable;
	PauseActor pauseActor;
	ChapterOne chapter;
	int gameState;
	
	public static String lastTouchActorName = "";
	
	public GameScreen(final TowerDefense game) {
		this.game = game;
		stage = new Stage(new FitViewport(WIDTH, HEIGHT));
//		stage.getCamera().rotate(stage.getCamera().direction, -10);
		
		chapter = new ChapterOne(this);
		showChapter(chapter);
		
		pauseActor = new PauseActor(this);
		stage.addActor(pauseActor);
		
		pauseTable = new PausePanel(this);
		stage.addActor(pauseTable);
		
		summaryTable = new SummaryPanel();
		stage.addActor(summaryTable);
		
		setGameState(GAME_RUNNING);
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(158f/255, 172f/255, 66f/255, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		switch(gameState){
		case GAME_RUNNING:
			stage.act(delta);
			break;
		}
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

	public void setGameState(int gameState) {
		this.gameState = gameState;
		switch(gameState){
		case GAME_RUNNING:
			chapter.setTouchable(Touchable.childrenOnly);
			pauseTable.setVisible(false);
			break;
		case GAME_PAUSED:
			pauseTable.setVisible(true);
			chapter.setTouchable(Touchable.disabled);
			break;
		case GAME_OVER:
			chapter.setTouchable(Touchable.disabled);
			summaryTable.setVisible(true);
			break;
		}
	}

	public int getGameState() {
		return gameState;
	}

}
