package domain.classes.restrictions;

import java.util.Map;

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
	 * @param subjects	Conjunt d'assignatures de l'entorn.
	 * @param groups	Conjunt de grups de l'entorn.
	 * @param lectures	Conjunt de sessions de l'entorn.
	 * @param pAssigMap		Conjunt de possibles assignacions per a cada sessi�.
	 * @return True si, un cop eliminat les aules en les que cada sessi� no podia anar, totes les sessions restant poden anar com a m�nim a una aula. False en cas contrari.
	 * Les aules que s'eliminen per cada sessi� restant s�n aquelles que farien que un grup pare i un subgrup anessin al mateix dia i hora.
	*/
	//Per cada lecture mirem totes les aules de totes les hores de tots els dies i les borrem si estaran ocupades per la lecture inserida
	public boolean validate(String lecture, String room, Integer day, Integer hour, Map<String, Subject> subjects,
			Map<String, Group> groups, Map<String, Lecture> lectures, Map<String, PosAssig> pAssigMap) {
		
		String group = lectures.get(lecture).getGroup();
		String subject = groups.get(group).getSubject();
		Group gr =  groups.get(group);
		String groupCode = gr.getCode();
		String parentGroupCode =  gr.getParentGroupCode();
		
		//If the lecture inserted is from a parentGroup
		if (groupCode.equals(parentGroupCode)) {
			//erase from other "children" lectures so that these don't overlap
			for (String g : subjects.get(subject).getGroups()) {
				Group posGroup = groups.get(g);
				if (posGroup.getParentGroupCode().equals(groupCode) 
					|| posGroup.getParentGroupCode().equals(posGroup.getCode())) {
					//Mirem si tenen com a pare el grup que avabem d'inserir o
					//Si son grups pares de la mateixa assignatura (M2 10 i M2 30 no s'han de solapar)
					for (String lec : posGroup.getLectures()) {
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
		//else if it is not a parent group
		else {
			//We want to erase from parent Group so that it doesn't overlap with this child group
			for (String g : subjects.get(subject).getGroups()) {
				Group posGroup = groups.get(g);
				if (posGroup.getCode().equals(parentGroupCode)) {
					//posGroup is the parent
					for (String lec : posGroup.getLectures()) {
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
					//When we have deleted the overlaps from parent group, there's no need to iterate through any more groups
					break;
				}
			}
		}
		return true;
	}
}