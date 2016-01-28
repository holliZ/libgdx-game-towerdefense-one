package com.tower.defense.one.game;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;



public class StaticVariable {

	public static int enemyNum = 0;
	public static String lastTouchActorName = "";
	public static ShapeRenderer shapeRenderer ;
	
	public static void load(){
		shapeRenderer = new ShapeRenderer();
	}
	
	public static void dispose(){
		shapeRenderer.dispose();
	}
	
}
