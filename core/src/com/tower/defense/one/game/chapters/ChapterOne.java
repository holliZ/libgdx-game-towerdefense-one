package com.tower.defense.one.game.chapters;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.TimeUtils;
import com.tower.defense.one.game.Assets;
import com.tower.defense.one.game.Const;
import com.tower.defense.one.game.actor.bg.BGActor;
import com.tower.defense.one.game.actor.enemy.Enemy;
import com.tower.defense.one.game.actor.player.Tower;
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
		
		Tower tower1 = new Tower(0, 0, 300, 250);
		tower1.setName(Const.TOWER_ACTOR + 1);
		addActor(tower1);
		
		Tower tower2 = new Tower(600, 100, 300, 250);
		tower2.setName(Const.TOWER_ACTOR + 2);
		addActor(tower2);
		
		newEnemy();
	}
	
	public void generateEnemyRoute(){
		enemyRoute = new EnemyRoute();
		enemyRoute.generateByCorner(new float[][]{{0,10},{800,10}});
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
		
		if(bgActor.getEnemyNum() < maxEnemy && 
				TimeUtils.nanoTime() - lastDropTime > Const.ONE_SECOND * 3) {
			newEnemy();
		}
		
	}
	
	public void newEnemy(){
		Enemy enemy = new Enemy(begin.getX(), begin.getY(), enemyRoute, Assets.bob);
		enemy.setName(Const.ENEMY_ACTOR + bgActor.getEnemyNum());
		addActor(enemy);
		bgActor.addEnemyNum();
		lastDropTime = TimeUtils.nanoTime();
	}
}
