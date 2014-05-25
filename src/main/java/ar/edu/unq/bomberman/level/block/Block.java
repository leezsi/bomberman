package ar.edu.unq.bomberman.level.block;

import ar.edu.unq.americana.GameComponent;
import ar.edu.unq.americana.appearances.utils.SpriteResources;
import ar.edu.unq.americana.configs.Property;
import ar.edu.unq.americana.events.annotations.Events;
import ar.edu.unq.bomberman.COLITION_GROUPS;
import ar.edu.unq.bomberman.ZINDEXS;
import ar.edu.unq.bomberman.level.GameMap;

public abstract class Block extends GameComponent<GameMap> {

	@Property("cell.width")
	protected static double CELL_WIDTH;

	@Property("cell.height")
	protected static double CELL_HEIGHT;

	public Block() {
		this.setAppearance(SpriteResources.sprite("assets/block/block",
				this.spriteResource()));
		this.setZ(ZINDEXS.block);
		this.setCollitionGroup(COLITION_GROUPS.block);
	}

	public void initialize(final double row, final double column) {
		this.setX(column * CELL_WIDTH);
		this.setY(row * CELL_HEIGHT);
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
