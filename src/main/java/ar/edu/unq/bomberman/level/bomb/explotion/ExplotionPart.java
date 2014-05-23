package ar.edu.unq.bomberman.level.bomb.explotion;

import ar.edu.unq.americana.GameComponent;
import ar.edu.unq.americana.appearances.utils.SpriteResources;
import ar.edu.unq.americana.configs.Property;
import ar.edu.unq.americana.events.annotations.Events;
import ar.edu.unq.bomberman.level.GameMap;

public abstract class ExplotionPart extends GameComponent<GameMap> {

	@Property("cell.width")
	protected static double CELL_WIDTH;

	@Property("cell.height")
	protected static double CELL_HEIGHT;

	@Property("explotion.duration")
	private static double ANIMATION_DURATION;

	private double remaindingTime;

	protected abstract String spriteName();

	public ExplotionPart initialize(final double x, final double y) {
		return this.initialize(x, y, 0);
	}

	protected ExplotionPart initialize(final double x, final double y,
			final double delta) {
		this.setAppearance(SpriteResources.sprite("assets/bomb/bomb",
				this.spriteName()).copy());
		this.setX(x);
		this.setY(y);
		this.remaindingTime = ANIMATION_DURATION * (Math.abs(delta) + 1);
		return this;
	}

	@Events.Update
	public void update(final double delta) {
		if ((this.remaindingTime -= delta) <= 0) {
			this.destroy();
		}
	}

}
