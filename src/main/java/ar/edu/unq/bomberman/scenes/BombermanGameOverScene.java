package ar.edu.unq.bomberman.scenes;

import java.awt.Color;

import ar.edu.unq.americana.components.Text;
import ar.edu.unq.americana.game.events.GameResumeEvent;

public class BombermanGameOverScene extends BombermanGameScene {

	@Override
	protected void addExtras() {

		final Text<BombermanGameOverScene> label = new Text<BombermanGameOverScene>(
				"game.gameover", this.font(), Color.black);
		this.addComponent(label);
	}

	@Override
	protected void addButtons(final MenuBuilder menuBuilder) {
		menuBuilder.button("main.playAgain", new GameResumeEvent());
		menuBuilder.button("main.exit", new GameCloseEvent());

	}

}
