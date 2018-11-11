package domain.restrictions;

import java.util.Map;

import domain.Group;
import domain.Lecture;
import domain.PosAssig;
import domain.Restriction;
import domain.Room;
import domain.Schedule;
import domain.Subject;

public abstract class UnaryRestriction extends Restriction {
	
	public abstract boolean validate(Integer day, Integer hour);

	public UnaryRestriction(boolean negotiable) {
		super(negotiable);
	}

}
