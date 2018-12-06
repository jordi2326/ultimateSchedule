package presentation;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class SubjectInfoView extends JDialog {
	
	private JPanel contentPanel;
	
	public SubjectInfoView(Frame parent, CtrlPresentation ctrlPresentation, String name) {
		super(parent, true);
		
		setTitle("Information");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(400, 200);
		setMinimumSize(new Dimension(400, 200));
		Object[] data = ctrlPresentation.getSubjectInfo(name);
	   
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
	    GridBagLayout layout = new GridBagLayout();
	    contentPanel = new JPanel();
	    contentPanel.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(5, 5, 5, 5), BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Subject:"), new EmptyBorder(10, 10, 10, 10))));
	    contentPanel.setLayout(layout);
	    setContentPane(contentPanel);
	    constraints.anchor = GridBagConstraints.PAGE_START;
	    constraints.ipady = 5;
  
	    constraints.gridy = 0;
	    add(new JLabel("Code:  ", SwingConstants.RIGHT), constraints);
	    JLabel iLabel = new JLabel((String) data[0]);
	    iLabel.setFont(iLabel.getFont().deriveFont(Font.PLAIN));
	    add(iLabel, constraints);
	    
	    constraints.gridy = 1;
	    add(new JLabel("Name:  ", SwingConstants.RIGHT), constraints);
	    iLabel = new JLabel((String) data[1]);
	    iLabel.setFont(iLabel.getFont().deriveFont(Font.PLAIN));
	    add(iLabel, constraints);

	    constraints.gridy = 2;
	    add(new JLabel("Level:  ", SwingConstants.RIGHT), constraints);
	    iLabel = new JLabel((String) data[2]);
	    iLabel.setFont(iLabel.getFont().deriveFont(Font.PLAIN));
	    add(iLabel, constraints);
	    
	    constraints.gridy = 3;
	    add(new JLabel("Corequisits:  ", SwingConstants.RIGHT), constraints);
	    ArrayList<String> coreqs = (ArrayList<String>) data[4];
	    String tmp = "-";
	    if(!coreqs.isEmpty()) {
	    	tmp = "<html>";
		    for(String s : coreqs)
		    	tmp += s + "<br>"; 
		    tmp += "</html>";
	    }
	    iLabel = new JLabel(tmp);
	    iLabel.setFont(iLabel.getFont().deriveFont(Font.PLAIN));
	    add(iLabel, constraints);
	    
	    constraints.gridy = 4;
	    add(new JLabel("Groups:  ", SwingConstants.RIGHT), constraints);
	    ArrayList<String> groups = (ArrayList<String>) data[3];
	    tmp = "-";
	    if(!groups.isEmpty()) {
	    	tmp = "<html>";
		    for(String s : groups)
		    	tmp += s + "<br>"; 
		    tmp += "</html>";
	    }
	    iLabel = new JLabel(tmp);
	    iLabel.setFont(iLabel.getFont().deriveFont(Font.PLAIN));
	    add(iLabel, constraints);
	    
	    pack();
	    setLocationRelativeTo(null);
	}
}
