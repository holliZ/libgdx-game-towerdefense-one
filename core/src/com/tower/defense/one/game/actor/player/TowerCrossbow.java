package com.tower.defense.one.game.actor.player;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.tower.defense.one.game.Const;
import com.tower.defense.one.game.ShaperRendererUtils;
import com.tower.defense.one.game.actor.bg.BGPanel;
import com.tower.defense.one.game.actor.enemy.Enemy;
import com.tower.defense.one.game.map.Point;

public class TowerCrossbow implements TowerType{
	
	private final int MaxFrameCount = 20;
	private int shownFrameCount = MaxFrameCount;
	private Point point;

	@Override
	public void towerDraw(Tower tower, Batch batch, float parentAlpha) {
		if(point != null) {
			batch.end();
			ShaperRendererUtils.DrawLine(point.getX(), point.getY(), tower.offsetX, tower.offsetY, Const.AttackColor, ShapeType.Line);
			batch.begin();
		}
		if(shownFrameCount > 0) {
			shownFrameCount--;
		} else {
			point = null;
		}
	}

	@Override
	public void towerAct(Tower tower, float delta) {
		if(tower.canAttack()) {
			for (int i = 0; i < BGPanel.enemyIndex; i++) {
				Enemy enemy = tower.getParent().findActor(Const.ENEMY_ACTOR + i);
				if (enemy != null && tower.findEnemy(enemy)) {
					enemy.hited(getATK(tower.Level), getMagicAttack(tower.Level));
					tower.AttackCount++;
					tower.lastAttackTime = TimeUtils.nanoTime();
					point = tower.enemyPoints.get(0);
					shownFrameCount = MaxFrameCount;
					break;
				}
			}
		}
	}

	@Override
	public float getAttackWidth(int level) {
		switch(level){
		case 1 :
			return 300;
		case 2 :
			return 325;
		case 3 :
			return 355;
		default :
			return 0;
		}
	}

	@Override
	public float getAttackHeight(int level) {
		switch(level){
		case 1 :
			return 210;
		case 2 :
			return 230;
		case 3 :
			return 255;
		default :
			return 0;
		}
	}

	@Override
	public int getIntBuy(int level) {
		switch(level){
		case 1 :
			return 50;
		case 2 :
			return 90;
		case 3 :
			return 130;
		default :
			return 0;
		}
	}
	
	@Override
	public int getIntSale(int level) {
		switch(level){
		case 1 :
			return 25;
		case 2 :
			return 45;
		case 3 :
			return 65;
		default :
			return 0;
		}
	}
	
	@Override
	public int getMagicAttack(int level) {
		return 0;
	}

	@Override
	public int getATK(int level) {
		switch(level){
		case 1 :
			return MathUtils.random(27, 47);
		case 2 :
			return MathUtils.random(57, 77);
		case 3 :
			return MathUtils.random(86, 106);
		default :
			return 0;
		}
	}

	@Override
	public float getAttackSpeed() {
		return 2f;
	}

	@Override
	public Color getTowerColor() {
		return Const.CrossbowColor;
	}

	@Override
	public String getTowerTypeName() {
		return "箭";
	}
}
