package domain.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import domain.classes.restrictions.CorequisitRestriction;
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
	private Map<String, Map<String, NaryRestriction>> naryRestrictions;
	
	private Environment() {
		subjects = new HashMap<String, Subject>();
		rooms = new HashMap<String, Room>();
		groups = new HashMap<String, Group>();
		lectures = new HashMap<String, Lecture>();
		unaryRestrictions = new HashMap<String, Map<String, UnaryRestriction>>();
		naryRestrictions = new HashMap<String, Map<String, NaryRestriction>>();
	}
	
	public static Environment getInstance() {
		if (instance == null) {
			instance = new Environment();
		}
		return instance;
	}	
	
	/////////////// RESTRICTIONS //////////////////////////////
	
	public Boolean groupHasUnaryRestrictions(String g) {
		return unaryRestrictions.containsKey(g);
	}
	
	public Boolean groupHasNaryRestrictions(String g) {
		return naryRestrictions.containsKey(g);
	}
	
	public Set<String> getGroupUnaryRestrictions(String g) {
		return unaryRestrictions.get(g).keySet();
	}
	
	public Set<String> getGroupNaryRestrictions(String g) {
		return naryRestrictions.get(g).keySet();
	}
	
	public Boolean validateGroupUnaryRestriction(String g, String r, int day, int hour, int duration) {
		return unaryRestrictions.get(g).get(r).validate(day, hour, duration);
	}
	
	public Boolean validateGroupNaryRestriction(String g, String restr, String room, int day, int hour, String lecture, Integer d, Integer h, String r, String l) {
		return naryRestrictions.get(g).get(restr).validate(room, day, hour, lecture, d, h, r, l);
	}
	
	public void addUnaryRestriction(String g, UnaryRestriction restr) {
		if (!unaryRestrictions.containsKey(g) ) {
			unaryRestrictions.put(g, new HashMap<String, UnaryRestriction>());
		}
		unaryRestrictions.get(g).put(restr.toString(), restr);
	}
	
	public void addNaryRestriction(String g, NaryRestriction restr) {
		if (!naryRestrictions.containsKey(g) ) {
			naryRestrictions.put(g, new HashMap<String, NaryRestriction>());
		}
		naryRestrictions.get(g).put(restr.toString(), restr);
	}
	
	/////////////// GROUPS //////////////////////////////
	
	public void addGroup(Group g) {
		groups.put(g.toString(), g);
	}
	
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
	
	public Boolean groupNeedsComputers(String g) {
		return groups.get(g).needsComputers();
	}
	
	public ArrayList<String> getGroupLectures(String g) {
		return groups.get(g).getLectures();
	}
	
	public Set<String> getAllGroups() {
		return groups.keySet();
	}
	
	/////////////// SUBJECTS //////////////////////////////
	
	public void addSubject(Subject s) {
		subjects.put(s.toString(), s);
	}
	
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
	
	public Set<String> getAllSubjects() {
		return subjects.keySet();
	}
	
	/////////////// LECTURE //////////////////////////////
	
	public void addLecture(Lecture l) {
		lectures.put(l.toString(), l);
	}
	
	public Integer getLectureDuration(String l) {
		return lectures.get(l).getDuration();
	}
	
	public String getLectureGroup(String l) {
		return lectures.get(l).getGroup();
	}
	
	public Set<String> getAllLectures() {
		return lectures.keySet();
	}
	
	/////////////// ROOM //////////////////////////////
	
	public void addRoom(Room r) {
		rooms.put(r.toString(), r);
	}
	
	public String getRoomCode(String r) {
		return rooms.get(r).getCode();
	}
	
	public Integer getRoomCapacity(String r) {
		return rooms.get(r).getCapacity();
	}
	
	public Boolean roomHasComputers(String r) {
		return rooms.get(r).hasComputers();
	}
	
	public Set<String> getAllRooms() {
		return rooms.keySet();
	}
	
}
