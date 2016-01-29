package com.tower.defense.one.game;

import static com.tower.defense.one.game.Assets.shapeRenderer;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class Utils {

	public static void DrawFilledRoundRect(float x, float y, float width,
			float height, float radius, ShapeRenderer shapeRenderer) {
		shapeRenderer.rect(x + radius, y, width - radius * 2, height);
		shapeRenderer.rect(x, y + radius, width, height - radius * 2);
		
		shapeRenderer.circle(x + radius, y + radius, radius);
		shapeRenderer.circle(x + width - radius, y + radius, radius);
		
		shapeRenderer.circle(x + radius, y + height - radius, radius);
		shapeRenderer.circle(x + width - radius, y + height - radius, radius);
	}
	
	public static void DrawPauseButton(float x, float y, float width, float height,float interval){
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(86f/255, 86f/255, 86f/255, 1);
		DrawFilledRoundRect(x - 10 , y - 5, width + 10 * 2, height + 5 * 2, 10, shapeRenderer);
		shapeRenderer.setColor(220f/255, 220f/255, 220f/255, 1);
		shapeRenderer.rect(x, y, (width - interval)/2, height);
		shapeRenderer.rect(x + (width + interval)/2, y, (width - interval)/2, height);
		shapeRenderer.end();
	}
}
