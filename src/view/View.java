 package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import controller.Controller;
import listeners.*;
import model.*;
import resources.Resourcer;

/**
 * A class that generates an output presentation to the user based on changes in
 * the Model {@link controller.Contrller} {@link model.Model}
 * 
 * @author Rodin E.V.
 * @version 1.0
 */
@SuppressWarnings("serial")
public class View extends JFrame implements Listener {
	/**
	 * A string field that is a window title
	 */
	public static final String WINDOW_TITLE = "Fat Guy Simulator";
	/**
	 * A numeric constant field that appoint width of Gamefield view in the
	 * frame {@link model.GameField}
	 */
	public static final int GAMEFIRLD_WIDTH = 900;
	/**
	 * A numeric constant field that appoint height of Gamefield view in the
	 * frame {@link model.GameField}
	 */
	public static final int GAMEFIRLD_HEIGHT = 566;
	/**
	 * A Controller object {@link controller.Controller}
	 */
	private Controller controller;
	/**
	 * A JFrame object that is the Main Frame of this java project
	 * {@link javax.swing.JFrame} {@link view.View#WINDOW_TITLE}
	 */
	private JFrame frame = new JFrame(WINDOW_TITLE);
	/**
	 * A JPanel object that appoint's view of the GameField
	 * {@link javax.swing.JPanel} {@link model.GameField}
	 */
	private JPanel gameField = new DrawPanel(Bufferizer.getImage(Resourcer
			.getString("files.image.gamefield")));
	/**
	 * A JPanel object that appoint's view of the Character
	 * {@link javax.swing.JPanel} {@link model.Character}
	 */
	private JPanel character = new DrawPanel(Bufferizer.getImage(Resourcer
			.getString("files.image.character")));
	/**
	 * A JLabel object that appoint's view of the game statistic: score and
	 * character health {@link javax.swing.JLabel} {@link model.Statistic}
	 */
	private JLabel sc;
	/**
	 * A Icon that appoint's view for the message about end of the game
	 */
	private ImageIcon die = new ImageIcon(Resourcer.getString("files.image.dead"));

	/**
	 * A constructer. Here is occur determining main frame parameters, occure
	 * adding frame components, occur adding key & mouse listeners to frame
	 * 
	 * @param controller
	 *            {@link view.View#controller}
	 */
	public View(Controller controller) {
		this.controller = controller;
		this.controller.addListener(this);
		frame.setIconImage(Bufferizer.getImage(Resourcer.getString("files.image.frame")));
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		JMenuBar faq = new JMenuBar();
		frame.getContentPane().add(faq, BorderLayout.NORTH);
		JMenu gameMenu = new JMenu("Game");
		JMenuItem exitItem = new JMenuItem("Exit");
		exitItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		JMenuItem helpItem = new JMenuItem("Help&Documentation");
		gameMenu.add(helpItem);
		gameMenu.add(exitItem);
		faq.add(gameMenu);
		frame.setJMenuBar(faq);
		frame.setResizable(false);
		sc = new JLabel();
		frame.getContentPane().add(sc, BorderLayout.SOUTH);
		frame.add(gameField);
		gameField.add(character);
		frame.setPreferredSize(new Dimension(GAMEFIRLD_WIDTH, GAMEFIRLD_HEIGHT));
		frame.setVisible(true);
		frame.pack();
		frame.setLocationRelativeTo(null);

		frame.addKeyListener(new KeyInputHandler(controller));
		frame.addMouseListener(new MouseInputHandler(controller));
	}

