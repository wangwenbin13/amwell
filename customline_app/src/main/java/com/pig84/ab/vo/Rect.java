package com.pig84.ab.vo;

/**
 * Rectangle.
 * 
 * @author GuoLin
 *
 */
public class Rect {

	private final Coord origin;
	
	private final Size size;

	public Rect(double minLng, double minLat, double maxLng, double maxLat) {
		this.origin = new Coord(minLng, minLat);
		this.size = new Size(maxLng - minLng, maxLat - minLat);
	}
	
	public Rect(Coord origin, Size size) {
		this.origin = origin;
		this.size = size;
	}

	public Coord getOrigin() {
		return origin;
	}

	public Size getSize() {
		return size;
	}

	public Coord getRB() {
		return origin.add(size);
	}

	public Coord getLT() {
		return origin;
	}

	public Coord getLB() {
		return origin.add(new Size(0, size.getHeight()));
	}

	public Coord getRT() {
		return origin.add(new Size(size.getWidth(), 0));
	}

}
