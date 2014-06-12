package ar.edu.unq.bomberman.level.enemies;

import ar.edu.unq.americana.appearances.Animation;
import ar.edu.unq.americana.appearances.Shape;
import ar.edu.unq.americana.components.events.ScoreUpEvent;
import ar.edu.unq.americana.configs.Property;
import ar.edu.unq.americana.events.annotations.Events;
import ar.edu.unq.americana.events.ioc.collision.CollisionStrategy;
import ar.edu.unq.americana.utils.TrigonometricsAndRandomUtils;
import ar.edu.unq.bomberman.COLLITION_GROUPS;
import ar.edu.unq.bomberman.components.PositionableComponent;
import ar.edu.unq.bomberman.level.bomb.Bomb;
import ar.edu.unq.bomberman.level.bomb.explotion.ExplotionPart;
import ar.edu.unq.bomberman.level.enemies.strategies.DieEnemyStrategy;
import ar.edu.unq.bomberman.level.enemies.strategies.IEnemyStrategy;
import ar.edu.unq.bomberman.level.enemies.strategies.NoneEnemyStrategy;

public abstract class Enemy extends PositionableComponent {
	@Property("cell.width")
	protected static double CELL_WIDTH;

	@Property("cell.height")
	protected static double CELL_HEIGHT;

	private boolean alive;

	private IEnemyStrategy strategy;

	public Enemy() {
		this.setAppearance(this.aliveAnimation());
		this.setCollitionGroup(COLLITION_GROUPS.enemy);
	}

	protected abstract IEnemyStrategy movementStrategy();

	protected abstract Animation aliveAnimation();

	protected void die() {
		this.alive = false;
		this.strategy = new DieEnemyStrategy();
		this.setAppearance(this.onDieAppearance());
	}

	protected abstract Shape onDieAppearance();

	@Override
	public void onAnimationEnd() {
		if (!this.alive) {
			EnemyPool.add(this);
			this.fire(new ScoreUpEvent());
		}
	}

	@Events.ColitionCheck.ForType(collisionStrategy = CollisionStrategy.FromBounds, type = Bomb.class)
	private void avoidCollitions(final Bomb bomb) {
		final IEnemyStrategy tmp = this.strategy;
		this.strategy = new NoneEnemyStrategy();
		TrigonometricsAndRandomUtils.fixPositionTo(this, bomb);
		this.strategy = tmp;
	}

	@Events.ColitionCheck.ForType(collisionStrategy = CollisionStrategy.PerfectPixel, type = ExplotionPart.class)
	private void explotionClollisionCheck(final ExplotionPart explotion) {
		this.die();
	}

	public void initialize(final int row, final int column) {
		this.setColumn(column);
		this.setRow(row);
		this.setX(column * CELL_WIDTH);
		this.setY(row * CELL_HEIGHT);
		this.alive = true;
		this.strategy = this.movementStrategy();
	}

	@Events.Update
	protected void update(final double delta) {
		this.strategy.takeStep(delta, this);

	}

	public boolean isAlive() {
		return this.alive;
	}

	public void changeStratety(final IEnemyStrategy strategy) {
		this.strategy = strategy;
	}
}
