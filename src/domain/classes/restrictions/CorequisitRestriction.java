package domain.classes.restrictions;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import domain.classes.Environment;
import domain.classes.Group;
import domain.classes.Lecture;
import domain.classes.PosAssig;
import domain.classes.Subject;

/** Restriccio sobre els correquisits de les assignatures.
 *  Dos grups amb el mateix parentCode de dues assignatures correquisites entre elles no poden anar a un mateix dia i hora.
 *  	Ex: PROP 10 T no pot anar al mateix dia i hora que TC 15 P, pero si pot anar amb F 11 L i FM 10 T.
 * @author Xavier Lacasa Curto
*/

public class CorequisitRestriction extends NaryRestriction{
	
	/** Constructora estandard.
	 */
	public CorequisitRestriction() {
		super(false); //negotiable
	}
	
	/**
	 * @return El String que identifica la restriccio.
	 */
	public String toString() {
		return CorequisitRestriction.class.getSimpleName();
	}
	
	/**
	 * @return El String que es veura en pantalla.
	 */
	public String stringView() {
		return "Corequisit Restriction";
	}
	
	/** Validacio de la restriccio.
	 * @param lecture	Sessio que hem afegit a l'horari.
	 * @param room		Aula on hem afegit la sessio.
	 * @param day		Dia en el que hem afegit la sessio.
	 * @param hour		Hora en la que hem afegit la sessio.
	 * @return True si, un cop eliminat les aules en les que cada sessio no podia anar, totes les sessions restant poden anar com a minim a una aula. False en cas contrari.
	 * Les aules que s'eliminen per cada sessio restant son aquelles que farien anar dos grups (un de cada assignatura correquisites entre elles), amb mateix parentCode, en un dia i hora concrets.
	*/
	//Si la assignatura A �s correq de B, llavors hi ha d'haver alguna combinaci� en que algun grup de A i un grup que pot ser diferent de B no es solapen
	public boolean validate(String room, Integer day, Integer hour, String lecture, Integer d, Integer h, String r, String l) {
		Environment env = Environment.getInstance();
		//Get inserted lecture info
		Integer duration = env.getLectureDuration(lecture);
		String group = env.getLectureGroup(lecture);
		String groupCode = env.getGroupCode(group);
		String subject = env.getGroupSubject(group);
		String groupParentCode = env.getGroupParentGroupCode(group);
		ArrayList<String> coreqs = env.getSubjectCoreqs(subject);
		//get checking lecture (l) info
		Integer dur = env.getLectureDuration(l);
		String g = env.getLectureGroup(l);
		String gcode = env.getGroupCode(g);
		String s = env.getGroupSubject(g);
		String gpcode = env.getGroupParentGroupCode(g);
		ArrayList<String> cqs = env.getSubjectCoreqs(s);
		//Nomes importa si son coreqs si tenen el mateix parent group
		Boolean sonCoreqs = false;
		if (groupCode.equals(gpcode) || groupCode.equals(gcode) || gcode.equals(groupParentCode)) {
			for (String c : cqs) {
				if (c.equals(subject)) {
					sonCoreqs = true;
				}
			}
			for (String coreq : coreqs) {
				if (coreq.equals(s)) {
					sonCoreqs = true;
				}
			}
		}
		return !(day.equals(d) && hour < h+dur && h < hour+duration && sonCoreqs);
	}
}