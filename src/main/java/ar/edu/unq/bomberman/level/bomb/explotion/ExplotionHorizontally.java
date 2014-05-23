package ar.edu.unq.bomberman.level.bomb.explotion;

public class ExplotionHorizontally extends ExplotionPart {

	private String spriteName;

	@Override
	protected String spriteName() {
		return this.spriteName;
	}

	public ExplotionPart initialize(final double x, final double y,
			final int delta, final String spriteName) {
		this.spriteName = spriteName;
		return super.initialize(x + (delta * CELL_WIDTH), y, delta);
	}

}
