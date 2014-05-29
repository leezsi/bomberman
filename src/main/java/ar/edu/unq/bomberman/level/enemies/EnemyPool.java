package ar.edu.unq.bomberman.level.enemies;

import java.util.HashMap;
import java.util.Map;

import ar.edu.unq.americana.pooling.AbstractPool;

public class EnemyPool {
	private static Map<Class<? extends Enemy>, AbstractPool<? extends Enemy>> pools = new HashMap<Class<? extends Enemy>, AbstractPool<? extends Enemy>>();
	static {
		pools.put(Balloon.class, new AbstractPool<Balloon>() {

			@Override
			protected void initialize() {
				this.initialize(5);
			}

			@Override
			protected Class<Balloon> getType() {
				return Balloon.class;
			}
		});
		pools.put(Snowflake.class, new AbstractPool<Snowflake>() {

			@Override
			protected void initialize() {
				this.initialize(5);
			}

			@Override
			protected Class<Snowflake> getType() {
				return Snowflake.class;
			}
		});
	}

	public static Enemy get(final Class<? extends Enemy> type) {
		return pools.get(type).get();
	}

	@SuppressWarnings("unchecked")
	public static void add(final Enemy enemy) {
		((AbstractPool<Enemy>) pools.get(enemy.getClass())).add(enemy);
	}

}
