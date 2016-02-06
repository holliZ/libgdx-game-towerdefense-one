package com.tower.defense.one.game.actor.player;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.tower.defense.one.game.Const;

public class TowerPoison implements TowerType {

	@Override
	public void towerDraw(Tower tower, Batch batch, float parentAlpha) {

	}

	@Override
	public void towerAct(Tower tower, float delta) {

	}

	@Override
	public float getAttackWidth(int level) {
		return 300;
	}

	@Override
	public float getAttackHeight(int level) {
		return 200;
	}

	@Override
	public int getIntBuy(int level) {
		switch(level){
		case 0:
			return 0;
		case 1 :
			return 90;
		case 2 :
			return 115;
		case 3 :
		default :
			return 135;
		}
	}
	
	@Override
	public int getIntSale(int level) {
		switch(level){
		case 0:
			return 0;
		case 1 :
			return 45;
		case 2 :
			return 57;
		case 3 :
		default :
			return 67;
		}
	}

	@Override
	public int getMagicAttack(int level) {
		switch(level){
		case 0:
			return 0;
		case 1 :
			return MathUtils.random(30, 50);
		case 2 :
			return MathUtils.random(55, 75);
		case 3 :
		default :
			return MathUtils.random(80, 100);
		}
	}

	@Override
	public int getATK(int level) {
		return 0;
	}

	@Override
	public float getAttackSpeed() {
		return 5f;
	}


	@Override
	public Color getTowerColor() {
		return Const.PoisonColor;
	}

	@Override
	public String getTowerTypeName() {
		return "毒";
	}

}
