package ar.edu.unq.bomberman.level.enemies.strategies.base;

import ar.edu.unq.americana.ia.pathfindier.Path;
import ar.edu.unq.bomberman.level.enemies.Enemy;
import ar.edu.unq.bomberman.level.enemies.strategies.IEnemyStrategy;
import ar.edu.unq.bomberman.level.enemies.strategies.selectors.IPathSelector;

public class StepByStepStrategy implements IEnemyStrategy {

	private static final double SPEED = 10;
	private final IPathSelector selector;
	private double remaining;
	private Path destination;

	public StepByStepStrategy(final IPathSelector selector) {
		this.selector = selector;
		this.remaining = 0.0;
	}

	@Override
	public void takeStep(final double delta, final Enemy enemy) {
		if ((this.remaining <= 0) || (this.destination == null)) {
			if (this.destination != null) {
				enemy.fixColumn(this.destination.deltaColumn(enemy));
				enemy.fixRow(this.destination.deltaRow(enemy));
			}
			this.destination = this.selector.getDestination(enemy);
			if (this.destination != null) {
				this.remaining = this.selector.getMoveTime();
			}
		} else {
			final double ds = delta * SPEED;
			this.destination.takeStep(ds, enemy);
			this.remaining -= ds;

		}
	}

	@Override
	public void reset() {
		this.remaining = 0;
	}

}
