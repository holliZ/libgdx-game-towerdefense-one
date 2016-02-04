package com.tower.defense.one.game.actor.button;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.tower.defense.one.game.Utils;
import com.tower.defense.one.game.actor.BasicActor;
import com.tower.defense.one.game.actor.enemy.Wave;

public class RightNowButton extends BasicActor {

	private float radius = 25;
	private Circle rightNowCircle;
	final Wave wave;

	public RightNowButton(final Wave wave) {
		super(wave.getWaveBeignX(), wave.getWaveBeignY(), 0, 0, true);
		this.wave = wave;
		rightNowCircle = new Circle(getX() + radius, getY(), radius);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		batch.end();
		Utils.DrawRightNowButton(rightNowCircle.x, rightNowCircle.y, rightNowCircle.radius, 360 * (wave.getLeftTime() + 1)/Wave.beCloseTime);
		batch.begin();
	}
	
	@Override
	public Actor hit(float x, float y, boolean touchable) {
		if (touchable && !isTouchable())
			return null;
		return rightNowCircle.contains(getX() + x, getY() + y) ? this : null;
	}
	
	@Override
	public void onClick() {
		super.onClick();
		wave.rightNow();
	}
}
