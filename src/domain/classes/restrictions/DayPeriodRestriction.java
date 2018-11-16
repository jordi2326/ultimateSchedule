package domain.classes.restrictions;

import domain.classes.Group;

/** Restricció sobre el nivell de les assignatures.
 *  Dos grups d'assignatures del mateix nivell no poden anar al mateix dia i hora si tenen el mateix codi i són del mateix tipus.
 *  	Ex: FM 10 T no pot anar al mateix dia i hora que PRO1 10 T, però sí pot anar amb PRO1 11 L i PRO1 20 T.
 * @author Xavier Lacasa Curto
*/

public class DayPeriodRestriction extends UnaryRestriction{
	
	/** 
	 * Hora en que passem a classes de tarda.
	 */
	private Integer midDay;
	
	/**
	 * Període del dia en el que ha d'anar el grup.
	 */
	private Group.DayPeriod dayPeriod;
	
	/** Constructora estàndard.
	 * @param midDay	Hora en que passem a classes de tarda.
	 * @param dayPeriod	Període del dia en el que ha d'anar el grup.
	 */
	public DayPeriodRestriction(Integer midDay, Group.DayPeriod dayPeriod) {
		super(true); //negotiable
		this.midDay = midDay;
		this.dayPeriod = dayPeriod;
	}
	
	/**
	 * @return El String que identifica la restricció.
	 */
	public String toString() {
		return DayPeriodRestriction.class.getSimpleName() + "-" + midDay + "-" + dayPeriod;
	}
	
	/**
	 * Validació de la restricció.
	 * @param day		Dia en que, combinat amb l'hora, no pot anar el grup.
	 * @param hour		Hora en que, combinada amb el dia, no pot anar el grup.
	 * @param duration	Duració del grup.
	 * @return True si<br>
	 * <ul>
	 * 		<li> El grup ha d'anar de matins i hour + duration <= midDay.</li>
	 * 		<li> El grup ha d'anar de tardes i midDay < (hour + duration) <= Última hora de classes del dia.</li>
	 * 		<li> El grup pot anar tant de matins com tardes i hour + duration <= Última hora de classes del dia.</li>
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
