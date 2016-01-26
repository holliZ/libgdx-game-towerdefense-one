package com.tower.defense.one.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class BackgroundActor extends Actor {

	private int dropsGathered = 0;
	private BitmapFont font;

	public BackgroundActor() {
		font = new BitmapFont();
		setBounds(0, 0, Const.WIDTH, Const.HEIGHT);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		font.draw(batch, "Drop collected: " + dropsGathered, 0, Const.HEIGHT);
	}

	public void addDropsGathered() {
		dropsGathered++;
	}

}
