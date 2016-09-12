package initialization;

import java.awt.Dimension;
import java.awt.Point;

import model.ObjectInfo;

public class Initializator {

	public static final int GAMEFIELD_WIDTH = 894;
	public static final int GAMEFILED_HEIGHT = 500;
	public static final int BLOCK_WIDTH = 45;
	public static final int BLOCK_HEIGHT = 45;
	public static final int ENEMY_WIDTH = 45;
	public static final int ENEMY_HEIGHT = 45;
	public static final int CHARACTER_WIDTH = 60;
	public static final int CHARACTER_HEIGHT = 60;
	public static final int CHARACTER_LOCATION_X = 150;
	public static final int CHARACTER_LOCATION_Y = 390;
	public static final int FIELD_BORDER = 450;

	public Initializator() {
	}

	public static ObjectInfo createGameField() {
		Dimension gameFieldDimension = new Dimension(GAMEFIELD_WIDTH, GAMEFILED_HEIGHT);
		ObjectInfo gameFieldInfo = new ObjectInfo(gameFieldDimension, null);
		return gameFieldInfo;
	}

	public static ObjectInfo createCharacter() {
		Dimension chDimension = new Dimension(CHARACTER_WIDTH, CHARACTER_HEIGHT);
		Point chLocation = new Point(CHARACTER_LOCATION_X, CHARACTER_LOCATION_Y);
		ObjectInfo chInfo = new ObjectInfo(chDimension, chLocation);
		return chInfo;
	}

	public static synchronized ObjectInfo createFriend() {
		Dimension friendDimension = new Dimension(BLOCK_WIDTH, BLOCK_HEIGHT);
		Point friendLocation = new Point((int) (Math.random() * 855) + 1, 0);
		ObjectInfo friendInfo = new ObjectInfo(friendDimension, friendLocation);
		return friendInfo;
	}

	public static synchronized ObjectInfo createEnemy() {
		Dimension enemyDimension = new Dimension(ENEMY_WIDTH, ENEMY_HEIGHT);
		Point enemyLocation = new Point((int) (Math.random() * 855) + 1, 0);
		ObjectInfo enemyInfo = new ObjectInfo(enemyDimension, enemyLocation);
		return enemyInfo;
	}
}
