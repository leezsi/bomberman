package ar.edu.unq.bomberman.level.items;

import java.util.HashMap;
import java.util.Map;

import ar.edu.unq.americana.configs.Bean;
import ar.edu.unq.americana.pooling.AbstractPool;

@Bean
public class ItemPool {

	private static Map<String, AbstractPool<?>> pools = new HashMap<String, AbstractPool<?>>();

	static {
		pools.put("fulgor", new AbstractPool<Fulgor>() {

			@Override
			protected void initialize() {
				this.initialize(5);
			}

			@Override
			protected Class<Fulgor> getType() {
				return Fulgor.class;
			}
		});
		pools.put("bomb", new AbstractPool<Bomb>() {

			@Override
			protected void initialize() {
				this.initialize(5);
			}

			@Override
			protected Class<Bomb> getType() {
				return Bomb.class;
			}
		});
		pools.put("door", new AbstractPool<Door>() {

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

	public static Item get(final String item) {
		return (Item) pools.get(item).get();
	}

}
