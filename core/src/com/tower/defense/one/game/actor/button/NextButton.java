package com.tower.defense.one.game.actor.button;

import static com.tower.defense.one.game.Const.*;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.tower.defense.one.game.Const;
import com.tower.defense.one.game.ShaperRendererUtils;
import com.tower.defense.one.game.actor.BasicActor;

public class NextButton extends BasicActor {
	
	public NextButton(float offsetX, float offsetY){
		super(offsetX - ACTION_BUTTON_WIDTH / 2, offsetY - ACTION_BUTTON_HEIGHT
				/ 2, ACTION_BUTTON_WIDTH, ACTION_BUTTON_HEIGHT, false);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		ShaperRendererUtils.DrawFontButton(batch, "Next", getX(), getY(), getWidth(), getHeight(), Const.ButtonInnerAColor);
	}
	
	@Override
	public void onClick() {
		super.onClick();
	}
}
