package com.tower.defense.one.game.actor.bg;

import static com.tower.defense.one.game.Assets.shapeRenderer;
import static com.tower.defense.one.game.Const.HEIGHT;
import static com.tower.defense.one.game.Const.WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.tower.defense.one.game.actor.button.ResumeActor;
import com.tower.defense.one.game.screen.GameScreen;

public class PausePanel extends Table{

	public PausePanel(final GameScreen gameScreen) {
		setBounds(0, 0, WIDTH, HEIGHT);
		setVisible(false);
		addActorAfter(this, new ResumeActor(gameScreen, WIDTH / 2 , HEIGHT / 2 + 50));
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

		batch.begin();
		
		super.draw(batch, parentAlpha);
	}

}
