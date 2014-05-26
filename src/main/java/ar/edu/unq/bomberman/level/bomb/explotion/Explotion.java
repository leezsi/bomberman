package ar.edu.unq.bomberman.level.bomb.explotion;

import ar.edu.unq.bomberman.level.GameMap;

public class Explotion {

	private final double cx;
	private final double cy;
	private final int explosionSize;

	public Explotion(final int explosionSize, final double cx, final double cy) {
		this.cx = cx;
		this.cy = cy;
		this.explosionSize = explosionSize;
	}

	public void addComponents(final GameMap scene) {
		scene.addExplotionPart(new ExplotionCenter().initialize(this.cx,
				this.cy));
		this.fillExplotion(scene);
		scene.addExplotionPart(new ExplotionVertically().initialize(this.cx,
				this.cy, -this.explosionSize, "explotion-top"));
		scene.addExplotionPart(new ExplotionVertically().initialize(this.cx,
				this.cy, this.explosionSize, "explotion-down"));
		scene.addExplotionPart(new ExplotionHorizontally().initialize(this.cx,
				this.cy, -this.explosionSize, "explotion-left"));
		scene.addExplotionPart(new ExplotionHorizontally().initialize(this.cx,
				this.cy, this.explosionSize, "explotion-right"));
	}

	private void fillExplotion(final GameMap scene) {
		for (int i = 1; i < this.explosionSize; i++) {
			scene.addExplotionPart(new ExplotionHorizontally().initialize(
					this.cx, this.cy, i, "explotion-horizontal"));
			scene.addExplotionPart(new ExplotionHorizontally().initialize(
					this.cx, this.cy, -i, "explotion-horizontal"));
			scene.addExplotionPart(new ExplotionVertically().initialize(
					this.cx, this.cy, i, "explotion-vertical"));
			scene.addExplotionPart(new ExplotionVertically().initialize(
					this.cx, this.cy, -i, "explotion-vertical"));
		}
	}
}
