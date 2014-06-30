package ar.edu.unq.bomberman.player;

import ar.edu.unq.americana.appearances.Animation;
import ar.edu.unq.americana.appearances.Shape;
import ar.edu.unq.americana.appearances.utils.SpriteResources;
import ar.edu.unq.americana.events.ioc.EventManager;
import ar.edu.unq.bomberman.Bomberman;
import ar.edu.unq.bomberman.player.events.PlayerLossLifeEvent;

public enum PlayerPositionState {

	GOING_RIGHT {
		@Override
		public void applyRightStop(final Player player) {
			player.setAppearance(this.getSprite("bomberman-right1"));
			player.setPositionState(STAY);
			player.fixColumn(1);
			player.fixPosition();
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
				Bomberman.PLAYER_STEP_SOUND.play();
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
			player.fixColumn(-1);
			player.fixPosition();

		}

		@Override
		public void update(final double delta, final Player player) {
			final double dx = this.width * delta * DELTA;
			this.remaind -= dx;
			if (this.remaind > 0) {
				player.move(-dx, 0);
			} else {
				Bomberman.PLAYER_STEP_SOUND.play();
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
			player.fixRow(-1);
			player.fixPosition();

		}

		@Override
		public void update(final double delta, final Player player) {
			final double dy = this.height * delta * DELTA;
			this.remaind -= dy;
			if (this.remaind > 0) {
				player.move(0, -dy);
			} else {
				Bomberman.PLAYER_STEP_SOUND.play();
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
			player.fixRow(1);
			player.fixPosition();

		}

		@Override
		public void update(final double delta, final Player player) {
			final double dy = this.height * delta * DELTA;
			this.remaind -= dy;
			if (this.remaind > 0) {
				player.move(0, dy);
			} else {
				Bomberman.PLAYER_STEP_SOUND.play();
				this.applyDownStop(player);
			}
		}
	},
	STAY {

		@Override
		public void update(final double delta, final Player player) {
		}

	},
	DIEING {
		@Override
		public void onAnimationEnd(final Player player) {
			player.setAppearance(this.getSprite("bomberman-front1"));
			EventManager.fire(new PlayerLossLifeEvent());
		}

		@Override
		public void applyRightStop(final Player player) {
		}

		@Override
		public void applyUpAnimation(final Player player) {
		}

		@Override
		public void applyUpStop(final Player player) {
		}

		@Override
		public void applyDownAnimation(final Player player) {
		}

		@Override
		public void applyDownStop(final Player player) {
		}

		@Override
		public void applyLeftAnimation(final Player player) {
		}

		@Override
		public void applyLeftStop(final Player player) {
		}

		@Override
		public void applyRightAnimation(final Player player) {
		}

		@Override
		public void applyDieingAnimation(final Player player) {

		}

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
		if ((this.remaind <= 0)) {
			if (player.getScene().isAccessible(player.getRow(),
					player.getColumn() + 1)) {
				player.setAppearance(this.getAnimation("bomberman-right"));
				player.setPositionState(GOING_RIGHT.initialize(this.width,
						this.height).remaind(this.width));
				player.setCanPutBomb(true);
			} else {
				player.setAppearance(this.getSprite("bomberman-right1"));
			}
		}
	};

	public void applyLeftAnimation(final Player player) {
		if (this.remaind <= 0) {
			if (player.getScene().isAccessible(player.getRow(),
					player.getColumn() - 1)) {
				player.setAppearance(this.getAnimation("bomberman-left"));
				player.setPositionState(GOING_LEFT.initialize(this.width,
						this.height).remaind(this.width));
				player.setCanPutBomb(true);
			} else {
				player.setAppearance(this.getSprite("bomberman-left1"));
			}
		}
	}

	public void applyLeftStop(final Player player) {
		// dummy

	}

	public void applyDownAnimation(final Player player) {
		if (this.remaind <= 0) {
			if (player.getScene().isAccessible(player.getRow() + 1,
					player.getColumn())) {
				player.setAppearance(this.getAnimation("bomberman-front"));
				player.setPositionState(GOING_DOWN.initialize(this.width,
						this.height).remaind(this.height));
				player.setCanPutBomb(true);
			} else {
				player.setAppearance(this.getSprite("bomberman-front1"));
			}
		}
	}

	public void applyDownStop(final Player player) {
		// dummy

	}

	public void applyUpAnimation(final Player player) {
		if (this.remaind <= 0) {
			if (player.getScene().isAccessible(player.getRow() - 1,
					player.getColumn())) {
				player.setAppearance(this.getAnimation("bomberman-back"));
				player.setPositionState(GOING_UP.initialize(this.width,
						this.height).remaind(this.height));
				player.setCanPutBomb(true);
			} else {
				player.setAppearance(this.getSprite("bomberman-back1"));
			}
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

	public void applyDieingAnimation(final Player player) {
		player.setPositionState(DIEING);
		final Animation animation = this.getAnimation("bomberman-die");
		player.setAppearance(animation);
	}

	public void onAnimationEnd(final Player player) {

	}
}
