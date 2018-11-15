package domain.classes;

/** Representa una "lecture". Una classe setmanal d'un grup.
 * @author XX
*/

public class Lecture {
	
	/** Codi identificador de la sessi� dins del grup en el que est�.
	*/
	private Integer id;
	
	/** Grup al qual pertany la sessi�.
	*/
	private String group;
	
	/** Duraci� de la sessi�.
	*/
	private Integer duration;
	
	/** Constructora est�ndard.
	*/
	public Lecture(Integer id, String group, Integer duration) {
		this.id = id;
		this.group = group;
		this.duration = duration;
	}
	
	/**
	 * Retorna el grup al qual pertany la sessi�.
	 * @return {@link Lecture#group}
	 */
	public String getGroup() {
		return group;
	}
	
	/**
	 * Retorna la duraci� de la sessi�.
	 * @return {@link Lecture#duration}
	 */
	public Integer getDuration() {
		return duration;
	}
	
	/**
	 * @return El String que identifica la sessi�.
	 */
	@Override
	public String toString() {
		return group + id;
	}
}
