package com.tower.defense.one.game.actor.bg;

import static com.tower.defense.one.game.Assets.font;
import static com.tower.defense.one.game.Assets.shapeRenderer;
import static com.tower.defense.one.game.Const.HEIGHT;
import static com.tower.defense.one.game.Const.WIDTH;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class BGPanel extends Table{
	
	public static int enemyNum;
	public static int enemyIndex;
	public static int killedEnemyNum;
	private static int BloodCur;
	public static int TreasureCur;
	public static int Star;
	public static int ChapterReward;
	int waveIndex = 0;
	int waveMax = 10;

	public BGPanel(int waveMax) {
		setBounds(0, 0, WIDTH, HEIGHT);
		this.waveMax = waveMax;
	}
	
	public void init() {
		BloodCur = 20;
		enemyIndex = 0;
		enemyNum = 0;
		killedEnemyNum = 0;
		TreasureCur = 160;
		Star = 3;
		ChapterReward = 0;
		setVisible(true);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.end();
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(72 / 255f, 22 / 255f, 15 / 255f, 1f);
		shapeRenderer.rect(0, HEIGHT - 45, WIDTH, 2);
		shapeRenderer.end();
		batch.begin();
		
		font.draw(batch, "Blood:" + BloodCur, 10, HEIGHT - 10);
		font.draw(batch, "Treasure:" + TreasureCur, 170, HEIGHT - 10);
		font.draw(batch, "Wave" + (waveIndex + 1) + "/" + waveMax, WIDTH * 3 / 5, HEIGHT - 10);
		super.draw(batch, parentAlpha);
		
		
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

	public static int getBloodCur() {
		return BloodCur;
	}

	public static void hurtBlood(int HURT) {
		BloodCur -= HURT;
		if(BloodCur < 20) {
			Star = 2;
			if(BloodCur < 10) {
				Star = 1;
				if(BloodCur <= 0) {
					Star = 0;
				}
			}
		}
	}
	
}
