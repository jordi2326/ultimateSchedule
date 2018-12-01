package presentation;

import java.util.ArrayList;
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
		String[][] data = ctrlDomain.getScheduleMatrix();
		if (data.length > 0) {
			String[][] transposed = new String[data[0].length][data.length];
            for (int i = 0; i < data[0].length; i++) {
                for (int j = 0; j < data.length; j++) {
                	transposed[i][j] = data[j][i];
                }
            }
            return transposed;
		}
		return new String[][]{};
	}
	public ArrayList<String> getAllRooms() {
		return ctrlDomain.getRoomNamesList();
	}

}
