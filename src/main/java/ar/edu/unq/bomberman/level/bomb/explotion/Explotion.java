package ar.edu.unq.bomberman.level.bomb.explotion;

import ar.edu.unq.bomberman.level.GameMap;

public class Explotion {

	private final int explosionSize;
	private final int column;
	private final int row;

	public Explotion(final int explosionSize, final int row, final int column) {
		this.row = row;
		this.column = column;
		this.explosionSize = explosionSize;
	}

	public void addComponents(final GameMap scene) {
		scene.addExplotionPart(new ExplotionCenter().initialize(this.row,
				this.column));
		this.fillRight(scene);
		this.fillLeft(scene);
		this.fillDown(scene);
		this.fillUp(scene);
	}

	private void fillUp(final GameMap scene) {
		this.addToUp(this.explosionSize, this.row - 1, this.column, scene);
	}

	private void addToUp(final int size, final int row, final int column,
			final GameMap scene) {
		final int fixedSize = (this.explosionSize - size) + 1;
		if (size > 1) {
			if (!scene.isBlockPresent(row, column)) {
				this.addToUp(size - 1, row - 1, column, scene);
			}
			scene.addExplotionPart(new ExplotionVertically().initialize(row,
					column, fixedSize, "explotion-vertical"));
		} else {
			scene.addExplotionPart(new ExplotionVertically().initialize(row,
					column, fixedSize, "explotion-top"));
		}
	}

	private void fillLeft(final GameMap scene) {
		this.addToLeft(this.explosionSize, this.row, this.column - 1, scene);
	}

	private void addToLeft(final int size, final int row, final int column,
			final GameMap scene) {
		final int fixedSize = (this.explosionSize - size) + 1;
		if (size > 1) {
			if (!scene.isBlockPresent(row, column)) {
				this.addToLeft(size - 1, row, column - 1, scene);
			}
			scene.addExplotionPart(new ExplotionHorizontally().initialize(row,
					column, fixedSize, "explotion-horizontal"));
		} else {
			scene.addExplotionPart(new ExplotionHorizontally().initialize(row,
					column, fixedSize, "explotion-left"));
		}
	}

	private void fillDown(final GameMap scene) {
		this.addToDown(this.explosionSize, this.row + 1, this.column, scene);
	}

	private void addToDown(final int size, final int row, final int column,
			final GameMap scene) {
		final int fixedSize = (this.explosionSize - size) + 1;
		if (size > 1) {
			if (!scene.isBlockPresent(row, column)) {
				this.addToDown(size - 1, row + 1, column, scene);
			}
			scene.addExplotionPart(new ExplotionVertically().initialize(row,
					column, fixedSize, "explotion-vertical"));
		} else {
			scene.addExplotionPart(new ExplotionVertically().initialize(row,
					column, fixedSize, "explotion-down"));
		}
	}

	private void fillRight(final GameMap scene) {
		this.addToRight(this.explosionSize, this.row, this.column + 1, scene);
	}

	private void addToRight(final int size, final int row, final int column,
			final GameMap scene) {
		final int fixedSize = (this.explosionSize - size) + 1;
		if (size > 1) {
			if (!scene.isBlockPresent(row, column)) {
				this.addToRight(size - 1, row, column + 1, scene);
			}
			scene.addExplotionPart(new ExplotionHorizontally().initialize(row,
					column, fixedSize, "explotion-horizontal"));
		} else {
			scene.addExplotionPart(new ExplotionHorizontally().initialize(row,
					column, fixedSize, "explotion-right"));
		}
	}

}
