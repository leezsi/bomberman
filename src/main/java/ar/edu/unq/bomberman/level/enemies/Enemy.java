package ar.edu.unq.bomberman.level.enemies;

import ar.edu.unq.americana.GameComponent;
import ar.edu.unq.americana.appearances.Animation;
import ar.edu.unq.americana.appearances.Shape;
import ar.edu.unq.americana.configs.Property;
import ar.edu.unq.americana.events.annotations.Events;
import ar.edu.unq.americana.events.ioc.collision.CollisionStrategy;
import ar.edu.unq.americana.scenes.components.tilemap.Positionable;
import ar.edu.unq.americana.utils.TrigonometricsAndRandomUtils;
import ar.edu.unq.bomberman.COLLITION_GROUPS;
import ar.edu.unq.bomberman.level.GameMap;
import ar.edu.unq.bomberman.level.bomb.Bomb;
import ar.edu.unq.bomberman.level.bomb.explotion.ExplotionPart;

public abstract class Enemy extends GameComponent<GameMap> implements
		Positionable {
	@Property("cell.width")
	protected static double CELL_WIDTH;

	@Property("cell.height")
	protected static double CELL_HEIGHT;

	private boolean alive;

	private EnemyStrategy strategy;

	private int column;

	private int row;

	public Enemy() {
		this.strategy = this.movementStrategy();
		this.setAppearance(this.aliveAnimation());
		this.setCollitionGroup(COLLITION_GROUPS.enemy);
	}

	protected abstract EnemyStrategy movementStrategy();

	protected abstract Animation aliveAnimation();

	protected void die() {
		this.alive = false;
		this.strategy = EnemyStrategy.NONE;
		this.setAppearance(this.onDieAppearance());
	}

	protected abstract Shape onDieAppearance();

	@Override
	public void onAnimationEnd() {
		if (!this.alive) {
			EnemyPool.add(this);
		}
	}

	@Events.ColitionCheck.ForType(collisionStrategy = CollisionStrategy.FromBounds, type = Bomb.class)
	private void avoidCollitions(final Bomb bomb) {
		final EnemyStrategy tmp = this.strategy;
		this.strategy = EnemyStrategy.NONE;
		TrigonometricsAndRandomUtils.fixPositionTo(this, bomb);
		this.strategy = tmp;
	}

	@Events.ColitionCheck.ForType(collisionStrategy = CollisionStrategy.PerfectPixel, type = ExplotionPart.class)
	private void explotionClollisionCheck(final ExplotionPart explotion) {
		this.die();
	}

	public void initialize(final int row, final int column) {
		this.setX((this.column = column) * CELL_WIDTH);
		this.setY((this.row = row) * CELL_HEIGHT);
		this.alive = true;
	}

	@Events.Update
	protected void update(final double delta) {
		this.strategy.takeStep(delta, this);

	}

	@Override
	public int getColumn() {
		return this.column;
	}

	@Override
	public int getRow() {
		return this.row;
	}

	@Override
	public void fixColumn(final int delta) {
		this.column += delta;
	}

	@Override
	public void fixRow(final int delta) {
		this.row += delta;
	}

	public boolean isAlive() {
		return this.alive;
	}

	public void changeStratety(final EnemyStrategy strategy) {
		this.strategy = strategy;
	}
}
