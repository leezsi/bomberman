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
	},
	STAY {

	};

	public void applyRightAnimation(final Player player) {
		player.setAppearance(this.getAnimation("bomberman-right"));
		player.setPositionState(GOING_RIGHT);
	};

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

	public void applyLeftAnimation(final Player player) {
		player.setAppearance(this.getAnimation("bomberman-left"));
		player.setPositionState(GOING_LEFT);
	}

	public void applyLeftStop(final Player player) {
		// dummy

	}

	public void applyDownAnimation(final Player player) {
		player.setAppearance(this.getAnimation("bomberman-front"));
		player.setPositionState(GOING_DOWN);
	}

	public void applyDownStop(final Player player) {
		// dummy

	}

	public void applyUpAnimation(final Player player) {
		player.setAppearance(this.getAnimation("bomberman-back"));
		player.setPositionState(GOING_UP);
	}

	public void applyUpStop(final Player player) {
		// dummy

	}
}
