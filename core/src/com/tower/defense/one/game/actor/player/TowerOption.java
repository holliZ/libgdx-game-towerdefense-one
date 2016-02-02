package com.tower.defense.one.game.actor.player;

import static com.tower.defense.one.game.Assets.apprenticeIcon;
import static com.tower.defense.one.game.Assets.cannonIcon;
import static com.tower.defense.one.game.Assets.crossbowIcon;
import static com.tower.defense.one.game.Assets.cryptIcon;
import static com.tower.defense.one.game.Assets.lockingIcon;
import static com.tower.defense.one.game.Assets.poisonIcon;
import static com.tower.defense.one.game.Assets.shapeRenderer;
import static com.tower.defense.one.game.screen.GameScreen.lastTouchActorName;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Ellipse;
import com.tower.defense.one.game.actor.BasicActor;
import com.tower.defense.one.game.actor.bg.BGPanel;

public class TowerOption extends BasicActor {

	public static final int CANNON = 1;
	public static final int CRYPT = 2;
	public static final int CROSSBOW = 3;
	public static final int APPRENTICE = 4;
	public static final int POISON = 5;

	private TextureRegion region;
	private int type = 1;
	private int COST = 10000;

	private Ellipse tempATKEllipse;
	private Color tempATKBoundColor;

	public TowerOption(float x, float y, int type) {
		this(x, y, type, false);
	}

	public TowerOption(float x, float y, int type, boolean locked) {
		super(x, y, 0, 0);
		tempATKEllipse = new Ellipse(0, 0, 0, 0);
		tempATKBoundColor = new Color();
		this.type = type;
		if (locked) {
			region = lockingIcon;
		} else {
			switch (type) {
			case CANNON:
				region = cannonIcon;
				COST = OrcCannon.COST;
				break;
			case CRYPT:
				region = cryptIcon;
				break;
			case CROSSBOW:
				region = crossbowIcon;
				break;
			case APPRENTICE:
				region = apprenticeIcon;
				break;
			case POISON:
				region = poisonIcon;
				break;
			}
		}
		setBounds(x - region.getRegionWidth() / 2, y - region.getRegionHeight()
				/ 2, region.getRegionWidth(), region.getRegionHeight());
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);

		batch.end();

		
		if (COST <= BGPanel.TreasureCur) {
//			shapeRenderer.setColor(225/255f, 94/255f, 39/255f, 0.8f);
		} else {
			Gdx.gl.glEnable(GL20.GL_BLEND);
			Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
			shapeRenderer.begin(ShapeType.Filled);
			shapeRenderer.setColor(112/255f, 112/255f, 112/255f, 0.8f);
			shapeRenderer.rect(getX(), getY(), getWidth(), getHeight());
			shapeRenderer.end();
			Gdx.gl.glDisable(GL20.GL_BLEND);
		}

		if (lastTouchActorName == getName()) {
			shapeRenderer.begin(ShapeType.Line);
			shapeRenderer.setColor(1, 0, 0, 1);
			shapeRenderer.rect(getX(), getY(), getWidth(), getHeight());
			shapeRenderer.setColor(tempATKBoundColor.r, tempATKBoundColor.g,
					tempATKBoundColor.b, tempATKBoundColor.a);
			shapeRenderer.ellipse(tempATKEllipse.x - tempATKEllipse.width / 2,
					tempATKEllipse.y - tempATKEllipse.height / 2,
					tempATKEllipse.width, tempATKEllipse.height);
			shapeRenderer.end();
		}

		batch.begin();
		batch.draw(region, getX(), getY(), getWidth(), getHeight());
	}

	@Override
	public void act(float delta) {
		super.act(delta);
	}

	public void onClick() {
		Gdx.app.debug("TowerType", type + " ");
		if (lastTouchActorName != getName()) {
			Tower tower = getParent().findActor(getName().split("_")[0]);
			if (tower != null) {
				switch (type) {
				case CANNON:
					tempATKEllipse.set(tower.offsetX, tower.offsetY,
							OrcCannon.getATKWidth(OrcCannon.Level1),
							OrcCannon.getATKHeight(OrcCannon.Level1));
					tempATKBoundColor.set(OrcCannon.ATKBoundColor);
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

		} else if (COST <= BGPanel.TreasureCur) {
			tempATKEllipse.set(0, 0, 0, 0);
			Tower tower = getParent().findActor(getName().split("_")[0]);
			if (tower != null) {
				switch (type) {
				case CANNON:
					Tower newTower = new OrcCannon(tower.offsetX, tower.offsetY);
					BGPanel.addTreasure(-COST);
					newTower.setName(tower.getName());
					getParent().addActorAfter(tower, newTower);
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
				tower.hideWhenClickOthers();
				tower.setVisible(false);

				for (int i = 1; i <= 5; i++) {
					if (i == type) {
						continue;
					}
					TowerOption type = getParent().findActor(
							getName().split("_")[0] + "_" + i);
					type.remove();
				}
				tower.remove();
				remove();
			}
		}
	}

}
