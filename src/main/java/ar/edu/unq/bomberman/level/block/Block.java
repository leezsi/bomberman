package ar.edu.unq.bomberman.level.block;

import ar.edu.unq.americana.GameComponent;
import ar.edu.unq.americana.appearances.utils.SpriteResources;
import ar.edu.unq.americana.configs.Property;
import ar.edu.unq.americana.events.annotations.Events;
import ar.edu.unq.bomberman.COLITION_GROUPS;
import ar.edu.unq.bomberman.ZINDEXS;
import ar.edu.unq.bomberman.level.GameMap;

public class Block extends GameComponent<GameMap> {

	@Property("cell.width")
	protected static double CELL_WIDTH;

	@Property("cell.height")
	protected static double CELL_HEIGHT;

	public Block(final double row, final double column) {
		this.setAppearance(SpriteResources.sprite("assets/block/block",
				this.spriteResource()));
		this.setX(column * CELL_WIDTH);
		this.setY(row * CELL_HEIGHT);
		this.setZ(ZINDEXS.block);
		this.setCollitionGroup(COLITION_GROUPS.block);
	}

	protected String spriteResource() {
		return "brick";
	}

	protected void die() {
		this.setAppearance(SpriteResources.animation("assets/block/block",
				"block-die"));
	}

	@Override
	public void onAnimationEnd() {
		this.destroy();
	}

	@Events.Update
	public void update(final double delta) {
	}

}
