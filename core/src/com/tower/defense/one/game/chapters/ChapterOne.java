package com.tower.defense.one.game.chapters;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.TimeUtils;
import com.tower.defense.one.game.Const;
import com.tower.defense.one.game.actor.bg.BGActor;
import com.tower.defense.one.game.actor.enemy.Enemy1;
import com.tower.defense.one.game.actor.player.Tower1;
import com.tower.defense.one.game.map.EnemyRoute;
import com.tower.defense.one.game.map.Point;

public class ChapterOne extends Table {
	
	BGActor bgActor;
	EnemyRoute enemyRoute;
	Point begin = new Point(0, 10);
	Point end = new Point(800,10);
	int maxEnemy = 20;
	
	private long lastDropTime;
	
	public ChapterOne(){
		System.out.println("Create ChapterOne");
		setBounds(0, 0, Const.WIDTH, Const.HEIGHT);
		setClip(true);
		
		bgActor = new BGActor();
		bgActor.setName(Const.BG_ACTOR);
		addActor(bgActor);
		
		generateEnemyRoute();
		
		Tower1 tower1 = new Tower1(400, 100, 200);
		tower1.setName(Const.TOWER_ACTOR + 1);
		addActor(tower1);
		
		Tower1 tower2 = new Tower1(200, 100, 200);
		tower2.setName(Const.TOWER_ACTOR + 2);
		addActor(tower2);
		
		newEnemy();
	}
	
	public void generateEnemyRoute(){
		enemyRoute = new EnemyRoute();
		for(int x=0;x<800;x++){
			enemyRoute.addPoint(x, 10);
		}
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		
		if(bgActor.getEnemyNum() < maxEnemy && 
				TimeUtils.nanoTime() - lastDropTime > Const.ONE_SECOND * 3) {
			newEnemy();
		}
		
	}
	
	public void newEnemy(){
		Enemy1 enemy = new Enemy1(begin.getX(), begin.getY(), enemyRoute);
		enemy.setName(Const.ENEMY_ACTOR + bgActor.getEnemyNum());
		addActor(enemy);
		bgActor.addEnemyNum();
		lastDropTime = TimeUtils.nanoTime();
	}
}
