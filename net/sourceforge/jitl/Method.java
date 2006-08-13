package net.sourceforge.jitl;

import net.sourceforge.jitl.astro.Utils;

public class Method {
    private double fajrAng;     /* Fajr angle */
    private double ishaaAng;    /* Ishaa angle */
    private double imsaakAng;   /* The angle difference between Imsaak and Fajr (default is 1.5)*/
    private int fajrInv;        /* Fajr Interval is the amount of minutes between Fajr and Shurooq (0 if not used) */
    private int ishaaInv;       /* Ishaa Interval is the amount if minutes between Ishaa and Maghrib (0 if not used) */
    private int imsaakInv;      /* Imsaak Interval is the amount of minutes between Imsaak and Fajr. The default is 10 minutes before Fajr if Fajr Interval is set */
    private int round;          /* Method used for rounding seconds:
									0: No Rounding. "Prayer.seconds" is set to the
									 amount of computed seconds.
									1: Normal Rounding. If seconds are equal to
									 30 or above, add 1 minute. Sets
									 "Prayer.seconds" to zero.
									2: Special Rounding. Similar to normal rounding
									 but we always round down for Shurooq and
									 Imsaak times. (default)
									3: Aggressive Rounding. Similar to Special
									 Rounding but we add 1 minute if the seconds
									 value are equal to 1 second or more.  */
    private int mathhab;        /* Assr prayer shadow ratio:
    								1: Shaf'i (default)
    								2: Hanafi 
    							*/
    private double nearestLat;  /* Latitude Used for the 'Nearest Latitude' extreme methods. The default is 48.5 */
    private int extremeLatitude;/* Extreme latitude calculation method (see below) */
    private int offset;         /* Enable Offsets switch (set this to 1 to
                           activate). This option allows you to add or
                           subtract any amount of minutes to the daily
                           computed prayer times based on values (in
                           minutes) for each prayer in the offList array */     
/* TODO For Example: If you want to add 30 seconds to
                           Maghrib and subtract 2 minutes from Ishaa:
                                offset = 1
                                offList[4] = 0.5
                                offList[5] = -2
                           ..and than call getPrayerTimes as usual. */
	
    private double fajrOffset;
    private double shurooqOffset;
    private double thuhrOffset;        
    private double assrOffset;
    private double maghribOffset;    
    private double ishaaOffset;   
    
    
    
    public Method() {
    }
    
    public Method(double fajrAng, double ishaaAng, double imsaakAng, int fajrInv, int ishaaInv, int imsaakInv, int round, int mathhab, double nearestLat, int extreme, int offset,
    		double fajrOffset, double shurooqOffset, double thuhrOffset, double assrOffset, double maghribOffset, double ishaaOffset) {
		this.fajrAng = fajrAng;
		this.ishaaAng = ishaaAng;
		this.imsaakAng = imsaakAng;
		this.fajrInv = fajrInv;
		this.ishaaInv = ishaaInv;
		this.imsaakInv = imsaakInv;
		this.round = round;
		this.mathhab = mathhab;
		this.nearestLat = nearestLat;
		this.extremeLatitude = extreme;
		this.offset = offset;

		this.fajrOffset = fajrOffset;
		this.shurooqOffset = shurooqOffset;
		this.thuhrOffset = thuhrOffset;        
		this.assrOffset = assrOffset;
		this.maghribOffset = maghribOffset;    
		this.ishaaOffset = ishaaOffset;
		
	}
    
    public Method copy() {
    	return new Method( fajrAng, ishaaAng, imsaakAng, fajrInv, ishaaInv, imsaakInv, round, mathhab, nearestLat,
    			extremeLatitude, offset, fajrOffset, shurooqOffset, thuhrOffset, assrOffset, maghribOffset, ishaaOffset);    	
    }
    
