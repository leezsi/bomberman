package ar.edu.unq.bomberman.level.enemies.strategies.selectors;

import java.util.ArrayList;
import java.util.List;

import ar.edu.unq.americana.ia.pathfindier.Path;
import ar.edu.unq.americana.utils.TrigonometricsAndRandomUtils;
import ar.edu.unq.bomberman.level.GameMap;
import ar.edu.unq.bomberman.level.enemies.Enemy;

public class RandomSelector implements IPathSelector {

	private double time;

	@Override
	public Path getDestination(final Enemy enemy) {
		final GameMap scene = enemy.getScene();
		final List<Path> adjacents = this.adjacents(enemy);
		final Path path = TrigonometricsAndRandomUtils.oneOf(adjacents);
		this.time = scene.getTileWidth();
		return path;

	}

	private List<Path> adjacents(final Enemy enemy) {
		final GameMap scene = enemy.getScene();
		final List<Path> adjacents = new ArrayList<Path>();
		final int r = enemy.getRow();
		final int c = enemy.getColumn();
		if (scene.isAccessible(r - 1, c)) {
			adjacents.add(new EPath(r - 1, c));
		}
		if (scene.isAccessible(r + 1, c)) {
			adjacents.add(new EPath(r + 1, c));
		}
		if (scene.isAccessible(r, c - 1)) {
			adjacents.add(new EPath(r, c - 1));
		}
		if (scene.isAccessible(r, c + 1)) {
			adjacents.add(new EPath(r, c + 1));
		}
		return adjacents;
	}

	@Override
	public double getMoveTime() {
		return this.time;
	}

}
