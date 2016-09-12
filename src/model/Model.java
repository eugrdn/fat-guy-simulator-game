package model;

import initialization.Initializator;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import listeners.*;
import audio.AudioPlayer;
import resources.Resourcer;

/**
 * A class that stores data that is retrieved according to commands from the
 * Controller and displayed in the View. {@link controller.Controller}
 * {@link view.View}
 * 
 * @author Rodin E.V.
 * @version 1.0
 */
public class Model implements Runnable {
	/**
	 * Numeric field that describes the number of initialized game objects
	 */
	private final static int NUMBER_OF_OBJECTS = 3;
	/**
	 * A boolean field that set the game mode (true/false)
	 */
	private boolean running;
	/**
	 * A boolean switch (true/false)
	 */
	private boolean flag = false;
	/**
	 * An object of the GameField class, that initialize other game objects on
	 * itself {@link model.GameField}
	 */
	private GameField gameField;
	/**
	 * An object of the Friend class that makes a character "bigger"
	 * {@link model.Friend}
	 */
	private Friend friend;
	/**
	 * An object of the Character class which user is playing for
	 * {@link model.Character}
	 */
	private Character ch;
	/**
	 * An object of the Enemy class that hurts Character and take away Character
	 * life's {@link model.Enemy}
	 */
	private Enemy enemy;
	/**
	 * An object of the Statist class, that count Character health's and score
	 * {@link model.Statist}
	 */
	private Statist statistic = new Statist();
	/**
	 * Numeric field that count Character score-points {@link model.Statist}
	 */
	public static int score;
	/**
	 * Numeric field that count Character health-points {@link model.Statist}
	 */
	public static int hp;
	/**
	 * An object of AudioPlayer class that describe what sound to play when
	 * Character eat Friend object {@link audio.AudioPlayer}
	 */
	private AudioPlayer ate = new AudioPlayer(Resourcer.getString("files.music.eat"));
	/**
	 * An object of AudioPlayer class that describe what sound to play when
	 * Character hurts by Enemy object {@link audio.AudioPlayer}
	 */
	private AudioPlayer hit = new AudioPlayer(Resourcer.getString("files.music.hurt"));
	/**
	 * A object of the ListenerList class {@link listeners.ListenerList}
	 */
	private ListenerList ls = new ListenerList();
	/**
	 * A list of the Enemy class objects {@link model.Enemy}
	 */
	private List<Enemy> enemys = new ArrayList<Enemy>();
	/**
	 * A list of the Friend class objects {@link model.Friend}
	 */
	private List<Friend> friends = new ArrayList<Friend>();

	/**
	 * An empty constructor
	 */
	public Model() {
	}

	/**
	 * A method that adds listener to Model object {@link listeners.Listener}
	 * 
	 * @param listener
	 */
	public void addListener(Listener listener) {
		this.ls.add(listener);
	}

	/**
	 * A method that run a game process. Thread uses.
	 */
	public void startGame() {
		running = true;
		new Thread(this).start();
	}

	/**
	 * An method (implemented by Runnable interface) that initialize main
	 * objects of the game: GameField, Character, Enemy's and Friend's and also
	 * game updates here
	 */
	@Override
	public void run() {
		initializeGameField();
		initializeCharacter();
		for (int i = 0; i < NUMBER_OF_OBJECTS; i++) {
			initializeEnemy();
			initializeFriend();
		}
		while (running) {
			gameUpdate();
		}
	}

	/**
	 * A method that includes methods of game unity which allow to update the
	 * game
	 */
	public synchronized void gameUpdate() {
		isStillAlive();
		friendControl();
		enemyControl();
	}

	/**
	 * A method that control all events which may happen with Enemy object
	 */
	private void enemyControl() {
		for (Enemy enemy : enemys) {
			if (isCatched(ch, enemy) && !flag) {
				flag = true;
				soundHit();
				statistic.setHealth(statistic.getHealth());
				enemy.setDie();
				enemys.remove(enemy);
				initializeEnemy();
				break;
			}
			flag = true;
			if (outField(enemy)) {
				enemy.setDie();
				enemys.remove(enemy);
				initializeEnemy();
				break;
			}
			flag = false;
		}
	}

	/**
	 * A method that control all events which may happen with Friend object
	 */
	private void friendControl() {
		for (Friend friend : friends) {
			if (isCatched(ch, friend) && !flag) {
				soundAte();
				statistic.setScore(statistic.getScore());
				score = statistic.getScore();
				setCharacterBigger();
				friend.setDie();
				friends.remove(friend);
				initializeFriend();
				break;
			}
			flag = true;
			if (outField(friend)) {
				friend.setDie();
				friends.remove(friend);
				initializeFriend();
				break;
			}
			flag = false;
		}
	}

