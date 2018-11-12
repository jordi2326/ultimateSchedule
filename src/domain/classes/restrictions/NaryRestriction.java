package domain.classes.restrictions;

import java.util.Map;

import domain.classes.Group;
import domain.classes.Lecture;
import domain.classes.PosAssig;
import domain.classes.Restriction;
import domain.classes.Subject;

public abstract class NaryRestriction extends Restriction {
	
	public NaryRestriction(boolean negotiable) {
		super(negotiable);
	}

	public abstract boolean validate(String lecture, String room, Integer day, Integer hour, Map<String, Subject> subjects,
			Map<String, Group> groups, Map<String, Lecture> lectures, Map<String, PosAssig> shrek);

}