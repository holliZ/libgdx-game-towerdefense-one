package com.tower.defense.one.game.actor.enemy;

import com.badlogic.gdx.math.MathUtils;
import com.tower.defense.one.game.Assets;
import com.tower.defense.one.game.map.EnemyRoute;

public class Peasant extends Enemy{
	
	private int ATKMax = 15;
	private int ATKMin = 5;

	public Peasant(float x, float y, EnemyRoute route){
		super(x,y,route, Assets.bob);
		HP = 135;
		ATK = MathUtils.random(ATKMin, ATKMax);
		REWARD = 4;
	}
}
