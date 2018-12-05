package domain.classes;

/** Representa una restricciï¿½ que es pot aplicar a l'hora de generar un horari.
 * @author Xavier Lacasa Curto
*/

public abstract class Restriction {
	
	/** Indica si la restricciï¿½ ï¿½s negociable.
	*/
	private boolean negotiable;
	
	/** Indica si la restricciï¿½ estï¿½ activada.
	*/
	private boolean enabled = true;
	
	
	/** Constructora estï¿½ndard.
	 * @param negotiable Indica si ï¿½s o no negociable.
	*/
	public Restriction(boolean negotiable) {
		this.negotiable = negotiable;
	}
	
	/**
	 * Retorna si la restricciï¿½ ï¿½s negociable.
	 * @return {@link Restriction#negotiable}
	 */
	public boolean isNegotiable() {
		return negotiable;
	}
	
	/**
	 * Retorna si la restricciï¿½ estï¿½ activada.
	 * @return {@link Restriction#enabled}
	 */
	public boolean isEnabled() {
		return enabled;
	}
	
	/**
	 * Activa la restricció.
	 * @return True si la restricció és negociable. False en cas contrari.
	 */
	public boolean enable() {
		if (negotiable) {
			enabled = true;
			return true;
		}
		return false;
	}
	
	/**
	 * Desactiva la restricció.
	 * @return True si la restricció és negociable. False en cas contrari.
	 */
	public boolean disable() {
		if (negotiable) {
			enabled = false;
			return true;
		}
		return false;
	}
	
	/**
	 * @return El String que identifica la restricció.
	 */
	public abstract String toString();
	
	/**
	 * @return El String que es veura en pantalla.
	 */
	public abstract String stringView();
}
