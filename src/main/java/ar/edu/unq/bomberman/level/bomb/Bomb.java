package ar.edu.unq.bomberman.level.bomb;

import ar.edu.unq.americana.DeltaState;
import ar.edu.unq.americana.GameComponent;
import ar.edu.unq.americana.appearances.utils.SpriteResources;
import ar.edu.unq.americana.configs.Property;
import ar.edu.unq.americana.constants.Key;
import ar.edu.unq.americana.events.annotations.EventType;
import ar.edu.unq.americana.events.annotations.Events;
import ar.edu.unq.bomberman.COLLITION_GROUPS;
import ar.edu.unq.bomberman.level.GameMap;
import ar.edu.unq.bomberman.level.bomb.explotion.Explotion;

public class Bomb extends GameComponent<GameMap> {
	private final int explosionSize;
	private double delay;
	@Property("bomb.delay")
	private static double BOMB_DELAY;

	public Bomb(final double x, final double y, final int explosionSize) {
		this.explosionSize = explosionSize;
		this.setX(x);
		this.setY(y);
		this.setCollitionGroup(COLLITION_GROUPS.player);
		this.setAppearance(SpriteResources
				.animation("assets/bomb/bomb", "bomb"));
		this.delay = BOMB_DELAY;
	}

	@Events.Update
	private void update(final double delta) {
		this.delay -= (delta * 100);
		if (!this.getScene().getPlayer().isBombHeart() && (this.delay <= 0)) {
			this.expode();
		}
	}

	@Events.Fired(PlayerMoveEvent.class)
	private void changeGroupCollision(final PlayerMoveEvent event) {
		this.setCollitionGroup(COLLITION_GROUPS.bomb);
	}

	@Events.Keyboard(key = Key.P, type = EventType.Pressed)
	private void die(final DeltaState state) {
		if (this.getScene().getPlayer().isBombHeart()) {
			this.expode();
		}
	}

	private void expode() {
		this.getScene().removeBomb(this);
		new Explotion(this.explosionSize, this.getX(), this.getY())
				.addComponents(this.getScene());
	}

	@Override
	public void onSceneActivated() {
		this.setX(this.getGame().getDisplayWidth() / 2);
		this.setY(this.getGame().getDisplayHeight() / 2);
	}
}
