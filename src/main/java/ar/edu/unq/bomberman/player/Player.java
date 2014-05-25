package ar.edu.unq.bomberman.player;

import ar.edu.unq.americana.DeltaState;
import ar.edu.unq.americana.GameComponent;
import ar.edu.unq.americana.appearances.utils.SpriteResources;
import ar.edu.unq.americana.components.Score;
import ar.edu.unq.americana.configs.Property;
import ar.edu.unq.americana.constants.Key;
import ar.edu.unq.americana.events.annotations.EventType;
import ar.edu.unq.americana.events.annotations.Events;
import ar.edu.unq.americana.events.ioc.collision.CollisionStrategy;
import ar.edu.unq.bomberman.COLITION_GROUPS;
import ar.edu.unq.bomberman.level.GameMap;
import ar.edu.unq.bomberman.level.bomb.PlayerMoveEvent;
import ar.edu.unq.bomberman.level.bomb.explotion.ExplotionPart;
import ar.edu.unq.bomberman.level.items.Item;
import ar.edu.unq.bomberman.player.events.PlayerLossLifeEvent;

public class Player extends GameComponent<GameMap> {

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

	public void setPositionState(final PlayerPositionState positionState) {
		this.positionState = positionState;
	}

	public Player(final double row, final double column) {
		this.setAppearance(SpriteResources.sprite("assets/bomberman/bomberman",
				"bomberman-front1"));
		this.setX(this.initialX = column * CELL_WIDTH);
		this.setY(this.initialY = row * CELL_HEIGHT);
		this.remaindingBombs = 1;
		this.setCollitionGroup(COLITION_GROUPS.player);
	}

	public Player initialize() {
		this.setX(this.initialX);
		this.setY(this.initialY);
		return this;
	}

	@Override
	public void move(final double dx, final double dy) {
		super.move(dx, dy);
		this.updateCamera();
		this.fire(new PlayerMoveEvent());
	}

	public void updateCamera() {
		final int x = this.getGame().getDisplayWidth() / 2;
		final int y = this.getGame().getDisplayHeight() / 2;
		final double dx = this.getX() - x;
		final double dy = this.getY() - y;
		this.getScene().setCamX(dx);
		this.getScene().setCamY(dy);
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
			Score.class, Item.class })
	private void avoidBlockClollision(final GameComponent<?> target) {
		this.alignVisualCloserTo(target);
	}

	@Events.ColitionCheck.ForType(collisionStrategy = CollisionStrategy.PerfectPixel, type = ExplotionPart.class)
	private void explotionClollisionCheck(final ExplotionPart explotion) {
		this.die();
	}

	@Events.Update
	private void update(final double delta) {
		this.positionState.update(delta, this);
	}

	private void die() {
		this.fire(new PlayerLossLifeEvent());
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
}
