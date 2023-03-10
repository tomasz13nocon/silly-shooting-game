package com.spikes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.DistanceFieldFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class FontManager {

	public static final BitmapFont font;
	public static final BitmapFont font2;
	public static final BitmapFont starFont;
	//public static final BitmapFont starFontGray;
	private static Clock clock = new Clock();

	static {
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Roboto-Bold.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 32;
		clock.restart();
		font = generator.generateFont(parameter);
		System.out.println("font loaded in: " + clock.getTimeInSeconds());

		parameter.size = 18;
		clock.restart();
		font2 = generator.generateFont(parameter);
		System.out.println("font2 loaded in: " + clock.getTimeInSeconds());

		generator = new FreeTypeFontGenerator(Gdx.files.internal("Starjedi.ttf"));
		parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 48;
		parameter.color = Color.YELLOW;
		clock.restart();
		starFont = generator.generateFont(parameter);
		System.out.println("starFont loaded in: " + clock.getTimeInSeconds());

		/*parameter.color = new Color(0.4f, 0.4f, 0.4f, 1.0f);
		clock.restart();
		starFontGray = generator.generateFont(parameter);
		System.out.println("starFontGray loaded in: " + clock.getTimeInSeconds());*/


		generator.dispose();
	}

}
