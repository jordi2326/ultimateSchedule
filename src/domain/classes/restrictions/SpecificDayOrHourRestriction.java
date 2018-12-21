package domain.classes.restrictions;

/** Restricci� sobre el dia i l'hora dels grups.
 *  Un grup no podr� anar a un dia i hora que es digui.
 *  	Ex: FM 10 T no pot anar Dilluns a les 09:00.
 * @author Xavier Lacasa Curto
*/

public class SpecificDayOrHourRestriction extends UnaryRestriction{
	
	/** Dia
	*/
	private Integer day;
	
	/** Hora
	*/
	private Integer hour;
	
	/** Constructora est�ndard.
	 * @param day	Dia
	 * @param hour	Hora
	*/
	public SpecificDayOrHourRestriction(Integer day, Integer hour) {
		super(true); //negotiable
		this.day = day;
		this.hour = hour;
	}
	
	/**
	 * @return El String que identifica la restricci�.
	 */
	public String toString() {
		return SpecificDayOrHourRestriction.class.getSimpleName() + "-" + day + "-" + hour;
	}
	
	/**
	 * @return El String que es veura en pantalla.
	 */
	public String stringView() {
		String res = "Day Period Restriction";
		if (hour < 10) res += (" (" + day + " - 0" + hour + ":00)");
		else res += (" (" + day + " - " + hour + ":00)");
		return res;
	}
	
	public Integer getDay() {
		return day;
	}
	
	public Integer getHour() {
		return hour;
	}
	
	/**
	 * Validaci� de la restricci�.
	 * @param day		Dia en que, combinat amb l'hora, no pot anar el grup.
	 * @param hour		Hora en que, combinada amb el dia, no pot anar el grup.
	 * @param duration	Duraci� del grup.
	 * @return True si day i hour s�n el dia i l'hora en el que el grup no pot anar. False en cas contrari.
	 */
	@Override
	public boolean validate(Integer day, Integer hour, Integer duration) {
		if (this.day == null) {
			return (hour > this.hour || hour+duration <= this.hour);
		}
		else if (this.hour == null) {
			return day != this.day;
		}
		else return (day != this.day) || (hour > this.hour || hour+duration <= this.hour);
	}

}
