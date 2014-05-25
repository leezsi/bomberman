package ar.edu.unq.bomberman;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;

import javax.imageio.ImageIO;

import ar.edu.unq.bomberman.map.GameMapXML;

/**
 * 
 * 
 * @author leezsi
 */
public class MapGenerator {

	private final BufferedImage baseImage;
	private final BufferedImage bricksImage;
	private final BufferedImage itemsImage;
	private final BufferedImage playersImage;

	public MapGenerator(final String level) throws IOException {
		final String basePath = "maps/images/map" + level + "/map-";
		this.baseImage = this.readImage(basePath, "base");
		this.bricksImage = this.readImage(basePath, "bricks");
		this.itemsImage = this.readImage(basePath, "items");
		this.playersImage = this.readImage(basePath, "players");
	}

	private BufferedImage readImage(final String basePath, final String file)
			throws IOException {
		System.err.println("Reading map image:" + file);
		return ImageIO.read(ClassLoader.getSystemResource(basePath + file
				+ ".png"));
	}

	public static void main(final String[] args) throws Exception {
		System.out.println("Ingrese el numero de mapa");
		System.out.print("mapa nº:");
		final BufferedReader br = new BufferedReader(new InputStreamReader(
				System.in));
		final String level = br.readLine();
		new MapGenerator(level).generateAs(level);
	}

	private void generateAs(final String level) throws Exception {
		final GameMapXML map = new GameMapXML();
		map.width = this.baseImage.getWidth();
		map.height = this.baseImage.getHeight();
		this.generateBaseMap(map.width, map.height, map);
		this.generateBrickMap(map.width, map.height, map);
		this.generateItemMap(map.width, map.height, map);
		this.generatePlayerMap(map.width, map.height, map);
		this.serialize(map, level);
	}

	private void generatePlayerMap(final double width, final double height,
			final GameMapXML map) {
		System.err.println("'player' map process: start");
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				final int pixel = this.playersImage.getRGB(i, j);
				if (((pixel >> 24) & 0xff) != 0) {
					map.addPlayer(j, i, pixel);
				}
			}
		}
		System.err.println("'player' map process: done");
	}

	private void generateItemMap(final double width, final double height,
			final GameMapXML map) {
		System.err.println("'item' map process: start");
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				final int pixel = this.itemsImage.getRGB(i, j);
				if (((pixel >> 24) & 0xff) != 0) {
					map.addItem(j, i, pixel);
				}
			}
		}
		System.err.println("'item' map process: done");
	}

	private void generateBrickMap(final double width, final double height,
			final GameMapXML map) {
		System.err.println("'brick' map process: start");
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				final int pixel = this.bricksImage.getRGB(i, j);
				if (((pixel >> 24) & 0xff) != 0) {
					map.addBlock(j, i);
				}
			}
		}
		System.err.println("'brick' map process: done");
	}

	private void generateBaseMap(final double width, final double height,
			final GameMapXML map) {
		System.err.println("'base' map process: start");
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				final int pixel = this.baseImage.getRGB(i, j);
				if (((pixel >> 24) & 0xff) != 0) {
					map.addSteelBrick(j, i);
				}
			}
		}
		System.err.println("'base' map process: done");
	}

	private void serialize(final GameMapXML map, final String level)
			throws Exception {
		System.err.println("serialing map: start");
		final File output = new File("src/main/resources/maps/level" + level
				+ ".data");
		if (output.exists()) {
			output.delete();
		}
		output.createNewFile();

		final FileOutputStream filestream = new FileOutputStream(output);
		final ObjectOutputStream objectOutputStream = new ObjectOutputStream(
				filestream);
		objectOutputStream.writeObject(map);
		objectOutputStream.close();
		filestream.close();
		System.err.println("serialing map: done");
	}

}
