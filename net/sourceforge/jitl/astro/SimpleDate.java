package net.sourceforge.jitl.astro;

public class SimpleDate {
    int day;
    int month;
    int year;
	
    
    public SimpleDate(int day, int month, int year) {
		super();
		this.day = day;
		this.month = month;
		this.year = year;
	}
    
    public SimpleDate copy() {
    	return new SimpleDate(day, month, year);
    }
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}

    
}
