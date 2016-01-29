package com.tower.defense.one.game.actor.enemy;

import com.badlogic.gdx.utils.TimeUtils;
import com.tower.defense.one.game.Const;
import com.tower.defense.one.game.actor.bg.BGActor;
import com.tower.defense.one.game.actor.button.PlaySpeedButton;
import com.tower.defense.one.game.map.EnemyRoute;

public class Wave {
	
	private int peasantNum = 0;
	private EnemyRoute route;
	private int waveBeginTime = 0;
	private int leftEnemy = 0;
	private long lastGenerateTime = 0;
	private float peasantInterval = 1.0f;
	
	public Wave(EnemyRoute route, int waveBeginTime){
		this.route = route;
		this.waveBeginTime = waveBeginTime;
	}
	
	public void setPeasantNum(int peasantNum) {
		this.peasantNum = peasantNum;
	}
	
	public int countLeftEmeny(){
		leftEnemy = this.peasantNum;
		return leftEnemy;
	}
	
	public Enemy generate(){
		while(peasantNum > 0) {
			if(TimeUtils.nanoTime() - lastGenerateTime > Const.ONE_SECOND / PlaySpeedButton.getSpeed()* peasantInterval) {
				peasantNum--;
				leftEnemy--;
				Peasant peasant = new Peasant(route);
				peasant.setName(Const.ENEMY_ACTOR + BGActor.enemyIndex++);
				lastGenerateTime = TimeUtils.nanoTime();
				return peasant;
			}
			return null;
		}
		return null;
	}

	public void setPeasantInterval(int peasantInterval) {
		this.peasantInterval = peasantInterval;
	}
	
	public boolean isAvailableWave(){
		return leftEnemy > 0;
	}

	public int getWaveBeginTime() {
		return waveBeginTime;
	}
	
}
