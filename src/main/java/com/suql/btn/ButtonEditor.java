package com.suql.btn;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ButtonEditor extends DefaultCellEditor {
    private static final long serialVersionUID = -6546334664166791132L;

    public ButtonEditor() {
        super(new JTextField());
        this.setClickCountToStart(1);
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        JButton button = (JButton)value;
        button.setText("after");
        button.addActionListener(new AbstractAction() {
            private static final long serialVersionUID = 1L;

            public void actionPerformed(ActionEvent e) {
                System.out.println("edit!!!!"); //$NON-NLS-1$
            }
        });
        return button;
    }
}
