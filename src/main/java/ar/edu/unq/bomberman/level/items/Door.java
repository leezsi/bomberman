package ar.edu.unq.bomberman.level.items;

import ar.edu.unq.americana.events.annotations.Events;
import ar.edu.unq.americana.events.ioc.collision.CollisionStrategy;
import ar.edu.unq.bomberman.events.LevelWinEvent;
import ar.edu.unq.bomberman.level.bomb.explotion.ExplotionPart;
import ar.edu.unq.bomberman.level.enemies.Coin;
import ar.edu.unq.bomberman.player.Player;

public class Door extends Item {

	@Override
	protected String image() {
		return "door";
	}

	@Events.ColitionCheck.ForType(collisionStrategy = CollisionStrategy.FromBounds, type = ExplotionPart.class)
	private void explodeCollition(final ExplotionPart part) {
		this.getScene().removeExplotionPart(part);
		this.addEnemies();
	}

	private void addEnemies() {
		for (int i = 0; i < 4; i++) {
			this.getScene().addEnemy(this.getRow(), this.getColumn(),
					Coin.class);
		}
	}

	@Override
	protected void applyEffect(final Player player) {
		this.fire(new LevelWinEvent(player));
	}

}
