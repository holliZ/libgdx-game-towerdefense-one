package com.tower.defense.one.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class BucketActor extends Actor{
	
	public BucketActor() {
		setBounds(Const.WIDTH/2 - Const.OBJ_UNIT/2, 20, Const.OBJ_UNIT, Const.OBJ_UNIT);
		addListener(new InputListener() {

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				Gdx.app.debug("BucketActor", "touchDown");
				return super.touchDown(event, x, y, pointer, button);
			}

			@Override
			public boolean mouseMoved(InputEvent event, float x, float y) {
				Gdx.app.debug("BucketActor", "mouseMoved");
				return super.mouseMoved(event, x, y);
			}
			
		});
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		batch.draw(Assets.bucketImage, getX(), getY());
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		
		if(Gdx.input.isTouched()){
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
//			camera.unproject(touchPos);
			setX(touchPos.x - Const.OBJ_UNIT/2);
		}
		
//		//Ëæ×ÅÊó±êÒÆ¶¯
////	Vector3 touchPos = new Vector3();
////	touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
////	camera.unproject(touchPos);
////	
////	bucket.x = touchPos.x - Const.OBJ_UNIT/2;
////	bucket.y = touchPos.y - Const.OBJ_UNIT/2;
//	
//	if(Gdx.input.isKeyPressed(Keys.LEFT)) bucket.x -= 200 * Gdx.graphics.getDeltaTime();
//	if(Gdx.input.isKeyPressed(Keys.RIGHT)) bucket.x += 200 * Gdx.graphics.getDeltaTime();
//	
//	if(bucket.x < 0) bucket.x = 0;
//	if(bucket.x > Const.WIDTH -Const.OBJ_UNIT) bucket.x = Const.WIDTH -Const.OBJ_UNIT;
	}

}
