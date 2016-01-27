package com.tower.defense.one.game.actor.player;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.tower.defense.one.game.Assets;
import com.tower.defense.one.game.Const;
import com.tower.defense.one.game.actor.bg.BGActor;
import com.tower.defense.one.game.actor.enemy.Enemy;
import com.tower.defense.one.game.map.Point;

public class Tower extends Actor {

	float ATK = 20;// 攻击力
	int ATKNum = 1;// 最多可以同时攻击多少个敌人;为0时指范围攻击
	int ATKSpeed = 1;// 攻击速度

	private int ATKCount = 0;// 本次已经攻击的敌人数目
	private Ellipse ATKEllipse;
	private Circle boundCircle;
	private long lastATKTime = 0;// 用来判定该塔是否可以攻击
	private Array<Point> enemyPoints = new Array<Point>(0);

	private ShapeRenderer shapeRenderer = new ShapeRenderer();

	public Tower(float x, float y, float atkWidth, float atkHeight) {
		setBounds(x, y, Const.TOWER_WIDTH, Const.TOWER_HEIGHT);
		ATKEllipse = new Ellipse(x + Const.TOWER_WIDTH / 2, y
				+ Const.TOWER_HEIGHT / 2, atkWidth, atkHeight);
		
		boundCircle = new Circle(x + Const.TOWER_WIDTH / 2, y
				+ Const.TOWER_HEIGHT / 2, Const.TOWER_RADIUS);
		addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				Gdx.app.debug("BucketActor", "touchDown");
				return super.touchDown(event, x, y, pointer, button);
			}
		});
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		batch.draw(Assets.dropImage, getX(), getY());

		batch.end();

		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(0, 1, 0, 1);
		shapeRenderer.circle(boundCircle.x, boundCircle.y, boundCircle.radius);
		shapeRenderer.end();

		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.setColor(1, 1, 0, 1);
		shapeRenderer.ellipse(ATKEllipse.x - ATKEllipse.width/2, ATKEllipse.y - ATKEllipse.height/2, ATKEllipse.width,
				ATKEllipse.height);
		Iterator<Point> it = enemyPoints.iterator();
		while (it.hasNext()) {
			Point point = it.next();
			shapeRenderer.line(ATKEllipse.x, ATKEllipse.y, point.getX(),
					point.getY());
		}
		shapeRenderer.end();

		batch.begin();
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		enemyPoints.clear();
		if (canATK()) {
			BGActor bgActor = this.getParent().findActor(Const.BG_ACTOR);

			for (int i = 0; i < bgActor.getEnemyNum(); i++) {
				Enemy enemy = this.getParent().findActor(Const.ENEMY_ACTOR + i);
				if (enemy != null && hitEnemy(enemy)) {
					ATKCount++;
					if (ATKNum == 0 || ATKCount < ATKNum) {
						continue;
					} else {
						break;
					}
				}
			}
		}
	}

	public boolean hitEnemy(Enemy enemy) {
		if (ATKEllipse.contains(enemy.getX(), enemy.getY())
				|| ATKEllipse.contains(enemy.getX() + enemy.getWidth(),
						enemy.getY())
				|| ATKEllipse.contains(enemy.getX(),
						enemy.getY() + enemy.getHeight())
				|| ATKEllipse.contains(enemy.getX() + enemy.getWidth(),
						enemy.getY() + enemy.getHeight())) {
			enemyPoints.add(new Point(enemy.getX() + enemy.getWidth() / 2,
					enemy.getY() + enemy.getHeight() / 2));
			enemy.hited(ATK);
			lastATKTime = TimeUtils.nanoTime();
			return true;
		}
		return false;
	}

	public boolean canATK() {
		return TimeUtils.nanoTime() - lastATKTime > Const.ONE_SECOND * ATKSpeed;
	}

	@Override
	public Actor hit(float x, float y, boolean touchable) {
		if (touchable && !isTouchable())
			return null;
		return boundCircle.contains(getX() + x, getY() + y) ? this : null;
	}

}
