import java.util.GregorianCalendar;

import net.sourceforge.jitl.DayPrayers;
import net.sourceforge.jitl.Jitl;
import net.sourceforge.jitl.Method;
import net.sourceforge.jitl.Prayer;
import net.sourceforge.jitl.Rounding;
import net.sourceforge.jitl.astro.Location;

public class PrayersTodayRiyadh {

    /**
     * @param args
     */
    public static void main(String[] args) {
        Location location = new Location(24.6851, 46.718, 3, 0);

        Method calcMethod = Method.UMM_ALQURRA.copy();
        calcMethod.setRound(Rounding.NORMAL);

        GregorianCalendar date = new GregorianCalendar();

        Jitl calculator = new Jitl(location, calcMethod);
        DayPrayers prayerTimes = calculator.getPrayerTimes(date);

        for (Prayer p : prayerTimes.getPrayers()) {
            System.out.println(String.format("%02d:%02d:%02d", p.getHour(), p
                    .getMinute(), p.getSecond()));
        }
    }

}
