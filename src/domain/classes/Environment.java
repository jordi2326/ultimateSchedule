package domain.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import domain.classes.Group.DayPeriod;
import domain.classes.restrictions.CorequisitRestriction;
import domain.classes.restrictions.DayPeriodRestriction;
import domain.classes.restrictions.LectureFromSameGroupOverlapRestriction;
import domain.classes.restrictions.NaryRestriction;
import domain.classes.restrictions.OccupiedRoomRestriction;
import domain.classes.restrictions.ParentGroupOverlapRestriction;
import domain.classes.restrictions.SpecificDayOrHourRestriction;
import domain.classes.restrictions.SubjectLevelRestriction;
import domain.classes.restrictions.UnaryRestriction;

/** Representa un escenari.
 * @author Xavier Lacasa Curto
*/

public class Environment {
	/** Instancia d'aquesta classe.
	*/
	private static Environment instance;
	
	/** Relative path (filename of environment)
	*/
	private String path;
	
	/** Conjunt d'assignatures de l'entorn.
	*/
	private Map<String, Subject> subjects;
	
	/** Conjunt d'aules de l'entorn.
	*/
	private Map<String, Room> rooms;
	
	/** Conjunt de grups de l'entorn.
	*/
	private Map<String, Group> groups;
	
	/** Conjunt de 'lectures' de l'entorn.
	*/
	private Map<String, Lecture> lectures;
	
	/** Map de restriccions unaries de l'entorn  
	*/
	private Map<String, Map<String, UnaryRestriction>> unaryRestrictions; //Key = group.toString()
	
	/** Map de restriccions n-aries de l'entorn  
	*/
	private Map<String, Map<String, NaryRestriction>> naryRestrictions;
	
	/** Conjunt de restriccions Naries comunes per cada grup.
	 */
	private Map<String, NaryRestriction> groupRestr;
	
	/**
	 * Constructora estandard.
	 */
	private Environment() {
		subjects = new HashMap<String, Subject>();
		rooms = new HashMap<String, Room>();
		groups = new HashMap<String, Group>();
		lectures = new HashMap<String, Lecture>();
		unaryRestrictions = new HashMap<String, Map<String, UnaryRestriction>>();
		naryRestrictions = new HashMap<String, Map<String, NaryRestriction>>();
		groupRestr = new HashMap<String, NaryRestriction>();		
		
		OccupiedRoomRestriction ocrr = new OccupiedRoomRestriction();
		groupRestr.put(ocrr.toString(), ocrr);
		ParentGroupOverlapRestriction pgor = new ParentGroupOverlapRestriction();
		groupRestr.put(pgor.toString(), pgor);
		CorequisitRestriction cr = new CorequisitRestriction();
		groupRestr.put(cr.toString(), cr);
		SubjectLevelRestriction slr = new SubjectLevelRestriction();
		groupRestr.put(slr.toString(), slr);
		LectureFromSameGroupOverlapRestriction lfgor = new LectureFromSameGroupOverlapRestriction();
		groupRestr.put(lfgor.toString(), lfgor);
	}
	
	/**
	 * Retorna la unica instancia de Environment.
	 * @return
	 */
	public static Environment getInstance() {
		if (instance == null) {
			instance = new Environment();
		}
		return instance;
	}	
	
	/**
	 * Borra tots els elements dels atributs de la classe.
	 */
	public void erase() {
		for (Iterator<Map.Entry<String,Subject>> iter = subjects.entrySet().iterator(); iter.hasNext();) {
			iter.next();
			iter.remove();
		}
		for (Iterator<Map.Entry<String,Room>> iter = rooms.entrySet().iterator(); iter.hasNext();) {
			iter.next();
			iter.remove();
		}
		for (Iterator<Map.Entry<String,Group>> iter = groups.entrySet().iterator(); iter.hasNext();) {
			iter.next();
			iter.remove();
		}
		for (Iterator<Map.Entry<String,Lecture>> iter = lectures.entrySet().iterator(); iter.hasNext();) {
			iter.next();
			iter.remove();
		}
		for (Iterator<Map.Entry<String,Map<String, UnaryRestriction>>> iter = unaryRestrictions.entrySet().iterator(); iter.hasNext();) {
			iter.next();
			iter.remove();
		}
		for (Iterator<Map.Entry<String,Map<String, NaryRestriction>>> iter = naryRestrictions.entrySet().iterator(); iter.hasNext();) {
			iter.next();
			iter.remove();
		}
		path = null;
	}
	
