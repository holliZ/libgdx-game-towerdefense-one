package com.tower.defense.one.game.actor.bg;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.tower.defense.one.game.Const;
import com.tower.defense.one.game.actor.MyActor;

public class BGActor extends MyActor{

	public BGActor() {
		setBounds(0, 0, Const.WIDTH, Const.HEIGHT);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
	}
	
}
