package domain.classes;

/** Representa una restricci� que es pot aplicar a l'hora de generar un horari.
 * @author Xavier Lacasa Curto
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
	
	/**
	 * Activa la restricci�.
	 * @return True si la restricci� �s negociable. False en cas contrari.
	 */
	public boolean enable() {
		if (negotiable) {
			enabled = true;
			return true;
		}
		return false;
	}
	
	/**
	 * Desactiva la restricci�.
	 * @return True si la restricci� �s negociable. False en cas contrari.
	 */
	public boolean disable() {
		if (negotiable) {
			enabled = false;
			return true;
		}
		return false;
	}
	
	/**
	 * @return El String que identifica la restricci�.
	 */
	public abstract String toString();
	
	/**
	 * @return El String que es veura en pantalla.
	 */
	public abstract String stringView();
}
