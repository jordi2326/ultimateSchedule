package domain.classes.restrictions;

import java.util.Map;

import domain.classes.Environment;
import domain.classes.Group;
import domain.classes.Lecture;
import domain.classes.PosAssig;
import domain.classes.Subject;

/** Restriccio sobre un mateix grup.
 *  Diverses sessions d'un mateix grup no poden anar en el mateix dia i hora.
 *  	Ex: FM 10 T de 2 hores no pot anar al mateix dia i hora que FM 10 T de 1 hora.
 * @author Xavier Lacasa Curto
*/

public class LectureFromSameGroupOverlapRestriction extends NaryRestriction{
	
	/** Constructora estandard.
	 */
	public LectureFromSameGroupOverlapRestriction() {
		super(false); //negotiable
	}
	
	/**
	 * @return El String que identifica la restriccio.
	 */
	public String toString() {
		return LectureFromSameGroupOverlapRestriction.class.getSimpleName();
	}
	
	/**
	 * @return El String que es veura en pantalla.
	 */
	public String stringView() {
		return "Lecture From Same Group Overlap Restriction";
	}
	
	/** Validacio de la restriccio.
	 * @param lecture	Sessio que hem afegit a l'horari.
	 * @param room		Aula on hem afegit la sessio.
	 * @param day		Dia en el que hem afegit la sessio.
	 * @param hour		Hora en la que hem afegit la sessio.
	 * @param pAssigMap		Conjunt de possibles assignacions per a cada sessio.
	 * @return True si, un cop eliminat les aules en les que cada sessio no podia anar, totes les sessions restant poden anar com a minim a una aula. False en cas contrari.
	 * Les aules que s'eliminen per cada sessio restant son aquelles que farien anar dues sessions del mateix grup al mateix dia i hora.
	*/
	//Lectures from M2 30P can't go in the same day
	public boolean validate(String room, Integer day, Integer hour, String lecture, Integer d, Integer h, String r, String l) {
		Environment env = Environment.getInstance();
		String g = env.getLectureGroup(l);
		String group = env.getLectureGroup(lecture);
		return !(g.equals(group) && d.equals(day));
	}
}