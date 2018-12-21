package domain.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import domain.classes.Group.DayPeriod;
import domain.classes.restrictions.CorequisitRestriction;
import domain.classes.restrictions.DayPeriodRestriction;
import domain.classes.restrictions.LectureFromSameGroupOverlapRestriction;
import domain.classes.restrictions.NaryRestriction;
import domain.classes.restrictions.OccupiedRoomRestriction;
import domain.classes.restrictions.ParentGroupOverlapRestriction;
import domain.classes.restrictions.SpecificDayOrHourRestriction;
import domain.classes.restrictions.SubjectLevelRestriction;
import domain.classes.restrictions.UnaryRestriction;

public class Environment {
	/** Instancia d'aquesta classe.
	*/
	private static Environment instance;
	
	/** Relative path (filename of environment)
	*/
	private String path;
	
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
	
	private Map<String, NaryRestriction> groupRestr;
	
	private Environment() {
		subjects = new HashMap<String, Subject>();
		rooms = new HashMap<String, Room>();
		groups = new HashMap<String, Group>();
		lectures = new HashMap<String, Lecture>();
		unaryRestrictions = new HashMap<String, Map<String, UnaryRestriction>>();
		naryRestrictions = new HashMap<String, Map<String, NaryRestriction>>();
		groupRestr = new HashMap<String, NaryRestriction>();		
		
		OccupiedRoomRestriction ocrr = new OccupiedRoomRestriction();
		groupRestr.put(ocrr.toString(), ocrr);
		ParentGroupOverlapRestriction pgor = new ParentGroupOverlapRestriction();
		groupRestr.put(pgor.toString(), pgor);
		CorequisitRestriction cr = new CorequisitRestriction();
		groupRestr.put(cr.toString(), cr);
		SubjectLevelRestriction slr = new SubjectLevelRestriction();
		groupRestr.put(slr.toString(), slr);
		LectureFromSameGroupOverlapRestriction lfgor = new LectureFromSameGroupOverlapRestriction();
		groupRestr.put(lfgor.toString(), lfgor);
	}
	
	public static Environment getInstance() {
		if (instance == null) {
			instance = new Environment();
		}
		return instance;
	}	
	
	public void erase() {
		for (Iterator<Map.Entry<String,Subject>> iter = subjects.entrySet().iterator(); iter.hasNext();) {
			iter.next();
			iter.remove();
		}
		for (Iterator<Map.Entry<String,Room>> iter = rooms.entrySet().iterator(); iter.hasNext();) {
			iter.next();
			iter.remove();
		}
		for (Iterator<Map.Entry<String,Group>> iter = groups.entrySet().iterator(); iter.hasNext();) {
			iter.next();
			iter.remove();
		}
		for (Iterator<Map.Entry<String,Lecture>> iter = lectures.entrySet().iterator(); iter.hasNext();) {
			iter.next();
			iter.remove();
		}
		for (Iterator<Map.Entry<String,Map<String, UnaryRestriction>>> iter = unaryRestrictions.entrySet().iterator(); iter.hasNext();) {
			iter.next();
			iter.remove();
		}
		for (Iterator<Map.Entry<String,Map<String, NaryRestriction>>> iter = naryRestrictions.entrySet().iterator(); iter.hasNext();) {
			iter.next();
			iter.remove();
		}
		path = null;
	}
	
	public Boolean setPath(String path) {
		if (this.path == null) {
			this.path = path;
			return true;
		}
		return false;
	}
	
	public String getPath() {
		return path;
	}
	
	/////////////// RESTRICTIONS //////////////////////////////
	
	public Map<String, Map<String, UnaryRestriction>> getUnaryRestrictions() {
		return unaryRestrictions;
	}
	
	public Map<String, Map<String, NaryRestriction>> getNaryRestrictions() {
		return naryRestrictions;
	}
	
	public ArrayList<Object[]> getRestrictionInfo() {
		ArrayList<Object[]> restr = new ArrayList<Object[]>();
		for (String group : groups.keySet()) {
			Map<String, UnaryRestriction> map = unaryRestrictions.get(group);
			for (String rest : map.keySet()) {
				if (map.get(rest).getClass().getSimpleName().equals(SpecificDayOrHourRestriction.class.getSimpleName())) {
					Object[] str = new Object[] {map.get(rest).toString(), group, ((SpecificDayOrHourRestriction) map.get(rest)).getDay(), ((SpecificDayOrHourRestriction) map.get(rest)).getHour()};
					restr.add(str);
				}
			}
		}
		
		return restr;
	}
	
