package com.tower.defense.one.game.actor.player;

import static com.tower.defense.one.game.Assets.chinese;
import static com.tower.defense.one.game.Assets.font2;
import static com.tower.defense.one.game.Const.TOWERTYPE_BUTTON_RADIUS;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;
import com.tower.defense.one.game.Const;
import com.tower.defense.one.game.ShaperRendererUtils;
import com.tower.defense.one.game.actor.BasicActor;
import com.tower.defense.one.game.actor.bg.BGPanel;

public class TowerActionOption extends BasicActor {

	public static final int SELL = 1;
	public static final int UPGRADE = 2;
	
	private int intBuy;
	private int intSale;
	
	private int type = 1;
	
	private Circle optionCircle;
	private String chineseStr;
	private Color optionColor;
	
	private Tower tower;
	private TowerType towerType = null;
	
	public TowerActionOption(Tower tower, float x, float y, int type) {
		super(x, y, 0, 0);
		this.tower = tower;
		optionCircle = new Circle(getX(), getY(), TOWERTYPE_BUTTON_RADIUS);
		this.type = type;
	}
	
	public void update(){
		towerType = tower.getTowerType();
		if(towerType != null) {
			switch (type) {
			case SELL:
				chineseStr = "售";
				optionColor = Const.ButtonInnerASColor;
				intSale = tower.getTowerType().getIntSale(tower.Level);
				break;
			case UPGRADE:
				chineseStr = "升";
				optionColor = Const.ButtonInnerAColor;
				intBuy = tower.getTowerType().getIntBuy(tower.Level + 1);
				break;
			}
		} else {
			intBuy = 0;
			intSale = 0;
		}
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);

		batch.end();
		
		ShaperRendererUtils.DrawCircle(optionCircle, Const.ButtonOuterColor, ShapeType.Filled);
		ShaperRendererUtils.DrawCircle(optionCircle.x, optionCircle.y, optionCircle.radius - 4, (intBuy <= BGPanel.TreasureCur || intSale > 0)?optionColor: Const.ButtonUnAvailableColor, ShapeType.Filled);
		
//		batch.draw(region, getX(), getY(), getWidth(), getHeight());
//		shapeRenderer.setColor(towerColor);
//		shapeRenderer.circle(optionCircle.x, optionCircle.y, optionCircle.radius + 2);
		
		
		batch.begin();
		if (lastTouchActorName == getName() && intSale > 0) {
			chinese.draw(batch, "√", getX() - optionCircle.radius, getY() + chinese.getCapHeight()/2, optionCircle.radius * 2, Align.center, false);
		} else {
			chinese.draw(batch, chineseStr, getX() - optionCircle.radius, getY() + chinese.getCapHeight()/2, optionCircle.radius * 2, Align.center, false);
		}
		
		if(intBuy > 0) {
			font2.draw(batch, intBuy +"", getX() - optionCircle.radius, getY() + chinese.getCapHeight() + 5, optionCircle.radius * 2, Align.center, false);
		}
	}
	
	public void onClick() {
		Gdx.app.debug(getName(), type + " ");
		if (lastTouchActorName != getName()) {
			if (tower != null) {
				switch (type) {
				case SELL:
					tower.tempAttackBoundSize(0, 0);
					break;
				case UPGRADE:
					tower.tempAttackBoundSize(towerType.getAttackWidth(tower.Level + 1), towerType.getAttackHeight(tower.Level + 1));
					break;
				}
			}

		} else if (intBuy <= BGPanel.TreasureCur) {
			tower.hideWhenClickOthers();
			if (tower != null) {
				switch (type) {
				case SELL:
					BGPanel.addTreasure(intSale);
					tower.setTowerType(null);
					break;
				case UPGRADE:
					BGPanel.addTreasure(-intBuy);
					tower.upgrade();
					break;
				}
			}
		}
	}
	
	@Override
	public Actor hit(float x, float y, boolean touchable) {
		if (touchable && !isTouchable())
			return null;
		return optionCircle.contains(getX() + x, getY() + y) ? this : null;
	}
	
}
