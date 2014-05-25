package ar.edu.unq.bomberman.level.items;

import ar.edu.unq.bomberman.events.LevelWinEvent;
import ar.edu.unq.bomberman.player.Player;

public class Door extends Item {

	@Override
	protected String image() {
		return "door";
	}

	@Override
	protected void applyEffect(final Player player) {
		this.fire(new LevelWinEvent(player));
	}

}
