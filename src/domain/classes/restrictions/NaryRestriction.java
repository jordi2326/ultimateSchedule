package domain.classes.restrictions;

import java.util.Map;

import domain.classes.Group;
import domain.classes.Lecture;
import domain.classes.PosAssig;
import domain.classes.Restriction;
import domain.classes.Subject;

/** Classe que engloba totes les restriccions n-�ries.
 * @author XX
*/

public abstract class NaryRestriction extends Restriction {
	
	/** Constructora est�ndard.
	 */
	public NaryRestriction(boolean negotiable) {
		super(negotiable);
	}

	/** Validaci� de la restricci�.
	 * @param lecture	Sessi� que hem afegit a l'horari.
	 * @param room		Aula on hem afegit la sessi�.
	 * @param day		Dia en el que hem afegit la sessi�.
	 * @param hour		Hora en la que hem afegit la sessi�.
	 * @param subjects	Conjunt d'assignatures de l'entorn.
	 * @param groups	Conjunt de grups de l'entorn.
	 * @param lectures	Conjunt de sessions de l'entorn.
	 * @param shrek		Conjunt de possibles assignacions per a cada sessi�.
	 * @return True si, un cop eliminat les aules en les que cada sessi� no podia anar, totes les sessions restant poden anar com a m�nim a una aula. False en cas contrari.
	 * Les aules que s'eliminaran dependran de la restricci� que estiguem validant.
	*/
	public abstract boolean validate(String lecture, String room, Integer day, Integer hour, Map<String, Subject> subjects,
			Map<String, Group> groups, Map<String, Lecture> lectures, Map<String, PosAssig> shrek);

}
