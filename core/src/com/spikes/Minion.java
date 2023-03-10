package com.spikes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class Minion extends SpriteEntity {

	private int hp;
	private int speed;
	private int goldDrop;
	private int dmg;
	private Type type;
	private static Texture texture = new Texture("ships.png");
	private static Sound shootingSound = Gdx.audio.newSound(Gdx.files.internal("Laser.ogg"));

	public enum Type {
		TieFighter,
		Minion2
	}

	public Minion(Type type) {
		this.type = type;
		switch (type) {
			case TieFighter:
				hp = 100;
				speed = 280;
				goldDrop = 10;
				dmg = 40;
				sprite = new Sprite(texture, 0, 0, 10, 9);
				sprite.setScale(3.0f);
				break;
			case Minion2:
				hp = 400;
				speed = 200;
				goldDrop = 15;
				dmg = 80;
				sprite = new Sprite(texture, 53, 0, 12, 14);
				sprite.setScale(3.0f);
				break;
			default:
				throw new IllegalArgumentException("Invalid minion type.");
		}
	}

	public Bullet shoot(float x, float y) {
		Vector2 direction = new Vector2(x, y).sub(sprite.getX()+sprite.getOriginX(), sprite.getY()+sprite.getOriginY()).nor();
		float rotation = -(float)Math.toDegrees(Math.atan2(direction.x, direction.y));

		Bullet bullet = new Bullet(sprite.getX()+sprite.getOriginX() + direction.x*15, sprite.getY()+sprite.getOriginY() + direction.y*15, direction, dmg, true);
		bullet.setRotation(rotation);

		long id = shootingSound.play();
		shootingSound.setVolume(id, 0.15f);
		shootingSound.setPitch(id, 1.15f);

		return bullet;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public void addHp(int hp) {
		this.hp += hp;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getGoldDrop() {
		return goldDrop;
	}

	public int getDmg() {
		return dmg;
	}

	public Type getType() {
		return type;
	}

}
