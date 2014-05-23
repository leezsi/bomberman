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
import ar.edu.unq.bomberman.player.events.PlayerLossLife;

public class Player extends GameComponent<GameMap> {

	@Property("cell.width")
	protected static double CELL_WIDTH;

	@Property("cell.height")
	protected static double CELL_HEIGHT;

	private PlayerPositionState positionState = PlayerPositionState.STAY;

	private int remaindingBombs;

	private final int explosionSize = 1;

	public void setPositionState(final PlayerPositionState positionState) {
		this.positionState = positionState;
	}

	public Player(final double row, final double column) {
		this.setAppearance(SpriteResources.sprite("assets/bomberman/bomberman",
				"bomberman-front1"));
		this.setX(column * CELL_WIDTH);
		this.setY(row * CELL_HEIGHT);
		this.remaindingBombs = 2;
		this.setCollitionGroup(COLITION_GROUPS.player);
	}

	@Override
	public void move(final double dx, final double dy) {
		super.move(dx, dy);
		this.updateCamera();
		this.fire(new PlayerMoveEvent());
	}

	private void updateCamera() {
		final int x = this.getGame().getDisplayWidth() / 2;
		final int y = this.getGame().getDisplayHeight() / 2;
		final double dx = this.getX() - x;
		final double dy = this.getY() - y;
		this.getScene().setCamX(dx);
		this.getScene().setCamY(dy);
	}

	@Events.Keyboard(type = EventType.BeingHold, key = Key.D)
	private void goRight(final DeltaState state) {
		this.positionState.applyRightAnimation(this);
		this.move(CELL_WIDTH * state.getDelta(), 0);
	}

	@Events.Keyboard(type = EventType.Released, key = Key.D)
	private void goRightStop(final DeltaState state) {
		this.positionState.applyRightStop(this);
	}

	@Events.Keyboard(type = EventType.BeingHold, key = Key.Z)
	private void goDie(final DeltaState state) {
		this.setAppearance(SpriteResources.animation(
				"assets/bomberman/bomberman", "bomberman-die"));
	}

	@Events.Keyboard(type = EventType.BeingHold, key = Key.A)
	private void goLeft(final DeltaState state) {
		this.positionState.applyLeftAnimation(this);
		this.move(-CELL_WIDTH * state.getDelta(), 0);
	}

	@Events.Keyboard(type = EventType.Released, key = Key.A)
	private void goLeftStop(final DeltaState state) {
		this.positionState.applyLeftStop(this);
	}

	@Events.Keyboard(type = EventType.BeingHold, key = Key.S)
	private void goDown(final DeltaState state) {
		this.positionState.applyDownAnimation(this);
		this.move(0, CELL_HEIGHT * state.getDelta());
	}

	@Events.Keyboard(type = EventType.Released, key = Key.S)
	private void goDownStop(final DeltaState state) {
		this.positionState.applyDownStop(this);
	}

	@Events.Keyboard(type = EventType.BeingHold, key = Key.W)
	private void goUp(final DeltaState state) {
		this.positionState.applyUpAnimation(this);
		this.move(0, -CELL_HEIGHT * state.getDelta());
	}

	@Events.Keyboard(type = EventType.Released, key = Key.W)
	private void goUpStop(final DeltaState state) {
		this.positionState.applyUpStop(this);
	}

	@Events.ColitionCheck.ForGroup(collisionStrategy = CollisionStrategy.PerfectPixel, exclude = { Score.class })
	private void avoidBlockClollision(final GameComponent<?> target) {
		this.alignVisualCloserTo(target);
	}

	@Events.ColitionCheck.ForGroup(collisionStrategy = CollisionStrategy.PerfectPixel)
	private void explotionClollisionCheck(final ExplotionPart explotion) {
		this.die();
	}

	private void die() {
		// TODO Auto-generated method stub
		this.destroy();
		this.fire(new PlayerLossLife());
	}

	@Events.Keyboard(key = Key.SPACE, type = EventType.Pressed)
	private void putBomb(final DeltaState state) {
		if (this.remaindingBombs > 0) {
			this.remaindingBombs--;
			this.getScene().putBomb(this.getX(), this.getY(),
					this.explosionSize);
		}
	}

	public void addBombRemaind() {
		this.remaindingBombs++;
	}

}
