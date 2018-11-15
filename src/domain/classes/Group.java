package domain.classes;

import java.util.ArrayList;

/** Representa un grup.
 * @author XX
*/

public class Group {
	
	public enum Type {
		LABORATORY, THEORY, PROBLEMS, PRACTICES
	}
	
	public enum DayPeriod {
		MORNING, AFTERNOON, INDIFERENT
	}
	
	/** Codi identificador únic. Ex: 10, 20
	*/
	private String code; //nomes numero
	
	/** Num. persones que pertanyen al grup.
	*/
	private Integer numPeople;
	
	/** Codi del grup pare al qual pertany.
	 * Ex: Si {@link Group#code} és 10, aleshores {@link Group#parentGroupCode} és 10
	 * Ex: Si {@link Group#code} és 12, aleshores {@link Group#parentGroupCode} és 10
	*/
	private String parentGroupCode;
	
	/** Assignatura a la qual pertany.
	*/
	private String subject; //Es el code del subject, que es el mateix que el subject.toString()
	
	/** Tipus de grup. Ex: "THEORY"
	*/
	private Type type;
	
	/** Període del dia en que ha d'anar el grup. Ex: "MORNING"
	*/
	private DayPeriod dayPeriod;
	
	/** Conjunt de sessions del grup per cada setmana.
	*/
	private ArrayList<String> lectures;
	
	/** Constructora estàndard.
	 * @param code				Codi del grup.
	 * @param numPeople			Núm. persones en el grup.
	 * @param parentGroupCode	Codi del grup pare.
	 * @param subject			Assignatura a la que pertany.
	 * @param type				Tipus de grup.
	 * @param dayPeriod			Període del dia.
	 * @param lectures			Conjunt de sessions del grup.
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
	 * Retorna el codi del grup.
	 * @return {@link Group#code}
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Retorna el núm. de persones del grup.
	 * @return {@link Group#numPeople}
	 */
	public Integer getNumOfPeople() {
		return numPeople;
	}

	/**
	 * Retorna el codi del grup pare.
	 * @return {@link Group#parentGroupCode}
	 */
	public String getParentGroupCode() {
		return parentGroupCode;
	}

	/**
	 * Retorna el tipus de classe del grup.
	 * @return {@link Group#type}
	 */
	public Type getType() {
		return type;
	}
	
	/**
	 * Retorna el període del dia en que aquest grup pot fer classe.
	 * @return {@link Group#dayPeriod}
	 */
	public DayPeriod getDayPeriod() {
		return dayPeriod;
	}
	
	/**
	 * Retorna el codi de l'assignatura del grup.
	 * @return {@link Group#subject}
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * Retorna la llista de lectures del grup.
	 * @return {@link Group#lectures}
	 */
	public ArrayList<String> getLectures() {
		return lectures;
	}
	
	/**
	 * @return El String que identifica el grup.
	 */
	@Override
	public String toString() {
		return subject + "-" + code + "-" + type;
	}
}
