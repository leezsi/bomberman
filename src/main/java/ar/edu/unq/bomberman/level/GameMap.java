package ar.edu.unq.bomberman.level;

import ar.edu.unq.americana.GameComponent;
import ar.edu.unq.americana.GameScene;
import ar.edu.unq.americana.components.Score;
import ar.edu.unq.americana.configs.Property;
import ar.edu.unq.bomberman.level.block.Block;
import ar.edu.unq.bomberman.level.block.BorderBlock;
import ar.edu.unq.bomberman.level.bomb.Bomb;
import ar.edu.unq.bomberman.level.items.Item;
import ar.edu.unq.bomberman.player.Player;

public class GameMap extends GameScene {

	@Property("cam.delta")
	private static double CAM_DELTA;
	private final int width;
	private final int height;
	private Player player;

	public GameMap(final double width, final double height,
			final Score<GameMap> score) {
		this.width = (int) width + 1;
		this.height = (int) height + 1;
		this.addUnbreackableBlocks();
		this.addComponent(score);
	}

	@Override
	public void onSetAsCurrent() {
		super.onSetAsCurrent();
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
			this.addComponent(new BorderBlock(row, i));
		}

	}

	private void addSteelVerticalLine(final int from, final int count,
			final int column) {
		for (int i = from; i < count; i++) {
			this.addComponent(new BorderBlock(i, column));
		}

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
		System.err.println(bomb);
		this.addComponent(bomb);
	}

	public void removeBomb(final Bomb bomb) {
		bomb.destroy();
		this.player.addBombRemaind();

	}
}
