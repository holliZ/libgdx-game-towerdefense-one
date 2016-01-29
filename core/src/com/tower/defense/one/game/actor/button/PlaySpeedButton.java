package com.tower.defense.one.game.actor.button;

import static com.tower.defense.one.game.Assets.shapeRenderer;
import static com.tower.defense.one.game.Const.HEIGHT;
import static com.tower.defense.one.game.Const.WIDTH;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.tower.defense.one.game.Assets;
import com.tower.defense.one.game.Utils;
import com.tower.defense.one.game.actor.BasicActor;

public class PlaySpeedButton extends BasicActor {

	static int speed = 1;

	public PlaySpeedButton() {
		super(WIDTH - 105, HEIGHT - 40, 40, 32, false);
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
		
		batch.end();
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(86f/255, 86f/255, 86f/255, 1);
		Utils.DrawFilledRoundRect(getX() - 10, getY() - 5, getWidth() + 10 * 2, getHeight() + 5 * 2, 10, shapeRenderer);
		shapeRenderer.end();
		batch.begin();
		Assets.font.draw(batch, "X" + speed, getX(), getY() + 30);
	}
	
	public static int getSpeed(){
		return speed;
	}
}
