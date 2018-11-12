package domain.classes.restrictions;

public class EspecificDayOrHourRestriction extends UnaryRestriction{
	private Integer day;
	private Integer hour;
	
	public EspecificDayOrHourRestriction(Integer day, Integer hour) {
		super(true); //negotiable
		this.day = day;
		this.hour = hour;
	}
	
	@Override
	public boolean validate(Integer day, Integer hour) {
		if (this.day == null) {
			return hour != this.hour;
		}
		else if (this.hour == null) {
			return day != this.day;
		}
		else return (day != this.day) && (hour != this.hour);
	}

}
