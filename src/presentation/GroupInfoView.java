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

/**
 * @author Xavier Lacasa Curto
*/
public class GroupInfoView extends JDialog {
	
	public GroupInfoView(Frame parent, String name) {
		super(parent, true);
		
		CtrlPresentation ctrlPresentation = CtrlPresentation.getInstance();
		
		setTitle("Information");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(400, 200);
		setMinimumSize(new Dimension(400, 200));
		Object[] data = ctrlPresentation.getGroupInfo(name);
	   
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
	    GridBagLayout layout = new GridBagLayout();
	    JPanel contentPanel = new JPanel();
	    contentPanel.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(5, 5, 5, 5), BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Group:"), new EmptyBorder(10, 10, 10, 10))));
	    contentPanel.setLayout(layout);
	    setContentPane(contentPanel);
	    constraints.anchor = GridBagConstraints.PAGE_START;
	    constraints.ipady = 5;
	    
	    constraints.gridy = 0;
	    add(new JLabel("Subject:  ", SwingConstants.RIGHT), constraints);
	    JLabel iLabel = new JLabel((String) data[3]);
	    iLabel.setFont(iLabel.getFont().deriveFont(Font.PLAIN));
	    add(iLabel, constraints);
  
	    constraints.gridy = 1;
	    add(new JLabel("Code:  ", SwingConstants.RIGHT), constraints);
	    iLabel = new JLabel((String) data[0]);
	    iLabel.setFont(iLabel.getFont().deriveFont(Font.PLAIN));
	    add(iLabel, constraints);

	    constraints.gridy = 2;
	    add(new JLabel("Parent Group:  ", SwingConstants.RIGHT), constraints);
	    iLabel = new JLabel((String) data[2]);
	    iLabel.setFont(iLabel.getFont().deriveFont(Font.PLAIN));
	    add(iLabel, constraints);
	    
	    constraints.gridy = 3;
	    add(new JLabel("Nº People:  ", SwingConstants.RIGHT), constraints);
	    iLabel = new JLabel((String) data[1]);
	    iLabel.setFont(iLabel.getFont().deriveFont(Font.PLAIN));
	    add(iLabel, constraints);
	    
	    constraints.gridy = 4;
	    add(new JLabel("Type:  ", SwingConstants.RIGHT), constraints);
	    iLabel = new JLabel((String) data[4]);
	    iLabel.setFont(iLabel.getFont().deriveFont(Font.PLAIN));
	    add(iLabel, constraints);
	    
	    constraints.gridy = 5;
	    add(new JLabel("Day Period:  ", SwingConstants.RIGHT), constraints);
	    iLabel = new JLabel((String) data[5]);
	    iLabel.setFont(iLabel.getFont().deriveFont(Font.PLAIN));
	    add(iLabel, constraints);
	    
	    constraints.gridy = 6;
	    add(new JLabel("Weekly Lectures:  ", SwingConstants.RIGHT), constraints);
	    ArrayList<Integer> ls = ctrlPresentation.getGroupsDuration(name);
	    String tmp = "-";
	    if(!ls.isEmpty()) {
	    	tmp = "<html>";
		    for(Integer s : ls)
		    	tmp += s.toString()+" hour(s)<br>";
		    tmp += "</html>";
	    }
	    iLabel = new JLabel(tmp);
	    iLabel.setFont(iLabel.getFont().deriveFont(Font.PLAIN));
	    add(iLabel, constraints);
	    
	    pack();
	    setLocationRelativeTo(null);
	}
}
