package model;

import java.awt.Dimension;
import java.awt.Point;
import listeners.*;

/**
 * A class that determines an character objects in the game. It's extends by the
 * GameObject class, that have main fields for each game object. User of the
 * game can controll only the Character. Character is a figure that moving only
 * horizontal direction. The task hero to catch as many friends as it can, to
 * reach more game points.
 * 
 * @author Rodin E.V.
 * @version 1.0
 */
public class Character extends GameObject {
	/**
	 * Numeric constant field that describes how fast Character object will
	 * moving
	 */
	private static final int SPEED = 5;
	/**
	 * Numeric field that describe offset axially X
	 * {@link model.Character#SPEED}
	 */
	private int dx = SPEED;
	/**
	 * A boolean switch for the running process forcing to move character object
	 * (true/false)
	 */
	private boolean run = true;

	/**
	 * A constructer that initializes Character objects using parent class
	 * fields
	 * 
	 * @param ls
	 *            ListenerList class object {@link listeners.ListenerList}
	 * @param gf
	 *            GameField class object {@link model.GameField}
	 * @param oi
	 *            ObjectInfo class object {@link model.ObjectInfo}
	 */
	public Character(ListenerList ls, GameField gameField, ObjectInfo objectInfo) {
		super(objectInfo, gameField, ls);
		this.setOi(objectInfo);
	}

	/**
	 * A getter for the oi field
	 * 
	 * @return oi {@link model.ObjectInfo}
	 */
	public ObjectInfo getObjectInfo() {
		return oi;
	}

	/**
	 * A method that create event about initialization and also notify View
	 * about this by listeners
	 */
	@Override
	public void setOi(ObjectInfo oi) {
		this.oi = oi;
		EventData eventData = new EventData(SenderType.CHARACTER, EventType.INITIALIZE, this.oi);
		ls.notify(eventData);
	}

	/**
	 * A method that set Character status - dead. It's also notify notify View
	 * about this by listeners
	 */
	public void setDead() {
		setRun(false);
		EventData eventData = new EventData(SenderType.CHARACTER, EventType.DIE, this.oi);
		ls.notify(eventData);
	}

	/**
	 * A getter for the dx field
	 * 
	 * @return dx {@link model.Character#dx}
	 */
	public int getDx() {
		return dx;
	}

	/**
	 * A setter for the dx field
	 * 
	 * @param dx
	 *            {@link model.Character#dx}
	 */
	public void setDx(int dx) {
		this.dx = dx;
	}

	/**
	 * A method that makes Character object moving with the boost. It's also
	 * notify notify View about this by listeners
	 */
	public void move() {
		Point location = this.oi.getLocation();
		Dimension dimension = this.oi.getDimension();
		Dimension gameFieldDimension = this.gf.getObjectInfo().getDimension();
		location.x += dx;
		if (location.x < 0) {
			location.x = 0;
			dx = -dx;
		}
		if (location.x + dimension.width >= gameFieldDimension.width) {
			location.x = gameFieldDimension.width - dimension.width;
			dx = -dx;
		}
		EventData eventData = new EventData(SenderType.CHARACTER, EventType.MOVE, this.oi);
		ls.notify(eventData);
	}

	/**
	 * An method (implemented by Runnable interface) that initialize render of
	 * character object, keeping it movable
	 */
	@Override
	public void run() {
		initializeFps();
		while (run) {
			setTimerFPS();
			gameRender();
			move();
		}
	}

	/**
	 * A method that check condition (relationed to the thread) if thread still
	 * running
	 * 
	 * @return run {@link model.Character#run}
	 */
	public boolean isRun() {
		return run;
	}

	/**
	 * A setter for the run field
	 * 
	 * @param run
	 *            {@link model.Character#run}
	 */
	public void setRun(boolean run) {
		this.run = run;
	}
}