	/**
	 * A method that catch events that done by senders, and execute certain
	 * actions provided by game description (MVC-pattern}
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

		if (sender == SenderType.GAME_FIELD && event == EventType.INITIALIZE) {
			initializeGameField((ObjectInfo) data);
		}
		if (sender == SenderType.FRIEND && event == EventType.ADD_FRIEND) {
			initializeFriendPanel(data);
		}
		if (sender == SenderType.ENEMY && event == EventType.ADD_ENEMY) {
			initializeEnemyPanel(data);
		}
		if (sender == SenderType.CHARACTER && event == EventType.DIE) {
			gameEnd();
		}
		if (sender == SenderType.CHARACTER && event == EventType.MOVE) {
			initializeCharacter((ObjectInfo) data);
			performStatistic();
		}
	}

	/**
	 * A method that executes outputing statistic on the main frame via Runnable
	 * interface by determined JLabel object {@link view.View#sc}
	 * {@link model.Statist}
	 * 
	 * @param sc
	 *            {@link view.View#sc}
	 */
	public void performStatistic() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				sc.setText(String.format(Resourcer.getString("view.stats"), "", "Score:",
						Integer.toString(Model.score), "Health:", Integer.toString(Model.hp)));
			}
		});
	}

	/**
	 * A method that use controller to sets the game end and stops the view of
	 * the character on the main frame. It also plays final game song and opens
	 * new Message Dialog where keeping the statistic about the game
	 * {@link model.Statist} {@link view.View#controller}
	 * {@link view.View#finalSoundplaying()}
	 */
	private void gameEnd() {
		controller.stopCharacter();
		finalSoundplaying();
		character.getParent().remove(character);
		JOptionPane.showMessageDialog(this, "YOUR SCORE: " + Integer.toString(Model.score),
				"Game Over", JOptionPane.INFORMATION_MESSAGE, die);
		System.exit(ABORT);
	}

	/**
	 * A method that starts Thread to play final game sound then stops this
	 * thread. Also it catchs different exceptions which may perform when the
	 * thread running
	 */
	private void finalSoundplaying() {
		try {
			File soundFile = new File(Resourcer.getString("files.music.gameover"));
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundFile);
			Clip clip = AudioSystem.getClip();
			clip.open(ais);
			clip.setFramePosition(0);
			clip.start();
			Thread.sleep(clip.getMicrosecondLength() / 1000);
			clip.stop();
			clip.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (UnsupportedAudioFileException exc) {
			exc.printStackTrace();
		} catch (LineUnavailableException exc) {
			exc.printStackTrace();
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		} catch (IllegalStateException ise) {
			ise.printStackTrace();
		}
	}

	/**
	 * A method that visualize GameField panel on the main frame
	 * 
	 * @param data
	 *            an object that contains dimension of the game object and it's
	 *            location {@link model.ObjectInfo}
	 */
	private void initializeGameField(ObjectInfo data) {
		gameField.setSize(data.getDimension());
		gameField.setLayout(null);
	}

	/**
	 * A method that visualize Friend panel on the game field. Also occur adding
	 * listener to friend object for the friend panel
	 * 
	 * @param data
	 *            an object that contains dimension of the game object and it's
	 *            location {@link model.ObjectInfo}
	 */
	private void initializeFriendPanel(Object data) {
		Friend friend = (Friend) data;
		FriendTexturePainter ft = new FriendTexturePainter(friend);
		friend.addListener(ft);
		ft.setVisible(true);
		gameField.add(ft);
		ft.setLayout(null);
	}

	/**
	 * A method that visualize Enemy panel on the game field. Also occur adding
	 * listener to enemy object for the enemy panel
	 * 
	 * @param data
	 *            an object that contains dimension of the game object and it's
	 *            location {@link model.ObjectInfo}
	 */
	private void initializeEnemyPanel(Object data) {
		Enemy enemy = (Enemy) data;
		EnemyTexturePainter et = new EnemyTexturePainter(enemy);
		enemy.addListener(et);
		et.setVisible(true);
		gameField.add(et);
		et.setLayout(null);
	}

	/**
	 * A method that initializes character object for it's visualizating on game
	 * field in future {@link view.View#character}
	 * 
	 * @param data
	 *            an object that contains dimension of the game object and it's
	 *            location {@link model.ObjectInfo}
	 */
	private void initializeCharacter(ObjectInfo data) {
		character.setSize(data.getDimension());
		character.setLocation(data.getLocation());
		character.setLayout(null);
	}
}
