package ar.edu.unq.bomberman.level.items;

import ar.edu.unq.bomberman.player.Player;

public class Fulgor extends Item {

	@Override
	protected String image() {
		return "fulgor";
	}

	@Override
	protected void applyEffect(final Player player) {
		player.increseExplosionSize();
	}

}
