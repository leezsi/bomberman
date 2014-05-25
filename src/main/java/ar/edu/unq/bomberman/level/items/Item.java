package ar.edu.unq.bomberman.level.items;

import ar.edu.unq.americana.GameComponent;
import ar.edu.unq.americana.appearances.utils.SpriteResources;
import ar.edu.unq.americana.configs.Property;
import ar.edu.unq.americana.events.annotations.Events;
import ar.edu.unq.americana.events.ioc.collision.CollisionStrategy;
import ar.edu.unq.bomberman.COLITION_GROUPS;
import ar.edu.unq.bomberman.ZINDEXS;
import ar.edu.unq.bomberman.level.GameMap;
import ar.edu.unq.bomberman.player.Player;

public abstract class Item extends GameComponent<GameMap> {
	@Property("cell.width")
	protected static double CELL_WIDTH;

	@Property("cell.height")
	protected static double CELL_HEIGHT;

	public Item() {
		this.setAppearance(SpriteResources.sprite("assets/block/items/items",
				this.image()));
		this.setZ(ZINDEXS.item);
		this.setCollitionGroup(COLITION_GROUPS.item);
	}

	public Item initialize(final double row, final double column) {
		this.setX(column * CELL_WIDTH);
		this.setY(row * CELL_HEIGHT);
		return this;
	}

	@Events.ColitionCheck.ForType(collisionStrategy = CollisionStrategy.PerfectPixel, type = Player.class)
	private void playerCollision(final Player player) {
		this.applyEffect(player);
		this.backToPool();
	}

	protected abstract void applyEffect(Player player);

	protected void backToPool() {
		ItemPool.add(this);
	}

	protected abstract String image();

}
