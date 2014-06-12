package ar.edu.unq.bomberman.level.enemies.strategies;

import ar.edu.unq.americana.utils.TrigonometricsAndRandomUtils;
import ar.edu.unq.bomberman.level.enemies.Enemy;

public class RandomStrategy extends BaseStrategy {

	private int deltaY;
	private int deltaX;

	public RandomStrategy(final double tileWidth, final double tileHeight) {
		super(tileWidth, tileHeight);
	}

	@Override
	public void takeStep(final double delta, final Enemy enemy) {
		if (this.getRemaining() <= 0) {
			final int column = enemy.getColumn();
			final int row = enemy.getRow();
			int dx = column;
			int dy = row;
			do {
				dx = column;
				dy = row;
				if (TrigonometricsAndRandomUtils.nextBoolean()) {
					dx += TrigonometricsAndRandomUtils.vectoreanBoolean();
					dy = row;
				} else {
					dy += TrigonometricsAndRandomUtils.vectoreanBoolean();
					dx = column;
				}
			} while (enemy.getScene().isBlockPresent(dy, dx));
			if (dx == column) {
				this.deltaX = 0;
				this.deltaY = dy - row;
				enemy.fixRow(this.deltaY);
				this.setRemaining(this.getTileHeight());
			} else {
				this.deltaY = 0;
				this.deltaX = dx - column;
				this.setRemaining(this.getTileWidth());
				enemy.fixColumn(this.deltaX);
			}
		} else {
			final double dxy = delta * SPEED;
			this.updateRemaining(-dxy);
			enemy.move(this.deltaX * dxy, this.deltaY * dxy);
		}
	}

}
