package net.sourceforge.jitl;

import net.sourceforge.jitl.astro.Astro;
import net.sourceforge.jitl.astro.AstroLib;
import net.sourceforge.jitl.astro.Date;
import net.sourceforge.jitl.astro.Location;
import net.sourceforge.jitl.astro.Utils;

public class Itl {
	/* Defaults */


	static final int NONE_EX = 0;
	static final int LAT_ALL = 1;
	static final int LAT_ALWAYS = 2;
	static final int LAT_INVALID = 3;
	static final int GOOD_ALL = 4;
	static final int GOOD_INVALID = 5;
	static final int SEVEN_NIGHT_ALWAYS = 6;
	static final int SEVEN_NIGHT_INVALID = 7;
	static final int SEVEN_DAY_ALWAYS = 8;
	static final int SEVEN_DAY_INVALID = 9;
	static final int HALF_ALWAYS = 10;
	static final int HALF_INVALID = 11;
	static final int MIN_ALWAYS = 12;
	static final int MIN_INVALID = 13;
	static final int GOOD_DIF  = 14;
	
	  
	static final int NONE = 20;
	static final int EGYPT_SURVEY = 21;
	static final int KARACHI_SHAF = 22;
	static final int KARACHI_HANAF = 23;
	static final int NORTH_AMERICA = 24;
	static final int MUSLIM_LEAGUE = 25;
	static final int UMM_ALQURRA = 26;
	static final int FIXED_ISHAA  = 27;



	private Astro astroCache = new Astro(); /* This is Used for storing some formulae results between
	                   * multiple getPrayerTimes calls*/

	private Location loc;
	private Method conf;
	
	public Itl(Location loc, Method conf) {
		this.loc = loc;
		this.conf = conf;
	}
	public void setLocation(Location loc) {
		this.loc = loc;
	}
	
	public void setMethod(Method conf) {
		this.conf = conf; 		
	}
	
	public void getPrayerTimes (final Date date,  DayPrayers pt)
	{  
	    DayCouple dc;

	    dc = getDayInfo ( date, loc.getGmtDiff());
	    getPrayerTimesByDay(dc, pt, 0);
	}

	 
	void getPrayerTimesByDay (DayCouple dc, DayPrayers pt, int type) {		
		getPrayerTimesByDay(conf, dc, pt, type);
	}

