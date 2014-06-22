package ar.edu.unq.bomberman.pause;

import java.awt.Font;

import ar.edu.unq.americana.events.annotations.Events.Fired;
import ar.edu.unq.americana.events.ioc.fired.FiredEvent;
import ar.edu.unq.americana.game.events.GameResumeEvent;
import ar.edu.unq.americana.scenes.menu.MenuGameScene;
import ar.edu.unq.bomberman.Bomberman;
import ar.edu.unq.bomberman.BombermanGameScene;

public class BombermanPauseScene extends MenuGameScene {

	private static class GameResetEvent extends FiredEvent {

	}

	@Override
	protected String logoResourcePath() {
		return "assets/menues/logo.png";
	}

	@Override
	protected void addButtons(final MenuBuilder menuBuilder) {
		menuBuilder.button("pause.resume", new GameResumeEvent());
		menuBuilder.button("pause.exit", new GameResetEvent());
	}

	@Fired(GameResetEvent.class)
	private void exit(final GameResetEvent event) {
		this.getGame().setCurrentScene(new BombermanGameScene());
	}

	@Override
	protected Font font() {
		return Bomberman.font;
	}

}
