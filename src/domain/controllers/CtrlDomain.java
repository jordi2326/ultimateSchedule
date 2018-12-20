package domain.controllers;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.json.simple.JSONArray; 
import org.json.simple.JSONObject; 
import org.json.simple.parser.*;
import domain.classes.Environment;
import domain.classes.Group;
import domain.classes.Lecture;
import domain.classes.Room;
import domain.classes.Schedule;
import domain.classes.Subject;
import domain.classes.restrictions.DayPeriodRestriction;
import domain.classes.restrictions.NaryRestriction;
import domain.classes.restrictions.SpecificDayOrHourRestriction;
import domain.classes.restrictions.UnaryRestriction;
import persistance.CtrlData;

/** Controlador principal de domini de l'aplicaciï¿½.
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
	
	/** Escenari
	 * 
	 */
	private Environment environment;
	
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
	public boolean importEnvironment(String filename, boolean isFullpath) throws ParseException, IOException   {
		
		 Environment env = Environment.getInstance();
		 env.erase();
		 
		 String jsonData = dataController.readEnvironment(filename, isFullpath);
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
  
        	}
        	env.addSubject(scode, (String) subject.get("name"), (String) subject.get("level"), groupsToString, (ArrayList<String>) subject.get("coreqs"));
        }
        // getting rooms 
        JSONArray jsonRooms = (JSONArray) jo.get("rooms");
        Map<String, Room> rooms = new HashMap<String, Room>();
        Iterator itr3 = jsonRooms.iterator(); 
        while (itr3.hasNext()) {
        	JSONObject room = (JSONObject) itr3.next();
        	//rooms.put(r.toString(), r);
        	env.addRoom((String) room.get("code"), ((Long) room.get("capacity")).intValue(), (Boolean) room.get("hasComputers"));
        }
        
        // getting subjects 
        JSONArray jsonRestrictions = (JSONArray) jo.get("restrictions");
        
        
        Iterator itr4 = jsonRestrictions.iterator(); 
        
        while (itr4.hasNext()) {
        	JSONObject restriction = (JSONObject) itr4.next();
        	String restrName = (String) restriction.get("name");
        	// if (restrName.equals(SpecificDayOrHourRestriction.class.getSimpleName())) {
        	if (true) {
        		JSONObject info = (JSONObject) restriction.get("info");
            	String restrGroup = (String) info.get("group");
            	Integer day = ((Long) info.get("day")).intValue();
            	Integer hour = ((Long) info.get("hour")).intValue();
            	SpecificDayOrHourRestriction r = new SpecificDayOrHourRestriction(day, hour);
            	env.addUnaryRestriction(restrGroup, r);
        	}
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
	public boolean importSchedule(String filename, boolean isFullpath) throws ParseException, FileNotFoundException {
		String jsonData = dataController.readSchedule(filename, isFullpath);
		schedule = new Schedule(jsonData);
	   	return true;
	}
	
	/**
	 * Guarda l'horari de l'entorn a un arxiu.
	 * @param filename Nom amb el que guardar l'horari.
	 * @return true si s'ha guardat correctament.
	 */
	public boolean exportSchedule(String filename, boolean isFullpath) throws IOException  {
        return dataController.writeSchedule(filename, schedule.toJsonString(), isFullpath);
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
			System.out.println(d.getKey()+" "+d.getValue()[0][0]+" "+d.getValue()[0][1]);
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
	
	// Funcions per comunicar-se amb la capa de presentaciï¿½
	
		// ROOM
		public Set<String> getRoomNames() {
			Environment env = Environment.getInstance();
			return new TreeSet<String>(env.getAllRooms());
		}
		
		public String[] getRoomInfo(String room) {
			 /* ********* ORDRE *********
			  * param code			Codi de l'aula.
			  * param capacity 		Capacitat de l'aula.
			  * param hasComputers 	Indica si l'aula tï¿½ ordinadors o no.
			  * ************************* */
			Environment env = Environment.getInstance();
			
			String[] infoRoom = new String[3];
			infoRoom[0] = env.getRoomCode(room);
			infoRoom[1] = env.getRoomCapacity(room).toString();
			infoRoom[2] = env.roomHasComputers(room).toString();
			
			return infoRoom;
		};
		
		// GROUP
		public Set<String> getGroupNames() {
			Environment env = Environment.getInstance();
			return new TreeSet<String>(env.getAllGroups());
		}
		
		public String[] getGroupInfo(String group) {
			 /* ********* ORDRE *********
			  * param code				Codi del grup.
			  * param numPeople			Nï¿½m. persones en el grup.
			  * param parentGroupCode	Codi del grup pare.
			  * param subject			Assignatura a la que pertany.
			  * param type				Tipus de grup.
			  * param dayPeriod			Perï¿½ode del dia.
			  * param needsComputers	True si necessita ordinadors.
			  * ************************* */
			Environment env = Environment.getInstance();
			
			String[] infoGroup = new String[7];
			infoGroup[0] = env.getGroupCode(group);
			infoGroup[1] = env.getGroupNumOfPeople(group).toString();
			infoGroup[2] = env.getGroupParentGroupCode(group);
			infoGroup[3] = env.getGroupSubject(group);
			infoGroup[4] = env.getGroupType(group).toString();
			infoGroup[5] = env.getGroupDayPeriod(group).toString();
			infoGroup[6] = env.groupNeedsComputers(group).toString();
			
			return infoGroup;
		};
		
		public Set<String> getGroupsNamesFromSuject(String s) {
			Environment env = Environment.getInstance();
			return new TreeSet<String>(env.getSubjectGroups(s));
		}
		
		// SUBJECT
		public Set<String> getSubjectNames() {
			Environment env = Environment.getInstance();
			return new TreeSet<String>(env.getAllSubjects());
		}
		
		public Object[] getSubjectInfo(String sub) {
			 /* ********* ORDRE *********
			  * param code		Codi de l'assignatura.
			  * param name		Nom complet de l'assignatura.
			  * param level		Nivell de l'assignatura.
			  * param groups	Grups de l'assignatura.
			  * param coreqs	Assignatues corequisites de l'assignatura que estem creant.
			  * ************************* */
			Environment env = Environment.getInstance();
			
			Object[] infoSub = new Object[] {
				env.getSubjectCode(sub),
				env.getSubjectName(sub),
				env.getSubjectLevel(sub),
				env.getSubjectGroups(sub),
				env.getSubjectCoreqs(sub)
			};
			
			return infoSub;
		};
		
		// RESTRICTION
		public Set<String> getRestrictionNames() {
			Environment env = Environment.getInstance();
			
			TreeSet<String> R = new TreeSet<String>();
			
			for (String group : env.getAllGroups()) {
				if (env.groupHasUnaryRestrictions(group)) R.addAll(env.getGroupUnaryRestrictions(group));
				if (env.groupHasNaryRestrictions(group)) R.addAll(env.getGroupNaryRestrictions(group));
			}
			
			return R;
		}
		
		public Set<String> getRestrictionNamesView() {
			Environment env = Environment.getInstance();
			
			TreeSet<String> R = new TreeSet<String>();
			
			for (String group : env.getAllGroups()) {
				if (env.groupHasUnaryRestrictions(group)) {
					Map<String, Map<String, UnaryRestriction>> allUnary = env.getUnaryRestrictions();
					for (UnaryRestriction unary : allUnary.get(group).values()) {
						R.add(unary.stringView());
					}
				}
				if (env.groupHasNaryRestrictions(group)) {
					Map<String, Map<String, NaryRestriction>> allNary = env.getNaryRestrictions();
					for (NaryRestriction nary : allNary.get(group).values()) {
						R.add(nary.stringView());
					}
				}
			}
			
			return R;
		}
		
		public String[] getRestrictionInfo(String res) {
			 /* ********* ORDRE *********
			  * param negotiable	Indica si la restricciï¿½ ï¿½s negociable.
			  * param enabled		Indica si la restricciï¿½ estï¿½ activada.
			  * ************************* */
			Environment env = Environment.getInstance();
			
			String[] infoRes = new String[2];
			
			for (Map<String, UnaryRestriction> groupRes : env.getUnaryRestrictions().values()) {
				for (String rest : groupRes.keySet()) {
					if (rest == res) {
						Boolean nego = groupRes.get(rest).isNegotiable();
						infoRes[0] = nego.toString();
						
						Boolean ena = groupRes.get(rest).isEnabled();
						infoRes[1] = ena.toString();
						
						return infoRes;
					}
				}
			}
			
			for (Map<String, NaryRestriction> groupRes : env.getNaryRestrictions().values()) {
				for (String rest : groupRes.keySet()) {
					if (rest == res) {
						Boolean nego = groupRes.get(rest).isNegotiable();
						infoRes[0] = nego.toString();
						
						Boolean ena = groupRes.get(rest).isEnabled();
						infoRes[1] = ena.toString();
						
						return infoRes;
					}
				}
			}
			
			// No existeix (error)
			//System.out.println("error");
			
			String[] error = new String[0];
			return error;
		};
		
		/** Elimina un grup en un dia i aula determinats.
		*   @param duration Duraciï¿½ del grup.
		*	@param room		Aula on eliminarem el grup.
		*	@param day		Dia on eliminarem el grup.
		*	@param hour		Hora on eliminarem el grup.
		*	@return Si existeix l'aula, eliminem el grup en qï¿½estiï¿½ i retornem true. Fals en cas contrari.
		*/
		public boolean removeLecture(int duration, String room, int day, int hour) {
			for (int i = 0; i < duration; i++) {
				boolean removeOne = schedule.removeLecture(room, day, hour + i);
				if (!removeOne) return false;
			}
			return true;
		}
		
		/** Afageix un grup en un dia i aula determinats.
		*   @param duration Duraciï¿½ del grup.
		*	@param room		Aula on eliminarem el grup.
		*	@param day		Dia on eliminarem el grup.
		*	@param hour		Hora on eliminarem el grup.
		*	@return True si s'ha pogut afegir el grup. Fals en cas contrari.
		*/
		public boolean putLecture(int duration, String room, int day, int hour) {
			String lecture = schedule.getSchedule().get(room)[day][hour];
			for (int i = 0; i < duration; i++) {
				boolean removeOne = schedule.putLecture(room, day, hour + i, lecture);
				if (!removeOne) return false;
			}
			return true;
		}
		
		public boolean validateAllRestrictions(String lecture, int day, int hour, String room, int duration) { // On ha d'anar
			Environment env = Environment.getInstance();
			System.out.println(lecture);
			String g = env.getLectureGroup(lecture);
			Map<String, String[][]> sche = schedule.getSchedule();
			
			//String g, String restr, String room, int day, int hour, String lecture, Integer d, Integer h, String r, String l
			
			for (String r : sche.keySet()) { // Restriccions nàries
				// Per cada room
				String[][] rm = sche.get(r);
						//env.getLectureGroup(lecture);
				
				for (int i = 0; i < 12; i++) {
					String groupCompare = rm[i][day];
					// Per cada hora
					if (groupCompare != null) {
						for (String restr : env.getGroupNaryRestrictions(lecture)) {
							// Coses que estàz intentant inserir     |      Coses amb qui ho compares
							if (!env.validateGroupNaryRestriction(g, restr, room, day, hour, lecture, day, i, r, groupCompare)) return false;
						}
					}
				}
			}
			
			for (String restr : env.getGroupUnaryRestrictions(lecture)) { // Restriccions unàries
				if (!env.validateGroupUnaryRestriction(lecture, room, day, hour, duration)) return false;
			}
			
			return true;
		}
		
		/** Mou un grup de dia, aula i hora determinats
		*   @param duration Duraciï¿½ del grup.
		*	@param iniDay		Dia on estï¿½ actualment el grup.
		*	@param fiDay		Dia on anirï¿½ el grup si es pot.
		*	@param iniHour		Hora on estï¿½ actualment el grup.
		*	@param fiHour		Hora on anirï¿½ el grup si es pot.
		*	@param iniRoom		Aula on estï¿½ actualment el grup.
		*	@param fiRoom		Aula on anirï¿½ el grup si es pot.
		*	@return True si s'ha pogut moure el grup. Fals en cas contrari.
		*/
		public boolean moveLecture(int duration, int iniDay, int fiDay, int iniHour, int fiHour, String iniRoom, String fiRoom) {
			String lecture = schedule.getSchedule().get(fiRoom)[iniDay][iniHour];
			
			// Eliminar lecture
			boolean removed = removeLecture(duration, iniRoom, iniDay, iniHour);
			
			if (removed) {
				// Restriccions
				boolean restr = validateAllRestrictions(lecture, fiDay, fiHour, fiRoom, duration);
				
				if (restr) {
					// Afegir-lo al lloc nou
					putLecture(duration, fiRoom, fiDay, fiHour);
					return true;
				} else {
					// Tornar a afegir-lo al lloc inicial
					putLecture(duration, iniRoom, iniDay, iniHour);
					return false;
				}
			} else return false;
		}
		
		/**
		 * @param inCode
		 * @param inName
		 * @param inLevel
		 * @param arrayList
		 * @param inCoreqs
		 * @return
		 */
		public boolean addSubject(String inCode, String inName, String inLevel, ArrayList<String> inCoreqs) {
			if (inCode == null || inCode.isEmpty() || inName == null || inName.isEmpty() || inLevel == null || inLevel.isEmpty()) return false;
			return environment.getInstance().addSubject(inCode, inName, inLevel, new ArrayList(), inCoreqs);
		}
		
		/**
		 * @param name
		 * @return
		 */
		public boolean removeSubject(String name) {
			return environment.removeSubject(name);
		}
		
		/**
		 * @param inCode
		 * @param inCapacity
		 * @param inHasComputers
		 * @return
		 */
		public boolean addRoom(String inCode, Integer inCapacity, Boolean inHasComputers) {
			if (inCode == null || inCode.isEmpty()) return false;
			return environment.getInstance().addRoom(inCode, inCapacity, inHasComputers);
		}
}