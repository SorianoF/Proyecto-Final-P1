package Interfaz.VentanaBuscar;

import javax.swing.*;
import java.awt.*;

public class BuscarVentana extends JFrame {
    JTextField txtID = new JTextField();
    JLabel ID = new JLabel("ID del empleado");
    JLabel depart = new JLabel("para buscar por departamento");
    JLabel depart2 = new JLabel("solo poner la ID del departamento");
    JLabel depart3 = new JLabel("Ejemplo: 01");
    JButton boton = new JButton("Buscar");
    String empleadoBuscado;
    public BuscarVentana(){
        setSize(400, 400);
        setLocationRelativeTo(null);
        setResizable(false);



        ID.setBounds(10, 20, 200, 40);
        ID.setFont(new Font("Roboto", Font.BOLD, 16));

        txtID.setBounds(10, 80, 200, 40);

        boton.setBounds(10, 140, 120, 40);

        depart.setBounds(10, 200, 200, 40);
        depart2.setBounds(10, 220, 200, 40);
        depart3.setBounds(10, 240, 200, 40);

        add(ID);
        add(txtID);
        add(boton);
        add(depart);
        add(depart2);
        add(depart3);
        setLayout(null);
        setVisible(true);

        boton.addActionListener(e -> {
            empleadoBuscado = txtID.getText();

            dispose();
        });

    }

    public String getEmpleadoBuscado() {
        return empleadoBuscado;
    }

    public static void main(String[] args) {
        BuscarVentana buscarVentana = new BuscarVentana();
    }
}
