package com.tower.defense.one.game.actor.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

public class OrcCannon extends Tower {
	
	public static final int Level1 = 1;
	public static final int Level2 = 2;
	public static final int Level3 = 3;
	
	static Color ATKBoundColor = new Color(183f/255, 32f/255, 238f/255, 1);
	
	public OrcCannon(float x, float y) {
		super(x, y);
		towerColor.set(183f/255, 32f/255, 238f/255, 1);
		updateATKBound(getATKWidth(Level1), getATKHeight(Level1));
	}
	
	public static float getATKWidth(int level){
		switch(level){
		case Level1 :
			return 300;
		case Level2 :
			return 350;
		case Level3 :
			return 450;
			default :
				return 0;
		}
	}
	
	public static float getATKHeight(int level){
		switch(level){
		case Level1 :
			return 200;
		case Level2 :
			return 250;
		case Level3 :
			return 300;
			default :
				return 0;
		}
	}

	@Override
	void showOrHideOption() {
		Gdx.app.debug("OrcCannon", "showOrHideTowerType");
	}

}
