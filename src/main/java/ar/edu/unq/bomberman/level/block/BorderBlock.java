package ar.edu.unq.bomberman.level.block;


public class BorderBlock extends Block {

	public BorderBlock(final int row, final int column) {
		super(row, column);
	}

	@Override
	protected String spriteResource() {
		return "steel-solid";
	}
}
