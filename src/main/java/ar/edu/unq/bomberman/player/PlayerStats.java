package ar.edu.unq.bomberman.player;

public class PlayerStats {

	private int remainingBombs;
	private boolean bombHeart;
	private int explotionSize;

	public PlayerStats() {
		this.remainingBombs = 1;
		this.explotionSize = 1;
	}

	public boolean hasAnyBombs() {
		return this.remainingBombs > 0;
	}

	public void putBomb() {
		this.remainingBombs--;
	}

	public void bombExplode() {
		this.remainingBombs++;
	}

	public boolean isBombHeart() {
		return this.bombHeart;
	}

	public void addBombHeart() {
		this.bombHeart = true;
	}

	public int getExplotionSize() {
		return this.explotionSize;
	}

	public void increseExplosionSize() {
		this.explotionSize++;
	}

}
