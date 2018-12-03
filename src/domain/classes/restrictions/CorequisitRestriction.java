package domain.classes.restrictions;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import domain.classes.Environment;
import domain.classes.Group;
import domain.classes.Lecture;
import domain.classes.PosAssig;
import domain.classes.Subject;

/** Restricci� sobre els correquisits de les assignatures.
 *  Dos grups amb el mateix parentCode de dues assignatures correquisites entre elles no poden anar a un mateix dia i hora.
 *  	Ex: PROP 10 T no pot anar al mateix dia i hora que TC 15 P, per� s� pot anar amb F 11 L i FM 10 T.
 * @author Xavier Lacasa Curto
*/

public class CorequisitRestriction extends NaryRestriction{
	
	/** Constructora est�ndard.
	 */
	public CorequisitRestriction() {
		super(true); //negotiable
	}
	
	/**
	 * @return El String que identifica la restricci�.
	 */
	public String toString() {
		return CorequisitRestriction.class.getSimpleName();
	}
	
	/** Validaci� de la restricci�.
	 * @param lecture	Sessi� que hem afegit a l'horari.
	 * @param room		Aula on hem afegit la sessi�.
	 * @param day		Dia en el que hem afegit la sessi�.
	 * @param hour		Hora en la que hem afegit la sessi�.
	 * @return True si, un cop eliminat les aules en les que cada sessi� no podia anar, totes les sessions restant poden anar com a m�nim a una aula. False en cas contrari.
	 * Les aules que s'eliminen per cada sessi� restant s�n aquelles que farien anar dos grups (un de cada assignatura correquisites entre elles), amb mateix parentCode, en un dia i hora concrets.
	*/
	//Si la assignatura A �s correq de B, llavors hi ha d'haver alguna combinaci� en que algun grup de A i un grup que pot ser diferent de B no es solapen
	public boolean validate(String room, Integer day, Integer hour, String lecture, Integer d, Integer h, String r, String l) {
		Environment env = Environment.getInstance();
		//Get inserted lecture info
		Integer duration = env.getLectureDuration(lecture);
		String subject = env.getGroupSubject(env.getLectureGroup(lecture));
		ArrayList<String> coreqs = env.getSubjectCoreqs(subject);
		//get checking lecture (l) info
		String s = env.getGroupSubject(env.getLectureGroup(l));
		ArrayList<String> cqs = env.getSubjectCoreqs(s);
		
		Boolean sonCoreqs = false;
		for (String c : cqs) {
			for (String coreq : coreqs) {
				if (coreq.equals(c)) {
					sonCoreqs = true;
				}
			}
		}
		return !(day.equals(d) && h >= hour && h < hour+duration && sonCoreqs);
	}
}