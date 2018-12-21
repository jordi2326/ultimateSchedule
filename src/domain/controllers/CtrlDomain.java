package domain.controllers;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;
import java.io.File;
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

import domain.classes.Environment;
import domain.classes.Group;
import domain.classes.Lecture;
import domain.classes.Room;
import domain.classes.Schedule;
import domain.classes.Subject;
import domain.classes.restrictions.DayPeriodRestriction;
import domain.classes.restrictions.LectureFromSameGroupOverlapRestriction;
import domain.classes.restrictions.NaryRestriction;
import domain.classes.restrictions.SpecificDayOrHourRestriction;
import domain.classes.restrictions.UnaryRestriction;
import persistance.CtrlData;

/** Controlador principal de domini de l'aplicacio.
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

	/** Constructora estandard.
	*/
	private CtrlDomain() {
		dataController = CtrlData.getInstance();
		schedule = new Schedule();
		environment = Environment.getInstance();
	}

	/**
	 * Retorna la instancia d'aquesta classe.
	 * @return {@link CtrlDomain#instance}.
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
		schedule = new Schedule();
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

	/**
	 * Borra l'horari actual.
	 */
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
	 * @return El path de l'escenari.
	 */
	public String getEnvirnonmentPath() {
		Environment env = Environment.getInstance();
		return env.getPath();		
	}
	
	/**
	 * Activa/Desactiva una restriccio.
	 * @param g		Nom del grup que te la restriccio.
	 * @param r		Nom de la restriccio.
	 * @param state	Boolea que indica l'estat a posar a la restriccio.
	 * @return True si s'ha pogut fer correctament el setter. False en cas contrari.
	 */
	public Boolean setRestrictionEnabled(String g, String r, Boolean state) {
		Environment env = Environment.getInstance();
		return env.setRestrictionEnabled(g, r, state);
	}

	/**
	 * Importa un entorn (aules, assignatures, aules) des d'un arxiu.
	 * @param filename Nom de l'entorn a importar.
	 * @return true si s'ha importat correctament, sino false.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public boolean importEnvironment(String filename, boolean isFullpath) throws ParseException, IOException   {

		 Environment env = Environment.getInstance();

		 env.erase();
		 String path = filename;
		 if (isFullpath) {
			 File f = new File(filename);
			 path = f.getName();
		 }

		 env.setPath(path);

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
        	env.addSubject(scode, (String) subject.get("name"), (String) subject.get("level"), new ArrayList<String>(), (ArrayList<String>) subject.get("coreqs"));
        	Iterator itr2 = jsonGroups.iterator();
        	while (itr2.hasNext()) {
        		JSONObject group = (JSONObject) itr2.next();
        		ArrayList<String> ls = new ArrayList<String>();
        		ArrayList<Long> durations = (ArrayList<Long>) group.get("lecturesDuration");

        		String gcode = (String) group.get("code");
        		env.addGroup(gcode, (Integer)((Long) group.get("numPeople")).intValue(), (String) group.get("parentGroupCode"), scode, (Boolean) group.get("needsComputers"), (String)group.get("type"), (String)group.get("dayPeriod"), ls);
        		
        		for(int i = 0; i < durations.size(); i++){
        			env.addLecture(i, scode +"-"+ gcode +"-"+ group.get("type"), durations.get(i).intValue());
        		}
        	}
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

	/**
	 * Exporta un escenari.
	 * @param filename		Nom de l'arxiu.
	 * @param absolutePath	Path absolut d'on guardar l'arxiu.
	 * @return Exporta un escenari.
	 */
	@SuppressWarnings("unchecked")
	public boolean exportEnvironment(String filename, Boolean absolutePath) throws IOException {
		Environment environment = Environment.getInstance();
		// creating JSONObject
        JSONObject jo = new JSONObject();
        // creating subjects JSONArray
        JSONArray jsonSubjects = new JSONArray();
        // creating rooms JSONArray
        JSONArray jsonRestrictions = new JSONArray();

        for (String s : environment.getAllSubjects()) {
        	JSONObject subject = new JSONObject();
        	subject.put("code", environment.getSubjectCode(s));
        	subject.put("name", environment.getSubjectName(s));
        	subject.put("level", environment.getSubjectLevel(s));
        	subject.put("coreqs", environment.getSubjectCoreqs(s));

        	JSONArray jsonGroups = new JSONArray();

        	for (String g : environment.getSubjectGroups(s)) {
        		JSONObject jsonGroup = new JSONObject();
        		jsonGroup.put("code", environment.getGroupCode(g));
        		jsonGroup.put("numPeople", environment.getGroupNumOfPeople(g));
        		jsonGroup.put("parentGroupCode", environment.getGroupParentGroupCode(g));
        		jsonGroup.put("subject", environment.getGroupSubject(g));
        		jsonGroup.put("type", environment.getGroupType(g).toString());
        		jsonGroup.put("dayPeriod", environment.getGroupDayPeriod(g).toString());
        		jsonGroup.put("needsComputers", environment.groupNeedsComputers(g).toString());
        		JSONArray jsonLectures = new JSONArray();
        		for (String l : environment.getGroupLectures(g)) {
        			jsonLectures.add(environment.getLectureDuration(l));
        		}
        		jsonGroup.put("lecturesDuration", jsonLectures);
        		jsonGroups.add(jsonGroup);

        		// restrictions
        		Set<ArrayList<Integer>> infoRestr = new HashSet<ArrayList<Integer>>();
        		infoRestr = environment.getSpecificDayHourInfo(g);
        		for (ArrayList<Integer> dayAndHour : infoRestr) {
        			Integer day = dayAndHour.get(0);
        			Integer hour = dayAndHour.get(1);
        			JSONObject restrJson = new JSONObject();
        			restrJson.put("name", SpecificDayOrHourRestriction.class.getSimpleName());
        			JSONObject infoJson = new JSONObject();
        			infoJson.put("group", g);
        			infoJson.put("day", day);
        			infoJson.put("hour", hour);
        			restrJson.put("info",  infoJson);
        			jsonRestrictions.add(restrJson);
        		}
        	}

        	subject.put("groups", jsonGroups);

        	jsonSubjects.add(subject);
        }

        // creating rooms JSONArray
        JSONArray jsonRooms = new JSONArray();

        for  (String r : environment.getAllRooms()) {
        	JSONObject room = new JSONObject();
        	room.put("code", environment.getRoomCode(r));
        	room.put("capacity",  environment.getRoomCapacity(r));
        	room.put("hasComputers", environment.roomHasComputers(r));
        	jsonRooms.add(room);
        }

        //Add Subjects and Rooms to final object
        jo.put("rooms", jsonRooms);
        jo.put("subjects", jsonSubjects);
        jo.put("restrictions", jsonRestrictions);

        //Send to data controller to write
        return dataController.writeEnvironment(filename, jo.toJSONString(0), absolutePath);
	}


	/**jsonData
	 * Importa un horari desde un arxiu.
	 * @param filename Nom de l'arxiu d'horari a importar.
	 * @return true si s'ha importat correctament, sino false
	 * @throws IOException
	 */
	public boolean importSchedule(String filename, boolean isFullpath) throws ParseException, IOException {
		String jsonData = dataController.readSchedule(filename, isFullpath);

		Object obj = new JSONParser().parse(jsonData);

		JSONObject jo = new JSONObject();
		jo = (JSONObject) obj;
		String scheduleData = ((JSONObject) jo.get("schedule")).toJSONString(0);
		schedule = new Schedule(scheduleData);

		String envPath = (String) jo.get("path");
		importEnvironment(envPath, false);

	   	return true;
	}

	/**
	 * Guarda l'horari de l'entorn a un arxiu.
	 * @param filename Nom amb el que guardar l'horari.
	 * @return true si s'ha guardat correctament.
	 * @throws ParseException
	 */
	@SuppressWarnings("unchecked")
	public boolean exportSchedule(String filename, boolean isFullpath) throws IOException, ParseException  {
		Environment environment = Environment.getInstance();

		Object obj = new JSONParser().parse(schedule.toJsonString());

		// typecasting obj to JSONObject
        JSONObject scheduleJSON = (JSONObject) obj;

        JSONObject jo = new JSONObject();

        jo.put("schedule", scheduleJSON);

        jo.put("path", environment.getPath());

        return dataController.writeSchedule(filename, jo.toJSONString(0), isFullpath);
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

	/**
	 * @return La matriu de l'horari per cada aula.
	 */
	public ArrayList<String[]>[][] getScheduleMatrix() {
		ArrayList<String[]>[][] data = new ArrayList[12][5];
		for(Entry<String, String[][]> d : schedule.getSchedule().entrySet()) {
			String[][] matrix = d.getValue();
			for (int i = 0; i < matrix[0].length; i++) {
                for (int j = 0; j < matrix.length; j++) {
                	if(data[i][j]==null) data[i][j] = new ArrayList<String[]>();
                	if(matrix[j][i]!=null && !matrix[j][i].isEmpty()) 
                		data[i][j].add(new String[]{getLectureGroup(matrix[j][i]), d.getKey(), matrix[j][i]});
                }
            }
		}
		return data;
	}

	/**
	 * Grup al qual pertany una sessio.
	 * @param lecture	Nom de la sessio.
	 * @return Nom del grup al qual pertany la sessio.
	 */
	public String getLectureGroup(String lecture){
		return environment.getLectureGroup(lecture);
	}

	// Funcions per comunicar-se amb la capa de presentaci�

		// ROOM
		/**
		 * @return El Set de noms d'aules de l'escenari actual.
		 */
		public Set<String> getRoomNames() {
			Environment env = Environment.getInstance();
			return new TreeSet<String>(env.getAllRooms());
		}

		/**
		 * Informacio d'una aula.
		 * @param room	Nom de l'aula.
		 * @return La informacio d'una aula.
		 */
		public String[] getRoomInfo(String room) {
			 /* ********* ORDRE *********
			  * param code			Codi de l'aula.
			  * param capacity 		Capacitat de l'aula.
			  * param hasComputers 	Indica si l'aula t� ordinadors o no.
			  * ************************* */
			Environment env = Environment.getInstance();

			String[] infoRoom = new String[3];
			infoRoom[0] = env.getRoomCode(room);
			infoRoom[1] = env.getRoomCapacity(room).toString();
			infoRoom[2] = env.roomHasComputers(room).toString();

			return infoRoom;
		};

		// GROUP
		/**
		 * @return El Set de noms dels grups de l'escenari actual.
		 */
		public Set<String> getGroupNames() {
			Environment env = Environment.getInstance();
			return new TreeSet<String>(env.getAllGroups());
		}

		/**
		 * Informacio d'un grup.
		 * @param group	Nom del grup.
		 * @return La informacio d'un grup.
		 */
		public String[] getGroupInfo(String group) {
			 /* ********* ORDRE *********
			  * param code				Codi del grup.
			  * param numPeople			N�m. persones en el grup.
			  * param parentGroupCode	Codi del grup pare.
			  * param subject			Assignatura a la que pertany.
			  * param type				Tipus de grup.
			  * param dayPeriod			Per�ode del dia.
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

		/**
		 * Conjunt de grups que pertanyen a una assignatura.
		 * @param s	Nom de l'assignatura.
		 * @return El Set de noms de grup que pertanyen a l'assignatura {@link CtrlDomain#s}.
		 */
		public Set<String> getGroupsNamesFromSuject(String s) {
			Environment env = Environment.getInstance();
			return new TreeSet<String>(env.getSubjectGroups(s));
		}

		// SUBJECT
		/**
		 * @return Conjunt de noms de les assignatures de l'escenari actual.
		 */
		public Set<String> getSubjectNames() {
			Environment env = Environment.getInstance();
			return new TreeSet<String>(env.getAllSubjects());
		}

		/**
		 * Informacio d'una assignatura.
		 * @param sub	Nom de la signatura.
		 * @return La informacio que correspon a l'assignatura {@link CtrlDomain#sub}.
		 */
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
		/**
		 * @return Conjunt de noms de les restriccions de l'escenari actual.
		 */
		public Set<String> getRestrictionNames() {
			Environment env = Environment.getInstance();

			TreeSet<String> R = new TreeSet<String>();

			for (String group : env.getAllGroups()) {
				if (env.groupHasUnaryRestrictions(group)) R.addAll(env.getGroupUnaryRestrictions(group));
				if (env.groupHasNaryRestrictions(group)) R.addAll(env.getGroupNaryRestrictions(group));
			}

			return R;
		}

		/**
		 * @return Nom de les restriccions per a la classe NamesView.
		 */
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

		/**
		 * @return El conjunt de restriccions negociables.
		 */
		public ArrayList<Object[]> getNegotiableRestrictions() {
			return environment.getNegotiableRestrictions();
		}
		/**
		 * @return El path de l'escenari actual.
		 */
		public String getEnvironmentName(){
			return environment.getPath();
		}
		
		/** Elimina un grup en un dia i aula determinats.
		*   @param duration Duracio del grup.
		*	@param room		Aula on eliminarem el grup.
		*	@param day		Dia on eliminarem el grup.
		*	@param hour		Hora on eliminarem el grup.
		*	@return Si existeix l'aula, eliminem el grup en que esta i retornem true. Fals en cas contrari.
		*/
		public boolean removeLecture(int duration, String room, int day, int hour) {
			for (int i = 0; i < duration; i++) {
				boolean removeOne = schedule.removeLecture(room, day, hour + i);
				if (!removeOne) return false;
			}
			return true;
		}

		/**
		 * Valida totes les restriccions.
		 * @param lecture	Nom de la sessio que passarem a les restriccions.
		 * @param day		Dia que passarem a les restriccions.
		 * @param hour		Hora que passarem a les restriccions.
		 * @param room		Aula que passarem a les restriccions.
		 * @param duration	Duracio de la sessio.
		 * @return True si s'han validat totes les restriccions que s'han passat. False en cas contrari.
		 */
		private boolean validateAllRestrictions(String lecture, int day, int hour, String room, int duration) { 
			Environment env = Environment.getInstance();
			String group = env.getLectureGroup(lecture);
			if (env.getGroupNumOfPeople(group) <= env.getRoomCapacity(room)) {
				//Group fits in room
				if ((env.getGroupType(group).equals(Group.Type.LABORATORY) && env.roomHasComputers(room))
					|| !env.getGroupType(group).equals(Group.Type.LABORATORY)) {
					if (env.groupHasUnaryRestrictions(group)) {
						for (String restr : env.getGroupUnaryRestrictions(group)) {
							if (env.restrictionIsEnabled(group, restr) && !env.validateGroupUnaryRestriction(group, restr, day, hour, duration)) {
								return false;
							}
						}
						// All unary restrictions checked and passed
						for (String r : schedule.getSchedule().keySet()) {
							for (int h = 0; h < 12; ++h) {
								String l = schedule.getSchedule().get(r)[day][h];
								if (l != null) {
									String g = env.getLectureGroup(l);
									String overlapRestr = LectureFromSameGroupOverlapRestriction.class.getSimpleName();
									if (env.getGroupNaryRestrictions(g).contains(overlapRestr)
											&& !env.validateGroupNaryRestriction(g, overlapRestr, room, day, hour, lecture, day, hour, room, l)) {
										return false;
									}
									for (String restr : env.getGroupNaryRestrictions(g)) {
										if (!env.validateGroupNaryRestriction(g, restr, room, day, hour, lecture, day, h, r, l)) {
											return false;
										}
									}
								}
							}
						}
							
					}
				}
			}
			return true;
		}

		/** Mou un grup de dia, aula i hora determinats
		*   @param duration Duracio del grup.
		*	@param iniDay		Dia on esta actualment el grup.
		*	@param fiDay		Dia on anira el grup si es pot.
		*	@param iniHour		Hora on esta actualment el grup.
		*	@param fiHour		Hora on anira el grup si es pot.
		*	@param iniRoom		Aula on esta actualment el grup.
		*	@param fiRoom		Aula on anira el grup si es pot.
		*	@return True si s'ha pogut moure el grup. Fals en cas contrari.
		*/
		public boolean moveLecture(int duration, int iniDay, int fiDay, int iniHour, int fiHour, String iniRoom, String fiRoom) {
			String lecture = schedule.getSchedule().get(iniRoom)[iniDay][iniHour];
			// Eliminar lecture
			boolean removed = removeLecture(duration, iniRoom, iniDay, iniHour);

			if (removed) {
				// Restriccions
				boolean restr = validateAllRestrictions(lecture, fiDay, fiHour, fiRoom, duration);

				if (restr) {
					// Afegir-lo al lloc nou
					while(--duration >= 0) {
						schedule.putLecture(fiRoom, fiDay, fiHour + duration, lecture);
					}
					return true;
				} else {
					// Tornar a afegir-lo al lloc inicial
					while(--duration >= 0) {
						schedule.putLecture(iniRoom, iniDay, iniHour + duration, lecture);
					}
					return false;
				}
			} else return false;
		}

		/**
		 * @param inCode	Codi de l'assignatura.
		 * @param inName	Nom de l'assignatura.
		 * @param inLevel	Nivell de l'assignatura.
		 * @param inCoreqs	Conjunt de corequisits de l'assignatura.
		 * @return True si s'ha pogut afegir l'assignatura. False en cas contrari.
		 */
		public boolean addSubject(String inCode, String inName, String inLevel, ArrayList<String> inCoreqs) {
			if (inCode == null || inCode.isEmpty() || inName == null || inName.isEmpty() || inLevel == null || inLevel.isEmpty()) return false;
			return environment.addSubject(inCode, inName, inLevel, new ArrayList(), inCoreqs);
		}

		/**
		 * Elimina una assignatura.
		 * @param name	Nom de l'assiognatura.
		 * @return True si s'ha pogut eliminar una assignatura. False en cas contrari.
		 */
		public boolean removeSubject(String name) {
			ArrayList<String> groups = environment.getSubjectGroups(name);
			Map<String, ArrayList<String>> lectures = new HashMap<String, ArrayList<String>>();
			for (String group : groups) {
				lectures.put(group, environment.getGroupLectures(group));
			}

			boolean erase = environment.removeSubject(name);

			if (erase) {
				for (String lecture : lectures.keySet()) {
					for (String l : lectures.get(lecture)) {
						eraseLecture(l);
					}
				}

				return true;
			}

			return false;
		}

		/**
		 * Afageix una aula.
		 * @param inCode			Codi de l'aula.
		 * @param inCapacity		Capacitat de l'aula.
		 * @param inHasComputers	Boolea que indica si una aula te ordinadors o no.
		 * @return True si s'ha pogut afegir l'aula. False en cas contrari.
		 */
		public boolean addRoom(String inCode, Integer inCapacity, Boolean inHasComputers) {
			if (inCode == null || inCode.isEmpty()) return false;
			return environment.addRoom(inCode, inCapacity, inHasComputers);
		}

		/**
		 * Elimina una aula.
		 * @param code	Codi de l'aula a eliminar.
		 * @return True si s'ha pogut eliminar l'aula. False en cas contrari.
		 */
		public boolean removeRoom(String code) {
			boolean erase = environment.removeRoom(code);

			if (erase) {
				schedule.getSchedule().remove(code);

				return true;
			}

			return false;
		}

		/**
		 * Afegeix grup.
		 * @param inCode				Codi del grup.
		 * @param inNPeople				Numero de persones que pertanyen al grup.
		 * @param inParentGroupCode		Codi del grup pare.
		 * @param subjectCode			Codi de l'assignatura.
		 * @param inNeedsComputers		Boolea que indica si el grup necessita una aula amb ordinadors.
		 * @param inType				Tipus de classe del grup.
		 * @param inDayPeriod			Periode del dia en que el grup te classe.
		 * @param arrayList				Conjunt de sessions del grup.
		 * @return True si s'ha pogut afegir el grup. False en cas contrari.
		 */
		public boolean addGroup(String inCode, Integer inNPeople, String inParentGroupCode, String subjectCode,
				Boolean inNeedsComputers, String inType, String inDayPeriod, ArrayList<String> arrayList) {
			if (inCode == null || inCode.isEmpty() || inParentGroupCode == null || inParentGroupCode.isEmpty()) return false;

			return environment.addGroup(inCode, inNPeople, inParentGroupCode, subjectCode,
					inNeedsComputers, inType, inDayPeriod, arrayList);
		}

		/**
		 * Elimina un grup.
		 * @param name	Nom del grup.
		 * @return True si s'ha pogut eliminar el grup. False en cas contrari.
		 */
		public boolean removeGroup(String name) {
			ArrayList<String> lectures = environment.getGroupLectures(name);
			boolean erase = environment.removeGroup(name);
			if (erase) {
				for (String lecture : lectures) {
					eraseLecture(lecture);
				}

				return true;
			}

			return false;
		}

		/**
		 * Afageix sessio.
		 * @param codi		Codi de la sessio.
		 * @param group		Nom del grup al qual pertany la sessio.
		 * @param duration	Duracio de la sessio.
		 * @return True si s'ha pogut afegir la sessio. False en cas contrari.
		 */
		public boolean addLecture(Integer codi, String group, Integer duration) {
			 return environment.addLecture(codi, group, duration);
		 }

		/**
		 * Elimina una sessio.
		 * @param name	Nom de la sessio.
		 * @return True si s'ha pogut eliminar la sessio. False en cas contrari.
		 */
		public boolean removeLecture(String name) {
			boolean erase = environment.removeLecture(name);
			if (erase) {
				eraseLecture(name);
				return true;
			}

			return false;
		}

		/**
		 * Eliminar sessio de l'horari.
		 * @param name	Nom de la sessio a eliminar.
		 */
		public void eraseLecture(String name) {
			for (String room : schedule.getSchedule().keySet()) {
				for (int i = 0; i < 11; i++) {
					for (int j = 0; j < 5; ++j) {
						if (schedule.getSchedule().get(room)[j][i] != null) {
							if (schedule.getSchedule().get(room)[j][i].equals(name)) {
								schedule.getSchedule().get(room)[j][i] = null;
							}
						}
					}
				}
			}
		}
		
		/**
		 * Afageix una restriccio.
		 * @param group	Grup al qual pertany la restriccio.
		 * @param day	Dia que passarem a la restriccio.
		 * @param hour	Hora que passarem a la restriccio.
		 * @return True si s'ha pogut afegir la restriccio. False en cas contrari.
		 */
		public boolean addRestriction(String group, Integer day, Integer hour) {
			if (group == null || group.isEmpty()) return false;
			
			SpecificDayOrHourRestriction rest = new SpecificDayOrHourRestriction(day, hour);
			
			if (!environment.getGroupUnaryRestrictions(group).contains(rest.toString())) {
				environment.addUnaryRestriction(group, rest);
				return true;
			}
			
			return false;
		}
		
		/**
		 * Elimina una restriccio.
		 * @param group	Nom del grup al qual pertany la restriccio.
		 * @param name	Nom de la restriccio.
		 * @return True si s'ha pogut eliminar la restriccio. False en cas contrari.
		 */
		public boolean removeRestriction(String group, String name) {
			return environment.removeRestriction(group, name);
		}
}
