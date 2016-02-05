package com.tower.defense.one.game.actor.player;

import static com.tower.defense.one.game.Assets.chinese;
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
	private int intBuy = 9999;
	
	private int type = 1;
	
	private Ellipse tempATKEllipse;
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
		tempATKEllipse = new Ellipse(0, 0, 0, 0);
		towerColor = Const.ButtonUnAvailableColor;
		
		this.type = type;
		if (locked) {
			chineseStr = "迷";
		} else {
			switch (type) {
			case CANNON:
				chineseStr = "炮";
				towerType = new OrcCannon();
				intBuy = towerType.getIntBuy(tower.Level + 1);
				towerColor = Const.CannonColor;
				break;
			case CRYPT:
				chineseStr = "兵";
				break;
			case CROSSBOW:
				chineseStr = "箭";
				break;
			case APPRENTICE:
				chineseStr = "法";
				break;
			case POISON:
				chineseStr = "毒";
				break;
			}
		}
		// if (locked) {
		// region = lockingIcon;
		// } else {
		// switch (type) {
		// case CANNON:
		// region = cannonIcon;
		// intBuy = OrcCannon.intBuy;
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
		ShaperRendererUtils.DrawCircle(optionCircle.x, optionCircle.y, optionCircle.radius + 4, Const.ButtonOuterColor, ShapeType.Filled);
		ShaperRendererUtils.DrawCircle(optionCircle, towerColor, ShapeType.Filled);
		ShaperRendererUtils.DrawCircle(optionCircle.x, optionCircle.y, optionCircle.radius - 2, intBuy <= BGPanel.TreasureCur ? Const.ButtonInnerAColor : Const.ButtonUnAvailableColor, ShapeType.Filled);
		
		batch.begin();
		if (lastTouchActorName == getName()) {
			chinese.draw(batch, "√", getX() - optionCircle.radius, getY() + chinese.getCapHeight()/2, optionCircle.radius * 2, Align.center, false);
		} else {
			chinese.draw(batch, chineseStr, getX() - optionCircle.radius, getY() + chinese.getCapHeight()/2, optionCircle.radius * 2, Align.center, false);
		}
	}

	public void onClick() {
		Gdx.app.debug(getName(), type + " ");
		if (lastTouchActorName != getName()) {
			if(towerType != null) {
				tower.tempATKBoundSize(towerType.getATKWidth(tower.Level + 1), towerType.getATKHeight(tower.Level + 1));
			} else {
				tower.tempATKBoundSize(-1, -1);
			}
		} else if (intBuy <= BGPanel.TreasureCur) {
			tempATKEllipse.set(0, 0, 0, 0);
			tower.hideWhenClickOthers();
			switch (type) {
			case CANNON:
				BGPanel.addTreasure(-intBuy);
				tower.setTowerType(towerType);
				break;
			case CRYPT:
				return;
			case CROSSBOW:
				return;
			case APPRENTICE:
				return;
			case POISON:
				return;
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
