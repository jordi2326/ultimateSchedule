package domain.classes.restrictions;

import java.util.Map;
import java.util.Map.Entry;

import domain.classes.Group;
import domain.classes.Lecture;
import domain.classes.PosAssig;
import domain.classes.Subject;

/** Restricci� sobre la disponibilitat de les aules.
 *  Un grup no pot anar a una aula ocupada.
 * @author Xavier Lacasa Curto
*/

public class OccupiedRoomRestriction extends NaryRestriction{
	
	/** Constructora est�ndard.
	 */
	public OccupiedRoomRestriction() {
		super(false); //negotiable
	}
	
	/**
	 * @return El String que identifica la restricci�.
	 */
	public String toString() {
		return OccupiedRoomRestriction.class.getSimpleName();
	}
	
	/** Validaci� de la restricci�.
	 * @param lecture	Sessi� que hem afegit a l'horari.
	 * @param room		Aula on hem afegit la sessi�.
	 * @param day		Dia en el que hem afegit la sessi�.
	 * @param hour		Hora en la que hem afegit la sessi�.
	 * @param subjects	Conjunt d'assignatures de l'entorn.
	 * @param groups	Conjunt de grups de l'entorn.
	 * @param lectures	Conjunt de sessions de l'entorn.
	 * @param shrek		Conjunt de possibles assignacions per a cada sessi�.
	 * @return True si, un cop eliminat les aules en les que cada sessi� no podia anar, totes les sessions restant poden anar com a m�nim a una aula. False en cas contrari.
	 * Les aules que s'eliminen per cada sessi� restant s�n aquelles que farien anar dos grups al mateix dia, hora i aula.
	*/
	//Per cada lecture mirem totes les aules de totes les hores de tots els dies i les borrem si estaran ocupades per la lecture inserida
	public boolean validate(String lecture, String room, Integer day, Integer hour, Map<String, Subject> subjects,
			Map<String, Group> groups, Map<String, Lecture> lectures, Map<String, PosAssig> pAssigMap) {
		
		for (Entry<String, PosAssig> entry : pAssigMap.entrySet()) {
			PosAssig pa = entry.getValue();
			if (pa.hasDay(day)) {
				if (pa.hasHourFromDay(day, hour)) {
					Integer duration = lectures.get(lecture).getDuration(); //duration of lecture
					Integer d = lectures.get(entry.getKey()).getDuration(); //duration of l
					Integer i = hour - d + 1;  //mirar foto del mobil per entendre si fa falta
					while (i < hour+duration) { //mirar foto del mobil per entendre si fa falta
						if(pa.hasHourFromDay(day, i)) {
							if (pa.hasRoomFromDayAndHour(day, i, room)) {
								pa.removeRoomFromHourAndDay(day, i, room);
							} 
						}
						++i;
					}
					if (pa.dayIsEmpty(day)) {
						pa.removeDay(day);
					}
					if (pa.hasNoDays()) {
						return false;
					}
				}
			}	
		}
		return true;
	}
}