	/**
	 * A method that start sound playing (open AudioPlayer Thread)
	 * {@link model.Model#ate} {@link audio.AudioPlayer}
	 */
	private void soundAte() {
		ate.startAudioThread();
	}

	/**
	 * A method that start sound playing (open AudioPlayer Thread)
	 * {@link model.Model#hit} {@link audio.AudioPlayer}
	 */
	private void soundHit() {
		hit.startAudioThread();
	}

	/**
	 * A method that change size parameters of Character objects
	 * {@link model.Model#ch}
	 */
	private void setCharacterBigger() {
		ch.getObjectInfo().setLocation(
				new Point(ch.getObjectInfo().getLocation().x - 7,
						ch.getObjectInfo().getLocation().y - 4));
		ch.getObjectInfo().setDimension(
				new Dimension(ch.getObjectInfo().getDimension().width + 7, ch.getObjectInfo()
						.getDimension().height + 4));
	}

	/**
	 * A method that control permissible number of the Characters health-points
	 * {@link model.Model#statistic}
	 */
	public void isStillAlive() {
		hp = statistic.getHealth();
		if (statistic.getHealth() == 0) {
			ch.setDead();
		}
	}

	/**
	 * A method that makes Character to go in the opposite direction
	 * {@link model.Model#ch}
	 */
	public void moveRound() {
		ch.setDx(-ch.getDx());
	}

	/**
	 * A method that stop Character thread and it moving
	 */
	public void stopCharacter() {
		ch.setRun(false);
	}

	/**
	 * A method that checking situation if Character object intersects
	 * GameObject (Enemy/Friend object) by their coordenates
	 * 
	 * @param ch
	 *            {@link model.Model#ch}
	 * @param g
	 *            An object of parent class GameObject of Character,Enemy and
	 *            Friend classes {@link model.GameObject}
	 * @return true if objects are intersect, false in other cases
	 */
	public synchronized boolean isCatched(Character ch, GameObject g) {
		Rectangle rect1 = new Rectangle(ch.getObjectInfo().getLocation().x, ch.getObjectInfo()
				.getLocation().y, ch.getObjectInfo().getDimension().width, ch.getObjectInfo()
				.getDimension().height);
		Rectangle rect2 = new Rectangle(g.getOi().getLocation().x, g.getOi().getLocation().y, g
				.getOi().getDimension().width, g.getOi().getDimension().height);
		if (rect1.intersects(rect2)) {
			return true;
		}
		return false;
	}

	/**
	 * A method that check condition if the object out of bounds (GameField
	 * coordinates) {@link model.GameFeield}
	 * 
	 * @param g
	 *            An object of parent class GameObject of Character,Enemy and
	 *            Friend classes {@link model.GameObject}
	 * @return true if object out false in other cases
	 */
	private boolean outField(GameObject g) {
		if (g.getOi().getLocation().y + g.getOi().getDimension().height >= gameField
				.getObjectInfo().getDimension().height) {
			return true;
		}
		return false;
	}

	/**
	 * A method that initializes initial parameters of GameField object
	 * {@link model.Model#gameField}
	 */
	private void initializeGameField() {
		this.gameField = new GameField(Initializator.createGameField(), this.ls);
	}

	/**
	 * A method that initializes initial parameters of Character object
	 * {@link model.Model#ch}
	 */
	private void initializeCharacter() {
		this.ch = new Character(this.ls, gameField, Initializator.createCharacter());
		Thread chThread = new Thread(ch);
		chThread.start();
	}

	/**
	 * A method that initializes initial parameters of Friend object and add
	 * this object to "friends" list. This method also notify about this.
	 * {@link model.Model#friend} {@link model.Model#friends}
	 */
	private synchronized void initializeFriend() {
		this.friend = new Friend(this.ls, gameField, Initializator.createFriend());
		this.friends.add(friend);
		Thread friendThread = new Thread(friend);
		friendThread.start();
		EventData eventData = new EventData(SenderType.FRIEND, EventType.ADD_FRIEND, this.friend);
		ls.notify(eventData);
	}

	/**
	 * A method that initializes initial parameters of Enemy object and add this
	 * object to "enemys" list. This method also notify about this.
	 * {@link model.Model#enemy} {@link model.Model#enemys}
	 */
	public synchronized void initializeEnemy() {
		this.enemy = new Enemy(this.ls, gameField, Initializator.createEnemy());
		this.enemys.add(enemy);
		Thread enemyThread = new Thread(enemy);
		enemyThread.start();
		EventData eventData = new EventData(SenderType.ENEMY, EventType.ADD_ENEMY, this.enemy);
		ls.notify(eventData);
	}
}