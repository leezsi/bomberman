package ar.edu.unq.bomberman.level.items;

import ar.edu.unq.bomberman.player.Player;

public class Heart extends Item {

	@Override
	protected void applyEffect(final Player player) {
		player.setBombHeart(true);
	}

	@Override
	protected String image() {
		return "heart";
	}

}
