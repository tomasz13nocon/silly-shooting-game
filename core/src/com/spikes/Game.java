package com.spikes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.util.Iterator;

public class Game extends Entity {

	private Main main;
	private OrthographicCamera camera;
	private Player player;
	private Node bullets;
	private Node imperialBullets;
	private Node coins;
	private Level level;
	private Shop shop;
	private Text levelText, hpText, maxHpText, dmgText, speedText, moneyText;
	private float mapX, mapY;
	private Vector3 shootAt;

	public Game(Main main, OrthographicCamera camera, float mapX, float mapY) {
		this.mapX = mapX;
		this.mapY = mapY;
		this.main = main;
		this.camera = camera;

		bullets = new Node();
		imperialBullets = new Node();
		coins = new Node();
		player = new Player(mapX, mapY);
		level = new Level(this, player, bullets, imperialBullets, coins, mapX, mapY);
		attach(bullets);
		attach(imperialBullets);
		attach(coins);
		attach(player);
		attach(level);

		shop = new Shop(this, player);

		levelText = new Text(FontManager.font, "Level: " + level.getLevel());
		levelText.setCenter(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight() - levelText.height);

		hpText = new Text(FontManager.font2, "HP: " + player.getHp());
		hpText.setPosition(8, Gdx.graphics.getHeight() - hpText.getHeight() - 8);

		maxHpText = new Text(FontManager.font2, "Max HP: " + player.getMaxHp());
		maxHpText.setPosition(hpText.getPosition(new Vector2()).sub(0, hpText.getHeight() + 8));

		dmgText = new Text(FontManager.font2, "Damage: " + player.getDmg());
		dmgText.setPosition(maxHpText.getPosition(new Vector2()).sub(0, maxHpText.getHeight() + 8));

		speedText = new Text(FontManager.font2, "Speed: " + player.getSpeed());
		speedText.setPosition(dmgText.getPosition(new Vector2()).sub(0, dmgText.getHeight() + 8));

		moneyText = new Text(FontManager.font2, "PESO: " + player.getMoney());
		moneyText.setPosition(Gdx.graphics.getWidth() - moneyText.getWidth() - 24, Gdx.graphics.getHeight() - moneyText.getHeight() - 8);
	}

	public void goToMainMenu() {
		main.showMainMenu();
	}

	public void goToShop() {
		detach(level);
		attach(shop);
		if ((level.getLevel()+1) % 15 == 0)
			saveGame();
	}

	public void nextLevel() {
		detach(shop);
		level.nextLevel();
		attach(level);
	}

	public void cancelShoot() {
		shootAt = null;
	}

	private void saveGame() {

	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if (button == Input.Buttons.LEFT)
			shootAt = camera.unproject(new Vector3(screenX, screenY, 0.0f));
		return false;
	}

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Input.Keys.ESCAPE) {
			main.showIngameMenu();
			return true;
		}
		return false;
	}

	@Override
	public void update(float dt) {
		// Player-Coin collision
		for (Iterator<? extends Entity> itCoin = coins.getChildren().iterator(); itCoin.hasNext();) {
			Coin coin = (Coin)itCoin.next();
			if (player.intersects(coin)) {
				itCoin.remove();
				player.addMoney(coin.getValue());
			}
		}

		// Player-ImperialBullet collision
		for (Iterator<? extends Entity> itImpBullet = imperialBullets.getChildren().iterator(); itImpBullet.hasNext();) {
			Bullet imperialBullet = (Bullet)itImpBullet.next();
			if (player.intersects(imperialBullet)) {
				player.addHp(-imperialBullet.getDmg());
				itImpBullet.remove();
			}

			// ImperialBullets out of bounds
			if (imperialBullet.getX() < -100.0f || imperialBullet.getX() > mapX + 100.0f) itImpBullet.remove();
			else if (imperialBullet.getY() < -100.0f || imperialBullet.getY() > mapY + 100.0f) itImpBullet.remove();
		}

		// Shooting
		if (shootAt != null) {
			bullets.attach(player.shoot(shootAt.x, shootAt.y));
			shootAt = null;
		}

		// Bullets out of bounds
		for (Iterator<? extends Entity> it = bullets.getChildren().iterator(); it.hasNext();) {
			Bullet bullet = (Bullet)it.next();
			if (bullet.getX() < -100.0f || bullet.getX() > mapX + 100.0f) it.remove();
			else if (bullet.getY() < -100.0f || bullet.getY() > mapY + 100.0f) it.remove();
		}

		// Update texts
		hpText.setText("HP: " + player.getHp());
		levelText.setText("Level: " + level.getLevel());
		maxHpText.setText("Max HP: " + player.getMaxHp());
		dmgText.setText("Damage: " + player.getDmg());
		speedText.setText("Speed: " + player.getSpeed());
		moneyText.setText("PESO: " + player.getMoney());

		// Camera position
		float halfWidth = Gdx.graphics.getWidth() / 2.0f;
		float halfHeight = Gdx.graphics.getHeight() / 2.0f;

		camera.position.x = player.getCenterX() - (player.getCenterX()/mapX - 0.5f)*500.0f*(halfWidth/halfHeight);
		if (camera.position.x < halfWidth) camera.position.x = halfWidth;
		else if (camera.position.x > mapX - halfWidth) camera.position.x = mapX - halfWidth;

		camera.position.y = player.getCenterY() - (player.getCenterY()/mapY - 0.5f)*500.0f;
		if (camera.position.y < halfHeight) camera.position.y = halfHeight;
		else if (camera.position.y > mapY - halfHeight) camera.position.y = mapY - halfHeight;
	}

	@Override
	public void render(SpriteBatch batch) {
		levelText.draw(batch);
		hpText.draw(batch);
		maxHpText.draw(batch);
		dmgText.draw(batch);
		speedText.draw(batch);
		moneyText.draw(batch);
	}

}
