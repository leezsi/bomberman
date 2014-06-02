package ar.edu.unq.bomberman.level.bomb.explotion;

public class ExplotionHorizontally extends ExplotionPart {

	private String spriteName;

	@Override
	protected String spriteName() {
		return this.spriteName;
	}

	public ExplotionPart initialize(final double row, final double column,
			final int delta, final String spriteName) {
		this.spriteName = spriteName;
		return super.initialize(row, column, delta);
	}

}
