package domain.classes;

/** Representa una restriccio que es pot aplicar a l'hora de generar un horari.
 * @author Xavier Lacasa Curto
*/

public abstract class Restriction {
	
	/** Indica si la restriccio es negociable.
	*/
	private boolean negotiable;
	
	/** Indica si la restriccio esta activada.
	*/
	private boolean enabled = true;
	
	
	/** Constructora estandard.
	 * @param negotiable Indica si es o no negociable.
	*/
	public Restriction(boolean negotiable) {
		this.negotiable = negotiable;
	}
	
	/**
	 * Retorna si la restriccio es negociable.
	 * @return {@link Restriction#negotiable}
	 */
	public boolean isNegotiable() {
		return negotiable;
	}
	
	/**
	 * Retorna si la restriccio esta activada.
	 * @return {@link Restriction#enabled}
	 */
	public boolean isEnabled() {
		return enabled;
	}
	
	/**
	 * Activa la restriccio.
	 * @return True si la restriccio es negociable. False en cas contrari.
	 */
	public boolean enable() {
		if (negotiable) {
			enabled = true;
			return true;
		}
		return false;
	}
	
	/**
	 * Desactiva la restriccio.
	 * @return True si la restriccio es negociable. False en cas contrari.
	 */
	public boolean disable() {
		if (negotiable) {
			enabled = false;
			return true;
		}
		return false;
	}
	
	/**
	 * @return El String que identifica la restriccio.
	 */
	public abstract String toString();
	
	/**
	 * @return El String que es veura en pantalla.
	 */
	public abstract String stringView();
}
