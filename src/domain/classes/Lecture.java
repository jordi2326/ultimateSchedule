package domain.classes;

/** Representa una "lecture". Una classe setmanal d'un grup.
 * @author Xavier Mart韓 Ballesteros
*/

public class Lecture {
	
	/** Codi identificador de la sessi贸 dins del grup en el que est脿.
	*/
	private Integer id;
	
	/** Grup al qual pertany la sessi贸.
	*/
	private String group;
	
	/** Duraci贸 de la sessi贸.
	*/
	private Integer duration;
	
	/** Constructora est脿ndard.
	*/
	public Lecture(Integer id, String group, Integer duration) {
		this.id = id;
		this.group = group;
		this.duration = duration;
	}
	
	/**
	 * Retorna el grup al qual pertany la sessi贸.
	 * @return {@link Lecture#group}
	 */
	public String getGroup() {
		return group;
	}
	
	/**
	 * Retorna la duraci贸 de la sessi贸.
	 * @return {@link Lecture#duration}
	 */
	public Integer getDuration() {
		return duration;
	}
	
	/**
	 * @return El String que identifica la sessi贸.
	 */
	@Override
	public String toString() {
		return group + "-" +id;
	}
}