	/**
	 * Setter del path.
	 * @param path	Path de l'escenari.
	 * @return True si el path no existia. False en cas contrari.
	 */
	public Boolean setPath(String path) {
		if (this.path == null) {
			this.path = path;
			return true;
		}
		return false;
	}
	
	public String getPath() {
		return path;
	}
	
	/////////////// RESTRICTIONS //////////////////////////////
	
	/**
	 * Mapa amb totes les restriccions unaries.
	 * @return Totes les restriccions unaries de l'escenari.
	 */
	public Map<String, Map<String, UnaryRestriction>> getUnaryRestrictions() {
		return unaryRestrictions;
	}
	
	/**
	 * Mapa amb totes les restriccions naries.
	 * @return Totes les restriccions naries de l'escenari.
	 */
	public Map<String, Map<String, NaryRestriction>> getNaryRestrictions() {
		return naryRestrictions;
	}
	
	/**
	 * @return Totes les restriccions que son negociables.
	 */
	public ArrayList<Object[]> getNegotiableRestrictions() {
		ArrayList<Object[]> restr = new ArrayList<Object[]>();
		for (String group : groups.keySet()) {
			Map<String, UnaryRestriction> map = unaryRestrictions.get(group);
			for (String rest : map.keySet()) {
				if (map.get(rest).getClass().getSimpleName().equals(SpecificDayOrHourRestriction.class.getSimpleName())) {
					Object[] str = new Object[] {map.get(rest).toString(), group, ((SpecificDayOrHourRestriction) map.get(rest)).getDay(), ((SpecificDayOrHourRestriction) map.get(rest)).getHour(), map.get(rest).isEnabled()};
					restr.add(str);
				}
			}
		}
		
		return restr;
	}
	
	/**
	 * Diu si un grup te restriccions unaries. 
	 * @param g	Group a mirar.
	 * @return True si el grup {@link Environment#g} te restriccions unaries. False en cas contrari.
	 */
	public Boolean groupHasUnaryRestrictions(String g) {
		return unaryRestrictions.containsKey(g);
	}
	
	/**
	 * Diu si un grup te restriccions naries.
	 * @param g	Grup a mirar.
	 * @return True si el grup {@link Environment#g} te restriccions naries. False en cas contrari.
	 */
	public Boolean groupHasNaryRestrictions(String g) {
		return naryRestrictions.containsKey(g);
	}
	
	/**
	 * Retorna un conjunt de tuples de tamany 2.
	 * @param g	Grup a mirar.
	 * @return Set d'ArrayLists d'enters formats per el dia i la hora de la restriccio SpecificDayOrHourRestriction.
	 */
	public  Set<ArrayList<Integer>> getSpecificDayHourInfo(String g) {
		Set<ArrayList<Integer>> info = new HashSet<ArrayList<Integer>>();
		for (UnaryRestriction r : unaryRestrictions.get(g).values()) {
			if (r.isNegotiable()) {
				ArrayList<Integer> dayAndHours = new ArrayList<>();
				dayAndHours.add( ((SpecificDayOrHourRestriction) r).getDay() );
				dayAndHours.add( ((SpecificDayOrHourRestriction) r).getHour() );
				info.add(dayAndHours);
			}
		}
		return info;
	}
	
	/**
	 * Elimina una restriccio.
	 * @param group	Grup on eliminar la restriccio.
	 * @param name Nom de la restriccio a eliminar.
	 * @return True si la restriccio amb nom {@link Environment#name} que pertanyia a {@link Environment#group} s'ha eliminat. False en cas contrari.
	 */
	public boolean removeRestriction(String group, String name) {
		if (unaryRestrictions.get(group).containsKey(name)) {
			unaryRestrictions.get(group).remove(name);
			return true;
		}
		return false;
	}
	
