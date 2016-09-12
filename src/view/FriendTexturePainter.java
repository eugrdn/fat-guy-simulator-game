package view;

import java.awt.Dimension;
import java.awt.Point;
import java.util.Random;

import listeners.EventData;
import listeners.EventType;
import listeners.Listener;
import listeners.SenderType;
import model.Friend;
import model.ObjectInfo;

import resources.Resourcer;

/**
 * A class that describes the visual view of the Friend objects in the game
 * 
 * @author Rodin E.V.
 * @version 1.0
 */
public class FriendTexturePainter extends DrawPanel implements Listener {
	/**
	 * Numeric constant field that describes a serial version UID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * A string field that describes a possible number of existing friend looks
	 */
	private static String number = "1";

	/**
	 * A constructor that download an friend picture, and install its parametres
	 * 
	 * @param friend
	 *            an object of the Friend class {@link model.Friend}
	 */
	public FriendTexturePainter(Friend friend) {
		super(Bufferizer.getImage(Resourcer.getString("files.image.friend") + number + ".png"));
		ObjectInfo friendInfo = friend.getOi();
		Dimension friendDimension = friendInfo.getDimension();
		Point friendLocation = friendInfo.getLocation();

		this.setSize(friendDimension);
		this.setLocation(friendLocation);
		this.setLayout(null);

		setRandomTexture();
	}

	/**
	 * A method that choose a Random picture for the friend object (using only
	 * when object is initializating)
	 */
	private void setRandomTexture() {
		number = "";
		Random rnd = new Random();
		number += rnd.nextInt(4);
	}

	/**
	 * A method that catch events that done by Friend class objects, and execute
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
		if (sender == SenderType.FRIEND && event == EventType.MOVE) {
			moveFriend((ObjectInfo) data);
		}
		if (sender == SenderType.FRIEND && event == EventType.DIE) {
			deleteFriend();
		}
	}

	/**
	 * A method that delete a friend panel, i.e visual view of the friend object
	 * on the frame
	 */
	private void deleteFriend() {
		this.getParent().remove(this);
	}

	/**
	 * A method that makes Panel of the Friend class object moving
	 * 
	 * @param data
	 *            an object of the ObjectInfo class {@link model.ObjectInfo}
	 */
	private void moveFriend(ObjectInfo data) {
		ObjectInfo friendInfo = (ObjectInfo) data;
		Point friendLocation = friendInfo.getLocation();
		this.setLocation(friendLocation);
		this.setSize(data.getDimension());
	}
}
