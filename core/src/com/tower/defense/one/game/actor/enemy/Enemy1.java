package com.tower.defense.one.game.actor.enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.tower.defense.one.game.Assets;
import com.tower.defense.one.game.Const;
import com.tower.defense.one.game.map.EnemyRoute;
import com.tower.defense.one.game.map.Point;

public class Enemy1 extends Actor {

	private int step = 0;
	private EnemyRoute route;
	private float HP = 100;
	private float ATK = 20;
	private BitmapFont font;
	private float stateTime;

	public Enemy1(float x, float y, EnemyRoute route) {
		setBounds(x, y, Const.ENEMY_WIDTH, Const.ENEMY_HEIGHT);
		this.route = route;
		font = new BitmapFont();
		stateTime = 0f;
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		batch.draw(Assets.bob.getKeyFrame(stateTime, true), getX(), getY());
		font.draw(batch, Float.toString(HP), getX(), getY() + getHeight());
		font.draw(batch, getName(), getX(), getY());
		
//		batch.end();
//		
//		ShapeRenderer shapeRenderer = new ShapeRenderer();
//		shapeRenderer.begin(ShapeType.Filled);
//		
//		shapeRenderer.setColor(1, 0, 0, 1);
//		shapeRenderer.rect(getX(), getY(), getWidth(), getHeight());
//		
//		shapeRenderer.end();
//		
//		batch.begin();
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		
		stateTime += delta;

		if(HP>0) {
			nextStep();
		} else {
			remove();
			Gdx.app.debug("Enemy1", "Killed");
		}
	}
	
	public void nextStep(){
		Point newPoint = route.getPoint(step);
		setBounds(newPoint.getX(), newPoint.getY(), Const.ENEMY_WIDTH,
				Const.ENEMY_HEIGHT);
		step++;

		if (route.isLastStep(step)) {
			remove();
			Gdx.app.debug("Enemy1", "Eat");
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
