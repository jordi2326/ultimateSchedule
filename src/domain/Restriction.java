package domain;

public abstract class Restriction {
	private boolean negotiable;
	private boolean enabled = true;
	
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
