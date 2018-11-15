package domain.classes;

/** Representa una restricció que es pot aplicar a l'hora de generar un horari.
 * @author XX
*/

public abstract class Restriction {
	
	/** Indica si la restricció és negociable.
	*/
	private boolean negotiable;
	
	/** Indica si la restricció està activada.
	*/
	private boolean enabled = true;
	
	
	/** Constructora estàndard.
	 * @param negotiable Indica si és o no negociable.
	*/
	public Restriction(boolean negotiable) {
		this.negotiable = negotiable;
	}
	
	/**
	 * Retorna si la restricció és negociable.
	 * @return {@link Restriction#negotiable}
	 */
	public boolean isNegotiable() {
		return negotiable;
	}
	
	/**
	 * Retorna si la restricció està activada.
	 * @return {@link Restriction#enabled}
	 */
	public boolean isEnabled() {
		return enabled;
	}
	
	/** Activa la restricció.
	 */
	public void enable() {
		enabled = true;
	}
	
	/** Desactiva la restricció.
	 */
	public void disable() {
		enabled = false;
	}
}
