package com.tower.defense.one.game.actor.bg;

import static com.tower.defense.one.game.Assets.font;
import static com.tower.defense.one.game.Assets.shapeRenderer;
import static com.tower.defense.one.game.Const.HEIGHT;
import static com.tower.defense.one.game.Const.WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.tower.defense.one.game.Utils;
public class SummaryPanel extends Table {

	private int W = 300;
	private int H = 220;
	private int W2 = W - 10;
	private int H2 = H - 10;
	private int radius = 35;

	public SummaryPanel() {
		setBounds(0, 0, WIDTH, HEIGHT);
		setVisible(false);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.end();
		
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(141f / 255, 141f / 255, 141f / 255, 120f / 255);
		shapeRenderer.rect(0, 0, WIDTH, HEIGHT);
		shapeRenderer.end();
		Gdx.gl.glDisable(GL20.GL_BLEND);
		
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(191f / 255, 191f / 255, 191f / 255, 0.8f);
		Utils.DrawFilledRoundRect(WIDTH / 2 - W / 2, HEIGHT / 2 - H / 2, W, H,
				radius, shapeRenderer);
		shapeRenderer.end();
		Gdx.gl.glDisable(GL20.GL_BLEND);

		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(191f / 255, 191f / 255, 191f / 255, 0.8f);
		Utils.DrawFilledRoundRect(WIDTH / 2 - W2 / 2, HEIGHT / 2 - H2 / 2, W2, H2,
				radius, shapeRenderer);
		shapeRenderer.end();
		Gdx.gl.glDisable(GL20.GL_BLEND);

		batch.begin();

		font.draw(batch, "Summary", WIDTH / 2 - W / 2 + radius / 2, HEIGHT / 2 + H
				/ 2 - radius / 2);
		
		super.draw(batch, parentAlpha);
	}

	
}
