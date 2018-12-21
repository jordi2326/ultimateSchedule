package presentation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

public class NewSubjectView extends JDialog {
	
	public NewSubjectView(Frame parent) {
		super(parent, true);
		
		CtrlPresentation ctrlPresentation = CtrlPresentation.getInstance();
		
		setTitle("New Subject");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(400, 200);
		setMinimumSize(new Dimension(400, 200));
		
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
	    gridPanel.add(new JLabel("Name:  ", SwingConstants.RIGHT), constraints);
	    JTextField tfName = new JTextField();
	    gridPanel.add(tfName, constraints);
	    
	    constraints.gridy = 1;
	    gridPanel.add(new JLabel("Code:  ", SwingConstants.RIGHT), constraints);
	    JTextField tfCode = new JTextField(6);
	    tfCode.setBorder(BorderFactory.createCompoundBorder(new MatteBorder(0, 0, 0, 100, getParent().getBackground()), tfCode.getBorder()));
	    gridPanel.add(tfCode, constraints);

	    constraints.gridy = 2;
	    gridPanel.add(new JLabel("Level:  ", SwingConstants.RIGHT), constraints);
	    JTextField tfLevel = new JTextField();
	    tfLevel.setBorder(BorderFactory.createCompoundBorder(new MatteBorder(0, 0, 0, 100, getParent().getBackground()), tfLevel.getBorder()));
	    gridPanel.add(tfLevel, constraints);
	    
	    constraints.gridy = 3;
	    gridPanel.add(new JLabel("Corequisits:  ", SwingConstants.RIGHT), constraints);
	    JButton addCoreq = new JButton(" + ");
	    JButton rmvCoreq = new JButton(" - ");
	    rmvCoreq.setVisible(false);
	    JPanel panelCoreqs = new JPanel();
	    ArrayList<String> inCoreqs = new ArrayList<String>();
	    panelCoreqs.setLayout(new BoxLayout(panelCoreqs, BoxLayout.Y_AXIS));
	    panelCoreqs.setBorder(new EmptyBorder(2, 0, 0, 0));
	    //addCoreq.setBorder(BorderFactory.createCompoundBorder(new MatteBorder(0, 0, 0, 100, getParent().getBackground()), tfCode.getBorder()));
	    addCoreq.setMargin(new Insets(0, 0, 0, 0));
	    addCoreq.setBorderPainted(true);
	    addCoreq.setFocusPainted(false);
	    addCoreq.setContentAreaFilled(false);
	    addCoreq.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ArrayList<String> subjectsToSelect = new ArrayList<>(ctrlPresentation.getSubjectNames());
				subjectsToSelect.removeAll(inCoreqs);
				String selected = (String) JOptionPane.showInputDialog(NewSubjectView.this, 
						"",
						"Add Corequisit",
				        JOptionPane.QUESTION_MESSAGE, 
				        null, 
				        subjectsToSelect.toArray(), 
						null);
				if(selected != null && !selected.isEmpty() && !inCoreqs.contains(selected)) {
					inCoreqs.add(selected);
					panelCoreqs.add(new JLabel(selected), panelCoreqs.getComponentCount()-1);
					panelCoreqs.revalidate();
					panelCoreqs.repaint();
					rmvCoreq.setVisible(true);
					pack();
				}
				
			}
		});
	    rmvCoreq.setMargin(new Insets(0, 0, 0, 0));
	    rmvCoreq.setBorderPainted(true);
	    rmvCoreq.setFocusPainted(false);
	    rmvCoreq.setContentAreaFilled(false);
	    rmvCoreq.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				inCoreqs.remove(inCoreqs.size()-1);
				panelCoreqs.remove(panelCoreqs.getComponentCount()-2);
				panelCoreqs.revalidate();
				panelCoreqs.repaint();
				if(inCoreqs.isEmpty()) rmvCoreq.setVisible(false);
				pack();
				
			}
		});
	    JPanel btnsCoreq = new JPanel();
	    btnsCoreq.setLayout(new BoxLayout(btnsCoreq, BoxLayout.X_AXIS));
	    btnsCoreq.setAlignmentX(JScrollPane.LEFT_ALIGNMENT);
	    btnsCoreq.add(addCoreq);
	    btnsCoreq.add(rmvCoreq);
	    panelCoreqs.add(btnsCoreq);
	    gridPanel.add(panelCoreqs, constraints);
	    
	    JButton btnAdd = new JButton("Add");
	    JButton btnCancel = new JButton("Cancel");
	    
	    JPanel bottomPanel = new JPanel();
	    bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
	    bottomPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
	    bottomPanel.add(btnAdd);
	    bottomPanel.add(btnCancel);
	    
	    btnAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String inCode = tfCode.getText();
				String inName = tfName.getText();
				String inLevel = tfLevel.getText();
				if(ctrlPresentation.addSubject(inCode, inName, inLevel, inCoreqs)) {
					ctrlPresentation.subjectAdded(inCode);
					setVisible(false);
					dispose();
				}else{
					JOptionPane.showMessageDialog(NewSubjectView.this, "Subject data is not valid", "", JOptionPane.WARNING_MESSAGE);
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
