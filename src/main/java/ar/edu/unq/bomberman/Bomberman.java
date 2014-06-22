package ar.edu.unq.bomberman;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import ar.edu.unq.americana.Game;
import ar.edu.unq.americana.appearances.utils.SpriteResources;
import ar.edu.unq.americana.components.LifeCounter;
import ar.edu.unq.americana.components.Score;
import ar.edu.unq.americana.configs.Property;
import ar.edu.unq.americana.events.annotations.Events;
import ar.edu.unq.americana.utils.ResourcesUtils;
import ar.edu.unq.bomberman.events.LevelWinEvent;
import ar.edu.unq.bomberman.level.GameMap;
import ar.edu.unq.bomberman.map.GameMapProvider;
import ar.edu.unq.bomberman.player.PlayerStats;

public class Bomberman extends Game {

	@Property("game.width")
	private static int WIDTH;
	@Property("game.height")
	private static int HEIGHT;

	private Dimension dimension;

	private Score<GameMap> score;
	private LifeCounter<?> lifeCounter;

	private int currentLevel;

	private final int levelCount = 2;

	private GameMap map;
	public static final Font font = ResourcesUtils.getFont(
			"assets/fonts/Bombardier.ttf", Font.TRUETYPE_FONT, Font.BOLD, 50);

	@Override
	protected String[] properties() {
		return new String[] { "bomberman.properties" };
	}

	@Override
	protected void initializeResources() {
	}

	@Override
	protected void setUpScenes() {
		this.startGame();
	}

	@Override
	public void startGame() {
		this.currentLevel = 1;
		this.score = new Score<GameMap>(10, font, Color.black);
		this.lifeCounter = new LifeCounter<GameMap>(3, SpriteResources.sprite(
				"assets/bomberman/bomberman", "bomberman-front1"));
		this.setUpMap(1);
	}

	private void setUpMap(final int level) {
		GameMapProvider.setUp(level);
		this.map = GameMapProvider.getMap();
		this.map.addCommonComponents(this.score, this.lifeCounter);
		this.setCurrentScene(this.map);
		GameMapProvider.fill(this.map);
	}

	@Events.Fired(LevelWinEvent.class)
	public void nextLevel(final LevelWinEvent event) {
		if (this.currentLevel++ <= this.levelCount) {
			final PlayerStats stats = this.map.getPlayer().getStats();
			this.setUpMap(this.currentLevel);
			this.map.getPlayer().setStats(stats);
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
