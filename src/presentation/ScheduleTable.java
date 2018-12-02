package presentation;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EventObject;
import java.util.HashSet;
import javax.swing.AbstractListModel;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.CellEditorListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

public class ScheduleTable extends JScrollPane{
	
	private JTable table;
	private JList rowHeader;
	private static String[] colNames = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
	private static String[] rowNames = {"08:00 - 09:00", "09:00 - 10:00", "10:00 - 11:00", "11:00 - 12:00", "12:00 - 13:00", "13:00 - 14:00", "14:00 - 15:00", "15:00 - 16:00", "16:00 - 17:00", "17:00 - 18:00", "18:00 - 19:00", "19:00 - 20:00"};
	private int[] heights = new int[12];
	private HashSet<String> rooms_filter = new HashSet<String>();
	private HashSet<String> groups_filter = new HashSet<String>();
	public ScheduleTable(Object[][] data){
		super();
		
		rooms_filter.add("A6002");
		rooms_filter.add("A5104");
		rooms_filter.add("A5109");
		rooms_filter.add("A5S01");
		rooms_filter.add("A5S104");
		rooms_filter.add("A5S105");
		rooms_filter.add("A5S109");
		rooms_filter.add("C6S306");
		rooms_filter.add("C6S308");
		
		table = new JTable(new ScheduleTableModel(data));
		JTableHeader header = table.getTableHeader();
		header.setOpaque(true);
		header.setBackground(Color.decode("#9499b7"));
		header.setBorder(new LineBorder(Color.decode("#646997"),1));
		header.setForeground(Color.white);
		header.setFont(header.getFont().deriveFont(Font.BOLD, 14f));
		table.setFillsViewportHeight(true);
		table.setRowHeight(40);
		Arrays.fill(heights, 40);
		table.setRowSelectionAllowed(false);
		table.setGridColor(Color.decode("#ebedf2"));
		table.setShowGrid(false);
		table.getTableHeader().setReorderingAllowed(false);
		
		CellRenderer renderer = new CellRenderer();
		table.setDefaultRenderer(Object.class, renderer);
		table.setDefaultEditor(Object.class, renderer);

		table.addMouseListener(new MouseAdapter() {
			@Override
            public void mousePressed(MouseEvent e) {
				int r = table.rowAtPoint(e.getPoint());
                int c = table.columnAtPoint(e.getPoint());
                if (table.getValueAt(r, c)!=null && !((ArrayList<String>) table.getValueAt(r, c)).isEmpty() && r >= 0 && r < table.getRowCount() && c >= 0 && c < table.getColumnCount()) {
                	table.editCellAt(r, c);
                }
			}
		});
		
		ListModel lm = new AbstractListModel() {
		      @Override
			public int getSize() {
		        return rowNames.length;
		      }

		      @Override
			public Object getElementAt(int index) {
		        return rowNames[index];
		      }
		    };
		    
		rowHeader = new JList(lm);
	    rowHeader.setFixedCellWidth(100);
	    rowHeader.setCellRenderer(new RowHeaderRenderer(table));
	    rowHeader.setOpaque(false);
	    
	    this.setViewportView(table);
		this.setRowHeaderView(rowHeader);
		this.getViewport().setOpaque(false);
	    this.setOpaque(false);
		table.setOpaque(false);
	}
	
	private class ScheduleTableModel extends AbstractTableModel {
		Object[][] data;
		public ScheduleTableModel(Object[][] data) {
			this.data = data;
		}

	    @Override
		public int getColumnCount() {
	        return colNames.length;
	    }

	    @Override
		public int getRowCount() {
	        return data.length;
	    }

	    @Override
		public String getColumnName(int col) {
	        return colNames[col];
	    }

	    @Override
		public Object getValueAt(int row, int col) {
	        return data[row][col];
	    }

	    @Override
		public Class getColumnClass(int c) {return String.class;}

	    @Override
		public boolean isCellEditable(int row, int col) {return true;}

	    @Override
		public void setValueAt(Object value, int row, int col) {
	        data[row][col] = value;
	        fireTableCellUpdated(row, col);
	    }
	    
	    public void changeData(Object[][] data) {
	        this.data = data;
	        fireTableDataChanged();
	    }
	}
	
