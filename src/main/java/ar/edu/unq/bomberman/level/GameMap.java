package ar.edu.unq.bomberman.level;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ar.edu.unq.americana.DeltaState;
import ar.edu.unq.americana.GameScene;
import ar.edu.unq.americana.components.LifeCounter;
import ar.edu.unq.americana.components.Score;
import ar.edu.unq.americana.configs.Property;
import ar.edu.unq.americana.constants.Key;
import ar.edu.unq.americana.events.annotations.EventType;
import ar.edu.unq.americana.events.annotations.Events;
import ar.edu.unq.americana.extras.ImageExtras;
import ar.edu.unq.americana.ia.pathfindier.Node;
import ar.edu.unq.americana.ia.pathfindier.TileMap;
import ar.edu.unq.americana.scenes.camera.Camera;
import ar.edu.unq.americana.scenes.camera.ICamera;
import ar.edu.unq.americana.scenes.components.tilemap.BaseTileMap;
import ar.edu.unq.americana.scenes.components.tilemap.ITileMapResourceProvider;
import ar.edu.unq.americana.scenes.components.tilemap.ITileMapScene;
import ar.edu.unq.americana.scenes.components.tilemap.TileMapBackground;
import ar.edu.unq.bomberman.Bomberman;
import ar.edu.unq.bomberman.level.block.Block;
import ar.edu.unq.bomberman.level.block.BrickPool;
import ar.edu.unq.bomberman.level.bomb.Bomb;
import ar.edu.unq.bomberman.level.bomb.explotion.ExplotionPart;
import ar.edu.unq.bomberman.level.enemies.Enemy;
import ar.edu.unq.bomberman.level.enemies.EnemyPool;
import ar.edu.unq.bomberman.level.items.Item;
import ar.edu.unq.bomberman.level.items.ItemPool;
import ar.edu.unq.bomberman.player.Player;
import ar.edu.unq.bomberman.player.events.PlayerLossLifeEvent;
import ar.edu.unq.bomberman.scenes.BombermanPauseScene;

public class GameMap extends GameScene implements ITileMapScene {

	@Property("cam.delta")
	private static double CAM_DELTA;
	private final int width;
	private final int height;
	private Player player;
	private final List<ExplotionPart> explotions = new ArrayList<ExplotionPart>();
	@Property("cell.width")
	protected static double CELL_WIDTH;

	@Property("cell.height")
	protected static double CELL_HEIGHT;

	private final ICamera camera = new Camera();
	private BaseTileMap tileMap;
	private ITileMapResourceProvider tileMapResourceProvider;
	private final Set<Enemy> enemies = new HashSet<Enemy>();
	private BufferedImage densityMap;
	private LifeCounter<?> lifeCounter;

	public GameMap(final double width, final double height) {
		this.width = (int) width;
		this.height = (int) height;
		this.initializeTileMap();
	}

	private void initializeTileMap() {
		this.tileMapResourceProvider = new BombermanTileMapResourceProvider(
				this.height, this.width);
		this.tileMap = new BaseTileMap(this, (int) CELL_WIDTH,
				(int) CELL_HEIGHT, this.tileMapResourceProvider);
	}

	@Events.Fired(PlayerLossLifeEvent.class)
	private void playerLossLife(final PlayerLossLifeEvent event) {
		this.cleanExplotions();
		this.lifeCounter.lossLife();
		this.player.initialize();
		for (final Enemy enemy : this.enemies) {
			enemy.reset();
		}
	}

	public void cleanExplotions() {
		for (final ExplotionPart part : this.explotions) {
			part.destroy();
		}
		this.explotions.clear();
	}

	public void addItem(final Item item) {
		this.addComponent(item);
	}

	public void addPlayer(final int row, final int column) {
		this.player = new Player(row, column);
		this.addComponent(this.player);
		this.player.initialize();
	}

	public void addExplotionPart(final ExplotionPart part) {
		this.explotions.add(part);
		this.addComponent(part);
	}

	public void putBomb(final int row, final int column, final int explosionSize) {
		final Bomb bomb = new Bomb(row, column, explosionSize);
		this.addComponent(bomb);
		ImageExtras.put(this.densityMap, row, column, Color.GREEN);
		Bomberman.BOMB_SET.play();
	}

	public void removeBomb(final Bomb bomb) {
		bomb.destroy();
		this.player.addBombRemaind();
		ImageExtras.clean(this.densityMap, bomb.getRow(), bomb.getColumn());
	}

	public void removeExplotionPart(final ExplotionPart part) {
		this.explotions.remove(part);
		part.destroy();
	}

