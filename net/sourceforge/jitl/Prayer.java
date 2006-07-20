package net.sourceforge.jitl;

public class Prayer {
	
	public final static int FAJR = 0;
	public final static int SHUROOQ = 1;
	public final static int THUHR = 2;
	public final static int ASSR = 3;
	public final static int MAGHRIB = 4;
	public final static int ISHAA = 5;
	public final static int IMSAAK = 6;
	public final static int NEXTFAJR = 7;

	
	
	private int hour;       /* prayer time hour */
    private int minute;     /* prayer time minute */
    private int second;     /* prayer time second */
    private boolean extreme;  /* Extreme calculation switch. The 'getPrayerTimes'
                       function sets this switch to 1 to indicate that this
                       particular prayer time has been calculated through
                       extreme latitude methods and NOT by conventional
                       means of calculation. */
	
    public Prayer(int hour, int minute, int second, boolean extreme) {
		super();
		this.hour = hour;
		this.minute = minute;
		this.second = second;
		this.extreme = extreme;
	}
	public Prayer() {
		
	}
    public Prayer copy() {
		return new Prayer(hour, minute, second, extreme);		
	}
    public int getHour() {
		return hour;
	}
	public void setHour(int hour) {
		this.hour = hour;
	}
	public boolean isExtreme() {
		return extreme;
	}
	public void setExtreme(boolean extreme) {
		this.extreme = extreme;
	}
	public int getMinute() {
		return minute;
	}
	public void setMinute(int minute) {
		this.minute = minute;
	}
	public int getSecond() {
		return second;
	}
	public void setSecond(int second) {
		this.second = second;
	} 

}
