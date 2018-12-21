package presentation;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import domain.classes.Group;
import domain.classes.Group.DayPeriod;
import domain.classes.Group.Type;

public class NewGroupView extends JDialog {
	
	public NewGroupView(Frame parent, String subjectCode) {
		super(parent, true);
		
		CtrlPresentation ctrlPresentation = CtrlPresentation.getInstance();
		
		setTitle("New " + subjectCode + " Group");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(400, 200);
		setMinimumSize(new Dimension(400, 200));
		
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
	    GridBagLayout gridLayout = new GridBagLayout();
	    JPanel contentPanel = new JPanel();
	    JPanel gridPanel = new JPanel();
	    gridPanel.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(5, 5, 5, 5), BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(subjectCode + " Group:"), new EmptyBorder(10, 10, 10, 10))));
	    gridPanel.setLayout(gridLayout);
	    contentPanel.setLayout(new BorderLayout(0, 0));
	    setContentPane(contentPanel);
	    contentPanel.add(gridPanel, BorderLayout.CENTER);
	    constraints.anchor = GridBagConstraints.PAGE_START;
	    constraints.ipady = 5;
	    constraints.ipadx = 15;
  
	    constraints.gridy = 0;
	    gridPanel.add(new JLabel("Code:", SwingConstants.RIGHT), constraints);
	    JTextField tfCode = new JTextField();
	    gridPanel.add(tfCode, constraints);
	    
	    constraints.gridy = 0;
	    gridPanel.add(new JLabel("Parent Group Code:  ", SwingConstants.RIGHT), constraints);
	    JTextField tfParentGroupCode = new JTextField();
	    gridPanel.add(tfParentGroupCode, constraints);
	    
	    constraints.gridy = 1;
	    gridPanel.add(new JLabel("Num. People:  ", SwingConstants.RIGHT), constraints);
	    JSpinner spNPeople = new JSpinner(new SpinnerNumberModel(0,0,Integer.MAX_VALUE,1));
	    JSpinner.DefaultEditor spinnerEditor = (DefaultEditor) spNPeople.getEditor();
	    spinnerEditor.getTextField().setColumns(8);
	    spinnerEditor.getTextField().setHorizontalAlignment(JTextField.LEFT);
	    gridPanel.add(spNPeople, constraints);

	    constraints.gridy = 1;
	    gridPanel.add(new JLabel("Needs Computers:  ", SwingConstants.RIGHT), constraints);
	    JCheckBox cbNeedsComputers = new JCheckBox("");
	    cbNeedsComputers.setSelected(false);
	    gridPanel.add(cbNeedsComputers, constraints);
	    
	    constraints.gridy = 2;
	    gridPanel.add(new JLabel("Class Type:  ", SwingConstants.RIGHT), constraints);
	    JComboBox cbbType = new JComboBox(Group.Type.values());
	    cbbType.setSelectedIndex(1);
	    gridPanel.add(cbbType, constraints);
	    
	    constraints.gridy = 2;
	    gridPanel.add(new JLabel("Day Period:  ", SwingConstants.RIGHT), constraints);
	    JComboBox cbbDayPeriod = new JComboBox(Group.DayPeriod.values());
	    cbbDayPeriod.setSelectedIndex(2);
	    gridPanel.add(cbbDayPeriod, constraints);
	    
	    constraints.gridy = 4;
	    gridPanel.add(new JLabel("Weekly Lectures:  ", SwingConstants.RIGHT), constraints);
	    JButton addLec = new JButton(" + ");
	    JButton rmvLec = new JButton(" - ");
	    rmvLec.setVisible(false);
	    JPanel panelLectures = new JPanel();
	    ArrayList<Integer> inLecs = new ArrayList<Integer>();
	    panelLectures.setLayout(new BoxLayout(panelLectures, BoxLayout.Y_AXIS));
	    panelLectures.setBorder(new EmptyBorder(2, 0, 0, 0));
	    addLec.setMargin(new Insets(0, 0, 0, 0));
	    addLec.setBorderPainted(true);
	    addLec.setFocusPainted(false);
	    addLec.setContentAreaFilled(false);
	    addLec.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Integer selected = (Integer) JOptionPane.showInputDialog(NewGroupView.this, 
						"",
						"Lecture Duration (hours)",
				        JOptionPane.QUESTION_MESSAGE, 
				        null, 
				        new Integer[] {1,2,3,4,5,6}, 
						null);
				if(selected != null) {
					inLecs.add(selected);
					panelLectures.add(new JLabel(selected.toString()+" hour(s)"), panelLectures.getComponentCount()-1);
					panelLectures.revalidate();
					panelLectures.repaint();
					rmvLec.setVisible(true);
					pack();
				}
				
			}
		});
	    rmvLec.setMargin(new Insets(0, 0, 0, 0));
	    rmvLec.setBorderPainted(true);
	    rmvLec.setFocusPainted(false);
	    rmvLec.setContentAreaFilled(false);
	    rmvLec.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				inLecs.remove(inLecs.size()-1);
				panelLectures.remove(panelLectures.getComponentCount()-2);
				panelLectures.revalidate();
				panelLectures.repaint();
				if(inLecs.isEmpty()) rmvLec.setVisible(false);
				pack();
				
			}
		});
	    JPanel btnsCoreq = new JPanel();
	    btnsCoreq.setLayout(new BoxLayout(btnsCoreq, BoxLayout.X_AXIS));
	    btnsCoreq.setAlignmentX(JScrollPane.LEFT_ALIGNMENT);
	    btnsCoreq.add(addLec);
	    btnsCoreq.add(rmvLec);
	    panelLectures.add(btnsCoreq);
	    gridPanel.add(panelLectures, constraints);
	    
	    JButton btnSave = new JButton("Add");
	    JButton btnCancel = new JButton("Cancel");
	    
	    JPanel bottomPanel = new JPanel();
	    bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
	    bottomPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
	    bottomPanel.add(btnSave);
	    bottomPanel.add(btnCancel);
	    
	    btnSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String inCode = tfCode.getText();
				
				try {
					spNPeople.commitEdit();
				} catch (ParseException e1) {}
				Integer inNPeople = (Integer) spNPeople.getValue();
				
				String inParentGroupCode = tfParentGroupCode.getText(); 
				Boolean inNeedsComputers = cbNeedsComputers.isSelected();
				String inType = cbbType.getSelectedItem().toString();
				String inDayPeriod  = cbbDayPeriod.getSelectedItem().toString();
				
				//ArrayList<String> lectures;
				
				if(ctrlPresentation.addGroup(inCode, inNPeople, inParentGroupCode, subjectCode, inNeedsComputers, inType, inDayPeriod, new ArrayList<String>())) {
					for(Integer d : inLecs) {
						//ctrlPresentation.addLecture(group idd != codee, d);
					}
				}else
					JOptionPane.showMessageDialog(NewGroupView.this, "Group data is not valid", "", JOptionPane.WARNING_MESSAGE);
			}
		});

	    btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
			}
		});
	    
	    contentPanel.add(bottomPanel, BorderLayout.SOUTH);
	    
	    pack();
	    setLocationRelativeTo(null);
	}
}
