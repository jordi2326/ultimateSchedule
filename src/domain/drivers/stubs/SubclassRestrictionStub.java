package domain.drivers.stubs;

import java.util.Random;
import domain.Group;
import domain.Restriction;
import domain.Room;
import domain.Schedule;

public class SubclassRestrictionStub extends Restriction{
	public SubclassRestrictionStub(boolean negotiable) {
		super(negotiable); //negotiable
	}
	
	@Override
	public boolean validate(Group group, Room room, Timeframe timeFrame, Schedule schedule) {
		return new Random().nextBoolean();
	}
}
