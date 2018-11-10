package domain;

public class Lecture {
	
	private Integer id;
	private String group;
	private Integer duration;
	
	/**
	 * @param id
	 * @param duration
	 * @param group
	 */
	public Lecture(Integer id, String group, Integer duration) {
		this.id = id;
		this.group = group;
		this.duration = duration;
	}
	
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	
	/**
	 * @param id the id to set
	 */
	public boolean setId(Integer id) {
		this.id = id;
		return true;
	}
	
	/**
	 * @return the group
	 */
	public String getGroup() {
		return group;
	}
	
	/**
	 * @param group the group to set
	 */
	public boolean setGroup(String group) {
		this.group = group;
		return true;
	}
	
	/**
	 * @return the duration
	 */
	public Integer getDuration() {
		return duration;
	}
	
	/**
	 * @param duration the duration to set
	 */
	public boolean setDuration(Integer duration) {
		this.duration = duration;
		return true;
	}
	
	@Override
	public String toString() {
		return group + id;
	}
}
