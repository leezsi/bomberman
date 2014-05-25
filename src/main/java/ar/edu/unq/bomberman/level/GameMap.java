package ar.edu.unq.bomberman.level;

import ar.edu.unq.americana.GameComponent;
import ar.edu.unq.americana.GameScene;
import ar.edu.unq.americana.appearances.utils.SpriteResources;
import ar.edu.unq.americana.components.LifeCounter;
import ar.edu.unq.americana.components.Score;
import ar.edu.unq.americana.configs.Property;
import ar.edu.unq.americana.events.annotations.Events;
import ar.edu.unq.bomberman.level.block.Block;
import ar.edu.unq.bomberman.level.block.BorderBlock;
import ar.edu.unq.bomberman.level.block.BrickPool;
import ar.edu.unq.bomberman.level.bomb.Bomb;
import ar.edu.unq.bomberman.level.items.Item;
import ar.edu.unq.bomberman.player.Player;
import ar.edu.unq.bomberman.player.events.PlayerLossLifeEvent;

public class GameMap extends GameScene {

	@Property("cam.delta")
	private static double CAM_DELTA;
	private final int width;
	private final int height;
	private Player player;
	private LifeCounter<GameMap> lives;
	private Score<GameMap> score;

	public GameMap(final double width, final double height,
			final Score<GameMap> score) {
		this.width = (int) width + 1;
		this.height = (int) height + 1;
		this.addUnbreackableBlocks();
		this.addComponent(this.score = score);
		this.lives = new LifeCounter<GameMap>(3, SpriteResources.sprite(
				"assets/bomberman/bomberman", "bomberman-front1"));
		this.addComponent(this.lives);
	}

	@Override
	public void onSetAsCurrent() {
		super.onSetAsCurrent();
		this.initializeCamera();
	}

	private void initializeCamera() {
		final int x = this.getGame().getDisplayWidth() / 2;
		final int y = this.getGame().getDisplayHeight() / 2;
		final double dx = this.player.getX() - x;
		final double dy = this.player.getY() - y;
		this.setCamX(dx);
		this.setCamY(dy);
	}

	public void setCamY(final double camY) {
		this.fixComponentPositionY(-camY);
	}

	public void setCamX(final double camX) {
		this.fixComponentPositionX(-camX);
	}

	private void fixComponentPositionX(final double delta) {
		for (final GameComponent<?> component : this.getComponents()) {
			component.setX(component.getX() + delta);
		}

	}

	private void fixComponentPositionY(final double delta) {
		for (final GameComponent<?> component : this.getComponents()) {
			component.setY(component.getY() + delta);
		}
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
			final BorderBlock block = BrickPool.<BorderBlock> get("border");
			block.initialize(row, i);
			this.addComponent(block);
		}

	}

	private void addSteelVerticalLine(final int from, final int count,
			final int column) {
		for (int i = from; i < count; i++) {
			final BorderBlock block = BrickPool.<BorderBlock> get("border");
			block.initialize(i, column);
			this.addComponent(block);
		}

	}

	@Events.Fired(PlayerLossLifeEvent.class)
	private void playerLossLife(final PlayerLossLifeEvent event) {
		this.lives.lossLife();
		final double oldX = this.player.getX();
		final double oldY = this.player.getY();
		this.restartCamera(oldX, oldY);
		this.addComponent(this.player.initialize());
		this.initializeCamera();
	}

	private void restartCamera(final double oldX, final double oldY) {
		final double dx = oldX - this.player.getX();
		final double dy = oldY - this.player.getY();
		this.setCamX(dx);
		this.setCamY(dy);
	}

	public void addBlock(final Block block) {
		this.addComponent(block);
	}

	public void addItem(final Item item) {
		this.addComponent(item);
	}

	public void addPlayer(final double row, final double column) {
		this.player = new Player(row, column);
		this.addComponent(this.player);
	}

	public void putBomb(final double x, final double y, final int explosionSize) {
		final Bomb bomb = new Bomb(x, y, explosionSize);
		this.addComponent(bomb);
	}

	public void removeBomb(final Bomb bomb) {
		bomb.destroy();
		this.player.addBombRemaind();

	}

	public Score<GameMap> getScore() {
		return this.score;
	}

	public LifeCounter<GameMap> getLives() {
		return this.lives;
	}

	public void setLives(final LifeCounter<GameMap> lives) {
		this.lives = lives;
	}

}
