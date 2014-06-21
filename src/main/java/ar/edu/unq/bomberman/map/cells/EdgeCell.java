package ar.edu.unq.bomberman.map.cells;

import ar.edu.unq.bomberman.level.GameMap;

public class EdgeCell extends Cell {

	private static final long serialVersionUID = -5971008668943578417L;

	public EdgeCell(final int row, final int column) {
		super(row, column);
	}

	@Override
	public void addContent(final GameMap map) {
		map.addEdgeBlock(this.getRow(), this.getColumn());

	}

}
