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
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class EditSubjectView extends JDialog {
	
	public EditSubjectView(Frame parent, CtrlPresentation ctrlPresentation, String name) {
		super(parent, true);
		
		setTitle("Information");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(400, 200);
		setMinimumSize(new Dimension(400, 200));
		setMaximumSize(new Dimension(800, 400));
		Object[] data = ctrlPresentation.getSubjectInfo(name);
		
		
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
	    gridPanel.add(new JLabel("Name:  ", SwingConstants.RIGHT), constraints);
	    txtfield = new JTextField((String) data[1]);
	    txtfield.setFont(txtfield.getFont().deriveFont(Font.PLAIN));
	    gridPanel.add(txtfield, constraints);

	    constraints.gridy = 2;
	    gridPanel.add(new JLabel("Level:  ", SwingConstants.RIGHT), constraints);
	    txtfield = new JTextField((String) data[2]);
	    txtfield.setFont(txtfield.getFont().deriveFont(Font.PLAIN));
	    gridPanel.add(txtfield, constraints);
	    
	    constraints.gridy = 3;
	    gridPanel.add(new JLabel("Corequisits:  ", SwingConstants.RIGHT), constraints);
	    ArrayList<String> coreqs = (ArrayList<String>) data[4];
	    String tmp = "-";
	    if(!coreqs.isEmpty()) {
	    	tmp = "<html>";
		    for(String s : coreqs)
		    	tmp += s + "<br>"; 
		    tmp += "</html>";
	    }
	    txtfield = new JTextField(tmp);
	    txtfield.setFont(txtfield.getFont().deriveFont(Font.PLAIN));
	    gridPanel.add(txtfield, constraints);
	    
	    constraints.gridy = 4;
	    gridPanel.add(new JLabel("Groups:  ", SwingConstants.RIGHT), constraints);
	    ArrayList<String> groups = (ArrayList<String>) data[3];
	    tmp = "-";
	    if(!groups.isEmpty()) {
	    	tmp = "<html>";
		    for(String s : groups)
		    	tmp += s + "<br>"; 
		    tmp += "</html>";
	    }
	    JLabel iLabel = new JLabel(tmp);
	    iLabel.setFont(iLabel.getFont().deriveFont(Font.PLAIN));
	    gridPanel.add(iLabel, constraints);
	    
	    
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
