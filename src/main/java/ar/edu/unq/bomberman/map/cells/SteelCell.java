package ar.edu.unq.bomberman.map.cells;

import ar.edu.unq.bomberman.level.GameMap;

public class SteelCell extends Cell {
	private static final long serialVersionUID = -3168698783440983475L;

	public SteelCell(final int row, final int column) {
		super(row, column);
	}

	@Override
	public void addContent(final GameMap map) {
		map.addSteelBlock(this.getFixedRow(), this.getFixedColumn());
	}
}
