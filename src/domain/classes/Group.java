package domain.classes;

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
	private ArrayList<String> lectures; // durations[i] = duration of lecture i
	
	/**
	 * @param code
	 * @param numPeople
	 * @param parentGroupCode
	 * @param subject
	 * @param type
	 * @param dayPeriod
	 * @param durations
	 */
	public Group(String code, Integer numPeople, String parentGroupCode, String subject, Type type, DayPeriod dayPeriod, ArrayList<String> lectures) {
		this.code = code;
		this.numPeople = numPeople;
		this.parentGroupCode = parentGroupCode;
		this.subject = subject;
		this.type = type;
		this.dayPeriod = dayPeriod;
		this.lectures = lectures;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @return the number of people
	 */
	public Integer getNumOfPeople() {
		return numPeople;
	}

	/**
	 * @return the code of the parent group
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
	 * @return array of lectures of lectures
	 */
	public ArrayList<String> getLectures() {
		return lectures;
	}
	

	@Override
	public String toString() {
		return subject + code + type;
	}
}