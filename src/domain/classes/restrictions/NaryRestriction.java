package domain.classes.restrictions;

import java.util.Map;

import domain.classes.Group;
import domain.classes.Lecture;
import domain.classes.PosAssig;
import domain.classes.Restriction;
import domain.classes.Subject;

/** Classe que engloba totes les restriccions n-aries.
 * @author Xavier Lacasa Curto
*/

public abstract class NaryRestriction extends Restriction {
	
	/** Constructora estandard.
	 * @param negotiable Indica si la restriccio que estem mirant es negociable o no.
	 */
	public NaryRestriction(boolean negotiable) {
		super(negotiable);
	}

	/** Validacio de la restriccio.
	 * @param lecture	Sessio que hem afegit a l'horari.
	 * @param room		Aula on hem afegit la sessio.
	 * @param day		Dia en el que hem afegit la sessio.
	 * @param hour		Hora en la que hem afegit la sessio.
	 * @param subjects	Conjunt d'assignatures de l'entorn.
	 * @param groups	Conjunt de grups de l'entorn.
	 * @param lectures	Conjunt de sessions de l'entorn.
	 * @param pAssigMap		Conjunt de possibles assignacions per a cada sessio.
	 * @return True si, un cop eliminat les aules en les que cada sessio no podia anar, totes les sessions restant poden anar com a minim a una aula. False en cas contrari.
	 * Les aules que s'eliminaran dependran de la restriccio que estiguem validant.
	*/
	public abstract boolean validate(String room, Integer day, Integer hour, String lecture, Integer d, Integer h, String r, String l);

}
