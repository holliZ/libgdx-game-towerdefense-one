package com.tower.defense.one.game.actor.enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.TimeUtils;
import com.tower.defense.one.game.Assets;
import com.tower.defense.one.game.Const;
import com.tower.defense.one.game.ShaperRendererUtils;
import com.tower.defense.one.game.actor.BasicActor;
import com.tower.defense.one.game.actor.bg.BGPanel;
import com.tower.defense.one.game.actor.button.PlaySpeedButton;
import com.tower.defense.one.game.map.Point;
import com.tower.defense.one.game.map.Route;

public abstract class Enemy extends BasicActor {

	float offsetX, offsetY;
	float HPCur;

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
		HPCur = getHP();
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
//		Assets.font2.draw(batch, getName().substring(10), getX(), getY() + getHeight() + 20);
		Assets.font2.draw(batch, HPCur + " ", getX(), getY() + getHeight() + 20);
		batch.draw(animation.getKeyFrame(stateTime, true), getX(), getY());

		if (HPCur != getHP()) {
			batch.end();
			ShaperRendererUtils.DrawRectangle(getX(), getY() - 3, getWidth(), 3, Color.RED, ShapeType.Line);
			ShaperRendererUtils.DrawRectangle(getX(), getY() - 3, getWidth() * ((float)HPCur / getHP()), 3, Color.RED, ShapeType.Line);
			batch.begin();
		}

	}

	@Override
	public void act(float delta) {
		super.act(delta);

		stateTime += delta * PlaySpeedButton.getSpeed();

		if (HPCur > 0) {
			if (canMove()) {
				nextStep();
				lastMoveTime = TimeUtils.nanoTime();
			}
		} else {
			BGPanel.addTreasure(getReward());
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
			BGPanel.hurtBlood(getHurt());
			Gdx.app.debug("Enemy", "Eat");
			BGPanel.enemyNum--;
			remove();
		}
	}

	public boolean hited(float ATK, float magicAttack) {
		HPCur -= ATK * getDenence();
		HPCur -= magicAttack * getMagicDenence();
		return HPCur > 0;
	}

	public Point getNextStep() {
		return route.getPoint(step);
	}

	private boolean canMove() {
		return TimeUtils.nanoTime() - lastMoveTime > Const.ONE_SECOND / PlaySpeedButton.getSpeed() * getMoveSpeed();
	}

	public float getOffsetX() {
		return offsetX;
	}

	public float getOffsetY() {
		return offsetY;
	}
	
	public abstract float getATK();
	public abstract float getMagicAttack();
	public abstract float getDenence();
	public abstract float getMagicDenence();
	public abstract int getHP();
	public abstract int getReward();
	public abstract int getHurt();
	public abstract float getMoveSpeed();
}
