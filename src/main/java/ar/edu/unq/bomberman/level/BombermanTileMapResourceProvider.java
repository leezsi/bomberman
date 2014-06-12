package ar.edu.unq.bomberman.level;

import ar.edu.unq.americana.appearances.utils.SpriteResources;
import ar.edu.unq.americana.scenes.components.tilemap.BaseTileMapResourceProvider;

public class BombermanTileMapResourceProvider extends
		BaseTileMapResourceProvider {

	public BombermanTileMapResourceProvider(final int rows, final int columns) {
		super(rows, columns);
		this.register(1,
				SpriteResources.sprite("assets/block/block", "steel-brick"));
		this.register(2,
				SpriteResources.sprite("assets/block/block", "steel-solid"));
	}

}
