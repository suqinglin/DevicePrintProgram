package com.suql.btn;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
public class JTableExamples {

    // frame
    JFrame f;
    // Table
    JTable j;

    // Constructor
    JTableExamples() {
        // Frame initiallization
        f = new JFrame();
        f.setLayout(new FlowLayout());

        // Frame Title
        f.setTitle("JTable Example"); //$NON-NLS-1$

        JPanel jp = new JPanel();
        final JButton jb = new JButton("添加");
        jp.add(jb);
//        f.getContentPane().add(jp);


        // Initializing the JTable
        j = new JTable(new JTableModel(10));
        j.addColumn(new TableColumn(0));
        j.addColumn(new TableColumn(1));
        j.addColumn(new TableColumn(2, 200, new ButtonRenderer(), new ButtonEditor()));
        j.addColumn(new TableColumn(3, 200, new ButtonRenderer(), new ButtonEditor()));

        // adding it to JScrollPane
        JScrollPane sp = new JScrollPane(j);
        f.getContentPane().add(sp);

        jb.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                j = new JTable(new JTableModel(10));
                j.addColumn(new TableColumn(0));
                j.addColumn(new TableColumn(1));
                j.addColumn(new TableColumn(2, 200, new ButtonRenderer(), new ButtonEditor()));
                j.addColumn(new TableColumn(3, 200, new ButtonRenderer(), new ButtonEditor()));

                // adding it to JScrollPane
                JScrollPane sp = new JScrollPane(j);
                f.getContentPane().add(sp);
                f.invalidate();
                f.validate();
//                f.getContentPane().remove(1);
            }
        });
        // Frame Size
        f.setSize(500, 200);
        // Frame Visible = true
        f.setVisible(true);
    }

    // Driver method
    public static void main(String[] args) {
        new JTableExamples();
    }

    private class ButtonRenderer implements TableCellRenderer {
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JButton button = (JButton)value;
            button.setText("before"); //$NON-NLS-1$
            return button;
        }
    }

    private class ButtonEditor extends DefaultCellEditor {
        private static final long serialVersionUID = -6546334664166791132L;

        public ButtonEditor() {
            super(new JTextField());
            this.setClickCountToStart(1);
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            JButton button = (JButton)value;
            button.setText("after"); //$NON-NLS-1$
            button.addActionListener(new AbstractAction() {
                private static final long serialVersionUID = 1L;

                public void actionPerformed(ActionEvent e) {
                    System.out.println("edit!!!!"); //$NON-NLS-1$
                }
            });
            return button;
        }
    }
}
