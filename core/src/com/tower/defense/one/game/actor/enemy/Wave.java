package com.tower.defense.one.game.actor.enemy;

import com.badlogic.gdx.utils.TimeUtils;
import com.tower.defense.one.game.Const;
import com.tower.defense.one.game.actor.bg.BGPanel;
import com.tower.defense.one.game.actor.button.PlaySpeedButton;
import com.tower.defense.one.game.map.Route;

public class Wave {
	
	private int peasantNum = 0;
	private Route route;
	private int delayTime = 0;
	private int leftEnemy = 0;
	private long lastGenerateTime = 0;
	private float peasantInterval = 1.0f;
	private boolean begin;
	
	public Wave(Route route, int delayTime){
		this.route = route;
		this.delayTime = delayTime;
		begin = false;
	}
	
	public Wave setPeasantNum(int peasantNum) {
		this.peasantNum = peasantNum;
		return this;
	}
	
	public Wave setPeasantInterval(int peasantInterval) {
		this.peasantInterval = peasantInterval;
		return this;
	}
	
	public int countLeftEmeny(){
		leftEnemy = this.peasantNum;
		return leftEnemy;
	}
	
	public boolean begin(long lastWaveBeginTime){
		if(begin) {
			return begin;
		} else {
			if(TimeUtils.timeSinceNanos(lastWaveBeginTime) > Const.ONE_SECOND/ PlaySpeedButton.getSpeed() * delayTime){
				begin = true;
			}
		}
		return begin;
	}
	
	public Enemy generate(){
		while(peasantNum > 0) {
			if(TimeUtils.nanoTime() - lastGenerateTime > Const.ONE_SECOND / PlaySpeedButton.getSpeed() * peasantInterval) {
				peasantNum--;
				leftEnemy--;
				Peasant peasant = new Peasant(route);
				peasant.setName(Const.ENEMY_ACTOR + BGPanel.enemyIndex++);
				lastGenerateTime = TimeUtils.nanoTime();
				return peasant;
			}
			return null;
		}
		return null;
	}

	
	public boolean isAvailableWave(){
		return leftEnemy > 0;
	}

}
