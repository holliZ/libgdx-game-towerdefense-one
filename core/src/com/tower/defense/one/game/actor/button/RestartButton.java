package com.tower.defense.one.game.actor.button;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.tower.defense.one.game.Utils;
import com.tower.defense.one.game.actor.BasicActor;
import com.tower.defense.one.game.screen.GameScreen;

public class RestartButton extends BasicActor {

	final GameScreen gameScreen;
	
	public RestartButton(final GameScreen gameScreen, float offsetX, float offsetY){
		super(offsetX - 150 /2, offsetY - 35/2, 150, 35, false);
		this.gameScreen = gameScreen;
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		Utils.DrawButtonByFont(batch, "Restart", getX(), getY(), getWidth(), getHeight());
	}
	
	@Override
	public void onClick() {
		super.onClick();
		gameScreen.startGame();
	}
}
