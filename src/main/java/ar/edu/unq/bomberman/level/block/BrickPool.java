package ar.edu.unq.bomberman.level.block;

import java.util.HashMap;
import java.util.Map;

import ar.edu.unq.americana.pooling.AbstractPool;

public abstract class BrickPool {
	private static Map<Class<? extends Block>, AbstractPool<? extends Block>> pools = new HashMap<Class<? extends Block>, AbstractPool<? extends Block>>();

	static {
		pools.put(Brick.class, new AbstractPool<Brick>() {
			@Override
			protected Class<Brick> getType() {
				return Brick.class;
			}

			@Override
			protected void initialize() {
				this.initialize(200);
			}
		});
		pools.put(BorderBlock.class, new AbstractPool<BorderBlock>() {

			@Override
			protected Class<BorderBlock> getType() {
				return BorderBlock.class;
			}

			@Override
			protected void initialize() {
				this.initialize(50);
			}

		});
		pools.put(UnbreakableBlock.class, new AbstractPool<UnbreakableBlock>() {

			@Override
			protected Class<UnbreakableBlock> getType() {
				return UnbreakableBlock.class;
			}

			@Override
			protected void initialize() {
				this.initialize(50);
			}

		});
	}

	@SuppressWarnings("unchecked")
	public static <B extends Block> B get(final Class<B> type) {
		return (B) pools.get(type).get();
	}

	@SuppressWarnings("unchecked")
	public static void backToBricks(final Block block) {
		((AbstractPool<Block>) pools.get(block.getClass())).add(block);
	}
}
