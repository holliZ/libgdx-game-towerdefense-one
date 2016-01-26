package com.tower.defense.one.game.demo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.tower.defense.one.game.Assets;
import com.tower.defense.one.game.Const;

public class DropActor extends Actor{

	public DropActor() {
		setBounds(MathUtils.random(0,Const.WIDTH-Const.OBJ_UNIT), Const.HEIGHT, Const.OBJ_UNIT, Const.OBJ_UNIT);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		batch.draw(Assets.dropImage, getX(), getY());
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		setY(getY()-200*delta);
		if(getY() + Const.OBJ_UNIT < 0) {
			Gdx.app.debug("DropActor", "getX():" + getX());
			Gdx.app.debug("DropActor", "getY():" + getY());
			Actor bucket = this.getParent().findActor(MyStage.BUCKET);
			Gdx.app.debug("DropActor", "bucket.getX():" + bucket.getX());
			Gdx.app.debug("DropActor", "bucket.getY():" + bucket.getY());
			Gdx.app.debug("DropActor", "after remove:" + (getParent().getChildren().size - 1));
			remove();
		} else {
			Actor bucket = this.getParent().findActor(MyStage.BUCKET);
			if(hit(Math.abs(bucket.getX() - getX()), Math.abs(getY() - bucket.getY()), false) != null){
				Assets.dropSound.play();
				BackgroundActor background = (BackgroundActor)this.getParent().findActor(MyStage.BACKGROUND);
				background.addDropsGathered();
				remove();
			}
		}
	}
	
}
