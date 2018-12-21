package domain.classes.restrictions;

import java.util.Map;
import java.util.Map.Entry;

import domain.classes.Environment;
import domain.classes.Group;
import domain.classes.Lecture;
import domain.classes.PosAssig;
import domain.classes.Subject;

/** Restriccio sobre la disponibilitat de les aules.
 *  Un grup no pot anar a una aula ocupada.
 * @author Xavier Lacasa Curto
*/

public class OccupiedRoomRestriction extends NaryRestriction{
	
	/** Constructora estandard.
	 */
	public OccupiedRoomRestriction() {
		super(false); //negotiable
	}
	
	/**
	 * @return El String que identifica la restriccio.
	 */
	public String toString() {
		return OccupiedRoomRestriction.class.getSimpleName();
	}
	
	/**
	 * @return El String que es veura en pantalla.
	 */
	public String stringView() {
		return "Occupied Room Restriction";
	}
	
	/** Validacio de la restriccio.
	 * @param lecture	Sessio que hem afegit a l'horari.
	 * @param room		Aula on hem afegit la sessi�.
	 * @param day		Dia en el que hem afegit la sessio.
	 * @param hour		Hora en la que hem afegit la sessio.
	 * @return True si, un cop eliminat les aules en les que cada sessio no podia anar, totes les sessions restant poden anar com a minim a una aula. False en cas contrari.
	 * Les aules que s'eliminen per cada sessio restant son aquelles que farien anar dos grups al mateix dia, hora i aula.
	*/
	//Per cada lecture mirem totes les aules de totes les hores de tots els dies i les borrem si estaran ocupades per la lecture inserida
	public boolean validate(String room, Integer day, Integer hour, String lecture, Integer d, Integer h, String r, String l) {
		Environment env = Environment.getInstance();
		Integer duration = env.getLectureDuration(lecture);
		Integer dur = env.getLectureDuration(l);
		return !(day.equals(d) && hour < h+dur && h < hour+duration && room.equals(r));
	}
}
