package domain.classes.restrictions;

public class SpecificDayOrHourRestriction extends UnaryRestriction{
	private Integer day;
	private Integer hour;
	
	public SpecificDayOrHourRestriction(Integer day, Integer hour) {
		super(true); //negotiable
		this.day = day;
		this.hour = hour;
	}
	
	public String toString() {
		return SpecificDayOrHourRestriction.class.toString() + "-" + day + "-" + hour;
	}
	
	@Override
	public boolean validate(Integer day, Integer hour, Integer duration) {
		if (this.day == null) {
			return (hour > this.hour || hour+duration <= this.hour);
		}
		else if (this.hour == null) {
			return day != this.day;
		}
		else return (day != this.day) || (hour > this.hour || hour+duration <= this.hour);
	}

}
