package domain.restrictions;

import java.util.Iterator;
import java.util.Map;

import domain.Group;
import domain.Lecture;
import domain.PosAssig;
import domain.Room;
import domain.Subject;
import domain.controllers.CtrlDomain;

public class SubjectLevelRestriction extends NaryRestriction{
	
	public SubjectLevelRestriction() {
		super(true); //negotiable
	}
	
	@Override
	public boolean validate(String lecture, String room, Integer day, Integer hour, Map<String, Subject> subjects,
			Map<String, Group> groups, Map<String, Lecture> lectures, Map<String, PosAssig> shrek) {
		String group = lectures.get(lecture).getGroup();
		String subject = groups.get(group).getSubject();
		//Get level from subject of inserted lecture
		String subjectLevel = subjects.get(subject).getLevel();
		//Get group code from inserted lecture
		String groupCode = groups.get(group).getCode();
		//Yweak every lecture of a group with same code and subject with same level. WE don't iterate all over shrek :) so efficient
		for (Subject sub : subjects.values()) {
			if (sub.getLevel().equals(subjectLevel)) {
				for (String gr : sub.getAllGroups()) {
					if (groups.get(gr).getCode().equals(groupCode)) {
						for (String lec : groups.get(gr).getLectures()) {
							//If same level and same code, then l cannot be in the same day and hour as lecture
							if (shrek.get(lec).hasDay(day)) {
								Integer duration = lectures.get(lecture).getDuration(); //duration of lecture
								Integer d = lectures.get(lec).getDuration(); //duration of l
								Integer i = hour - d + 1;
								while (i < hour+duration) { 
									if(shrek.get(lec).hasHourFromDay(day, i)) {
										shrek.get(lec).removeHourFromDay(day, i); //it returns a boolean that is false if the hour or day weren't in shrek. But it's not needed here
									}
									++i;
								}
								if (shrek.get(lec).dayIsEmpty(day)) {
									shrek.get(lec).removeDay(day);
								}
								if (shrek.isEmpty()) {
									return false;
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