	void getPrayerTimesByDay (Method conf, DayCouple dc, DayPrayers pt, int type)
	{
	    int i, invalid;
	    double th, sh, mg, fj, is, ar;
	    double lat, lon, dec;
	    double tempPrayer[] = new double[6];
	    Astro tAstro = new Astro();

	    lat = loc.getDegreeLat(); 
	    lon = loc.getDegreeLong();
	    invalid = 0;

	    /* Start by filling the tAstro structure with the appropriate astronomical
	     * values for this day. We also pass the cache structure to update and check
	     * if the actual values are already available. */
	    AstroLib.getAstroValuesByDay(dc.getJulianDay(), loc, astroCache, tAstro);
	    dec = Utils.DEG_TO_RAD(tAstro.getDec()[1]);

	    /* Get Prayer Times formulae results for this day of year and this
	     * location. The results are NOT the actual prayer times */
	    fj   = getFajIsh (lat, dec, conf.getFajrAng());
	    sh   = getShoMag (loc, tAstro, Prayer.SHUROOQ);
	    th   = getThuhr (lon, tAstro);
	    ar   = getAssr (lat, dec, conf.getMathhab());
	    mg   = getShoMag (loc, tAstro, Prayer.MAGHRIB);
	    is   = getFajIsh (lat, dec, conf.getIshaaAng());
	    
	    /* Calculate all prayer times as Base-10 numbers in Normal circumstances */ 
	    /* Fajr */   
	    if (fj == 99) {
	        tempPrayer[0] = 99;
	        invalid = 1;
	    } 
	    else tempPrayer[0] = th - fj;
	     
	    if (sh == 99)
	        invalid = 1;

	    tempPrayer[1] = sh;
	    tempPrayer[2] = th;
	    tempPrayer[3] = th + ar;
	    tempPrayer[4] = mg;

	    if (mg == 99)
	        invalid = 1;

	    /* Ishaa */
	    if (is == 99) {
	        tempPrayer[5] = 99;
	        invalid = 1;
	    } 
	    else tempPrayer[5] = th + is;
	    
	    
	    /* Calculate all prayer times as Base-10 numbers in Extreme Latitudes (if
	     * needed) */
	  
	    /* Reset status of extreme switches */
	    pt.setAllExtreme(0);
	     
	     
	    if ((conf.getExtreme() != NONE_EX) && !((conf.getExtreme() == GOOD_INVALID || 
	                                         conf.getExtreme() == LAT_INVALID ||
	                                         conf.getExtreme() == SEVEN_NIGHT_INVALID ||
	                                         conf.getExtreme() == SEVEN_DAY_INVALID ||
	                                         conf.getExtreme() == HALF_INVALID) &&
	                                        (invalid == 0)))
	    {
	        double exdecPrev, exdecNext;
	        double exTh=99, exFj=99, exIs=99, exAr=99, exIm=99, exSh=99, exMg=99;
	        double portion = 0;
	        double nGoodDay = 0;
	        int exinterval = 0;
	        Location exLoc = loc; //TODO
	        Astro exAstroPrev;
	        Astro exAstroNext;

	        switch(conf.getExtreme())
	        {
	        /* Nearest Latitude (Method.nearestLat) */
	        case LAT_ALL:
	        case LAT_ALWAYS:
	        case LAT_INVALID:

	            /* xxxthamer: we cannot compute this when interval is set because
	             * angle==0 . Only the if-invalid methods would work */
	            exLoc.setDegreeLat(conf.getNearestLat());
	            exFj = getFajIsh(conf.getNearestLat(), dec, conf.getFajrAng());
	            exIm = getFajIsh(conf.getNearestLat(), dec, conf.getImsaakAng());
	            exIs = getFajIsh(conf.getNearestLat(), dec, conf.getIshaaAng());
	            exAr = getAssr(conf.getNearestLat(), dec, conf.getMathhab());
	            exSh = getShoMag (exLoc, tAstro, Prayer.SHUROOQ);
	            exMg = getShoMag (exLoc, tAstro, Prayer.MAGHRIB);


	            switch(conf.getExtreme())
	            {
	            case LAT_ALL:
	                tempPrayer[0] = th - exFj;
	                tempPrayer[1] = exSh;
	                tempPrayer[3] = th + exAr;
	                tempPrayer[4] = exMg;
	                tempPrayer[5] = th + exIs;
	                pt.setAllExtreme(1);
	                break;
	        
	            case LAT_ALWAYS:
	                tempPrayer[0] = th - exFj;
	                tempPrayer[5] = th + exIs;
	                pt.fajr().setIsExtreme(1);
	                pt.ishaa().setIsExtreme(1);
	                break;
	        
	            case LAT_INVALID:
	                if (tempPrayer[0] == 99) {
	                    tempPrayer[0] = th - exFj;
	                    pt.fajr().setIsExtreme(1);
	                }
	                if (tempPrayer[5] == 99) {
	                    tempPrayer[5] = th + exIs;
	                    pt.ishaa().setIsExtreme(1);
	                }
	                break;
	            }
	            break;

	           
	        /* Nearest Good Day */
	        case GOOD_ALL:
	        case GOOD_INVALID:
	        case GOOD_DIF:
	            
	            exAstroPrev = astroCache;
	            exAstroNext = astroCache;

	            /* Start by getting last or next nearest Good Day */
	            for(i=0; i <= dc.getLastDay(); i++)
	            {

	                /* last closest day */
	                nGoodDay = dc.getJulianDay() - i;
	                AstroLib.getAstroValuesByDay(nGoodDay, loc, exAstroPrev, tAstro);
	                exdecPrev = Utils.DEG_TO_RAD(tAstro.getDec()[1]);
	                exFj = getFajIsh(lat, exdecPrev, conf.getFajrAng());

	                
	                if (exFj != 99)
	                {
	                    exIs = getFajIsh(lat, exdecPrev, conf.getIshaaAng());
	                    if (exIs != 99)
	                    {
	                        exTh = getThuhr (lon, tAstro);
	                        exSh = getShoMag (loc, tAstro, Prayer.SHUROOQ);
	                        exMg = getShoMag (loc, tAstro, Prayer.MAGHRIB);
	                        exAr = getAssr (lat, exdecPrev, conf.getMathhab());
	                        break;
	                    }
	                }

	                /* Next closest day */
	                nGoodDay = dc.getJulianDay() + i;
	                AstroLib.getAstroValuesByDay(nGoodDay, loc, exAstroNext, tAstro);
	                exdecNext = Utils.DEG_TO_RAD(tAstro.getDec()[1]);
	                exFj = getFajIsh(lat, exdecNext, conf.getFajrAng());
	                if (exFj != 99)
	                {
	                    exIs = getFajIsh(lat, exdecNext, conf.getIshaaAng());
	                    if (exIs != 99)
	                    {
	                        exTh = getThuhr (lon, tAstro);
	                        exSh = getShoMag (loc, tAstro, Prayer.SHUROOQ);
	                        exMg = getShoMag (loc, tAstro, Prayer.MAGHRIB);
	                        exAr = getAssr (lat, exdecNext, conf.getMathhab());
	                        break;
	                    }
	                }
	            }

	            switch(conf.getExtreme())
	            {
	            case GOOD_ALL:
	                tempPrayer[0] = exTh - exFj;
	                tempPrayer[1] = exSh;
	                tempPrayer[2] = exTh;
	                tempPrayer[3] = exTh + exAr;
	                tempPrayer[4] = exMg;
	                tempPrayer[5] = exTh + exIs;
	                pt.setAllExtreme(1);

	                break;
	            case GOOD_INVALID:
	                if (tempPrayer[0] == 99) {
	                    tempPrayer[0] = exTh - exFj;
	                    pt.fajr().setIsExtreme(1);
	                }
	                if (tempPrayer[5] == 99) {
	                    tempPrayer[5] = exTh + exIs;
	                    pt.ishaa().setIsExtreme(1);
	                }
	                break;

	            case GOOD_DIF:
	                /* Nearest Good Day: Different good days for Fajr and Ishaa (Not
	                 * implemented) */
	                break;
	            }
	            break;
	      
	        case SEVEN_NIGHT_ALWAYS:
	        case SEVEN_NIGHT_INVALID:
	        case SEVEN_DAY_ALWAYS:
	        case SEVEN_DAY_INVALID:
	        case HALF_ALWAYS:
	        case HALF_INVALID:

	            /* xxxthamer: For clarity, we may need to move the HALF_* methods
	             * into their own separate case statement. */    
	            switch(conf.getExtreme())
	            {
	            case SEVEN_NIGHT_ALWAYS:
	            case SEVEN_NIGHT_INVALID:
	                portion = (24 - (tempPrayer[4] - tempPrayer[1])) * (1/7.0);
	                break;
	            case SEVEN_DAY_ALWAYS:
	            case SEVEN_DAY_INVALID:
	                portion = (tempPrayer[4] - tempPrayer[1]) * (1/7.0);
	                break;
	            case HALF_ALWAYS:
	            case HALF_INVALID:
	                portion = (24 - tempPrayer[4] - tempPrayer[1]) * (1/2.0);
	                break;
	            }


	            if (conf.getExtreme() == SEVEN_NIGHT_INVALID ||
	                conf.getExtreme() == SEVEN_DAY_INVALID ||
	                conf.getExtreme() == HALF_INVALID)
	            {
	                if (tempPrayer[0] == 99) {
	                    if  (conf.getExtreme() == HALF_INVALID)
	                        tempPrayer[0] =  portion - (conf.getFajrInv() / 60.0);
	                    else tempPrayer[0] = tempPrayer[1] - portion;
	                    pt.fajr().setIsExtreme(1);
	                }
	                if (tempPrayer[5] == 99) {
	                    if  (conf.getExtreme() == HALF_INVALID)
	                        tempPrayer[5] = portion + (conf.getIshaaInv() / 60.0) ;
	                    else tempPrayer[5] = tempPrayer[4] + portion;
	                    pt.ishaa().setIsExtreme(1);
	                }
	            } else { /* for the always methods */
	                
	                if  (conf.getExtreme() == HALF_ALWAYS) {
	                    tempPrayer[0] = portion - (conf.getFajrInv() / 60.0);
	                    tempPrayer[5] = portion + (conf.getIshaaInv() / 60.0) ;
	                }

	                else {
	                    tempPrayer[0] = tempPrayer[1] - portion;
	                    tempPrayer[5] = tempPrayer[4] + portion;
	                }
	                pt.fajr().setIsExtreme (1);
	                pt.ishaa().setIsExtreme (1);
	            }
	            break;

	        case MIN_ALWAYS:
	            /* Do nothing here because this is implemented through fajrInv and
	             * ishaaInv structure members */
	            tempPrayer[0] = tempPrayer[1];
	            tempPrayer[5] = tempPrayer[4];
	            pt.fajr().setIsExtreme(1);
	            pt.ishaa().setIsExtreme(1);
	            break;
	      
	        case MIN_INVALID:
	            if (tempPrayer[0] == 99) {
	                exinterval = (int)((double)conf.getFajrInv() / 60.0);
	                tempPrayer[0] = tempPrayer[1] - exinterval;
	                pt.fajr().setIsExtreme(1);
	            }
	            if (tempPrayer[5] == 99) {
	                exinterval = (int)((double)conf.getIshaaInv() / 60.0);
	                tempPrayer[5] = tempPrayer[4] + exinterval;
	                pt.ishaa().setIsExtreme(1);
	            }
	            break;
	        } /* end switch */
	    } /* end extreme */

	    
	    /* Apply intervals if set */
	    if (conf.getExtreme() != MIN_INVALID && 
	        conf.getExtreme() != HALF_INVALID &&
	        conf.getExtreme() != HALF_ALWAYS) {
	        if (conf.getFajrInv() != 0)
	            tempPrayer[0] = tempPrayer[1] - (conf.getFajrInv() / 60.0);    
	        if (conf.getIshaaInv() != 0)
	            tempPrayer[5] = tempPrayer[4] + (conf.getIshaaInv() / 60.0); 
	    }

	 
	    /* Final Step: Fill the Prayer array by doing decimal degree to
	     * Prayer structure conversion*/
	    if (type == Prayer.IMSAAK || type == Prayer.NEXTFAJR)
	        base6hm(tempPrayer[0], conf, pt.fajr(), type);
	    else {
	        Prayer[] pArray = pt.getPrayers();
	    	for (i=0; i<6; i++) {
	            base6hm(tempPrayer[i], conf, pArray[i], i);
	    	}
	    }

	}



