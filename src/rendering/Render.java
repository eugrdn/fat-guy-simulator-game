package rendering;

/**
 * A class that computates stable sleeping time for the abstract Thread
 * 
 * @author Rodin E.V.
 * @version 1.0
 */
public class Render {
	/**
	 * Numeric constant field that describes number of frames per second
	 */
	private final static int FPS = 30;
	/**
	 * Numeric field that describes number of millisecond per frame
	 */
	private double millsPerFps;
	/**
	 * Numeric field that describes the starting time of System working
	 */
	private long timerFPS;
	/**
	 * Numeric field that describes how the abstract Thread need to sleep
	 * {@link java.lang.Runnable}
	 */
	private int sleepTime;

	/**
	 * Constructor
	 */
	public Render() {
	}

	public void setTimerFPS() {
		this.timerFPS = System.nanoTime();
	}

	/**
	 * A method that initializate parametres
	 * 
	 * @param millsPerFps
	 *            {@link rendering.Render#millsPerFps}
	 * @param sleepTime
	 *            {@link rendering.Render#sleepTime}
	 */
	public void initializeFps() {
		millsPerFps = 1000 / FPS;
		sleepTime = 0;
	}

	/**
	 * A method that computates stable sleeping time for the abstract Thread
	 */
	public void gameRender() {
		timerFPS = (System.nanoTime() - timerFPS) / 1000000;
		if (millsPerFps > timerFPS) {
			sleepTime = (int) (millsPerFps - timerFPS);
		} else {
			sleepTime = 1;
		}
		try {
			Thread.sleep(sleepTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		timerFPS = 0;
		sleepTime = 1;
	}
}
