package com.tower.defense.one.game.actor.button;

import static com.tower.defense.one.game.Const.*;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.tower.defense.one.game.Utils;
import com.tower.defense.one.game.actor.BasicActor;
import com.tower.defense.one.game.screen.GameScreen;

public class PauseActor extends BasicActor {
	
	final GameScreen gameScreen;
	public PauseActor(final GameScreen gameScreen) {
		super(WIDTH - PAUSE_WIDTH - 10, HEIGHT - PAUSE_HEIGHT - 8, PAUSE_WIDTH, PAUSE_HEIGHT, false);
		this.gameScreen = gameScreen;
	}

	@Override
	public void onClick() {
		super.onClick();
		gameScreen.setGameState(GAME_PAUSED);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		batch.end();
		if(gameScreen.getGameState() == GAME_RUNNING) {
			Utils.DrawPauseButton(getX(), getY(), getWidth(), getHeight(), 10);
		}
		batch.begin();
	}
}
