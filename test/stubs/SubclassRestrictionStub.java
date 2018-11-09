package stubs;

import java.util.Random;
import domain.Group;
import domain.Room;
import domain.Schedule;
import domain.Timeframe;
import domain.restrictions.UnaryRestriction;

public class SubclassRestrictionStub extends UnaryRestriction{
	public SubclassRestrictionStub(boolean negotiable) {
		super(negotiable); //negotiable
	}
	
	@Override
	public boolean validate(Group group, Room room, Timeframe timeFrame, Schedule schedule) {
		return new Random().nextBoolean();
	}

	
}