	void base6hm(double bs, Method conf, Prayer pt, int type)	{
	    double min, sec;

	    if (bs == 99)
	    {
	        pt.setHour(99);
	        pt.setMinute(99);
	        pt.setSecond(0);
	        return;
	    }
	        
	    /* Add offsets */
	    if (conf.getOffset() == 1) {
	        if (type == Prayer.IMSAAK || type == Prayer.NEXTFAJR)
	            bs += (conf.getOffList()[0] / 60.0);
	        else  bs += (conf.getOffList()[type] / 60.0);
	    }


	    /* Fix after minus offsets before midnight */
	    if (bs < 0) {
	        while (bs < 0)
	            bs = 24 + bs;
	    }

	    min = (bs - Math.floor(bs)) * 60;
	    sec = (min - Math.floor(min)) * 60;


	    /* Add rounding minutes */
	    if (conf.getRound() == 1)
	    {
	        if (sec >= Utils.DEFAULT_ROUND_SEC)
	            bs += 1/60.0;
	        /* compute again */
	        min = (bs - Math.floor(bs)) * 60;
	        sec = 0;

	    } else if (conf.getRound() == 2 || conf.getRound() == 3)
	    {
	        switch(type)
	        {
	        case Prayer.FAJR :
	        case Prayer.THUHR:
	        case Prayer.ASSR:
	        case Prayer.MAGHRIB:
	        case Prayer.ISHAA:
	        case Prayer.NEXTFAJR:
	            
	            if (conf.getRound() == 2) {
	                if (sec >= Utils.DEFAULT_ROUND_SEC) {
	                    bs += 1/60.0;
	                    min = (bs - Math.floor(bs)) * 60;
	                }
	            } else if (conf.getRound() == 3)
	            {
	                if (sec >= Utils.AGGRESSIVE_ROUND_SEC) {
	                    bs += 1/60.0;
	                    min = (bs - Math.floor(bs)) * 60;
	                }
	            }
	            sec = 0;
	            break;

	        case Prayer.SHUROOQ:
	        case Prayer.IMSAAK:
	            sec = 0;
	            break;
	        }
	    }

	    /* Add daylight saving time and fix after midnight times */
	    bs += loc.getDst();
	    if (bs >= 24) {
	        bs = Math.IEEEremainder(bs, 24);
	    }
	          
	    pt.setHour((int)bs);
	    pt.setMinute((int)min);
	    pt.setSecond((int)sec);

	}

