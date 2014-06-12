package ar.edu.unq.bomberman.level.bomb.explotion;

import ar.edu.unq.americana.GameComponent;
import ar.edu.unq.americana.appearances.utils.SpriteResources;
import ar.edu.unq.americana.configs.Property;
import ar.edu.unq.americana.events.annotations.Events;
import ar.edu.unq.bomberman.level.GameMap;

public class ExplotionPart extends GameComponent<GameMap> {

	@Property("cell.width")
	protected static double CELL_WIDTH;

	@Property("cell.height")
	protected static double CELL_HEIGHT;

	@Property("explotion.duration")
	private static double ANIMATION_DURATION;

	private double remaindingTime;

	protected ExplotionPart initialize(final double row, final double column,
			final double delta, final String spriteName) {
		this.setAppearance(SpriteResources.sprite("assets/bomb/bomb",
				spriteName));
		this.setX(column * CELL_WIDTH);
		this.setY(row * CELL_HEIGHT);
		this.remaindingTime = ANIMATION_DURATION * (Math.abs(delta) + 1);
		return this;
	}

	@Events.Update
	public void update(final double delta) {
		if ((this.remaindingTime -= delta) <= 0) {
			this.destroy();
			this.getScene().removeExplotionPart(this);
		}
	}

}
