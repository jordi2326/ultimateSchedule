package domain.classes.restrictions;

import domain.classes.Group;

/** Restriccio sobre el nivell de les assignatures.
 *  Dos grups d'assignatures del mateix nivell no poden anar al mateix dia i hora si tenen el mateix codi i son del mateix tipus.
 *  	Ex: FM 10 T no pot anar al mateix dia i hora que PRO1 10 T, pero si pot anar amb PRO1 11 L i PRO1 20 T.
 * @author Xavier Lacasa Curto
*/

public class DayPeriodRestriction extends UnaryRestriction{
	
	/** 
	 * Hora en que passem a classes de tarda.
	 */
	private Integer midDay;
	
	/**
	 * Per�ode del dia en el que ha d'anar el grup.
	 */
	private Group.DayPeriod dayPeriod;
	
	/** Constructora estandard.
	 * @param midDay	Hora en que passem a classes de tarda.
	 * @param dayPeriod	Periode del dia en el que ha d'anar el grup.
	 */
	public DayPeriodRestriction(Integer midDay, Group.DayPeriod dayPeriod) {
		super(false); //negotiable
		this.midDay = midDay;
		this.dayPeriod = dayPeriod;
	}
	
	/**
	 * @return El String que identifica la restriccio.
	 */
	public String toString() {
		return DayPeriodRestriction.class.getSimpleName() + "-" + midDay + "-" + dayPeriod;
	}
	
	/**
	 * @return El String que es veura en pantalla.
	 */
	public String stringView() {
		String res = "Day Period Restriction";
		if (midDay < 10) res += (" (" + dayPeriod + " - 0" + midDay + ":00)");
		else res += (" (" + dayPeriod + " - " + midDay + ":00)");
		return res;
	}
	
	/**
	 * Validacio de la restriccio.
	 * @param day		Dia en que, combinat amb l'hora, no pot anar el grup.
	 * @param hour		Hora en que, combinada amb el dia, no pot anar el grup.
	 * @param duration	Duracio del grup.
	 * @return True si<br>
	 * <ul>
	 * 		<li> El grup ha d'anar de matins i hour + duration <= midDay.</li>
	 * 		<li> El grup ha d'anar de tardes i midDay < (hour + duration) <= ultima hora de classes del dia.</li>
	 * 		<li> El grup pot anar tant de matins com tardes i hour + duration <= ultima hora de classes del dia.</li>
	 * </ul>
	 * False en cas contrari.
	 */
	@Override
	public boolean validate(Integer day, Integer hour, Integer duration) {
		if ((dayPeriod.equals(Group.DayPeriod.MORNING)) && (hour + duration > midDay)) {
			return false;
		}
		if ((dayPeriod.equals(Group.DayPeriod.AFTERNOON)) && (hour < midDay || hour+duration > 12)) {
			return false;
		}
		if ((dayPeriod.equals(Group.DayPeriod.INDIFERENT)) && hour + duration > 12) {
			return false;
		}
		return true;
	}
}
