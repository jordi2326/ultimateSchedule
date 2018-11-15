package domain.classes;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/** Representa un conjunt d'assignacions candidates (aules i hores/dies) per quan es genera un horari.
 * @author XX
*/

public class PosAssig {
	/** Mapa de les possibles assignacions d'una sessi� d'un grup en concret.
	 *  El mapa t� de key els dies i, per cada dia, t� un mapa amb les hores com a clau i les aules que poden anar-hi com a valor.
	*/
	Map<Integer, Map< Integer, Set<String>>> shrek;
	//   Dia		    Hora		Aula
	
	/** Constructora est�ndard.
	*/
	public PosAssig() {
		shrek = new HashMap<Integer, Map< Integer, Set<String>>>();
	}
	
	/** Constructora est�ndard.
	 * @param shrek Conjunt de possibles assignacions.
	*/
	public PosAssig(Map<Integer,Map <Integer, Set<String>>> shrek) {
		this.shrek = shrek;
	}
	
	/**
	 * Retorna les possibles assignacions.
	 * @return {@link PosAssig#shrek}
	 */
	public Map<Integer, Map< Integer, Set<String>>> getMap() {
		return shrek;
	}

	/**
	 * Indica si un dia concret est� a {@link PosAssig#shrek} o no.
	 * @param day Dia a mirar.
	 * @return Retorna true si hi ha una possible assignaci� en un dia concret. Fals en cas contrari.
	 */
	public boolean hasDay(Integer day) {
		return shrek.containsKey(day);
	}
	
	/**
	 * Indica si hour est� a {@link PosAssig#shrek} en un dia concret o no.
	 * Pre: day s� que est� dins {@link PosAssig#shrek}.
	 * @param day  Dia a mirar.
	 * @param hour Hora a mirar.
	 * @return Retorna true si hi ha una possible assignaci� en un dia i hora concrets. Fals en cas contrari.
	 */
	public boolean hasHourFromDay(Integer day, Integer hour) {
		return shrek.get(day).containsKey(hour);
	}
	
	/**
	 * Indica si a {@link PosAssig#shrek}, a un dia determinat no hi ha possibles assignacions.
	 * @param day  Dia a mirar.
	 * @return Retorna true si no hi ha possibles assignacions a day. Fals en cas contrari.
	 */
	public boolean dayIsEmpty(Integer day) {
		return shrek.get(day).isEmpty();
	}
	
	/**
	 * Indica si a {@link PosAssig#shrek} no hi ha possibles assignacions.
	 * @return Retorna true si no hi ha possibles assignacions. Fals en cas contrari.
	 */
	public boolean hasNoDays() {
		return shrek.isEmpty();
	}
	
	/**
	 * Elimina de {@link PosAssig#shrek} totes les possibles assignacions per un dia i hora concrets.
	 * @param day	Dia on eliminar les possibles assignacions.
	 * @param hour	Hora on eliminar les possibles assignacions.
	 * @return {@link PosAssig#shrek} amb totes les possibles assignacions a day i hour eliminats.
	 */
	public void removeHourFromDay(Integer day, Integer hour) {
		shrek.get(day).remove(hour);
	}
	
	/**
	 * Elimina de {@link PosAssig#shrek} totes les possibles assignacions per un dia concret.
	 * @param day	Dia on eliminar les possibles assignacions.
	 * @return {@link PosAssig#shrek} amb totes les possibles assignacions a day eliminats.
	 */
	public void removeDay(Integer day) {
		shrek.remove(day);
	}
	
	/**
	 * Indica si a {@link PosAssig#shrek} en un dia i hora concrets hi ha una aula concreta.
	 * @param day	Dia on buscar l'aula.
	 * @param hour	Hora on buscar l'aula.
	 * @param room	Aula que volem buscar.
	 * @return Retorna true si a {@link PosAssig#shrek} apareix room a day i hour. Fals en cas contrari.
	 */
	public boolean hasRoomFromDayAndHour(Integer day, Integer hour, String room) {
		return shrek.get(day).get(hour).contains(room);
	}
	
	/**
	 * Elimina de {@link PosAssig#shrek} una aula concreta en una dia i hora determinats.
	 * @param day	Dia on eliminar l'aula.
	 * @param hour	Hora on eliminar l'aula.
	 * @param room	Aula que volem eliminar.
	 * @return {@link PosAssig#shrek} amb la room eliminada a day i hour. Si un cop eliminada la room a day i hour no queden possibles assignacions, tamb� eliminarem hour.
	 */
	public void removeRoomFromHourAndDay(Integer day, Integer hour, String room) {
		shrek.get(day).get(hour).remove(room);
		if (shrek.get(day).get(hour).isEmpty()) {
			shrek.get(day).remove(hour);
		}
	}
}
