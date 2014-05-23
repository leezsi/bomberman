package ar.edu.unq.bomberman.level.enemies;

import ar.edu.unq.americana.DeltaState;
import ar.edu.unq.americana.GameComponent;
import ar.edu.unq.americana.appearances.Animation;
import ar.edu.unq.americana.appearances.Shape;
import ar.edu.unq.americana.constants.Key;
import ar.edu.unq.americana.events.annotations.EventType;
import ar.edu.unq.americana.events.annotations.Events;
import ar.edu.unq.bomberman.level.GameMap;

public abstract class Enemy extends GameComponent<GameMap> {

	public Enemy() {
		this.setAppearance(this.aliveAnimation());
	}

	protected abstract Animation aliveAnimation();

	@Events.Keyboard(key = Key.SPACE, type = EventType.Pressed)
	private void die(final DeltaState state) {
		this.die();
	}

	protected void die() {
		this.setOnDieAppearance();
	}

	private void setOnDieAppearance() {
		this.setAppearance(this.onDieAppearance());
	}

	protected abstract Shape onDieAppearance();

	@Override
	public void onSceneActivated() {
		this.setX(this.getGame().getDisplayWidth() / 2);
		this.setY(this.getGame().getDisplayHeight() / 2);
	}

}
