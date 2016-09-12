package audio;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioPlayer implements Runnable {

	private String source = "";
	private Thread audioThread = null;
	private Clip clip;
	private static final int SLEEP_TIME = 400;

	public AudioPlayer(String source) {
		super();
		this.source = source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public void startAudioThread() {
		this.audioThread = new Thread(this);
		audioThread.start();
	}

	@SuppressWarnings("deprecation")
	public void stopAudioThread() {
		clip.stop();
		clip.close();
		this.audioThread.stop();
	}

	public synchronized void audioRunner() {
		try {
			File soundFile = new File(source);
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundFile);
			clip = AudioSystem.getClip();
			clip.open(ais);
			clip.setFramePosition(0);
			clip.start();
			Thread.sleep(SLEEP_TIME);
			clip.stop();
			clip.close();
		} catch (IOException exc) {
			exc.printStackTrace();
		} catch (UnsupportedAudioFileException exc) {
			exc.printStackTrace();
		} catch (LineUnavailableException exc) {
			exc.printStackTrace();
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		} catch (IllegalStateException ise) {
			// TODO
		}
	}

	@Override
	public void run() {
		audioRunner();
	}

}
