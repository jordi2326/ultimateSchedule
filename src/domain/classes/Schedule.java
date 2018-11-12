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

public class Schedule {
	Map<String, String[][]> schedule;
	
	public Schedule() {
		schedule = new HashMap<String, String[][]>(); //String es codi del grup 
	}
	
	public Schedule(String json) {
		schedule = new HashMap<String, String[][]>();
		try {
			JSONObject jo = (JSONObject) new JSONParser().parse(json);
			for(Map.Entry<String,JSONObject> dayE : (Set<Map.Entry<String,JSONObject>>) jo.entrySet()) {
				for(Map.Entry<String,JSONObject> hourE : (Set<Map.Entry<String,JSONObject>>) dayE.getValue().entrySet()) {
					for(Map.Entry<String,String> l : (Set<Map.Entry<String,String>>) hourE.getValue().entrySet()) {
						this.putLecture(l.getKey(), DayOfWeek.valueOf(dayE.getKey()).ordinal(), Integer.parseInt(hourE.getKey()), l.getValue());
					}
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	public boolean putLecture(String room, int day, int hour, String group) {
		if(!schedule.containsKey(room)){
			String[][] ls = new String[5][12];
			ls[day][hour] = group;
			schedule.put(room, ls);
		}else {
			schedule.get(room)[day][hour]=group;
		}
		return true;
	}
	
	public boolean removeLecture(String room, int day, int hour) {
		if(schedule.containsKey(room)){
			schedule.get(room)[day][hour]=null;
			return true;
		}
		return false;
	}
	
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
        				if(!((JSONObject) jo.get(dayName)).containsKey(hour.toString())) ((JSONObject) jo.get(dayName)).put(hour.toString(), new JSONObject());
        				((JSONObject) ((JSONObject) jo.get(dayName)).get(hour.toString())).put(roomName, roomSchedule[day][hour]);
        			}
        		}
        	}
        }
        
        return jo.toJSONString(0);        
	}
}
