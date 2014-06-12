package ar.edu.unq.bomberman.components;

import ar.edu.unq.americana.GameComponent;
import ar.edu.unq.americana.scenes.components.tilemap.Positionable;
import ar.edu.unq.bomberman.level.GameMap;

public class PositionableComponent extends GameComponent<GameMap> implements
		Positionable {

	private int column;
	private int row;

	protected void setColumn(final int column) {
		this.column = column;
	}

	protected void setRow(final int row) {
		this.row = row;
	}

	@Override
	public int getColumn() {
		return this.column;
	}

	@Override
	public void fixColumn(final int delta) {
		this.column += delta;
	}

	@Override
	public int getRow() {
		return this.row;
	}

	@Override
	public void fixRow(final int delta) {
		this.row += delta;
	}

}
