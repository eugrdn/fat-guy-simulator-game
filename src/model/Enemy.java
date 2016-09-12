package model;

import java.awt.Point;
import listeners.*;

/**
 * A class that determines an enemy objects in the game. It's extends by the
 * GameObject class, that have main fields for each game object. Enemy is a
 * figure that falls down in straight line, and if it intersects the character
 * object, that hurt it and take away one of it's lifes, in different case - it
 * will nothing to do
 * 
 * @author Rodin E.V.
 * @version 1.0
 */
public class Enemy extends GameObject {
	/**
	 * Numeric constant field that describes how fast Enemy object will fly down
	 */
	private static final int SPEED = 5;
	/**
	 * A boolean switch for the running process forcing to move enemy object
	 * (true/false)
	 */
	private boolean run = true;
	/**
	 * Initialization of the thread. Every enemy object will exist in it's own
	 * thread
	 */
	private Thread enemyThread = null;
	/**
	 * A boolean switch that describe situation when enemy object die or not, to
	 * controll game process
	 */
	private boolean die = false;
	/**
	 * Numeric field that describe acceleration of the object fly speed
	 * {@link model.Enemy#SPEED}
	 */
	private double boost;
	/**
	 * Numeric field that describe offset axially Y {@link model.Enemy#SPEED}
	 */
	private double dy = SPEED;
	/**
	 * Initialization of an object of the ListenerList class
	 * {@link listeners.ListenerList}
	 */
	private ListenerList enemyListener = new ListenerList();

	/**
	 * A constructer that initializes Enemy class objects using parent class
	 * fields
	 * 
	 * @param ls
	 *            ListenerList class object {@link listeners.ListenerList}
	 * @param gf
	 *            GameField class object {@link model.GameField}
	 * @param oi
	 *            ObjectInfo class object {@link model.ObjectInfo}
	 */
	public Enemy(ListenerList ls, GameField gf, ObjectInfo oi) {
		super(oi, gf, ls);
		this.setOi(oi);
	}

	/**
	 * A method that adds listener to Enemy object {@link listeners.Listener}
	 * 
	 * @param e
	 *            an object of Listener class
	 */
	public boolean addEnemyListener(Listener e) {
		return enemyListener.add(e);
	}

	/**
	 * A method that runs thread for the each enemy
	 * {@link model.Enemy#enemyThread}
	 */
	public void startEnemyThread() {
		this.enemyThread = new Thread(this);
		enemyThread.start();
	}

	/**
	 * A method that sets enemy initial location and boost using Random class.
	 * It's also notify View about this by listeners
	 */
	@Override
	public void setOi(ObjectInfo oi) {
		this.oi = oi;
		this.oi.setLocation(new Point((int) (Math.random() * 855) + 1, 0));
		boost = ((Math.random() * 1.1) + 0.9);
		EventData eventData = new EventData(SenderType.ENEMY, EventType.INITIALIZE, this.oi);
		ls.notify(eventData);
	}

	/**
	 * A method that set Enemy status - dead.It's also notify notify View about
	 * this by listeners
	 */
	public void setDead() {
		setRun(false);
		EventData eventData = new EventData(SenderType.ENEMY, EventType.DIE, this.oi);
		enemyListener.notify(eventData);
	}

	/**
	 * A getter for the dy field
	 * 
	 * @return dy {@link model.Enemy#dy}
	 */
	public double getDy() {
		return dy;
	}

	/**
	 * A setter for the dy field
	 * 
	 * @param dy
	 *            {@link model.Enemy#dy}
	 */
	public void setDy(double dy) {
		this.dy = dy;
	}

	/**
	 * A method that makes Enemy object moving with the boost. It's also notify
	 * notify View about this by listeners
	 */
	public void move() {
		Point location = this.oi.getLocation();
		location.y += dy * boost;
		EventData eventData = new EventData(SenderType.ENEMY, EventType.MOVE, this.oi);
		enemyListener.notify(eventData);
	}

	/**
	 * An method (implemented by Runnable interface) that initialize render of
	 * enemy object, keeping it movable and set it's dead when it's necessary
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
	 * A method that adds listener to Enemy object {@link listeners.Listener}
	 * 
	 * @param e
	 *            an object of Listener class
	 */
	public boolean addListener(Listener e) {
		return enemyListener.add(e);
	}

	/**
	 * A method that check condition (relationed to the thread) if thread still
	 * running
	 * 
	 * @return run {@link model.Enemy#run}
	 */
	public boolean isRun() {
		return run;
	}

	/**
	 * A setter for the run field
	 * 
	 * @param run
	 *            {@link model.Enemy#run}
	 */
	public void setRun(boolean run) {
		this.run = run;
	}

	/**
	 * A setter for the die field
	 * 
	 * @param dy
	 *            {@link model.Enemy#die}
	 */
	public void setDie() {
		this.die = true;
	}

}
