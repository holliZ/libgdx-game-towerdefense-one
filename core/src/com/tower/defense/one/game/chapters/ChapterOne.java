package com.tower.defense.one.game.chapters;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.tower.defense.one.game.Const;
import com.tower.defense.one.game.Utils;
import com.tower.defense.one.game.actor.bg.BGPanel;
import com.tower.defense.one.game.actor.enemy.Enemy;
import com.tower.defense.one.game.actor.enemy.Wave;
import com.tower.defense.one.game.actor.player.Tower;
import com.tower.defense.one.game.map.Route;
import com.tower.defense.one.game.screen.GameScreen;

public class ChapterOne extends Table {
	
	final GameScreen gameScreen;
	Route enemyRoute;
	Array<Wave> waves;
	float[][] corners = new float[][]{{0,600-200},{25,600-200},{170,600-285},{320,600-335},{530,600-410},{690,600-530},{800,600-540}};
	Tower[] towers = new Tower[6];
	int curWaveIndex, lastAvailableWaveIndex;
	final int waveMax = 10;
	
	private long chapterBeginTime;
	
	public ChapterOne(final GameScreen gameScreen){
		setBounds(0, 0, Const.WIDTH, Const.HEIGHT);
		setClip(true);
		this.gameScreen = gameScreen;
	}
	
	public void init() {
		lastAvailableWaveIndex = 0;
		curWaveIndex = 0;
		gameScreen.setWaveIndex(curWaveIndex);
		generateTowers();
		generateEnemys();
		chapterBeginTime = TimeUtils.nanoTime();
		setTouchable(Touchable.childrenOnly);
	}
	
	public void generateTowers() {
		towers[0] = newTower(132, 600-172, 1);
		towers[1] = newTower(292, 600-242, 2);
		towers[2] = newTower(528, 600-326, 3);
		towers[3] = newTower(704, 600-460, 4);
		towers[4] = newTower(374, 600-425, 5);
		towers[5] = newTower(482, 600-476, 6);
	}
	
	public void generateEnemys(){
		enemyRoute = new Route();
		enemyRoute.generateByCorner(corners, 1.0f);

		waves = new Array<Wave>(waveMax);
		for(int i=0;i<waveMax;i++){
			Wave wave = new Wave(enemyRoute, 30);
			wave.setPeasantNum(2 + i * 1);
			waves.add(wave);
			BGPanel.enemyNum += wave.countLeftEmeny();
		}
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		
		if(BGPanel.getBloodCur() <= 0 || (BGPanel.enemyNum <=0)) {
			gameScreen.setGameState(Const.GAME_OVER);
		} else {
			for(int waveIndex = lastAvailableWaveIndex; waveIndex < waveMax; waveIndex++){
				Wave wave = waves.get(waveIndex);
				if(wave.begin(chapterBeginTime)) {
					if(wave.isAvailableWave()) {
						curWaveIndex = waveIndex;
						gameScreen.setWaveIndex(curWaveIndex);
						Enemy enemy = wave.generate();
						if(enemy != null) addActor(enemy); 
					} else {
						lastAvailableWaveIndex = waveIndex + 1;
					}
				} else {
					break;
				}
			}
		}
	}
	
	public Tower newTower(float x, float y, int count){
		Tower tower = new Tower(x, y);
		tower.setName(Const.TOWER_ACTOR + count);
		addActor(tower);
		tower.init();
		return tower;
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
//		batch.draw(Assets.chapterOne, 0, 0);
//		Utils.DrawRouteInLineByCorners(batch, corners);
		Utils.DrawRouteInFilledByCorners(batch, corners);
		super.draw(batch, parentAlpha);
	}
	
	public int getWaveMax() {
		return waveMax;
	}
	
	public void righrNow(){
		
	}

}
