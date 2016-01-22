package com.tower.defense.one.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TowerDefense extends Game {
	SpriteBatch batch;
	BitmapFont font;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		font = new BitmapFont();
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		this.setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () {
		Gdx.app.debug("MyTag", "render game");
		super.render();
	}

	@Override
	public void dispose() {
		long javaHeap = Gdx.app.getJavaHeap();
		long nativeHeap = Gdx.app.getNativeHeap();
		System.out.println("javaHeap:" + javaHeap);
		System.out.println("nativeHeap:" + nativeHeap);
		switch (Gdx.app.getType()) {
	    case Android:
	    	System.out.println("android platform");
	    	int androidVersion = Gdx.app.getVersion();
	    	System.out.println("android version:" + androidVersion);
	        break;
	    case Desktop:
	    	System.out.println("desktop platform");
	        break;
	    case WebGL:
	    	System.out.println("web platform");
	        break;
	    default:
	    	System.out.println("other platform");
	}
		batch.dispose();
		font.dispose();
	}
	
}
