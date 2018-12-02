package presentation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import domain.controllers.CtrlDomain;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JTabbedPane;
import javax.swing.JTree;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;

public class MainView extends JFrame {

	private CtrlPresentation ctrlPresentation;
	
	private JPanel contentPanel;
	private ScheduleTable table;
	
	final private JFileChooser fc = new JFileChooser();
	
	public MainView(CtrlPresentation ctrlPresentation) {
		this.ctrlPresentation = ctrlPresentation;
		
		//TODO: Remove
		CtrlDomain ctrlDomain = CtrlDomain.getInstance();
		try {
			ctrlDomain.importEnvironment("Q1+Q2.json");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		//
		
	    setTitle("Ultimate Schedule");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1200, 600);
		setLocationRelativeTo(null);
		contentPanel = new JPanel();
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanel.setLayout(new BorderLayout(0, 0));
		contentPanel.setOpaque(true);
		//contentPanel.setBackground(Color.decode("#616161"));
		setContentPane(contentPanel);
		
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		contentPanel.add(panel, BorderLayout.NORTH);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton btn_genSchedule = new JButton("Generate Schedule");
		panel.add(btn_genSchedule);
		
		btn_genSchedule.addActionListener(new ActionListener() {
			
			@Override	
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(MainView.this, "wtf");
			}
		});
		
		JButton btnLoadSchedule = new JButton("Load Schedule");
		panel.add(btnLoadSchedule);
		
		btnLoadSchedule.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				File path = new File("data/schedules");
				File selected = loadLocalFile(path);
				if (!selected.equals(path)) {	//user selected a file
					try {
						ctrlPresentation.importSchedule(selected.getName());
						redrawScheduleMatrix();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		
		JButton btnLoadEnv = new JButton("Load Environment");
		panel.add(btnLoadEnv);
		
		JTabbedPane tabbedPane = new JTabbedPane(SwingConstants.TOP);
		contentPanel.add(tabbedPane, BorderLayout.WEST);
		tabbedPane.setPreferredSize(new Dimension(300, 0));
		
		JTree treeSubjects = new JTree();
		JScrollPane scollPnlSubjects = new JScrollPane(treeSubjects, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		tabbedPane.addTab("Subjects", null, scollPnlSubjects, null);
		
		JTree treeRooms = new JTree();
		JScrollPane scollPnlRooms = new JScrollPane(treeRooms, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		tabbedPane.addTab("Rooms", null, scollPnlRooms, null);
		
		DefaultTreeModel model =(DefaultTreeModel) treeRooms.getModel();
		
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");
		model.setRoot(root);
		for(String room : ctrlPresentation.getAllRooms()) {
			DefaultMutableTreeNode child = new DefaultMutableTreeNode(room);
			root.add(child);
		}
		treeRooms.expandPath(new TreePath(root.getPath()));
		treeRooms.setRootVisible(false);
		
		
		table = new ScheduleTable(ctrlPresentation.getScheduleMatrix());
		contentPanel.add(table, BorderLayout.CENTER);
		
		btnLoadEnv.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(MainView.this, "wtf duuuude", "really?", JOptionPane.WARNING_MESSAGE);
			}
		});
	}
	
	private File loadLocalFile(File file) {
		fc.setCurrentDirectory(file);
		int returnVal = fc.showOpenDialog(contentPanel);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            return fc.getSelectedFile();
        }
		return file;
	}
	
	private void redrawScheduleMatrix() {
		ArrayList<String>[][] data = ctrlPresentation.getScheduleMatrix();
		table.changeData(data);
	}

}
