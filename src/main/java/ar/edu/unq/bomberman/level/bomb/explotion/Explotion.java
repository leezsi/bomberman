package ar.edu.unq.bomberman.level.bomb.explotion;

import ar.edu.unq.bomberman.level.GameMap;

public class Explotion {

	private final int explosionSize;
	private final int column;
	private final int row;

	enum Function {
		ADD {
			@Override
			public Function next() {
				final Function ret = ADD;
				ret.value(this.value() + 1);
				return ret;
			}
		},
		SUB {
			@Override
			public Function next() {
				final Function ret = SUB;
				ret.value(this.value() - 1);
				return ret;
			}
		},
		EQUAL {
			@Override
			public Function next() {
				return this;
			}
		};

		private int value;

		public Function value(final int value) {
			this.value = value;
			return this;
		}

		public int value() {
			return this.value;
		}

		public abstract Function next();
	}

	public Explotion(final int explosionSize, final int row, final int column) {
		this.row = row;
		this.column = column;
		this.explosionSize = explosionSize;
	}

	public void addComponents(final GameMap scene) {
		scene.addExplotionPart(new ExplotionPart().initialize(this.row,
				this.column, 0, "explotion-center"));
		this.fillRight(scene);
		this.fillLeft(scene);
		this.fillDown(scene);
		this.fillUp(scene);
	}

	private void fillUp(final GameMap scene) {
		this.doFill(this.explosionSize, Function.SUB.value(this.row - 1),
				Function.EQUAL.value(this.column), scene, "explotion-vertical",
				"explotion-top");
	}

	private void fillLeft(final GameMap scene) {
		this.doFill(this.explosionSize, Function.EQUAL.value(this.row),
				Function.SUB.value(this.column - 1), scene,
				"explotion-horizontal", "explotion-left");
	}

	private void fillDown(final GameMap scene) {
		this.doFill(this.explosionSize, Function.ADD.value(this.row + 1),
				Function.EQUAL.value(this.column), scene, "explotion-vertical",
				"explotion-down");
	}

	private void fillRight(final GameMap scene) {
		this.doFill(this.explosionSize, Function.EQUAL.value(this.row),
				Function.ADD.value(this.column + 1), scene,
				"explotion-horizontal", "explotion-right");
	}

	private void doFill(final int size, final Function row,
			final Function column, final GameMap scene, final String segment,
			final String lastSegment) {
		if (!scene.isBlockPresent(row.value(), column.value())) {
			if (size > 1) {
				scene.addExplotionPart(new ExplotionPart().initialize(
						row.value(), column.value(), this.explosionSize - size,
						segment));
				this.doFill(size - 1, row.next(), column.next(), scene,
						segment, lastSegment);
			} else {
				scene.addExplotionPart(new ExplotionPart().initialize(
						row.value(), column.value(), this.explosionSize - size,
						lastSegment));
			}
		}
	}
}
