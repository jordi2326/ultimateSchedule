package presentation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

public class SDHRestrictionView {
	
	Object[] message;
	int duration;
	int iniDay;
	int iniHour;
	String iniRoom;
	CtrlPresentation ctrlPresentation;
	
	public SDHRestrictionView(String group, int day, int hour) {
		this.duration = duration;
		this.iniDay = day;
		this.iniHour = hour;
		ctrlPresentation = CtrlPresentation.getInstance();
		
		JComboBox<String> days = new JComboBox<String>(ScheduleTable.colNames);
		days.setSelectedIndex(day);
		JComboBox<String> hours = new JComboBox<String>(ScheduleTable.rowNames);
		hours.setSelectedIndex(hour);
		JComboBox<String> subjects = new JComboBox<String>((String[]) ctrlPresentation.getSubjectNames().toArray(new String[0]));
		subjects.setSelectedItem(null);
		JComboBox<String> groups = new JComboBox<String>();
		subjects.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				DefaultComboBoxModel model = new DefaultComboBoxModel(ctrlPresentation.getGroupsNamesFromSuject((String) subjects.getSelectedItem()).toArray(new String[0]));
				groups.setModel(model);
			}
		});
		
		message = new Object[]{
			"Subject:", subjects,
			"Group:", groups,
		    "Day:", days,
		    "Time:", hours
		};
	}
	
	public void makeVisible() {
		int result = JOptionPane.showOptionDialog(null, message, "Move Lecture", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
		if (result == JOptionPane.OK_OPTION) {
	         /**System.out.println("x value: " + ((JComboBox<String>) message[4]).getSelectedItem());
	         System.out.println("y value: " + ((JComboBox<String>) message[6]).getSelectedItem());
	         System.out.println("z value: " + ((JComboBox<String>) message[8]).getSelectedItem());
	         ctrlPresentation.moveLecture(duration, iniDay, ((JComboBox<String>) message[4]).getSelectedIndex(), iniHour, ((JComboBox<String>) message[6]).getSelectedIndex(), iniRoom, (String) ((JComboBox<String>) message[4]).getSelectedItem());**/
	    }
	}
	
	private void initializeComponents(String group, int duration, int day, int hour, String room) {
		
		
		//JOptionPane optionPane = new JOptionPane(message);
	}
}
