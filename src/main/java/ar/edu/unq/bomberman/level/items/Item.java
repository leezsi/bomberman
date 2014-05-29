package ar.edu.unq.bomberman.level.items;

import ar.edu.unq.americana.GameComponent;
import ar.edu.unq.americana.appearances.utils.SpriteResources;
import ar.edu.unq.americana.configs.Property;
import ar.edu.unq.americana.events.annotations.Events;
import ar.edu.unq.americana.events.ioc.collision.CollisionStrategy;
import ar.edu.unq.bomberman.COLLITION_GROUPS;
import ar.edu.unq.bomberman.ZINDEXS;
import ar.edu.unq.bomberman.level.GameMap;
import ar.edu.unq.bomberman.level.Positionable;
import ar.edu.unq.bomberman.player.Player;

public abstract class Item extends GameComponent<GameMap> implements
		Positionable {
	@Property("cell.width")
	protected static double CELL_WIDTH;

	@Property("cell.height")
	protected static double CELL_HEIGHT;

	private int row;

	private int column;

	public Item() {
		this.setAppearance(SpriteResources.sprite("assets/block/items/items",
				this.image()));
		this.setZ(ZINDEXS.item);
		this.setCollitionGroup(COLLITION_GROUPS.item);
	}

	public Item initialize(final int row, final int column) {
		this.setX(column * CELL_WIDTH);
		this.setY(row * CELL_HEIGHT);
		this.column = column;
		this.row = row;
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

	@Override
	public int getColumn() {
		return this.column;
	}

	@Override
	public int getRow() {
		return this.row;
	}

	@Override
	public void fixColumn(final int delta) {
		this.column += delta;
	}

	@Override
	public void fixRow(final int delta) {
		this.row += delta;
	}
}
