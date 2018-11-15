package domain.classes;

import java.util.ArrayList;

/** Representa una assignatura.
 * @author XX
*/

public class Subject {
	
	/** Codi identificador únic. Ex: "PROP", "M2"
	*/
	private String code;
	
	/** Nom complet. Ex: "Projectes de Programació", "Matemàtiques 2"
	*/
	private String name;
	
	/** Nivell de l’assignatura en el pla docent. Ex: "Q1", "Q5"
	*/
	private String level;
	
	/** Codis dels grups de l'assignatura. Ex: "['PROP40T', 'PROP41L', 'PROP42L']"
	*/
	private ArrayList<String> groups;
	
	/** Codis de les assignatures que son corequisit d’aquesta. Ex: "['FM', 'M1']"
	*/
	private ArrayList<String> coreqs;

	/** Constructora estàndard.
	 * @param code		Codi de l'assignatura.
	 * @param name		Nom complet de l'assignatura.
	 * @param level		Nivell de l'assignatura.
	 * @param groups	Grups de l'assignatura.
	 * @param coreqs	Assignatues corequisites de l'assignatura que estem creant.
	*/
	public Subject(String code, String name, String level, ArrayList<String> groups, ArrayList<String> coreqs) {
		this.code = code;
		this.name = name;
		this.level = level;
		this.coreqs = coreqs;
		this.groups = groups;
	}
	
	/**
	 * Retorna el codi de l'assignatura.
	 * @return {@link Subject#code}
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Retorna el nom de l'assignatura.
	 * @return {@link Subject#name}
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Retorna el nivell de l'assignatura.
	 * @return {@link Subject#level}
	 */
	public String getLevel() {
		return level;
	}
	
	/**
	 * Retorna la llista de corequisits de l'assignatura.
	 * @return {@link Subject#coreqs}
	 */
	public ArrayList<String> getCoreqs() {
		return coreqs;
	}
	
	/**
	 * Retorna la llista de grups de l'assignatura.
	 * @return {@link Subject#groups}
	 */
	public ArrayList<String> getGroups() {
		return groups;
	}
	
	/**
	 * @return El String que identifica l'assignatura.
	 */
	@Override
	public String toString() {
		return code;
	}
}