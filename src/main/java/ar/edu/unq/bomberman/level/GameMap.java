package ar.edu.unq.bomberman.level;

import java.util.ArrayList;
import java.util.List;

import ar.edu.unq.americana.DeltaState;
import ar.edu.unq.americana.components.LifeCounter;
import ar.edu.unq.americana.components.Score;
import ar.edu.unq.americana.configs.Property;
import ar.edu.unq.americana.constants.Key;
import ar.edu.unq.americana.events.annotations.EventType;
import ar.edu.unq.americana.events.annotations.Events;
import ar.edu.unq.americana.ia.pathfindier.Node;
import ar.edu.unq.americana.ia.pathfindier.TileMap;
import ar.edu.unq.americana.scenes.camera.Camera;
import ar.edu.unq.americana.scenes.camera.ICamera;
import ar.edu.unq.americana.scenes.components.tilemap.BaseTileMap;
import ar.edu.unq.americana.scenes.components.tilemap.ITileMapResourceProvider;
import ar.edu.unq.americana.scenes.components.tilemap.ITileMapScene;
import ar.edu.unq.americana.scenes.components.tilemap.TileMapBackground;
import ar.edu.unq.americana.scenes.normal.DefaultScene;
import ar.edu.unq.bomberman.level.block.Block;
import ar.edu.unq.bomberman.level.block.BrickPool;
import ar.edu.unq.bomberman.level.bomb.Bomb;
import ar.edu.unq.bomberman.level.bomb.explotion.ExplotionPart;
import ar.edu.unq.bomberman.level.enemies.Enemy;
import ar.edu.unq.bomberman.level.enemies.EnemyPool;
import ar.edu.unq.bomberman.level.items.Item;
import ar.edu.unq.bomberman.level.items.ItemPool;
import ar.edu.unq.bomberman.pause.BombermanPauseScene;
import ar.edu.unq.bomberman.player.Player;
import ar.edu.unq.bomberman.player.events.PlayerLossLifeEvent;

public class GameMap extends DefaultScene implements ITileMapScene {

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
	private boolean[][] accessibleCells;
	private boolean[][] blocksExistence;

	public GameMap(final double width, final double height,
			final Score<?> score, final LifeCounter<?> lifeCounter) {
		super(score, lifeCounter);
		this.width = (int) width + 1;
		this.height = (int) height + 1;
		this.initializeTileMap();
		this.initializeMatrixs();
		this.addUnbreackableBlocks();
	}

	private void initializeMatrixs() {
		this.accessibleCells = new boolean[this.height][this.width];
		this.blocksExistence = new boolean[this.height][this.width];
		for (int c = 0; c < this.width; c++) {
			for (int r = 0; r < this.height; r++) {
				this.accessibleCells[r][c] = true;
				this.blocksExistence[r][c] = false;
			}
		}
	}

	private void initializeTileMap() {
		this.tileMapResourceProvider = new BombermanTileMapResourceProvider(
				this.height + 1, this.width + 1);
		this.tileMap = new BaseTileMap(this, (int) CELL_WIDTH,
				(int) CELL_HEIGHT, this.tileMapResourceProvider);
	}

	private void addUnbreackableBlocks() {
		this.addSteelHorizontalLine(0, this.width, 0);
		this.addSteelHorizontalLine(1, this.width + 1, this.height);
		this.addSteelVerticalLine(1, this.height + 1, 0);
		this.addSteelVerticalLine(0, this.height, this.width);
	}

	private void addSteelHorizontalLine(final int from, final int count,
			final int row) {
		for (int i = from; i < count; i++) {
			this.tileMapResourceProvider.putAt(row, i, 2);
		}

	}

	private void addSteelVerticalLine(final int from, final int count,
			final int column) {
		for (int i = from; i < count; i++) {
			this.tileMapResourceProvider.putAt(i, column, 2);
		}

	}

	@Events.Fired(PlayerLossLifeEvent.class)
	private void playerLossLife(final PlayerLossLifeEvent event) {
		this.cleanExplotions();
		this.getLifeCounter().lossLife();
		this.player.initialize();
	}

	private void cleanExplotions() {
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
	}

	public void addExplotionPart(final ExplotionPart part) {
		this.explotions.add(part);
		this.addComponent(part);
	}

	public void putBomb(final int row, final int column, final int explosionSize) {
		final Bomb bomb = new Bomb(row, column, explosionSize);
		this.addComponent(bomb);
		this.accessibleCells[row][column] = false;
	}

	public void removeBomb(final Bomb bomb) {
		bomb.destroy();
		this.player.addBombRemaind();
		this.accessibleCells[bomb.getRow()][bomb.getColumn()] = false;

	}

	public void removeExplotionPart(final ExplotionPart part) {
		this.explotions.remove(part);
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
		enemy.initialize(fixedRow, fixedColumn);
		this.addComponent(enemy);
	}

	public void addSteelBlock(final int row, final int column) {
		this.tileMapResourceProvider.putAt(row, column, 1);
		this.accessibleCells[row][column] = false;
		this.blocksExistence[row][column] = true;
	}

	public void addBlock(final int row, final int column) {
		final Block block = BrickPool.instance().get();
		this.addComponent(block.initialize(row, column));
		this.accessibleCells[row][column] = false;
		this.blocksExistence[row][column] = true;
	}

	public boolean isBlockPresent(final int row, final int column) {
		if ((row < 1) || (column < 1) || (row >= this.height)
				|| (column >= this.width)) {
			return true;
		}
		return this.blocksExistence[row][column];
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
		if ((row < 1) || (column < 1) || (row >= this.height)
				|| (column >= this.width)) {
			return false;
		}
		return this.accessibleCells[row][column];
	}

	public void removeBlock(final Block block) {
		this.blocksExistence[block.getRow()][block.getColumn()] = false;
	}

	@Override
	public void addTileBackground(final TileMapBackground tileMapBackGround) {
		this.addComponent(tileMapBackGround);
	}

}
