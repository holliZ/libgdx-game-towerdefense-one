package com.tower.defense.one.game.actor.bg;

import static com.tower.defense.one.game.Const.HEIGHT;
import static com.tower.defense.one.game.Const.WIDTH;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.tower.defense.one.game.ShaperRendererUtils;
import com.tower.defense.one.game.actor.button.RestartButton;
import com.tower.defense.one.game.actor.button.ResumeActor;
import com.tower.defense.one.game.screen.GameScreen;

public class PausePanel extends Table{

	public PausePanel(final GameScreen gameScreen) {
		setBounds(0, 0, WIDTH, HEIGHT);
		setVisible(false);
		addActorAfter(this, new ResumeActor(gameScreen, WIDTH / 2 , HEIGHT / 2 + 50));
		addActorAfter(this, new RestartButton(gameScreen, WIDTH / 2, HEIGHT / 2));
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.end();
		ShaperRendererUtils.DrawCoverLayer();
		batch.begin();
		
		super.draw(batch, parentAlpha);
	}

}
