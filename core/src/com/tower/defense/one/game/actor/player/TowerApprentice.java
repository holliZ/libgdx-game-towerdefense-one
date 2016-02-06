package com.tower.defense.one.game.actor.player;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.tower.defense.one.game.Const;

public class TowerApprentice implements TowerType {
	
	@Override
	public void towerDraw(Tower tower, Batch batch, float parentAlpha) {

	}

	@Override
	public void towerAct(Tower tower, float delta) {

	}

	@Override
	public float getAttackWidth(int level) {
		return 275;
	}

	@Override
	public float getAttackHeight(int level) {
		return 190;
	}

	@Override
	public int getIntBuy(int level) {
		switch(level){
		case 0:
			return 0;
		case 1 :
			return 90;
		case 2 :
			return 155;
		case 3 :
		default :
			return 215;
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
			return 77;
		case 3 :
		default :
			return 107;
		}
	}
	
	@Override
	public int getMagicAttack(int level) {
		switch(level){
		case 0:
			return 0;
		case 1 :
			return MathUtils.random(92, 102);
		case 2 :
			return MathUtils.random(190, 200);
		case 3 :
		default :
			return MathUtils.random(295, 305);
		}
	}

	@Override
	public int getATK(int level) {
		return 0;
	}


	@Override
	public float getAttackSpeed() {
		return 3f;
	}

	@Override
	public Color getTowerColor() {
		// TODO Auto-generated method stub
		return Const.ApprenticeColor;
	}

	@Override
	public String getTowerTypeName() {
		return "法";
	}

}
