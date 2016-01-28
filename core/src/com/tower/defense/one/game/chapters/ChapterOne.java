package com.tower.defense.one.game.chapters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.TimeUtils;
import com.tower.defense.one.game.Const;
import com.tower.defense.one.game.StaticVariable;
import com.tower.defense.one.game.actor.bg.BGActor;
import com.tower.defense.one.game.actor.player.Tower;
import com.tower.defense.one.game.map.EnemyRoute;
import com.tower.defense.one.game.map.Point;

public class ChapterOne extends Table {
	
	BGActor bgActor;
	EnemyRoute enemyRoute;
	Point begin = new Point(-50, 10);
	Point end = new Point(800,10);
	int maxEnemy = 20;
	
	private long lastDropTime;
	
	public ChapterOne(){
		setBounds(0, 0, Const.WIDTH, Const.HEIGHT);
		setClip(true);
		
		addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				Gdx.app.log("ChapterOne", "touchDown");
				return super.touchDown(event, x, y, pointer, button);
			}
		});
		
		bgActor = new BGActor();
		bgActor.setName(Const.BG_ACTOR);
		addActor(bgActor);
		
//		newTower(400, 300, 1);
		
		newTower(132, 600-172, 1);
		newTower(292, 600-242, 2);
		newTower(528, 600-326, 3);
		newTower(704, 600-460, 4);
		newTower(374, 600-425, 5);
		newTower(482, 600-476, 6);
		
		generateEnemyRoute();
		
		newEnemy();
	}
	
	public void generateEnemyRoute(){
		enemyRoute = new EnemyRoute();
		enemyRoute.generateByCorner(new float[][]{{-50, 600-200},{0,600-200},{25,600-200},{170,600-285},{320,600-335},{530,600-410},{690,600-530},{800,600-540}});
	}
	
	public void generateEnemyRoute(Point[] cornerPoints){
		enemyRoute = new EnemyRoute();
		for(int x=0;x<800;x++){
			enemyRoute.addPoint(x, 10);
		}
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		
		if(StaticVariable.enemyNum < maxEnemy && 
				TimeUtils.nanoTime() - lastDropTime > Const.ONE_SECOND * 3) {
			newEnemy();
		}
	}
	
	public void newTower(float x, float y, int count){
		Tower tower = new Tower(x, y);
		tower.setName(Const.TOWER_ACTOR + count);
		addActor(tower);
		tower.initTower();
	}
	
	public void newEnemy(){
//		Enemy enemy = new Enemy(begin.getX(), begin.getY(), enemyRoute, Assets.bob);
//		enemy.setName(Const.ENEMY_ACTOR + StaticVariable.enemyNum );
//		addActor(enemy);
//		StaticVariable.enemyNum++;
//		lastDropTime = TimeUtils.nanoTime();
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
	}
}
