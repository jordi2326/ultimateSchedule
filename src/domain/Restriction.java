package domain;

public abstract class Restriction {
	private boolean negotiable;
	private boolean enabled = true;
	
	public abstract boolean validate(Group group, Room room, Timeframe timeFrame, Schedule schedule);
	
	public Restriction(boolean negotiable) {
		this.negotiable = negotiable;
	}
	
	public boolean isNegotiable() {
		return negotiable;
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	
	public void enable() {
		enabled = true;
	}
	
	public void disable() {
		enabled = false;
	}
	
}
