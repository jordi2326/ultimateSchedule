package domain.restrictions;

import java.util.Map;

import domain.Group;
import domain.Lecture;
import domain.PosAssig;
import domain.Restriction;
import domain.Subject;

public abstract class NaryRestriction extends Restriction {
	
	public NaryRestriction(boolean negotiable) {
		super(negotiable);
	}

	public abstract boolean validate(String lecture, String room, Integer day, Integer hour, Map<String, Subject> subjects,
			Map<String, Group> groups, Map<String, Lecture> lectures, Map<String, PosAssig> shrek);

}
