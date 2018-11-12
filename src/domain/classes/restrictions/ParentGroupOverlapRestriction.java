package domain.classes.restrictions;

import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

import domain.classes.Group;
import domain.classes.Lecture;
import domain.classes.PosAssig;
import domain.classes.Subject;


public class ParentGroupOverlapRestriction extends NaryRestriction{
	
	public ParentGroupOverlapRestriction() {
		super(true); //negotiable
	}
	
	//Per cada lecture mirem totes les aules de totes les hores de tots els dies i les borrem si estaran ocupades per la lecture inserida
	public boolean validate(String lecture, String room, Integer day, Integer hour, Map<String, Subject> subjects,
			Map<String, Group> groups, Map<String, Lecture> lectures, Map<String, PosAssig> shrek) {
		
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
				if (posGroup.getParentGroupCode().equals(groupCode)) {
					for (String lec : posGroup.getLectures()) {
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
		//else if it is not a parent group
		else {
			//We want to erase from parent Group so that it doesn't overlap woth this child group
			for (String g : subjects.get(subject).getGroups()) {
				Group posGroup = groups.get(g);
				if (posGroup.getCode().equals(parentGroupCode)) {
					//posGroup is the parent
					for (String lec : posGroup.getLectures()) {
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
					//When we have deleted the overlaps from parent group, there's no need to iterate through any more groups
					break;
				}
			}
		}
		return true;
	}
}