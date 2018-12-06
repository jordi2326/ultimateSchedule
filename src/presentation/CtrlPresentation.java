package presentation;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import org.json.simple.parser.ParseException;
import domain.controllers.CtrlDomain;

public class CtrlPresentation {
	
	private static CtrlPresentation instance;
	
	private CtrlDomain ctrlDomain;
	private MainView mainView;
	
	private CtrlPresentation() {
		ctrlDomain = CtrlDomain.getInstance();
		mainView = new MainView(this);
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
	
	public boolean generateSchedule() {
		return ctrlDomain.generateSchedule();
	}
}
