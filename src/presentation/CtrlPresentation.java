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
import domain.controllers.CtrlDomain;

public class CtrlPresentation {
	
	private static CtrlPresentation instance;
	
	private CtrlDomain ctrlDomain;
	private MainView mainView;
	ProgressView progressV;
	private SwingWorker<Boolean, Void> task;
	Timer tmr;
	
	private CtrlPresentation() {
		ctrlDomain = CtrlDomain.getInstance();
		mainView = new MainView(this);
		progressV = new ProgressView(mainView, this);
	}
	
	public static CtrlPresentation getInstance() {
		if (instance == null)
			instance = new CtrlPresentation();
		return instance;
	}
	
	public void initialize() {
		mainView.setVisible(true);
	}
	
	public void switchToSubjectInfoView(String name) {
		SubjectInfoView subjectInfoView = new SubjectInfoView(null, this, name);
		subjectInfoView.setVisible(true);
	}
	
	public void switchToGroupInfoView(String name) {
		GroupInfoView groupInfoView = new GroupInfoView(null, this, name);
		groupInfoView.setVisible(true);
	}
	
	public void switchToRoomInfoView(String name) {
		RoomInfoView roomInfoView = new RoomInfoView(null, this, name);
		roomInfoView.setVisible(true);
	}
	
	public ArrayList<String[]>[][] getScheduleMatrix(){
		return ctrlDomain.getScheduleMatrix();
	}
	
	public boolean importEnvironment(String filename)
		throws ParseException, IOException {
		return ctrlDomain.importEnvironment(filename);
	}
	
	public boolean importSchedule(String filename)
		throws FileNotFoundException, ParseException {
		return ctrlDomain.importSchedule(filename);
	}
	
	// Getters de totes les coses d'environment
	public Set<String> getRoomNames() {
		return ctrlDomain.getRoomNames();
	}
	
	public String[] getRoomInfo(String room) {
		return ctrlDomain.getRoomInfo(room);
	}
	
	public Set<String> getGroupNames() {
		return ctrlDomain.getGroupNames();
	}
	
	public Set<String> getGroupsNamesFromSuject(String s) {
		return ctrlDomain.getGroupsNamesFromSuject(s);
	}
	
	public String[] getGroupInfo(String group) {
		return ctrlDomain.getGroupInfo(group);
	}
	
	public Set<String> getSubjectNames() {
		return ctrlDomain.getSubjectNames();
	}
	
	public Object[] getSubjectInfo(String sub) {
		return ctrlDomain.getSubjectInfo(sub);
	}
	
	public Set<String> getRestrictionNames() {
		return ctrlDomain.getRestrictionNames();
	}
	
	public String[] getRestrictionInfo(String res) {
		return ctrlDomain.getRestrictionInfo(res);
	}
	
	// Com vols que es vegin a la pantalla
	public Set<String> getRestrictionNamesView() {
		return ctrlDomain.getRestrictionNamesView();
	}
	
	public void generateSchedule() {
        task = new SwingWorker<Boolean, Void>(){
        	@Override
			protected Boolean doInBackground() throws Exception {
        		return ctrlDomain.generateSchedule();
			}
        	@Override
            public void done() {
        		tmr.cancel();
        		Toolkit.getDefaultToolkit().beep();
        		progressV.setVisible(false);
        		//progressV.dispose();
				try {
					if(get()) {
						mainView.scheduleLoaded();
						//JOptionPane.showMessageDialog(mainView, "Schedule Generated!", null, JOptionPane.PLAIN_MESSAGE);
					}else {
						JOptionPane.showMessageDialog(mainView, "No Valid Schedule Found", null, JOptionPane.WARNING_MESSAGE);
					}
				} catch (CancellationException | InterruptedException e) { //do nothing
				} catch (HeadlessException | ExecutionException e) {
					JOptionPane.showMessageDialog(mainView, "Error when generating Schedule", null, JOptionPane.ERROR_MESSAGE);
				}
            }
        };
        task.execute();
        tmr = new Timer();
        tmr.schedule(new TimerTask() {
        	@Override
        	public void run() {
        		progressV.setVisible(true);
        		}
        	},
        	500
        );
	}

	public void stopTask() {
		task.cancel(true);
	}
	
	/** Elimina un grup en un dia i aula determinats.
	*   @param duration Duració del grup.
	*	@param room		Aula on eliminarem el grup.
	*	@param day		Dia on eliminarem el grup.
	*	@param hour		Hora on eliminarem el grup.
	*	@return Si existeix l'aula, eliminem el grup en qï¿½estiï¿½ i retornem true. Fals en cas contrari.
	*/
	public boolean removeLecture(int duration, String room, int day, int hour) {
		return ctrlDomain.removeLecture(duration, room, day, hour);
	}
}
