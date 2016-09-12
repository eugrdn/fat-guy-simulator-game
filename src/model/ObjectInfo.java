package model;

import java.awt.Dimension;
import java.awt.Point;

public class ObjectInfo {

	private Dimension dimension;
	private Point location;

	public ObjectInfo(Dimension dimension, Point location) {
		super();
		this.dimension = dimension;
		this.location = location;
	}

	public Dimension getDimension() {
		return dimension;
	}

	public void setDimension(Dimension dimension) {
		this.dimension = dimension;
	}

	public Point getLocation() {
		return location;
	}

	public void setLocation(Point location) {
		this.location = location;
	}

}
