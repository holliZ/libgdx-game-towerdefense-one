package com.tower.defense.one.game;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.TimeUtils;

public class MyStage extends Table {
	
	public static final String BACKGROUND = "BACKGROUD";
	public static final String BUCKET = "BUCKET";
	
	private BucketActor bucket;
	private BackgroundActor background;
	private long lastDropTime;

	public MyStage(){
		setBounds(0, 0, Const.WIDTH, Const.HEIGHT);
		setClip(true);
		
		background = new BackgroundActor();
		background.setName(BACKGROUND);
		addActor(background);
		
		bucket = new BucketActor();
		bucket.setName(BUCKET);
		addActor(bucket);
		
		DropActor drop = new DropActor();
		addActor(drop);
		lastDropTime = TimeUtils.nanoTime();
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		
		if(TimeUtils.nanoTime() - lastDropTime > 1000000000) {
			DropActor drop = new DropActor();
			addActor(drop);
			lastDropTime = TimeUtils.nanoTime();
		}
	}
	
}