	public void getImsaak (Date date, Prayer pt)
	{

	    Method tmpConf;
	    DayCouple dc;
	    DayPrayers temp = new DayPrayers();

	    tmpConf = conf.copy();

	    if (conf.getFajrInv() != 0) { 
	        if (conf.getImsaakInv() == 0)
	            tmpConf.setFajrInv((int)(tmpConf.getFajrInv() + Utils.DEF_IMSAAK_INTERVAL));
	        else tmpConf.setFajrInv ((int)(tmpConf.getFajrInv()+conf.getImsaakInv()));
	        
	    } else if (conf.getImsaakInv() != 0) {
	        /* use an inv even if al-Fajr is computed (Indonesia?) */       
	        tmpConf.getOffList()[0] += (conf.getImsaakInv() * -1);
	        tmpConf.setOffset(1);
	    } else { 
	        tmpConf.setFajrAng(tmpConf.getFajrAng() + conf.getImsaakAng() );
	    }

	    dc = getDayInfo ( date, loc.getGmtDiff());
	    getPrayerTimesByDay( tmpConf, dc, temp, Prayer.IMSAAK);

	    /* xxxthamer: We probably need to check whether it's possible to compute
	     * Imsaak normally for some extreme methods first */
	    /* In case of an extreme Fajr time calculation use intervals for Imsaak and
	     * compute again */
	    if (temp.fajr().getIsExtreme() != 0)
	    {
	        tmpConf = conf.copy();
	        if ( conf.getImsaakInv() == 0)
	        {
	            tmpConf.getOffList()[0] -= Utils.DEF_IMSAAK_INTERVAL;
	            tmpConf.setOffset(1);
	        } else
	        {
	            tmpConf.getOffList()[0] -= conf.getImsaakInv();
	            tmpConf.setOffset(1);
	        }
	        getPrayerTimesByDay( tmpConf, dc, temp, Prayer.IMSAAK);
	    }
	    
	    pt = temp.fajr().copy();
	}

