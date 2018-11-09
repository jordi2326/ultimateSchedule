package domain.restrictions;

import domain.Group;
import domain.Restriction;
import domain.Room;
import domain.Schedule;

public abstract class NaryRestriction extends Restriction {
	
	public abstract boolean validate(Group group, Room room, Schedule schedule);
	
	public NaryRestriction(boolean negotiable) {
		super(negotiable);
	}	
}
