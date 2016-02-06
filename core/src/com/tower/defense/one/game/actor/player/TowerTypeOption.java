package com.tower.defense.one.game.actor.player;

import static com.tower.defense.one.game.Assets.chinese;
import static com.tower.defense.one.game.Assets.font2;
import static com.tower.defense.one.game.Const.TOWERTYPE_BUTTON_RADIUS;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;
import com.tower.defense.one.game.Const;
import com.tower.defense.one.game.ShaperRendererUtils;
import com.tower.defense.one.game.actor.BasicActor;
import com.tower.defense.one.game.actor.bg.BGPanel;

public class TowerTypeOption extends BasicActor {

	public static final int CANNON = 1;
	public static final int CRYPT = 2;
	public static final int CROSSBOW = 3;
	public static final int APPRENTICE = 4;
	public static final int POISON = 5;

	// private TextureRegion region;
	private int IntBuyLocked = 10000;
	private int intBuy = IntBuyLocked;
	
	private int type = 1;
	
	private Ellipse tempAttackEllipse;
	private Color towerColor;
	private String chineseStr;
	private Circle optionCircle;
	private Tower tower;
	private TowerType towerType = null;

	public TowerTypeOption(Tower tower, float x, float y, int type) {
		this(tower, x, y, type, false);
	}

	public TowerTypeOption(Tower tower, float x, float y, int type, boolean locked) {
		super(x, y, 0, 0);
		this.tower = tower;
		optionCircle = new Circle(getX(), getY(), TOWERTYPE_BUTTON_RADIUS);
		tempAttackEllipse = new Ellipse(0, 0, 0, 0);
		towerColor = Const.ButtonUnAvailableColor;
		
		this.type = type;
		if (locked) {
			chineseStr = "迷";
		} else {
			switch (type) {
			case CANNON:
				towerType = new TowerCannon();
				break;
			case CRYPT:
				towerType = new TowerCrypt();
				break;
			case CROSSBOW:
				towerType = new TowerCrossbow();
				break;
			case APPRENTICE:
				towerType = new TowerApprentice();
				break;
			case POISON:
				towerType = new TowerPoison();
				break;
			}
			intBuy = towerType.getIntBuy(tower.Level + 1);
			towerColor = towerType.getTowerColor();
			chineseStr = towerType.getTowerTypeName();
		}
		// if (locked) {
		// region = lockingIcon;
		// } else {
		// switch (type) {
		// case CANNON:
		// region = cannonIcon;
		// intBuy = Cannon.intBuy;
		// break;
		// case CRYPT:
		// region = cryptIcon;
		// break;
		// case CROSSBOW:
		// region = crossbowIcon;
		// break;
		// case APPRENTICE:
		// region = apprenticeIcon;
		// break;
		// case POISON:
		// region = poisonIcon;
		// break;
		// }
		// }
		// setBounds(x - region.getRegionWidth() / 2, y -
		// region.getRegionHeight()
		// / 2, region.getRegionWidth(), region.getRegionHeight());
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);

		batch.end();
		
//		batch.draw(region, getX(), getY(), getWidth(), getHeight());
//		ShaperRendererUtils.DrawCircle(optionCircle.x, optionCircle.y, optionCircle.radius + 4, Const.ButtonOuterColor, ShapeType.Filled);
		ShaperRendererUtils.DrawCircle(optionCircle, towerColor, ShapeType.Filled);
		ShaperRendererUtils.DrawCircle(optionCircle.x, optionCircle.y, optionCircle.radius - 4, intBuy <= BGPanel.TreasureCur ? Const.ButtonInnerAColor : Const.ButtonUnAvailableColor, ShapeType.Filled);
		
		batch.begin();
		if (lastTouchActorName == getName() && intBuy < IntBuyLocked) {
			chinese.draw(batch, "√", getX() - optionCircle.radius, getY() + chinese.getCapHeight()/2, optionCircle.radius * 2, Align.center, false);
		} else {
			chinese.draw(batch, chineseStr, getX() - optionCircle.radius, getY() + chinese.getCapHeight()/2, optionCircle.radius * 2, Align.center, false);
		}
		
		if(intBuy < IntBuyLocked) {
			font2.draw(batch, intBuy +"", getX() - optionCircle.radius, getY() + chinese.getCapHeight() + 5, optionCircle.radius * 2, Align.center, false);
		}
	}

	public void onClick() {
		Gdx.app.debug(getName(), type + " ");
		if (lastTouchActorName != getName()) {
			if(towerType != null) {
				tower.tempAttackBoundSize(towerType.getAttackWidth(tower.Level + 1), towerType.getAttackHeight(tower.Level + 1));
			} else {
				tower.tempAttackBoundSize(0, 0);
			}
		} else if (intBuy <= BGPanel.TreasureCur && intBuy < IntBuyLocked) {
			tempAttackEllipse.set(0, 0, 0, 0);
			tower.hideWhenClickOthers();
			BGPanel.addTreasure(-intBuy);
			tower.setTowerType(towerType);
		}
	}
	
	@Override
	public Actor hit(float x, float y, boolean touchable) {
		if (touchable && !isTouchable())
			return null;
		return optionCircle.contains(getX() + x, getY() + y) ? this : null;
	}
	

}
