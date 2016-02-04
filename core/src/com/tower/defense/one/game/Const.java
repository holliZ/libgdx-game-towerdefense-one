package com.tower.defense.one.game;

import com.badlogic.gdx.graphics.Color;

public class Const {
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	
	public static final int OBJ_UNIT = 64;
	
	public static final int ENEMY_WIDTH = 32;
	public static final int ENEMY_HEIGHT = 32;
	
	public static final int TOWER_WIDTH = 80;
	public static final int TOWER_HEIGHT = 55;
	
	public static final int TOWER_TYPE_WIDTH = 64;
	public static final int TOWER_TYPE_HEIGHT = 64;
	
	public static final String ENEMY_ACTOR = "EnemyActor" ;
	public static final String TOWER_ACTOR = "TowerActor" ;
	
	public static final long ONE_SECOND = 1000000000;
	
	public static final int GAME_RUNNING = 0;
	public static final int GAME_PAUSED = 1;
	public static final int GAME_OVER = 2;
	
	public static final int ACTION_BUTTON_WIDTH = 150;
	public static final int ACTION_BUTTON_HEIGHT = 36;
	
	public static final int TOWERTYPE_BUTTON_RADIUS = 28;
	
	public static final Color SplitLinesColor = new Color(72 / 255f, 22 / 255f, 15 / 255f, 1f);
	public static final Color CoverColor = new Color(141/255f, 141/255f, 141/255f, 120/255f);
	public static final Color SummaryColor = new Color(191/255f, 191/255f, 191/255f, 1f);
	public static final Color CheckedLineColor = Color.BLACK;
	
	public static final Color RouteColor = new Color(251/255f, 195/255f, 136/255f, 1);
	public static final Color ButtonBGColor = new Color(239/255f, 172/255f, 52/255f, 1);
	public static final Color ButtonAvailableColor = new Color(187/255f,209/255f,35/255f,1f);

	public static final Color LockedTowerColor = new Color(112/255f, 112/255f, 112/255f, 0.8f);
	public static final Color DefaultTowerColor = new Color(219/255f, 163/255f, 27/255f, 1f);
	public static final Color CannonColor = new Color(183/255f, 32/255f, 238/255f, 1f);
	
	
}
