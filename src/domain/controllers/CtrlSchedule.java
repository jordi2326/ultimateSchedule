package domain.controllers;

import domain.classes.Environment;
import domain.classes.Group;
import domain.classes.Lecture;
import domain.classes.PosAssig;
import domain.classes.Room;
import domain.classes.Schedule;
import domain.classes.Subject;
import domain.classes.restrictions.NaryRestriction;
import domain.classes.restrictions.UnaryRestriction;

import java.util.AbstractMap;
import java.util.Comparator;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.HashMap;
import java.util.HashSet;


/** Controlador de Schedule.
 * @author Xavier Martín Ballesteros i Xavier Lacasa Curto
*/

class groupHeuristicComparator implements Comparator<Map.Entry<Integer, String>>{ 
    
    // Overriding compare()method of Comparator  
    // for descending order of the integer of the pair, which is the number of possible rooms of that lecture
    public int compare(Map.Entry<Integer, String> p1, Map.Entry<Integer, String> p2) { 
        if (p1.getKey() > p2.getKey()) 
            return 1; 
        else if (p1.getKey() < p2.getKey()) 
            return -1; 
        else return 0; 
        } 
} 

public class CtrlSchedule {
	
	/** Instancia d'aquesta classe.
	*/
	private static CtrlSchedule instance;
	
	/**
	 * Retorna la instancia d'aquesta classe.
	 * @return {@link CtrlSchedule#instance}
	 */
	public static CtrlSchedule getInstance() {
		if (instance == null)
			instance = new CtrlSchedule();
		return instance;
	}
	
	/** Constructora estàndard.
	*/
	private CtrlSchedule() {
	}
	
	private static String createKey(String room, Integer day, Integer hour) {
		return room+"-"+hour+"-"+day;
	}
	
	private static String getRoomFromKey(String key) {
		return key.split("-")[0]; 
	}
	
	private static Integer getHourFromKey(String key) {
		return Integer.valueOf(key.split("-")[1]); 
	}
	
	private static Integer getDayFromKey(String key) {
		return Integer.valueOf(key.split("-")[2]); 
	}

	/**
	 * Funció principal que genera un horari amb les dades donades.
	 * @param schedule Objecte de la classe Schedule que contindrà l'horari generat al finalitzar l'algoritme.
	 * @return  true si s'ha trobat un horari valid, sino false.
	 */
	public boolean generateSchedule(Schedule schedule) {
		//To know how many possibilities has each lecture and be able to choose the one with less possibilities to place
		Map<String, Integer> numPossibleAlloc = new HashMap<String, Integer>(); //Map<lecture.toString(), num possible rooms day hours>
		//To keep track of which day hour rooms are poossible for each lecture
		Map<String, PosAssig> assignations = new HashMap<String, PosAssig>(); //String = Lecture.toString()
		//To keep track of day hour rooms that don't have any lecture as possible.
		Map<String, Integer> referencedRooms = new HashMap<String, Integer>(); // Map<createKey(Room.toString(),day,hour), num of lectures that have this room hour day as possible>>
		//Filter possible rooms hours and days for each group according to room capacity, PC's, day period and restrictions of days / hours
		Environment env = Environment.getInstance();
		for (String g : env.getAllGroups()) {
			int totalGroupRooms = 0;
			Set<String> roomSet = new HashSet<String>();
			// get possible rooms for group g				
			for (String r : env.getAllRooms()) {
				if (env.getGroupNumOfPeople(g) <= env.getRoomCapacity(r)) {
					//Group fits in room
					if ((env.getGroupType(g).equals(Group.Type.LABORATORY) && env.roomHasComputers(r))
						|| !env.getGroupType(g).equals(Group.Type.LABORATORY)) {
							roomSet.add(r);
							++totalGroupRooms;
					}
				}
			}
			//now roomSet contains possible rooms for group
			for (String l : env.getAllLectures()) {
				//Initialize number of possible allocations to zero
				numPossibleAlloc.put(l, 0);
				int lecDuration = env.getLectureDuration(l);
				for (int day = 0; day < 5; ++day) {
					for (int hour = 0; hour < 12; ++hour) {
						boolean valid = true;
						if (env.groupHasUnaryRestrictions(g)) {
							for (String restr : env.getGroupUnaryRestrictions(g)) {
								if (!env.validateGroupUnaryRestriction(g, restr, day, hour, lecDuration)) {
									valid = false;
									break;
								}
							}
						}
						if (valid) {
							//Add all rooms from that day and hour to total allocations of lecture l
							numPossibleAlloc.put(l, numPossibleAlloc.get(l) + totalGroupRooms);
							if (!assignations.containsKey(l)) {
								PosAssig pa = new PosAssig();
								assignations.put(l, pa);
							}
							for (String room : roomSet) {
								//add one to the number of references of each room hour day
								String key = createKey(room, day, hour);
								if (!referencedRooms.containsKey(key)) {
									referencedRooms.put(key, 0);
								}
								referencedRooms.put(key, referencedRooms.get(key)+1);
								//Put rooms in possible assignations
								assignations.get(l).putRoomInDayAndHour(day, hour, room);
							}
							//add the number of rooms to the number of possibilities of the lecture l
							numPossibleAlloc.put(l, numPossibleAlloc.get(l)+roomSet.size());
						}
					}
				}
			}
		}
		return this.backjumping(schedule, numPossibleAlloc, assignations, referencedRooms);
	}
	
