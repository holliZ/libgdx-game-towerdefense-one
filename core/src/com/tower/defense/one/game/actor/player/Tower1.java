package com.tower.defense.one.game.actor.player;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.tower.defense.one.game.Assets;
import com.tower.defense.one.game.Const;
import com.tower.defense.one.game.actor.bg.BGActor;
import com.tower.defense.one.game.actor.enemy.Enemy1;
import com.tower.defense.one.game.map.Point;

public class Tower1 extends Actor {

	private Circle boundCircle;
	private float ATK = 20;
	private int ATKSpeed = 1;
	private long lastATKTime = 0;//用来判定该塔是否可以攻击

	private Array<Point> enemyPoints = new Array<Point>(0);
	private Point enemyPoint = null;
	
	private ShapeRenderer shapeRenderer = new ShapeRenderer();

	public Tower1(float x, float y, float boundRadius) {
		setBounds(x, y, Const.TOWER_WIDTH, Const.TOWER_HEIGHT);
		boundCircle = new Circle(x + Const.TOWER_WIDTH / 2, y
				+ Const.TOWER_HEIGHT / 2, boundRadius);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		batch.draw(Assets.dropImage, getX(), getY());

		batch.end();
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.setColor(1, 1, 0, 1);
		shapeRenderer.circle(boundCircle.x, boundCircle.y, boundCircle.radius);
//		Iterator<Point> it = enemyPoints.iterator();
//		while(it.hasNext()) {
//			Point point = it.next();
//			shapeRenderer.line(boundCircle.x, boundCircle.y, point.getX(),
//					point.getY());
//		}
		if (enemyPoint != null) {
			shapeRenderer.line(boundCircle.x, boundCircle.y, enemyPoint.getX(),
					enemyPoint.getY());
			enemyPoint = null;
		}
		shapeRenderer.end();

		batch.begin();

	}

	@Override
	public void act(float delta) {
		super.act(delta);

		if (canATK()) {
			BGActor bgActor = this.getParent().findActor(Const.BG_ACTOR);

			for (int i = 0; i < bgActor.getEnemyNum(); i++) {
				Enemy1 enemy = this.getParent().findActor(Const.ENEMY_ACTOR + i);
				if (enemy != null && hitEnemy(enemy)) {
					break;
				}
			}
		}
	}

	public boolean hitEnemy(Enemy1 enemy) {
		if (boundCircle.contains(enemy.getX(), enemy.getY())
				|| boundCircle.contains(
						enemy.getX() + enemy.getWidth(), enemy.getY())
				|| boundCircle.contains(
						enemy.getX(), enemy.getY() + enemy.getHeight()) 
				|| boundCircle.contains(
						enemy.getX() + enemy.getWidth(), enemy.getY() + enemy.getHeight())) {
			enemyPoint = new Point(enemy.getX() + enemy.getWidth()
					/ 2, enemy.getY() + enemy.getHeight() / 2);
			enemyPoints.add(enemyPoint);
			enemy.hited(ATK);
			lastATKTime = TimeUtils.nanoTime();
			return true;
		}
		return false;
	}
	
	public boolean canATK(){
		return TimeUtils.nanoTime() - lastATKTime > Const.ONE_SECOND * ATKSpeed;
	}

}
