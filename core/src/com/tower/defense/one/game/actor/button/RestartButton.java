package com.tower.defense.one.game.actor.button;

import static com.tower.defense.one.game.Const.ACTION_BUTTON_HEIGHT;
import static com.tower.defense.one.game.Const.ACTION_BUTTON_WIDTH;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.tower.defense.one.game.Const;
import com.tower.defense.one.game.ShaperRendererUtils;
import com.tower.defense.one.game.actor.BasicActor;
import com.tower.defense.one.game.screen.GameScreen;

public class RestartButton extends BasicActor {

	final GameScreen gameScreen;
	
	public RestartButton(final GameScreen gameScreen, float offsetX, float offsetY){
		super(offsetX - ACTION_BUTTON_WIDTH / 2, offsetY - ACTION_BUTTON_HEIGHT
				/ 2, ACTION_BUTTON_WIDTH, ACTION_BUTTON_HEIGHT, false);
		this.gameScreen = gameScreen;
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		ShaperRendererUtils.DrawFontButton(batch, "Restart", getX(), getY(), getWidth(), getHeight(), Const.ButtonInnerADColor);
	}
	
	@Override
	public void onClick() {
		super.onClick();
		gameScreen.startGame();
	}
}
