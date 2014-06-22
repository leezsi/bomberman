package ar.edu.unq.bomberman.level.enemies;

import ar.edu.unq.americana.appearances.Animation;
import ar.edu.unq.americana.appearances.Shape;
import ar.edu.unq.americana.appearances.utils.SpriteResources;
import ar.edu.unq.bomberman.level.enemies.strategies.IEnemyStrategy;
import ar.edu.unq.bomberman.level.enemies.strategies.base.StepByStepStrategy;
import ar.edu.unq.bomberman.level.enemies.strategies.selectors.AStarSelector;

public class Snowflake extends Enemy {

	@Override
	protected IEnemyStrategy movementStrategy() {
		return new StepByStepStrategy(new AStarSelector());
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
