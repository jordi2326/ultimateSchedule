package domain;

import java.util.ArrayList;

public class Group {
	
	//TODO: Afegir override del hash per totes les classes que ho necessitin
	
	public enum Type {
		LABORATORY, THEORY, PROBLEMS, PRACTICES
	}
	
	public enum DayPeriod {
		MORNING, AFTERNOON, INDIFERENT
	}
	
	private String code; //nomes numero
	private Integer numPeople;
	private String parentGroupCode;
	private String subject; //Es el code del subject, que es el mateix que el subject.toString()
	private Type type;
	private DayPeriod dayPeriod;
	private ArrayList<String> lectures; // lectures.toString(). 0 < length
	
	/**
	 * @param code
	 * @param numPeople
	 * @param parentGroupCode
	 * @param subject
	 * @param type
	 * @param dayPeriod
	 * @param durations
	 */
	public Group(String code, Integer numPeople, String parentGroupCode, String subject, Type type, DayPeriod dayPeriod, ArrayList<Integer> durations) {
		this.code = code;
		this.numPeople = numPeople;
		this.parentGroupCode = parentGroupCode;
		this.subject = subject;
		this.type = type;
		this.dayPeriod = dayPeriod;
		this.lecturesDuration = durations; 
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @return the numPeople
	 */
	public Integer getNumPeople() {
		return numPeople;
	}

	/**
	 * @return the parentGroupCode
	 */
	public String getParentGroupCode() {
		return parentGroupCode;
	}

	/**
	 * @return the type
	 */
	public Type getType() {
		return type;
	}
	
	/**
	 * @return the dayPeriod
	 */
	public DayPeriod getDayPeriod() {
		return dayPeriod;
	}
	
	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @return the number of lectures
	 */
	public Integer getNumberOfLectures() {
		return this.lecturesDuration.size();
	}

	/**
	 * @return the duration of the i-th lecture
	 */
	public Integer getLectureDuration(Integer i) {
		// Hem de tenir en compte que es possible que i > lecturesDuration.size()?
		return this.lecturesDuration.get(i);
	}
	
	public ArrayList<Integer> getLecturesDuration() {
		return lecturesDuration;
	}
	
	public boolean removeLecture(Integer duration) {
		this.lecturesDuration.remove(duration);
		return true;
	}
	
	public boolean addLecture(Integer duration) {
		this.lecturesDuration.add(0, duration);
		return true;
	}
	
	public String getTypeAsString() {
		return type.toString();
	}
	

	public boolean hasLectures() {
		if (lecturesDuration.isEmpty()) return false;
		return true;
	}

	public String getDayPeriodAsString() {
		return dayPeriod.toString();
	}

	@Override
	public String toString() {
		return subject + code + type;
	}
	
	public ArrayList<String> getLectures() {
		return lectures;
	}
}
