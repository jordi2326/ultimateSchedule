package domain.classes.restrictions;

import java.util.Map;

import domain.classes.Environment;
import domain.classes.Group;
import domain.classes.Lecture;
import domain.classes.PosAssig;
import domain.classes.Subject;

/** Restricci� sobre un mateix grup.
 *  Diverses sessions d'un mateix grup no poden anar en el mateix dia i hora.
 *  	Ex: FM 10 T de 2 hores no pot anar al mateix dia i hora que FM 10 T de 1 hora.
 * @author Xavier Lacasa Curto
*/

public class LectureFromSameGroupOverlapRestriction extends NaryRestriction{
	
	/** Constructora est�ndard.
	 */
	public LectureFromSameGroupOverlapRestriction() {
		super(false); //negotiable
	}
	
	/**
	 * @return El String que identifica la restricci�.
	 */
	public String toString() {
		return LectureFromSameGroupOverlapRestriction.class.getSimpleName();
	}
	
	/** Validaci� de la restricci�.
	 * @param lecture	Sessi� que hem afegit a l'horari.
	 * @param room		Aula on hem afegit la sessi�.
	 * @param day		Dia en el que hem afegit la sessi�.
	 * @param hour		Hora en la que hem afegit la sessi�.
	 * @param pAssigMap		Conjunt de possibles assignacions per a cada sessi�.
	 * @return True si, un cop eliminat les aules en les que cada sessi� no podia anar, totes les sessions restant poden anar com a m�nim a una aula. False en cas contrari.
	 * Les aules que s'eliminen per cada sessi� restant s�n aquelles que farien anar dues sessions del mateix grup al mateix dia i hora.
	*/
	//Lectures from M2 30P can't go in the same day
	public boolean validate(String room, Integer day, Integer hour, String lecture, Integer d, Integer h, String r, String l) {
		Environment env = Environment.getInstance();
		String g = env.getLectureGroup(l);
		String group = env.getLectureGroup(lecture);
		return !(g.equals(group) && d.equals(day));
	}
}