package domain.classes.restrictions;

import java.util.Map;

import domain.classes.Environment;
import domain.classes.Group;
import domain.classes.Lecture;
import domain.classes.PosAssig;
import domain.classes.Subject;

/** Restricci� sobre els grups.
 *  Un subgrup (31, 45...) no pot anar al mateix dia i hora que el seu grup pare (30, 40...).
 *  	Ex: FM 11 L no pot anar al mateix dia i hora que FM 10 T, per� s� pot anar amb FM 12 L.
 * @author Xavier Lacasa Curto
*/

public class ParentGroupOverlapRestriction extends NaryRestriction {
	
	/** Constructora est�ndard.
	 */
	public ParentGroupOverlapRestriction() {
		super(false); //negotiable
	}
	
	/**
	 * @return El String que identifica la restricci�.
	 */
	public String toString() {
		return ParentGroupOverlapRestriction.class.getSimpleName();
	}
	
	/** Validaci� de la restricci�.
	 * @param lecture	Sessi� que hem afegit a l'horari.
	 * @param room		Aula on hem afegit la sessi�.
	 * @param day		Dia en el que hem afegit la sessi�.
	 * @param hour		Hora en la que hem afegit la sessi�.
	 * @return True si, un cop eliminat les aules en les que cada sessi� no podia anar, totes les sessions restant poden anar com a m�nim a una aula. False en cas contrari.
	 * Les aules que s'eliminen per cada sessi� restant s�n aquelles que farien que un grup pare i un subgrup anessin al mateix dia i hora.
	*/
	//Per cada lecture mirem totes les aules de totes les hores de tots els dies i les borrem si estaran ocupades per la lecture inserida
	public boolean validate(String room, Integer day, Integer hour, String lecture, Integer d, Integer h, String r, String l) {
		Environment env = Environment.getInstance();
		//Info from  inserted lecture
		Integer duration = env.getLectureDuration(lecture);
		String group = env.getLectureGroup(lecture);
		String groupCode = env.getGroupCode(group);
		String parentGroupCode = env.getGroupParentGroupCode(group);
		String subject = env.getGroupSubject(group);
		//Info from checked lecture (l)
		String g = env.getLectureGroup(l);
		String gcode = env.getGroupCode(g);
		String parentgcode = env.getGroupParentGroupCode(g);
		String s = env.getGroupSubject(g);
		//If lecture inserted is from parent group, make sure subgroups don't overlap
		//And also other parentGroups from same subject don't overlap
		//Si son grups pares de la mateixa assignatura (M2 10 i M2 30 no s'han de solapar)
		if (s.equals(subject)) {
			if (groupCode.equals(parentGroupCode)) {
				return !(day.equals(d) && h >= hour && h < hour+duration && (gcode.equals(parentgcode) || parentgcode.equals(groupCode)));
			}
			else {
				//check that l is not the parent group of lecture inserted
				return !(day.equals(d) && h >= hour && h < hour+duration && (gcode.equals(parentGroupCode)));
			}
		}
		return true;
	}
}