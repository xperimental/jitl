package net.sourceforge.jitl;

import java.util.Iterator;

public class DayPrayers {
	private Prayer[] prayers = new Prayer[6];
	
	
	public DayPrayers() {
		for(int i=0; i<6; i++) {
			prayers[i] = new Prayer();
		}
	}
	
	public void setAllExtreme(int b) {
		for(int i=0; i<6; i++) {
			prayers[i].setIsExtreme(b);
		}
	}
	
	public Prayer fajr() {
		return prayers[0];
	}
	
	public Prayer shuruq() {
		return prayers[1];
	}
	
	public Prayer thuhr() {
		return prayers[2];		
	}
	
	public Prayer assr() {
		return prayers[3];
	}

	public Prayer maghrib() {
		return prayers[4];
	}
	
	public Prayer ishaa() {
		return prayers[5];
	}
	
	Prayer[] getPrayers() {
		return prayers;
	}
	
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
