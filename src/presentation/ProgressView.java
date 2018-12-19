package presentation;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.EmptyBorder;

public class ProgressView extends JDialog {

  private JProgressBar progressBar;
  private JButton startButton;

  public ProgressView(Frame parent) {
	super(parent, true);
	
	CtrlPresentation ctrlPresentation = CtrlPresentation.getInstance();
	
    setTitle("Working...");
    setMinimumSize(new Dimension(300, 100));
    BorderLayout layout = new BorderLayout();
    JPanel contentPanel = new JPanel();
    contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
    contentPanel.setLayout(layout);
    setContentPane(contentPanel);

    progressBar = new JProgressBar(0, 100);
    contentPanel.add(progressBar, BorderLayout.CENTER);
    progressBar.setIndeterminate(true);
    
    JPanel panel_1 = new JPanel();
    getContentPane().add(panel_1, BorderLayout.SOUTH);
    
    startButton = new JButton("Cancel");
    panel_1.add(startButton);
    startButton.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			ctrlPresentation.stopTask();
		}
	});
    pack();
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); //close via Cancel button
  }

}
