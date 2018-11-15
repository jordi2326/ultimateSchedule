package domain.classes.restrictions;

import java.util.Map;
import java.util.Map.Entry;

import domain.classes.Group;
import domain.classes.Lecture;
import domain.classes.PosAssig;
import domain.classes.Subject;


public class OccupiedRoomRestriction extends NaryRestriction{
	
	public OccupiedRoomRestriction() {
		super(false); //negotiable
	}
	
	public String toString() {
		return OccupiedRoomRestriction.class.toString();
	}
	
	//Per cada lecture mirem totes les aules de totes les hores de tots els dies i les borrem si estaran ocupades per la lecture inserida
	public boolean validate(String lecture, String room, Integer day, Integer hour, Map<String, Subject> subjects,
			Map<String, Group> groups, Map<String, Lecture> lectures, Map<String, PosAssig> shrek) {
		
		for (Entry<String, PosAssig> entry : shrek.entrySet()) {
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
