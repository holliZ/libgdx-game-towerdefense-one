package com.tower.defense.one.game.chapters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.tower.defense.one.game.Const;
import com.tower.defense.one.game.actor.bg.BGActor;
import com.tower.defense.one.game.actor.button.PlaySpeedButton;
import com.tower.defense.one.game.actor.enemy.Enemy;
import com.tower.defense.one.game.actor.enemy.Wave;
import com.tower.defense.one.game.actor.player.Tower;
import com.tower.defense.one.game.map.EnemyRoute;
import com.tower.defense.one.game.screen.GameScreen;

public class ChapterOne extends Table {
	
	final GameScreen gameScreen;
	BGActor bgActor;
	EnemyRoute enemyRoute;
	Array<Wave> waves;
	
	int curWaveIndex = 0;
	int waveMax = 2;
	
	private long chapterBeginTime;
	
	public ChapterOne(final GameScreen gameScreen){
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
		
		bgActor = new BGActor(waveMax);
		bgActor.setName(Const.BG_ACTOR);
		addActor(bgActor);
		
		addActor(new PlaySpeedButton());
		
//		newTower(400, 300, 1);
		generateTowers();
		generateEnemys();
		
		this.gameScreen = gameScreen;
		
		chapterBeginTime = TimeUtils.nanoTime();
	}
	
	public void generateTowers() {
		newTower(132, 600-172, 1);
		newTower(292, 600-242, 2);
		newTower(528, 600-326, 3);
		newTower(704, 600-460, 4);
		newTower(374, 600-425, 5);
		newTower(482, 600-476, 6);
	}
	
	public void generateEnemys(){
		enemyRoute = new EnemyRoute();
		enemyRoute.generateByCorner(new float[][]{{0,600-200},{25,600-200},{170,600-285},{320,600-335},{530,600-410},{690,600-530},{800,600-540}});

		waves = new Array<Wave>(waveMax);
		for(int i=0;i<waveMax;i++){
			Wave wave = new Wave(enemyRoute, i * 20 + 5);
			wave.setPeasantNum(5 + i * 1);
			waves.add(wave);
			BGActor.enemyNum += wave.countLeftEmeny();
		}
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		
		if(BGActor.BloodCur <= 0 || (BGActor.enemyNum <=0)) {
			gameScreen.setGameState(Const.GAME_OVER);
		} else {
			for(int waveIndex = curWaveIndex; waveIndex < waveMax; waveIndex++){
				Wave wave = waves.get(waveIndex);
				
				if(TimeUtils.nanoTime() - chapterBeginTime > Const.ONE_SECOND/ PlaySpeedButton.getSpeed() * wave.getWaveBeginTime()){
					if(wave.isAvailableWave()) {
						curWaveIndex = waveIndex;
						bgActor.setWaveIndex(curWaveIndex);
						Enemy enemy = wave.generate();
						if(enemy != null) addActor(enemy); 
					}
				} else {
					break;
				}
			}
		}
	}
	
	public void newTower(float x, float y, int count){
		Tower tower = new Tower(x, y);
		tower.setName(Const.TOWER_ACTOR + count);
		addActor(tower);
		tower.initTower();
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
	}
}
