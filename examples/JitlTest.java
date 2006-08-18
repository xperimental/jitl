
import java.util.Calendar;



import net.sourceforge.jitl.DayPrayers;
import net.sourceforge.jitl.Jitl;
import net.sourceforge.jitl.Method;
import net.sourceforge.jitl.Prayer;

import net.sourceforge.jitl.astro.Dms;
import net.sourceforge.jitl.astro.Location;
import net.sourceforge.jitl.astro.SimpleDate;


public class JitlTest {

    public static void main(String args[]) {
    	Dms qibla;
        
    	SimpleDate today = new SimpleDate(30, 10, 1982);
        
        //Date today = new Date();
        Prayer imsaak ;
        Prayer nextImsaak ;
        Prayer nextFajr;
      
        /* auto fill the method structure. Have a look at prayer.h for a
         * list of supported methods */
        
        Method m = Method.MUSLIM_LEAGUE.copy();
        m.setRound(0);
        Jitl itl = new Jitl(new Location(24.4833, 54.35, 4, 0 ), m);        
        itl.setCalendar(Calendar.getInstance());
        
        //Jitl itl = new Jitl(new Location(21.423333, 39.823333, 1, 1 ), Method.MUSLIM_LEAGUE);
        
        /* Call the main function to fill the Prayer times array of
         * structures */
        DayPrayers ptList = itl.getPrayerTimes (today);

        /* Call functions for other prayer times and qibla */
        imsaak = itl.getImsaak (today);
        nextFajr = itl.getNextDayFajr (today);
        nextImsaak = itl.getNextDayImsaak (today);
        qibla = itl.getNorthQibla();

        System.out.print("\nQibla\t=" + qibla + "\n");
        System.out.print(ptList);
        /*
        Iterator iterator = ptList.iterator();
        Prayer p = null;
        while(iterator.hasNext()) {
            p = (Prayer)iterator.next();
        	System.out.println(p);
        }
        */
        System.out.println("Today's Imsaak: " + imsaak);
        System.out.println("Tomorrow's Fajr: " + nextFajr);
        System.out.println("Tomorrow's Imsaak: " + nextImsaak);
        //System.out.println(Utils.PI + "\n");
        //System.out.println(Math.PI + "\n");
        
    }

}
