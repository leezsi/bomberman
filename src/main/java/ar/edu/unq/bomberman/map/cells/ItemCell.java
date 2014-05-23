package ar.edu.unq.bomberman.map.cells;

import ar.edu.unq.americana.exceptions.GameException;
import ar.edu.unq.bomberman.level.GameMap;
import ar.edu.unq.bomberman.level.items.ItemPool;

public class ItemCell extends Cell {

	private static final long serialVersionUID = 3836010254523720576L;
	private final String type;

	public ItemCell(final int row, final int column, final int pixel) {
		super(row, column);
		this.type = this.getItemType(pixel);
	}

	private String getItemType(final int pixel) {
		switch (pixel) {
		case 0xffffffff:
			return "door";
		case 0xff0000ff:
			return "fulgor";
		case 0xff00ff00:
			return "bomb";
		default:
			throw new GameException("unknow item type");
		}
	}

	@Override
	public void addContent(final GameMap map) {
		map.addItem(ItemPool.get(this.type).initialize(this.getFixedRow(),
				this.getFixedColumn()));
	}
}
