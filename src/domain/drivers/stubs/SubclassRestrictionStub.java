package domain.drivers.stubs;

import java.util.Random;

import domain.classes.Group;
import domain.classes.Restriction;
import domain.classes.Room;
import domain.classes.Schedule;

public class SubclassRestrictionStub extends Restriction{
	public SubclassRestrictionStub(boolean negotiable) {
		super(negotiable); //negotiable
	}
	
	@Override
	public boolean validate(Group group, Room room, Timeframe timeFrame, Schedule schedule) {
		return new Random().nextBoolean();
	}
}
