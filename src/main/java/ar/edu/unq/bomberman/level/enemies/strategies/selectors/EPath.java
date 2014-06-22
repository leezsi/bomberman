package ar.edu.unq.bomberman.level.enemies.strategies.selectors;

import ar.edu.unq.americana.ia.pathfindier.Path;
import ar.edu.unq.americana.scenes.components.tilemap.Positionable;

public class EPath extends Path {

	private final int column;
	private final int row;

	public EPath(final int row, final int column) {
		super(null);
		this.row = row;
		this.column = column;
	}

	@Override
	public int deltaColumn(final Positionable component) {
		return this.column - component.getColumn();
	}

	@Override
	public int deltaRow(final Positionable component) {
		return this.row - component.getRow();
	}

	@Override
	public String toString() {
		return "EPath [column=" + this.column + ", row=" + this.row + "]";
	}

}
