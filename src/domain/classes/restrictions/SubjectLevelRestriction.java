package domain.classes.restrictions;

import java.util.Map;

import domain.classes.Group;
import domain.classes.Lecture;
import domain.classes.PosAssig;
import domain.classes.Subject;

/** Restricció sobre el nivell de les assignatures.
 *  Dos grups d'assignatures del mateix nivell no poden anar al mateix dia i hora si tenen el mateix codi i són del mateix tipus.
 *  	Ex: FM 10 T no pot anar al mateix dia i hora que PRO1 10 T, però sí pot anar amb PRO1 11 L i PRO1 20 T.
 * @author XX
*/

public class SubjectLevelRestriction extends NaryRestriction {
	
	/** Constructora estàndard.
	 */
	public SubjectLevelRestriction() {
		super(true); //negotiable
	}
	
	/**
	 * @return El String que identifica la restricció.
	 */
	public String toString() {
		return SubjectLevelRestriction.class.toString();
	}
	
	/** Validació de la restricció.
	 * @param lecture	Sessió que hem afegit a l'horari.
	 * @param room		Aula on hem afegit la sessió.
	 * @param day		Dia en el que hem afegit la sessió.
	 * @param hour		Hora en la que hem afegit la sessió.
	 * @param subjects	Conjunt d'assignatures de l'entorn.
	 * @param groups	Conjunt de grups de l'entorn.
	 * @param lectures	Conjunt de sessions de l'entorn.
	 * @param shrek		Conjunt de possibles assignacions per a cada sessió.
	 * @return True si, un cop eliminat les aules en les que cada sessió no podia anar, totes les sessions restant poden anar com a mínim a una aula. False en cas contrari.
	 * Les aules que s'eliminen per cada sessió restant són aquelles que farien anar dos grups, amb mateix codi i tipus, d'assignatures del mateix nivell al mateix dia i hora.
	*/
	@Override
	public boolean validate(String lecture, String room, Integer day, Integer hour, Map<String, Subject> subjects,
			Map<String, Group> groups, Map<String, Lecture> lectures, Map<String, PosAssig> shrek) {
		String group = lectures.get(lecture).getGroup();
		String subject = groups.get(group).getSubject();
		//Get level from subject of inserted lecture
		String subjectLevel = subjects.get(subject).getLevel();
		//Get group code from inserted lecture
		String groupParentCode = groups.get(group).getParentGroupCode();
		//Yweak every lecture of a group with same code and subject with same level. WE don't iterate all over shrek :) so efficient
		for (Subject sub : subjects.values()) {
			if (sub.getLevel().equals(subjectLevel)) {
				for (String gr : sub.getGroups()) {
					if (groups.get(gr).getParentGroupCode().equals(groupParentCode) && !sub.toString().equals(subject)) {
						for (String lec : groups.get(gr).getLectures()) {
							//If same level and same code, then l cannot be in the same day and hour as lecture
							if (shrek.containsKey(lec)) {
								if (shrek.get(lec).hasDay(day)) {
									Integer duration = lectures.get(lecture).getDuration(); //duration of lecture
									Integer d = lectures.get(lec).getDuration(); //duration of l
									Integer i = hour - d + 1;  //mirar foto del mobil per entendre si fa falta
									while (i < hour+duration) { //mirar foto del mobil per entendre si fa falta
										if(shrek.get(lec).hasHourFromDay(day, i)) {
											shrek.get(lec).removeHourFromDay(day, i); //it returns a boolean that is false if the hour or day weren't in shrek. But it's not needed here
										}
										++i;
									}
									if (shrek.get(lec).dayIsEmpty(day)) {
										shrek.get(lec).removeDay(day);
									}
									if (shrek.get(lec).hasNoDays()) {
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