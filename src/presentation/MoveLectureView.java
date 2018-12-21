package presentation;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

public class MoveLectureView {
	
	Object[] message;
	int duration;
	int iniDay;
	int iniHour;
	String iniRoom;
	CtrlPresentation ctrlPresentation;
	
	public MoveLectureView(String group, int duration, int day, int hour, String room) {
		this.duration = duration;
		this.iniDay = day;
		this.iniHour = hour;
		this.iniRoom = room;
		ctrlPresentation = CtrlPresentation.getInstance();
		
		JComboBox<String> days = new JComboBox<String>(ScheduleTable.colNames);
		days.setSelectedIndex(day);
		JComboBox<String> hours = new JComboBox<String>(ScheduleTable.startTimes);
		hours.setSelectedIndex(hour);
		JComboBox<String> rooms = new JComboBox<String>((String[]) ctrlPresentation.getRoomNames().toArray(new String[0]));
		rooms.setSelectedItem(room);
		
		message = new Object[]{group,
			"Duration: " + duration +" hour(s)", " ",
		    "Day:", days,
		    "Start Time:", hours,
		    "Room:", rooms
		};
	}
	
	public void makeVisible() {
		int result = JOptionPane.showOptionDialog(null, message, "Move Lecture", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
		if (result == JOptionPane.OK_OPTION)
	         if(!ctrlPresentation.moveLecture(duration, iniDay, ((JComboBox<String>) message[4]).getSelectedIndex(), iniHour, ((JComboBox<String>) message[6]).getSelectedIndex(), iniRoom, (String) ((JComboBox<String>) message[8]).getSelectedItem()))
	        	 JOptionPane.showMessageDialog(null, "Movement not valid. Try something else.", "Error", JOptionPane.ERROR_MESSAGE);
	}
}
