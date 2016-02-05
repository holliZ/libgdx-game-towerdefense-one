package com.tower.defense.one.game.actor.player;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;

public interface TowerType {

	public void towerDraw(Batch batch, float parentAlpha);
	public void towerAct(Tower tower, float delta);
	
	public float getATKWidth(int level);
	public float getATKHeight(int level);
	public int getIntBuy(int level);
	public int getIntSale(int level);
	
	public int getATK(int level);
	public float getATKSpeed();
	public Color getTowerColor();
	
}
