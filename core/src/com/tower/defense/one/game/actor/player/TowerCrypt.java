package com.tower.defense.one.game.actor.player;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;

public class TowerCrypt implements TowerType {

	@Override
	public void towerDraw(Tower tower, Batch batch, float parentAlpha) {
		
	}

	@Override
	public void towerAct(Tower tower, float delta) {
		
	}

	@Override
	public float getAttackWidth(int level) {
		return 0;
	}

	@Override
	public float getAttackHeight(int level) {
		return 0;
	}

	@Override
	public int getIntBuy(int level) {
		return 0;
	}

	@Override
	public int getIntSale(int level) {
		return 0;
	}

	@Override
	public int getMagicAttack(int level) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getATK(int level) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getAttackSpeed() {
		return 0;
	}

	@Override
	public Color getTowerColor() {
		return null;
	}

	@Override
	public String getTowerTypeName() {
		return "兵";
	}

}
