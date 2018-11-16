package domain.classes.restrictions;

import java.util.Map;

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
		super(true); //negotiable
	}
	
	/**
	 * @return El String que identifica la restricci�.
	 */
	public String toString() {
		return SubjectLevelRestriction.class.getSimpleName();
	}
	
	/** Validaci� de la restricci�.
	 * @param lecture	Sessi� que hem afegit a l'horari.
	 * @param room		Aula on hem afegit la sessi�.
	 * @param day		Dia en el que hem afegit la sessi�.
	 * @param hour		Hora en la que hem afegit la sessi�.
	 * @param subjects	Conjunt d'assignatures de l'entorn.
	 * @param groups	Conjunt de grups de l'entorn.
	 * @param lectures	Conjunt de sessions de l'entorn.
	 * @param pAssigMap		Conjunt de possibles assignacions per a cada sessi�.
	 * @return True si, un cop eliminat les aules en les que cada sessi� no podia anar, totes les sessions restant poden anar com a m�nim a una aula. False en cas contrari.
	 * Les aules que s'eliminen per cada sessi� restant s�n aquelles que farien anar dos grups, amb mateix codi i tipus, d'assignatures del mateix nivell al mateix dia i hora.
	*/
	@Override
	public boolean validate(String lecture, String room, Integer day, Integer hour, Map<String, Subject> subjects,
			Map<String, Group> groups, Map<String, Lecture> lectures, Map<String, PosAssig> pAssigMap) {
		String group = lectures.get(lecture).getGroup();
		String subject = groups.get(group).getSubject();
		//Get level from subject of inserted lecture
		String subjectLevel = subjects.get(subject).getLevel();
		//Get group code from inserted lecture
		String groupParentCode = groups.get(group).getParentGroupCode();
		//Yweak every lecture of a group with same code and subject with same level. WE don't iterate all over pAssigMap :) so efficient
		for (Subject sub : subjects.values()) {
			if (sub.getLevel().equals(subjectLevel)) {
				for (String gr : sub.getGroups()) {
					if (groups.get(gr).getParentGroupCode().equals(groupParentCode) && !sub.toString().equals(subject)) {
						for (String lec : groups.get(gr).getLectures()) {
							//If same level and same code, then l cannot be in the same day and hour as lecture
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