	public void getNextDayImsaak (Date date, 
	                       Prayer pt)
	{
	    /* Copy the date structure and increment for next day.*/
	    Prayer temppt = new Prayer();
	    Date tempd = date.copy(); 
	    tempd.setDay(tempd.getDay()+1);
	     
	    getImsaak (tempd, temppt);

	    pt = temppt.copy(); 

	}

	public void getNextDayFajr (Date date, 
	                     Prayer pt)
	{

	    DayPrayers temp = new DayPrayers();
	    DayCouple dc;

	    dc = getDayInfo ( date, loc.getGmtDiff());
	    dc.setJulianDay(dc.getJulianDay() + 1);
	    getPrayerTimesByDay(dc, temp, Prayer.NEXTFAJR);	 
	    pt = temp.fajr().copy(); 
	}


	static double getFajIsh(double Lat, double dec, double Ang)
	{

	    double part1 = Math.cos(Utils.DEG_TO_RAD(Lat)) * Math.cos(dec);
	    double part2 = -Math.sin(Utils.DEG_TO_RAD(Ang)) - Math.sin(Utils.DEG_TO_RAD(Lat))
	        * Math.sin(dec);
	    
	    double part3 = part2 / part1;
	    if ( part3 <= Utils.INVALID_TRIGGER) {
	        return 99;
	    }

	    return Utils.DEG_TO_10_BASE * Utils.RAD_TO_DEG (Math.acos(part3) );

	}


