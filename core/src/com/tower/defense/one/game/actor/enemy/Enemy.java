package com.tower.defense.one.game.actor.enemy;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.tower.defense.one.game.Const;
import com.tower.defense.one.game.actor.MyActor;
import com.tower.defense.one.game.map.EnemyRoute;
import com.tower.defense.one.game.map.Point;

public class Enemy extends MyActor {

	float HP = 100;
	float ATK = 20;
	float ARMOR = 0;//物理防御力
	float MAGICDEFENSE = 0; //魔法防御力
	float REWARD = 1;
	float HURT = 1;
	
	private int step = 0;
	private EnemyRoute route;
	private BitmapFont font;
	private float stateTime;
	private Animation animation;

	public Enemy(float x, float y, EnemyRoute route, Animation animation) {
		setBounds(x, y, Const.ENEMY_WIDTH, Const.ENEMY_HEIGHT);
		this.route = route;
		this.animation = animation;
		font = new BitmapFont();
		stateTime = 0f;
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		batch.draw(animation.getKeyFrame(stateTime, true), getX(), getY());
		font.draw(batch, Float.toString(HP), getX(), getY() + getHeight());
		font.draw(batch, getName(), getX(), getY());
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		
		stateTime += delta;

		if(HP > 0) {
			nextStep();
		} else {
			remove();
		}
	}
	
	public void nextStep(){
		Point newPoint = route.getPoint(step);
		setBounds(newPoint.getX(), newPoint.getY(), Const.ENEMY_WIDTH,
				Const.ENEMY_HEIGHT);
		step++;

		if (route.isLastStep(step)) {
			remove();
		}
	}
	
	public boolean hited(float ATK) {
		HP-= ATK;
		return HP > 0;
	}
	
	public Point getNextStep(){
		return route.getPoint(step);
	}
}
