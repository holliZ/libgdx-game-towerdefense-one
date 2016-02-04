package com.tower.defense.one.game.actor.enemy;

import static com.tower.defense.one.game.Assets.shapeRenderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.TimeUtils;
import com.tower.defense.one.game.Assets;
import com.tower.defense.one.game.Const;
import com.tower.defense.one.game.actor.BasicActor;
import com.tower.defense.one.game.actor.bg.BGPanel;
import com.tower.defense.one.game.actor.button.PlaySpeedButton;
import com.tower.defense.one.game.map.Route;
import com.tower.defense.one.game.map.Point;

public class Enemy extends BasicActor {

	int HP = 100;
	int HPMax = 100;
	int ATK = 20;
	int ARMOR = 0;// 物理防御力
	int MAGICDEFENSE = 0; // 魔法防御力
	int REWARD = 1;
	int HURT = 1;
	float MoveSpeed = 0.05f;// 移动速度, 数值越大越慢
	
	float offsetX, offsetY;

	private int step = 0;
	private Route route;
	private float stateTime;
	private Animation animation;
	protected long lastMoveTime = 0;// 用来判定该敌人是否可以移动

	public Enemy(Route route, Animation animation) {
		super(-50, -50, Const.ENEMY_WIDTH, Const.ENEMY_HEIGHT);
		this.route = route;
		this.animation = animation;
		stateTime = 0f;
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		Assets.font2.draw(batch, getName().substring(10), getX(), getY() + getHeight() + 20);
		batch.draw(animation.getKeyFrame(stateTime, true), getX(), getY());

		if (HP != HPMax) {
			batch.end();
			shapeRenderer.setColor(Color.RED);
			shapeRenderer.begin(ShapeType.Line);
			shapeRenderer.rect(getX(), getY() - 3, getWidth(), 3);
			shapeRenderer.end();

			shapeRenderer.begin(ShapeType.Filled);
			shapeRenderer.rect(getX(), getY() - 3, getWidth() * ((float)HP / HPMax), 3);
			shapeRenderer.end();
			batch.begin();
		}

	}

	@Override
	public void act(float delta) {
		super.act(delta);

		stateTime += delta * PlaySpeedButton.getSpeed();

		if (HP > 0) {
			if (canMove()) {
				nextStep();
				lastMoveTime = TimeUtils.nanoTime();
			}
		} else {
			BGPanel.addTreasure(REWARD);
			Gdx.app.debug("Enemy", "killed");
			BGPanel.enemyNum--;
			BGPanel.killedEnemyNum++;
			remove();
		}
	}

	public void nextStep() {
		Point newPoint = route.getPoint(step);
		offsetX = newPoint.getX();
		offsetY = newPoint.getY();
		setBounds(offsetX - Const.ENEMY_WIDTH/2, offsetY - Const.ENEMY_HEIGHT/2, Const.ENEMY_WIDTH,
				Const.ENEMY_HEIGHT);
		step++;

		if (route.isLastStep(step)) {
			BGPanel.hurtBlood(HURT);
			Gdx.app.debug("Enemy", "Eat");
			BGPanel.enemyNum--;
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
		return TimeUtils.nanoTime() - lastMoveTime > Const.ONE_SECOND / PlaySpeedButton.getSpeed() * MoveSpeed;
	}

	public float getOffsetX() {
		return offsetX;
	}

	public float getOffsetY() {
		return offsetY;
	}
	
}
