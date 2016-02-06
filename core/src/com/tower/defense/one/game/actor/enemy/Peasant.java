package com.tower.defense.one.game.actor.enemy;

import com.badlogic.gdx.math.MathUtils;
import com.tower.defense.one.game.Assets;
import com.tower.defense.one.game.map.Route;

public class Peasant extends Enemy{
	
	private int AttackMax = 15;
	private int AttackMin = 5;

	public Peasant(Route route){
		super(route, Assets.bob);
	}

	@Override
	public float getATK() {
		return MathUtils.random(AttackMin, AttackMax);
	}

	@Override
	public float getMagicAttack() {
		return 0;
	}

	@Override
	public float getDenence() {
		return 1f;
	}

	@Override
	public float getMagicDenence() {
		return 1f;
	}

	@Override
	public int getHP() {
		return 135;
	}

	@Override
	public int getReward() {
		return 4;
	}

	@Override
	public int getHurt() {
		return 1;
	}

	@Override
	public float getMoveSpeed() {
		return 0.05f;
	}
}
