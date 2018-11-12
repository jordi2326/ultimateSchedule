package domain.controllers;

import domain.classes.Group;
import domain.classes.Lecture;
import domain.classes.PosAssig;
import domain.classes.Room;
import domain.classes.Schedule;
import domain.classes.Subject;
import domain.classes.Group.DayPeriod;
import domain.classes.Group.Type;
import domain.controllers.CtrlDomain;
import domain.classes.restrictions.NaryRestriction;
import domain.classes.restrictions.UnaryRestriction;
import javafx.util.Pair;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
// import java.util.Calendar; No s'utilitza ja
import java.util.HashMap;
import java.util.HashSet;


//TODO: Fer que subject tingui String[] groups en comptes de ArrayList<String>. Fer el mateix amb Group, etc.

class groupHeuristicComparator implements Comparator<Pair<Integer, String>>{ 
    
    // Overriding compare()method of Comparator  
    // for descending order of the integer of the pair, which is the number of possible rooms of that lecture
    public int compare(Pair<Integer, String> p1, Pair<Integer, String> p2) { 
        if (p1.getKey() > p2.getKey()) 
            return 1; 
        else if (p1.getKey() < p2.getKey()) 
            return -1; 
        else return 0; 
        } 
} 

public class CtrlSchedule {
	
	private static CtrlSchedule instance;
	
	public static CtrlSchedule getInstance() {
		if (instance == null)
			instance = new CtrlSchedule();
		return instance;
	}
	
	private CtrlSchedule() {
		// TODO Auto-generated constructor stub
	}

	
	// ************************************************************************
	
	public static void generateSchedule(Map<String, UnaryRestriction[]> unaryRestrictions, Map<String, NaryRestriction[]> naryRestrictions, Map<String, Group> groups, Map<String, Rooms> rooms, Map<String, Subject> subjects, Map<String, Lecture> lectures) {
		//TODO: Implementar la restriction de que un grup no vagi en un dia o hora concrets
		//Filter possible rooms hours and days for each group according to room capacity, PC's, day period and restrictions of days / hours
		
		//posAssig te
		//Map<Integer,<Map <Integer, Set<String>>>>
		Map<String, PosAssig> shrek = new HashMap<String, PosAssig>(); //String = Lecture.toString()
		PriorityQueue<Pair<Integer, String>> pq = new PriorityQueue<Pair<Integer, String>>(new groupHeuristicComparator()); // PriorityQueue<Pair<Int, Lecture.toString()>>
		
		for (Group g : groups.values()) {
			
			Integer totalGroupRooms = 0;
			Set<String> roomSet = new HashSet<String>();
			for (Room r : rooms.values()) {
				if (g.getNumOfPeople() <= r.getCapacity()) {
					if ((g.getType() == Group.Type.LABORATORY && r.hasComputers())
						|| (g.getType() != Group.Type.LABORATORY)) {
						roomSet.add(r.toString());
						totalGroupRooms += 1;
					}
				}
			}
			Map<Integer, Map <Integer, Set<String>>> dayHourRooms = new HashMap<Integer, Map <Integer, Set< String>>>();
			for (int day = 0; day < 5; ++day) {
				Map <Integer, Set<String>> hourRooms = new HashMap<Integer, Set<String>>();
				for (int hour = 0; hour < 12; ++hour) {	
					boolean valid = true;
					for (UnaryRestriction restr : unaryRestrictions.get(g.toString())) {
						if (!restr.validate(day, hour)) {
							valid = false;
						}
					}
					if (valid) {
						hourRooms.put(hour, roomSet);
					}
				}
				if (!hourRooms.isEmpty()) {
					dayHourRooms.put(day, hourRooms);
				}
			}
			if (!dayHourRooms.isEmpty()) {
				PosAssig pa = new PosAssig(dayHourRooms);
				for (String lecture : g.getLectures()) {
					shrek.put(lecture, pa);
					Pair<Integer, String> pair = new Pair<>(totalGroupRooms, lecture);
					pq.add(pair);
				}
			}
		}
		
		Schedule schedule = new Schedule();
		
		boolean exists = generate(schedule, shrek, pq, naryRestrictions);
		
		if (exists) {
			schedule.imprimir();
		} else {
			// Error
		}
	}
	
