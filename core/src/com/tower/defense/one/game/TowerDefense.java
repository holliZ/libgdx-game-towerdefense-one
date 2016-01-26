package com.tower.defense.one.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.tower.defense.one.game.screen.GameScreen;

public class TowerDefense extends Game {
	
	@Override
	public void create () {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		Assets.load();
		this.setScreen(new GameScreen(this));
	}

	@Override
	public void render () {
//		Gdx.app.debug("TowerDefense", "render game");
		super.render();
	}

	@Override
	public void dispose() {
		long javaHeap = Gdx.app.getJavaHeap();
		long nativeHeap = Gdx.app.getNativeHeap();
		Gdx.app.debug("TowerDefense", "javaHeap:" + javaHeap);
		Gdx.app.debug("TowerDefense", "nativeHeap:" + nativeHeap);
		switch (Gdx.app.getType()) {
	    case Android:
	    	Gdx.app.debug("TowerDefense", "android platform");
	    	int androidVersion = Gdx.app.getVersion();
	    	Gdx.app.debug("TowerDefense", "android version:" + androidVersion);
	        break;
	    case Desktop:
	    	Gdx.app.debug("TowerDefense", "desktop platform");
	        break;
	    case WebGL:
	    	Gdx.app.debug("TowerDefense", "web platform");
	        break;
	    default:
	    	Gdx.app.debug("TowerDefense", "other platform");
	}
		Assets.dispose();
	}
	
}
