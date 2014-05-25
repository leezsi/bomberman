package ar.edu.unq.bomberman.level.items;

import ar.edu.unq.bomberman.player.Player;

public class Bomb extends Item {

	@Override
	protected String image() {
		return "bomb";
	}

	@Override
	protected void applyEffect(final Player player) {
		player.addBombRemaind();
	}

}
