package ar.edu.unq.bomberman.level.bomb.explotion;

import ar.edu.unq.americana.GameScene;

public class Explotion {

	private final double cx;
	private final double cy;
	private final int explosionSize;

	public Explotion(final int explosionSize, final double cx, final double cy) {
		this.cx = cx;
		this.cy = cy;
		this.explosionSize = explosionSize;
	}

	public void addComponents(final GameScene scene) {
		scene.addComponent(new ExplotionCenter().initialize(this.cx, this.cy));
		this.fillExplotion(scene);
		scene.addComponent(new ExplotionVertically().initialize(this.cx,
				this.cy, -this.explosionSize, "explotion-top"));
		scene.addComponent(new ExplotionVertically().initialize(this.cx,
				this.cy, this.explosionSize, "explotion-down"));
		scene.addComponent(new ExplotionHorizontally().initialize(this.cx,
				this.cy, -this.explosionSize, "explotion-left"));
		scene.addComponent(new ExplotionHorizontally().initialize(this.cx,
				this.cy, this.explosionSize, "explotion-right"));
	}

	private void fillExplotion(final GameScene scene) {
		for (int i = 1; i < this.explosionSize; i++) {
			scene.addComponent(new ExplotionHorizontally().initialize(this.cx,
					this.cy, i, "explotion-horizontal"));
			scene.addComponent(new ExplotionHorizontally().initialize(this.cx,
					this.cy, -i, "explotion-horizontal"));
			scene.addComponent(new ExplotionVertically().initialize(this.cx,
					this.cy, i, "explotion-vertical"));
			scene.addComponent(new ExplotionVertically().initialize(this.cx,
					this.cy, -i, "explotion-vertical"));
		}
	}
}
