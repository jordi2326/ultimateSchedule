package domain.classes;

/** Representa una "lecture". Una classe setmanal d'un grup.
 * @author XX
*/

public class Lecture {
	
	/** Codi identificador de la sessió dins del grup en el que està.
	*/
	private Integer id;
	
	/** Grup al qual pertany la sessió.
	*/
	private String group;
	
	/** Duració de la sessió.
	*/
	private Integer duration;
	
	/** Constructora estàndard.
	*/
	public Lecture(Integer id, String group, Integer duration) {
		this.id = id;
		this.group = group;
		this.duration = duration;
	}
	
	/**
	 * Retorna el grup al qual pertany la sessió.
	 * @return {@link Lecture#group}
	 */
	public String getGroup() {
		return group;
	}
	
	/**
	 * Retorna la duració de la sessió.
	 * @return {@link Lecture#duration}
	 */
	public Integer getDuration() {
		return duration;
	}
	
	/**
	 * @return El String que identifica la sessió.
	 */
	@Override
	public String toString() {
		return group + id;
	}
}
