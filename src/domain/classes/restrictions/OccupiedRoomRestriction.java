package domain.classes.restrictions;

import java.util.Map;
import java.util.Map.Entry;

import domain.classes.Environment;
import domain.classes.Group;
import domain.classes.Lecture;
import domain.classes.PosAssig;
import domain.classes.Subject;

/** Restricci� sobre la disponibilitat de les aules.
 *  Un grup no pot anar a una aula ocupada.
 * @author Xavier Lacasa Curto
*/

public class OccupiedRoomRestriction extends NaryRestriction{
	
	/** Constructora est�ndard.
	 */
	public OccupiedRoomRestriction() {
		super(false); //negotiable
	}
	
	/**
	 * @return El String que identifica la restricci�.
	 */
	public String toString() {
		return OccupiedRoomRestriction.class.getSimpleName();
	}
	
	/** Validaci� de la restricci�.
	 * @param lecture	Sessi� que hem afegit a l'horari.
	 * @param room		Aula on hem afegit la sessi�.
	 * @param day		Dia en el que hem afegit la sessi�.
	 * @param hour		Hora en la que hem afegit la sessi�.
	 * @return True si, un cop eliminat les aules en les que cada sessi� no podia anar, totes les sessions restant poden anar com a m�nim a una aula. False en cas contrari.
	 * Les aules que s'eliminen per cada sessi� restant s�n aquelles que farien anar dos grups al mateix dia, hora i aula.
	*/
	//Per cada lecture mirem totes les aules de totes les hores de tots els dies i les borrem si estaran ocupades per la lecture inserida
	public boolean validate(String room, Integer day, Integer hour, String lecture, Integer d, Integer h, String r, String l) {
		Environment env = Environment.getInstance();
		Integer duration = env.getLectureDuration(lecture);
		Integer dur = env.getLectureDuration(l);
		return !(day.equals(d) && hour < h+dur && h < hour+duration && room.equals(r));
	}
}
