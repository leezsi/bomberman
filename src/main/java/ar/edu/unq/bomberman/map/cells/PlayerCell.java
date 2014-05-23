package ar.edu.unq.bomberman.map.cells;

import ar.edu.unq.americana.exceptions.GameException;
import ar.edu.unq.bomberman.level.GameMap;

public class PlayerCell extends Cell {

	private static final long serialVersionUID = 333199836733830362L;
	private final String type;

	public PlayerCell(final int row, final int column, final int pixel) {
		super(row, column);
		this.type = this.getType(pixel);
	}

	private String getType(final int pixel) {
		switch (pixel) {
		case 0xffffffff:
			return "bomberman";
		case 0xff000032:
			return "balloon";
		default:
			throw new GameException("unknow player type");
		}
	}

	@Override
	public void addContent(final GameMap map) {
		if (this.type.equals("bomberman")) {
			map.addPlayer(this.getFixedRow(), this.getFixedColumn());
		}

	}

}
