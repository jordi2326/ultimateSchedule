package domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class PosAssig {
	Map<Integer, Map< Integer, Set<String>>> shrek;
	//   Dia		    Hora		Aula
	
	public PosAssig() {
		shrek = new HashMap<Integer, Map< Integer, Set<String>>>();
	}
	
	public Map<Integer, Map< Integer, Set<String>>> getMap() { // Pensar en canviar el nom xD
		return shrek;
	}
	
	public PosAssig(Map<Integer,Map <Integer, Set<String>>> shrek) {
		this.shrek = shrek;
	}
	
	public boolean hasDay(Integer day) {
		return shrek.containsKey(day);
	}
	
	public boolean hasHourFromDay(Integer day, Integer hour) {
		return shrek.get(day).containsKey(hour);
	}
	
	public boolean dayIsEmpty(Integer day) {
		return shrek.get(day).isEmpty();
	}
	
	public boolean isEmpty() {
		return shrek.isEmpty();
	}
	
	public void removeHourFromDay(Integer day, Integer hour) {
		shrek.get(day).remove(hour);
	}
	
	public void removeDay(Integer day) {
		shrek.remove(day);
	}
}