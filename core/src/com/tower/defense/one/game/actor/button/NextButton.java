package com.tower.defense.one.game.actor.button;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.tower.defense.one.game.Utils;
import com.tower.defense.one.game.actor.BasicActor;

public class NextButton extends BasicActor {
	
	public NextButton(float offsetX, float offsetY){
		super(offsetX - 150 /2, offsetY - 35/2, 150, 35, false);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		Utils.DrawButtonByFont(batch, "Next", getX(), getY(), getWidth(), getHeight(), 30, 0);
	}
	
	@Override
	public void onClick() {
		super.onClick();
	}
}