	/**
	 * Retorna el conjunt de restriccions unaries d'un grup en concret.
	 * @param g	Grup on agafar les restriccions.
	 * @return Set de les restriccions unaries que pertanyen al grup {@link Environment#group}.
	 */
	public Set<String> getGroupUnaryRestrictions(String g) {
		return unaryRestrictions.get(g).keySet();
	}
	
	/**
	 * Retorna el conjunt de restriccions naries d'un grup en concret.
	 * @param g	Grup on agafar les restriccions.
	 * @return Set de les restriccions naries que pertanyen al grup {@link Environment#group}.
	 */
	public Set<String> getGroupNaryRestrictions(String g) {
		if (naryRestrictions.containsKey(g)) {
			return naryRestrictions.get(g).keySet();
		}
		return new HashSet<String>();
	}
	
	/**
	 * Valida una restricció concreta d'un grup.
	 * @param g			Nom de la lecture al que pertany la restriccio.
	 * @param r			Nom de la restriccio.
	 * @param day		Dia en que s'ha de validar la restriccio.
	 * @param hour		Hora en que s'ha de validar la restriccio.
	 * @param duration	Duracio de la sessió del grup {@link Environment#g};
	 * @return True si s'ha pogut validar la restriccio unaria. False en cas contrari.
	 */
	public Boolean validateGroupUnaryRestriction(String g, String r, int day, int hour, int duration) {
		return unaryRestrictions.get(g).get(r).validate(day, hour, duration);
	}
	
	/**
	 * Valida una restriccio concreta d'un grup.
	 * @param g			Nom del grup al que pertany la restriccio.
	 * @param restr		Nom de la restriccio.
	 * @param room		Nom de l'aula a la que esta el grup.
	 * @param day		Dia en que esta el grup.
	 * @param hour		Hora en que esta el grup.
	 * @param lecture	Nom de la lecture a comparar amb {@link Environment#l}.
	 * @param d			Dia en el que esta {@link Environment#l}.
	 * @param h			Hora en la que esta {@link Environment#l}.
	 * @param r			Aula en la que esta {@link Environment#l}.
	 * @param l			Nom de la lecture a comparar amb {@link Environment#lecture}.
	 * @return True si s'ha pogut validar la restriccio naria. False en cas contrari.
	 */
	public Boolean validateGroupNaryRestriction(String g, String restr, String room, int day, int hour, String lecture, Integer d, Integer h, String r, String l) {
		return naryRestrictions.get(g).get(restr).validate(room, day, hour, lecture, d, h, r, l);
	}
	
	/**
	 * Afageix una restriccio unaria a un grup.
	 * @param g		Grup al que afegir la restriccio unaria.
	 * @param restr Nom de la restriccio unaria.
	 */
	public void addUnaryRestriction(String g, UnaryRestriction restr) {
		if (!unaryRestrictions.containsKey(g) ) {
			unaryRestrictions.put(g, new HashMap<String, UnaryRestriction>());
		}
		unaryRestrictions.get(g).put(restr.toString(), restr);
	}
	
	/**
	 * Afageix una restriccio naria a un grup.
	 * @param g		Grup al que afegir la restriccio naria.
	 * @param restr Nom de la restriccio naria.
	 */
	public void addNaryRestriction(String g, NaryRestriction restr) {
		if (!naryRestrictions.containsKey(g) ) {
			naryRestrictions.put(g, new HashMap<String, NaryRestriction>());
		}
		naryRestrictions.get(g).put(restr.toString(), restr);
	}
	
	/////////////// GROUPS //////////////////////////////
	
