package com.tower.defense.one.game;

import static com.tower.defense.one.game.Assets.font;
import static com.tower.defense.one.game.Assets.shapeRenderer;
import static com.tower.defense.one.game.Const.CoverColor;
import static com.tower.defense.one.game.Const.HEIGHT;
import static com.tower.defense.one.game.Const.WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.utils.Align;

public class ShaperRendererUtils {
	
	private static boolean alpha = false;
	
	public static void EnableAlpha(){
		alpha = true;
	}
	
	public static void DrawLine(float x1, float y1, float x2, float y2,  Color color, ShapeType type){
		shapeRenderer.begin(type);
		shapeRenderer.setColor(color);
		shapeRenderer.line(x1, y1, x2, y2);
		shapeRenderer.end();
	}
	
	public static void DrawRectangle(float x, float y, float width, float height, Color color, ShapeType type){
		if(alpha) {
			Gdx.gl.glEnable(GL20.GL_BLEND);
			Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		}
		shapeRenderer.begin(type);
		shapeRenderer.setColor(color);
		shapeRenderer.rect(x, y, width, height);
		shapeRenderer.end();
		if(alpha) {
			Gdx.gl.glDisable(GL20.GL_BLEND);
		}
		alpha = false;
	}
	
	public static void DrawEllipse(Ellipse ellipse, Color color, ShapeType type) {
		if(alpha) {
			Gdx.gl.glEnable(GL20.GL_BLEND);
			Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		}
		shapeRenderer.begin(type);
		shapeRenderer.setColor(color);
		shapeRenderer.ellipse(ellipse.x - ellipse.width/2, ellipse.y - ellipse.height/2, ellipse.width, ellipse.height);
		shapeRenderer.end();
		if(alpha) {
			Gdx.gl.glDisable(GL20.GL_BLEND);
		}
		alpha = false;
	}
	
	public static void DrawCircle(Circle circle, Color color, ShapeType type){
		DrawCircle(circle.x, circle.y, circle.radius, color, type);
	}
	
	public static void DrawCircle(float x, float y, float radius, Color color, ShapeType type){
		if(alpha) {
			Gdx.gl.glEnable(GL20.GL_BLEND);
			Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		}
		shapeRenderer.begin(type);
		shapeRenderer.setColor(color);
		shapeRenderer.circle(x, y, radius);
		shapeRenderer.end();
		if(alpha) {
			Gdx.gl.glDisable(GL20.GL_BLEND);
		}
		alpha = false;
	}
	
	public static void DrawFilledRoundRect(float x, float y, float width,
			float height, float radius, Color color) {
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(color);
		shapeRenderer.rect(x + radius, y, width - radius * 2, height);
		shapeRenderer.rect(x, y + radius, width, height - radius * 2);
		
		shapeRenderer.circle(x + radius, y + radius, radius);
		shapeRenderer.circle(x + width - radius, y + radius, radius);
		
		shapeRenderer.circle(x + radius, y + height - radius, radius);
		shapeRenderer.circle(x + width - radius, y + height - radius, radius);
		shapeRenderer.end();
	}
	
	public static void DrawTopFontButton(Batch batch, String s, float x, float y, float width, float height){
		batch.end();
		DrawFilledRoundRect(x-2 ,y-2 ,width+4, height+4, 10, Const.ButtonOuterColor);
		DrawFilledRoundRect(x ,y ,width, height, 10, Const.TopButtonBGColor);
		batch.begin();
		font.draw(batch, s, x, y + 30, width, Align.center, false);
	}
	
	public static void DrawFontButton(Batch batch, String s, float x, float y, float width, float height, Color color) {
		batch.end();
		DrawFilledRoundRect(x-2 ,y-2 ,width+4, height+4, 10, Const.ButtonOuterColor);
		DrawFilledRoundRect(x ,y ,width, height, 10, color);
		batch.begin();
		font.draw(batch, s, x, y + 30, width, Align.center, false);
	}
	
	public static void DrawRouteByCorners(float[][] corners, ShapeType type){
		int radius = 30;
		float lastX, lastY, nextX, nextY;
		if(alpha) {
			Gdx.gl.glEnable(GL20.GL_BLEND);
			Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		}
		shapeRenderer.begin(type);
		shapeRenderer.setColor(Const.RouteColor);
		if(corners.length > 0) {
			lastX = corners[0][0];
			lastY = corners[0][1];
			for(int i = 1; i < corners.length; i++){
				nextX = corners[i][0];
				nextY = corners[i][1];
				if(type == ShapeType.Line) {
					shapeRenderer.line(lastX, lastY - radius, nextX, nextY - radius);
					shapeRenderer.line(lastX, lastY + radius, nextX, nextY + radius);
				} else if(type == ShapeType.Filled) {
					shapeRenderer.circle(lastX, lastY, radius);
					drawObliqueRect(lastX, lastY, nextX, nextY, radius * 2);
				}
				lastX = nextX;
				lastY = nextY;
			}
			if(type == ShapeType.Filled) {
				shapeRenderer.circle(lastX, lastY, radius);
			}
		}
		shapeRenderer.end();
		if(alpha) {
			Gdx.gl.glDisable(GL20.GL_BLEND);
		}
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
	
	public static void DrawRightNowButton(float x, float y, float radius, float angel) {
		DrawCircle(x, y, radius, Const.RightNowOuterColor, ShapeType.Filled);
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(Const.RightNowMiddleColor);
		shapeRenderer.arc(x, y, radius - 3, 90, angel);
		shapeRenderer.end();
		DrawCircle(x, y, radius - 5, Const.RightNowInnerColor, ShapeType.Filled);
	}
	
	public static void DrawCoverLayer() {
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(CoverColor);
		shapeRenderer.rect(0, 0, WIDTH, HEIGHT);
		shapeRenderer.end();
		Gdx.gl.glDisable(GL20.GL_BLEND);
	}
	
	public static void DrawCoverCircle(float x, float y, float radius){
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(CoverColor);
		shapeRenderer.circle(x, y, radius);
		shapeRenderer.end();
		Gdx.gl.glDisable(GL20.GL_BLEND);
	}
	
//	static void DrawPauseButton(float x, float y, float width, float height,float interval){
//	shapeRenderer.begin(ShapeType.Filled);
//	shapeRenderer.setColor(86/255f, 86/255f, 86/255f, 1);
//	DrawFilledRoundRect(x - 10 , y - 5, width + 10 * 2, height + 5 * 2, 10, shapeRenderer);
//	shapeRenderer.setColor(220/255f, 220/255f, 220/255f, 1);
//	shapeRenderer.rect(x, y, (width - interval)/2, height);
//	shapeRenderer.rect(x + (width + interval)/2, y, (width - interval)/2, height);
//	shapeRenderer.end();
//}
}
