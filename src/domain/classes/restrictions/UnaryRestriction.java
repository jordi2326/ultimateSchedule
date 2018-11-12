package domain.restrictions;

import domain.Restriction;


public abstract class UnaryRestriction extends Restriction {
	
	public abstract boolean validate(Integer day, Integer hour);

	public UnaryRestriction(boolean negotiable) {
		super(negotiable);
	}

}
