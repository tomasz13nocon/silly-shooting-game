package com.spikes;

import com.badlogic.gdx.math.Vector2;

import java.util.Iterator;

public class Level extends Node {

	private int level = 1;
	private Game game;
	private Player player;
	private Node minions;
	private Node bullets;
	private Node imperialBullets;
	private Node coins;
	private int tieFighterCount = 6, minion2Count = 0;
	private float mapX, mapY;


	public Level(Game game, Player player, Node bullets, Node imperialBullets, Node coins, float mapX, float mapY) {
		this.game = game;
		this.mapX = mapX;
		this.mapY = mapY;
		this.player = player;
		this.bullets = bullets;
		this.imperialBullets = imperialBullets;
		this.coins = coins;
		minions = new Node();
		attach(minions);
		spawnMinions();
	}

	public int getLevel() {
		return level;
	}

	public void nextLevel() {
		level++;
		tieFighterCount += 4;
		if (level > 2)
			minion2Count++;
		else if (level == 2)
			minion2Count = 2;

		spawnMinions();
	}

	private void spawnMinions() {
		for (int i = 0; i < tieFighterCount; i++) {
			Minion minion = new Minion(Minion.Type.TieFighter);
			calculateRandomPosition(minion);
			minions.attach(minion);
		}
		for (int i = 0; i < minion2Count; i++) {
			Minion minion = new Minion(Minion.Type.Minion2);
			calculateRandomPosition(minion);
			minions.attach(minion);
		}
	}

	private void calculateRandomPosition(Minion minion) {
		float rand = (float)Math.random() * 600;
		minion.setCenter(rand < 300 ? rand : mapX + 300 - rand, (float)Math.random() * mapY);
	}

	@Override
	public void update(float dt) {
		for (Iterator<? extends Entity> itMinion = minions.getChildren().iterator(); itMinion.hasNext();) {
			Minion minion = (Minion)itMinion.next();
			// Minion movement
			Vector2 direction = player.getCenter().sub(minion.getCenter()).nor();
			minion.move(direction.x * minion.getSpeed() * dt, direction.y * minion.getSpeed() * dt);
			minion.setRotation(-(float)Math.toDegrees(Math.atan2(direction.x, direction.y)) + 180);

			// Minion-Player collision
			if (player.intersects(minion)) {
				player.addHp(-minion.getDmg());
				itMinion.remove();
			}

			// Minion-Bullet collision
			for (Iterator<? extends Entity> itBullet = bullets.getChildren().iterator(); itBullet.hasNext();) {
				Bullet bullet = (Bullet)itBullet.next();
				if (minion.intersects(bullet)) {
					minion.addHp(-player.getDmg());
					itBullet.remove();
				}
			}

			// Minion shots
			if (minion.getType() == Minion.Type.Minion2) {
				if (Math.random() < 0.06f * dt) {
					imperialBullets.attach(minion.shoot(player.getCenterX(), player.getCenterY()));
				}
			}

			// Minion HP
			if (minion.getHp() <= 0) {
				itMinion.remove();
				if (Math.random() < 0.2)
					coins.attach(new Coin(minion.getCenterX(), minion.getCenterY()));
			}

			// Player HP
			if (player.getHp() <= 0)
				game.goToMainMenu();

			// Minion count check
			if (minions.getChildren().size() == 0)
				game.goToShop();
		}

	}

}
