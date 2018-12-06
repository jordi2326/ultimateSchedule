package presentation;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

public class MoveLectureView {
	
	Object[] message;
	
	public MoveLectureView(String group, int duration, int day, int hour, String room) {
		initializeComponents(group, duration, day, hour, room);
	}
	
	public void makeVisible() {
		int result = JOptionPane.showOptionDialog(null, message, "Move Lecture", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
		if (result == JOptionPane.OK_OPTION) {
	         System.out.println("x value: " + ((JComboBox<String>) message[4]).getSelectedItem());
	         System.out.println("y value: " + ((JComboBox<String>) message[6]).getSelectedItem());
	         System.out.println("z value: " + ((JComboBox<String>) message[8]).getSelectedItem());
	    }
	}
	
	private void initializeComponents(String group, int duration, int day, int hour, String room) {
		JComboBox<String> days = new JComboBox<String>(ScheduleTable.colNames);
		days.setSelectedIndex(day);
		JComboBox<String> hours = new JComboBox<String>(ScheduleTable.startTimes);
		hours.setSelectedIndex(hour);
		//JComboBox<String> rooms = CtrlDomain.getInstance().getRooms()
		JComboBox<String> rooms = new JComboBox<String>(new String[] {"A6002", "A5001"});
		//rooms.setSelectedItem(room);
		message = new Object[]{group, "Duration: " + duration +" hour(s)", " ",
		    "Day:", days,
		    "Start Time:", hours,
		    "Room:", rooms
		};
		
		JOptionPane optionPane = new JOptionPane(message);
	}
}
