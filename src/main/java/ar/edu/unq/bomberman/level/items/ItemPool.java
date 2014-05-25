package ar.edu.unq.bomberman.level.items;

import java.util.HashMap;
import java.util.Map;

import ar.edu.unq.americana.configs.Bean;
import ar.edu.unq.americana.pooling.AbstractPool;

@Bean
public class ItemPool {

	private static Map<Class<? extends Item>, AbstractPool<? extends Item>> pools = new HashMap<Class<? extends Item>, AbstractPool<? extends Item>>();

	static {
		pools.put(Fulgor.class, new AbstractPool<Fulgor>() {

			@Override
			protected void initialize() {
				this.initialize(5);
			}

			@Override
			protected Class<Fulgor> getType() {
				return Fulgor.class;
			}
		});
		pools.put(Bomb.class, new AbstractPool<Bomb>() {

			@Override
			protected void initialize() {
				this.initialize(5);
			}

			@Override
			protected Class<Bomb> getType() {
				return Bomb.class;
			}
		});
		pools.put(Door.class, new AbstractPool<Door>() {

			@Override
			protected void initialize() {
				this.initialize(5);
			}

			@Override
			protected Class<Door> getType() {
				return Door.class;
			}
		});
	}

	public static Item get(final Class<? extends Item> item) {
		return pools.get(item).get();
	}

	@SuppressWarnings("unchecked")
	public static void add(final Item item) {
		((AbstractPool<Item>) pools.get(item.getClass())).add(item);
	}

}
