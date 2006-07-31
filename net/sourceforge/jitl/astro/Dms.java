package net.sourceforge.jitl.astro;

/**
 * this class encapsulates a number represented in DMS format
 */
public class Dms {

	private int degree;
	private int minute;
	private double second;
	
	public int getDegree() {
		return degree;
	}

	public int getMinute() {
		return minute;
	}

	public double getSecond() {
		return second;
	}

	public Dms(int degree, int minute, double second){
		this.degree = degree;
		this.minute = minute;
		this.second = second;		
	}
	
	public Dms(double decimal) {
	    double tempmin;
	    double v;
	    v = Math.floor(decimal);
	    degree = (int)v;
	    tempmin = (decimal - v) * 60.0;
	    
	    v = Math.floor(tempmin);
	    minute = (int)v;
	    second = (tempmin - v) * 60.0;

	}
		
	public double getDecimalValue(Direction dir) {
	    double sum = degree + ((minute/60.0)+(second/3600.0));
	    if (dir == Direction.WEST || dir == Direction.SOUTH) {
	        return sum * (-1.0);
	    }
	    return sum;	
	}

	public String toString() {
		return degree + "°" + minute + "'" + second + "''";
	}

}
