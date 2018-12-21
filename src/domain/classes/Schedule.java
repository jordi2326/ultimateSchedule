/**
 * 
 */
package domain.classes;

import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/** Representa un horari.
 * @author Carlos Bergillos Varela
*/

public class Schedule {
	/** Horari.
	*/
	Map<String, String[][]> schedule;
	
	/** Constructora estandard.
	*/
	public Schedule() {
		schedule = new HashMap<String, String[][]>(); //String es codi del grup 
	}

	/**
	 * Retorna l'horari.
	 * @return {@link Schedule#schedule}.
	 */
	public Map<String, String[][]> getSchedule() {
		return schedule;
	}

	/** Constructora a partir d'un String JSON.
	*	@param json	Text Json que descriu l'horari.
	*/
	@SuppressWarnings("unchecked")
	public Schedule(String json) {
		schedule = new HashMap<String, String[][]>();
		try {
			JSONObject jo = (JSONObject) new JSONParser().parse(json);
			for(Map.Entry<String,JSONObject> dayE : (Set<Map.Entry<String,JSONObject>>) jo.entrySet()) {
				for(Map.Entry<String,JSONObject> hourE : (Set<Map.Entry<String,JSONObject>>) dayE.getValue().entrySet()) {
					for(Map.Entry<String,String> l : (Set<Map.Entry<String,String>>) hourE.getValue().entrySet()) {
						this.putLecture(l.getKey(), DayOfWeek.valueOf(dayE.getKey()).ordinal(), Integer.parseInt(hourE.getKey())-8, l.getValue());
					}
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	/** Associa un grup amb una aula, dia i hora determinades.
	*	@param room		Aula on afagirem el grup.
	*	@param day		Dia on afegirem el grup.
	*	@param hour		Hora on afegirem el grup.
	*	@param group	Grup a afegir a l'horari.
	*	@return L'horari amb el grup afegit a l'aula room al dia day i hora hour.
	*/
	public boolean putLecture(String room, Integer day, Integer hour, String group) {
		if(!schedule.containsKey(room)){
			String[][] ls = new String[5][12];
			ls[day][hour] = group;
			schedule.put(room, ls);
		}else {
			schedule.get(room)[day][hour]=group;
		}
		return true;
	}
	
	/** Elimina un grup d'una aula, dia i hora determinades.
	*	@param room		Aula on eliminarem el grup.
	*	@param day		Dia on eliminarem el grup.
	*	@param hour		Hora on eliminarem el grup.
	*	@return Si existeix l'aula, eliminem el grup en que esta i retornem true. Fals en cas contrari.
	*/
	public boolean removeLecture(String room, int day, int hour) {
		if(schedule.containsKey(room)){
			schedule.get(room)[day][hour]=null;
			return true;
		}
		return false;
	}
	
	/** 
	*	Transforma l'horari en un String en format JSON.
	*	@return L'horari en format Json.
	*/
	@SuppressWarnings("unchecked")
	public String toJsonString(){
		DayOfWeek[] dayNames = DayOfWeek.values();
        JSONObject jo = new JSONObject();
        
        for (Entry<String, String[][]> entry : schedule.entrySet()) {
        	String roomName = entry.getKey();
        	String[][] roomSchedule = entry.getValue();
        	for(Integer day = 0; day<5; day++) {
        		String dayName = dayNames[day].toString();
        		for(Integer hour = 0; hour<12; hour++) {
        			if(roomSchedule[day][hour]!=null && !roomSchedule[day][hour].isEmpty()) {
        				if(!jo.containsKey(dayName)) jo.put(dayName, new JSONObject());
        				if(!((JSONObject) jo.get(dayName)).containsKey(Integer.toString(hour+8))) ((JSONObject) jo.get(dayName)).put(Integer.toString(hour+8), new JSONObject());
        				((JSONObject) ((JSONObject) jo.get(dayName)).get(Integer.toString(hour+8))).put(roomName, roomSchedule[day][hour]);
        			}
        		}
        	}
        }
        
        return jo.toJSONString(0);        
	}
	
	/** Compara dos schedules.
	*	@param sc Parametre a comparar.
	*	@return True si sc es igual que this. False en cas contrari.
	*/
	public boolean equals(Schedule sc) {
		for (String room : this.getSchedule().keySet()) {
			if (!sc.getSchedule().containsKey(room)) return false;
			if (this.getSchedule().get(room).length == sc.getSchedule().get(room).length) {
				for (int i = 0; i < this.getSchedule().get(room).length; i++) {
					if (this.getSchedule().get(room)[i].length == sc.getSchedule().get(room)[i].length) {
						for (int j = 0; j < this.getSchedule().get(room)[i].length; ++j) {
							if (this.getSchedule().get(room)[i][j] == null && sc.getSchedule().get(room)[i][j] == null) {
								
							} else {
								if (this.getSchedule().get(room)[i][j] == null && sc.getSchedule().get(room)[i][j] != null) return false;
								if (this.getSchedule().get(room)[i][j] != null && sc.getSchedule().get(room)[i][j] == null) return false;
								if (!this.getSchedule().get(room)[i][j].equals(sc.getSchedule().get(room)[i][j])) return false;
							}	
						}
					} else return false;
					
				}
			} else return false;
		}
		return true;
	}
}
