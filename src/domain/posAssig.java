package domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class posAssig {
	Map<Integer, Map< Integer, Set<String>>> shrek;
	//   Dia		    Hora		Aula
	
	public posAssig() {
		shrek = new HashMap<Integer, Map< Integer, Set<String>>>();
	}
	
	public Map<Integer, Map< Integer, Set<String>>> getMap() { // Pensar en canviar el nom xD
		return shrek;
	}

}