	public Boolean groupHasUnaryRestrictions(String g) {
		return unaryRestrictions.containsKey(g);
	}
	
	public Boolean groupHasNaryRestrictions(String g) {
		return naryRestrictions.containsKey(g);
	}
	
	public  Set<ArrayList<Integer>> getSpecificDayHourInfo(String g) {
		Set<ArrayList<Integer>> info = new HashSet<ArrayList<Integer>>();
		for (UnaryRestriction r : unaryRestrictions.get(g).values()) {
			if (r.isNegotiable()) {
				ArrayList<Integer> dayAndHours = new ArrayList<>();
				dayAndHours.add( ((SpecificDayOrHourRestriction) r).getDay() );
				dayAndHours.add( ((SpecificDayOrHourRestriction) r).getHour() );
				info.add(dayAndHours);
			}
		}
		return info;
	}
	
	public boolean removeRestriction(String group, String name) {
		if (unaryRestrictions.get(group).containsKey(name)) {
			unaryRestrictions.get(group).remove(name);
			return true;
		}
		return false;
	}
	
	public Set<String> getGroupUnaryRestrictions(String g) {
		return unaryRestrictions.get(g).keySet();
	}
	
	public Set<String> getGroupNaryRestrictions(String g) {
		if (naryRestrictions.containsKey(g)) {
			return naryRestrictions.get(g).keySet();
		}
		return new HashSet<String>();
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
	
	/**
	 * @param inCode
	 * @param inNPeople
	 * @param inParentGroupCode
	 * @param subjectCode
	 * @param inNeedsComputers
	 * @param inType
	 * @param inDayPeriod
	 * @param arrayList
	 * @return
	 */
	public boolean addGroup(String inCode, Integer inNPeople, String inParentGroupCode, String subjectCode,
			Boolean inNeedsComputers, String inType, String inDayPeriod, ArrayList<String> arrayList) {
		if (!groups.containsKey(subjectCode + "-" + inCode + "-" + inType)) {
			Group g = new Group(inCode, inNPeople, inParentGroupCode, subjectCode, inNeedsComputers, Group.Type.valueOf((String) inType), Group.DayPeriod.valueOf((String) inDayPeriod), arrayList);
			
			groups.put(g.toString(), g);
			naryRestrictions.put(g.toString(), groupRestr);
			System.out.println(inCode);
			return true;
		}
		
		return false;
	}
	
	/**
	 * @param name
	 * @return
	 */
	public boolean removeGroup(String name) {
		for(String lecture : groups.get(name).getLectures()) {
			removeLecture(lecture);
		}
		groups.remove(name);
		return true;
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
	
	public boolean addSubject(String inCode, String inName, String inLevel, ArrayList<String> groups, ArrayList<String> inCoreqs) {
		if (!subjects.containsKey(inCode)) {
			Subject newSub = new Subject(inCode, inName, inLevel, groups, inCoreqs);
			
			subjects.put(inCode, newSub);
			return true;
		}
		return false;
	}
	
	/**
	 * @param name
	 * @return
	 */
	public boolean removeSubject(String name) {
		// Pre: el Subject amb nom "name" existeix
		for (String group : subjects.get(name).getGroups()) {
			removeGroup(group);
		}
		subjects.remove(name);
		return true;
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
	
	/**
	 * @param codi
	 * @param group
	 * @param duration
	 * @return
	 */
	public boolean addLecture(Integer codi, String group, Integer duration) {
		if (!lectures.containsKey(group + "-" + codi)) {
			Lecture L = new Lecture(codi, group, duration);
			
			lectures.put(group + "-" + codi, L);
			return true;
		}
		
		return false;
	}
	
	/**
	 * @param name
	 * @return
	 */
	public boolean removeLecture(String name) {
		lectures.remove(name);

		return true;
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
	
	public boolean addRoom(String inCode, Integer inCapacity, Boolean inHasComputers) {
		if (!rooms.containsKey(inCode)) {
			Room r = new Room(inCode, inCapacity, inHasComputers);
			rooms.put(inCode, r);
			
			return true;
		}

		return false;
	}
	
	/**
	 * @param code
	 * @return
	 */
	public boolean removeRoom(String code) {
		rooms.remove(code);
		return true;
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
