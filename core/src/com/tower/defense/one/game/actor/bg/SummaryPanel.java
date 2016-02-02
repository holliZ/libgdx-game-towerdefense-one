package com.tower.defense.one.game.actor.bg;

import static com.tower.defense.one.game.Assets.font;
import static com.tower.defense.one.game.Assets.font2;
import static com.tower.defense.one.game.Assets.shapeRenderer;
import static com.tower.defense.one.game.Const.HEIGHT;
import static com.tower.defense.one.game.Const.WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.tower.defense.one.game.Utils;
import com.tower.defense.one.game.actor.button.NextButton;
import com.tower.defense.one.game.actor.button.RestartButton;
import com.tower.defense.one.game.screen.GameScreen;
public class SummaryPanel extends Table {

	private int W = 500;
	private int H = 220;
	private int W2 = W - 10;
	private int H2 = H - 10;
	private int radius = 35;
	final GameScreen gameScreen;
	
	public SummaryPanel(final GameScreen gameScreen) {
		this.gameScreen = gameScreen;
		setBounds(0, 0, WIDTH, HEIGHT);
		
		addActorAfter(this, new RestartButton( gameScreen, WIDTH / 2  - W / 2 + 90, H));
		addActorAfter(this, new NextButton( WIDTH / 2  + W / 2 - 90, H));
	}
	
	public void init() {
		setVisible(false);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.end();
		
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(141/255f, 141/255f, 141/255f, 120/255f);
		shapeRenderer.rect(0, 0, WIDTH, HEIGHT);
		shapeRenderer.end();
		Gdx.gl.glDisable(GL20.GL_BLEND);
		
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(191/255f, 191/255f, 191/255f, 0.8f);
		Utils.DrawFilledRoundRect(WIDTH / 2 - W / 2, HEIGHT / 2 - H / 2, W, H,
				radius, shapeRenderer);
		shapeRenderer.end();
		Gdx.gl.glDisable(GL20.GL_BLEND);

		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(191/255f, 191/255f, 191/255f, 1f);
		Utils.DrawFilledRoundRect(WIDTH / 2 - W2 / 2, HEIGHT / 2 - H2 / 2, W2, H2,
				radius, shapeRenderer);
		
		shapeRenderer.setColor(72 / 255f, 22 / 255f, 15 / 255f, 1f);
		shapeRenderer.rect(WIDTH / 2 - W / 2 + 10, HEIGHT / 2 + H / 2 - radius * 14 / 5, W - 20, 2);
		shapeRenderer.end();

		batch.begin();
		
		int killedEnemyReward = (int)Math.ceil(BGPanel.killedEnemyNum/2);
		int leftBloodReward = (BGPanel.getBloodCur() * 5);
		int leftTreasureReward = (int)Math.ceil(BGPanel.TreasureCur * 0.05);
		int starReward = (int)Math.ceil(BGPanel.Star/3 * BGPanel.ChapterReward);
		int totalReward = killedEnemyReward + leftBloodReward + leftTreasureReward + starReward;
		
		font2.draw(batch, "Enemy * " + BGPanel.killedEnemyNum, WIDTH / 2 - W / 2 + 18, HEIGHT / 2 + H
				/ 2 - radius);
		font2.draw(batch, "= " + killedEnemyReward, WIDTH / 2 - W / 2 + 170, HEIGHT / 2 + H
				/ 2 - radius);
		
		font2.draw(batch, "Blood * " + (BGPanel.getBloodCur()), WIDTH / 2 + 18, HEIGHT / 2 + H
				/ 2 - radius);
		font2.draw(batch, "= " + leftBloodReward, WIDTH / 2 + 150, HEIGHT / 2 + H
				/ 2 - radius);
		
		font2.draw(batch, "Treasure * " + BGPanel.TreasureCur, WIDTH / 2 - W / 2 + 18, HEIGHT / 2 + H
				/ 2 - radius * 2);
		font2.draw(batch, "= " + leftTreasureReward, WIDTH / 2 - W / 2 + 170, HEIGHT / 2 + H
				/ 2 - radius * 2);
		
		font2.draw(batch, "Star * " + (BGPanel.Star), WIDTH / 2 + 18, HEIGHT / 2 + H
				/ 2 - radius * 2);
		font2.draw(batch, "= " + starReward, WIDTH / 2 + 150, HEIGHT / 2 + H
				/ 2 - radius * 2);
		
		font.draw(batch, "Total:", WIDTH / 2 + 40, HEIGHT / 2 + H
				/ 2 - radius * 3);
		font.draw(batch, "  " + totalReward, WIDTH / 2 + 140, HEIGHT / 2 + H
				/ 2 - radius * 3);
		
		super.draw(batch, parentAlpha);
	}

	
}
