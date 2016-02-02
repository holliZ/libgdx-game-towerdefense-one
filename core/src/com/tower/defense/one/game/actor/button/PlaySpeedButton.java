package com.tower.defense.one.game.actor.button;

import static com.tower.defense.one.game.Const.HEIGHT;
import static com.tower.defense.one.game.Const.WIDTH;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.tower.defense.one.game.Utils;
import com.tower.defense.one.game.actor.BasicActor;

public class PlaySpeedButton extends BasicActor {

	static int speed = 1;

	public PlaySpeedButton() {
		super(WIDTH - 115, HEIGHT - 40, 60, 36, false);
	}
	
	@Override
	public void onClick() {
		super.onClick();
		if(speed == 1) {
			speed = 2;
		} else if(speed == 2) {
			speed = 4;
		} else {
			speed = 1;
		}
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		Utils.DrawButtonByFont(batch, "[" + speed +"]", getX(), getY(), getWidth(), getHeight());
	}
	
	public static int getSpeed(){
		return speed;
	}
	
	public static void initSpeed(){
		speed = 1;
	}
}
