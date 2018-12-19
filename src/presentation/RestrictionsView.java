package presentation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.ListCellRenderer;
import javax.swing.ScrollPaneLayout;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class RestrictionsView extends JDialog {
	
	public RestrictionsView(Frame parent) {
		super(parent, true);
		
		CtrlPresentation ctrlPresentation = CtrlPresentation.getInstance();
		
		setTitle("Restrictions");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(400, 600);
		setLocationRelativeTo(null);
		
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		
		JScrollPane scrollPane = new JScrollPane(contentPanel);
		scrollPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		scrollPane.setLayout(new ScrollPaneLayout());
		scrollPane.setOpaque(true);
		scrollPane.setMaximumSize(scrollPane.getPreferredSize());
		setContentPane(scrollPane);
		
		
		/**JList<String[]> list = new JList<String[]>();
		//list.setBackground(bg);
		//list.setOpaque(false);
		contentPanel.add(new JScrollPane(list), BorderLayout.CENTER);
		list.setCellRenderer(new CellRenderer());
		
		list.setListData(testdata);**/
		String[][] testdata = {{"M1-11-THEORY", "Monday", "10:00"}, {"M1CCCCCC", "Monday", "10:00"}, {"CCCCCC", "Monday", "10:00"}};
		for(String[] rowData : testdata) {
			JPanel row = new JPanel();
			row.setOpaque(true);
			row.setBackground(Color.white);
			row.setLayout(new BoxLayout(row, BoxLayout.Y_AXIS));
			row.setBorder(new CompoundBorder(new LineBorder(Color.decode("#eeeeee"), 4), new EmptyBorder(4, 4, 4, 4)));
			JPanel topPanel = new JPanel();
			topPanel.setLayout(new BorderLayout());
			topPanel.setOpaque(false);
			JPanel bottomPanel = new JPanel();
			bottomPanel.setLayout(new BorderLayout());
			bottomPanel.setOpaque(false);
			JLabel label1 = new JLabel();
			
			label1.setBorder(new EmptyBorder(4, 4, 4, 4));
			topPanel.add(label1, BorderLayout.CENTER);
			JButton btnRemove = new JButton("x");
			btnRemove.setMargin(new Insets(0, 4, 0, 4));
			btnRemove.setBorderPainted(true);
			btnRemove.setBorder(new CompoundBorder(new EmptyBorder(10, 10, 10, 10), btnRemove.getBorder()));
			btnRemove.setFocusPainted(false);
			btnRemove.setContentAreaFilled(false);
			topPanel.add(btnRemove, BorderLayout.EAST);
			btnRemove.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					System.out.println("clicked");
					
				}
			});
			
			JLabel label2 = new JLabel();
			label2.setBorder(new EmptyBorder(4, 4, 4, 4));
			label2.setFont(label1.getFont().deriveFont(Font.PLAIN));
			bottomPanel.add(label2, BorderLayout.CENTER);
			
			row.add(topPanel);
			row.add(new JSeparator());
			row.add(bottomPanel);
			
			label1.setText(((String[]) rowData)[0]);
			label2.setText(((String[]) rowData)[1] + " - " + ((String[]) rowData)[2]);
			row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
			contentPanel.add(row);
		}
		
		JPanel bottomRow = new JPanel();
		bottomRow.setLayout(new BorderLayout());
		bottomRow.setOpaque(true);
		bottomRow.setBackground(Color.white);
		bottomRow.setBorder(new CompoundBorder(new LineBorder(Color.decode("#eeeeee"), 4), new EmptyBorder(4, 4, 4, 4)));
		bottomRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
		JButton btnAdd = new JButton("Add");
		btnAdd.setBorderPainted(true);
		btnAdd.setFocusPainted(false);
		btnAdd.setContentAreaFilled(false);
		bottomRow.add(btnAdd, BorderLayout.CENTER);
		contentPanel.add(bottomRow);
		
		btnAdd.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
	}
}
