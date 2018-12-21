package domain.classes;

import java.util.ArrayList;

/** Representa un grup.
 * @author Xavier Martín Ballesteros
*/

public class Group {
	
	public enum Type {
		LABORATORY, THEORY, PROBLEMS, PRACTICES
	}
	
	public enum DayPeriod {
		MORNING, AFTERNOON, INDIFERENT
	}
	
	/** Codi identificador unic. Ex: 10, 20
	*/
	private String code; //nomes numero
	
	/** Num. persones que pertanyen al grup.
	*/
	private Integer numPeople;
	
	/** Codi del grup pare al qual pertany.
	 * Ex: Si {@link Group#code} es 10, aleshores {@link Group#parentGroupCode} es 10
	 * Ex: Si {@link Group#code} es 12, aleshores {@link Group#parentGroupCode} es 10
	*/
	private String parentGroupCode;
	
	/** Assignatura a la qual pertany.
	*/
	private String subject; //Es el code del subject, que es el mateix que el subject.toString()
	
	/** True si necessita ordinadors
	*/
	private Boolean needsComputers;
	
	/** Tipus de grup. Ex: "THEORY"
	*/
	private Type type;
	
	/** Periode del dia en que ha d'anar el grup. Ex: "MORNING"
	*/
	private DayPeriod dayPeriod;
	
	/** Conjunt de sessions del grup per cada setmana.
	*/
	private ArrayList<String> lectures;
	
	/** Constructora estandard.
	 * @param code				Codi del grup.
	 * @param numPeople			Num. persones en el grup.
	 * @param parentGroupCode	Codi del grup pare.
	 * @param subject			Assignatura a la que pertany.
	 * @param type				Tipus de grup.
	 * @param dayPeriod			Periode del dia.
	 * @param lectures			Conjunt de sessions del grup.
	*/
	public Group(String code, Integer numPeople, String parentGroupCode, String subject, Boolean needsComputers, Type type, DayPeriod dayPeriod, ArrayList<String> lectures) {
		this.code = code;
		this.numPeople = numPeople;
		this.parentGroupCode = parentGroupCode;
		this.subject = subject;
		this.needsComputers = needsComputers;
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
	 * Retorna el num. de persones del grup.
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
	 * Retorna true si necessita ordinadors.
	 * @return {@link Group#needsComputers}
	 */
	public Boolean needsComputers() {
		return needsComputers;
	}
	
	/**
	 * Retorna el tipus de classe del grup.
	 * @return {@link Group#type}
	 */
	public Type getType() {
		return type;
	}
	
	/**
	 * Retorna el periode del dia en que aquest grup pot fer classe.
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
	 * Afageix una sessio nova al grup.
	 * @param i			Codi de la sessio.
	 * @param duration	Duracio de la sessio.
	 * @return True si s'ha pogut afegir la nova sessio. False en cas contrari.
	 */
	public boolean addLectures(int i, int duration) {
		if(lectures.contains(this.toString()+"-"+i)) return false;
		lectures.add(this.toString()+"-"+i);
		return true;
	}
	
	/**
	 * @return El String que identifica el grup.
	 */
	@Override
	public String toString() {
		return subject + "-" + code + "-" + type;
	}
}
