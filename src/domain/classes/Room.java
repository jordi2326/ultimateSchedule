package domain.classes;

/** Representa una aula.
 * @author Xavier Martín Ballesteros
*/

public class Room {
	
	/** Codi identificador únic.
	*/
	private String code;
	
	/** Núm. màxim d’estudiants que hi caben.
	*/
	private Integer capacity;
	
	/** Indica si l’aula disposa d’ordinadors per als alumnes.
	*/
	private Boolean hasComputers;
	
	/** Constructora estàndard.
	 * @param code			Codi de l'aula.
	 * @param capacity 		Capacitat de l'aula.
	 * @param hasComputers 	Indica si l'aula té ordinadors o no.
	*/
	public Room(String code, Integer capacity, Boolean hasComputers) {
		this.code = code;
		this.capacity = capacity;
		this.hasComputers = hasComputers;
	}
	
	/**
	 * Retorna el codi de l'aula.
	 * @return {@link Room#code}
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Retorna el núm. màxim d’estudiants que hi caben.
	 * @return {@link Room#capacity}
	 */
	public Integer getCapacity() {
		return capacity;
	}

	/**
	 * Retorna true si l’aula disposa d’ordinadors per als alumnes.
	 * @return {@link Room#hasComputers}
	 */
	public Boolean hasComputers() {
		return hasComputers;
	}
	
	/**
	 * @return El String que identifica l'aula.
	 */
	@Override
	public String toString() {
		return code;
	}
}
