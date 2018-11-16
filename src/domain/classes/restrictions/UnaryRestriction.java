package domain.classes.restrictions;

import domain.classes.Restriction;

/** Classe que engloba totes les restriccions un-àries.
 * @author XX
*/

public abstract class UnaryRestriction extends Restriction {

	/** Constructora estàndard.
	 * @param negotiable Indica si la restricció que estem mirant és negociable o no.
	 */
	public UnaryRestriction(boolean negotiable) {
		super(negotiable);
	}

	/**
	 * Validació de la restricció.
	 * @param day		Dia en que, combinat amb l'hora, no pot anar el grup.
	 * @param hour		Hora en que, combinada amb el dia, no pot anar el grup.
	 * @param duration	Duració del grup.
	 * @return True si el grup es pot posar en el day i hour. False en cas contrari.
	 */
	public abstract boolean validate(Integer day, Integer hour, Integer duration);
}
