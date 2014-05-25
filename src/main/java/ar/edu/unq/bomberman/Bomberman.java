package ar.edu.unq.bomberman;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import ar.edu.unq.americana.Game;
import ar.edu.unq.americana.components.Score;
import ar.edu.unq.americana.configs.Bean;
import ar.edu.unq.americana.events.annotations.Events;
import ar.edu.unq.americana.utils.ResourcesUtils;
import ar.edu.unq.bomberman.events.LevelWinEvent;
import ar.edu.unq.bomberman.level.GameMap;
import ar.edu.unq.bomberman.map.GameMapProvider;

@Bean
public class Bomberman extends Game {

	private static final int WIDTH = 800;

	private static final int HEIGHT = 600;

	private Dimension dimension;

	private Score<GameMap> score;

	private int currentLevel;

	private final int levelCount = 2;

	private GameMap map;

	private static final Font font = ResourcesUtils.getFont(
			"assets/fonts/Bombardier.ttf", Font.TRUETYPE_FONT, Font.BOLD, 50);

	@Override
	protected String[] properties() {
		return new String[] { "bomberman.properties" };
	}

	@Override
	protected void initializeResources() {
		this.score = new Score<GameMap>(10, font, Color.black);
	}

	@Override
	protected void setUpScenes() {
		this.startGame();
	}

	public void startGame() {
		this.currentLevel = 1;
		this.map = GameMapProvider.level(this.currentLevel, this, this.score);
		this.setCurrentScene(this.map);
	}

	@Events.Fired(LevelWinEvent.class)
	public void nextLevel(final LevelWinEvent event) {
		if (this.currentLevel++ <= this.levelCount) {
			this.setCurrentScene(this.map = GameMapProvider.level(
					this.currentLevel, this, this.map));
		}
	}

	@Override
	public Dimension getDisplaySize() {
		if (this.dimension == null) {
			this.dimension = new Dimension(WIDTH, HEIGHT);
		}
		return this.dimension;
	}

	@Override
	public String getTitle() {
		return "Bomberman";
	}

}
