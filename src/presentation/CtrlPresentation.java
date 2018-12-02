package presentation;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

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
	
	public ArrayList<String>[][] getScheduleMatrix(){
		return ctrlDomain.getScheduleMatrix();
	}
	
	public ArrayList<String> getAllRooms() {
		return ctrlDomain.getRoomNamesList();
	}
	
	public boolean importSchedule(String filename)
		throws FileNotFoundException, ParseException {
		return ctrlDomain.importSchedule(filename);
	}

}
