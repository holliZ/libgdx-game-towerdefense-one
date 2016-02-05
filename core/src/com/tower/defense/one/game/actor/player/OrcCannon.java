package com.tower.defense.one.game.actor.player;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.utils.TimeUtils;
import com.tower.defense.one.game.Const;
import com.tower.defense.one.game.ShaperRendererUtils;
import com.tower.defense.one.game.actor.bg.BGPanel;
import com.tower.defense.one.game.actor.button.PlaySpeedButton;
import com.tower.defense.one.game.actor.enemy.Enemy;
import com.tower.defense.one.game.map.Point;
import com.tower.defense.one.game.map.Route;

public class OrcCannon implements TowerType {
	
	protected Circle cannonBall;
	private Route cannonBallRoute;
	private int step;
	
	public OrcCannon() {
		cannonBall = new Circle(-50, - 50, 20);
	}

	@Override
	public void towerAct(Tower tower, float delta) {
		if (tower.canATK()) {
			for (int i = 0; i < BGPanel.enemyIndex; i++) {
				Enemy enemy = tower.getParent().findActor(Const.ENEMY_ACTOR + i);
				if (enemy != null && tower.findEnemy(enemy)) {
					cannonBallRoute = new Route();
					cannonBallRoute.generateByCorner(new float[][]{{tower.offsetX, tower.offsetY}, {enemy.getOffsetX(), enemy.getOffsetY()}}, 2.0f);
					step = 0;
					tower.lastATKTime = TimeUtils.nanoTime();
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
						enemy.hited(tower.ATK);
						tower.ATKCount++;
					}
				}
				cannonBall.setPosition(-50, -50);
			}
		
		}
	}

	@Override
	public void towerDraw(Batch batch, float parentAlpha) {
		batch.end();
		ShaperRendererUtils.DrawCircle(cannonBall, getTowerColor(), ShapeType.Line);
		batch.begin();
	}

	@Override
	public float getATKWidth(int level) {
		switch(level){
		case 1 :
			return 300;
		case 2 :
			return 350;
		case 3 :
			return 450;
			default :
				return 0;
		}
	}

	@Override
	public float getATKHeight(int level) {
		switch(level){
		case 1 :
			return 200;
		case 2 :
			return 250;
		case 3 :
			return 300;
			default :
				return 0;
		}
	}

	@Override
	public int getIntBuy(int level) {
		switch(level){
		case 1 :
			return 110;
		case 2 :
			return 260;
		case 3 :
			return 360;
			default :
				return 0;
		}
	}
	
	@Override
	public int getIntSale(int level) {
		return 10;
	}

	@Override
	public int getATK(int level) {
		switch(level){
		case 1 :
			return 110;
		case 2 :
			return 150;
		case 3 :
			return 200;
			default :
				return 0;
		}
	}

	@Override
	public float getATKSpeed() {
		return 13f;
	}

	@Override
	public Color getTowerColor() {
		return Const.CannonColor;
	}

}
