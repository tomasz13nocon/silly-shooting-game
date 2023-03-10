package com.spikes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

// TODO: Make this a hierarchy: Bullet <- RebelBullet, ImperialBullet
public class Bullet extends SpriteEntity {

	private static Texture texture = new Texture("shot.png");
	private static Texture imperialTexture = new Texture("imperialShot.png");
	private Vector2 direction;
	private float speed = 1000;
	private int dmg;

	public Bullet(float posX, float posY, Vector2 direction, int dmg) {
		this(posX, posY, direction, dmg, false);
	}

	public Bullet(float posX, float posY, Vector2 direction, int dmg, boolean imperial) {
		sprite = new Sprite(imperial ? imperialTexture : texture);
		if (imperial) speed = 800;
		sprite.setOrigin(0, 0);
		this.direction = direction;
		sprite.setPosition(posX, posY);
		this.dmg = dmg;
	}

	public int getDmg() {
		return dmg;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public float getX() {
		return sprite.getX();
	}

	public float getY() {
		return sprite.getY();
	}

	@Override
	public void update(float dt) {
		float offset = dt * speed;
		sprite.translate(direction.x * offset, direction.y * offset);
	}

}
