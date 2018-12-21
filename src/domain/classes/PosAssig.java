package domain.classes;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/** Representa un conjunt d'assignacions candidates (aules i hores/dies) per quan es genera un horari.
 * @author Carlos Bergillos Varela
*/

public class PosAssig {
	/** Mapa de les possibles assignacions d'una sessio d'un grup en concret.
	 *  El mapa te de key els dies i, per cada dia, te un mapa amb les hores com a clau i les aules que poden anar-hi com a valor.
	*/
	Map<Integer, Map< Integer, Set<String>>> assigMap;
	//   Dia		    Hora		Aula
	
	/** Constructora estandard.
	*/
	public PosAssig() {
		assigMap = new HashMap<Integer, Map< Integer, Set<String>>>();
	}
	
	/** Constructora estandard.
	 * @param assigMap Conjunt de possibles assignacions.
	*/
	public PosAssig(Map<Integer,Map <Integer, Set<String>>> map) {
		assigMap = new HashMap<Integer, Map< Integer, Set<String>>>();
		for (Integer day : map.keySet()) {
			Map<Integer, Set<String>> aux1 = new HashMap<Integer, Set<String>>();
			for (Integer hour : map.get(day).keySet()) {
				Set<String> rooms = new HashSet<String>();
				for (String room : map.get(day).get(hour)) {
					rooms.add(new String(room));
				}
				aux1.put(new Integer(hour), rooms);
			}
			assigMap.put(new Integer(day), aux1);
		} 
		//assigMap = map;
	}
	
	/**
	 * Conjunt de dies en els que hi ha una possible assignacio.
	 * @return El Set amb els dies en els que hi ha una possible assignacio.
	 */
	public Set<Integer> getAllDays() {
		return assigMap.keySet();
	}
	
	/**
	 * Conjunt de hores en les que hi ha una possible assignacio donat un dia concret.
	 * @param day Dia a mirar.
	 * @return El Set amb el conjunt d'hores en les que hi ha una possible assignacio donat el dia {@link PosAssig#day}.
	 */
	public Set<Integer> getAllHoursFromDay(Integer day) {
		return assigMap.get(day).keySet();
	}
	
	/**
	 * Conjunt d'aules en les que hi ha una possible assignacio donat un dia i hora concrets.
	 * @param day	Dia a mirar.
	 * @param hour	Hora a mirar.
	 * @return El Set amb el conjunt d'aules en les que hi ha una possible assignacio donat un dia {@link PosAssig#day} i un hour {@link PosAssig#hour}.
	 */
	public Set<String> getAllRoomsFromHourAndDay(Integer day, Integer hour) {
		return assigMap.get(day).get(hour);
	}
	
	/**
	 * Retorna les possibles assignacions.
	 * @return {@link PosAssig#assigMap}
	 */
	public Map<Integer, Map< Integer, Set<String>>> getMap() {
		return assigMap;
	}
	
	/**
	 * Indica si un dia concret esta a {@link PosAssig#assigMap} o no.
	 * @param day Dia a mirar.
	 * @return Retorna true si hi ha una possible assignacio en un dia concret. Fals en cas contrari.
	 */
	public boolean hasDay(Integer day) {
		return assigMap.containsKey(day);
	}
	
	/**
	 * Indica si hour esta a {@link PosAssig#assigMap} en un dia concret o no.
	 * Pre: day se que esta dins {@link PosAssig#assigMap}.
	 * @param day  Dia a mirar.
	 * @param hour Hora a mirar.
	 * @return Retorna true si hi ha una possible assignacio en un dia i hora concrets. Fals en cas contrari.
	 */
	public boolean hasHourFromDay(Integer day, Integer hour) {
		return (assigMap.containsKey(day) && assigMap.get(day).containsKey(hour));
	}
	
	/**
	 * Indica si a {@link PosAssig#assigMap}, a un dia determinat no hi ha possibles assignacions.
	 * @param day  Dia a mirar.
	 * @return Retorna true si no hi ha possibles assignacions a day. Fals en cas contrari.
	 */
	public boolean dayIsEmpty(Integer day) {
		return assigMap.get(day).isEmpty();
	}
	
