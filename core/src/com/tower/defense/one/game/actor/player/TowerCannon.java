package com.tower.defense.one.game.actor.player;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.tower.defense.one.game.Const;
import com.tower.defense.one.game.ShaperRendererUtils;
import com.tower.defense.one.game.actor.bg.BGPanel;
import com.tower.defense.one.game.actor.button.PlaySpeedButton;
import com.tower.defense.one.game.actor.enemy.Enemy;
import com.tower.defense.one.game.map.Point;
import com.tower.defense.one.game.map.Route;

public class TowerCannon implements TowerType {
	
	protected Circle cannonBall;
	private Route cannonBallRoute;
	private int step;
	
	public TowerCannon() {
		cannonBall = new Circle(-50, - 50, 20);
	}
	

	@Override
	public void towerDraw(Tower tower, Batch batch, float parentAlpha) {
		batch.end();
		ShaperRendererUtils.DrawCircle(cannonBall, Const.AttackColor, ShapeType.Line);
		batch.begin();
	}

	@Override
	public void towerAct(Tower tower, float delta) {
		if (tower.canAttack()) {
			for (int i = 0; i < BGPanel.enemyIndex; i++) {
				Enemy enemy = tower.getParent().findActor(Const.ENEMY_ACTOR + i);
				if (enemy != null && tower.findEnemy(enemy)) {
					cannonBallRoute = new Route();
					cannonBallRoute.generateByCorner(new float[][]{{tower.offsetX, tower.offsetY}, {enemy.getOffsetX(), enemy.getOffsetY()}}, 3.0f);
					step = 0;
					tower.lastAttackTime = TimeUtils.nanoTime();
					break;
				}
			}
		}
		
		if (cannonBallRoute != null) {
			Point newPoint = cannonBallRoute.getPoint(step);
			cannonBall.setPosition(newPoint.getX(), newPoint.getY());
			step += PlaySpeedButton.getSpeed();
			if (cannonBallRoute.isLastStep(step)) {
				cannonBallRoute = null;
				for (int i = 0; i < BGPanel.enemyIndex; i++) {
					Enemy enemy = tower.getParent().findActor(
							Const.ENEMY_ACTOR + i);
					if (enemy != null
							&& ((cannonBall.contains(enemy.getX(),
									enemy.getY())
									|| cannonBall.contains(enemy.getX()
											+ enemy.getWidth(),
											enemy.getY())
									|| cannonBall.contains(
											enemy.getX(),
											enemy.getY()
													+ enemy.getHeight()) || cannonBall
										.contains(
												enemy.getX()
														+ enemy.getWidth(),
												enemy.getY()
														+ enemy.getHeight())))) {
						tower.enemyPoints.add(new Point(enemy.getX()
								+ enemy.getWidth() / 2, enemy.getY()
								+ enemy.getHeight() / 2));
						enemy.hited(getATK(tower.Level), getMagicAttack(tower.Level));
						tower.AttackCount++;
					}
				}
				cannonBall.setPosition(-50, -50);
			}
		
		}
	}

	@Override
	public float getAttackWidth(int level) {
		switch(level){
		case 0:
			return 0;
		case 1 :
			return 385;
		case 2 :
			return 415;
		case 3 :
		default :
			return 445;
		}
	}

	@Override
	public float getAttackHeight(int level) {
		switch(level){
		case 0:
			return 0;
		case 1 :
			return 260;
		case 2 :
			return 290;
		case 3 :
		default :
			return 310;
		}
	}

	@Override
	public int getIntBuy(int level) {
		switch(level){
		case 0:
			return 0;
		case 1 :
			return 110;
		case 2 :
			return 180;
		case 3 :
		default :
			return 260;
		}
	}
	
	@Override
	public int getIntSale(int level) {
		switch(level){
		case 0:
			return 0;
		case 1 :
			return 55;
		case 2 :
			return 90;
		case 3 :
		default :
			return 130;
		}
	}

	@Override
	public int getMagicAttack(int level) {
		return 0;
	}

	@Override
	public int getATK(int level) {
		switch(level){
		case 0:
			return 0;
		case 1 :
			return MathUtils.random(105, 135);
		case 2 :
			return MathUtils.random(225, 255);
		case 3 :
		default :
			return MathUtils.random(345, 375);
		}
	}

	@Override
	public float getAttackSpeed() {
		return 6f;
	}

	@Override
	public Color getTowerColor() {
		return Const.CannonColor;
	}


	@Override
	public String getTowerTypeName() {
		return "炮";
	}

}
