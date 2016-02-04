package com.tower.defense.one.game.actor.enemy;

import com.badlogic.gdx.utils.TimeUtils;
import com.tower.defense.one.game.Const;
import com.tower.defense.one.game.actor.bg.BGPanel;
import com.tower.defense.one.game.actor.button.PlaySpeedButton;
import com.tower.defense.one.game.actor.button.RightNowButton;
import com.tower.defense.one.game.chapters.ChapterOne;
import com.tower.defense.one.game.map.Route;

public class Wave {
	
	final ChapterOne chapter;
	
	private int peasantNum = 0;
	private Route route;
	private int delayTime = 0;
	private int leftEnemy = 0;
	private long lastGenerateTime = 0;
	private float peasantInterval = 1.0f;
	private boolean begin;
	private float leftTime;
	public static final int beCloseTime = 30;
	private RightNowButton rightNowButton;
	
	public Wave(final ChapterOne chapter, Route route, int delayTime){
		this.chapter = chapter;
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
	
	public boolean begin(float accumulator){
		if(begin) {
			return begin;
		} else {
			leftTime = delayTime - accumulator;
			if(leftTime <= 0){
				rightNow();
			} else if(rightNowButton == null && leftTime <= beCloseTime) {
				rightNowButton = new RightNowButton(this);
				chapter.addActorAt(6, rightNowButton);
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

	public void rightNow(){
		begin = true;
		if(rightNowButton != null) {
			rightNowButton.remove();
			rightNowButton = null;
		}
		chapter.rightNow(delayTime);
		if(chapter.getCurWaveIndex() > 0) {
			BGPanel.addTreasure(Math.max((int)leftTime,0) * 1); 
		}
	}
	
	public float getWaveBeignX(){
		return route.getPoint(0).getX();
	}

	public float getWaveBeignY(){
		return route.getPoint(0).getY();
	}
	
	public float getLeftTime() {
		return leftTime;
	}
	
}
