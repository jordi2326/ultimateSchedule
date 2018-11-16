package domain.controllers;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray; 
import org.json.simple.JSONObject; 
import org.json.simple.parser.*;

import domain.classes.Group;
import domain.classes.Lecture;
import domain.classes.PosAssig;
import domain.classes.Room;
import domain.classes.Schedule;
import domain.classes.Subject;
import domain.classes.Group.DayPeriod;
import domain.classes.Group.Type;
import domain.classes.restrictions.CorequisitRestriction;
import domain.classes.restrictions.DayPeriodRestriction;
import domain.classes.restrictions.LectureFromSameGroupOverlapRestriction;
import domain.classes.restrictions.SubjectLevelRestriction;
import domain.classes.restrictions.NaryRestriction;
import domain.classes.restrictions.OccupiedRoomRestriction;
import domain.classes.restrictions.ParentGroupOverlapRestriction;
import domain.classes.restrictions.SpecificDayOrHourRestriction;
import domain.classes.restrictions.UnaryRestriction;
import persistance.CtrlData;

public class CtrlDomain {
	
	private static CtrlDomain instance;
	private CtrlData dataController;
	//The keys of these Maps are always the value.toString() result. Ex: the key of a subject is thatSubject.toString()
	private Map<String, Subject> subjects;
	private Map<String, Room> rooms;
	private Map<String, Group> groups;
	private Map<String, Lecture> lectures;
	private Schedule schedule;
	//Restrictions for every group
	private Map<String, Map<String, UnaryRestriction>> unaryRestrictions; //Key = group.toString()
	private Map<String, NaryRestriction> naryRestrictions;
	
	
	private CtrlDomain() {
		dataController = CtrlData.getInstance();
		subjects = new HashMap<String, Subject>();
		rooms = new HashMap<String, Room>();
		groups = new HashMap<String, Group>();
		lectures = new HashMap<String, Lecture>();
		unaryRestrictions = new HashMap<String, Map<String, UnaryRestriction>>();
		naryRestrictions = new HashMap<String, NaryRestriction>();
		
		OccupiedRoomRestriction ocrr = new OccupiedRoomRestriction();
		naryRestrictions.put(ocrr.toString(), ocrr);
		ParentGroupOverlapRestriction pgor = new ParentGroupOverlapRestriction();
		//naryRestrictions.put(pgor.toString(), pgor);
		CorequisitRestriction cr = new CorequisitRestriction();
		naryRestrictions.put(cr.toString(), cr);
		SubjectLevelRestriction slr = new SubjectLevelRestriction();
		//naryRestrictions.put(slr.toString(), slr);
		LectureFromSameGroupOverlapRestriction lfgor = new LectureFromSameGroupOverlapRestriction();
		//naryRestrictions.put(lfgor.toString(), lfgor);
	}
	
	public static CtrlDomain getInstance() {
		if (instance == null)
			instance = new CtrlDomain();
		return instance;
	}
	
	public ArrayList<String> getRoomNamesList() {
		return new ArrayList<String>(rooms.keySet());
	}
	
	public ArrayList<String> getSubjectNamesList() {
		return new ArrayList<String>(subjects.keySet());
	}
	
	public boolean generateSchedule() {
		CtrlSchedule ctS = CtrlSchedule.getInstance();
		schedule = new Schedule();
		//Filtrem restriccions unaries
		Map<String, Map<String, UnaryRestriction>> enabledUnaryRestrictions = new HashMap<String, Map<String, UnaryRestriction>>();
		for (String g : unaryRestrictions.keySet()) {
			Map<String, UnaryRestriction> restrictions = new HashMap<String, UnaryRestriction>();
			for (UnaryRestriction r : unaryRestrictions.get(g).values()) {
				if (r.isEnabled()) {
					restrictions.put(r.toString(), r);
				}
			}
			if (!restrictions.isEmpty()) {
				enabledUnaryRestrictions.put(g, restrictions);
			}
		}
		//Filtrem restriccions naries
		Map<String, NaryRestriction> enabledNaryRestrictions = new HashMap<String, NaryRestriction>();
		for (NaryRestriction r : naryRestrictions.values()) {
			if (r.isEnabled()) {
				enabledNaryRestrictions.put(r.toString(), r);
			}
		}
		return ctS.generateSchedule(enabledUnaryRestrictions, enabledNaryRestrictions, groups, rooms, subjects, lectures, schedule);
	}
	
	public String scheduleToJsonString() {
		return schedule.toJsonString();
	}
	
