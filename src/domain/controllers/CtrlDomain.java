package domain.controllers;

import java.util.Map;
import java.util.Set;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator; 

import org.json.simple.JSONArray; 
import org.json.simple.JSONObject; 
import org.json.simple.parser.*;

import domain.Room;
import domain.Schedule;
import domain.Subject;
import domain.restrictions.NaryRestriction;
import domain.restrictions.UnaryRestriction;
import persistance.CtrlData;
import domain.Group; 

public class CtrlDomain {
	
	private static CtrlDomain instance;
	private CtrlData dataController;
	//The keys of these Maps are always the value.toString() result. Ex: the key of a subject is thatSubject.toString()
	private Map<String, Subject> subjects;
	private Map<String, Room> rooms;
	private Map<String, Group> groups;
	
	//Restrictions for every group
	private Map<String, Map<String, Set<UnaryRestriction>>> unaryRestricions; //Key = group.toString()
	private Map<String, Map<String, Set<NaryRestriction>>> naryRestrictions;
	
	
	private CtrlDomain() {
		dataController = CtrlData.getInstance();
		subjects = new HashMap<String, Subject>();
		rooms = new HashMap<String, Room>();
		groups = new HashMap<String, Group>();
	}
	
	public static CtrlDomain getInstance() {
		if (instance == null)
			instance = new CtrlDomain();
		return instance;
	}
	
	public ArrayList<Room> getRooms() {
		ArrayList<Room> roomsArray = new ArrayList<Room>();
		for (Room room : rooms.values()) {
			roomsArray.add(room);
		}
		return roomsArray;
	}
	
	public ArrayList<Group> getGroups() {
		ArrayList<Group> groupsArray = new ArrayList<Group>();
		for (Group group : groups.values()) {
			groupsArray.add(group);
		}
		return groupsArray;
	}
	
	public Subject getSubject(String s){
		return subjects.get(s);
	}
	
	public Group getGroup(String g){
		return groups.get(g);
	}
	
	public ArrayList<Timeframe> generatePossibleTimeframes(DayOfWeek iniDayOfWeek, DayOfWeek finDayOfWeek, int iniTime, int finTime) {
		ArrayList<Timeframe> possibleTimeframes = new ArrayList<Timeframe>();
		DayOfWeek currentDayOfWeek = iniDayOfWeek;
		finDayOfWeek = finDayOfWeek.plus(1);
		while (currentDayOfWeek!=finDayOfWeek) {
			int currentTime = iniTime;
			while (currentTime <= finTime) {
				possibleTimeframes.add(new Timeframe(currentDayOfWeek, LocalTime.of(currentTime, 0)));
				currentTime++;
			}
			currentDayOfWeek = currentDayOfWeek.plus(1);
		}
		return possibleTimeframes;
	}
	
	//For now every day starts at the same hour and ends at the same hour
	public ArrayList<Calendar> generatePossibleCalendars(int iniDayOfWeek, int finDayOfWeek, int iniHourOfDay, int finHourOfDay) {
		ArrayList<Calendar> possibleCalendars = new ArrayList<Calendar>();
		
		//initialize currentCalendar with first possible day of week
		Calendar currentCalendar = Calendar.getInstance();
		currentCalendar.setTime(new Date(0));
		currentCalendar.set(Calendar.DAY_OF_WEEK, iniDayOfWeek);
		
		//Add all possible calendars
		while (currentCalendar.get(Calendar.DAY_OF_WEEK) <= finDayOfWeek) {	
			//Set hour to first hour of the day
			//HOUR_OF_DAY is in 24 hour format
			currentCalendar.set(Calendar.HOUR_OF_DAY, iniHourOfDay);
			
			//IF day ends at 20:00h, the last hour added to possibleCalendars will be 19:00h
			while (currentCalendar.get(Calendar.HOUR_OF_DAY) < finHourOfDay) {
				//Add currentCalendar to possible Calendars
				possibleCalendars.add((Calendar) currentCalendar.clone());
				//Increase currentCalendar hour by 1
				currentCalendar.add(Calendar.HOUR_OF_DAY, 1);
			}
			//Set currentCalendar to next Day of the week
			currentCalendar.add(Calendar.DAY_OF_WEEK, 1);
		}
		return possibleCalendars;
	}
	
