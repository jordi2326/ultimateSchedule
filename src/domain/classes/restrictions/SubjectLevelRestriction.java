package domain.classes.restrictions;

import java.util.Map;

import domain.classes.Environment;
import domain.classes.Group;
import domain.classes.Lecture;
import domain.classes.PosAssig;
import domain.classes.Subject;

/** Restricci� sobre el nivell de les assignatures.
 *  Dos grups d'assignatures del mateix nivell no poden anar al mateix dia i hora si tenen el mateix codi i s�n del mateix tipus.
 *  	Ex: FM 10 T no pot anar al mateix dia i hora que PRO1 10 T, per� s� pot anar amb PRO1 11 L i PRO1 20 T.
 * @author Xavier Lacasa Curto
*/

public class SubjectLevelRestriction extends NaryRestriction {
	
	/** Constructora est�ndard.
	 */
	public SubjectLevelRestriction() {
		super(false); //negotiable
	}
	
	/**
	 * @return El String que identifica la restricci�.
	 */
	public String toString() {
		return SubjectLevelRestriction.class.getSimpleName();
	}
	
	/**
	 * @return El String que es veura en pantalla.
	 */
	public String stringView() {
		return "Subject Level Restriction";
	}
	
	/** Validaci� de la restricci�.
	 * @param lecture	Sessi� que hem afegit a l'horari.
	 * @param room		Aula on hem afegit la sessi�.
	 * @param day		Dia en el que hem afegit la sessi�.
	 * @param hour		Hora en la que hem afegit la sessi�.
	 * @return True si, un cop eliminat les aules en les que cada sessi� no podia anar, totes les sessions restant poden anar com a m�nim a una aula. False en cas contrari.
	 * Les aules que s'eliminen per cada sessi� restant s�n aquelles que farien anar dos grups, amb mateix codi i tipus, d'assignatures del mateix nivell al mateix dia i hora.
	*/
	@Override
	public boolean validate(String room, Integer day, Integer hour, String lecture, Integer d, Integer h, String r, String l) {
		Environment env = Environment.getInstance();
		//Info from lecture inserted
		Integer duration = env.getLectureDuration(lecture);
		String group = env.getLectureGroup(lecture);
		String groupCode = env.getGroupCode(group);
		String parentGroupCode = env.getGroupParentGroupCode(group);
		String subject = env.getGroupSubject(group);
		String subjectLevel = env.getSubjectLevel(subject);
		//Info from checked lecture (l)
		Integer dur = env.getLectureDuration(l);
		String g = env.getLectureGroup(l);
		String gcode = env.getGroupCode(g);
		String parentgcode = env.getGroupParentGroupCode(g);
		String s = env.getGroupSubject(g);
		String slevel = env.getSubjectLevel(s);
		//If groups have same parent (even though they are different subjects) and same subject level, they can't go together
		return !(day.equals(d) && hour < h+dur && h < hour+duration	&& subjectLevel.equals(slevel) && 
				(gcode.equals(groupCode) || gcode.equals(parentGroupCode) || groupCode.equals(parentgcode)));
	}
}