	/**
	 * Afageix un grup.
	 * @param inCode			Codi del grup a afegir.
	 * @param inNPeople			Nombre de persones del grup.
	 * @param inParentGroupCode	Codi del grup pare del grup amb codi {@link Environment#inCode}.
	 * @param subjectCode		Codi de l'assignatura a la qual pertany el grup.
	 * @param inNeedsComputers	Boolea que indica si el grup necessita una aula amb ordinadors o no.
	 * @param inType			Tipus de classe del grup.
	 * @param inDayPeriod		Periode del dia en que ha d'anar el grup.
	 * @param lectures			Conjunt de sessions del grup.
	 * @return True si s'ha pogut afegir el grup. False en cas contrari.
	 */
	public boolean addGroup(String inCode, Integer inNPeople, String inParentGroupCode, String subjectCode,
			Boolean inNeedsComputers, String inType, String inDayPeriod, ArrayList<String> lectures) {
		if (!groups.containsKey(subjectCode + "-" + inCode + "-" + inType)) {

			Group g = new Group(inCode, inNPeople, inParentGroupCode, subjectCode, inNeedsComputers, 
					Group.Type.valueOf(inType), Group.DayPeriod.valueOf(inDayPeriod), lectures);	

			

			groups.put(g.toString(), g);
			DayPeriodRestriction dpr = new DayPeriodRestriction(6, g.getDayPeriod());
			if (!unaryRestrictions.containsKey(g.toString())) {
				unaryRestrictions.put(g.toString(), new HashMap<String, UnaryRestriction>());
			}
			unaryRestrictions.get(g.toString()).put(dpr.toString(), dpr);
			naryRestrictions.put(g.toString(), groupRestr);
			subjects.get(subjectCode).addGroup(g.toString());
			return true;
		}
		
		return false;
	}
	
	/**
	 * Indica si una restriccio esta activada o no.
	 * @param g	Grup on mirar la restriccio.
	 * @param r	Nom de la restriccio.
	 * @return True si la restriccio del grup {@link Environment#g} amb nom {@link Environment#r} esta activada. False en cas contrari.
	 */
	public Boolean restrictionIsEnabled(String g, String r) {
		return unaryRestrictions.get(g).get(r).isEnabled();
	}
	
	/**
	 * Activa o desactiva una restriccio.
	 * @param g		Grup on mirar la restriccio.
	 * @param r		Nom de la restriccio.
	 * @param state	Boolea que indica l'estat d'una restriccio.
	 * @return True si s'ha pogut activar/desactivar la restriccio. False en cas contrari.
	 */
	public Boolean setRestrictionEnabled(String g, String r, Boolean state) {
		if (state) {
			unaryRestrictions.get(g).get(r).enable();
		}
		else {
			unaryRestrictions.get(g).get(r).disable();
		}
		return true;
	}
	
	/**
	 * Elimina un grup.
	 * @param name	Nom del grup a eliminar.
	 * @return True si s'ha pogut eliminar el grup amb nom {@link Environment#name}. False en cas contrari.
	 */
	public boolean removeGroup(String name) {
		subjects.get(groups.get(name).getSubject()).removeGroup(name);
		for(String lecture : groups.get(name).getLectures()) {
			removeLecture(lecture);
		}
		groups.remove(name);
		return true;
	}
	
	/**
	 * Nom de l'assignatura a la qual pertany un grup.
	 * @param g	Nom del grup.
	 * @return El nom de l'assignatura a la qual pertany el grup {@link Environment#g}.
	 */
	public String getGroupSubject(String g) {
		return groups.get(g).getSubject();
	}
	
	/**
	 * Codi d'un grup.
	 * @param g	Nom del grup.
	 * @return El nom del codi del grup {@link Environment#g}.
	 */
	public String getGroupCode(String g) {
		return groups.get(g).getCode();
	}
	
	/**
	 * Numero de persones d'un grup.
	 * @param g	Nom del grup.
	 * @return La capacitat del grup {@link Environment#g}.
	 */
	public Integer getGroupNumOfPeople (String g) {
		return groups.get(g).getNumOfPeople();
	}
	
	/**
	 * Tipus de classe d'un grup.
	 * @param g	Nom del grup.
	 * @return El tipus de classe del grup {@link Environment#g}.
	 */
	public Group.Type getGroupType(String g) {
		return groups.get(g).getType();
	}
	
