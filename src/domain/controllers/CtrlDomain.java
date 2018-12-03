package domain.controllers;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray; 
import org.json.simple.JSONObject; 
import org.json.simple.parser.*;

import domain.classes.Environment;
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
	
	/** Horari 
	*/
	private Schedule schedule;
	
	/*
	public static void main(String[] args) throws Throwable, IOException {
		CtrlDomain cd = CtrlDomain.getInstance();
		cd.importEnvironment("Q1+Q2.json");
		Environment env = Environment.getInstance();
		
		int i = 0;
		
	}*/
	
	/** Constructora estandard.
	*/
	private CtrlDomain() {
		dataController = CtrlData.getInstance();	
		schedule = new Schedule();
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
	 * Genera un horari amb les condicions de l'entorn.
	 * @return true si s'ha trobat un horari valid, sino false.
	 */
	public boolean generateSchedule() {
		CtrlSchedule ctS = CtrlSchedule.getInstance();
		//Filtrem restriccions unaries
		boolean a = ctS.generateSchedule(schedule);
		return a;
	}
	
	/**
	 * Retorna l'horari del domini converit en Json.
	 * @return L'horari del domini converit en String Json.
	 */
	public String scheduleToJsonString() {
		return schedule.toJsonString();
	}
	
	public void erase() {
		schedule = new Schedule();
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
		
		// Imprimir l'11
		after = true;
		while (after) {
			System.out.print("|      |");
			after = false;
			for (int j = 0; j < 5; j++) {
				boolean found = false;
				for (Map.Entry<String, String[][]> entry : SCH.entrySet()) {
					if (!found) {
						String text = entry.getValue()[j][11];
						if (text!=null && !text.isEmpty()) {
							found = true;
								String[] split = text.split("-");
								String ntext = split[0]+" "+split[1]+split[2].substring(0, 1);
								System.out.print("  " + entry.getKey()+ ": " + ntext);
								for (int k = (2 + entry.getKey().length() + 2 + ntext.length()); k < 20; ++k) System.out.print(" ");
								System.out.print("|");
							entry.getValue()[j][11] = null;
						}
					} else if (!after) {
						// String room = entry.getKey();
						String lecture = entry.getValue()[j][11];
						if (lecture != null) {
							after = true;
						}
					}
				}
				if (!found) System.out.print("                    |");
			}
			System.out.println("");
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
		
		 Environment env = Environment.getInstance();
		 
		 String jsonData = dataController.readEnvironment(filename);
		 Object obj = new JSONParser().parse(jsonData);
		 
		// typecasting obj to JSONObject 
        JSONObject jo = (JSONObject) obj; 
        
        // getting subjects 
        JSONArray jsonSubjects = (JSONArray) jo.get("subjects");
        
        
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
        			//lectures.put(l.toString(), l);
        			env.addLecture(l);
        			ls.add(l.toString());
        		}
    			Group g = new Group(
    					gcode,
    					((Long) group.get("numPeople")).intValue(),
    					(String) group.get("parentGroupCode"),
    					scode,
    					(Boolean) group.get("needsComputers"), //hopefully aixi ja funcionara
    					Group.Type.valueOf((String) group.get("type")),
    					Group.DayPeriod.valueOf((String) group.get("dayPeriod")),
    					ls);
    			//groups.put(g.toString(), g);
    			env.addGroup(g);    			
    			groupsToString.add(g.toString());
    			//Afegim la restriccio d'aquest grup de mati o tarda o indiferent
    						
			DayPeriodRestriction dpr = new DayPeriodRestriction(6, g.getDayPeriod());
			env.addUnaryRestriction(g.toString(), dpr);
  
			/*
    			if (g.toString().equals("FM-10-THEORY")) {
    				SpecificDayOrHourRestriction sdohr = new SpecificDayOrHourRestriction(2, 2);
    				restrictions.put(sdohr.toString(), sdohr);
    			}
			*/
        		
        	}
        	Subject s = new Subject(
				scode,
				(String) subject.get("name"),
				(String) subject.get("level"),
				groupsToString,
				(ArrayList<String>) subject.get("coreqs")
    			);
        	env.addSubject(s);
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
        	//rooms.put(r.toString(), r);
        	env.addRoom(r);
        }
       
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
	
	public ArrayList<String[]>[][] getScheduleMatrix() {
		ArrayList<String[]>[][] data = new ArrayList[12][5];
		for(Entry<String, String[][]> d : schedule.getSchedule().entrySet()) {
			String[][] matrix = d.getValue();
			for (int i = 0; i < matrix[0].length; i++) {
                for (int j = 0; j < matrix.length; j++) {
                	if(data[i][j]==null) data[i][j] = new ArrayList<String[]>();
                	if(matrix[j][i]!=null && !matrix[j][i].isEmpty()) data[i][j].add(new String[]{matrix[j][i], d.getKey()});
                }
            }
		}
		
		return data;
	}
}
