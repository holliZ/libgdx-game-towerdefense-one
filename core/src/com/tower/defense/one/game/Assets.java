package com.tower.defense.one.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Assets {
	public static Texture dropImage;
	public static Texture bucketImage;

	public static Sound dropSound;
	public static Music rainMusic;
	public static Animation bob;

	public static Texture items;
	public static Texture items2;

	public static TextureRegion cannonIcon;
	public static TextureRegion cryptIcon;
	public static TextureRegion crossbowIcon;
	public static TextureRegion apprenticeIcon;
	public static TextureRegion poisonIcon;
	public static TextureRegion lockingIcon;

	public static BitmapFont font;
	public static BitmapFont font2;
	public static ShapeRenderer shapeRenderer ;
	
	public static void load() {
		dropImage = loadTexture("2.jpg");
		bucketImage = loadTexture("1.jpg");

		dropSound = loadSound("record.mp3");
		rainMusic = loadMusic("music.mp3");

		rainMusic.setLooping(true);

		items = loadTexture("data/towerIcon.png");
		items2 = loadTexture("data/items.png");

		cannonIcon = new TextureRegion(items, 0, 84, 62, 39);
		cryptIcon = new TextureRegion(items, 64, 84, 61, 37);
		crossbowIcon = new TextureRegion(items, 64, 41, 61, 41);
		apprenticeIcon = new TextureRegion(items, 0, 0, 63, 39);
		poisonIcon = new TextureRegion(items, 0, 41, 62, 41);
		lockingIcon = new TextureRegion(items, 65, 0, 39, 37);

		bob = new Animation(0.2f, new TextureRegion(items2, 0, 128, 32, 32),
				new TextureRegion(items2, 64, 128, 32, 32), new TextureRegion(
						items2, 32, 128, 32, 32), new TextureRegion(items2, 96,
						128, 32, 32));

		font = new BitmapFont(Gdx.files.internal("data/font.fnt"),
				Gdx.files.internal("data/font.png"), false);
		font2 = new BitmapFont();
		
		shapeRenderer = new ShapeRenderer();
	}

	public static void dispose() {
		dropImage.dispose();
		bucketImage.dispose();
		dropSound.dispose();
		rainMusic.dispose();

		items.dispose();
		items2.dispose();
		
		font.dispose();
		font2.dispose();
		shapeRenderer.dispose();
	}

	public static Texture loadTexture(String file) {
		return new Texture(Gdx.files.internal(file));
	}

	public static Sound loadSound(String file) {
		return Gdx.audio.newSound(Gdx.files.internal(file));
	}

	public static Music loadMusic(String file) {
		return Gdx.audio.newMusic(Gdx.files.internal(file));
	}
}
