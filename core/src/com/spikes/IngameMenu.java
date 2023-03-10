package com.spikes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class IngameMenu extends Entity {

	private Text continueGame, /*loadGame, saveGame,*/ quitGame;
	private Main main;

	public IngameMenu(final Main main) {
		this.main = main;
		continueGame = new Text(FontManager.starFont, "continue");
		continueGame.setCenter(Gdx.graphics.getWidth()/2, (Gdx.graphics.getHeight()/3) * 2);
		/*loadGame = new Text(FontManager.starFontGray, "load game");
		loadGame.setCenter(Gdx.graphics.getWidth()/2, (Gdx.graphics.getHeight()/7) * 4);
		saveGame = new Text(FontManager.starFontGray, "save game");
		saveGame.setCenter(Gdx.graphics.getWidth()/2, (Gdx.graphics.getHeight()/7) * 3);*/
		quitGame = new Text(FontManager.starFont, "exit game");
		quitGame.setCenter(Gdx.graphics.getWidth()/2, (Gdx.graphics.getHeight()/3) * 1);
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if (button == Input.Buttons.LEFT) {
			int ypos = Gdx.graphics.getHeight() - screenY;
			if (continueGame.contains(screenX, ypos))
				main.continueGame();
			else if (quitGame.contains(screenX, ypos))
				Gdx.app.exit();
		}
		return false;
	}

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Input.Keys.ESCAPE)
			Gdx.app.exit();
		return false;
	}

	@Override
	public void render(SpriteBatch batch) {
		continueGame.draw(batch);
		/*loadGame.draw(batch);
		saveGame.draw(batch);*/
		quitGame.draw(batch);
	}

}
