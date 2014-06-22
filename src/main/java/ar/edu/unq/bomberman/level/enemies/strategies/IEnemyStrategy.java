package ar.edu.unq.bomberman.level.enemies.strategies;

import ar.edu.unq.bomberman.level.enemies.Enemy;

public interface IEnemyStrategy {

	public void takeStep(final double delta, final Enemy enemy);

	public void reset();

}
