package domain.classes.restrictions;

import java.util.ArrayList;
import java.util.Map;

import domain.classes.Group;
import domain.classes.Lecture;
import domain.classes.PosAssig;
import domain.classes.Subject;

/** Restricció sobre els correquisits de les assignatures.
 *  Dos grups amb el mateix parentCode de dues assignatures correquisites entre elles no poden anar a un mateix dia i hora.
 *  	Ex: PROP 10 T no pot anar al mateix dia i hora que TC 15 P, però sí pot anar amb F 11 L i FM 10 T.
 * @author Xavier Lacasa Curto
*/

public class CorequisitRestriction extends NaryRestriction{
	
	/** Constructora estàndard.
	 */
	public CorequisitRestriction() {
		super(true); //negotiable
	}
	
	/**
	 * @return El String que identifica la restricció.
	 */
	public String toString() {
		return CorequisitRestriction.class.toString();
	}
	
	/** Validació de la restricció.
	 * @param lecture	Sessió que hem afegit a l'horari.
	 * @param room		Aula on hem afegit la sessió.
	 * @param day		Dia en el que hem afegit la sessió.
	 * @param hour		Hora en la que hem afegit la sessió.
	 * @param subjects	Conjunt d'assignatures de l'entorn.
	 * @param groups	Conjunt de grups de l'entorn.
	 * @param lectures	Conjunt de sessions de l'entorn.
	 * @param pAssigMap		Conjunt de possibles assignacions per a cada sessió.
	 * @return True si, un cop eliminat les aules en les que cada sessió no podia anar, totes les sessions restant poden anar com a mínim a una aula. False en cas contrari.
	 * Les aules que s'eliminen per cada sessió restant són aquelles que farien anar dos grups (un de cada assignatura correquisites entre elles), amb mateix parentCode, en un dia i hora concrets.
	*/
	//Si la assignatura A ï¿½s correq de B, llavors hi ha d'haver alguna combinaciï¿½ en que algun grup de A i un grup que pot ser diferent de B no es solapen
	public boolean validate(String lecture, String room, Integer day, Integer hour, Map<String, Subject> subjects,
			Map<String, Group> groups, Map<String, Lecture> lectures, Map<String, PosAssig> pAssigMap) {
		String group = lectures.get(lecture).getGroup();
		String subject = groups.get(group).getSubject();
		ArrayList<String> coreqs = subjects.get(subject).getCoreqs();
		//Get group code from inserted lecture
		String groupParentCode = groups.get(group).getParentGroupCode();
		//Tweak every lecture of a group with same code and subject that is correq. 
		for (Subject sub : subjects.values()) {
			if (sub.getCoreqs().contains(subject) || coreqs.contains(sub.toString())) {
				for (String gr : sub.getGroups()) {
					if (groups.get(gr).getParentGroupCode().equals(groupParentCode)) {
						for (String lec : groups.get(gr).getLectures()) {
							//If coreq and same group code, then l cannot be in the same day and hour as lecture
							if (pAssigMap.containsKey(lec)) {
								if (pAssigMap.get(lec).hasDay(day)) {
									Integer duration = lectures.get(lecture).getDuration(); //duration of lecture
									Integer d = lectures.get(lec).getDuration(); //duration of l
									Integer i = hour - d + 1;  //mirar foto del mobil per entendre si fa falta
									while (i < hour+duration) { //mirar foto del mobil per entendre si fa falta
										if(pAssigMap.get(lec).hasHourFromDay(day, i)) {
											pAssigMap.get(lec).removeHourFromDay(day, i); //it returns a boolean that is false if the hour or day weren't in pAssigMap. But it's not needed here
										}
										++i;
									}
									if (pAssigMap.get(lec).dayIsEmpty(day)) {
										pAssigMap.get(lec).removeDay(day);
									}
									if (pAssigMap.get(lec).hasNoDays()) {
										return false;
									}
								}
							}
						}
					}	
				}
			}
		}
		return true;
	}
}