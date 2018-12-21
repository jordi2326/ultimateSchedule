package domain.classes;

/** Representa una "lecture". Una classe setmanal d'un grup.
 * @author Xavier Martín Ballesteros
*/

public class Lecture {
	
	/** Codi identificador de la sessio dins del grup en el que esta.
	*/
	private Integer id;
	
	/** Grup al qual pertany la sessio.
	*/
	private String group;
	
	/** Duracio de la sessio.
	*/
	private Integer duration;
	
	/** Constructora estandard.
	 * @param id		Identificació de la sessio.
	 * @param group		Grup al qual pertany.
	 * @param duration	Duració de la sessio.
	*/
	public Lecture(Integer id, String group, Integer duration) {
		this.id = id;
		this.group = group;
		this.duration = duration;
	}
	
	/**
	 * Retorna el grup al qual pertany la sessio.
	 * @return {@link Lecture#group}
	 */
	public String getGroup() {
		return group;
	}
	
	/**
	 * Retorna la duracio de la sessio.
	 * @return {@link Lecture#duration}
	 */
	public Integer getDuration() {
		return duration;
	}
	
	/**
	 * @return El String que identifica la sessio.
	 */
	@Override
	public String toString() {
		return group + "-" +id;
	}
}
