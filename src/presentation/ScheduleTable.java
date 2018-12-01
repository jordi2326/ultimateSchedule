package presentation;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

public class ScheduleTable extends JScrollPane{
	
	private JTable table;
	private static String[] colNames = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
	private static String[] rowNames = {"08:00 - 09:00", "09:00 - 10:00", "10:00 - 11:00", "11:00 - 12:00", "12:00 - 13:00", "13:00 - 14:00", "14:00 - 15:00", "15:00 - 16:00", "16:00 - 17:00", "17:00 - 18:00", "18:00 - 19:00", "19:00 - 20:00"};
	
	public ScheduleTable(Object[][] data){
		super();
		table = new JTable(new ScheduleTableModel(data));
		JTableHeader header = table.getTableHeader();
		header.setOpaque(true);
		header.setBackground(Color.decode("#9499b7"));
		//header.setBorder(new LineBorder(Color.decode("#646997"),1));
		header.setBorder(new LineBorder(Color.decode("#646997"),1));
		header.setForeground(Color.white);
		header.setFont(header.getFont().deriveFont(Font.BOLD, 14f));
		table.setFillsViewportHeight(true);
		table.setRowHeight(40);
		table.setRowSelectionAllowed(false);
		table.setDefaultRenderer(String.class, new CellRenderer());
		//table.setGridColor(Color.decode("#c5cae9"));
		table.setGridColor(Color.decode("#ebedf2"));
		table.setShowGrid(false);
		
		final JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem removeLec = new JMenuItem("Remove Lecture");
        
        JMenuItem moveLec = new JMenuItem("Move Lecture");
        moveLec.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(ScheduleTable.this, "Duuuuuuuuude no..");
            }
        });
        
        removeLec.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(ScheduleTable.this, "Duuuuuuuuude really?");
            }
        });
        
        popupMenu.add(moveLec);
        popupMenu.add(removeLec);
        table.setComponentPopupMenu(popupMenu);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int r = table.rowAtPoint(e.getPoint());
                int c = table.columnAtPoint(e.getPoint());
                if (table.getValueAt(r, c)!=null && !((String) table.getValueAt(r, c)).isEmpty() && r >= 0 && r < table.getRowCount() && c >= 0 && c < table.getColumnCount()) {
                    table.setRowSelectionInterval(r, r);
                    table.setColumnSelectionInterval(c, c);
                } else {
                    table.clearSelection();
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
		JList rowHeader = new JList(lm);
	    rowHeader.setFixedCellWidth(100);
	    rowHeader.setFixedCellHeight(table.getRowHeight());
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
		public Class getColumnClass(int c) {
	        //return getValueAt(0, c).getClass();
	    	return String.class;
	    }

	    /*
	     * Don't need to implement this method unless your table's
	     * editable.
	     */
	    @Override
		public boolean isCellEditable(int row, int col) {
	        return false;
	    }

	    /*
	     * Don't need to implement this method unless your table's
	     * data can change.
	     */
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
	
	private class CellRenderer extends JLabel implements TableCellRenderer {

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int rowIndex, int vColIndex) {
			setOpaque(true);
		    if(value == null || ((String) value).isEmpty()) {
		    	setText("");
		    	setToolTipText("");
		    	if(rowIndex%2==0) setBackground(Color.decode("#fcfcfc")); 
		    	else setBackground(Color.decode("#ebedf2"));
		    	setBorder(BorderFactory.createEmptyBorder());
		    }else {
		    	setText((String) value);
		    	setToolTipText((String) value);
		    	//setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		    	Border padding = BorderFactory.createEmptyBorder(4,4,4,4);
		    	Border dec = new LineBorder(Color.decode("#fcfcfc"),2);
		    	if(rowIndex%2!=0) dec = new LineBorder(Color.decode("#ebedf2"),2);
		    	if(table.getSelectedColumn()==vColIndex && table.getSelectedRow()==rowIndex) {
		    		dec = new LineBorder(Color.decode("#49599a"),2);
			    }
		    	setBorder(BorderFactory.createCompoundBorder(dec, padding));
		    	setForeground(Color.decode("#e8eaf6"));
		    	setBackground(Color.decode("#7986cb"));
		    }
		    
		    
		    return this;
		}
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
		public Component getListCellRendererComponent(JList list, Object value,
		      int index, boolean isSelected, boolean cellHasFocus) {
		    setText((value == null) ? "" : value.toString());
		    return this;
		  }
	}

	public void changeData(Object[][] data) {
		((ScheduleTableModel) table.getModel()).changeData(data);
	}
}
