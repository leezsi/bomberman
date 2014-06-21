package ar.edu.unq.bomberman.map.cells;

import ar.edu.unq.americana.exceptions.GameException;
import ar.edu.unq.bomberman.level.GameMap;
import ar.edu.unq.bomberman.level.items.Bomb;
import ar.edu.unq.bomberman.level.items.Door;
import ar.edu.unq.bomberman.level.items.Fulgor;
import ar.edu.unq.bomberman.level.items.Heart;
import ar.edu.unq.bomberman.level.items.Item;

public class ItemCell extends Cell {

	private static final long serialVersionUID = 3836010254523720576L;
	private final Class<? extends Item> type;

	public ItemCell(final int row, final int column, final int pixel) {
		super(row, column);
		this.type = this.getItemType(pixel);
	}

	private Class<? extends Item> getItemType(final int pixel) {
		switch (pixel) {
		case 0xffffffff:
			return Door.class;
		case 0xff0000ff:
			return Fulgor.class;
		case 0xff00ff00:
			return Bomb.class;
		case 0xffdf006a:
			return Heart.class;
		default:
			throw new GameException("unknow item type");
		}
	}

	@Override
	public void addContent(final GameMap map) {
		map.addItem(this.type, this.getRow(), this.getColumn());
	}
}