	/**
	 * Funció de Forward Checking que comprova la validesa de l'última assignació i reduiex les possibles assignacions si es possible.
	 * @param lecture Última 'lecture' que s'ha assignat a l'horari, i es vol comprovar la seva validesa.
	 * @param room Aula en la qual s'ha assignat la 'lecture'.
	 * @param day Dia en el que s'ha assignat la 'lecture'.
	 * @param hour Hora en la que s'ha assignat la 'lecture'.
	 * @return True si l'assigació a comprovar es valida, sino false.
	 */)
	private static boolean forwardCheck(String lecture, String room, Integer day, Integer hour, Map<String, Integer> numPossibleAlloc, Map<String, PosAssig> assignations, Map<String, Integer> referencedRooms) {
		for (NaryRestriction restr : naryRestrictions.values()) {
			if (!restr.validate(lecture, room, day, hour, subjects, groups, lectures, shrek)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Funció que fa Backjumping, utilitzat en l'algoritme que genera un horari.
	 * @param schedule Objecte de la classe Schedule que contindrà l'horari generat al finalitzar l'algoritme.
	 */
	private boolean backjumping(Schedule schedule, Map<String, Integer> numPossibleAlloc, Map<String, PosAssig> assignations, Map<String, Integer> referencedRooms) {	
		Environment env = Environment.getInstance();
		// All lectures have been inserted in Schedule
		if (assignations.isEmpty()) {
			return true;
		}	
		//Get lecture with the least amount of possibilities to insert it first
		Integer minLecture = Integer.MAX_VALUE;
		String lecture;
		for (Map.Entry<String, Integer> entry : numPossibleAlloc.entrySet()) {
			if (entry.getValue() < minLecture) {
				minLecture = entry.getValue();
				lecture = entry.getKey();
			}
		}
		//Order room+hour+day from the lecture with less possibilities, in ascending number of references from other lectures
		//Because the room with the least amount of references is most likely not gonna be used by any other lecture
		PriorityQueue<Map.Entry<Integer, String>> pq = new PriorityQueue<Map.Entry<Integer, String>>(new groupHeuristicComparator()); // PriorityQueue<Pair<Int, room.toString()>>
		for (Integer day : assignations.get(lecture).getAllDays()) {
			for (Integer hour : assignations.get(lecture).getAllHoursFromDay(day)) {
				for (String r : assignations.get(lecture).getAllRoomsFromHourAndDay(day, hour)) {
					String key = createKey(r, day, hour);
					Integer numRefs = referencedRooms.get(key);
					Map.Entry<Integer, String> pair = new AbstractMap.SimpleEntry<Integer, String>(numRefs, key);
					pq.add(pair);
				}
			}
		}
		//We should try to fit lecture in room+hour+day with less references
		//If doesn't work, try next room+hour+day (might be another day or/and hour) 
		Boolean generated = false;
		while (!pq.isEmpty() && !generated) {
			String key = pq.remove().getValue();
			String room = getRoomFromKey(key);
			Integer day = getDayFromKey(key);
			Integer hour = getHourFromKey(key);
			
			//Try forwardChecking and generating schedule before the insertion so that if it fails we don't have to delete lecture from schedule
			Map<String, PosAssig> copyAssignations = assignations.entrySet().stream().collect(Collectors.toMap(e -> e.getKey(), e -> new PosAssig(e.getValue().getMap())));
			Map<String, Integer> copyReferencedRooms = referencedRooms.entrySet().stream().collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));
			Map<String, Integer> copyNumPossibleAlloc = numPossibleAlloc.entrySet().stream().collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));
			
			if (forwardCheck(lecture, room, day, hour, copyNumPossibleAlloc, copyAssignations, copyReferencedRooms)) {
				//Once checked there are still possibilities, try generating the rest of the schedule with
				//the impossible combinations cut out by the forward checking
				generated = backjumping(schedule, copyNumPossibleAlloc, copyAssignations, copyReferencedRooms);
				if (generated) {
					//If backjumping returned true, the schedule is possible, so insert the lecture
					//All lectures from here on have been inserted recursively
					Integer h = 0;
					while (h < env.getLectureDuration(lecture)) {
						schedule.putLecture(room, day, hour+h, env.getLectureGroup(lecture));
						++h;
					}
				}
			}
		}
		return generated;
	}
	
	/* ********************** FORWARD CHECKING ******************
	 * FunciÃ¯Â¿Â½n: forward checking (vfuturas, solucion)
		si vfuturas.es_vacio?() entonces
			retorna solucion
		sino
			vactual <- vfuturas.primero()
			vfuturas.borrar_primero()
				para cada v (pertany) vactual.valores() hacer
				vactual.asignar(v)
				solucion.anadir(vactual)
				vfuturas.propagar_restricciones(vactual) // forward checking
				si no vfuturas.algun_dominio_vacio?() entonces
					solucion <- forward_checking(vfuturas,solucion)
					si no solucion.es_fallo?() entonces
						retorna solucion
					sino
						solucion.borrar(vactual)
				sino
				solucion.borrar(vactual)
			retorna solucion.fallo()
*/
}
