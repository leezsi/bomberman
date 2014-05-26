package ar.edu.unq.bomberman.level.enemies;

import ar.edu.unq.americana.appearances.Animation;
import ar.edu.unq.americana.appearances.Shape;
import ar.edu.unq.americana.appearances.utils.SpriteResources;

public class Balloon extends Enemy {
	@Override
	protected Animation aliveAnimation() {
		return SpriteResources.animation("assets/enemies/enemies", "balloon");
	}

	@Override
	protected Shape onDieAppearance() {
		return SpriteResources.animation("assets/enemies/enemies",
				"balloon-die");
	}

	@Override
	protected EnemyStrategy movementStrategy() {
		return EnemyStrategy.Balloon.initialize(CELL_WIDTH, CELL_HEIGHT);
	}
}