	static double getShoMag (Location loc, Astro astro, int type)
	{
	    double lhour, M, sidG, ra0 = astro.getRa()[0], ra2=astro.getRa()[2];
	    double A, B, H, sunAlt, R, tH;

	    double part1 = Math.sin (Utils.DEG_TO_RAD (loc.getDegreeLat())) * Math.sin (Utils.DEG_TO_RAD(astro.getDec()[1]));
	    double part2a =  Utils.CENTER_OF_SUN_ANGLE;
	    double part2 = Math.sin (Utils.DEG_TO_RAD (part2a)) - part1;
	    double part3 = Math.cos (Utils.DEG_TO_RAD (loc.getDegreeLat())) * Math.cos (Utils.DEG_TO_RAD(astro.getDec()[1]));

	    double part4 = part2 / part3;
	    
	    if (part4 <= -1 || part4 >= 1)
	        return 99;

	    lhour =  AstroLib.limitAngle180 (( Utils.RAD_TO_DEG (Math.acos (part4))));
	    M = ((astro.getRa()[1] - loc.getDegreeLong() - astro.getSid()[1]) / 360.0);

	    if (type ==  Prayer.SHUROOQ)
	        M = M - (lhour/360.0);
	    if (type == Prayer.MAGHRIB)
	        M = M + (lhour/360.0);
	        
	    M = AstroLib.limitAngle111(M);

	    sidG = AstroLib.limitAngle(astro.getSid()[1] + 360.985647 * M);

	    ra0 = astro.getRa()[0];
	    ra2 = astro.getRa()[2];

	    if (astro.getRa()[1] > 350 && astro.getRa()[2] < 10)
	        ra2 += 360;
	    if (astro.getRa()[0] > 350 && astro.getRa()[1] < 10)
	        ra0 = 0;

	    A = astro.getRa()[1] + (M * (( astro.getRa()[1] - ra0) +
	                             (ra2 - astro.getRa()[1] ) +
	                             (( ra2 - astro.getRa()[1] ) -
	                              ( astro.getRa()[1]  -  ra0)) * M) / 2.0 );

	    B = astro.getDec()[1] + (M * ((astro.getDec()[1] - astro.getDec()[0]) + 
	                              (astro.getDec()[2] - astro.getDec()[1]) + 
	                              ((astro.getDec()[2] - astro.getDec()[1]) -  
	                               (astro.getDec()[1] - astro.getDec()[0])) * M) / 2.0 );

	    H =  AstroLib.limitAngle180between(sidG +  loc.getDegreeLong() -  A);

	    tH =  H - Utils.RAD_TO_DEG(astro.getDra()[1]);

	    sunAlt = Utils.RAD_TO_DEG(Math.asin (  Math.sin(Utils.DEG_TO_RAD( loc.getDegreeLat())) * Math.sin(Utils.DEG_TO_RAD (B)) 
	                                + Math.cos(Utils.DEG_TO_RAD( loc.getDegreeLat())) * Math.cos(Utils.DEG_TO_RAD (B)) 
	                                * Math.cos(Utils.DEG_TO_RAD(tH)) ));

	    sunAlt += AstroLib.getRefraction(loc, sunAlt);
	    
	    R = (M + (( sunAlt - Utils.CENTER_OF_SUN_ANGLE+ (Utils.ALTITUDE_REFRACTION * 
	                                               Math.pow (loc.getSeaLevel(),0.5)))
	              /(360.0 * Math.cos(Utils.DEG_TO_RAD (B)) *  Math.cos(Utils.DEG_TO_RAD( loc.getDegreeLat())) * 
	                                               Math.sin(Utils.DEG_TO_RAD (tH)))));

	    return  (R * 24.0);

	}