	private class CellRenderer implements TableCellRenderer, TableCellEditor {
		JList list;
		JList list_ed;
		public CellRenderer() {
			list = new JList();
			list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			list.setLayoutOrientation(JList.VERTICAL);
			list.setFixedCellHeight(30);
			list.setCellRenderer(new LectureRenderer());
			
			list_ed = new JList();
			list_ed.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			list_ed.setLayoutOrientation(JList.VERTICAL);
			list_ed.setFixedCellHeight(list.getFixedCellHeight());
			list_ed.setCellRenderer(new LectureRenderer());
			
			final JPopupMenu popupMenu = new JPopupMenu();
	        JMenuItem removeLec = new JMenuItem("Remove Lecture");
	        
	        JMenuItem moveLec = new JMenuItem("Move Lecture");
	        moveLec.addActionListener(new ActionListener() {

	            @Override
	            public void actionPerformed(ActionEvent e) {
	                JOptionPane.showMessageDialog(ScheduleTable.this, "Duuuuuuuuude no.." + list_ed.getSelectedValue());
	            }
	        });
	        
	        removeLec.addActionListener(new ActionListener() {

	            @Override
	            public void actionPerformed(ActionEvent e) {
	                JOptionPane.showMessageDialog(ScheduleTable.this, "Duuuuuuuuude really? " + list_ed.getSelectedValue());
	            }
	        });
	        
	        popupMenu.add(moveLec);
	        popupMenu.add(removeLec);
	        
			list_ed.addMouseListener(new MouseAdapter(){   
	            public void mouseReleased(MouseEvent e) {
	            	int i = list_ed.locationToIndex(e.getPoint());
	            	if(list_ed.getCellBounds(i, i).contains(e.getPoint())) {
	            		list_ed.setSelectedIndex(i);
	            		stopCellEditing();
						if(SwingUtilities.isRightMouseButton(e)) {
	                    	popupMenu.show(e.getComponent(), e.getX(), e.getY());
	                    }
	            	}
	            }
	         });
		}
		
		public Component getComponent(JList l, JTable table, Object value, boolean isSelected, boolean hasFocus, int rowIndex, int colIndex) {
			if(rowIndex%2==0) l.setBackground(Color.decode("#fcfcfc")); 
	    	else l.setBackground(Color.decode("#ebedf2"));
		    if(value == null) {
		    	l.setListData(new String[][] {});   	
		    }else {
				//((ArrayList<String[]>) value).removeIf(x -> groups_whitelist.contains(x[0]) || rooms_whitelist.contains(x[1]));
		    	ArrayList<String[]> filtered = (ArrayList<String[]>) ((ArrayList<String[]>) value).clone();
		    	filtered.removeIf(x -> !rooms_filter.contains(x[1]));
				l.setListData(filtered.toArray());
				
				int height = Math.max(l.getFixedCellHeight()*filtered.size(), table.getRowHeight(rowIndex));
				table.setRowHeight(rowIndex, height);
				heights[rowIndex] = height;
				rowHeader.setListData(rowNames); //to force reddraw
		    }
		    return l;
		}
		
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int rowIndex, int colIndex) {
		    return getComponent(list, table, value, isSelected, hasFocus, rowIndex, colIndex);
		}
		
		@Override
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int rowIndex, int colIndex) {
		    return getComponent(list_ed, table, value, isSelected, true, rowIndex, colIndex);
		}

		@Override
		public void addCellEditorListener(CellEditorListener arg0) {}

		@Override
		public void cancelCellEditing() {}

		@Override
		public Object getCellEditorValue() {return null;}

		@Override
		public boolean isCellEditable(EventObject arg0) {return true;}

		@Override
		public void removeCellEditorListener(CellEditorListener arg0) {}

		@Override
		public boolean shouldSelectCell(EventObject arg0) {return true;}

		@Override
		public boolean stopCellEditing() {return true;}

		
	}
	
	private class RowHeaderRenderer extends JLabel implements ListCellRenderer {
		  
		  RowHeaderRenderer(JTable table) {
		    JTableHeader header = table.getTableHeader();
		    setOpaque(true);
		    setBorder(header.getBorder());
		    setHorizontalAlignment(CENTER);
		    setForeground(header.getForeground());
		    setBackground(Color.decode("#9499b7"));
		    setFont(header.getFont().deriveFont(Font.BOLD, 12f));
		  }
		  
		  @Override
		  public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		    setText((value == null) ? "" : value.toString());
		    setPreferredSize(new Dimension(0, heights[index]));
		    
		    return this;
		  }
	}
	
	private class LectureRenderer extends JLabel implements ListCellRenderer {
		@Override
		public Component getListCellRendererComponent(JList list, Object value,int index, boolean isSelected, boolean cellHasFocus) {
			  if(value == null || ((String[]) value).length==0) {
			    	setText("");
				  	setOpaque(false);
			    	setBorder(BorderFactory.createEmptyBorder());
			    }else {
			    	setText(((String[]) value)[0] + "  [" + ((String[]) value)[1] + "]");
			    	setOpaque(true);
			    	Border padding = BorderFactory.createEmptyBorder(4,4,4,4);
			    	Border dec = new LineBorder(Color.decode("#fcfcfc"),2);
			    	if(isSelected) dec = new LineBorder(Color.decode("#49599a"), 3);
			    	setBorder(BorderFactory.createCompoundBorder(dec, padding));
			    	setForeground(Color.decode("#e8eaf6"));
			    	setBackground(Color.decode("#7986cb"));
			    }
		    return this;
		  }
	}

	public void changeData(Object[][] data) {
		((ScheduleTableModel) table.getModel()).changeData(data);
	}
	
	public void stopEditing() {
		table.removeEditor();
	}
	
	public void filterRoomIn(String roomName){
		rooms_filter.add(roomName);
		((AbstractTableModel) table.getModel()).fireTableDataChanged();
	}
	
	public void filterRoomOut(String roomName){
		rooms_filter.remove(roomName);
		((AbstractTableModel) table.getModel()).fireTableDataChanged();
	}
}
