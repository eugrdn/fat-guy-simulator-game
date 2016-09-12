/**
 * @author Rodin E.V.
 */
package runner;

import controller.Controller;
import model.Model;
import view.View;

public class Runner {

	public static void main(String[] args) {
		Model model = new Model();
		Controller controller = new Controller(model);
		@SuppressWarnings("unused")
		View view = new View(controller);
		model.startGame();
	}
}
