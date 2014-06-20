package ar.edu.unq.bomberman.map;

import java.io.InputStream;
import java.io.ObjectInputStream;

import ar.edu.unq.americana.components.LifeCounter;
import ar.edu.unq.americana.components.Score;
import ar.edu.unq.americana.exceptions.GameException;
import ar.edu.unq.bomberman.Bomberman;
import ar.edu.unq.bomberman.level.GameMap;
import ar.edu.unq.bomberman.map.cells.Cell;

public class GameMapProvider {
	private GameMapProvider() {
	}

	public static GameMap level(final int level, final Bomberman bomberman,
			final Score<?> score, final LifeCounter<?> lifeCounter) {
		try {

			final InputStream systemResource = ClassLoader
					.getSystemResourceAsStream("maps/level" + level + ".data");
			final ObjectInputStream objectStream = new ObjectInputStream(
					systemResource);
			final GameMapXML map = (GameMapXML) objectStream.readObject();
			objectStream.close();
			systemResource.close();
			return new GameMapProvider().fill(bomberman, map, score,
					lifeCounter);
		} catch (final Exception e) {
			throw new GameException(e);
		}

	}

	private GameMap fill(final Bomberman bomberman, final GameMapXML xml,
			final Score<?> score, final LifeCounter<?> lifeCounter) {
		final GameMap map = new GameMap(xml.width, xml.height, score,
				lifeCounter);
		for (final Cell cell : xml.cells) {
			cell.addContent(map);
		}
		return map;
	}
}
