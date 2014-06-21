package ar.edu.unq.bomberman.map.cells;

import ar.edu.unq.americana.exceptions.GameException;
import ar.edu.unq.bomberman.level.GameMap;
import ar.edu.unq.bomberman.level.enemies.Balloon;
import ar.edu.unq.bomberman.level.enemies.Snowflake;
import ar.edu.unq.bomberman.player.Player;

public class PlayerCell extends Cell {

	private static final long serialVersionUID = 333199836733830362L;
	private final Class<?> type;

	public PlayerCell(final int row, final int column, final int pixel) {
		super(row, column);
		this.type = this.getType(pixel);
	}

	private Class<?> getType(final int pixel) {
		switch (pixel) {
		case 0xffffffff:
			return Player.class;
		case 0xff000032:
			return Balloon.class;
		case 0xffdf006a:
			return Snowflake.class;
		default:
			throw new GameException("unknow player type");
		}
	}

	@Override
	public void addContent(final GameMap map) {
		if (this.type.equals(Player.class)) {
			map.addPlayer(this.getRow(), this.getColumn());
		} else {
			map.addEnemy(this.getRow(), this.getColumn(), this.type);
		}

	}

}
