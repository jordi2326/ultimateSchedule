/**
 * 
 */
package domain.classes;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Schedule {
	Map<String, String[][]> schedule;
	
	public Schedule() {
		schedule = new HashMap<String, Group[][]>();
	}
	
	public String[][] getScheduleOf (String room) {
		return schedule.get(room);
	}
	
	public Schedule (Map<Timeframe, Map <String, String>> schedule) {
		this.schedule = schedule;
	}
	
	public Map<Timeframe, Map <String, String>> getScheduleAsMap() {
		return schedule;
	}Map<String, Group> groups, Map<String, Room> rooms, ArrayList<Timeframe> timeFrames, ArrayList<Restriction> restrictions, Schedule schedule
	
	public Integer getSizeOf(Timeframe timeFrame) {
		return this.schedule.get(timeFrame).size();
	}
	
	public boolean te(Timeframe timeFrame) {
		return schedule.containsKey(timeFrame);
	}
	
	/*public Calendar getFirst() {
		for (Timeframe tf : schedule.keySet()) {
			return tf;
		}
	}*/
	
	/**
	 * @param c
	 * @param r
	 * @param g
	 * @param duration
	 */
	// TODO: Que passa si nHours es 2 i la primera hora es pot afegir pero a la segona no es pot? Perque en teoria retornen False a la
	// segona hora pero la primera ja ha estat afegida
	public boolean addLecture(Timeframe c, String r, String g, Integer duration) {
		Integer nHours = duration;
		Timeframe iniTime = c;
		//Check if there's any class at time iniTime (because this will determine whether the Map<String, String> exists or not)
		while (nHours > 0) {
			if(schedule.containsKey(iniTime)) {
				//If the room is already occupied return false
				if(schedule.get(iniTime).containsKey(r)) return false;
				//If room is available, add lecture to schedule
				schedule.get(iniTime).put(r,g);
			} else {
				//If there isn't any class at time iniTime, we first create a map with a lecture
				Map<String, String> n = new HashMap<String, String>();
				n.put(r, g);
				//and put the map with 1 lecture in the schedule at time iniTime
				schedule.put(iniTime, n);
			}
			//Decrease by 1 the duration left
			nHours -= 1;
			//Increase by 1 the next hour at which the following piece of lecture will start

			iniTime = new Timeframe(iniTime.getDayOfWeek(), iniTime.getTime().plusHours(1));
		}	
		//Everything has worked and lecture has been added to schedule
		return true;
	}
	
	// Es pot fer millor? (Passant-li duration i quan troba el que volem anar iterant fins que acabi duration)
	// L'he creat per si la necessitem pel que he dit a la funcio anterior (enlloc de return false cridar aquesta funcio)
	public void removeLecture(String r, String g) {
		// For all the keys of the calendar
		for (Timeframe c : schedule.keySet()) {
			// For all the rooms of one day & hour
			for (String ro : schedule.get(c).keySet()) {
				// If the key ro is equal to the @r
				if (ro.equals(r)) {
					// If the value is @g
					if (schedule.get(c).get(ro).equals(g)) {
						schedule.get(c).remove(ro);
					}
				}
			}
		}
	}

	public void imprimir() {
		for (Timeframe tf : schedule.keySet()) {
			System.out.println("DHA: " + tf + "\n" + "\n");
			for (String ro : schedule.get(tf).keySet()) {
				System.out.println("Room: " + ro + "	" + "Group: " + schedule.get(tf).get(ro) +"\n");
			}
		}
	}

	public Map<String, String> getLecturesAtTime(Timeframe c) {
		return schedule.get(c);
	}
}
