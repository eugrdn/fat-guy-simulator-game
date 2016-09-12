package listeners;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import controller.Controller;

public class MouseInputHandler extends MouseAdapter {

	private Controller controller;

	public MouseInputHandler(Controller controller) {
		super();
		this.controller = controller;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			controller.moveObjectRound();
		}
	}

}
