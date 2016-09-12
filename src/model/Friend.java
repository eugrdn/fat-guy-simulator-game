package model;

import java.awt.Point;
import listeners.*;

/**
 * A class that determines an friend objects in the game. It's extends by the
 * GameObject class, that have main fields for each game object. Friend is a
 * figure that falls down in straight line, and if it intersects the character
 * object, that give it a one game point (game score - sum of the game points),
 * in different case - it will nothing to do
 * 
 * @author Rodin E.V.
 * @version 1.0
 */
public class Friend extends GameObject {
	/**
	 * Numeric constant field that describes how fast friend object will fly
	 * down
	 */
	private static final int SPEED = 5;
	/**
	 * A boolean switch that describe situation when friend object die or not,
	 * to controll game process
	 */
	private boolean die = false;
	/**
	 * A boolean switch for the running process forcing to move friend object
	 * (true/false)
	 */
	private boolean run = true;
	/**
	 * Numeric field that describe offset axially Y {@link model.Friend#SPEED}
	 */
	private double dy = SPEED;
	/**
	 * Numeric field that describe acceleration of the object fly speed
	 * {@link model.Friend#SPEED}
	 */
	private double boost;
	/**
	 * Initialization of the thread. Every friend object will exist in it's own
	 * thread
	 */
	private Thread friendThread = null;
	/**
	 * Initialization of an object of the ListenerList class
	 * {@link listeners.ListenerList}
	 */
	private ListenerList friendListener = new ListenerList();

	/**
	 * A constructer that initializes Friend class objects using parent class
	 * fields
	 * 
	 * @param ls
	 *            ListenerList class object {@link listeners.ListenerList}
	 * @param gf
	 *            GameField class object {@link model.GameField}
	 * @param oi
	 *            ObjectInfo class object {@link model.ObjectInfo}
	 */
	public Friend(ListenerList ls, GameField gf, ObjectInfo oi) {
		super(oi, gf, ls);
		this.setOi(oi);
	}

	/**
	 * A method that adds listener to Friend object {@link listeners.Listener}
	 * 
	 * @param e
	 *            an object of Listener class
	 */
	public boolean addFriendListener(Listener e) {
		return friendListener.add(e);
	}

	/**
	 * A method that runs thread for the each friend
	 * {@link model.Friend#friendThread}
	 */
	/*public void startFriendThread() {
		friendThread = new Thread(this);
		friendThread.start();
	}
*/
	/**
	 * A method that sets friend initial location and boost using Random class.
	 * It's also notify View about this by listeners
	 */
	@Override
	public void setOi(ObjectInfo oi) {
		this.oi = oi;
		boost = ((Math.random() * 1.1) + 0.9);
		EventData eventData = new EventData(SenderType.FRIEND, EventType.INITIALIZE, this.oi);
		ls.notify(eventData);
	}

	/**
	 * A method that set Friend status - dead. It's also notify notify View
	 * about this by listeners
	 */
	public void setDead() {
		setRun(false);
		EventData eventData = new EventData(SenderType.FRIEND, EventType.DIE, this.oi);
		friendListener.notify(eventData);
	}

	/**
	 * A getter for the dy field
	 * 
	 * @return dy {@link model.Friend#dy}
	 */
	public double getDy() {
		return dy;
	}

	/**
	 * A setter for the dy field
	 * 
	 * @param dy
	 *            {@link model.Friend#dy}
	 */
	public void setDy(double dy) {
		this.dy = dy;
	}

	/**
	 * A method that makes Friend object moving with the boost. It's also notify
	 * notify View about this by listeners
	 */
	public void move() {
		Point location = this.oi.getLocation();
		location.y += dy * boost;
		EventData eventData = new EventData(SenderType.FRIEND, EventType.MOVE, this.oi);
		friendListener.notify(eventData);
	}

	/**
	 * An method (implemented by Runnable interface) that initialize render of
	 * friend object, keeping it movable and set it's dead when it's necessary
	 * 
	 */
	@Override
	public void run() {
		initializeFps();
		while (run) {
			setTimerFPS();
			gameRender();
			move();
			if (die) {
				setDead();
			}
		}
	}

	/**
	 * A setter for the run field
	 * 
	 * @param run
	 *            {@link model.Friend#run}
	 */
	public void setRun(boolean run) {
		this.run = run;
	}

	/**
	 * A setter for the die field
	 * 
	 * @param dy
	 *            {@link model.Friend#die}
	 */
	public void setDie() {
		this.die = true;
	}

	/**
	 * A method that adds listener to Character object
	 * {@link listeners.Listener}
	 * 
	 * @param e
	 *            an object of Listener class
	 */
	public boolean addListener(Listener e) {
		return friendListener.add(e);
	}

}
