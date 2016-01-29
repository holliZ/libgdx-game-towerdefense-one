package com.tower.defense.one.game.actor.bg;

import com.badlogic.gdx.graphics.g2d.Batch;
import static com.tower.defense.one.game.Assets.font;
import com.tower.defense.one.game.Const;
import com.tower.defense.one.game.actor.BasicActor;

public class BGActor extends BasicActor{
	
	public static int enemyNum;
	public static int enemyIndex;
	public static int BloodCur;
	public static int TreasureCur;
	int waveIndex = 0;
	int waveMax = 10;

	public BGActor(int waveMax) {
		super(0, 0, Const.WIDTH, Const.HEIGHT);
		BloodCur = 20;
		enemyIndex = 0;
		enemyNum = 0;
		TreasureCur = 300;
		this.waveMax = waveMax;
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		font.draw(batch, "Blood:" + BloodCur, 10, Const.HEIGHT - 10);
		font.draw(batch, "Treasure:" + TreasureCur, 170, Const.HEIGHT - 10);
		font.draw(batch, "Wave" + (waveIndex + 1) + "/" + waveMax, Const.WIDTH * 3 / 5, Const.HEIGHT - 10);
	}
	
	public static void addTreasure(int reward) {
		TreasureCur += reward;
		if(TreasureCur >= 9999) {
			TreasureCur = 9999;
		}
	}

	public void setWaveIndex(int waveIndex) {
		this.waveIndex = waveIndex;
	}
	
}
