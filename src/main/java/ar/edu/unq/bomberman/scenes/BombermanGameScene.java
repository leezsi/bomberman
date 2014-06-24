package ar.edu.unq.bomberman.scenes;

import java.awt.Font;

import ar.edu.unq.americana.Game;
import ar.edu.unq.americana.events.annotations.Events.Fired;
import ar.edu.unq.americana.events.ioc.fired.FiredEvent;
import ar.edu.unq.americana.game.events.GameResumeEvent;
import ar.edu.unq.americana.scenes.menu.MenuGameScene;
import ar.edu.unq.bomberman.Bomberman;

public class BombermanGameScene extends MenuGameScene {
	protected static class GameCloseEvent extends FiredEvent {

	}

	@Override
	protected void addButtons(final MenuBuilder menuBuilder) {
		menuBuilder.button("main.play", new GameResumeEvent());
		menuBuilder.button("main.exit", new GameCloseEvent());

	}

	@Override
	protected void resume(final GameResumeEvent event) {
		final Game game = this.getGame();
		game.startGame();
	}

	@Fired(GameCloseEvent.class)
	private void closeGame(final GameCloseEvent closeEvent) {
		this.getGame().closeGame();
	}

	@Override
	protected String logoResourcePath() {
		return "assets/menues/logo.png";
	}

	@Override
	protected Font font() {
		return Bomberman.font;
	}

}
