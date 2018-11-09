package domain.restrictions;

import domain.Group;
import domain.Restriction;
import domain.Room;
import domain.Schedule;

public abstract class UnaryRestriction extends Restriction {
	
	public abstract boolean validate(Integer day, Integer hour);

	public UnaryRestriction(boolean negotiable) {
		super(negotiable);
	}	
}
