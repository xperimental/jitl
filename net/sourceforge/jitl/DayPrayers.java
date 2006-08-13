package net.sourceforge.jitl;

import java.util.Iterator;

/**
 * Encapsulates the list of prayers time + shuruq time
 *
 */

public class DayPrayers {
	private Prayer[] prayers = new Prayer[6];

	public DayPrayers() {
		for(int i=0; i<6; i++) {
			prayers[i] = new Prayer();
		}
	}
	
	/**
	 * set all prayer calculation to extreme
	 * @param b
	 */
	void setAllExtreme(boolean b) {
		for(int i=0; i<6; i++) {
			prayers[i].setExtreme(b);
		}
	}
	
	/**
	 * 
	 * @return fajr prayer time
	 */
	public Prayer fajr() {
		return prayers[0];
	}
	
	/**
	 * 
	 * @return shuruq time
	 */
	public Prayer shuruq() {
		return prayers[1];
	}
	
	/**
	 * 
	 * @return thuhr prayer time
	 */
	public Prayer thuhr() {
		return prayers[2];		
	}
	
	/**
	 * 
	 * @return assr prayer time
	 */
	public Prayer assr() {
		return prayers[3];
	}

	/**
	 * 
	 * @return maghrib time
	 */
	public Prayer maghrib() {
		return prayers[4];
	}
	
	/**
	 * 
	 * @return ishaa time
	 */
	public Prayer ishaa() {
		return prayers[5];
	}
	
	/**
	 * 
	 * @return an array containing the list of prayer times including
	 * the shuruq. The size of the array is 6.
	 * @see #iterator()
	 */
	public Prayer[] getPrayers() {
		return prayers;
	}
	
	/**
	 * @return prayer times as a string. It contains 6 lines
	 */
	public String toString() {
		String ret = "";
		for(int i=0;i<6;i++){
			ret += prayers[i].toString() + "\n";
		}
		return ret;
	}
	
	/**
	 * 
	 * @return an iterator over the prayers
	 * @see #getPrayers()
	 */
	public Iterator iterator() {
		return new Iterator() {
			private int i = 0;
			public boolean hasNext() {
				if(i<6) return true;
				return false;
			}

			public Object next() {
				return prayers[i++];
			}

			public void remove() {
				if(i>0) {
					i--;
				}				
			}
			
		};
	}
}
