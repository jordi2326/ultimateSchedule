package presentation;

import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import org.json.simple.parser.ParseException;

import domain.classes.Environment;
import domain.controllers.CtrlDomain;

/**
 * @author Carlos Bergillos Varela
*/
public class CtrlPresentation {
	
	/**
	 * 
	 */
	private static CtrlPresentation instance;
	
	/**
	 * 
	 */
	private CtrlDomain ctrlDomain;
	/**
	 * 
	 */
	private MainView mainView;
	
	/**
	 * 
	 */
	private SwingWorker<Boolean, Void> task;
	/**
	 * 
	 */
	/**
	 * 
	 */
	Timer tmr1, tmr2;
	
	/**
	 * 
	 */
	private CtrlPresentation() {
		ctrlDomain = CtrlDomain.getInstance();
	}
	
	/**
	 * @return
	 */
	public static CtrlPresentation getInstance() {
		if (instance == null)
			instance = new CtrlPresentation();
		return instance;
	}
	
	/**
	 * 
	 */
	public void initialize() {
		mainView = new MainView(this);
		mainView.setVisible(true);
	}
	
	/**
	 * @param name
	 */
	public void switchToSubjectInfoView(String name) {
		SubjectInfoView subjectInfoView = new SubjectInfoView(mainView, this, name);
		subjectInfoView.setVisible(true);
	}
	
	/**
	 * @param name
	 */
	public void switchToGroupInfoView(String name) {
		GroupInfoView groupInfoView = new GroupInfoView(mainView, name);
		groupInfoView.setVisible(true);
	}
	
	/**
	 * @param name
	 */
	public void switchToRoomInfoView(String name) {
		RoomInfoView roomInfoView = new RoomInfoView(mainView, this, name);
		roomInfoView.setVisible(true);
	}
	
	/**
	 * 
	 */
	public void switchToNewSubjectView() {
		NewSubjectView newSubjectView = new NewSubjectView(mainView);
		newSubjectView.setVisible(true);
	}
	
	/**
	 * @param subjectCode
	 */
	public void switchToNewGroupView(String subjectCode) {
		NewGroupView newGroupView = new NewGroupView(mainView, subjectCode);
		newGroupView.setVisible(true);
	}
	
	/**
	 * 
	 */
	public void switchToNewRoomView() {
		NewRoomView newRoomView = new NewRoomView(mainView);
		newRoomView.setVisible(true);
	}
	
	/**
	 * 
	 */
	public void switchToRestrictionsView() {
		RestrictionsView restrictionsView = new RestrictionsView(mainView);
		restrictionsView.setVisible(true);
	}
	
	/**
	 * 
	 */
	public void switchToNewRestrictionsView() {
		NewRestrictionView newRestrictionsView = new NewRestrictionView();
		newRestrictionsView.makeVisible();
	}
	
	/**
	 * @return
	 */
	public ArrayList<String[]>[][] getScheduleMatrix(){
		return ctrlDomain.getScheduleMatrix();
	}
	
	/**
	 * @param filename
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public boolean importEnvironment(String filename)
		throws ParseException, IOException {
		return ctrlDomain.importEnvironment(filename, true);
	}
	
	/**
	 * @param filename
	 * @return
	 * @throws ParseException
	 * @throws IOException 
	 */
	public boolean importSchedule(String filename) {
		try {
			return ctrlDomain.importSchedule(filename, true);
		} catch (ParseException | IOException e) {
			return false;
		}
	}
	
	/**
	 * @param filename
	 * @return
	 * @throws IOException
	 * @throws ParseException 
	 */
	public boolean exportSchedule(String filename) throws IOException, ParseException{
			return ctrlDomain.exportSchedule(filename, true);
		}
	
	public boolean exportEnvironment(String filename) throws IOException, ParseException{
		return ctrlDomain.exportEnvironment(filename, true);
	}
	
	// Getters de totes les coses d'environment
	/**
	 * @return
	 */
	public Set<String> getRoomNames() {
		return ctrlDomain.getRoomNames();
	}
	
