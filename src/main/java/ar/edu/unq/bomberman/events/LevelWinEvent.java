package ar.edu.unq.bomberman.events;

import ar.edu.unq.americana.events.ioc.fired.FiredEvent;
import ar.edu.unq.bomberman.player.Player;

public class LevelWinEvent extends FiredEvent {

	private final Player player;

	public LevelWinEvent(final Player player) {
		this.player = player;
	}

	public Player getPlayer() {
		return this.player;
	}

}
