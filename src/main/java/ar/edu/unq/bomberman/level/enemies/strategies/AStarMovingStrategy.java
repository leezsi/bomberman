package ar.edu.unq.bomberman.level.enemies.strategies;

import ar.edu.unq.americana.ia.pathfindier.Path;
import ar.edu.unq.bomberman.level.enemies.Enemy;

public class AStarMovingStrategy extends BaseStrategy {

	private final Path path;

	public AStarMovingStrategy(final double tileWidth, final double tileHeight,
			final Path path) {
		super(tileWidth, tileHeight);
		this.path = path;
	}

	protected Path getPath() {
		return this.path;
	}

	@Override
	public void takeStep(final double delta, final Enemy enemy) {
		if (this.getRemaining() > 0) {
			final double ds = delta * SPEED;
			this.updateRemaining(-ds);
			this.path.takeStep(ds, enemy);
		} else {
			enemy.fixColumn(this.path.deltaColumn(enemy));
			enemy.fixRow(this.path.deltaRow(enemy));
			enemy.changeStratety(new AStarLookingForStrategy(this
					.getTileWidth(), this.getTileHeight()));

		}
	}

}
