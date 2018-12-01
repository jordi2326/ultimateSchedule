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
	
	public String[][] getScheduleMatrix(){
		String[][] table = new String[12][5];
		for (String[] row: table)
		    Arrays.fill(row, "");
		String[][] data = ctrlDomain.getScheduleMatrix();
		if (data.length > 0) {
            for (int i = 0; i < data[0].length; i++) {
                for (int j = 0; j < data.length; j++) {
                	table[i][j] = data[j][i];
                }
            }
		}
		return table;
	}
	
	public ArrayList<String> getAllRooms() {
		return ctrlDomain.getRoomNamesList();
	}
	
	public boolean importSchedule(String filename)
		throws FileNotFoundException, ParseException {
		return ctrlDomain.importSchedule(filename);
	}

}
