package view;

import java.awt.Dimension;
import java.awt.Point;
import java.util.Random;

import listeners.EventData;
import listeners.EventType;
import listeners.Listener;
import listeners.SenderType;
import model.Enemy;
import model.ObjectInfo;

import resources.Resourcer;

/**
 * A class that describes the visual view of the Enemy objects in the game
 * 
 * @author Rodin E.V.
 * @version 1.0
 */
public class EnemyTexturePainter extends DrawPanel implements Listener {
	/**
	 * Numeric constant field that describes a serial version UID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * A string field that describes a possible number of existing enemy looks
	 */
	private static String number = "1";

	/**
	 * A constructor that download an enemy picture, and install its parametres
	 * 
	 * @param enemy
	 *            an object of the Enemy class {@link model.Enemy}
	 */
	public EnemyTexturePainter(Enemy enemy) {
		super(Bufferizer.getImage(Resourcer.getString("files.image.enemy") + number + ".png"));
		ObjectInfo enemyInfo = enemy.getOi();
		Dimension enemyDimension = enemyInfo.getDimension();
		Point enemyLocation = enemyInfo.getLocation();

		this.setSize(enemyDimension);
		this.setLocation(enemyLocation);
		this.setLayout(null);

		setRandomTexture();
	}

	/**
	 * A method that choose a Random picture for the enemy object (using only
	 * when object is initializating)
	 */
	private void setRandomTexture() {
		number = "";
		Random rnd = new Random();
		number += rnd.nextInt(4);
	}

	/**
	 * A method that catch events that done by Enemy class objects, and execute
	 * certain actions provided by game description
	 * 
	 * @param eventData
	 *            an object that stores basic information about event: sender
	 *            type,event type, data {@link listeners.EventData}
	 */
	@Override
	public void handleEvent(EventData eventData) {
		SenderType sender = eventData.getSenderType();
		EventType event = eventData.getEventType();
		Object data = eventData.getData();
		if (sender == SenderType.ENEMY && event == EventType.MOVE) {
			moveEnemy((ObjectInfo) data);
		}
		if (sender == SenderType.ENEMY && event == EventType.DIE) {
			deleteEnemy();
		}
	}

	/**
	 * A method that delete a enemy panel, i.e visual view of the enemy object
	 * on the frame
	 */
	private void deleteEnemy() {
		this.getParent().remove(this);
	}

	/**
	 * A method that makes Panel of the Enemy class object moving
	 * 
	 * @param data
	 *            an object of the ObjectInfo class {@link model.ObjectInfo}
	 */
	private void moveEnemy(ObjectInfo data) {
		ObjectInfo enemyInfo = (ObjectInfo) data;
		Point enemyLocation = enemyInfo.getLocation();
		this.setLocation(enemyLocation);
		this.setSize(data.getDimension());
	}
}
