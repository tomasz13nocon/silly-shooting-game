package com.spikes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Coin extends SpriteEntity {

	private static Texture texture = new Texture("coin1.png");
	private int value;

	public Coin(float x, float y) {
		sprite = new Sprite(texture);
		sprite.setCenter(x, y);
		sprite.setScale(0.4f);
		value = 10;
	}

	public int getValue() {
		return value;
	}
}
