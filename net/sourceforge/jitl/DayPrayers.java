package net.sourceforge.jitl;

public class DayPrayers {
	private Prayer[] prayers = new Prayer[6];
	
	Prayer getFajr() {
		return prayers[0];
	}
	
	Prayer getShuruq() {
		return prayers[1];
	}
	
	Prayer getThuhr() {
		return prayers[2];		
	}
	
	Prayer getAsr() {
		return prayers[3];
	}

	Prayer getMaghrib() {
		return prayers[4];
	}
	
	Prayer getIshaa() {
		return prayers[5];
	}
}
