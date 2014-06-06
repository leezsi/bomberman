package ar.edu.unq.bomberman.level.block;

import ar.edu.unq.americana.appearances.utils.SpriteResources;
import ar.edu.unq.americana.events.annotations.Events;
import ar.edu.unq.americana.events.ioc.collision.CollisionStrategy;
import ar.edu.unq.bomberman.level.bomb.explotion.ExplotionPart;

public class Brick extends Block {
	@Override
	protected String spriteResource() {
		return "brick";
	}

	@Events.ColitionCheck.ForType(collisionStrategy = CollisionStrategy.PerfectPixel, type = ExplotionPart.class)
	private void collisionExplotion(final ExplotionPart explotionPart) {
		this.die();
	}

	protected void die() {
		this.setAppearance(SpriteResources.animation("assets/block/block",
				"block-die"));
		BrickPool.backToBricks(this);
		this.getScene().removeBlock(this);
	}

}
