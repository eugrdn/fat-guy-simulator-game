package listeners;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import controller.Controller;

public class KeyInputHandler extends KeyAdapter {

	private Controller controller;

	public KeyInputHandler(Controller controller) {
		super();
		this.controller = controller;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			controller.moveObjectRound();
		}
	}

}
