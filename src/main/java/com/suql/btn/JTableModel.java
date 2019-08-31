package com.suql.btn;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JTableModel extends AbstractTableModel {
    private int count;
    private static final long serialVersionUID = 1L;
    private static final String[] COLUMN_NAMES = new String[] {"Id", "Stuff", "Button1", "Button2"};
    private static final Class<?>[] COLUMN_TYPES = new Class<?>[] {Integer.class, String.class, JButton.class,  JButton.class};

    public JTableModel(int count) {
        this.count = count;
    }

    public int getColumnCount() {
        return COLUMN_NAMES.length;
    }

    public int getRowCount() {
        return count;
    }

    @Override public String getColumnName(int columnIndex) {
        return COLUMN_NAMES[columnIndex];
    }

    @Override public Class<?> getColumnClass(int columnIndex) {
        return COLUMN_TYPES[columnIndex];
    }

    public Object getValueAt(final int rowIndex, final int columnIndex) {
        /*Adding components*/
        switch (columnIndex) {
            case 0: return rowIndex;
            case 1: return "Text for "+rowIndex;
            case 2: // fall through
                /*Adding button and creating click listener*/
            case 3: final JButton button = new JButton(COLUMN_NAMES[columnIndex]);
                button.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent arg0) {
                        JOptionPane.showMessageDialog(JOptionPane.getFrameForComponent(button),
                                "Button clicked for row "+rowIndex);
                    }
                });
                return button;
            default: return "Error";
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        switch (columnIndex) {

            case 0:
                return false;
            case 1:
                return false;
            case 2:
                return true;
            case 3:
                return true;
            default:
                return false;

        }
    }
}
