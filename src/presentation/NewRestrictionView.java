package presentation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

public class NewRestrictionView {
	
	Object[] message;
	int duration;
	int iniDay;
	int iniHour;
	String iniRoom;
	CtrlPresentation ctrlPresentation;
	
	public NewRestrictionView() {
		ctrlPresentation = CtrlPresentation.getInstance();
		
		JComboBox<String> days = new JComboBox<String>(ScheduleTable.colNames);
		days.setSelectedIndex(0);
		JComboBox<String> hours = new JComboBox<String>(ScheduleTable.rowNames);
		hours.setSelectedIndex(0);
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
		int result = JOptionPane.showOptionDialog(null, message, "New Restriction", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
		if (result == JOptionPane.OK_OPTION) {
			ctrlPresentation.addRestriction((String) ((JComboBox<String>) message[3]).getSelectedItem(), ((JComboBox<String>) message[5]).getSelectedIndex(), ((JComboBox<String>) message[7]).getSelectedIndex());
	    }
	}
}
