package ar.edu.unq.bomberman.player;

import ar.edu.unq.americana.appearances.Animation;
import ar.edu.unq.americana.appearances.Shape;
import ar.edu.unq.americana.appearances.utils.SpriteResources;

public enum PlayerPositionState {

	GOING_RIGHT {
		@Override
		public void applyRightStop(final Player player) {
			player.setAppearance(this.getSprite("bomberman-right1"));
			player.setPositionState(STAY);
		}

		@Override
		public void applyRightAnimation(final Player player) {
			// dummy
		}

		@Override
		public void update(final double delta, final Player player) {
			final double dx = this.width * delta * DELTA;
			this.remaind -= dx;
			if (this.remaind > 0) {
				player.move(dx, 0);
			} else {
				player.fixColumn(1);
				this.applyRightStop(player);
			}
		};
	},
	GOING_LEFT {
		@Override
		public void applyLeftAnimation(final Player player) {
			// dummy
		}

		@Override
		public void applyLeftStop(final Player player) {
			player.setAppearance(this.getSprite("bomberman-left1"));
			player.setPositionState(STAY);
		}

		@Override
		public void update(final double delta, final Player player) {
			final double dx = this.width * delta * DELTA;
			this.remaind -= dx;
			if (this.remaind > 0) {
				player.move(-dx, 0);
			} else {
				player.fixColumn(-1);
				this.applyLeftStop(player);
			}
		}
	},
	GOING_UP {
		@Override
		public void applyUpAnimation(final Player player) {
			// dummy
		}

		@Override
		public void applyUpStop(final Player player) {
			player.setAppearance(this.getSprite("bomberman-back1"));
			player.setPositionState(STAY);
		}

		@Override
		public void update(final double delta, final Player player) {
			final double dy = this.height * delta * DELTA;
			this.remaind -= dy;
			if (this.remaind > 0) {
				player.move(0, -dy);
			} else {
				player.fixRow(-1);
				this.applyUpStop(player);
			}
		}
	},
	GOING_DOWN {
		@Override
		public void applyDownAnimation(final Player player) {
			// dummy
		}

		@Override
		public void applyDownStop(final Player player) {
			player.setAppearance(this.getSprite("bomberman-front1"));
			player.setPositionState(STAY);
		}

		@Override
		public void update(final double delta, final Player player) {
			final double dy = this.height * delta * DELTA;
			this.remaind -= dy;
			if (this.remaind > 0) {
				player.move(0, dy);
			} else {
				player.fixRow(1);
				this.applyDownStop(player);
			}
		}
	},
	STAY {

		@Override
		public void update(final double delta, final Player player) {
		}

	};

	protected double height;
	protected double width;
	protected double remaind;

	protected Animation getAnimation(final String animation) {
		return SpriteResources.animation("assets/bomberman/bomberman",
				animation);
	}

	public void applyRightStop(final Player player) {
		// dummy
	}

	protected Shape getSprite(final String sprite) {
		return SpriteResources.sprite("assets/bomberman/bomberman", sprite);
	}

	public void applyRightAnimation(final Player player) {
		if (this.remaind <= 0) {
			player.setAppearance(this.getAnimation("bomberman-right"));
			player.setPositionState(GOING_RIGHT.initialize(this.width,
					this.height).remaind(this.width));
			player.setCanPutBomb(true);
		}
	};

	public void applyLeftAnimation(final Player player) {
		if (this.remaind <= 0) {
			player.setAppearance(this.getAnimation("bomberman-left"));
			player.setPositionState(GOING_LEFT.initialize(this.width,
					this.height).remaind(this.width));
			player.setCanPutBomb(true);
		}
	}

	public void applyLeftStop(final Player player) {
		// dummy

	}

	public void applyDownAnimation(final Player player) {
		if (this.remaind <= 0) {
			player.setAppearance(this.getAnimation("bomberman-front"));
			player.setPositionState(GOING_DOWN.initialize(this.width,
					this.height).remaind(this.height));
			player.setCanPutBomb(true);
		}
	}

	public void applyDownStop(final Player player) {
		// dummy

	}

	public void applyUpAnimation(final Player player) {
		if (this.remaind <= 0) {
			player.setAppearance(this.getAnimation("bomberman-back"));
			player.setPositionState(GOING_UP
					.initialize(this.width, this.height).remaind(this.height));
			player.setCanPutBomb(true);
		}
	}

	public void applyUpStop(final Player player) {
		// dummy

	}

	public abstract void update(double delta, Player player);

	public PlayerPositionState initialize(final double width,
			final double height) {
		this.width = width;
		this.height = height;
		return this;
	}

	protected PlayerPositionState remaind(final double remaind) {
		this.remaind = remaind;
		return this;
	}

	public static double DELTA = 2;
}
