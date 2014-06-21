package ar.edu.unq.bomberman.map.cells;

import ar.edu.unq.bomberman.level.GameMap;

public class BrickCell extends Cell {

	private static final long serialVersionUID = 9201547903694908060L;

	public BrickCell(final int row, final int column) {
		super(row, column);
	}

	@Override
	public void addContent(final GameMap map) {
		map.addBlock(this.getRow(), this.getColumn());
	}

}
