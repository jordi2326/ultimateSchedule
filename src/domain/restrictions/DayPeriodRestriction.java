package domain.restrictions;

import domain.Group;
import domain.Group.DayPeriod;
import domain.Room;
import domain.Schedule;

public class DayPeriodRestriction extends UnaryRestriction{
	private int midDay;
	private Group.DayPeriod dayPeriod;
	
	public DayPeriodRestriction(int midDay, Group.DayPeriod dayPeriod) {
		super(false); //negotiable
		this.midDay = midDay;
		this.dayPeriod = dayPeriod;
	}
	
	@Override
	public boolean validate(Integer day, Integer hour) {
		if (dayPeriod == Group.DayPeriod.AFTERNOON) {
			return hour <= midDay;
		}
		else if (dayPeriod == Group.DayPeriod.MORNING) {
			return hour > midDay;
		}
		else return true;
	}

}
