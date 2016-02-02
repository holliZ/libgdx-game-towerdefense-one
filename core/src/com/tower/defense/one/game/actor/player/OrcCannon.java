package com.tower.defense.one.game.actor.player;

import static com.tower.defense.one.game.Assets.shapeRenderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.utils.TimeUtils;
import com.tower.defense.one.game.Const;
import com.tower.defense.one.game.actor.bg.BGPanel;
import com.tower.defense.one.game.actor.button.PlaySpeedButton;
import com.tower.defense.one.game.actor.enemy.Enemy;
import com.tower.defense.one.game.map.Point;
import com.tower.defense.one.game.map.Route;

public class OrcCannon extends Tower {
	
	public static final int Level1 = 1;
	public static final int Level2 = 2;
	public static final int Level3 = 3;
	
	static Color ATKBoundColor = new Color(183/255f, 32/255f, 238/255f, 1);
	
	protected Circle cannonBall;
	private Route cannonBallRoute;
	private int step;
	
	public OrcCannon(float x, float y) {
		super(x, y);
		COST = 110;
		ATK = 100;
		ATKSpeed = 13f;
		ATKColor = null;
		towerColor.set(183/255f, 32/255f, 238/255f, 1);
		cannonBall = new Circle(-50, - 50, 20);
		updateATKBound(getATKWidth(Level1), getATKHeight(Level1));
	}
	
	public static float getATKWidth(int level){
		switch(level){
		case Level1 :
			return 300;
		case Level2 :
			return 350;
		case Level3 :
			return 450;
			default :
				return 0;
		}
	}
	
	public static float getATKHeight(int level){
		switch(level){
		case Level1 :
			return 200;
		case Level2 :
			return 250;
		case Level3 :
			return 300;
			default :
				return 0;
		}
	}

	@Override
	void showOrHideOption() {
		Gdx.app.debug("OrcCannon", "showOrHideTowerType");
	}

	@Override
	protected void towerAct(float delta) {
		if (canATK()) {
			for (int i = 0; i < BGPanel.enemyIndex; i++) {
				Enemy enemy = getParent().findActor(Const.ENEMY_ACTOR + i);
				if (enemy != null && findEnemy(enemy)) {
					cannonBallRoute = new Route();
					cannonBallRoute.generateByCorner(new float[][]{{offsetX, offsetY}, {enemy.getOffsetX(), enemy.getOffsetY()}}, 2.0f);
					step = 0;
					lastATKTime = TimeUtils.nanoTime();
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
					Enemy enemy = getParent().findActor(
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
						enemyPoints.add(new Point(enemy.getX()
								+ enemy.getWidth() / 2, enemy.getY()
								+ enemy.getHeight() / 2));
						enemy.hited(ATK);
						ATKCount++;
					}
				}
				cannonBall.setPosition(-50, -50);
			}
		
		}
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		batch.end();
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.setColor(towerColor.r, towerColor.g, towerColor.b, towerColor.a);
		shapeRenderer.circle(cannonBall.x, cannonBall.y, cannonBall.radius);
		shapeRenderer.end();
		batch.begin();
	}

}