	/**
	 * Periode del dia on fa classe un grup.
	 * @param g	Nom del grup.
	 * @return El periode del dia on fa classe el grup {@link Environment#g}.
	 */
	public Group.DayPeriod getGroupDayPeriod(String g) {
		return groups.get(g).getDayPeriod();
	}
	
	/**
	 * Codi del grup pare d'un grup.
	 * @param g	Nom del grup.
	 * @return El codi del grup pare del grup {@link Environment#g};
	 */
	public String getGroupParentGroupCode(String g) {
		return groups.get(g).getParentGroupCode();
	}
	
	/**
	 * Informa sobre si un grup necessita ordinadors a l'aula o no.
	 * @param g	Nom del grup.
	 * @return True si el grup {@link Environment#g} necessita ordinadors a la classe. False en cas contrari.
	 */
	public Boolean groupNeedsComputers(String g) {
		return groups.get(g).needsComputers();
	}
	
	/**
	 * Conjunt de sessions d'un grup.
	 * @param g	Nom del grup.
	 * @return ArrayList amb els noms de les sessions del grup {@link Environment#g}.
	 */
	public ArrayList<String> getGroupLectures(String g) {
		return groups.get(g).getLectures();
	}
	
	/**
	 * Conjunt de grups de l'escenari.
	 * @return Un Set amb tots els grups que te l'escenari.
	 */
	public Set<String> getAllGroups() {
		return groups.keySet();
	}
	
	/////////////// SUBJECTS //////////////////////////////
	
	/**
	 * Afageix una assignatura.
	 * @param inCode	Codi de la nova assignatura.
	 * @param inName	Nom de la nova assignatura.
	 * @param inLevel	Nivell de la nova assignatura.
	 * @param groups	Conjunt de grups de la nova assignatura.
	 * @param inCoreqs	Conjunt de corequisits de la nova assignatura.
	 * @return True si s'ha pogut afegir l'assignatura. False en cas contrari.
	 */
	public boolean addSubject(String inCode, String inName, String inLevel, ArrayList<String> groups, ArrayList<String> inCoreqs) {
		if (!subjects.containsKey(inCode)) {
			Subject newSub = new Subject(inCode, inName, inLevel, groups, inCoreqs);
			
			subjects.put(inCode, newSub);
			return true;
		}
		return false;
	}
	
	/**
	 * Elimina una assignatura.
	 * @param name	Nom de l'assignatura a eliminar.
	 * @return True si s'ha pogut eliminar l'assignatura. False en cas contrari.
	 */
	public boolean removeSubject(String name) {
		// Pre: el Subject amb nom "name" existeix
		for (String group : (ArrayList<String>) subjects.get(name).getGroups().clone()) {
			removeGroup(group);
		}
		subjects.remove(name);
		return true;
	}
	
	/**
	 * Codi d'una assignatura.
	 * @param s	Nom de l'assignatura.
	 * @return El codi de l'assignatura {@link Environment#s}.
	 */
	public String getSubjectCode(String s) {
		return subjects.get(s).getCode();
	}
	
	/**
	 * Nom d'una assignatura.
	 * @param s	Nom de l'assignatura.
	 * @return El nom de l'assignatura {@link Environment#s}.
	 */
	public String getSubjectName(String s) {
		return subjects.get(s).getName();
	}
	
	/**
	 * Nivell de'una assignatura.
	 * @param s	Nom de l'assignatura.
	 * @return El nivell de l'assignatura {@link Environment#s}.
	 */
	public String getSubjectLevel(String s) {
		return subjects.get(s).getLevel();
	}
	
	/**
	 * Conjunt de corequisits d'una assignatura.
	 * @param s	Nom de l'assignatura.
	 * @return ArrayList amb el nom del conjunt de corequisits de l'assignatura {@link Environment#s}.
	 */
	public ArrayList<String> getSubjectCoreqs(String s) {
		return subjects.get(s).getCoreqs();
	}
	
