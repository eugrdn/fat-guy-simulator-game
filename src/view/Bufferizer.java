package view;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Bufferizer {

	private static Image image = null;
	private static BufferedImage bufferImage = null;

	public static Image getImage(String source) {
		File imageFile = new File(source);
		try {
			bufferImage = ImageIO.read(imageFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		image = bufferImage;
		return image;
	}
}