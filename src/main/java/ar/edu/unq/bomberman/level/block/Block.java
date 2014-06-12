package ar.edu.unq.bomberman.level.block;

import ar.edu.unq.americana.appearances.utils.SpriteResources;
import ar.edu.unq.americana.components.events.ScoreUpEvent;
import ar.edu.unq.americana.configs.Property;
import ar.edu.unq.americana.events.annotations.Events;
import ar.edu.unq.americana.events.ioc.collision.CollisionStrategy;
import ar.edu.unq.bomberman.COLLITION_GROUPS;
import ar.edu.unq.bomberman.ZINDEXS;
import ar.edu.unq.bomberman.components.PositionableComponent;
import ar.edu.unq.bomberman.level.bomb.explotion.ExplotionPart;

public class Block extends PositionableComponent {

	@Property("cell.width")
	protected static double CELL_WIDTH;

	@Property("cell.height")
	protected static double CELL_HEIGHT;

	public Block() {
		this.setAppearance(SpriteResources
				.sprite("assets/block/block", "brick"));
		this.setZ(ZINDEXS.block);
		this.setCollitionGroup(COLLITION_GROUPS.block);
	}

	public Block initialize(final int row, final int column) {
		this.setX(column * CELL_WIDTH);
		this.setColumn(column);
		this.setY(row * CELL_HEIGHT);
		this.setRow(row);
		return this;
	}

	@Events.ColitionCheck.ForType(collisionStrategy = CollisionStrategy.PerfectPixel, type = ExplotionPart.class)
	private void collisionExplotion(final ExplotionPart explotionPart) {
		this.setAppearance(SpriteResources.animation("assets/block/block",
				"block-die"));
		BrickPool.instance().add(this);
		this.getScene().removeBlock(this);
		this.fire(new ScoreUpEvent());
	}

	@Override
	public void onAnimationEnd() {
		this.destroy();
	}

	@Events.Update
	public void update(final double delta) {
	}

}