	/**
	 * Conjunt de grups d'una assignatura.
	 * @param s	Nom de l'assignatura.
	 * @return ArrayList amb el conjunt de grups de l'assignatura {@link Environment#s}.
	 */
	public ArrayList<String> getSubjectGroups(String s) {
		return subjects.get(s).getGroups();
	}
	
	/**
	 * Conjunt d'assignatures de l'escenari.
	 * @return El conjunt d'assignatures de l'escenari.
	 */
	public Set<String> getAllSubjects() {
		return subjects.keySet();
	}
	
	/////////////// LECTURE //////////////////////////////
	
	/**
	 * Afageix una sessio.
	 * @param codi		Codi de la sessió.
	 * @param group		Nom del grup al qual pertany la sessio.
	 * @param duration	Duracio de la sessio.
	 * @return True si s'ha pogut afegir la sessio. False en cas contrari.
	 */
	public boolean addLecture(Integer codi, String group, Integer duration) {
		if (!lectures.containsKey(group + "-" + codi)) {
			Lecture L = new Lecture(codi, group, duration);
			
			lectures.put(group + "-" + codi, L);
			groups.get(group).addLectures(codi, duration);
			return true;
		}
		
		return false;
	}
	
	/**
	 * Elimina una sessio.
	 * @param name	Nom de la sessio.
	 * @return True si s'ha pogut eliminar la sessio {@link Environment#name}.
	 */
	public boolean removeLecture(String name) {
		lectures.remove(name);

		return true;
	}
	
	/**
	 * Duracio d'una sessio.
	 * @param l	Nom de la sessio.
	 * @return La duracio de la sessio {@link Environment#l}.
	 */
	public Integer getLectureDuration(String l) {
		return lectures.get(l).getDuration();
	}
	
	/**
	 * Grup al qual pertany una sessio.
	 * @param l	Nom de la sessio.
	 * @return El grup al qual pertany la sessio {@link Environment#l}.
	 */
	public String getLectureGroup(String l) {
		return lectures.get(l).getGroup();
	}
	
	/**
	 * Conjunt de sessions de l'escenari.
	 * @return El Set format per totes les sessions de l'escenari.
	 */
	public Set<String> getAllLectures() {
		return lectures.keySet();
	}
	
	/////////////// ROOM //////////////////////////////
	
	/**
	 * Afageix una aula.
	 * @param inCode			Codi de l'aula.
	 * @param inCapacity		Capacitat de l'aula.
	 * @param inHasComputers	Boolea que indica si una aula te ordinadors o no.
	 * @return True si s'ha pogut afegir l'aula. False en cas contrari.
	 */
	public boolean addRoom(String inCode, Integer inCapacity, Boolean inHasComputers) {
		if (!rooms.containsKey(inCode)) {
			Room r = new Room(inCode, inCapacity, inHasComputers);
			rooms.put(inCode, r);
			
			return true;
		}

		return false;
	}
	
	/**
	 * Elimina una aula.
	 * @param code	Nom de l'aula.
	 * @return True si s'ha pogut eliminar l'aula. False en cas contrari.
	 */
	public boolean removeRoom(String code) {
		rooms.remove(code);
		return true;
	}
	
	/**
	 * Codi d'una aula.
	 * @param r	Nom de l'aula.
	 * @return El codi de l'aula {@link Environment#r}.
	 */
	public String getRoomCode(String r) {
		return rooms.get(r).getCode();
	}
	
	/**
	 * Capacitat d'una aula.
	 * @param r	Nom de l'aula.
	 * @return La capacitat de l'aula {@link Environment#r}.
	 */
	public Integer getRoomCapacity(String r) {
		return rooms.get(r).getCapacity();
	}
	
	/**
	 * Informa si l'aula te ordinadors.
	 * @param r	Nom de l'aula.
	 * @return True si l'aula {@link Environment#r} te ordinadors. False en cas contrari.
	 */
	public Boolean roomHasComputers(String r) {
		return rooms.get(r).hasComputers();
	}
	
	/**
	 * Conjunt d'aules de l'escenari.
	 * @return El Set format pel nom de les aules de l'escenari.
	 */
	public Set<String> getAllRooms() {
		return rooms.keySet();
	}
	
}
