package ar.edu.unq.bomberman.player;

import ar.edu.unq.americana.DeltaState;
import ar.edu.unq.americana.appearances.utils.SpriteResources;
import ar.edu.unq.americana.configs.Bean;
import ar.edu.unq.americana.configs.Property;
import ar.edu.unq.americana.constants.Key;
import ar.edu.unq.americana.events.annotations.EventType;
import ar.edu.unq.americana.events.annotations.Events;
import ar.edu.unq.americana.events.ioc.collision.CollisionStrategy;
import ar.edu.unq.americana.scenes.camera.CameraUpdateEvent;
import ar.edu.unq.bomberman.COLLITION_GROUPS;
import ar.edu.unq.bomberman.components.PositionableComponent;
import ar.edu.unq.bomberman.level.bomb.PlayerMoveEvent;
import ar.edu.unq.bomberman.level.bomb.explotion.ExplotionPart;
import ar.edu.unq.bomberman.level.enemies.Enemy;
import ar.edu.unq.bomberman.player.events.PlayerLossLifeEvent;

@Bean
public class Player extends PositionableComponent {

	public PlayerStats getStats() {
		return this.stats;
	}

	public void setStats(final PlayerStats stats) {
		this.stats = stats;
	}

	private PlayerPositionState positionState = PlayerPositionState.STAY
			.initialize(CELL_WIDTH, CELL_HEIGHT);

	private boolean canPutBomb = true;

	private double initialX;

	private double initialY;

	// private int row;
	//
	// private int column;

	private PlayerStats stats;

	public void setPositionState(final PlayerPositionState positionState) {
		this.positionState = positionState;
	}

	public Player(final int row, final int column) {
		this.setAppearance(SpriteResources.sprite("assets/bomberman/bomberman",
				"bomberman-front1"));
		this.setX(this.initialX = column * CELL_WIDTH);
		this.setColumn(column);
		this.setY(this.initialY = row * CELL_HEIGHT);
		this.setRow(row);
		this.setCollitionGroup(COLLITION_GROUPS.player);
		this.resetStats();
	}

	public void resetStats() {
		this.stats = new PlayerStats();
	}

	public Player initialize() {
		this.setX(this.initialX);
		this.setY(this.initialY);
		return this;
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

	@Events.ColitionCheck.ForType(collisionStrategy = CollisionStrategy.PerfectPixel, type = ExplotionPart.class)
	private void explotionClollisionCheck(final ExplotionPart explotion) {
		this.fire(new PlayerLossLifeEvent());
	}

	@Events.ColitionCheck.ForType(collisionStrategy = CollisionStrategy.PerfectPixel, type = Enemy.class)
	private void enemyClollisionCheck(final Enemy enemy) {
		if (enemy.isAlive()) {
			this.positionState.applyDieingAnimation(this);
			// this.fire(new PlayerLossLifeEvent());
		}
	}

	@Events.Update
	private void update(final double delta) {
		this.positionState.update(delta, this);
	}

	@Events.Keyboard(key = Key.SPACE, type = EventType.Pressed)
	private void putBomb(final DeltaState state) {
		if (this.canPutBomb()) {
			this.stats.putBomb();
			this.setCanPutBomb(false);
			this.getScene().putBomb(this.getRow(), this.getColumn(),
					this.stats.getExplotionSize());
		}
	}

	private boolean canPutBomb() {
		return this.canPutBomb && (this.stats.hasAnyBombs());
	}

	public void setCanPutBomb(final boolean canPutBomb) {
		this.canPutBomb = canPutBomb;
	}

	public void addBombRemaind() {
		this.stats.bombExplode();
	}

	public void increseExplosionSize() {
		this.stats.increseExplosionSize();
	}

	@Override
	public String toString() {
		return "Player [row=" + this.getRow() + ", column=" + this.getColumn()
				+ "]";
	}

	@Override
	public void move(final double dx, final double dy) {
		super.move(dx, dy);
		this.fire(new CameraUpdateEvent(dx, dy));
		this.fire(new PlayerMoveEvent());
	}

	public boolean isBombHeart() {
		return this.stats.isBombHeart();
	}

	public void addBombHeart() {
		this.stats.addBombHeart();
	}

	@Property("cell.width")
	protected static double CELL_WIDTH;

	@Property("cell.height")
	protected static double CELL_HEIGHT;
}
