package ar.edu.unq.bomberman.level.enemies;

import ar.edu.unq.americana.configs.Configs;
import ar.edu.unq.americana.configs.Property;
import ar.edu.unq.americana.ia.pathfindier.Path;
import ar.edu.unq.americana.ia.pathfindier.astar.AStarPathFinding;
import ar.edu.unq.americana.utils.TrigonometricsAndRandomUtils;
import ar.edu.unq.bomberman.level.GameMap;
import ar.edu.unq.bomberman.player.Player;

public enum EnemyStrategy {
	Balloon {
		private int deltaX;
		private int deltaY;

		@Override
		public void takeStep(final double delta, final Enemy enemy) {
			if (this.remaining <= 0) {
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
					this.remaining = this.cellHeight;
				} else {
					this.deltaY = 0;
					this.deltaX = dx - column;
					this.remaining = this.cellWidth;
					enemy.fixColumn(this.deltaX);
				}
			} else {
				final double dxy = delta * SPEED;
				this.remaining -= dxy;
				enemy.move(this.deltaX * dxy, this.deltaY * dxy);
			}
		}
	},
	NONE {

		@Override
		public void takeStep(final double delta, final Enemy enemy) {

		}

	},
	PathfindingLoockFor {

		@Override
		public void takeStep(final double delta, final Enemy enemy) {
			final GameMap scene = enemy.getScene();
			final Path _path = new AStarPathFinding().find(scene.getTileMap(),
					enemy, scene.getPlayer());
			if (_path != null) {
				final EnemyStrategy strategy = PathfindingMoving.initialize(
						this.cellWidth, this.cellHeight, _path);
				strategy.remaining = _path.deltaColumn(enemy) == 0 ? this.cellHeight
						: this.cellWidth;
				enemy.changeStratety(strategy);
			}
		}

	},
	PathfindingMoving {

		@Override
		public void takeStep(final double delta, final Enemy enemy) {
			if (this.remaining > 0) {
				final double ds = delta * SPEED;
				this.remaining -= ds;
				this.path.takeStep(ds, enemy);
			} else {
				enemy.fixColumn(this.path.deltaColumn(enemy));
				enemy.fixRow(this.path.deltaRow(enemy));
				enemy.changeStratety(PathfindingLoockFor.initialize(
						this.cellWidth, this.cellHeight));
			}
		}

	},

	Snowflake {
		private double deltaX;
		private double deltaY;

		@Override
		public void takeStep(final double delta, final Enemy enemy) {
			final GameMap scene = enemy.getScene();
			final Player player = scene.getPlayer();
			if (this.remaining <= 0) {
				final int column = enemy.getColumn();
				final int row = enemy.getRow();
				int dc = 0, dr = 0;
				do {
					final double dx = (player.getX() - enemy.getX());
					final double dy = (player.getY() - enemy.getY());
					if (TrigonometricsAndRandomUtils.nextBoolean()) {
						dc = (int) (dx == 0 ? 0 : Math.signum(dx));
						if (dc == 0) {
							dr = (int) Math.signum(dy);
						} else {
							dr = 0;
						}
					} else {
						dr = (int) (dy == 0 ? 0 : Math.signum(dy));
						if (dr == 0) {
							dc = (int) Math.signum(dx);
						} else {
							dc = 0;
						}
					}
				} while (scene.isBlockPresent(row + dr, column + dc));
				this.deltaX = dc;
				this.deltaY = dr;
				enemy.fixColumn(dc);
				enemy.fixRow(dr);
				this.remaining = dc == 0 ? this.cellHeight : this.cellWidth;
			} else {
				final double dxy = delta * SPEED;
				this.remaining -= dxy;
				enemy.move(this.deltaX * dxy, this.deltaY * dxy);
			}
		}
	};
	@Property("enemy.speed")
	private static double SPEED;
	protected double remaining;
	protected double cellHeight;
	protected double cellWidth;
	protected Path path;

	public abstract void takeStep(final double delta, Enemy enemy);

	protected EnemyStrategy initialize(final double width, final double height,
			final Path path) {
		this.path = path;
		return this.initialize(width, height);
	}

	private EnemyStrategy() {
		Configs.injectAndReadBeans(this);
		Configs.injectConfigs(this);
	}

	public EnemyStrategy initialize(final double width, final double height) {
		this.cellWidth = width;
		this.cellHeight = height;
		this.remaining = 0;
		return this;
	}
}
