package ar.edu.unq.bomberman.player;

import ar.edu.unq.americana.DeltaState;
import ar.edu.unq.americana.appearances.utils.SpriteResources;
import ar.edu.unq.americana.configs.Bean;
import ar.edu.unq.americana.configs.Property;
import ar.edu.unq.americana.constants.Key;
import ar.edu.unq.americana.events.annotations.EventType;
import ar.edu.unq.americana.events.annotations.Events;
import ar.edu.unq.americana.events.ioc.collision.CollisionStrategy;
import ar.edu.unq.americana.scenes.camera.CameraResetEvent;
import ar.edu.unq.americana.scenes.camera.CameraUpdateEvent;
import ar.edu.unq.americana.scenes.components.tilemap.PositionableComponent;
import ar.edu.unq.bomberman.COLLITION_GROUPS;
import ar.edu.unq.bomberman.level.GameMap;
import ar.edu.unq.bomberman.level.bomb.PlayerMoveEvent;
import ar.edu.unq.bomberman.level.bomb.explotion.ExplotionPart;
import ar.edu.unq.bomberman.level.enemies.Enemy;

@Bean
public class Player extends PositionableComponent<GameMap> {
	@Property("cell.width")
	protected static double CELL_WIDTH;

	@Property("cell.height")
	protected static double CELL_HEIGHT;

	public PlayerStats getStats() {
		return this.stats;
	}

	public void setStats(final PlayerStats stats) {
		this.stats = stats;
	}

	private PlayerPositionState positionState = PlayerPositionState.STAY
			.initialize(CELL_WIDTH, CELL_HEIGHT);

	private boolean canPutBomb = true;

	private PlayerStats stats;

	public void setPositionState(final PlayerPositionState positionState) {
		this.positionState = positionState;
	}

	public Player(final int row, final int column) {
		this.setAppearance(SpriteResources.sprite("assets/bomberman/bomberman",
				"bomberman-front1"));
		this.resetPosition(row, column);
		this.setCollitionGroup(COLLITION_GROUPS.player);
		this.resetStats();
	}

	public void resetStats() {
		this.stats = new PlayerStats();
	}

	public Player initialize() {
		this.resetPosition();
		this.setX(this.getColumn() * this.getScene().getTileWidth());
		this.setY(this.getRow() * this.getScene().getTileHeight());
		this.fire(new CameraResetEvent(this));
		this.resetStats();
		this.positionState = PlayerPositionState.STAY;
		return this;
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
		this.getScene().cleanExplotions();
		this.die();
	}

	private void die() {
		this.positionState.applyDieingAnimation(this);
	}

	@Events.ColitionCheck.ForType(collisionStrategy = CollisionStrategy.PerfectPixel, type = Enemy.class)
	private void enemyClollisionCheck(final Enemy enemy) {
		if (enemy.isAlive()) {
			this.die();
		}
	}

	@Override
	public void onAnimationEnd() {
		this.positionState.onAnimationEnd(this);
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

}
