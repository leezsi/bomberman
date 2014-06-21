package ar.edu.unq.bomberman.map.cells;

import java.io.Serializable;

import ar.edu.unq.bomberman.level.GameMap;

public abstract class Cell implements Serializable {
	private static final long serialVersionUID = -3617055785945703151L;

	private final int row, column;

	public Cell() {
		this(0, 0);
	}

	public Cell(final int row, final int column) {
		this.row = row;
		this.column = column;
	}

	public int getRow() {
		return this.row;
	}

	public int getColumn() {
		return this.column;
	}

	public abstract void addContent(final GameMap map);

}
