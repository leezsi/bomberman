package ar.edu.unq.bomberman.level.enemies.strategies;

import ar.edu.unq.americana.ia.pathfindier.Path;
import ar.edu.unq.americana.ia.pathfindier.astar.AStarPathFinding;
import ar.edu.unq.americana.utils.TrigonometricsAndRandomUtils;
import ar.edu.unq.bomberman.level.GameMap;
import ar.edu.unq.bomberman.level.enemies.Enemy;

public class AStarLookingForStrategy extends BaseStrategy {

	public AStarLookingForStrategy(final double tileWidth,
			final double tileHeight) {
		super(tileWidth, tileHeight);
	}

	@Override
	public void takeStep(final double delta, final Enemy enemy) {
		final GameMap scene = enemy.getScene();
		if (TrigonometricsAndRandomUtils.manhattan(enemy, scene.getPlayer()) < 5) {
			final Path _path = new AStarPathFinding().find(scene.getTileMap(),
					enemy, scene.getPlayer());
			if (_path != null) {
				final AStarMovingStrategy strategy = new AStarMovingStrategy(
						this.getTileWidth(), this.getTileHeight(), _path);
				strategy.setRemaining(_path.deltaColumn(enemy) == 0 ? this
						.getTileHeight() : this.getTileWidth());
				enemy.changeStratety(strategy);
			}
		}

	}

}
