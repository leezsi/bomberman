package ar.edu.unq.bomberman.level.enemies;

import ar.edu.unq.americana.appearances.Animation;
import ar.edu.unq.americana.appearances.Shape;
import ar.edu.unq.americana.appearances.utils.SpriteResources;
import ar.edu.unq.bomberman.level.enemies.strategies.RandomStrategy;
import ar.edu.unq.bomberman.level.enemies.strategies.IEnemyStrategy;

public class Coin extends Enemy {

	@Override
	protected IEnemyStrategy movementStrategy() {
		return new RandomStrategy(this.getScene().getTileWidth(), this
				.getScene().getTileHeight());
	}

	@Override
	protected Animation aliveAnimation() {
		return SpriteResources.animation("assets/enemies/enemies", "coin");
	}

	@Override
	protected Shape onDieAppearance() {
		return SpriteResources.animation("assets/enemies/enemies", "coin-die");
	}

}
