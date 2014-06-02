package ar.edu.unq.bomberman.level.bomb;

import ar.edu.unq.americana.DeltaState;
import ar.edu.unq.americana.GameComponent;
import ar.edu.unq.americana.appearances.utils.SpriteResources;
import ar.edu.unq.americana.configs.Property;
import ar.edu.unq.americana.constants.Key;
import ar.edu.unq.americana.events.annotations.EventType;
import ar.edu.unq.americana.events.annotations.Events;
import ar.edu.unq.bomberman.COLLITION_GROUPS;
import ar.edu.unq.bomberman.ZINDEXS;
import ar.edu.unq.bomberman.level.GameMap;
import ar.edu.unq.bomberman.level.bomb.explotion.Explotion;

public class Bomb extends GameComponent<GameMap> {
	private final int explosionSize;
	private double delay;
	private int column;
	private int row;
	@Property("cell.width")
	protected static double CELL_WIDTH;

	@Property("cell.height")
	protected static double CELL_HEIGHT;
	@Property("bomb.delay")
	private static double BOMB_DELAY;

	public Bomb(final int row, final int column, final int explosionSize) {
		this.explosionSize = explosionSize;
		this.setX((this.column = column) * CELL_WIDTH);
		this.setY((this.row = row) * CELL_HEIGHT);
		this.setZ(ZINDEXS.bomb);
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
		new Explotion(this.explosionSize, this.row, this.column)
				.addComponents(this.getScene());
	}

}
