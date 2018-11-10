package domain.controllers;

import domain.Schedule;
import domain.Subject;
import domain.posAssig;
import domain.Group.DayPeriod;
import domain.Group.Type;
import domain.Lecture;
import domain.controllers.CtrlDomain;
import domain.restrictions.NaryRestriction;
import domain.restrictions.UnaryRestriction;
import javafx.util.Pair;

import domain.Room;
import domain.Group;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
// import java.util.Calendar; No s'utilitza ja
import java.util.HashMap;
import java.util.HashSet;

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

	
	// **************************** PROVA MAIN ********************************
	
	public static void main(String [] args) {
		CtrlDomain domainController = CtrlDomain.getInstance();
		
		// Map<String, Group> groups = domainController.groups;
		// Map<String, Room> rooms = domainController.rooms;
		
		// Harcodejare els grups i aules
		HashMap<String, Group> groups = new HashMap<String, Group>();
		HashMap<String, Room> rooms = new HashMap<String, Room>();
		
		// Borrem tot el que hi hagi (proves)
		//groups.clear();
		//rooms.clear();
		
		// Aules
		Room room = new Room("A6001", 90, false);
		rooms.put(room.toString(), room);
		
		room = new Room("A6002", 80, false);
		rooms.put(room.toString(), room);
		
		// Grups
		ArrayList<Integer> durations = new ArrayList<Integer>();
		durations.add(1);
		durations.add(2);
		
		Type t = Type.THEORY;
		DayPeriod d = DayPeriod.MORNING;
		Group group = new Group("10", 90, "10", "PRO1", t, d, durations);
		groups.put(group.toString(), group);
		
		
		durations = new ArrayList<Integer>();
		durations.add(2);
		
		t = Type.LABORATORY;
		d = DayPeriod.MORNING;
		group = new Group("11", 30, "10", "PRO1", t, d, durations);
		groups.put(group.toString(), group);
		
		// Fib calendar
		ArrayList<Timeframe> timeFrames = domainController.generatePossibleTimeframes(DayOfWeek.MONDAY, DayOfWeek.FRIDAY, 8, 20);
		
		// Preguntar carlos si ja existeix aquesta funcio: fer un ArrayList de Restriction
		ArrayList<UnaryRestriction> restrictions = new ArrayList<UnaryRestriction>();
		
		// S'haurï¿½ d'imprimir per pantalla?
		// Guardar en un file?
		generateSchedule(groups, rooms, timeFrames, restrictions);
		System.out.println("All correct");
	}
	
	// ************************************************************************
	
	public static void generateSchedule(Map<String, UnaryRestriction> unaryRestrictions, Map<String, NaryRestriction> naryRestrictions, Map<String, Group> groups, Map<String, Subject> subjects, Map<String, Room> rooms) {
		//TODO: Implementar la restriction de que un grup no vagi en un dia o hora concrets
		//Filter possible rooms hours and days for each group according to room capacity, PC's, day period and restrictions of days / hours
		Map<String, Map<Integer, Set<Map <Integer, Set<String>>>>> shrek = new HashMap<String, Map<Integer, Set<Map <Integer, Set<String>>>>>();
		
		//no se si s'ordena sol. L'Integer es el numero de classes totals a les que pot anar aquell grup
		PriorityQueue<Pair<Integer, String>> pq = new PriorityQueue<Pair<Integer, String>>(); // PriorityQueue<Pair<Int, Group.toString()>>
		for (Group g : groups.values()) {
			Integer totalGroupRooms = 0;
			Set<String> roomSet = new HashSet<String>();
			for (Room r : rooms.values()) {
				if (g.getNumPeople() <= r.getCapacity()) {
					if ((g.getType() == Group.Type.LABORATORY && r.hasComputers())
						|| (g.getType() != Group.Type.LABORATORY)) {
						roomSet.add(r.toString());
						totalGroupRooms += 1;
					}
				}
			}
			Map<Integer, Set<Map <Integer, Set<String>>>> dayHourRooms = new HashMap<Integer, Set<Map <Integer, Set< String>>>>();
			for (int day = 0; day < 5; ++day) {
				Set<Map <Integer, Set<String>>> setHourRooms = new HashSet<Map <Integer, Set<String>>>();
				for (int hour = 0; hour < 12; ++hour) {	
					Map <Integer, Set<String>> hourRooms = new HashMap<Integer, Set<String>>();
					boolean valid = true;
					for (UnaryRestriction restr : unaryRestrictions.values()) {
						if (!restr.validate(day, hour)) {
							valid = false;
						}
					}
					if (valid) {
						hourRooms.put(hour, roomSet);
					}
					setHourRooms.add(hourRooms);
				}
				dayHourRooms.put(day, setHourRooms);
			}
			shrek.put(g.toString(), dayHourRooms);
			Pair<Integer, String> pair = new Pair<>(totalGroupRooms, g.toString());
			pq.add(pair);
		}
		
		Schedule schedule = new Schedule();
		
	
		
		boolean exists = generate(schedule, shrek, pq, naryRestrictions);
		
		if (exists) {
			schedule.imprimir();
		} else {
			// Error
		}
	}
	
	private static boolean generate(Schedule schedule, Map<String, posAssig> shrek, Map<String, NaryRestriction[]> naryRestrictions, PriorityQueue<Pair<Integer, Lecture>> heuristica) { // Canviar nom de la PQ si volem xD		
		// Pre: a shrek hi tenim només les Lectures que falten afegir i les assignacions possibles que li podem donar. Només les possibles! (forward checking)
		// Pre: a més, per com està feta la funció podar, no hi ha cap Lecture amb 0 possibles assignacions
		
		// Cas base (CORRECTE) => shrek està buit => No queda res per afegir
		if (shrek.size() == 0) {
			return true;
		} else { // Encara tenim Lectures per afegir
			// 1.	Agafar la primera Lecture, que ha estat ordenat heruísticament
			Pair<Integer, Lecture> firstCandidate = heuristica.poll(); // També l'elimino de la PQ
			Lecture lecture = firstCandidate.getValue(); // Lecture
			
			String group = lecture.getGroup(); // Grup ///////////////////////////////////////////////////////////
			Integer duration = lecture.getDuration(); // Duració de la Lecture ///////////////////////////////////

			posAssig possibleAssignacions = shrek.get(lecture.toString());
			
			// 2.	Eliminar la lecture de shrek
			shrek.remove(lecture.toString());

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
						String[][] scheduleRoom = schedule.getScheduleOf(room);
						Integer h = 0;
						while (h < duration) {
							scheduleRoom[hour + h][day] = group;
							++h;
						}
						
						// 5.	Podar
						boolean podat; // Funció d'en Laca
						
						if (podat) {
							boolean possible = generate(schedule, shrek, naryRestrictions, heuristica); // True => És possible generar l'horari
																										// False => No és possible
							if (possible) return true;
							else {
								// Borrar de l'schedule i seguir iterant per les possibles assignacions
								h = 0;
								while (h < duration) {
									scheduleRoom[hour + h][day] = null;
									++h;
								}
							}
						} else {
							// Borrar de l'schedule i seguir iterant per les possibles assignacions
							h = 0;
							while (h < duration) {
								scheduleRoom[hour + h][day] = null;
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