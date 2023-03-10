package com.spikes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MainMenu extends Entity {

	private Text newGame, /*loadGame,*/ quitGame;
	private Main main;

	public MainMenu(final Main main) {
		this.main = main;
		newGame = new Text(FontManager.starFont, "new game");
		newGame.setCenter(Gdx.graphics.getWidth()/2, (Gdx.graphics.getHeight()/3) * 2);
		/*loadGame = new Text(FontManager.starFontGray, "load game");
		loadGame.setCenter(Gdx.graphics.getWidth()/2, (Gdx.graphics.getHeight()/6) * 3);*/
		quitGame = new Text(FontManager.starFont, "exit game");
		quitGame.setCenter(Gdx.graphics.getWidth()/2, (Gdx.graphics.getHeight()/3) * 1);
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if (button == Input.Buttons.LEFT) {
			int ypos = Gdx.graphics.getHeight() - screenY;
			if (newGame.contains(screenX, ypos))
				main.newGame();
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
		newGame.draw(batch);
		//loadGame.draw(batch);
		quitGame.draw(batch);
	}

}
