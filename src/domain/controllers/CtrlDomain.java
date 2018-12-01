package domain.controllers;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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

/** Controlador principal de domini de l'aplicació.
 * @author Carlos Bergillos Varela
*/

public class CtrlDomain {
	
	/** Instancia d'aquesta classe.
	*/
	private static CtrlDomain instance;
	
	/** Controlador de dades.
	*/
	private CtrlData dataController;
	
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
	
	/** Horari 
	*/
	private Schedule schedule;
	
	/** Map de restriccions unaries de l'entorn  
	*/
	private Map<String, Map<String, UnaryRestriction>> unaryRestrictions; //Key = group.toString()
	
	/** Map de restriccions n-aries de l'entorn  
	*/
	private Map<String, NaryRestriction> naryRestrictions;
	
	/** Constructora estandard.
	*/
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
		naryRestrictions.put(pgor.toString(), pgor);
		CorequisitRestriction cr = new CorequisitRestriction();
		naryRestrictions.put(cr.toString(), cr);
		SubjectLevelRestriction slr = new SubjectLevelRestriction();
		naryRestrictions.put(slr.toString(), slr);
		LectureFromSameGroupOverlapRestriction lfgor = new LectureFromSameGroupOverlapRestriction();
		naryRestrictions.put(lfgor.toString(), lfgor);
	}
	
	/**
	 * Retorna la instancia d'aquesta classe.
	 * @return {@link CtrlDomain#instance}
	 */
	public static CtrlDomain getInstance() {
		if (instance == null)
			instance = new CtrlDomain();
		return instance;
	}
	
	/**
	 * Retorna una llista de codis de les aules de l'entorn del domini.
	 * @return Retorna la llista de codis de les aules de l'entorn del domini.
	 */
	public ArrayList<String> getRoomNamesList() {
		ArrayList<String> list = new ArrayList<String>(rooms.keySet());
		Collections.sort(list);
		return list;
	}
	
	/**
	 * Retorna una llista de codis dels grups de l'entorn del domini.
	 * @return Retorna una llista de codis dels grups de l'entorn del domini.
	 */
	public ArrayList<String> getGroupNamesList() {
		return new ArrayList<String>(groups.keySet());
	}
	
	/**
	 * Retorna una llista de codis d'assignatures de l'entorn del domini.
	 * @return Retorna la llista de codis d'assignatures de l'entorn del domini.
	 */
	public ArrayList<String> getSubjectNamesList() {
		return new ArrayList<String>(subjects.keySet());
	}
	
	/**
	 * Retorna una llista de codis de les restriccions n-àries de l'entorn del domini.
	 * @return Retorna la llista de codis de les restriccions n-àries de l'entorn del domini.
	 */
	public ArrayList<String> getNaryRestrictions() {
		return new ArrayList<String>(naryRestrictions.keySet());
	}
	
	/**
	 * Borra les restriccions un-àries.
	 * @return Retorna el map de restriccions un-àries buit.
	 */
	public void erase() {
		//naryRestrictions = new HashMap<String, NaryRestriction>();
		unaryRestrictions = new HashMap<String, Map<String, UnaryRestriction>>();
	}
	
	/**
	 * Retorna una llista de codis de les restriccions un-àries de l'entorn del domini.
	 * @return Retorna la llista de codis de les restriccions un-àries de l'entorn del domini.
	 */
	public Map<String, String> getUnaryRestrictions() {
		Map<String, String> result = new HashMap<String, String>();
		for (String group : unaryRestrictions.keySet()) {
			for (String name : unaryRestrictions.get(group).keySet()) {
				result.put(group, name);
			}
		}
		
		return result;
	}
	
	/**
	 * Genera un horari amb les condicions de l'entorn.
	 * @return true si s'ha trobat un horari valid, sino false.
	 */
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
	
	/**
	 * Retorna l'horari del domini converit en Json.
	 * @return L'horari del domini converit en String Json.
	 */
	public String scheduleToJsonString() {
		return schedule.toJsonString();
	}
	
	/**
	 * Imprimeix l'horari en una taula.
	 */
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
						String text = entry.getValue()[j][i];
						if (text!=null && !text.isEmpty()) {
							found = true;
								String[] split = text.split("-");
								String ntext = split[0]+" "+split[1]+split[2].substring(0, 1);
								System.out.print("  " + entry.getKey()+ ": " + ntext);
								for (int k = (2 + entry.getKey().length() + 2 + ntext.length()); k < 20; ++k) System.out.print(" ");
								System.out.print("|");
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
			if (!after && i != 11) System.out.println("|------+--------------------+--------------------+--------------------+--------------------+--------------------|");
		}
		System.out.println("|---------------------------------------------------------------------------------------------------------------|");
	}

	/**
	 * Importa un entorn (aules, assignatures, aules) desde un arxiu.
	 * @param filename Nom de l'entorn a importar.
	 * @return true si s'ha importat correctament, sino false.
	 */
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
    			
			DayPeriodRestriction dpr = new DayPeriodRestriction(6, g.getDayPeriod());
			restrictions.put(dpr.toString(), dpr);
  
			/*
    			if (g.toString().equals("FM-10-THEORY")) {
    				SpecificDayOrHourRestriction sdohr = new SpecificDayOrHourRestriction(2, 2);
    				restrictions.put(sdohr.toString(), sdohr);
    			}
			*/
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
	
	/**
	 * Importa un horari desde un arxiu.
	 * @param filename Nom de l'arxiu d'horari a importar.
	 * @return true si s'ha importat correctament, sino false
	 */
	public boolean importSchedule(String filename) throws ParseException, FileNotFoundException {
		String jsonData = dataController.readSchedule(filename);
		schedule = new Schedule(jsonData);
	   	return true;
	}
	
	/**
	 * Guarda l'horari de l'entorn a un arxiu.
	 * @param filename Nom amb el que guardar l'horari.
	 * @return true si s'ha guardat correctament.
	 */
	public boolean exportSchedule(String filename) throws IOException  {
        return dataController.writeSchedule(filename, schedule.toJsonString());
	}
	
	/**
	 * Retorna una llista de noms d'arxius d'entorn disponibles.
	 * @return Una llista de noms d'arxius d'entorn disponibles.
	 */
	public List<String> getEnvironmentFilesList() {
		return dataController.getEnvironmentFilesList();
	}
	
	/**
	 * Retorna una llista de noms d'arxius d'horari disponibles.
	 * @return Una llista de noms d'arxius d'horari disponibles.
	 */
	public List<String> getScheduleFilesList() {
		return dataController.getScheduleFilesList();
	}
	
	public String[][] getScheduleMatrix() {
		return (String[][]) schedule.getSchedule().values().toArray()[0];
	}
}
