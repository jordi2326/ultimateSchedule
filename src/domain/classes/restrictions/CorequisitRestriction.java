package domain.classes.restrictions;

import java.util.ArrayList;
import java.util.Map;

import domain.classes.Group;
import domain.classes.Lecture;
import domain.classes.PosAssig;
import domain.classes.Subject;


public class CorequisitRestriction extends NaryRestriction{
	
	public CorequisitRestriction() {
		super(true); //negotiable
	}
	
	public String toString() {
		return CorequisitRestriction.class.toString();
	}
	
	//Si la assignatura A �s correq de B, llavors hi ha d'haver alguna combinaci� en que algun grup de A i un grup que pot ser diferent de B no es solapen
	public boolean validate(String lecture, String room, Integer day, Integer hour, Map<String, Subject> subjects,
			Map<String, Group> groups, Map<String, Lecture> lectures, Map<String, PosAssig> shrek) {
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