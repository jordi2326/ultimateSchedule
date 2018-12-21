package presentation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.ScrollPaneLayout;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class RestrictionsView extends JDialog {
	JPanel contentPanel;
	CtrlPresentation ctrlPresentation;
	
	public RestrictionsView(Frame parent) {
		super(parent, true);
		
		ctrlPresentation = CtrlPresentation.getInstance();
		
		setTitle("Restrictions");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(400, 600);
		setMinimumSize(new Dimension(400, 600));
		setLocationRelativeTo(null);
		
		contentPanel = new JPanel();
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		
		JScrollPane scrollPane = new JScrollPane(contentPanel);
		scrollPane.setBorder(new EmptyBorder(0, 5, 0, 5));
		scrollPane.setLayout(new ScrollPaneLayout());
		scrollPane.setOpaque(true);
		scrollPane.setBackground(Color.decode("#dddddd"));
		scrollPane.setMaximumSize(scrollPane.getPreferredSize());
		setContentPane(scrollPane);
		
		JPanel bottomRow = new JPanel();
		bottomRow.setLayout(new BorderLayout());
		bottomRow.setOpaque(true);
		bottomRow.setBackground(Color.white);
		bottomRow.setBorder(new CompoundBorder(new LineBorder(Color.decode("#dddddd"), 4), new EmptyBorder(4, 4, 4, 4)));
		bottomRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
		JButton btnAdd = new JButton("Add");
		btnAdd.setOpaque(true);
		btnAdd.setBorderPainted(true);
		btnAdd.setFocusPainted(false);
		btnAdd.setContentAreaFilled(true);
		btnAdd.setBackground(Color.decode("#fafafa"));
		bottomRow.add(btnAdd, BorderLayout.CENTER);
		contentPanel.add(bottomRow);
		
		btnAdd.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ctrlPresentation.switchToNewRestrictionsView();
				loadData();
			}
		});
		
		loadData();
	}
	
	private void loadData() {
		while(contentPanel.getComponentCount()>1) {
			contentPanel.remove(0);
		}
		
		for(Object[] data : ctrlPresentation.getNegotiableRestrictions()) {
			JPanel row = new JPanel();
			JPanel content = new JPanel();
			content.setLayout(new BorderLayout());
			content.setOpaque(false);
			row.setOpaque(true);
			row.setBackground(Color.white);
			row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));
			row.setBorder(new CompoundBorder(new LineBorder(Color.decode("#dddddd"), 4), new CompoundBorder(new LineBorder(Color.decode("#6382bf"), 1), new EmptyBorder(4, 4, 4, 4))));
			JPanel topPanel = new JPanel();
			topPanel.setLayout(new BorderLayout());
			topPanel.setOpaque(false);
			JPanel bottomPanel = new JPanel();
			bottomPanel.setLayout(new BorderLayout());
			bottomPanel.setOpaque(false);
			JLabel label1 = new JLabel();
			label1.setFont(label1.getFont().deriveFont(Font.BOLD, 14f));
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
					ctrlPresentation.removeRestriction(data[1].toString(), data[0].toString());
					//row.setVisible(false);
					loadData();
				}
			});
			
			JLabel label2 = new JLabel();
			label2.setBorder(new EmptyBorder(4, 4, 4, 4));
			label2.setFont(label2.getFont().deriveFont(Font.PLAIN));
			//label2.setFont(label1.getFont().deriveFont(Font.PLAIN));
			bottomPanel.add(label2, BorderLayout.CENTER);
			
			content.add(topPanel, BorderLayout.NORTH);
			content.add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.CENTER);
			content.add(bottomPanel, BorderLayout.SOUTH);
			JCheckBox checkbox = new JCheckBox("");
			checkbox.setOpaque(false);
			checkbox.setBorder(new EmptyBorder(0, 0, 0, 4));
			checkbox.setSelected((Boolean) data[4]);
			checkbox.addItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent e) {
					ctrlPresentation.setRestrictionEnabled(data[1].toString(), data[0].toString(), checkbox.isSelected());
				}
			});
			
			row.add(checkbox);
			row.add(new JSeparator(SwingConstants.VERTICAL), BorderLayout.CENTER);
			row.add(content);
			label1.setText(data[1].toString());
			label2.setText("<html><b>NOT</b> allowed on <b>" + ScheduleTable.colNames[(int) data[2]].toString() + "</b> at <b>" + ScheduleTable.startTimes[(int) data[3]].toString() + "</b></html>");
			row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
			row.setBackground(Color.decode("#fafafa"));
			contentPanel.add(row, contentPanel.getComponentCount()-1);
		}
		contentPanel.repaint();
		pack();
	}
}