	private static boolean forwardCheck() {
		
	}

	private static boolean generate(Schedule schedule, Map<String, PosAssig> shrek, Map<String, NaryRestriction[]> naryRestrictions, PriorityQueue<Pair<Integer, Lecture>> heuristica) { // Canviar nom de la PQ si volem xD		
		// Pre: a shrek hi tenim només les Lectures que falten afegir i les assignacions possibles que li podem donar. Només les possibles! (forward checking)
		// Pre: a més, per com està feta la funció podar, no hi ha cap Lecture amb 0 possibles assignacions
		
		// Cas base (CORRECTE) => shrek està buit => No queda res per afegir
		if (shrek.size() == 0) {
			return true;
		} else { // Encara tenim Lectures per afegir
			
			// Faig còpia d'shrek perquè no se m'eliminin coses al passar-ho per referència
			Map<String, PosAssig> copyShrek = new HashMap<String, PosAssig>(shrek);
			
			
			// 1.	Agafar la primera Lecture, que ha estat ordenat heruísticament
			Pair<Integer, Lecture> firstCandidate = heuristica.poll(); // També l'elimino de la PQ
			Lecture lecture = firstCandidate.getValue(); // Lecture
			
			String group = lecture.getGroup(); // Grup ///////////////////////////////////////////////////////////
			Integer duration = lecture.getDuration(); // Duració de la Lecture ///////////////////////////////////

			PosAssig possibleAssignacions = shrek.get(lecture.toString());
			
			// 2.	Eliminar la lecture de shrek
			shrek.remove(lecture.toString()); // Ara ha de ser shrek o copyShrek?????????????????????????????????

			Map<Integer, Map<Integer, Set<String>>> posA = possibleAssignacions.getMap(); // Això és el map de dia hora i aula
			
			// 3.	Iterem per totes les possible assignacions
			for (Map.Entry<Integer, Map<Integer, Set<String>>> firstOfposAssig : posA.entrySet()) {
				Integer day = firstOfposAssig.getKey(); // Dia ///////////////////////////////////////////////////
				Map<Integer, Set<String>> hourRoom = firstOfposAssig.getValue(); // Hora i Aula
				
				for (Map.Entry<Integer, Set<String>> HR : hourRoom.entrySet()) {
					Integer hour = HR.getKey(); // Hour //////////////////////////////////////////////////////////////
					Set<String> Rooms = HR.getValue();
					
					for (String room : Rooms) {
						// 4.	Afegir-lo al schedule (Map<String, String[][]>)
						Integer h = 0;
						while (h < duration) {
							schedule.putLecture(room, day, hour+h, group);
							++h;
						}
						
						// 5.	Podar
						boolean podat; // Funció d'en Laca (passant-li copyShrek)
						
						if (podat) {
							boolean possible = generate(schedule, copyShrek, naryRestrictions, heuristica); // True => És possible generar l'horari
																											// False => No és possible
							if (possible) return true;
							else {
								// Borrar de l'schedule i seguir iterant per les possibles assignacions
								h = 0;
								while (h < duration) {
									schedule.removeLecture(room, day, hour+h);
									++h;
								}
							}
						} else {
							// Borrar de l'schedule i seguir iterant per les possibles assignacions
							h = 0;
							while (h < duration) {
								schedule.removeLecture(room, day, hour+h);
								++h;
							}
							
							// Backtracking => Fa falta aquí? Crec que no
							/*shrek.put(lecture.toString(), possibleAssignacions);
							Pair<Integer, Lecture> p = new Pair<Integer, Lecture>(firstCandidate.getKey(), lecture); // Canvair nom si volem
							heuristica.add(p);
							
							return false;*/
						}
					}
				}
			}
			// Backtracking
			shrek.put(lecture.toString(), possibleAssignacions);
			Pair<Integer, Lecture> p = new Pair<Integer, Lecture>(firstCandidate.getKey(), lecture); // Canvair nom si volem
			heuristica.add(p);
			
			return false;
		}
	}
	
	/* ********************** FORWARD CHECKING ******************
	 * Función: forward checking (vfuturas, solucion)
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