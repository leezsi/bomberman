package ar.edu.unq.bomberman.level.block;

import java.util.HashMap;
import java.util.Map;

import ar.edu.unq.americana.pooling.AbstractPool;

public abstract class BrickPool<B extends Block> extends AbstractPool<B> {
	private static Map<String, AbstractPool<? extends Block>> pools = new HashMap<String, AbstractPool<? extends Block>>();

	static {
		pools.put("normal", new BrickPool<Brick>() {
			@Override
			protected Class<Brick> getType() {
				return Brick.class;
			}
		});
		pools.put("border", new BrickPool<BorderBlock>() {

			@Override
			protected Class<BorderBlock> getType() {
				return BorderBlock.class;
			}

		});
		pools.put("unbreakable", new BrickPool<UnbreakableBlock>() {

			@Override
			protected Class<UnbreakableBlock> getType() {
				return UnbreakableBlock.class;
			}

		});
	}

	@Override
	protected void initialize() {
		super.initialize(40);
	}

	@SuppressWarnings("unchecked")
	public static <B extends Block> B get(final String type) {
		return (B) pools.get(type).get();
	}

	@SuppressWarnings("unchecked")
	public static void backToBricks(final Brick brick) {
		((BrickPool<Brick>) pools.get("normal")).add(brick);
	}
}
