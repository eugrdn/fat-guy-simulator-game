package model;

public class Statist {

	private static final int HP = 3;
	private int score = 0;
	private int health = HP;

	public Statist() {
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score + 1;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health - 1;
	}

}
