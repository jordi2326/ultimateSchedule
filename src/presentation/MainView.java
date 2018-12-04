package presentation;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTabbedPane;
import javax.swing.JTree;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.JScrollPane;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Set;

import domain.controllers.CtrlDomain;
import presentation.ScheduleTable.LectureClickedEventListener;

public class MainView extends JFrame {

	private CtrlPresentation ctrlPresentation;
	
	private JPanel contentPanel;
	private ScheduleTable table;
	
	final private JFileChooser fc = new JFileChooser();
	
	private boolean environmentLoaded;
	
	public MainView(CtrlPresentation ctrlPresentation) {
		this.ctrlPresentation = ctrlPresentation;
		
		environmentLoaded = false;
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
		
		JButton btnLoadEnv = new JButton("Load Environment");
		panel.add(btnLoadEnv);
		
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
		
		JButton btnGenSchedule = new JButton("Generate Schedule");
		panel.add(btnGenSchedule);
		btnGenSchedule.setEnabled(environmentLoaded);
		
		btnGenSchedule.addActionListener(new ActionListener() {
			
			@Override	
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(MainView.this, "wtf");
			}
		});
		
		JTabbedPane tabbedPane = new JTabbedPane(SwingConstants.TOP);
		contentPanel.add(tabbedPane, BorderLayout.WEST);
		tabbedPane.setPreferredSize(new Dimension(300, 0));
		
		JTree treeSubjects = new JTree();
		JScrollPane scollPnlSubjects = new JScrollPane(treeSubjects, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		tabbedPane.addTab("Subjects", null, scollPnlSubjects, null);
		
		JCheckBoxTree treeRooms = new JCheckBoxTree();
		JScrollPane scollPnlRooms = new JScrollPane(treeRooms, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		tabbedPane.addTab("Rooms", null, scollPnlRooms, null);
		
		DefaultTreeModel model =(DefaultTreeModel) treeRooms.getModel();
		
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("All Rooms");
		model.setRoot(root);

		Set<String> rooms = ctrlPresentation.getRoomNames();;

		for(String room : rooms) {
			DefaultMutableTreeNode child = new DefaultMutableTreeNode(room);
			root.add(child);
		}
		
		treeRooms.addCheckChangeEventListener(new JCheckBoxTree.CheckChangeEventListener() {
            public void checkStateChanged(JCheckBoxTree.CheckChangeEvent event) {
            	table.stopEditing();
            	DefaultMutableTreeNode  tn = ((DefaultMutableTreeNode ) event.getPath().getLastPathComponent());
            	if(tn.isLeaf()) {
            		String roomName = (String) tn.getUserObject();
            		if(event.getChecked()) {
            			table.filterRoomIn(roomName);
            		}else{
            			table.filterRoomOut(roomName);
            		}
            	}else { //is root
            		Enumeration<TreeNode> childs = tn.children();
            		while(childs.hasMoreElements()) {
            			DefaultMutableTreeNode tnn = (DefaultMutableTreeNode) childs.nextElement();
            			if(event.getChecked()) {
                			table.filterRoomIn((String) tnn.getUserObject());
                		}else{
                			table.filterRoomOut((String) tnn.getUserObject());
                		}
            		}
            	}
            }           
        });
		
		treeRooms.expandPath(new TreePath(root.getPath()));
		//treeRooms.setRootVisible(false);
		treeRooms.setModel(model);
		treeRooms.checkSubTree(new TreePath(root.getPath()), true);
		
		table = new ScheduleTable(ctrlPresentation.getScheduleMatrix());
		contentPanel.add(table, BorderLayout.CENTER);
		
		table.addLectureClickedEventListener(new LectureClickedEventListener() {
			
			@Override
			public void lectureClicked(MouseEvent e, String[] value, int duration, int row, int col) {
				if(SwingUtilities.isRightMouseButton(e)) {
					final JPopupMenu popupMenu = new JPopupMenu();
			        JMenuItem removeLec = new JMenuItem("Remove Lecture");
			        
			        JMenuItem moveLec = new JMenuItem("Move Lecture");
			        moveLec.addActionListener(new ActionListener() {

			            @Override
			            public void actionPerformed(ActionEvent e) {
			            	MoveLectureView mlView = new MoveLectureView(value[0], duration, col, row, value[1]);
			            	mlView.makeVisible();
			            }
			        });
			        
			        removeLec.addActionListener(new ActionListener() {

			            @Override
			            public void actionPerformed(ActionEvent e) {
			            }
			        });
			        
			        popupMenu.add(moveLec);
			        popupMenu.add(removeLec);
                	popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
				
			}
		});
		
		btnLoadEnv.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(MainView.this, "wtf duuuude", "really?", JOptionPane.WARNING_MESSAGE);
			}
		});
        
		treeRooms.addMouseListener(new MouseAdapter() {
		     public void mousePressed(MouseEvent e) {
		    	 if(SwingUtilities.isRightMouseButton(e)) {
                 	int selRow = treeRooms.getRowForLocation(e.getX(), e.getY());
	   		        TreePath selPath = treeRooms.getPathForLocation(e.getX(), e.getY());
	   		        treeRooms.clearSelection();
	   		        treeRooms.setSelectionPath(selPath);
	   		        if(selRow != -1 && ((DefaultMutableTreeNode) selPath.getLastPathComponent()).isLeaf()) {
	   		        	final JPopupMenu popupMenu = new JPopupMenu();
		   		         JMenuItem editRoom = new JMenuItem("Edit Room");
		   		         JMenuItem deleteRoom = new JMenuItem("Delete Room");
		   		         editRoom.addActionListener(new ActionListener() {
	
		   		             @Override
		   		             public void actionPerformed(ActionEvent e) {
		   		                 JOptionPane.showMessageDialog(MainView.this, "Duuuuuuuuude no.." + selPath);
		   		             }
		   		         });
		   		         
		   		         deleteRoom.addActionListener(new ActionListener() {
	
		   		             @Override
		   		             public void actionPerformed(ActionEvent e) {
		   		                 JOptionPane.showMessageDialog(MainView.this, "Duuuuuuuuude really? " + selPath);
		   		             }
		   		         });
		   		         
		   		        popupMenu.add(editRoom);
		   		        popupMenu.add(deleteRoom);
	   		        	popupMenu.show(e.getComponent(), e.getX(), e.getY());
	   		        }
                 }
		         
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
		ArrayList<String[]>[][] data = ctrlPresentation.getScheduleMatrix();
		table.changeData(data);
	}

}
