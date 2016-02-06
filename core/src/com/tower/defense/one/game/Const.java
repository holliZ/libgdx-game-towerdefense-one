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
	
	public static final Color BGColor = new Color(158/255f, 172/255f, 66/255f, 1);
	
	public static final Color SplitLinesColor = new Color(72 / 255f, 22 / 255f, 15 / 255f, 1f);
	public static final Color CoverColor = new Color(141/255f, 141/255f, 141/255f, 120/255f);
	public static final Color SummaryColor = new Color(191/255f, 191/255f, 191/255f, 1f);
	public static final Color CheckedLineColor = Color.BLACK;
	
	public static final Color RouteColor = new Color(250/255f, 189/255f, 132/255f, 1);
	
	public static final Color TopButtonBGColor = new Color(240/255f, 175/255f, 54/255f, 1);
	public static final Color ButtonOuterColor = new Color(76/255f, 60/255f, 48/255f, 1);
	public static final Color ButtonInnerADColor = new Color(134/255f,114/255f,84/255f,1f);//有效但不推荐的按钮的颜色,ButtonInnerAvailableDeprecatedColor
	public static final Color ButtonInnerAColor = new Color(170/255f,186/255f,53/255f,1f);
	public static final Color ButtonInnerASColor = new Color(230/255f,85/255f,20/255f,1f);
	public static final Color ButtonUnAvailableColor = new Color(112/255f, 112/255f, 112/255f, 0.8f);
	
	public static final Color AttackColor = new Color(230/255f, 84/255f, 20/255f, 0.2f);
	public static final Color DefaultTowerColor = new Color(243/255f, 204/255f, 102/255f, 1f);
	public static final Color CannonColor = new Color(183/255f, 32/255f, 238/255f, 1f);
	public static final Color CryptColor = new Color(230/255f, 59/255f, 78/255f, 1f);
	public static final Color CrossbowColor = new Color(138/255f, 88/255f, 244/255f, 1f);
	public static final Color ApprenticeColor = new Color(78/255f, 245/255f, 115/255f, 1f);
	public static final Color PoisonColor = new Color(238/255f, 96/255f, 188/255f, 1f);
	
	public static final Color RightNowOuterColor = new Color(100/255f, 79/255f, 60/255f, 1f);
	public static final Color RightNowMiddleColor = new Color(255/255f, 239/255f, 215/255f, 1f);
	public static final Color RightNowInnerColor = new Color(59/255f, 46/255f, 39/255f, 1f);
	
}
