package controller;

import listeners.Listener;
import model.Model;

/**
 * A class that send commands to the Model to update the Model's state (e.g.
 * editing a document). It can also send commands to its associated View to
 * change the View's presentation of the Model (e.g. by scrolling through a
 * document). {@link view.View} {@link model.Model}
 * 
 * @author Rodin E.V.
 * @version 1.0
 */
public class Controller {
	/**
	 * An objetct of Model class that need for the execute control on Model by
	 * Controller {@link model.Model}
	 */
	private Model model;

	/**
	 * Constructor
	 * 
	 * @param model
	 *            {@link controller.Controller#model}
	 */
	public Controller(Model model) {
		this.model = model;
	}

	/**
	 * A method that install a listener for Model object
	 * 
	 * @param listener
	 *            {@link listeners.Listener}
	 */
	public void addListener(Listener listener) {
		model.addListener(listener);
	}

	/**
	 * A method that inform Model to change movement direction for reverse
	 * {@link model.Model#moveRound()}
	 */
	public void moveObjectRound() {
		model.moveRound();
	}

	/**
	 * A method that inform Model to stop Character object
	 * {@link model.Model#stopCharacter()}
	 */
	public void stopCharacter() {
		model.stopCharacter();
	}

}
