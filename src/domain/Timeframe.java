package domain;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
public class Timeframe {
	
	private DayOfWeek dayOfWeek;
	private LocalTime time;
	
	public Timeframe(DayOfWeek dayOfWeek, LocalTime time) {
		this.dayOfWeek = dayOfWeek;
		this.time = time;
	}
	
	public Timeframe(DayOfWeek dayOfWeek, String s_time) {
		this.dayOfWeek = dayOfWeek;
		this.time = LocalTime.parse(s_time);
	}
	
	/**
	 * @return the dayOfWeek
	 */
	public DayOfWeek getDayOfWeek() {
		return dayOfWeek;
	}
	
	/**
	 * @return the time
	 */
	public LocalTime getTime() {
		return time;
	}
	
	public String timeToString() {
		return time.format(DateTimeFormatter.ofPattern("HH:mm"));
	}
	
	@Override
	public String toString() {
		return dayOfWeek+ " - " +this.timeToString();
	}
	
	@Override    
    public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		
		Timeframe tf = (Timeframe) o;        
        if (!dayOfWeek.equals(tf.dayOfWeek)) return false;
        if (!time.equals(tf.time)) return false;        
        return true; 
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(dayOfWeek, time);
	}
	
}