	@SuppressWarnings("rawtypes")
	public boolean importData(String filename) throws ParseException, IOException   {
		 String jsonData = dataController.readData(filename);
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
        	
        	//getting groups
        	Iterator itr2 = jsonGroups.iterator(); 
        	while (itr2.hasNext()) {
        		JSONObject group = (JSONObject) itr2.next();

    			@SuppressWarnings("unchecked")
				Group g = new Group(
    					(String) group.get("code"),
    					((Long) group.get("numPeople")).intValue(),
    					(String) group.get("parentGroupCode"),
    					(String) subject.get("code"),
    					Group.Type.valueOf((String) group.get("type")),
    					Group.DayPeriod.valueOf((String) group.get("dayPeriod")),
    					(ArrayList<Integer>) group.get("lecturesDuration")
    					);
    			groups.put(g.toString(), g);
    			groupsToString.add(g.toString());
        	}
        	@SuppressWarnings("unchecked")
			Subject s = new Subject(
    			(String) subject.get("code"),
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
	
	@SuppressWarnings("unchecked")
	public boolean exportData(String filename) throws IOException {
		// creating JSONObject 
        JSONObject jo = new JSONObject();
        // creating subjects JSONArray
        JSONArray jsonSubjects = new JSONArray();
        
        for (Subject value : subjects.values()) {
        	JSONObject subject = new JSONObject();
        	subject.put("code", value.getCode());
        	subject.put("name", value.getName());
        	subject.put("level", value.getLevel());
        	subject.put("coreqs", value.getAllCorequisits());
        	
        	JSONArray jsonGroups = new JSONArray();
        	
        	ArrayList<Group> groupsFromSubject = this.getGroupsFromSubject(value.toString());
        	for (Group group : groupsFromSubject) {
        		JSONObject jsonGroup = new JSONObject();
        		jsonGroup.put("code", group.getCode());
        		jsonGroup.put("numPeople", group.getNumPeople());
        		jsonGroup.put("parentGroupCode", group.getParentGroupCode());
        		jsonGroup.put("subject", group.getSubject());
        		jsonGroup.put("type", group.getTypeAsString());
        		jsonGroup.put("dayPeriod", group.getDayPeriodAsString());
        		jsonGroup.put("lecturesDuration", group.getLecturesDuration());
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
        return dataController.writeData(filename, jo.toJSONString(0));        
	}
	
	@SuppressWarnings("unchecked")
	public Schedule importSchedule(String filename) throws ParseException, FileNotFoundException {
		String jsonData = dataController.readData(filename);
		Object obj = new JSONParser().parse(jsonData);
	
		// typecasting obj to JSONObject 
	   	JSONObject jo = (JSONObject) obj; 
	   	Map<Timeframe, Map<String, String>> scheduleAsMap = new HashMap<Timeframe, Map<String, String>>();
	   	
	   	JSONArray jsonTimeframes = (JSONArray) jo.get("timeFrames"); 
    	
    	//getting groups
    	Iterator itr1 = jsonTimeframes.iterator(); 
    	while (itr1.hasNext()) {
    		JSONObject timeframeJSON = (JSONObject) itr1.next();
    		Timeframe timeframe = new Timeframe(DayOfWeek.valueOf((String) timeframeJSON.get("dayOfWeek")), (String) timeframeJSON.get("timeStart"));
    		scheduleAsMap.put(timeframe, (Map<String, String>) timeframeJSON.get("lectures"));
    	}
	   	Schedule schedule = new Schedule(scheduleAsMap);
	   	return schedule;
	}
	
	@SuppressWarnings("unchecked")
	public boolean exportSchedule(String filename, Schedule schedule) throws IOException  {
		Map<String, Map<String, String>> prettySchedule = new HashMap<String, Map<String, String>>();
		Map<Timeframe, Map<String, String>> scheduleAsMap = schedule.getScheduleAsMap();

		JSONObject jo = new JSONObject();
        JSONArray jsonTfs = new JSONArray();
        
        for (Timeframe tf : scheduleAsMap.keySet()) {
        	JSONObject room = new JSONObject();
        	room.put("dayOfWeek", tf.getDayOfWeek().toString());
        	room.put("timeStart", tf.timeToString());
        	JSONObject classes = (JSONObject) scheduleAsMap.get(tf);
        	room.put("lectures", classes);
        	
        	jsonTfs.add(room);
        }
        
        //Add Subjects and Rooms to final object
        jo.put("timeFrames", jsonTfs);
		//Send to data controller to write
        return dataController.writeData(filename, jo.toJSONString(0));        
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
	
	
}