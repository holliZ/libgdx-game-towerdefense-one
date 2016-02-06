package com.tower.defense.one.game.actor.player;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;

public interface TowerType {

	public void towerDraw(Tower tower, Batch batch, float parentAlpha);
	public void towerAct(Tower tower, float delta);
	
	public float getAttackWidth(int level);
	public float getAttackHeight(int level);
	public int getIntBuy(int level);
	public int getIntSale(int level);
	
	public int getMagicAttack(int level);
	public int getATK(int level);
	public float getAttackSpeed();
	public Color getTowerColor();
	public String getTowerTypeName();
	
}