	/**
	 * @param room
	 * @return
	 */
	public String[] getRoomInfo(String room) {
		return ctrlDomain.getRoomInfo(room);
	}
	
	/**
	 * @return
	 */
	public Set<String> getGroupNames() {
		return ctrlDomain.getGroupNames();
	}
	
	/**
	 * @param s
	 * @return
	 */
	public Set<String> getGroupsNamesFromSuject(String s) {
		return ctrlDomain.getGroupsNamesFromSuject(s);
	}
	
	/**
	 * @param group
	 * @return
	 */
	public String[] getGroupInfo(String group) {
		return ctrlDomain.getGroupInfo(group);
	}
	
	/**
	 * @return
	 */
	public Set<String> getSubjectNames() {
		return ctrlDomain.getSubjectNames();
	}
	
	/**
	 * @param sub
	 * @return
	 */
	public Object[] getSubjectInfo(String sub) {
		return ctrlDomain.getSubjectInfo(sub);
	}
	
	/**
	 * @return
	 */
	public Set<String> getRestrictionNames() {
		return ctrlDomain.getRestrictionNames();
	}
	
	/**
	 * @param res
	 * @return
	 */
	public ArrayList<Object[]> getNegotiableRestrictions() {
		return ctrlDomain.getNegotiableRestrictions();
	}
	
	// Com vols que es vegin a la pantalla
	/**
	 * @return
	 */
	public Set<String> getRestrictionNamesView() {
		return ctrlDomain.getRestrictionNamesView();
	}
	
	/**
	 * 
	 */
	public void generateSchedule() {
		ProgressView progressV  = new ProgressView(mainView);
        task = new SwingWorker<Boolean, Void>(){
        	@Override
			protected Boolean doInBackground() throws Exception {
        		return ctrlDomain.generateSchedule();
			}
        	@Override
            public void done() {
        		tmr1.cancel();
        		tmr2.cancel();
        		Toolkit.getDefaultToolkit().beep();
        		progressV.setVisible(false);
        		//progressV.dispose();
        		if(!isCancelled()) {
        			try {
    					if(get()) {
    						mainView.reloadSchedule();
    						//JOptionPane.showMessageDialog(mainView, "Schedule Generated!", null, JOptionPane.PLAIN_MESSAGE);
    					}else {
    						JOptionPane.showMessageDialog(mainView, "No Valid Schedule Found", null, JOptionPane.WARNING_MESSAGE);
    					}
    				} catch (CancellationException | InterruptedException e) { //do nothing
    				} catch (HeadlessException | ExecutionException e) {
    					JOptionPane.showMessageDialog(mainView, "Error when generating Schedule", null, JOptionPane.ERROR_MESSAGE);
    				}
        		}
            }
        };
        task.execute();
        tmr1 = new Timer();
        tmr1.schedule(new TimerTask() {
        	@Override
        	public void run() {
        		progressV.setVisible(true);
        		}
        	},
        	500
        );
        tmr2 = new Timer();
        tmr2.schedule(new TimerTask() {
        	@Override
        	public void run() {
        		stopTask();
        		JOptionPane.showMessageDialog(mainView, "No Valid Schedule Found", null, JOptionPane.WARNING_MESSAGE);
        		}
        	},
        	30000 //30 seconds
        );
	}

	/**
	 * 
	 */
	public void stopTask() {
		task.cancel(true);
	}
	
	/** Elimina un grup en un dia i aula determinats.
	*   @param duration Duraci� del grup.
	*	@param room		Aula on eliminarem el grup.
	*	@param day		Dia on eliminarem el grup.
	*	@param hour		Hora on eliminarem el grup.
	*	@return Si existeix l'aula, eliminem el grup en q�esti� i retornem true. Fals en cas contrari.
	*/
	public boolean removeLecture(int duration, String room, int day, int hour) {
		return ctrlDomain.removeLecture(duration, room, day, hour);
	}
	
