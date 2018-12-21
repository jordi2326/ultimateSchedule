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
import java.text.ParseException;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

/** Representa un grup.
 * @author Xavier Martín Ballesteros
*/
public class NewRoomView extends JDialog {
	
	public NewRoomView(Frame parent) {
		super(parent, true);
		
		CtrlPresentation ctrlPresentation = CtrlPresentation.getInstance();
		
		setTitle("New Room");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(400, 200);
		setMinimumSize(new Dimension(400, 200));
		
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
	    GridBagLayout gridLayout = new GridBagLayout();
	    JPanel contentPanel = new JPanel();
	    JPanel gridPanel = new JPanel();
	    gridPanel.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(5, 5, 5, 5), BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Room:"), new EmptyBorder(10, 10, 10, 10))));
	    gridPanel.setLayout(gridLayout);
	    contentPanel.setLayout(new BorderLayout(0, 0));
	    setContentPane(contentPanel);
	    contentPanel.add(gridPanel, BorderLayout.CENTER);
	    constraints.anchor = GridBagConstraints.PAGE_START;
	    constraints.ipady = 5;

	    constraints.gridy = 0;
	    gridPanel.add(new JLabel("Code:  ", SwingConstants.RIGHT), constraints);
	    JTextField tfCode = new JTextField(6);
	    tfCode.setColumns(2);
	    gridPanel.add(tfCode, constraints);
	    
	    constraints.gridy = 1;
	    gridPanel.add(new JLabel("Capacity:  ", SwingConstants.RIGHT), constraints);
	    JSpinner spCapacity = new JSpinner(new SpinnerNumberModel(0,0,Integer.MAX_VALUE,1));
	    //spCapacity.setSize(20, spCapacity.getSize().height);
	    JSpinner.DefaultEditor spinnerEditor = (DefaultEditor) spCapacity.getEditor();
	    spinnerEditor.getTextField().setHorizontalAlignment(JTextField.LEFT);
	    spinnerEditor.getTextField().setColumns(6);
	    JPanel jpCapacity = new JPanel();
	    jpCapacity.setLayout(new BorderLayout());
	    //jpCapacity.setBorder(new EmptyBorder(0, 0, 0, 100));
	    jpCapacity.add(spCapacity, BorderLayout.CENTER);
	    gridPanel.add(jpCapacity, constraints);

	    constraints.gridy = 2;
	    gridPanel.add(new JLabel("Has Computers:  ", SwingConstants.RIGHT), constraints);
	    JCheckBox cbHasComputers = new JCheckBox("");
	    cbHasComputers.setSelected(false);
	    gridPanel.add(cbHasComputers, constraints);
	    
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
					spCapacity.commitEdit();
				} catch (ParseException e1) {}
				Integer inCapacity = (Integer) spCapacity.getValue();
				
				Boolean inHasComputers = cbHasComputers.isSelected();

				if(ctrlPresentation.addRoom(inCode, inCapacity, inHasComputers)) {
					ctrlPresentation.roomAdded(inCode);
					setVisible(false);
					dispose();
				}else{
					JOptionPane.showMessageDialog(NewRoomView.this, "Room data is not valid", "", JOptionPane.WARNING_MESSAGE);
				}
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
