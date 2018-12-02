package domain.classes;

import java.util.ArrayList;
import java.util.Map;

import domain.classes.restrictions.NaryRestriction;
import domain.classes.restrictions.UnaryRestriction;

public class Environment {
	/** Instancia d'aquesta classe.
	*/
	private static Environment instance;
	
	/** Conjunt d'assignatures de l'entorn.
	*/
	private Map<String, Subject> subjects;
	
	/** Conjunt d'aules de l'entorn.
	*/
	private Map<String, Room> rooms;
	
	/** Conjunt de grups de l'entorn.
	*/
	private Map<String, Group> groups;
	
	/** Conjunt de 'lectures' de l'entorn.
	*/
	private Map<String, Lecture> lectures;
	
	/** Map de restriccions unaries de l'entorn  
	*/
	private Map<String, Map<String, UnaryRestriction>> unaryRestrictions; //Key = group.toString()
	
	/** Map de restriccions n-aries de l'entorn  
	*/
	private Map<String, NaryRestriction> naryRestrictions;
	
	
	/////////////// GROUPS //////////////////////////////
	public String getGroupSubject(String g) {
		return groups.get(g).getSubject();
	}
	
	public String getGroupCode(String g) {
		return groups.get(g).getCode();
	}
	
	public Integer getGroupNumOfPeople (String g) {
		return groups.get(g).getNumOfPeople();
	}
	
	public Group.Type getGroupType(String g) {
		return groups.get(g).getType();
	}
	
	public Group.DayPeriod getGroupDayPeriod(String g) {
		return groups.get(g).getDayPeriod();
	}
	
	public String getGroupParentGroupCode(String g) {
		return groups.get(g).getParentGroupCode();
	}
	
	public ArrayList<String> getGroupLectures(String g) {
		return groups.get(g).getLectures();
	}
	
	/////////////// SUBJECTS //////////////////////////////
	
	public String getSubjectCode(String s) {
		return subjects.get(s).getCode();
	}
	
	public String getSubjectName(String s) {
		return subjects.get(s).getName();
	}
	
	public String getSubjectLevel(String s) {
		return subjects.get(s).getLevel();
	}
	
	public ArrayList<String> getSubjectCoreqs(String s) {
		return subjects.get(s).getCoreqs();
	}
	
	public ArrayList<String> getSubjectGroups(String s) {
		return subjects.get(s).getGroups();
	}
	
	/////////////// LECTURE //////////////////////////////
	
	public Integer getLectureDuration(String l) {
		return lectures.get(l).getDuration();
	}
	
	public String getLectureGroup(String l) {
		return lectures.get(l).getGroup();
	}
	
	
	/////////////// ROOM //////////////////////////////
	
	
	public String getRoomCode(String r) {
		return rooms.get(r).getCode();
	}
	
	public Integer getRoomCapacity(String r) {
		return rooms.get(r).getCapacity();
	}
	
	public Boolean roomHasComputers(String r) {
		return rooms.get(r).hasComputers();
	}
	
	
	
	
}