	/** Mou un grup de dia, aula i hora determinats
	*   @param duration Duraci� del grup.
	*	@param iniDay		Dia on est� actualment el grup.
	*	@param fiDay		Dia on anir� el grup si es pot.
	*	@param iniHour		Hora on est� actualment el grup.
	*	@param fiHour		Hora on anir� el grup si es pot.
	*	@param iniRoom		Aula on est� actualment el grup.
	*	@param fiRoom		Aula on anir� el grup si es pot.
	*	@return True si s'ha pogut moure el grup. Fals en cas contrari.
	*/
	public boolean moveLecture(int duration, int iniDay, int fiDay, int iniHour, int fiHour, String iniRoom, String fiRoom) {
		return ctrlDomain.moveLecture(duration, iniDay, fiDay, iniHour, fiHour, iniRoom, fiRoom);
	}

	/**
	 * @param inCode
	 * @param inCapacity
	 * @param inHasComputers
	 * @return
	 */
	public boolean addRoom(String inCode, Integer inCapacity, Boolean inHasComputers) {
		return ctrlDomain.addRoom(inCode, inCapacity, inHasComputers);
	}

	/**
	 * @param code
	 */
	public boolean removeRoom(String code) {
		return ctrlDomain.removeRoom(code);
	}
	
	/**
	 * @param codi
	 * @param group
	 * @param duration
	 * @return
	 */
	public boolean addLecture(Integer codi, String group, Integer duration) {
		return ctrlDomain.addLecture(codi, group, duration);
	}
	
	/**
	 * @param name
	 * @return
	 */
	public boolean removeLecture(String name) {
		return ctrlDomain.removeLecture(name);
	}

	/**
	 * @param inCode
	 * @param inName
	 * @param inLevel
	 * @param arrayList
	 * @param inCoreqs
	 * @return
	 */
	public boolean addSubject(String inCode, String inName, String inLevel,  ArrayList<String> inCoreqs) {
		return ctrlDomain.addSubject(inCode, inName, inLevel, inCoreqs);
	}
	
	/**
	 * @param name
	 * @return
	 */
	public boolean removeSubject(String name) {
		return ctrlDomain.removeSubject(name);
	}
	

	/**
	 * @param inCode
	 * @param inNPeople
	 * @param inParentGroupCode
	 * @param subjectCode
	 * @param inNeedsComputers
	 * @param inType
	 * @param inDayPeriod
	 * @param arrayList
	 * @return
	 */
	public boolean addGroup(String inCode, Integer inNPeople, String inParentGroupCode, String subjectCode,
			Boolean inNeedsComputers, String inType, String inDayPeriod, ArrayList<String> arrayList) {
		return ctrlDomain.addGroup(inCode, inNPeople, inParentGroupCode, subjectCode,
				inNeedsComputers, inType, inDayPeriod, arrayList);
	}
	
	/**
	 * @param name
	 * @return
	 */
	public boolean removeGroup(String name) {
		return ctrlDomain.removeGroup(name);
	}
	
	public boolean addRestriction(String group, Integer day, Integer hour) {
		return ctrlDomain.addRestriction(group, day, hour);
	}
	
	public boolean removeRestriction(String group, String name) {
		return ctrlDomain.removeRestriction(group, name);
	}
	
	/**
	 * @param name
	 */
	public void subjectAdded(String name) {
		mainView.subjectAdded(name);
	}
	
	/**public void subjectRemoved(String name) {
		mainView.subjectRemoved(name);
	}**/
	
	public void groupAdded(String subjectCode, String name) {
		mainView.groupAdded(subjectCode, name);
	}
	
	/**public void groupRemoved(String name) {
		mainView.groupRemoved(name);
	}**/
	
	public void roomAdded(String name) {
		mainView.roomAdded(name);
	}
	
	/**public void roomRemoved(String name) {
		mainView.roomRemoved(name);
	}**/
	
	public Boolean setRestrictionEnabled(String g, String r, Boolean state) {
		return ctrlDomain.setRestrictionEnabled(g, r, state);
	}
	
	public String getEnvironmentName(){
		return ctrlDomain.getEnvironmentName();
	}
}
