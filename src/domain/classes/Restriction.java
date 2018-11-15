package domain.classes;

/** Representa una restricci� que es pot aplicar a l'hora de generar un horari.
 * @author XX
*/

public abstract class Restriction {
	
	/** Indica si la restricci� �s negociable.
	*/
	private boolean negotiable;
	
	/** Indica si la restricci� est� activada.
	*/
	private boolean enabled = true;
	
	
	/** Constructora est�ndard.
	 * @param negotiable Indica si �s o no negociable.
	*/
	public Restriction(boolean negotiable) {
		this.negotiable = negotiable;
	}
	
	/**
	 * Retorna si la restricci� �s negociable.
	 * @return {@link Restriction#negotiable}
	 */
	public boolean isNegotiable() {
		return negotiable;
	}
	
	/**
	 * Retorna si la restricci� est� activada.
	 * @return {@link Restriction#enabled}
	 */
	public boolean isEnabled() {
		return enabled;
	}
	
	/** Activa la restricci�.
	 */
	public void enable() {
		enabled = true;
	}
	
	/** Desactiva la restricci�.
	 */
	public void disable() {
		enabled = false;
	}
}
