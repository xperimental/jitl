package net.sourceforge.jitl;

public class ExtremeLatitude {
	
	private ExtremeLatitude() {
		
	}
	
    /**
     * 0:  none. if unable to calculate, leave as 99:99
     */
	public static final ExtremeLatitude NONE_EX = new ExtremeLatitude();
    
    /**
     * 1: Nearest Latitude: All prayers Always
     */	
	public static final ExtremeLatitude LAT_ALL = new ExtremeLatitude();
	  
    /**
     * 2:  Nearest Latitude: Fajr Ishaa Always
     */
	public static final ExtremeLatitude LAT_ALWAYS = new ExtremeLatitude();
	
	/**
	 * 3:  Nearest Latitude: Fajr Ishaa if invalid 
	 */
	public static final ExtremeLatitude LAT_INVALID  = new ExtremeLatitude();
	
	/**
	 * 4:  Nearest Good Day: All prayers Always
	 */	
	public static final ExtremeLatitude GOOD_ALL = new ExtremeLatitude();
	
	/**
	 * 5:  Nearest Good Day: Fajr Ishaa if invalid (default)
	 */
	public static final ExtremeLatitude GOOD_INVALID = new ExtremeLatitude();
	
	/**
	 * 6:  1/7th of Night: Fajr Ishaa Always
	 */
	public static final ExtremeLatitude SEVEN_NIGHT_ALWAYS = new ExtremeLatitude();

	/**
	 * 7:  1/7th of Night: Fajr Ishaa if invalid
	 */
	public static final ExtremeLatitude SEVEN_NIGHT_INVALID = new ExtremeLatitude();
	
	/**
	 * 8:  1/7th of Day: Fajr Ishaa Always
	 */
	public static final ExtremeLatitude SEVEN_DAY_ALWAYS = new ExtremeLatitude();
	
	/**
	 * 9:  1/7th of Day: Fajr Ishaa if invalid
	 */
	public static final ExtremeLatitude SEVEN_DAY_INVALID = new ExtremeLatitude();
	
	/**
	 * 10: Half of the Night: Fajr Ishaa Always
	 */
	public static final ExtremeLatitude HALF_ALWAYS = new ExtremeLatitude();
	
	/**
	 * 11: Half of the Night: Fajr Ishaa if invalid 
	 */	
	public static final ExtremeLatitude HALF_INVALID = new ExtremeLatitude();
	
	/**
	 * 12: Minutes from Shorooq/Maghrib: Fajr Ishaa Always (e.g. Maghrib=Ishaa)
	 */
	public static final ExtremeLatitude MIN_ALWAYS = new ExtremeLatitude();

	/**
	 * 13: Minutes from Shorooq/Maghrib: Fajr Ishaa If invalid
	 */
	public static final ExtremeLatitude MIN_INVALID = new ExtremeLatitude();
	
	/**
	 * Nearest Good Day: Different good days for Fajr and Ishaa (Not
	 * implemented)
	 */
	public static final ExtremeLatitude GOOD_DIF = new ExtremeLatitude();


}
