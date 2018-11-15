package domain.classes;

/** Representa una "lecture". Una classe setmanal d'un grup.
 * @author XX
*/

public class Lecture {
	
	private Integer id;
	private String group;
	private Integer duration;
	
	/** Constructora estàndard.
	*/
	public Lecture(Integer id, String group, Integer duration) {
		this.id = id;
		this.group = group;
		this.duration = duration;
	}
	
	public String getGroup() {
		return group;
	}
	
	public Integer getDuration() {
		return duration;
	}
	@Override
	public String toString() {
		return group + id;
	}
}
