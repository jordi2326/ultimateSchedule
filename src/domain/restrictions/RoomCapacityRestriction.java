package domain.restrictions;

import domain.Group;
import domain.Restriction;
import domain.Room;
import domain.Schedule;
import domain.Timeframe;

public class RoomCapacityRestriction extends Restriction{
	public RoomCapacityRestriction() {
		super(false); //negotiable
	}
	
	@Override
	public boolean validate(Group group, Room room, Timeframe timeFrame, Schedule schedule) {
		return group.getNumOfPeople() <= room.getCapacity();
	}

	
}