	private String firstOf(Type dp) {
		if (dp.equals(Type.LABORATORY)) return "L";
		if (dp.equals(Type.THEORY)) return "T";
		if (dp.equals(Type.PROBLEMS)) return "P";
		if (dp.equals(Type.PRACTICES)) return "PRAC";
		return "";
	}

	public void printSchedule() {
		Schedule copy = new Schedule(schedule.toJsonString());
		
		Map<String, String[][]> SCH = new HashMap<String, String[][]>(copy.getSchedule());
		System.out.println("|---------------------------------------------------------------------------------------------------------------|");
		System.out.println("|      |      MONDAY        |      TUESDAY       |     WEDNESDAY      |      THURSDAY      |       FRIDAY       |");
		System.out.println("|------+--------------------+--------------------+--------------------+--------------------+--------------------|");
		boolean after = false;
		ArrayList<Integer> posL = new ArrayList<Integer>();
		posL.add(20);
		for (int i = 0; i < 12; i++) {
			if (after) {
				i--;
				System.out.print("|      |");
			} else {
				if (i + 8 < 10) System.out.print("|0" + (i + 8) + ":00 |");
				else System.out.print("|" + (i + 8) + ":00 |");
			}
			after = false;
			for (int j = 0; j < 5; j++) {
				boolean found = false;
				for (Map.Entry<String, String[][]> entry : SCH.entrySet()) {
					if (!found) {
						// String room = entry.getKey();
						String lecture = entry.getValue()[j][i];
						//Lecture l = lectures.get(lecture.toString());
						Group g = groups.get(lecture);
						if (lecture != null) {
							found = true;
							if (j == 0) {
								System.out.print("      " + g.getSubject() + " " + g.getCode() + " " + firstOf(g.getType()));
								for (int k = (8 + g.getSubject().length() + g.getCode().length() + 1); k < 20; ++k) System.out.print(" ");
								System.out.print("|");
							} else {
								System.out.print("      " + g.getSubject() + " " + g.getCode() + " " + firstOf(g.getType()));
								for (int k = (8 + g.getSubject().length() + g.getCode().length() + 1); k < 20; ++k) System.out.print(" ");
								System.out.print("|");
							}
							entry.getValue()[j][i] = null;
						}
					} else if (!after) {
						// String room = entry.getKey();
						String lecture = entry.getValue()[j][i];
						if (lecture != null) {
							after = true;
						}
					}
				}
				if (!found) System.out.print("                    |");
			}
			System.out.println("");
			if (!after) System.out.println("|------+--------------------+--------------------+--------------------+--------------------+--------------------|");
		}
		System.out.println("|---------------------------------------------------------------------------------------------------------------|");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public boolean importEnvironment(String filename) throws ParseException, IOException   {
		 String jsonData = dataController.readEnvironment(filename);
		 Object obj = new JSONParser().parse(jsonData);
		 
		// typecasting obj to JSONObject 
        JSONObject jo = (JSONObject) obj; 
        
        // getting subjects 
        JSONArray jsonSubjects = (JSONArray) jo.get("subjects");
        
        Map<String, Subject> subjects = new HashMap<String, Subject>();
    	Map<String, Group> groups = new HashMap<String, Group>();	
        
        Iterator itr1 = jsonSubjects.iterator(); 

        while (itr1.hasNext()) {
        	JSONObject subject = (JSONObject) itr1.next();
        	JSONArray jsonGroups = (JSONArray) subject.get("groups"); 
        	ArrayList<String> groupsToString = new ArrayList<String>();
        	String scode = (String) subject.get("code");
        	//getting groups
        	Iterator itr2 = jsonGroups.iterator(); 
        	while (itr2.hasNext()) {
        		JSONObject group = (JSONObject) itr2.next();
        		ArrayList<String> ls = new ArrayList<String>();
        		ArrayList<Long> durations = (ArrayList<Long>) group.get("lecturesDuration");
        		
        		String gcode = (String) group.get("code");
        		for(int i = 0; i < durations.size(); i++){
        			Lecture l = new Lecture(i, scode +"-"+ gcode +"-"+ group.get("type"), durations.get(i).intValue());
        			lectures.put(l.toString(), l);
        			ls.add(l.toString());
        		}
    			Group g = new Group(
    					gcode,
    					((Long) group.get("numPeople")).intValue(),
    					(String) group.get("parentGroupCode"),
    					scode,
    					Group.Type.valueOf((String) group.get("type")),
    					Group.DayPeriod.valueOf((String) group.get("dayPeriod")),
    					ls);
    			groups.put(g.toString(), g);
    			groupsToString.add(g.toString());
    			//Afegim la restriccio d'aquest grup de mati o tarda o indiferent
    			
    			Map<String, UnaryRestriction> restrictions = new HashMap<String, UnaryRestriction>();
    			//Potser cal fer lo de clonarlo amb collection stream perque no siguin el mateix
    			DayPeriodRestriction dpr = new DayPeriodRestriction(6, g.getDayPeriod());
    			if (!g.getDayPeriod().equals(Group.DayPeriod.INDIFERENT)) {
    				restrictions.put(dpr.toString(), dpr);
    			}
    			unaryRestrictions.put(g.toString(), restrictions); 
        		
        	}
        	Subject s = new Subject(
				scode,
				(String) subject.get("name"),
				(String) subject.get("level"),
				groupsToString,
				(ArrayList<String>) subject.get("coreqs")
    			);
        	subjects.put(s.toString(), s);
        }
        // getting rooms 
        JSONArray jsonRooms = (JSONArray) jo.get("rooms");
        Map<String, Room> rooms = new HashMap<String, Room>();
        Iterator itr3 = jsonRooms.iterator(); 
        while (itr3.hasNext()) {
        	JSONObject room = (JSONObject) itr3.next();
        	Room r = new Room(
        			(String) room.get("code"),
        			((Long) room.get("capacity")).intValue(),
        			(Boolean) room.get("hasComputers")
        			);
        	rooms.put(r.toString(), r);
        }
        
        this.subjects = subjects;
        this.groups = groups;
        this.rooms = rooms;
		return true;
	}
	
	/**Not used anywhere
	 * 
	@SuppressWarnings("unchecked")
	public boolean exportEnvironment(String filename) throws IOException {
		// creating JSONObject 
        JSONObject jo = new JSONObject();
        // creating subjects JSONArray
        JSONArray jsonSubjects = new JSONArray();
        
        for (Subject value : subjects.values()) {
        	JSONObject subject = new JSONObject();
        	subject.put("code", value.getCode());
        	subject.put("name", value.getName());
        	subject.put("level", value.getLevel());
        	subject.put("coreqs", value.getCoreqs());
        	
        	JSONArray jsonGroups = new JSONArray();
        	
        	ArrayList<Group> groupsFromSubject = this.getGroupsFromSubject(value.toString());
        	for (Group group : groupsFromSubject) {
        		JSONObject jsonGroup = new JSONObject();
        		jsonGroup.put("code", group.getCode());
        		jsonGroup.put("numPeople", group.getNumOfPeople());
        		jsonGroup.put("parentGroupCode", group.getParentGroupCode());
        		jsonGroup.put("subject", group.getSubject());
        		jsonGroup.put("type", group.getType().toString());
        		jsonGroup.put("dayPeriod", group.getDayPeriod().toString());
        		//jsonGroup.put("lecturesDuration", ); 
        		jsonGroups.add(jsonGroup);
        	}
        	
        	subject.put("groups", jsonGroups);	
        	
        	jsonSubjects.add(subject);
        }
        
        // creating rooms JSONArray
        JSONArray jsonRooms = new JSONArray();
        for (Room value : rooms.values()) {
        	JSONObject room = new JSONObject();
        	room.put("code", value.getCode());
        	room.put("capacity", value.getCapacity());
        	room.put("hasComputers", value.hasComputers());
        	
        	jsonRooms.add(room);
        }
        
        //Add Subjects and Rooms to final object
        jo.put("rooms", jsonRooms);
        jo.put("subjects", jsonSubjects);
    	
        //Send to data controller to write
        return dataController.writeEnvironment(filename, jo.toJSONString(0));        
	}
	private ArrayList<Group> getGroupsFromSubject(String subject) {
		Subject auxSubject = subjects.get(subject);
		ArrayList<String> subjectGroupsToString = auxSubject.getGroups();
		ArrayList<Group> groupsFromSubject = new ArrayList<Group>();
		for (String groupToString : subjectGroupsToString) {
			groupsFromSubject.add(groups.get(groupToString));
		}
		return groupsFromSubject;
	}
	**/
	
	public boolean importSchedule(String filename) throws ParseException, FileNotFoundException {
		String jsonData = dataController.readSchedule(filename);
		schedule = new Schedule(jsonData);
	   	return true;
	}
	
	public boolean exportSchedule(String filename) throws IOException  {
        return dataController.writeSchedule(filename, schedule.toJsonString());
	}
	
	public List<String> getEnvironmentFilesList(){
		return dataController.getEnvironmentFilesList();
	}
	
	public List<String> getScheduleFilesList(){
		return dataController.getScheduleFilesList();
	}
	
}