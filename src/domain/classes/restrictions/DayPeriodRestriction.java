package domain.classes.restrictions;

import domain.classes.Group;

public class DayPeriodRestriction extends UnaryRestriction{
	private Integer midDay;
	private Group.DayPeriod dayPeriod;
	
	public DayPeriodRestriction(Integer midDay, Group.DayPeriod dayPeriod) {
		super(true); //negotiable
		this.midDay = midDay;
		this.dayPeriod = dayPeriod;
	}
	
	@Override
	public boolean validate(Integer day, Integer hour, Integer duration) {
		if ((dayPeriod.equals(Group.DayPeriod.MORNING)) && (hour + duration > midDay)) {
			return false;
		}
		if ((dayPeriod.equals(Group.DayPeriod.AFTERNOON)) && (hour < midDay || hour+duration > 11)) {
			return false;
		}
		return true;
	}
}
