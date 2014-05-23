package ar.edu.unq.bomberman.map.cells;

import java.io.Serializable;

import ar.edu.unq.bomberman.level.GameMap;

public abstract class Cell implements Serializable {
	private static final long serialVersionUID = -3617055785945703151L;

	private final double row, column;

	public Cell() {
		this(0, 0);
	}

	public Cell(final int row, final int column) {
		this.row = row;
		this.column = column;
	}

	public double getFixedRow() {
		return this.row + 1;
	}

	public double getFixedColumn() {
		return this.column + 1;
	}

	public abstract void addContent(final GameMap map);

}
