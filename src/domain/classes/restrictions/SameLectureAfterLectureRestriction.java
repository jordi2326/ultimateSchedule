package domain.classes.restrictions;

public class SameLectureAfterLectureRestriction extends NaryRestriction{
	
	/** Constructora est�ndard.
	 */
	public SameLectureAfterLectureRestriction() {
		super(false); //negotiable
	}
	
	/**
	 * @return El String que identifica la restricci�.
	 */
	public String toString() {
		return SameLectureAfterLectureRestriction.class.getSimpleName();
	}

}
