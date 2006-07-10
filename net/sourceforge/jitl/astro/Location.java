package net.sourceforge.jitl.astro;

public class Location {
    
	private	double degreeLong;  /* Longitude in decimal degree. */
	private double degreeLat;   /* Latitude in decimal degree. */
	private double gmtDiff;     /* GMT difference at regular time. */
	private int dst;            /* Daylight savings time switch (0 if not used).
                           Setting this to 1 should add 1 hour to all the
                           calculated prayer times */
	private double seaLevel;    /* Height above Sea level in meters */
	private double pressure;    /* Atmospheric pressure in millibars (the
                           astronomical standard value is 1010) */
	private double temperature; /* Temperature in Celsius degree (the astronomical
                           standard value is 10) */
	public double getDegreeLat() {
		return degreeLat;
	}
	public void setDegreeLat(double degreeLat) {
		this.degreeLat = degreeLat;
	}
	public double getDegreeLong() {
		return degreeLong;
	}
	public void setDegreeLong(double degreeLong) {
		this.degreeLong = degreeLong;
	}
	public int getDst() {
		return dst;
	}
	public void setDst(int dst) {
		this.dst = dst;
	}
	public double getGmtDiff() {
		return gmtDiff;
	}
	public void setGmtDiff(double gmtDiff) {
		this.gmtDiff = gmtDiff;
	}
	public double getPressure() {
		return pressure;
	}
	public void setPressure(double pressure) {
		this.pressure = pressure;
	}
	public double getSeaLevel() {
		return seaLevel;
	}
	public void setSeaLevel(double seaLevel) {
		this.seaLevel = seaLevel;
	}
	public double getTemperature() {
		return temperature;
	}
	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}
}

	
	
	