	static double getThuhr(double lon, Astro astro)
	{

	    double M, sidG;
	    double ra0=astro.getRa()[0], ra2=astro.getRa()[2];
	    double A, H;

	    M = ((astro.getRa()[1] - lon - astro.getSid()[1]) / 360.0);
	    M = AstroLib.limitAngle111(M);
	    sidG =  astro.getSid()[1] + 360.985647 * M;

	    if (astro.getRa()[1] > 350 && astro.getRa()[2] < 10)
	        ra2 += 360;
	    if (astro.getRa()[0] > 350 && astro.getRa()[1] < 10)
	        ra0 = 0;

	    A = astro.getRa()[1] + (M * ((astro.getRa()[1] - ra0) 
	                             + ( ra2 - astro.getRa()[1]) + 
	                             (( ra2 - astro.getRa()[1]) -  
	                              (astro.getRa()[1] - ra0)) * M) / 2.0 );

	    H =  AstroLib.limitAngle180between(sidG + lon - A);

	    return  24.0 * (M - H/360.0);
	}

	static double getAssr(double Lat, double dec, int mathhab)
	{
	    double part1, part2, part3, part4;
	    
	    part1 = mathhab + Math.tan(Utils.DEG_TO_RAD(Lat) - dec);
	    if (part1 < 1 || Lat < 0)
	        part1 = mathhab - Math.tan(Utils.DEG_TO_RAD(Lat) - dec);

	    part2 = (Utils.PI/2.0) - Math.atan(part1);
	    part3 = Math.sin(part2) - Math.sin(Utils.DEG_TO_RAD(Lat)) * Math.sin(dec);
	    part4 = (part3 / (Math.cos(Utils.DEG_TO_RAD(Lat)) * Math.cos(dec)));

	/*  if (part4 > 1) */
	/*      return 99; */

	    return Utils.DEG_TO_10_BASE * Utils.RAD_TO_DEG (Math.acos(part4));
	}

	 
	static int getDayofYear(int year, int month, int day)
	{
	    int i;
	    int isLeap = (((year & 3) == 0) && ((year % 100) != 0 
	                                       || (year % 400) == 0) ) ? 1 : 0;
	  
	    char dayList[][] = {
	        {0,31,28,31,30,31,30,31,31,30,31,30,31},
	        {0,31,29,31,30,31,30,31,31,30,31,30,31}
	    };

	    for (i=1; i<month; i++)
	        day += dayList[isLeap][i];
	  
	    
	    return day;
	}

	static double dms2Decimal(int deg, int min, double sec, char dir)
	{
	    double sum = deg + ((min/60.0)+(sec/3600.0));
	    if (dir == 'S' || dir == 'W' || dir == 's' || dir == 'w')
	        return sum * (-1.0);
	    return sum;
	}

	static public void decimal2Dms(double decimal, int deg, int min, double sec)
	{
	    double tempmin, tempsec, n1, n2;
	    double v;
	    v = Math.floor(decimal);
	    n1 = decimal -v;
	    tempmin = v * 60.0;
	    
	    v = Math.floor(tempmin);
	    n2 = decimal -v;
	    tempsec = v * 60.0;

	    deg = (int)n1;
	    min = (int)n2;
	    sec = tempsec;

	}

	static DayCouple getDayInfo ( Date date, double gmt)
	{
	    int ld;
	    double jd;
	    ld = getDayofYear(date.getYear(), 12, 31);
	    jd = AstroLib.getJulianDay(date, gmt);
	    return new DayCouple(ld, jd);
	}


	/* Obtaining the direction of the shortest distance towards Qibla by uMath.sing the
	 * great circle formula */ 
	static public double getNorthQibla(Location loc)
	{
	    /* xxxthamer: reduce Utils.DEG_TO_RAD usage */
	    double num, denom;
	    num = Math.sin (Utils.DEG_TO_RAD (loc.getDegreeLong()) - Utils.DEG_TO_RAD (Utils.KAABA_LONG));
	    denom = (Math.cos (Utils.DEG_TO_RAD (loc.getDegreeLat())) * Math.tan (Utils.DEG_TO_RAD (Utils.KAABA_LAT))) -
	        (Math.sin (Utils.DEG_TO_RAD (loc.getDegreeLat())) * ((Math.cos ((Utils.DEG_TO_RAD (loc.getDegreeLong()) -
	                                                     Utils.DEG_TO_RAD(Utils.KAABA_LONG))))));
	    return Utils.RAD_TO_DEG (Math.atan2 (num, denom));

	}

	public double getNorthQibla()
	{
	    return getNorthQibla(loc);
	}
	
	
}
