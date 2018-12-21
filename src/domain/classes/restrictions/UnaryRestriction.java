package domain.classes.restrictions;

import domain.classes.Restriction;

/** Classe que engloba totes les restriccions un-aries.
 * @author Xavier Lacasa Curto
*/

public abstract class UnaryRestriction extends Restriction {

	/** Constructora estandard.
	 * @param negotiable Indica si la restriccio que estem mirant es negociable o no.
	 */
	public UnaryRestriction(boolean negotiable) {
		super(negotiable);
	}

	/**
	 * Validacio de la restriccio.
	 * @param day		Dia en que, combinat amb l'hora, no pot anar el grup.
	 * @param hour		Hora en que, combinada amb el dia, no pot anar el grup.
	 * @param duration	Duració del grup.
	 * @return True si el grup es pot posar en el day i hour. False en cas contrari.
	 */
	public abstract boolean validate(Integer day, Integer hour, Integer duration);
}