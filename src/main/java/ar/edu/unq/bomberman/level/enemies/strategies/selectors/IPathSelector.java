package ar.edu.unq.bomberman.level.enemies.strategies.selectors;

import ar.edu.unq.americana.ia.pathfindier.Path;
import ar.edu.unq.bomberman.level.enemies.Enemy;

public interface IPathSelector {

	Path getDestination(Enemy enemy);

	double getMoveTime();

}
