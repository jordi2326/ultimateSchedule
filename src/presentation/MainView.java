package presentation;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import org.json.simple.parser.ParseException;

import domain.controllers.CtrlDomain;

import javax.swing.JButton;
import javax.swing.BoxLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.JScrollPane;

public class MainView extends JFrame {

	private CtrlPresentation ctrlPresentation;
	
	private JPanel contentPanel;
	private JTable table;
	
	public MainView(CtrlPresentation ctrlPresentation) {
		this.ctrlPresentation = ctrlPresentation;
		
		//
		CtrlDomain ctrlDomain = CtrlDomain.getInstance();
		try {
			ctrlDomain.importEnvironment("Q1+Q2.json");
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//
		
	    setTitle("Ultimate Schedule");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 400);
		setLocationRelativeTo(null);
		contentPanel = new JPanel();
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanel.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPanel);
		
		JPanel panel = new JPanel();
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
		
		
		JButton btnLoadEnv = new JButton("Load Environment");
		panel.add(btnLoadEnv);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPanel.add(tabbedPane, BorderLayout.WEST);
		tabbedPane.setPreferredSize(new Dimension(300, 0));
		
		JTree treeSubjects = new JTree();
		JScrollPane scollPnlSubjects = new JScrollPane(treeSubjects, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		tabbedPane.addTab("Subjects", null, scollPnlSubjects, null);
		
		JTree treeRooms = new JTree();
		JScrollPane scollPnlRooms = new JScrollPane(treeRooms, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
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
		
		
		table = new JTable();
		contentPanel.add(table, BorderLayout.CENTER);
		
		btnLoadEnv.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(MainView.this, "wtf duuuude", "really?", JOptionPane.WARNING_MESSAGE);
			}
		});
	}

}
