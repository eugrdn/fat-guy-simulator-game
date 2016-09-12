package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.JPanel;

/**
 * A class that download different images and paint them on JLabel
 * 
 * @author Rodin E.V.
 * @version 1.0
 */
@SuppressWarnings("serial")
class DrawPanel extends JPanel {
	/**
	 * An object of Image class - future texture of the Game object
	 */
	private Image image;

	/**
	 * Constructor that creates object with image and sets its backgroud
	 * 
	 * @param image
	 *            a texture of the game object {@link view.DrawPanel#image}
	 */
	public DrawPanel(Image image) {
		super();
		this.setBackground(new Color(0, 0, 0, 0));
		this.image = image;
	}

	/**
	 * A getter for the Image object
	 * 
	 * @return image {@link view.DrawPanel#image}
	 */
	public Image getImage() {
		return image;
	}

	/**
	 * A setter for the Image object
	 * 
	 * @param image
	 *            {@link view.DrawPanel#image}
	 */
	public void setImage(Image image) {
		this.image = image;
	}

	/**
	 * An overrided method that paint a JPanel with downloaded image
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(image, 0, 0, getWidth(), getHeight(), null);
	}
}
