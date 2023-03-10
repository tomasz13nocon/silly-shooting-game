package com.spikes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Shop extends Entity {

	private Text leaveText, buyDmgText, buyDmgCostText, buyHpText, buyHpCostText, restoreHpText, restoreHpCostText, buySpeedText, buySpeedCostText;
	private int dmgCost = 20, hpCost = 50, speedCost = 150, restoreHpCost;
	private Game game;
	private Player player;
	private Sound buyingSound = Gdx.audio.newSound(Gdx.files.internal("buy.ogg"));

	public Shop(Game game, Player player) {
		this.game = game;
		this.player = player;

		leaveText = new Text(FontManager.font, "LEAVE SHOP");
		leaveText.setCenter(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);


		buyDmgText = new Text(FontManager.font, "BUY 15 DAMAGE");
		buyDmgText.setCenter(Gdx.graphics.getWidth()/5 * 4, Gdx.graphics.getHeight()/3 * 2);
		buyDmgCostText = new Text(FontManager.font2, dmgCost + " peso");
		buyDmgCostText.setCenter(buyDmgText.getCenter(new Vector2()).sub(0, buyDmgText.getHeight()));

		buyHpText = new Text(FontManager.font, "BUY 40 MAX HP");
		buyHpText.setCenter(Gdx.graphics.getWidth()/5, Gdx.graphics.getHeight()/3 * 2);
		buyHpCostText = new Text(FontManager.font2, hpCost + " peso");
		buyHpCostText.setCenter(buyHpText.getCenter(new Vector2()).sub(0, buyHpText.getHeight()));

		restoreHpCost = (player.getMaxHp() - player.getHp())/5;
		restoreHpText = new Text(FontManager.font, "RESTORE HP");
		restoreHpText.setCenter(Gdx.graphics.getWidth()/5, Gdx.graphics.getHeight()/3);
		restoreHpCostText = new Text(FontManager.font2, restoreHpCost + " peso");
		restoreHpCostText.setCenter(restoreHpText.getCenter(new Vector2()).sub(0, restoreHpText.getHeight()));

		buySpeedText = new Text(FontManager.font, "BUY 60 SPEED");
		buySpeedText.setCenter(Gdx.graphics.getWidth()/5 * 4, Gdx.graphics.getHeight()/3);
		buySpeedCostText = new Text(FontManager.font2, speedCost + " peso");
		buySpeedCostText.setCenter(buySpeedText.getCenter(new Vector2()).sub(0, buySpeedText.getHeight()));
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		int posY = Gdx.graphics.getHeight() - screenY;
		if (leaveText.contains(screenX, posY)) {
			game.nextLevel();
			game.cancelShoot();
		}
		else if (buyDmgText.contains(screenX, posY)) {
			if (player.getMoney() >= dmgCost) {
				player.addMoney(-dmgCost);
				player.addDmg(15);
				buyingSound.play();
			}
			game.cancelShoot();
		}
		else if (buyHpText.contains(screenX, posY)) {
			if (player.getMoney() >= hpCost) {
				player.addMoney(-hpCost);
				player.addMaxHp(40);
				buyingSound.play();
			}
			game.cancelShoot();
		}
		else if (restoreHpText.contains(screenX, posY)) {
			if (player.getMoney() >= restoreHpCost && player.getHp() != player.getMaxHp()) {
				player.addMoney(-restoreHpCost);
				player.setHp(player.getMaxHp());
				buyingSound.play();
			}
			game.cancelShoot();
		}
		else if (buySpeedText.contains(screenX, posY)) {
			if (player.getMoney() >= speedCost) {
				player.addMoney(-speedCost);
				player.addSpeed(60);
				buyingSound.play();
			}
			game.cancelShoot();
		}
		return false;
	}

	@Override
	public void update(float dt) {
		if (player.getHp() > player.getMaxHp())
			throw new IllegalStateException("Player HP is bigger than his max HP.");
		restoreHpCost = (player.getMaxHp() - player.getHp())/5; // I have nowhere else to put it...
		restoreHpCostText.setText(restoreHpCost + " peso");
	}

	@Override
	public void render(SpriteBatch batch) {
		leaveText.draw(batch);
		buyDmgText.draw(batch);
		buyDmgCostText.draw(batch);
		buyHpText.draw(batch);
		buyHpCostText.draw(batch);
		restoreHpText.draw(batch);
		restoreHpCostText.draw(batch);
		buySpeedText.draw(batch);
		buySpeedCostText.draw(batch);
	}

}
