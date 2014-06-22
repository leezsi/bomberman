package ar.edu.unq.bomberman.map;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

import javax.imageio.ImageIO;

import ar.edu.unq.americana.exceptions.GameException;
import ar.edu.unq.bomberman.level.GameMap;
import ar.edu.unq.bomberman.map.cells.Cell;

public class GameMapProvider {
	private static GameMapXML xml;
	private static int levelIndex;

	private GameMapProvider() {
	}

	public static void setUp(final int level) {
		try {

			final InputStream systemResource = ClassLoader
					.getSystemResourceAsStream("maps/level" + level + ".data");
			final ObjectInputStream objectStream = new ObjectInputStream(
					systemResource);
			final GameMapXML map = (GameMapXML) objectStream.readObject();
			xml = map;
			levelIndex = level;
			objectStream.close();
			systemResource.close();
		} catch (final Exception e) {
			e.printStackTrace();
			throw new GameException(e);
		}
	}

	public static GameMap getMap() {
		return new GameMap(xml.width, xml.height);
	}

	public static void fill(final GameMap map) {
		final String basePath = "maps/images/map" + levelIndex + "/map-";
		for (final Cell cell : xml.cells) {
			cell.addContent(map);
		}
		try {
			map.setDensityImage(ImageIO.read(ClassLoader
					.getSystemResourceAsStream(basePath + "bricks.png")));
		} catch (final IOException e) {
			throw new GameException(e);
		}

	}
}
