package domain.classes.restrictions;

import java.util.Map;

import domain.classes.Group;
import domain.classes.Lecture;
import domain.classes.PosAssig;
import domain.classes.Subject;

/** Restricció sobre un mateix grup.
 *  Diverses sessions d'un mateix grup no poden anar en el mateix dia i hora.
 *  	Ex: FM 10 T de 2 hores no pot anar al mateix dia i hora que FM 10 T de 1 hora.
 * @author Xavier Lacasa Curto
*/

public class LectureFromSameGroupOverlapRestriction extends NaryRestriction{
	
	/** Constructora estàndard.
	 */
	public LectureFromSameGroupOverlapRestriction() {
		super(false); //negotiable
	}
	
	/**
	 * @return El String que identifica la restricció.
	 */
	public String toString() {
		return LectureFromSameGroupOverlapRestriction.class.getSimpleName();
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
	 * Les aules que s'eliminen per cada sessió restant són aquelles que farien anar dues sessions del mateix grup al mateix dia i hora.
	*/
	//All lectures from M2 30P can't go to the same day hour
	public boolean validate(String lecture, String room, Integer day, Integer hour, Map<String, Subject> subjects,
			Map<String, Group> groups, Map<String, Lecture> lectures, Map<String, PosAssig> pAssigMap) {
		String group = lectures.get(lecture).getGroup();
		//Get group code from inserted lecture
		for (String lec : groups.get(group).getLectures()) {
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
		return true;
	}
}