package ar.edu.unq.bomberman.map;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unq.bomberman.map.cells.BrickCell;
import ar.edu.unq.bomberman.map.cells.Cell;
import ar.edu.unq.bomberman.map.cells.ItemCell;
import ar.edu.unq.bomberman.map.cells.PlayerCell;
import ar.edu.unq.bomberman.map.cells.SteelCell;

public class GameMapXML implements Serializable {

	private static final long serialVersionUID = 4588808924225885966L;

	public double width, height;

	public List<Cell> cells = new ArrayList<Cell>();

	public void addBlock(final int row, final int column) {
		this.cells.add(new BrickCell(row, column));
	}

	public void addItem(final int row, final int column, final int pixel) {
		this.cells.add(new ItemCell(row, column, pixel));
	}

	public void addSteelBrick(final int row, final int column) {
		this.cells.add(new SteelCell(row, column));

	}

	public void addPlayer(final int row, final int column, final int pixel) {
		this.cells.add(new PlayerCell(row, column, pixel));
	}

}
