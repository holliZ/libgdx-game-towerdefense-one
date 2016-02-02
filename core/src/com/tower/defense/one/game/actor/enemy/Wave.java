package com.tower.defense.one.game.actor.enemy;

import com.badlogic.gdx.utils.TimeUtils;
import com.tower.defense.one.game.Const;
import com.tower.defense.one.game.actor.bg.BGPanel;
import com.tower.defense.one.game.actor.button.PlaySpeedButton;
import com.tower.defense.one.game.actor.button.RightNowButton;
import com.tower.defense.one.game.map.Route;

public class Wave {
	
	private int peasantNum = 0;
	private Route route;
	private int delayTime = 0;
	private int leftEnemy = 0;
	private long lastGenerateTime = 0;
	private float peasantInterval = 1.0f;
	private boolean begin;
	private int leftTime;
	private final int beCloseTime = 15;
	private RightNowButton rightNowButton;
	private boolean firstEnemy = false;
	
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
			System.out.println("leftTime:" + leftTime);
			leftTime = (int) (delayTime - TimeUtils.timeSinceNanos(lastWaveBeginTime)/Const.ONE_SECOND );
			if(TimeUtils.timeSinceNanos(lastWaveBeginTime) > Const.ONE_SECOND/ PlaySpeedButton.getSpeed() * delayTime){
				righrNow();
			}
		}
		return begin;
	}
	
	public boolean isCreateRightNowButton(){
		if(rightNowButton == null && leftTime <= beCloseTime) {
			return true;
		} else {
			return false;
		}
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

	public void righrNow(){
		begin = true;
		firstEnemy = true;
		if(rightNowButton != null) {
			rightNowButton.remove();
			rightNowButton = null;
		}
	}
	
	public float getWaveBeignX(){
		return route.getPoint(0).getX();
	}

	public float getWaveBeignY(){
		return route.getPoint(0).getY();
	}
	
	public void setRightNowButton(RightNowButton rightNowButton) {
		System.out.println("setRightNowButton:" + leftTime);
		this.rightNowButton = rightNowButton;
	}
	
	public RightNowButton getRightNowButton() {
		return rightNowButton;
	}

	public boolean isFirstEnemy() {
		return firstEnemy;
	}

	public void setFirstEnemy(boolean firstEnemy) {
		this.firstEnemy = firstEnemy;
	}

	public int getLeftTime() {
		return leftTime;
	}

	public int getDelayTime() {
		return delayTime;
	}
	
}
