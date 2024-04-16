package Interfaz.EditorVentana;

import javax.swing.*;
import java.awt.*;
import java.util.Enumeration;

public class EditorVentana extends JDialog {
        private String nuevoNombre;
        private String nuevoApellido;
        private String nuevoDepartamento;
        private float nuevoSueldo;
        private String nuevoTipoDeSueldo;
        private JTextField txtNombre;
        private JComboBox<String> cboDepartamento;
        private JTextField txtApellido;
        private JTextField txtSueldo;
        private JRadioButton botonPorHora;
        private JRadioButton botonMensual;
         ButtonGroup grupo = new ButtonGroup();
        private JButton btnGuardar;

        public EditorVentana(JFrame parent, String nombreActual, String apellidoActual, String departamentoActual, float sueldoActual, String tipoDeSueldoActual) {
            super(parent, "Editar empleado", true);
            setSize(400, 400);
            setLocationRelativeTo(null);
            setResizable(false);

            setLayout(null);

            JLabel lblNombre = new JLabel("Nombre:");
            lblNombre.setBounds(10, 10, 150, 30);
            lblNombre.setFont(new Font("Roboto", Font.BOLD, 16));

            txtNombre = new JTextField(nombreActual);
            txtNombre.setBounds(170, 10, 150, 30);

            JLabel lblApellido = new JLabel("Apellido:");
            lblApellido.setFont(new Font("Roboto", Font.BOLD, 16));
            lblApellido.setBounds(10, 50, 150, 30);

            txtApellido = new JTextField(apellidoActual);
            txtApellido.setBounds(170, 50, 150, 30);

            JLabel lblDepartamento = new JLabel("Departamento:");
            lblDepartamento.setBounds(10, 90, 150, 30);
            lblDepartamento.setFont(new Font("Roboto", Font.BOLD, 16));

            cboDepartamento = new JComboBox<>(new String[]
                    {"Cobros", "Auditoria", "RRHH", "Ventas", "TI", "Departamento legal", "Servicio al cliente"});
            cboDepartamento.setSelectedItem(departamentoActual);
            cboDepartamento.setBounds(170, 90, 150, 20);

            JLabel lblSueldo = new JLabel("Sueldo:");
            lblSueldo.setBounds(10, 130, 150, 30);
            lblSueldo.setFont(new Font("Roboto", Font.BOLD, 16));

            txtSueldo = new JTextField(String.valueOf(sueldoActual));
            txtSueldo.setBounds(170, 130, 150, 30);

            JLabel lblTipoSUELDO = new JLabel("tipo:");
            lblTipoSUELDO.setBounds(10, 170, 150, 30);
            lblTipoSUELDO.setFont(new Font("Roboto", Font.BOLD, 16));

            botonPorHora = new JRadioButton("Por hora");
            botonPorHora.setBounds(170, 170, 100, 30);
            botonPorHora.setFont(new Font("Roboto", Font.BOLD, 12));
            grupo.add(botonPorHora);

            botonMensual = new JRadioButton("Mensual");
            botonMensual.setBounds(250, 170, 100, 30);
            botonMensual.setFont(new Font("Roboto", Font.BOLD, 12));
            grupo.add(botonMensual);

            btnGuardar = new JButton("Guardar");
            btnGuardar.setBounds(170, 210, 150, 30);

            add(lblNombre);
            add(txtNombre);
            add(lblApellido);
            add(txtApellido);
            add(lblDepartamento);
            add(cboDepartamento);
            add(btnGuardar);
            add(lblSueldo);
            add(lblTipoSUELDO);
            add(txtSueldo);
            add(botonMensual);
            add(botonPorHora);

            btnGuardar.addActionListener(e -> {
                nuevoNombre = txtNombre.getText();
                nuevoApellido = txtApellido.getText();
                nuevoSueldo = Float.parseFloat(txtSueldo.getText());
                nuevoTipoDeSueldo = obtenerTipoSueldo();
                nuevoDepartamento = cboDepartamento.getSelectedItem().toString();


                setVisible(false);
            });
        }
        public String getNuevoNombre() {
            return nuevoNombre;
        }
        public String getNuevoApellido() {
            return nuevoApellido;
        }
        public String getNuevoDepartamento() {
            return nuevoDepartamento;
        }

    public float getNuevoSueldo() {
        return nuevoSueldo;
    }

    public String getNuevoTipoDeSueldo() {
        return nuevoTipoDeSueldo;
    }

    private String obtenerTipoSueldo() {
        Enumeration<AbstractButton> buttons = grupo.getElements();
        while (buttons.hasMoreElements()) {
            AbstractButton button = buttons.nextElement();
            if (button.isSelected()) {
                return button.getText();
            }
        }
        return "";
    }
    }

