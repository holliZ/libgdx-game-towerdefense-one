package com.tower.defense.one.game.actor.bg;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.tower.defense.one.game.Const;

public class BGActor extends Actor{

	private int enemyNum;
	public BGActor() {
		setBounds(0, 0, Const.WIDTH, Const.HEIGHT);
		enemyNum = 0;
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
	}
	
	public void addEnemyNum(){
		enemyNum++;
	}
	
	public int getEnemyNum() {
		return enemyNum;
	}
	
}
