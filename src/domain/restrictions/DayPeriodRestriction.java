package domain.restrictions;

import domain.Group;
import domain.Group.DayPeriod;
import domain.Restriction;
import domain.Room;
import domain.Schedule;
import domain.Timeframe;

public class DayPeriodRestriction extends Restriction{
	private int midDay;
	
	public DayPeriodRestriction(int midDay) {
		super(true); //negotiable
		this.midDay = midDay;
	}
	
	@Override
	public boolean validate(Group group, Room room, Timeframe timeFrame, Schedule schedule) {
		return (group.getDayPeriod()==DayPeriod.INDIFERENT)
				|| (group.getDayPeriod()==DayPeriod.MORNING && timeFrame.getTime().getHour()<midDay)
				|| (group.getDayPeriod()==DayPeriod.AFTERNOON && timeFrame.getTime().getHour()>=midDay);
	}

}
