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
    private int isExtreme;  /* Extreme calculation switch. The 'getPrayerTimes'
                       function sets this switch to 1 to indicate that this
                       particular prayer time has been calculated through
                       extreme latitude methods and NOT by conventional
                       means of calculation. */
	
    public Prayer(int hour, int minute, int second, int isExtreme) {
		super();
		// TODO Auto-generated constructor stub
		this.hour = hour;
		this.minute = minute;
		this.second = second;
		this.isExtreme = isExtreme;
	}
	public Prayer() {
		
	}
    public Prayer copy() {
		return new Prayer(hour, minute, second, isExtreme);		
	}
    public int getHour() {
		return hour;
	}
	public void setHour(int hour) {
		this.hour = hour;
	}
	public int getIsExtreme() {
		return isExtreme;
	}
	public void setIsExtreme(int isExtreme) {
		this.isExtreme = isExtreme;
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
