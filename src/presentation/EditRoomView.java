package presentation;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class EditRoomView extends JDialog {
	
	public EditRoomView(Frame parent, CtrlPresentation ctrlPresentation, String name) {
		super(parent, true);
		
		setTitle("Information");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(400, 200);
		setMinimumSize(new Dimension(400, 200));
		setMaximumSize(new Dimension(800, 400));
		Object[] data = ctrlPresentation.getRoomInfo(name);
		
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
	    GridBagLayout gridLayout = new GridBagLayout();
	    JPanel contentPanel = new JPanel();
	    JPanel gridPanel = new JPanel();
	    gridPanel.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(5, 5, 5, 5), BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Subject:"), new EmptyBorder(10, 10, 10, 10))));
	    gridPanel.setLayout(gridLayout);
	    contentPanel.setLayout(new BorderLayout(0, 0));
	    setContentPane(contentPanel);
	    contentPanel.add(gridPanel, BorderLayout.CENTER);
	    constraints.anchor = GridBagConstraints.PAGE_START;
	    constraints.ipady = 5;
  
	    constraints.gridy = 0;
	    gridPanel.add(new JLabel("Code:  ", SwingConstants.RIGHT), constraints);
	    JTextField txtfield = new JTextField((String) data[0]);
	    txtfield.setFont(txtfield.getFont().deriveFont(Font.PLAIN));
	    gridPanel.add(txtfield, constraints);
	    
	    constraints.gridy = 1;
	    gridPanel.add(new JLabel("Capacity:  ", SwingConstants.RIGHT), constraints);
	    JSpinner spinner = new JSpinner(new SpinnerNumberModel(Integer.parseInt((String) data[1]),0,Integer.MAX_VALUE,1));
	    JSpinner.DefaultEditor spinnerEditor = (DefaultEditor) spinner.getEditor();
	    spinnerEditor.getTextField().setHorizontalAlignment(JTextField.LEFT);
	    gridPanel.add(spinner, constraints);

	    constraints.gridy = 2;
	    gridPanel.add(new JLabel("Has Computers:  ", SwingConstants.RIGHT), constraints);
	    JCheckBox chckbox = new JCheckBox("");
	    chckbox.setSelected(Boolean.parseBoolean((String) data[2]));
	    gridPanel.add(txtfield, constraints);
	    
	    JButton btnSave = new JButton("Save");
	    JButton btnCancel = new JButton("Cancel");
	    
	    JPanel bottomPanel = new JPanel();
	    bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
	    bottomPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
	    bottomPanel.add(btnSave);
	    bottomPanel.add(btnCancel);
	    
	    btnSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});

	    btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
	    
	    contentPanel.add(bottomPanel, BorderLayout.SOUTH);
	    
	    
	    pack();
	    setLocationRelativeTo(null);
	}
}
