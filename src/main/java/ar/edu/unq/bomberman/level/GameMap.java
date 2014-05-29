package ar.edu.unq.bomberman.level;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ar.edu.unq.americana.DeltaState;
import ar.edu.unq.americana.GameComponent;
import ar.edu.unq.americana.appearances.utils.SpriteResources;
import ar.edu.unq.americana.components.LifeCounter;
import ar.edu.unq.americana.components.Score;
import ar.edu.unq.americana.configs.Property;
import ar.edu.unq.americana.constants.Key;
import ar.edu.unq.americana.events.annotations.EventType;
import ar.edu.unq.americana.events.annotations.Events;
import ar.edu.unq.americana.scenes.camera.CameraGameScene;
import ar.edu.unq.bomberman.level.block.BorderBlock;
import ar.edu.unq.bomberman.level.block.Brick;
import ar.edu.unq.bomberman.level.block.BrickPool;
import ar.edu.unq.bomberman.level.block.UnbreakableBlock;
import ar.edu.unq.bomberman.level.bomb.Bomb;
import ar.edu.unq.bomberman.level.bomb.explotion.ExplotionPart;
import ar.edu.unq.bomberman.level.enemies.Enemy;
import ar.edu.unq.bomberman.level.enemies.EnemyPool;
import ar.edu.unq.bomberman.level.items.Item;
import ar.edu.unq.bomberman.level.items.ItemPool;
import ar.edu.unq.bomberman.pause.BombermanPauseScene;
import ar.edu.unq.bomberman.player.Player;
import ar.edu.unq.bomberman.player.events.PlayerLossLifeEvent;

public class GameMap extends CameraGameScene {

	@Property("cam.delta")
	private static double CAM_DELTA;
	private final int width;
	private final int height;
	private Player player;
	private final List<ExplotionPart> explotions = new ArrayList<ExplotionPart>();
	private final Set<GameComponent<?>> elemements[][];

	private final boolean[][] blocksExistence;
	private final boolean[][] steelBlocksExistence;

	@SuppressWarnings("unchecked")
	public GameMap(final double width, final double height,
			final Score<?> score, final LifeCounter<?> lifeCounter) {
		super(score, lifeCounter);
		this.width = (int) width + 1;
		this.height = (int) height + 1;
		this.elemements = new HashSet[this.height][this.width];
		this.blocksExistence = new boolean[this.height][this.width];
		this.steelBlocksExistence = new boolean[this.height][this.width];
		this.addUnbreackableBlocks();
	}

	public void addElement(final Positionable component) {
		final GameComponent<?> casted = (GameComponent<?>) component;
		this.addComponent(casted);
		Set<GameComponent<?>> set;
		if ((set = this.elemements[component.getRow()][component.getColumn()]) == null) {
			set = this.elemements[component.getRow()][component.getColumn()] = new HashSet<GameComponent<?>>();
		}
		set.add(casted);
	}

	@Override
	public void onSetAsCurrent() {
		super.onSetAsCurrent();
		this.initializeCamera(this.player);
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
			final BorderBlock block = BrickPool
					.<BorderBlock> get(BorderBlock.class);
			block.initialize(row, i);
			this.addComponent(block);
		}

	}

	private void addSteelVerticalLine(final int from, final int count,
			final int column) {
		for (int i = from; i < count; i++) {
			final BorderBlock block = BrickPool
					.<BorderBlock> get(BorderBlock.class);
			block.initialize(i, column);
			this.addComponent(block);
		}

	}

	@Events.Fired(PlayerLossLifeEvent.class)
	private void playerLossLife(final PlayerLossLifeEvent event) {
		this.getLifeCounter().lossLife();
		this.player.setAppearance(SpriteResources.animation(
				"assets/bomberman/bomberman", "bomberman-die"));
		this.resetCamera();
		this.cleanExplotions();
		this.cameraFocusOn(this.player.initialize());
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

	public void addPlayer(final double row, final double column) {
		this.player = new Player(row, column);
		this.addComponent(this.player);
	}

	public void addExplotionPart(final ExplotionPart part) {
		this.explotions.add(part);
		this.addComponent(part);
	}

	public void putBomb(final double x, final double y, final int explosionSize) {
		final Bomb bomb = new Bomb(x, y, explosionSize);
		this.addComponent(bomb);
	}

	public void removeBomb(final Bomb bomb) {
		bomb.destroy();
		this.player.addBombRemaind();

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

	public void setPlayer(final Player player) {
		this.player.setExplosionSize(player.getExplosionSize());
		this.player.setRemaindingBombs(player.getRemaindingBombs());
	}

	@SuppressWarnings("unchecked")
	public void addEnemy(final double fixedRow, final double fixedColumn,
			final Class<?> type) {
		final Enemy enemy = EnemyPool.get((Class<? extends Enemy>) type);
		enemy.initialize(fixedRow, fixedColumn);
		this.addComponent(enemy);
	}

	public void addSteelBlock(final double row, final double column) {
		final UnbreakableBlock block = BrickPool
				.<UnbreakableBlock> get(UnbreakableBlock.class);
		this.addComponent(block.initialize(row, column));
		this.steelBlocksExistence[(int) row][(int) column] = true;
	}

	public void addBlock(final double row, final double column) {
		final Brick block = BrickPool.<Brick> get(Brick.class);
		this.addComponent(block.initialize(row, column));
		this.blocksExistence[(int) row][(int) column] = true;
	}

	public boolean isBlockPresent(final int row, final int column) {
		if ((row > 0) && (row < this.blocksExistence.length) && (column > 0)
				&& (column < this.blocksExistence[0].length)) {
			return this.steelBlocksExistence[row][column]
					|| this.blocksExistence[row][column];
		}
		return true;
	}

	public boolean isSteelBlockPresent(final int row, final int column) {
		if ((row > 0) && (row < this.blocksExistence.length) && (column > 0)
				&& (column < this.blocksExistence[0].length)) {
			return this.steelBlocksExistence[row][column];
		}
		return true;
	}

	public void addItem(final Class<? extends Item> type, final int fixedRow,
			final int fixedColumn) {
		final Item item = ItemPool.<Item> get(type).initialize(fixedRow,
				fixedColumn);
		this.addElement(item);
	}
}
