package domain.controllers;

import domain.Schedule;
import domain.Subject;
import domain.PosAssig;
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
	
	public static void generateSchedule(Map<String, UnaryRestriction[]> unaryRestrictions, NaryRestriction[] naryRestrictions, Map<String, Group> groups, Map<String, Room> rooms, Map<String, Subject> subjects, Map<String, Lecture> lectures) {
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
				if (g.getNumPeople() <= r.getCapacity()) {
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
					if ((g.getDayPeriod().equals(Group.DayPeriod.AFTERNOON) && hour <= midDay)
						|| (g.getDayPeriod().equals(Group.DayPeriod.MORNING) && hour > midDay)) {
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
	
	
	
	
	
	
	
	private static boolean forwardCheck(String lecture, String room, Integer day, Integer hour, Map<String, Subject> subjects,
			Map<String, Group> groups, Map<String, Lecture> lectures, Map<String, PosAssig> shrek, NaryRestriction[] naryRestrictions) {
		for (NaryRestriction restr : naryRestrictions) {
			if (!restr.validate(lecture, room, day, hour, subjects, groups, lectures, shrek)) {
				return false;
			}
		}
		return true;
	}
	
	
	
	
	
	
	
	private static boolean generate(Schedule schedule, Map<Lecture, posAssig> shrek, Map<String, NaryRestriction[]> naryRestrictions) {
		// Map<String, String[][]> schedule;
		// Map<Integer, Map< Integer, Set<String>>> shrek;
		//      Dia		       Hora		   Aula
		
		
		return true;
	}
}
