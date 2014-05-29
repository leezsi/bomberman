package ar.edu.unq.bomberman.pause;

import java.awt.Font;

import ar.edu.unq.americana.game.events.GameResumeEvent;
import ar.edu.unq.americana.scenes.pause.PauseGameScene;
import ar.edu.unq.bomberman.Bomberman;

public class BombermanPauseScene extends PauseGameScene {

	@Override
	protected String logoResourcePath() {
		return "assets/menues/logo.png";
	}

	@Override
	protected void addButtons(final MenuBuilder menuBuilder) {
		menuBuilder.button("pause.exit", new GameResumeEvent());
		menuBuilder.button("pause.resume", new GameResumeEvent());
	}

	@Override
	protected Font font() {
		return Bomberman.font;
	}

}
