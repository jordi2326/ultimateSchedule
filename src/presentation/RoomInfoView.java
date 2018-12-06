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

public class RoomInfoView extends JDialog {
	
	private JPanel contentPanel;
	
	public RoomInfoView(Frame parent, CtrlPresentation ctrlPresentation, String name) {
		super(parent, true);
		
		setTitle("Information");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(400, 200);
		setMinimumSize(new Dimension(400, 200));
		Object[] data = ctrlPresentation.getRoomInfo(name);
	   
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
	    GridBagLayout layout = new GridBagLayout();
	    contentPanel = new JPanel();
	    contentPanel.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(5, 5, 5, 5), BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Room:"), new EmptyBorder(10, 10, 10, 10))));
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
	    add(new JLabel("Capacity:  ", SwingConstants.RIGHT), constraints);
	    iLabel = new JLabel((String) data[1]);
	    iLabel.setFont(iLabel.getFont().deriveFont(Font.PLAIN));
	    add(iLabel, constraints);

	    constraints.gridy = 2;
	    add(new JLabel("Has Computers:  ", SwingConstants.RIGHT), constraints);
	    iLabel = new JLabel((String) data[2]);
	    iLabel.setFont(iLabel.getFont().deriveFont(Font.PLAIN));
	    add(iLabel, constraints);
	    
	    pack();
	    setLocationRelativeTo(null);
	}
}
