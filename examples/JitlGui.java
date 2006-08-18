import java.util.Calendar;
import java.util.Date;

import net.sourceforge.jitl.DayPrayers;
import net.sourceforge.jitl.Jitl;
import net.sourceforge.jitl.Method;
import net.sourceforge.jitl.astro.Direction;
import net.sourceforge.jitl.astro.Dms;
import net.sourceforge.jitl.astro.Location;
import net.sourceforge.jitl.astro.SimpleDate;

import org.eclipse.swt.SWT;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

public class JitlGui {

	private static Combo combo;
	private static Text lon, lat, day, month, year, gmt, dst;
	private static Text prayerTime[];
	private static Dms qibla;
	private static Jitl itl;
	private static GC gc;
	private static Display display;
	
	public static void main(String[] args) {
		display = new Display();
		Shell shell = new Shell(display);
		//shell.setSize(200, 400);
		shell.setText("Jitl GUI example");

		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		shell.setLayout(gridLayout);

		/**
		 * method
		 */
		Label label1 = new Label(shell, SWT.NONE);
		label1.setText("Method :");
		combo = new Combo(shell, SWT.BORDER | SWT.READ_ONLY);
		combo.add("MUSLIM_LEAGUE");
		combo.add("EGYPT_SURVEY");
		combo.add("KARACHI_SHAF");
		combo.add("KARACHI_HANAF");
		combo.add("NORTH_AMERICA");
		combo.add("UMM_ALQURRA");
		combo.add("FIXED_ISHAA");
		combo.select(0);
		combo.pack();


		/**
		 * latitude
		 */
		label1 = new Label(shell, SWT.NONE);
		label1.setText("Latitude :");
		lat = new Text(shell, SWT.BORDER);
		lat.setText("34.86");
		lat.pack();

		/**
		 * longitude
		 */
		label1 = new Label(shell, SWT.NONE);
		label1.setText("Longitude :");
		lon = new Text(shell, SWT.BORDER);
		lon.setText("10.51");
		lon.pack();
		
		/**
		 * Gmt
		 */
		label1 = new Label(shell, SWT.NONE);
		label1.setText("Gmt diff :");
		gmt = new Text(shell, SWT.BORDER);
		gmt.setText("1");
		gmt.pack();

		/**
		 * DST
		 */
		SimpleDate today = new SimpleDate(new Date(), Calendar.getInstance());
		label1 = new Label(shell, SWT.NONE);
		label1.setText("DST :");
		dst = new Text(shell, SWT.BORDER);
		dst.setText("1");
		dst.pack();

		/**
		 * Day
		 */
		label1 = new Label(shell, SWT.NONE);
		label1.setText("Day :");
		day = new Text(shell, SWT.BORDER);
		day.setText(String.valueOf(today.getDay()));
		day.pack();

		/**
		 * Month
		 */
		label1 = new Label(shell, SWT.NONE);
		label1.setText("Month :");
		month = new Text(shell, SWT.BORDER);
		month.setText(String.valueOf(today.getMonth()));
		month.pack();

		/**
		 * Year
		 */
		label1 = new Label(shell, SWT.NONE);
		label1.setText("Year :");
		year = new Text(shell, SWT.BORDER);
		year.setText(String.valueOf(today.getYear()));
		year.pack();

		Button button1 = new Button(shell, SWT.NONE);
		button1.setText("Update");
	    button1.addSelectionListener(new SelectionListener() {
	        public void widgetSelected(SelectionEvent arg0) {
	        	update();	        	
	        }
	        public void widgetDefaultSelected(SelectionEvent arg0) {
	        }
	      });


		Button button2 = new Button(shell, SWT.NONE);
		button2.setText("  Quit  ");
	    button2.addSelectionListener(new SelectionListener() {
	        public void widgetSelected(SelectionEvent arg0) {
	        }
	        public void widgetDefaultSelected(SelectionEvent arg0) {
	        }
	      });

		String prayers[] = { "Fajr", "Shurouk", "Thuhr", "Assr", "Maghrib",
				"Ishaa" };
		prayerTime = new Text[6];

		for (int i = 0; i < 6; i++) {
			label1 = new Label(shell, SWT.NONE);
			label1.setText(prayers[i] + " :");
			prayerTime[i] = new Text(shell, SWT.READ_ONLY);
			prayerTime[i].setForeground(display.getSystemColor(SWT.COLOR_BLUE));
			prayerTime[i].setText("00:00:00");
			prayerTime[i].pack();
		}
		
		label1 = new Label(shell, SWT.NONE);
		label1.setText("Qibla :");
		
	    Canvas canvas = new Canvas(shell, SWT.NONE);
	    canvas.setSize(200,400);
	    canvas.setLocation(10,10);
	    gc = new GC(canvas);
	    shell.pack();
	    shell.open();
	    
	    
	    update();
	    


		
		shell.pack();
		shell.open();
		while (!shell.isDisposed())
			if (!display.readAndDispatch())
				display.sleep();

		gc.dispose();
		display.dispose();
	}

	private static void update() {
		Method methods[] = {Method.MUSLIM_LEAGUE, Method.EGYPT_SURVEY, Method.KARACHI_SHAF, Method.KARACHI_HANAF,
				Method.NORTH_AMERICA, Method.UMM_ALQURRA, Method.FIXED_ISHAA};
		
		/*
		itl = new Jitl(new 
					Location(Double.valueOf(lat.getText()).doubleValue(), 
							 Double.valueOf(lon.getText()).doubleValue(), 
							 Double.valueOf(gmt.getText()).intValue(), 
							 Double.valueOf(dst.getText()).intValue()),
					methods[combo.getSelectionIndex()]);
		*/
		
		
		Method m = methods[combo.getSelectionIndex()].copy();
		m.setRound(0);
		
		itl = new Jitl(new 
				Location(Double.valueOf(lat.getText()).doubleValue(), 
						 Double.valueOf(lon.getText()).doubleValue(), 
						 Double.valueOf(gmt.getText()).intValue(), 
						 Double.valueOf(dst.getText()).intValue()),
				m);

		DayPrayers ptList = itl.getPrayerTimes(new SimpleDate(
				Double.valueOf(day.getText()).intValue(), 
				Double.valueOf(month.getText()).intValue(), 
				Double.valueOf(year.getText()).intValue()));
		
		for(int i=0;i<6;i++) {
			prayerTime[i].setText(ptList.getPrayers()[i].toString());
		}

		gc.setForeground(display.getSystemColor(SWT.COLOR_RED));
		gc.fillRectangle(0,0,100,100);
		gc.setForeground(display.getSystemColor(SWT.COLOR_RED));
	    qibla = itl.getNorthQibla();
	    gc.drawOval(10,10,50,50);	    
	    gc.drawLine(10 + 25, 10+25, 10+25, 10);
	    
	    gc.setForeground(display.getSystemColor(SWT.COLOR_BLUE));
	    gc.drawLine(10 + 25, 10+25, (int)(35+ (25*Math.cos(2*3.1415/180*qibla.getDecimalValue(Direction.NORTH)))),(int)( 35 + (25*Math.sin(2*3.1415/180*qibla.getDecimalValue(Direction.NORTH)))) );
	    
	    
	    
		
	}
}