	public int getExtremeLatitude() {
		return extremeLatitude;
	}
	public void setExtremeLatitude(int extreme) {
		this.extremeLatitude = extreme;
	}
	public double getFajrAng() {
		return fajrAng;
	}
	public void setFajrAng(double fajrAng) {
		this.fajrAng = fajrAng;
	}
	public int getFajrInv() {
		return fajrInv;
	}
	public void setFajrInv(int fajrInv) {
		this.fajrInv = fajrInv;
	}
	public double getImsaakAng() {
		return imsaakAng;
	}
	public void setImsaakAng(double imsaakAng) {
		this.imsaakAng = imsaakAng;
	}
	public int getImsaakInv() {
		return imsaakInv;
	}
	public void setImsaakInv(int imsaakInv) {
		this.imsaakInv = imsaakInv;
	}
	public double getIshaaAng() {
		return ishaaAng;
	}
	public void setIshaaAng(double ishaaAng) {
		this.ishaaAng = ishaaAng;
	}
	public int getIshaaInv() {
		return ishaaInv;
	}
	public void setIshaaInv(int ishaaInv) {
		this.ishaaInv = ishaaInv;
	}
	public int getMathhab() {
		return mathhab;
	}
	public void setMathhab(int mathhab) {
		this.mathhab = mathhab;
	}
	public double getNearestLat() {
		return nearestLat;
	}
	public void setNearestLat(double nearestLat) {
		this.nearestLat = nearestLat;
	}

	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	public int getRound() {
		return round;
	}
	public void setRound(int round) {
		this.round = round;
	}

	
	/*
	fajrAng, ishaaAng, imsaakAng, fajrInv = 0, ishaaInv = 0, imsaakInv = 0, round = 2, mathhab = 1,
	nearestLat =  Utils.DEF_NEAREST_LATITUDE , extreme = 5, offset = 0, offList = null
	*/
	
	public static final Method NONE = new Method(	0.0, 0.0, 0, 0, 0, 0, 2, 1, Utils.DEF_NEAREST_LATITUDE , 5, 0, 0, 0, 0, 0, 0, 0);
	public static final Method EGYPT_SURVEY = new Method(20, 18, 0, 0, 0, 0, 2, 1, Utils.DEF_NEAREST_LATITUDE , 5, 0, 0, 0, 0, 0, 0, 0);
	public static final Method KARACHI_SHAF = new Method(18, 18, 0, 0, 0, 0, 2, 1, Utils.DEF_NEAREST_LATITUDE , 5, 0, 0, 0, 0, 0, 0, 0); 
    public static final Method KARACHI_HANAF = new Method(18, 18, 0, 0, 0, 0, 2, 2, Utils.DEF_NEAREST_LATITUDE , 5, 0, 0, 0, 0, 0, 0, 0);
    public static final Method NORTH_AMERICA = new Method(15, 15, 0, 0, 0, 0, 2, 1, Utils.DEF_NEAREST_LATITUDE , 5, 0, 0, 0, 0, 0, 0, 0); 
    public static final Method MUSLIM_LEAGUE =  new Method(18, 17, 0, 0, 0, 0, 2, 1, Utils.DEF_NEAREST_LATITUDE , 5, 0, 0, 0, 0, 0, 0, 0);
    public static final Method UMM_ALQURRA = new Method(19, 0, 0, 0, 90, 0, 2, 1, Utils.DEF_NEAREST_LATITUDE , 5, 0, 0, 0, 0, 0, 0, 0);  
    public static final Method FIXED_ISHAA = new Method(19.5, 0, 0, 0, 90, 0, 2, 1, Utils.DEF_NEAREST_LATITUDE , 5, 0, 0, 0, 0, 0, 0, 0);



	public double getAssrOffset() {
		return assrOffset;
	}

	public void setAssrOffset(double assrOffset) {
		this.assrOffset = assrOffset;
	}

	public double getFajrOffset() {
		return fajrOffset;
	}

	public void setFajrOffset(double fajrOffset) {
		this.fajrOffset = fajrOffset;
	}

	public double getIshaaOffset() {
		return ishaaOffset;
	}

	public void setIshaaOffset(double ishaaOffset) {
		this.ishaaOffset = ishaaOffset;
	}

	public double getMaghribOffset() {
		return maghribOffset;
	}

	public void setMaghribOffset(double maghribOffset) {
		this.maghribOffset = maghribOffset;
	}

	public double getShurooqOffset() {
		return shurooqOffset;
	}

	public void setShurooqOffset(double shurooqOffset) {
		this.shurooqOffset = shurooqOffset;
	}

	public double getThuhrOffset() {
		return thuhrOffset;
	}

	public void setThuhrOffset(double thuhrOffset) {
		this.thuhrOffset = thuhrOffset;
	}
	
	public double getOffset(PrayerTime prayer) {
		if(prayer == PrayerTime.FAJR) return getFajrOffset();
		if(prayer == PrayerTime.SHUROOQ) return getShurooqOffset();
		if(prayer == PrayerTime.THUHR) return getThuhrOffset();
		if(prayer == PrayerTime.ASSR) return getAssrOffset();
		if(prayer == PrayerTime.MAGHRIB) return getMaghribOffset();
		if(prayer == PrayerTime.ISHAA) return getIshaaOffset();
		return 0;
	}

}
