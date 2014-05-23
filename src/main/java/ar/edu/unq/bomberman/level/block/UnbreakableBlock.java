package ar.edu.unq.bomberman.level.block;

public class UnbreakableBlock extends Block {

	public UnbreakableBlock(final double row, final double column) {
		super(row, column);
	}

	@Override
	protected String spriteResource() {
		return "steel-brick";
	}
}
