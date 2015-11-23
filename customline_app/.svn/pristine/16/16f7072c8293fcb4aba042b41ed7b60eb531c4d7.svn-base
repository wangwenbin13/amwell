package com.pig84.ab.vo;

import org.apache.commons.lang3.math.NumberUtils;

/**
 * Coordinate
 * 
 * @author GuoLin
 *
 */
public class Coord {

	private static final double EARTH_RADIUS = 6378137;

	private final double lng;

	private final double lat;

	public Coord(double lng, double lat) {
		this.lng = lng;
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public double getLat() {
		return lat;
	}
	
	public Coord add(Size size) {
		return new Coord(lng + size.getWidth(), lat + size.getHeight());
	}

	/**
	 * Distance to another coordinate.
	 * @param coord Coordinate to measure
	 * @return Distance between two coordinates
	 */
	public double distance(Coord coord) {
		double rad1 = rad(this.lat);
		double rad2 = rad(coord.lat);
		double a = rad1 - rad2;
		double b = rad(this.lng) - rad(coord.lng);
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
				+ Math.cos(rad1) * Math.cos(rad2)
				* Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		s = Math.round(s * 10000) / 10000;
		return s;
	}

	/**
	 * Find a rectangle which around the point with specified radius.
	 * <p>
	 * The circumference of the earth is 24,901 miles. 24,901/360 = 69.17 miles / degree</p>
	 * <p>
	 * <a href="http://snipperize.todayclose.com/snippet/php/SQL-Query-to-Find-All-Retailers-Within-a-Given-Radius-of-a-Latitude-and-Longitude--65095/">Further details</a></p> 
	 * @param radius Radius to find, unit: meter
	 * @return A rectangle
	 */
	public Rect aroundRect(int radius) {
        double degree = (24901 * 1609) / 360.0;
        double raidusMile = radius;

		double dpmLat = 1 / degree;
        double radiusLat = dpmLat * raidusMile;
        double mpdLng = degree * Math.cos(lat * (Math.PI / 180));
        double dpmLng = 1 / mpdLng;
        double radiusLng = dpmLng * raidusMile;
        return new Rect(lng - radiusLng, lat - radiusLat, 
        				lng + radiusLng, lat + radiusLat);
	}

	public static Coord valueOf(String lng, String lat) {
		if (!NumberUtils.isNumber(lng) || !NumberUtils.isNumber(lat)) {
			return null;
		}
		return new Coord(NumberUtils.toDouble(lng), NumberUtils.toDouble(lat));
	}
	
	private static double rad(double d) {
		return d * Math.PI / 180.0;
	}

}
