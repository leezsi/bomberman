package ar.edu.unq.bomberman.level.enemies.strategies;

import ar.edu.unq.americana.configs.Configs;
import ar.edu.unq.americana.configs.Property;

public abstract class BaseStrategy implements IEnemyStrategy {
	private final double tileHeight;
	private final double tileWidth;
	private double remaining;
	@Property("enemy.speed")
	protected static double SPEED;

	static {
		Configs.injectConfigs(BaseStrategy.class);
	}

	public BaseStrategy(final double tileWidth, final double tileHeight) {
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		this.remaining = 0.0;
	}

	protected double getRemaining() {
		return this.remaining;
	}

	protected void setRemaining(final double remaining) {
		this.remaining = remaining;
	}

	protected double getTileHeight() {
		return this.tileHeight;
	}

	protected double getTileWidth() {
		return this.tileWidth;
	}

	protected void updateRemaining(final double d) {
		this.remaining += d;

	}
}
