package ar.edu.unq.bomberman.level.block;

import ar.edu.unq.americana.GameComponent;
import ar.edu.unq.americana.appearances.utils.SpriteResources;
import ar.edu.unq.americana.configs.Property;
import ar.edu.unq.americana.events.annotations.Events;
import ar.edu.unq.americana.scenes.components.tilemap.Positionable;
import ar.edu.unq.bomberman.COLLITION_GROUPS;
import ar.edu.unq.bomberman.ZINDEXS;
import ar.edu.unq.bomberman.level.GameMap;

public abstract class Block extends GameComponent<GameMap> implements
		Positionable {

	@Property("cell.width")
	protected static double CELL_WIDTH;

	@Property("cell.height")
	protected static double CELL_HEIGHT;

	private int column;

	private int row;

	public Block() {
		this.setAppearance(SpriteResources.sprite("assets/block/block",
				this.spriteResource()));
		this.setZ(ZINDEXS.block);
		this.setCollitionGroup(COLLITION_GROUPS.block);
	}

	public Block initialize(final int row, final int column) {
		this.setX(column * CELL_WIDTH);
		this.column = column;
		this.setY(row * CELL_HEIGHT);
		this.row = row;
		return this;
	}

	@Override
	public int getRow() {
		return this.row;
	}

	@Override
	public int getColumn() {
		return this.column;
	}

	@Override
	public void fixColumn(final int delta) {
		this.column += delta;
	}

	@Override
	public void fixRow(final int delta) {
		this.row += this.row;
	}

	protected abstract String spriteResource();

	@Override
	public void onAnimationEnd() {
		this.destroy();
	}

	@Events.Update
	public void update(final double delta) {
	}

}
