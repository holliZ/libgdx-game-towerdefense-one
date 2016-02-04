package com.tower.defense.one.game.actor.button;

import static com.tower.defense.one.game.Const.GAME_PAUSED;
import static com.tower.defense.one.game.Const.HEIGHT;
import static com.tower.defense.one.game.Const.WIDTH;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.tower.defense.one.game.Utils;
import com.tower.defense.one.game.actor.BasicActor;
import com.tower.defense.one.game.screen.GameScreen;

public class PauseButton extends BasicActor {
	
	final GameScreen gameScreen;
	public PauseButton(final GameScreen gameScreen) {
		super(WIDTH - 50, HEIGHT - 40, 45, 36, true);
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
		Utils.DrawButtonByFont(batch, "II", getX(), getY(), getWidth(), getHeight());
	}
}