	/**
	 * Indica si a {@link PosAssig#assigMap} no hi ha possibles assignacions.
	 * @return Retorna true si no hi ha possibles assignacions. Fals en cas contrari.
	 */
	public boolean isEmpty() {
		return assigMap.isEmpty();
	}
	
	/**
	 * Elimina de {@link PosAssig#assigMap} totes les possibles assignacions per un dia i hora concrets.
	 * @param day	Dia on eliminar les possibles assignacions.
	 * @param hour	Hora on eliminar les possibles assignacions.
	 * @return {@link PosAssig#assigMap} amb totes les possibles assignacions a day i hour eliminats.
	 */
	public void removeHourFromDay(Integer day, Integer hour) {
		assigMap.get(day).remove(hour);
		if (assigMap.get(day).isEmpty()) {
			assigMap.remove(day);
		}
	}
	
	/**
	 * Elimina de {@link PosAssig#assigMap} totes les possibles assignacions per un dia concret.
	 * @param day	Dia on eliminar les possibles assignacions.
	 * @return {@link PosAssig#assigMap} amb totes les possibles assignacions a day eliminats.
	 */
	public void removeDay(Integer day) {
		assigMap.remove(day);
	}
	
	/**
	 * Indica si a {@link PosAssig#assigMap} en un dia i hora concrets hi ha una aula concreta.
	 * @param day	Dia on buscar l'aula.
	 * @param hour	Hora on buscar l'aula.
	 * @param room	Aula que volem buscar.
	 * @return Retorna true si a {@link PosAssig#assigMap} apareix room a day i hour. Fals en cas contrari.
	 */
	public boolean hasRoomFromDayAndHour(Integer day, Integer hour, String room) {
		return assigMap.get(day).get(hour).contains(room);
	}
	
	/**
	 * Elimina de {@link PosAssig#assigMap} una aula concreta en una dia i hora determinats.
	 * @param day	Dia on eliminar l'aula.
	 * @param hour	Hora on eliminar l'aula.
	 * @param room	Aula que volem eliminar.
	 * @return {@link PosAssig#assigMap} amb la room eliminada a day i hour. Si un cop eliminada la room a day i hour no queden possibles assignacions, tambe eliminarem hour.
	 */
	public void removeRoomFromHourAndDay(Integer day, Integer hour, String room) {
		assigMap.get(day).get(hour).remove(room);
		if (assigMap.get(day).get(hour).isEmpty()) {
			assigMap.get(day).remove(hour);
			if (assigMap.get(day).isEmpty()) {
				assigMap.remove(day);
			}
		}
	}
	
	/**
	 * Afageix {@link PosAssig#room} a {@link PosAssig#day} i {@link PosAssig#hour} seleccionats. Si no existeixen els crea.
	 * @param day	Dia seleccionat.
	 * @param hour	Hora seleccionada.
	 * @param room	Aula a afegir.
	 */
	//Posa room a day i hour seleccionats. Si no existeixen els crea.
	public void putRoomInDayAndHour(Integer day, Integer hour, String room) {
		//Si no te day l'afegim
		if (!assigMap.containsKey(day)) {
			Map<Integer, Set<String>> hoursAndRooms = new HashMap<Integer, Set<String>>();
			assigMap.put(day, hoursAndRooms);
		}
		//Si no te hour l'afegim
		if (!assigMap.get(day).containsKey(hour)) {
			Set<String> rooms = new HashSet<String>();
			assigMap.get(day).put(hour, rooms);
		}
		//Afegim room
		assigMap.get(day).get(hour).add(room);
	}
	
	public Map<Integer, Set<String>> getDayWithAll(Integer day) {
		return assigMap.get(day);
	}
	
	public void setDayWithAll(Integer day, Map<Integer, Set<String>> hoursAndRooms) {
		if (hoursAndRooms != null) assigMap.put(day, hoursAndRooms);
	}
}