	@Events.Keyboard(type = EventType.Pressed, key = Key.ESC)
	private void gamePause(final DeltaState state) {
		this.getGame().pause(new BombermanPauseScene());
	}

	public Player getPlayer() {
		return this.player;
	}

	public void changPlayerStats(final Player player) {
		this.player.setStats(player.getStats());
	}

	@SuppressWarnings("unchecked")
	public void addEnemy(final int fixedRow, final int fixedColumn,
			final Class<?> type) {
		final Enemy enemy = EnemyPool.get((Class<? extends Enemy>) type);
		this.addComponent(enemy);
		enemy.initialize(fixedRow, fixedColumn);
		this.enemies.add(enemy);
	}

	public void addSteelBlock(final int row, final int column) {
		this.tileMapResourceProvider.putAt(row, column, 1);
	}

	public void addBlock(final int row, final int column) {
		final Block block = BrickPool.instance().get();
		this.addComponent(block.initialize(row, column));
	}

	public boolean isBlockPresent(final int row, final int column) {
		final Color pixel = ImageExtras.getColor(this.densityMap, row, column);
		final boolean b = (pixel.getAlpha() != 0)
				&& (!pixel.equals(Color.GREEN));
		return b;
	}

	public void addItem(final Class<? extends Item> type, final int fixedRow,
			final int fixedColumn) {
		final Item item = ItemPool.<Item> get(type).initialize(fixedRow,
				fixedColumn);
		this.addComponent(item);
	}

	@Override
	public ICamera getCamera() {
		return this.camera;
	}

	@Override
	public double getWidth() {
		return this.width * CELL_WIDTH;
	}

	@Override
	public double getHeight() {
		return this.height * CELL_HEIGHT;
	}

	@Override
	public int columnsCount() {
		return this.width;
	}

	@Override
	public int rowsCount() {
		return this.height;
	}

	@Override
	public List<Node> adjacents(final Node node) {
		final List<Node> adjacents = new ArrayList<Node>();
		final int r = node.row();
		final int c = node.column();
		if (this.isAccessible(r - 1, c)) {
			adjacents
					.add(new Node(r - 1, c, this.tileMap.getHeristic(r - 1, c)));
		}
		if (this.isAccessible(r + 1, c)) {
			adjacents
					.add(new Node(r + 1, c, this.tileMap.getHeristic(r + 1, c)));
		}
		if (this.isAccessible(r, c - 1)) {
			adjacents
					.add(new Node(r, c - 1, this.tileMap.getHeristic(r, c - 1)));
		}
		if (this.isAccessible(r, c + 1)) {
			adjacents
					.add(new Node(r, c + 1, this.tileMap.getHeristic(r, c + 1)));
		}
		return adjacents;
	}

	@Override
	public TileMap getTileMap() {
		return this.tileMap;
	}

	@Override
	public boolean isAccessible(final int row, final int column) {
		return ImageExtras.isTransparent(this.densityMap, row, column);
	}

	public void removeBlock(final Block block) {
		ImageExtras.clean(this.densityMap, block.getRow(), block.getColumn());
	}

	@Override
	public void addTileBackground(final TileMapBackground tileMapBackGround) {
		this.addComponent(tileMapBackGround);
	}

	public double getTileWidth() {
		return CELL_WIDTH;
	}

	public double getTileHeight() {
		return CELL_HEIGHT;
	}

	public void removeEnemy(final Enemy enemy) {
		this.enemies.remove(enemy);
	}

	public void addEdgeBlock(final int row, final int column) {
		this.tileMapResourceProvider.putAt(row, column, 2);
	}

	public void setDensityImage(final BufferedImage densityMap) {
		this.densityMap = densityMap;
	}

	public boolean isValidExplotionSite(final int row, final int column) {
		return ImageExtras.isTransparent(this.densityMap, row, column)
				|| this.isBreakableBlock(row, column);
	}

	public boolean isBreakableBlock(final int row, final int column) {
		return ImageExtras.getColor(this.densityMap, row, column).equals(
				Color.RED);
	}

	public void addCommonComponents(final Score<GameMap> score,
			final LifeCounter<?> lifeCounter) {
		this.addComponents(score, this.lifeCounter = lifeCounter);
	}

	@Override
	public double getLeft() {
		return super.getLeft() - (CELL_WIDTH / 2);
	}

	@Override
	public double getRight() {
		return super.getRight() + (CELL_WIDTH / 2);
	}

	@Override
	public double getTop() {
		return super.getTop() - (CELL_HEIGHT / 2);
	}

	@Override
	public double getBottom() {
		return super.getBottom() + (CELL_HEIGHT / 2);
	}

}
