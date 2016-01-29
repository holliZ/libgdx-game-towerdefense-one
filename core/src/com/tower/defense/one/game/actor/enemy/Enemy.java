package com.tower.defense.one.game.actor.enemy;

import static com.tower.defense.one.game.Assets.shapeRenderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.TimeUtils;
import com.tower.defense.one.game.Const;
import com.tower.defense.one.game.actor.BasicActor;
import com.tower.defense.one.game.actor.bg.BGActor;
import com.tower.defense.one.game.actor.button.PlaySpeedButton;
import com.tower.defense.one.game.map.EnemyRoute;
import com.tower.defense.one.game.map.Point;

public class Enemy extends BasicActor {

	float HP = 100;
	float HPMax = 100;
	float ATK = 20;
	float ARMOR = 0;// 物理防御力
	float MAGICDEFENSE = 0; // 魔法防御力
	int REWARD = 1;
	float HURT = 1;
	float MoveSpeed = 0.05f;// 移动速度, 数值越大越慢 0.05f;

	private int step = 0;
	private EnemyRoute route;
	private float stateTime;
	private Animation animation;
	private long lastMoveTime = 0;// 用来判定该敌人是否可以移动

	public Enemy(EnemyRoute route, Animation animation) {
		super(-50, -50, Const.ENEMY_WIDTH, Const.ENEMY_HEIGHT);
		this.route = route;
		this.animation = animation;
		stateTime = 0f;
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		batch.draw(animation.getKeyFrame(stateTime, true), getX(), getY());

		if (HP != HPMax) {
			batch.end();
			shapeRenderer.begin(ShapeType.Line);
			shapeRenderer.setColor(1, 0, 0, 1);
			shapeRenderer.rect(getX(), getY() - 3, getWidth(), 3);
			shapeRenderer.end();

			shapeRenderer.begin(ShapeType.Filled);
			shapeRenderer
					.rect(getX(), getY() - 3, getWidth() * (HP / HPMax), 3);
			shapeRenderer.end();
			batch.begin();
		}

	}

	@Override
	public void act(float delta) {
		super.act(delta);

		stateTime += delta;

		if (HP > 0) {
			if (canMove()) {
				nextStep();
				lastMoveTime = TimeUtils.nanoTime();
			}
		} else {
			BGActor.addTreasure(REWARD);
			Gdx.app.debug("Enemy", "killed");
			BGActor.enemyNum--;
			remove();
		}
	}

	public void nextStep() {
		Point newPoint = route.getPoint(step);
		setBounds(newPoint.getX(), newPoint.getY(), Const.ENEMY_WIDTH,
				Const.ENEMY_HEIGHT);
		step++;

		if (route.isLastStep(step)) {
			BGActor.BloodCur--;
			Gdx.app.debug("Enemy", "Eat");
			BGActor.enemyNum--;
			remove();
		}
	}

	public boolean hited(float ATK) {
		HP -= ATK;
		return HP > 0;
	}

	public Point getNextStep() {
		return route.getPoint(step);
	}

	private boolean canMove() {
		return TimeUtils.nanoTime() - lastMoveTime > Const.ONE_SECOND / PlaySpeedButton.getSpeed()
				* MoveSpeed;
	}
}
