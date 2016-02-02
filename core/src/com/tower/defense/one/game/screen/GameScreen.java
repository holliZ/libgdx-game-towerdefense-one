package com.tower.defense.one.game.screen;

import static com.tower.defense.one.game.Const.GAME_OVER;
import static com.tower.defense.one.game.Const.GAME_PAUSED;
import static com.tower.defense.one.game.Const.GAME_RUNNING;
import static com.tower.defense.one.game.Const.HEIGHT;
import static com.tower.defense.one.game.Const.WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.tower.defense.one.game.TowerDefense;
import com.tower.defense.one.game.actor.bg.BGPanel;
import com.tower.defense.one.game.actor.bg.PausePanel;
import com.tower.defense.one.game.actor.bg.SummaryPanel;
import com.tower.defense.one.game.actor.button.PauseButton;
import com.tower.defense.one.game.actor.button.PlaySpeedButton;
import com.tower.defense.one.game.chapters.ChapterOne;

public class GameScreen implements Screen {
	
	final TowerDefense game;
	private Stage stage;
	SummaryPanel summaryPanel;
	PausePanel pausePanel;
	PauseButton pauseButton;
	PlaySpeedButton playSpeedButton;
	ChapterOne chapter;
	BGPanel bgPanel;
	int gameState;
	
	public static String lastTouchActorName = "";
	
	public GameScreen(final TowerDefense game) {
		this.game = game;
		stage = new Stage(new FitViewport(WIDTH, HEIGHT));
//		stage.getCamera().rotate(stage.getCamera().direction, -10);
		
		chapter = new ChapterOne(this);
		stage.addActor(chapter);
		
		bgPanel = new BGPanel(chapter.getWaveMax());
		stage.addActor(bgPanel);
		
		playSpeedButton = new PlaySpeedButton();
		stage.addActor(playSpeedButton);
		
		pauseButton = new PauseButton(this);
		stage.addActor(pauseButton);
		
		pausePanel = new PausePanel(this);
		stage.addActor(pausePanel);
		
		summaryPanel = new SummaryPanel(this);
		stage.addActor(summaryPanel);
		
		startGame();
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(158/255f, 172/255f, 66/255f, 1);
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
	

	public void setGameState(int gameState) {
		this.gameState = gameState;
		switch(gameState){
		case GAME_RUNNING:
			chapter.setTouchable(Touchable.childrenOnly);
			pausePanel.setVisible(false);
			break;
		case GAME_PAUSED:
			pausePanel.setVisible(true);
			chapter.setTouchable(Touchable.disabled);
			break;
		case GAME_OVER:
			bgPanel.setVisible(false);
			chapter.setTouchable(Touchable.disabled);
			summaryPanel.setVisible(true);
			break;
		}
	}

	public int getGameState() {
		return gameState;
	}
	
	public void startGame() {
		chapter.clear();
		bgPanel.init();
		chapter.init();
		summaryPanel.init();
		setGameState(GAME_RUNNING);
	}
	
	public void setWaveIndex(int waveIndex){
		bgPanel.setWaveIndex(waveIndex);
	}

}
