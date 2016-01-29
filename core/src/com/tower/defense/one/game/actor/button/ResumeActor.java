package com.tower.defense.one.game.actor.button;

import static com.tower.defense.one.game.Assets.font;
import static com.tower.defense.one.game.Const.GAME_RUNNING;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.tower.defense.one.game.actor.BasicActor;
import com.tower.defense.one.game.screen.GameScreen;

public class ResumeActor extends BasicActor {

	final GameScreen gameScreen;
	
	public ResumeActor(final GameScreen gameScreen, float centerX, float centerY) {
		super(centerX - 135 /2, centerY - 30/2, 135, 30, false);
		this.gameScreen = gameScreen;
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
//		batch.end();
//		shapeRenderer.begin(ShapeType.Filled);
//		shapeRenderer.setColor(1, 1, 191f / 255, 0.8f);
//		shapeRenderer.rect(getX(), getY(), getWidth(), getHeight());
//		shapeRenderer.end();
//		batch.begin();
		font.draw(batch, "Resume", getX(), getY() + 30);
	}
	
	@Override
	public void onClick() {
		super.onClick();
		gameScreen.setGameState(GAME_RUNNING);
	}
}
