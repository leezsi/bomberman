package ar.edu.unq.bomberman.level.block;

import ar.edu.unq.americana.pooling.AbstractPool;

public class BrickPool extends AbstractPool<Block> {
	private static final BrickPool INSTANCE = new BrickPool();

	public static BrickPool instance() {
		return INSTANCE;
	}

	@Override
	protected void initialize() {
		this.initialize(50);
	}

	@Override
	protected Class<Block> getType() {
		return Block.class;
	}
}
