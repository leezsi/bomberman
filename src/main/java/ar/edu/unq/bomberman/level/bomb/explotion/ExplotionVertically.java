package ar.edu.unq.bomberman.level.bomb.explotion;

public class ExplotionVertically extends ExplotionPart {

	protected String spriteName;

	public ExplotionPart initialize(final double x, final double y,
			final int delta, final String spriteName) {
		this.spriteName = spriteName;
		return super.initialize(x, y + (delta * CELL_HEIGHT), delta);
	}

	@Override
	protected String spriteName() {
		return this.spriteName;
	}

}
