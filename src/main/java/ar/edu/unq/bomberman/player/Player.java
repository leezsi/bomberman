package ar.edu.unq.bomberman.player;

import ar.edu.unq.americana.DeltaState;
import ar.edu.unq.americana.GameComponent;
import ar.edu.unq.americana.appearances.utils.SpriteResources;
import ar.edu.unq.americana.components.Score;
import ar.edu.unq.americana.configs.Bean;
import ar.edu.unq.americana.configs.Property;
import ar.edu.unq.americana.constants.Key;
import ar.edu.unq.americana.events.annotations.EventType;
import ar.edu.unq.americana.events.annotations.Events;
import ar.edu.unq.americana.events.ioc.collision.CollisionStrategy;
import ar.edu.unq.americana.scenes.camera.CameraUpdateEvent;
import ar.edu.unq.bomberman.COLLITION_GROUPS;
import ar.edu.unq.bomberman.level.GameMap;
import ar.edu.unq.bomberman.level.Positionable;
import ar.edu.unq.bomberman.level.bomb.PlayerMoveEvent;
import ar.edu.unq.bomberman.level.bomb.explotion.ExplotionPart;
import ar.edu.unq.bomberman.level.enemies.Enemy;
import ar.edu.unq.bomberman.level.items.Item;
import ar.edu.unq.bomberman.player.events.PlayerLossLifeEvent;

@Bean
public class Player extends GameComponent<GameMap> implements Positionable {

	public void setBombHeart(final boolean bombHeart) {
		this.bombHeart = bombHeart;
	}

	public int getRemaindingBombs() {
		return this.remaindingBombs;
	}

	public void setRemaindingBombs(final int remaindingBombs) {
		this.remaindingBombs = remaindingBombs;
	}

	public int getExplosionSize() {
		return this.explosionSize;
	}

	public void setExplosionSize(final int explosionSize) {
		this.explosionSize = explosionSize;
	}

	@Property("cell.width")
	protected static double CELL_WIDTH;

	@Property("cell.height")
	protected static double CELL_HEIGHT;

	private PlayerPositionState positionState = PlayerPositionState.STAY
			.initialize(CELL_WIDTH, CELL_HEIGHT);

	private int remaindingBombs;

	private int explosionSize = 1;

	private boolean canPutBomb = true;

	private double initialX;

	private double initialY;

	private boolean bombHeart;

	private int row;

	private int column;

	public void setPositionState(final PlayerPositionState positionState) {
		this.positionState = positionState;
	}

	public Player(final int row, final int column) {
		this.setAppearance(SpriteResources.sprite("assets/bomberman/bomberman",
				"bomberman-front1"));
		this.setX(this.initialX = column * CELL_WIDTH);
		this.column = column;
		this.setY(this.initialY = row * CELL_HEIGHT);
		this.row = row;
		this.remaindingBombs = 1;
		this.setCollitionGroup(COLLITION_GROUPS.player);
	}

	public Player initialize() {
		this.setX(this.initialX);
		this.setY(this.initialY);
		this.explosionSize = 1;
		this.remaindingBombs = 1;
		this.bombHeart = false;
		return this;
	}

	@Override
	public void move(final double dx, final double dy) {
		super.move(dx, dy);
		this.fire(new CameraUpdateEvent());
		this.fire(new PlayerMoveEvent());
	}

	@Events.Keyboard(type = EventType.BeingHold, key = Key.Z)
	private void goDie(final DeltaState state) {
		this.setAppearance(SpriteResources.animation(
				"assets/bomberman/bomberman", "bomberman-die"));
	}

	@Events.Keyboard(type = EventType.BeingHold, key = Key.D)
	private void goRight(final DeltaState state) {
		this.positionState.applyRightAnimation(this);
	}

	@Events.Keyboard(type = EventType.BeingHold, key = Key.A)
	private void goLeft(final DeltaState state) {
		this.positionState.applyLeftAnimation(this);
	}

	@Events.Keyboard(type = EventType.BeingHold, key = Key.S)
	private void goDown(final DeltaState state) {
		this.positionState.applyDownAnimation(this);
	}

	@Events.Keyboard(type = EventType.BeingHold, key = Key.W)
	private void goUp(final DeltaState state) {
		this.positionState.applyUpAnimation(this);
	}

	@Events.ColitionCheck.ForGroup(collisionStrategy = CollisionStrategy.PerfectPixel, exclude = {
			Score.class, Item.class, Enemy.class })
	private void avoidBlockClollision(final GameComponent<?> target) {
		this.alignVisualCloserTo(target);
	}

	@Events.ColitionCheck.ForType(collisionStrategy = CollisionStrategy.PerfectPixel, type = ExplotionPart.class)
	private void explotionClollisionCheck(final ExplotionPart explotion) {
		this.fire(new PlayerLossLifeEvent());
	}

	@Events.ColitionCheck.ForType(collisionStrategy = CollisionStrategy.PerfectPixel, type = Enemy.class)
	private void enemyClollisionCheck(final Enemy enemy) {
		if (enemy.isAlive()) {
			this.fire(new PlayerLossLifeEvent());
		}
	}

	@Events.Update
	private void update(final double delta) {
		this.positionState.update(delta, this);
	}

	@Events.Keyboard(key = Key.SPACE, type = EventType.Pressed)
	private void putBomb(final DeltaState state) {
		if (this.canPutBomb()) {
			this.remaindingBombs--;
			this.setCanPutBomb(false);
			this.getScene().putBomb(this.getX(), this.getY(),
					this.explosionSize);
		}
	}

	private boolean canPutBomb() {
		return this.canPutBomb && (this.remaindingBombs > 0);
	}

	public void setCanPutBomb(final boolean canPutBomb) {
		this.canPutBomb = canPutBomb;
	}

	public void addBombRemaind() {
		this.remaindingBombs++;
	}

	public void increseExplosionSize() {
		this.explosionSize++;
	}

	public boolean isBombHeart() {
		return this.bombHeart;
	}

	@Override
	public int getColumn() {
		return this.column;
	}

	@Override
	public void fixColumn(final int delta) {
		this.column += delta;
	}

	@Override
	public int getRow() {
		return this.row;
	}

	@Override
	public void fixRow(final int delta) {
		this.row += delta;
	}
}
