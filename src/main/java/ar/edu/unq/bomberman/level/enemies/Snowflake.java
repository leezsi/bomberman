package ar.edu.unq.bomberman.level.enemies;

import ar.edu.unq.americana.appearances.Animation;
import ar.edu.unq.americana.appearances.Shape;
import ar.edu.unq.americana.appearances.utils.SpriteResources;
import ar.edu.unq.bomberman.level.enemies.strategies.IEnemyStrategy;
import ar.edu.unq.bomberman.level.enemies.strategies.AStarLookingForStrategy;

public class Snowflake extends Enemy {

	@Override
	protected IEnemyStrategy movementStrategy() {
		return new AStarLookingForStrategy(CELL_WIDTH, CELL_HEIGHT);
		// return EnemyStrategy.PathfindingLoockFor.initialize(CELL_WIDTH,
		// CELL_HEIGHT);
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
