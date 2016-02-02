package com.tower.defense.one.game;

import static com.tower.defense.one.game.Assets.font;
import static com.tower.defense.one.game.Assets.shapeRenderer;

import com.badlogic.gdx.graphics.g2d.Batch;
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
	
	static void DrawPauseButton(float x, float y, float width, float height,float interval){
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(86/255f, 86/255f, 86/255f, 1);
		DrawFilledRoundRect(x - 10 , y - 5, width + 10 * 2, height + 5 * 2, 10, shapeRenderer);
		shapeRenderer.setColor(220/255f, 220/255f, 220/255f, 1);
		shapeRenderer.rect(x, y, (width - interval)/2, height);
		shapeRenderer.rect(x + (width + interval)/2, y, (width - interval)/2, height);
		shapeRenderer.end();
	}
	
	public static void DrawButtonByFont(Batch batch, String s, float x, float y, float width, float height, float fontOffsetX, float fontOffsetY){
		batch.end();
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(239/255f, 172/255f, 52/255f, 1);
		DrawFilledRoundRect(x ,y ,width, height, 10, shapeRenderer);
		shapeRenderer.end();
		batch.begin();
		font.draw(batch, s, x + 10 + fontOffsetX, y + 30 + fontOffsetY);
	}
	
	public static void DrawButtonByFont(Batch batch, String s, float x, float y, float width, float height){
		DrawButtonByFont(batch, s, x, y, width, height, 0, 0);
	}
	
	public static void DrawRouteInLineByCorners(Batch batch, float[][] corners){
		int radius = 30;
		float lastX, lastY, nextX, nextY;
		batch.end();
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.setColor(251/255f, 195/255f, 136/255f, 1);
		if(corners.length > 0) {
			lastX = corners[0][0];
			lastY = corners[0][1];
			for(int i = 1; i < corners.length; i++){
				nextX = corners[i][0];
				nextY = corners[i][1];
				shapeRenderer.line(lastX, lastY - radius, nextX, nextY - radius);
				shapeRenderer.line(lastX, lastY + radius, nextX, nextY + radius);
				lastX = nextX;
				lastY = nextY;
			}
		}
		shapeRenderer.end();
		batch.begin();
	}
	
	public static void DrawRouteInFilledByCorners(Batch batch, float[][] corners){
		int radius = 30;
		float lastX, lastY, nextX, nextY;
		batch.end();
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(251/255f, 195/255f, 136/255f, 1);
		shapeRenderer.identity();
		if(corners.length > 0) {
			lastX = corners[0][0];
			lastY = corners[0][1];
			for(int i = 1; i < corners.length; i++){
				nextX = corners[i][0];
				nextY = corners[i][1];
				shapeRenderer.circle(lastX, lastY, radius);
				drawObliqueRect(lastX, lastY, nextX, nextY, radius * 2);
				lastX = nextX;
				lastY = nextY;
			}
		}
		shapeRenderer.end();
		batch.begin();
	}
	
	private static void drawObliqueRect(float leftCenterX1, float leftCenterY1, float leftCenterX2, float leftCenterY2, float newHeight) {
		float centerX, centerY;
		float newLeftCenterX1, newLeftCenterY1;
		float newWidth, angel;
		centerX = (leftCenterX2 + leftCenterX1) / 2;
		centerY = (leftCenterY2 + leftCenterY1) / 2;
		newWidth = (float)(2 * Math.sqrt(Math.pow(leftCenterX1 - centerX, 2)
				+ Math.pow(leftCenterY1 - centerY, 2)));
		newLeftCenterX1 = (float)(centerX - newWidth/2);
		newLeftCenterY1 = centerY;
		
		angel = (float)(360 * Math.asin(Math.sqrt(Math.pow(leftCenterX1 - newLeftCenterX1, 2)
				+ Math.pow(leftCenterY1 - newLeftCenterY1, 2))
				/ newWidth)/ Math.PI);
		if(leftCenterX1 > newLeftCenterX1 && leftCenterY1 > newLeftCenterY1) {
			angel = -angel;
		}
		shapeRenderer.translate(centerX, centerY, 0);
		shapeRenderer.rotate(0, 0, 1, angel);
		shapeRenderer.rect(- newWidth / 2,  - newHeight / 2, newWidth, newHeight);
		shapeRenderer.rotate(0, 0, -1, angel);
		shapeRenderer.translate(-centerX, -centerY, 0);
	}
	
}
