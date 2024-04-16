package Styler;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class TableStyler {

    public static void styleTable(JTable table) {
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(30);

        // Establecer renderizador de celdas personalizado
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                           boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (isSelected) {
                    c.setBackground(table.getSelectionBackground());
                    c.setForeground(table.getSelectionForeground());
                } else {
                    if (row % 2 == 0) {
                        c.setBackground(new Color(80, 178, 253));
                        c.setForeground(Color.black);
                        c.setFont(new Font("Times new Roman", Font.BOLD, 12));
                    } else {
                        c.setBackground(Color.white);
                        c.setForeground(Color.black);
                        c.setFont(new Font("Times new Roman", Font.BOLD, 12));
                    }
                }
                return c;
            }
        });

        // Establecer el color de fondo de la cabecera de la tabla
        JTableHeader header = table.getTableHeader();
        header.setBackground(Color.lightGray);
        header.setForeground(Color.black);
        header.setFont(header.getFont().deriveFont(Font.BOLD));
        header.setReorderingAllowed(false);
    }
}