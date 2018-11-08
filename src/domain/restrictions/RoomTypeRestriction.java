package domain.restrictions;

import domain.Group;
import domain.Group.Type;
import domain.Restriction;
import domain.Room;
import domain.Schedule;
import domain.Timeframe;

public class RoomTypeRestriction extends Restriction{
	public RoomTypeRestriction() {
		super(true); //negotiable
	}
	
	@Override
	public boolean validate(Group group, Room room, Timeframe timeFrame, Schedule schedule) {
		return room.hasComputers() || group.getType()!=Type.LABORATORY;
		//TODO make each group have a list of valid room types, instead of hasComputers?
	}

	
}
