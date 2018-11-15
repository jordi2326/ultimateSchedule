package domain.classes.restrictions;

import java.util.Map;

import domain.classes.Group;
import domain.classes.Lecture;
import domain.classes.PosAssig;
import domain.classes.Subject;

public class SubjectLevelRestriction extends NaryRestriction{
	
	public SubjectLevelRestriction() {
		super(true); //negotiable
	}
	
	public String toString() {
		return SubjectLevelRestriction.class.toString();
	}
	
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