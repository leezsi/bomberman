package ar.edu.unq.bomberman.level.enemies.strategies.selectors;

import ar.edu.unq.americana.ia.pathfindier.Path;
import ar.edu.unq.americana.ia.pathfindier.astar.AStarPathFinding;
import ar.edu.unq.americana.utils.TrigonometricsAndRandomUtils;
import ar.edu.unq.bomberman.level.GameMap;
import ar.edu.unq.bomberman.level.enemies.Enemy;

public class AStarSelector implements IPathSelector {

	private double time;

	@Override
	public Path getDestination(final Enemy enemy) {
		final GameMap scene = enemy.getScene();
		if (TrigonometricsAndRandomUtils.manhattan(enemy, scene.getPlayer()) <= 5) {
			final Path path = new AStarPathFinding().find(scene.getTileMap(),
					enemy, scene.getPlayer());
			this.time = scene.getTileWidth();
			return path;
		}
		return null;
	}

	@Override
	public double getMoveTime() {
		return this.time;
	}

}
