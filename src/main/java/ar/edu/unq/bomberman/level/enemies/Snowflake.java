package ar.edu.unq.bomberman.level.enemies;

import ar.edu.unq.americana.appearances.Animation;
import ar.edu.unq.americana.appearances.Shape;
import ar.edu.unq.americana.appearances.utils.SpriteResources;

public class Snowflake extends Enemy {

	@Override
	protected EnemyStrategy movementStrategy() {
		return EnemyStrategy.PathfindingLoockFor.initialize(CELL_WIDTH,
				CELL_HEIGHT);
	}

	@Override
	protected Animation aliveAnimation() {
		return SpriteResources.animation("assets/enemies/enemies", "snowflake");
	}

	@Override
	protected Shape onDieAppearance() {
		return SpriteResources.animation("assets/enemies/enemies",
				"snowflake-die");
	}

}
