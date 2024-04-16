import ConexionSQL.ConexionMySQL;
import Interfaz.EditorVentana.EditorVentana;
import Interfaz.VentanaBuscar.BuscarVentana;
import Interfaz.Login.Login;
import Styler.TableStyler;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.*;
import java.util.Date;
import java.util.Timer;
import javax.swing.table.DefaultTableModel;

public class PaginaPrincipal extends JFrame {
    JPanel panelBGAdmin = new JPanel();
    JPanel panelBGEmpleado = new JPanel();
    JPanel panelMenu = new JPanel();
    JPanel menuAgregarEmpleado = new JPanel();
    JPanel menuEditarEmpleado = new JPanel();
    JPanel menuRegistro = new JPanel();
    JPanel menuNomina = new JPanel();
    JPanel panelCrearEmpleado = new JPanel();
    JPanel panelEditEmpleado = new JPanel();
    JPanel panelPoncharEmpleado = new JPanel();
    JPanel panelAgregarEmpleado = new JPanel();
    JPanel panelFuncionesAdmin = new JPanel();
    JPanel panelBtnEdit = new JPanel();
    JPanel panelBtnBorrar = new JPanel();
    JPanel panelBtnBuscar = new JPanel();
    JPanel panelBtnPonchar = new JPanel();
    JPanel panelBtnRegistro = new JPanel();
    JPanel panelBtnNomina = new JPanel();
    JPanel panelBtnNominaPorHora = new JPanel();
    JPanel panelBtnNominaMensual = new JPanel();
    JLabel imagenTrabajador = new JLabel();
    JLabel imagenEditar = new JLabel();
    JLabel imagenBasurero = new JLabel();
    JLabel imagenLupa = new JLabel();
    JLabel labelCREM = new JLabel("Agregar Empleado");
    JLabel labelEditEm = new JLabel("Gestionar Empleado");
    JLabel labelPoncharEm = new JLabel("Asistencia");
    JLabel labelVerRegistro = new JLabel("Registro");
    JLabel labelFuncionesAdmin = new JLabel("Administrador");
    JLabel labelNomina = new JLabel("Pagos");
    JLabel BtnAgregar = new JLabel("Agregar");
    JLabel BtnEdit = new JLabel("Editar empleado");
    JLabel BtnBuscar = new JLabel("Buscar empleado");
    JLabel BtnBorrar = new JLabel("Eliminar empleado");
    JLabel BtnPonchar = new JLabel("Marcar asistencia");
    JLabel NombreEm = new JLabel("Nombre");
    JLabel ApellidoEm = new JLabel("Apellido");
    JLabel SueldoEm = new JLabel("Sueldo base");
    JLabel DepartEm = new JLabel("Departamento");
    JLabel lblMarcarAsistencia = new JLabel("Marcar Asistencia");
    JLabel lblPoncheIdEmpleado = new JLabel("ID de empleado:");
    JLabel lblTipoPonche = new JLabel("Registro mi:");
    JLabel lblRegistroAsist = new JLabel("Registro de Asistencia");
    JLabel lblInstruccionesNomina = new JLabel("Seleccione el departamento para ver los empleados:");
    JLabel lblNominaPorHora = new JLabel("Por hora");
    JLabel lblNominaMensual = new JLabel("Mensual");
    JComboBox<String> cboDep;
    JComboBox<String> cboNom = new JComboBox<>(new String[]{"Cobros", "Auditoria", "RRHH", "Ventas", "TI", "Departamento legal", "Servicio al cliente"});
    private JRadioButton botonPoncheEntrada;
    private JRadioButton botonPoncheSalida;
    ButtonGroup grupoPonche = new ButtonGroup();
    JTextField txtNombre = new JTextField("Ingrese el nombre del empleado");
    JTextField txtApellido = new JTextField("Ingrese el apellido del empleado");
    JTextField txtSueldo = new JTextField("Ingrese el sueldo del empleado");
    JTextField txtID = new JTextField("Ingrese la ID del empleado");
    JLabel separador1 = new JLabel("———————————————————————————————");
    JLabel separador2 = new JLabel("———————————————————————————————");
    JLabel separador3 = new JLabel("——————————————————————————————————————");
    JLabel relojLabel = new JLabel();
    JLabel TipoDeSuedolbl = new JLabel("Tipo de sueldo");
    JRadioButton BotonsueldoPorHora = new JRadioButton("Por hora");
    JRadioButton BotonsueldoMensual = new JRadioButton("Mensual");
    ButtonGroup grupo = new ButtonGroup();

    private JTable tablaEmpleados;

    private JTable tablaAsistencia;
    private JTable tablaPagos;
    String nuevoNombre,nuevoApellido,nuevoDepartamento, nuevoTipodeSueldo;
    float nuevoSueldo;
    String nombreActual, apellidoActual,  departamentoActual, id_empleado, tipoDeSueldoActual;
    float sueldoActual;


    public PaginaPrincipal(){
        tablaEmpleados = new JTable(obtenerDatos());
        DefaultTableModel modeloNomina = new DefaultTableModel();
        inicializarComponentes();
        frameConfig();
        crearPanelBGAdmin();
        crearPanelBGEmpleado();
        crearPanelMenu();
        crearPanelCREM();
        crearPanelEditEmpleado();
        crearPanelPoncharEmpleado();
        crearPanelBtnRegistro();
        crearPanelFuncionesAdmin();
        crearPanelBtnNomina();
        iconos();
        agregarEventos();
        iniciarReloj();


    }
    public class DatabaseConnection {
        private static final String URL = "jdbc:mysql://localhost:3306/BDEmpleados";
        private static final String USER = "root";
        private static final String PASSWORD = "Mpepen01234*";

        public static Connection getConnection() throws SQLException {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        }
    }

    private void inicializarComponentes() {
        cboDep = new JComboBox<>();
        cargarDepartamentos();
        inicializarTablaPagos();
        cargarDepNom();
        cboNom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String DepSelec = (String) cboNom.getSelectedItem();
                actualizarTablaNomina(DepSelec);
            }
        });
    }

    public void cargarDepartamentos() {
        try (Connection conexion = DatabaseConnection.getConnection();
             Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT DISTINCT DepaEm FROM Empleados")) {
            while (rs.next()) {
                cboDep.addItem(rs.getString("DepaEm"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar departamentos: " + e.getMessage(), "Error de Base de Datos", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void frameConfig(){
        setTitle("Menu");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        setLayout(null);
        add(panelMenu);
        add(panelBGAdmin);
        add(panelBGEmpleado);
        setVisible(true);
    }
    public void crearPanelBGAdmin(){
        panelBGAdmin.setSize(1000, 600);
        panelBGAdmin.setBackground(new Color(50, 158, 253));

        panelBGAdmin.setLayout(null);
        panelBGAdmin.add(menuRegistro);

        panelBGAdmin.setVisible(false);
    }
    public void crearPanelBGEmpleado(){
        panelBGEmpleado.setSize(1000, 600);
        panelBGEmpleado.setBackground(new Color(50, 158, 253));

        panelBGEmpleado.setLayout(null);
    }
    public void crearPanelCREM(){
        panelCrearEmpleado.setBounds(820, 20, 180, 40);
        panelCrearEmpleado.setBackground(new Color(50, 158, 253));

        labelCREM.setFont(new Font("Roboto", Font.BOLD, 16));
        labelCREM.setForeground(Color.white);

        panelCrearEmpleado.add(labelCREM);

        panelBGAdmin.add(panelCrearEmpleado);
    }
    public void agregarEventos(){
        //eventos del boton de agregar empleados en la barra de la derecha
        panelCrearEmpleado.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                panelMenu.setVisible(false);
                menuEditarEmpleado.setVisible(false);
                menuRegistro.setVisible(false);
                menuNomina.setVisible(false);
                menuAgregarEmpleado.setVisible(true);
                crearMenuAgrEm();
                crearPanelAgregarEmpleado();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                panelCrearEmpleado.setBackground(new Color(0, 139, 207));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                panelCrearEmpleado.setBackground(new Color(50, 158, 253));
            }
        });
        //eventos del btextfield nombre
        txtNombre.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                txtNombreMousePressed(evt);
            }

        });
        //eventos del textField apellido

        txtApellido.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                txtApellidoMousePressed(evt);
            }
        });
        //eventos del textField sueldo
        txtSueldo.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                txtSueldoMousePressed(evt);
            }
        });
        //eventos del boton de agregar empleado a la base de datos
        panelAgregarEmpleado.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(txtNombre.getText().isEmpty() || txtNombre.getText().equals("Ingrese el nombre del empleado")){
                    JOptionPane.showMessageDialog(null, "Llenar todos los campos", "ERROR", JOptionPane.ERROR_MESSAGE);
                }else if(txtApellido.getText().isEmpty() || txtApellido.getText().equals("Ingrese el apellido del empleado")){
                    JOptionPane.showMessageDialog(null, "Llenar todos los campos", "ERROR", JOptionPane.ERROR_MESSAGE);
                }else{
                    Calendar calendar = Calendar.getInstance();
                    int year = calendar.get(Calendar.YEAR);

                    String NombreEm = txtNombre.getText();
                    String ApellidoEm = txtApellido.getText();
                    String DepaEm = (String) cboDep.getSelectedItem();
                    float sueldo = Float.parseFloat(txtSueldo.getText());
                    String tipoSueldo = obtenerTipoSueldo();
                    int año_contratacion = year;
                    String ID_Departamento = obtenerIdDepartamento(DepaEm);

                    // Generar la ID del empleado

                    int ultimoNumeroEmpleado = obtenerUltimoNumeroEmpleado(year, ID_Departamento);
                    String numeroEmpleado = String.format("%04d", ultimoNumeroEmpleado + 1);
                    String idEmpleado = year + "-" + ID_Departamento + "-" + numeroEmpleado;

                    insertarUsuario(NombreEm, ApellidoEm, DepaEm, sueldo, tipoSueldo, año_contratacion, ID_Departamento);

                }
                limpiar();
            }
            

            @Override
            public void mouseEntered(MouseEvent e) {
                panelAgregarEmpleado.setBackground(new Color(0, 139, 207));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                panelAgregarEmpleado.setBackground(new Color(50, 158, 253));
            }
        });
        //eventos del boton de Gestionar Empleados en la barra derecha
        panelEditEmpleado.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                panelMenu.setVisible(false);
                menuAgregarEmpleado.setVisible(false);
                menuRegistro.setVisible(false);
                menuNomina.setVisible(false);
                menuEditarEmpleado.setVisible(true);
                crearPanelEliminarEmpleado();
                crearBtnBorrar();
                crearBtnEdit();
                crearBtnBuscar();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                panelEditEmpleado.setBackground(new Color(0, 139, 207));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                panelEditEmpleado.setBackground(new Color(50, 158, 253));
            }
        });
        //eventos del boton de editar empleados
        panelBtnEdit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(tablaEmpleados.getSelectedRow() >= 0){
                        panelEditar();
                        editarEmpleadoEnBaseDeDatos(id_empleado, nuevoNombre, nuevoApellido, nuevoDepartamento, nuevoSueldo, nuevoTipodeSueldo);
                }else{
                    JOptionPane.showMessageDialog(null, "Seleccione un empleado", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                panelBtnEdit.setBackground(new Color(0, 139, 207));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                panelBtnEdit.setBackground(new Color(50, 158, 253));
            }
        });
        //eventos del boton de eliminar empleados
        panelBtnBorrar.addMouseListener(new MouseAdapter() {


            @Override
            public void mouseClicked(MouseEvent e) {
                if (tablaEmpleados.getSelectedRow() >= 0) {
                    int filaSeleccionada = tablaEmpleados.getSelectedRow();
                    String id_empleado = (String) tablaEmpleados.getValueAt(filaSeleccionada, 3);


                    int dialogResult = JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar a " + tablaEmpleados.getValueAt(filaSeleccionada, 0) + "?", "Eliminar Empleado", JOptionPane.YES_NO_OPTION);
                    if (dialogResult == JOptionPane.YES_OPTION) {

                        eliminarEmpleado(id_empleado);


                        ((DefaultTableModel)tablaEmpleados.getModel()).removeRow(filaSeleccionada);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Seleccione un empleado", "ERROR", JOptionPane.ERROR_MESSAGE);
                }

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                panelBtnBorrar.setBackground(new Color(0, 139, 207));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                panelBtnBorrar.setBackground(new Color(50, 158, 253));
            }
        });
        panelBtnBuscar.addMouseListener(new MouseAdapter() {


            @Override
            public void mouseClicked(MouseEvent e) {
                BuscarVentana buscarVentana = new BuscarVentana();
                buscarVentana.setVisible(true);
                buscarVentana.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        String empleadoBuscado = buscarVentana.getEmpleadoBuscado();
                        if (empleadoBuscado != null) {
                            buscarEmpleadoPorId(tablaEmpleados, empleadoBuscado);
                        }
                    }
                });
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                panelBtnBuscar.setBackground(new Color(0, 139, 207));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                panelBtnBuscar.setBackground(new Color(50, 158, 253));
            }
        });
        panelBtnPonchar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (botonPoncheEntrada.isSelected()) {
                    marcarEntrada();
                } else if (botonPoncheSalida.isSelected()) {
                    marcarSalida();
                } else if(txtID.getText().equals("Ingrese la ID del empleado") || txtID.getText().equals("")) {
                    JOptionPane.showMessageDialog(panelMenu, "Por favor proporcione su ID");
                }else {
                    JOptionPane.showMessageDialog(panelMenu, "Por favor seleccione el tipo de ponche (entrada/salida).");
                }
                txtID.setText("Ingrese la ID del empleado");
                txtID.setForeground(Color.lightGray);
                grupoPonche.clearSelection();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                panelBtnPonchar.setBackground(new Color(0, 139, 207));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                panelBtnPonchar.setBackground(new Color(50, 158, 253));
            }
        });
        //evento para que cuando haga click afuera de la tabla, se deseleccione la fila
        menuEditarEmpleado.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (tablaEmpleados.getSelectedRow() != -1) {
                    Point puntoClic = e.getPoint();
                    int fila = tablaEmpleados.rowAtPoint(puntoClic);
                    int columna = tablaEmpleados.columnAtPoint(puntoClic);

                    if (fila == -1 || columna == -1) {
                        tablaEmpleados.clearSelection();
                    }
                }
            }
        });
        panelPoncharEmpleado.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                menuAgregarEmpleado.setVisible(false);
                menuEditarEmpleado.setVisible(false);
                menuRegistro.setVisible(false);
                panelMenu.setVisible(true);
                menuNomina.setVisible(false);
                crearPanelMenu();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                panelPoncharEmpleado.setBackground(new Color(0, 139, 207));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                panelPoncharEmpleado.setBackground(new Color(50, 158, 253));
            }
        });
        panelBtnRegistro.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                panelMenu.setVisible(false);
                menuEditarEmpleado.setVisible(false);
                menuAgregarEmpleado.setVisible(false);
                menuNomina.setVisible(false);
                menuRegistro.setVisible(true);
                crearMenuRegistro();

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                panelBtnRegistro.setBackground(new Color(0, 139, 207));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                panelBtnRegistro.setBackground(new Color(50, 158, 253));
            }
        });
        panelFuncionesAdmin.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Login login = new Login();
                login.setVisible(true);

                login.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        super.windowClosed(e);

                        String nombre = login.getNombreUsuario();
                        String contrasena = login.getContrasena();

                        if (login.verificarCredenciales(nombre, contrasena)) {
                            panelPoncharEmpleado.setVisible(false);
                            mostrarPanelAdmin();
                        } else {
                            JOptionPane.showMessageDialog(null, "Credenciales incorrectas", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });
            }



            @Override
            public void mouseEntered(MouseEvent e) {
                panelFuncionesAdmin.setBackground(new Color(0, 139, 207));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                panelFuncionesAdmin.setBackground(new Color(50, 158, 253));
            }
        });

        panelBtnNomina.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                panelMenu.setVisible(false);
                menuEditarEmpleado.setVisible(false);
                menuAgregarEmpleado.setVisible(false);
                menuRegistro.setVisible(false);
                menuNomina.setVisible(true);
                crearMenuNomina();
                mostrarMenuNomina();

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                panelBtnNomina.setBackground(new Color(0, 139, 207));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                panelBtnNomina.setBackground(new Color(50, 158, 253));
            }
        });
        panelBtnNominaPorHora.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                panelBtnNominaPorHora.setBackground(new Color(0, 139, 207));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                panelBtnNominaPorHora.setBackground(new Color(50, 158, 253));
            }
        });
        panelBtnNominaMensual.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                panelBtnNominaMensual.setBackground(new Color(0, 139, 207));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                panelBtnNominaMensual.setBackground(new Color(50, 158, 253));
            }
        });



        //fin de los eventos
    }

    public void crearPanelMenu(){
        panelMenu.setBounds(20, 20, 800, 600 );
        panelMenu.setBackground(Color.white);


        relojLabel.setBounds(330, 20, 200, 40);
        relojLabel.setFont(new Font("Roboto", Font.BOLD, 36));
        relojLabel.setForeground(new Color(50, 158, 253));

        lblMarcarAsistencia.setBounds(290, 80, 300, 40);
        lblMarcarAsistencia.setFont(new Font("Roboto", Font.BOLD, 28));
        lblMarcarAsistencia.setForeground(new Color(50, 158, 253));

        lblPoncheIdEmpleado.setBounds(40, 150, 250, 40);
        lblPoncheIdEmpleado.setFont(new Font("Roboto", Font.BOLD, 28));

        txtID.setBounds(260, 150, 250, 40);
        txtID.setForeground(Color.lightGray);
        txtID.setFont(new Font("Roboto", Font.PLAIN, 16));
        Border emptyBorder4 = BorderFactory.createEmptyBorder();
        txtID.setBorder(emptyBorder4);

        txtID.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txtID.getText().equals("Ingrese la ID del empleado")) {
                    txtID.setText("");
                    txtID.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txtID.getText().isEmpty()) {
                    txtID.setText("Ingrese la ID del empleado");
                    txtID.setForeground(Color.LIGHT_GRAY);
                }
            }
        });

        lblTipoPonche.setBounds(40, 220, 250, 40);
        lblTipoPonche.setFont(new Font("Roboto", Font.BOLD, 28));

        botonPoncheEntrada = new JRadioButton("Entrada");
        botonPoncheEntrada.setBounds(230, 230, 100, 30);
        botonPoncheEntrada.setFont(new Font("Roboto", Font.BOLD, 16));
        botonPoncheEntrada.setBackground(Color.white);
        grupoPonche.add(botonPoncheEntrada);

        botonPoncheSalida = new JRadioButton("Salida");
        botonPoncheSalida.setBounds(330, 230, 100, 30);
        botonPoncheSalida.setFont(new Font("Roboto", Font.BOLD, 16));
        botonPoncheSalida.setBackground(Color.white);
        grupoPonche.add(botonPoncheSalida);


        panelMenu.add(relojLabel);
        panelMenu.add(lblMarcarAsistencia);
        panelMenu.add(lblPoncheIdEmpleado);
        panelMenu.add(txtID);
        panelMenu.add(lblTipoPonche);
        panelMenu.add(botonPoncheEntrada);
        panelMenu.add(botonPoncheSalida);


        crearBtnPonchar();

        panelMenu.setLayout(null);

    }
    public void crearBtnPonchar(){
        panelBtnPonchar.setBounds(500, 220, 200, 40);
        panelBtnPonchar.setBackground(new Color(50, 158, 253));

        int panelWidth = panelBtnPonchar.getWidth();
        int panelHeight = panelBtnPonchar.getHeight();
        int buttonWidth = 150;
        int buttonHeight = 30;


        int x = (panelWidth - buttonWidth) / 2;
        int y = (panelHeight - buttonHeight) / 2;


        BtnPonchar.setBounds(x, y, buttonWidth, buttonHeight);
        BtnPonchar.setFont(new Font("Roboto", Font.BOLD, 16));
        BtnPonchar.setForeground(Color.white);

        panelBtnEdit.setLayout(null);

        panelMenu.add(panelBtnPonchar);
        panelBtnPonchar.add(BtnPonchar);
    }
    private String obtenerTipoPonche() {
        Enumeration<AbstractButton> buttons = grupoPonche.getElements();
        while (buttons.hasMoreElements()) {
            AbstractButton button = buttons.nextElement();
            if (button.isSelected()) {
                return button.getText();
            }
        }
        return "";
    }

    public void marcarEntrada() {

            String idEmpleado = txtID.getText();
            LocalTime horaEntrada = LocalTime.now();
            LocalTime horaSalida = LocalTime.now();
            Date fecha = new Date();

            marcarAsistencia(idEmpleado, horaEntrada, horaSalida, fecha);
    }


    public void marcarSalida() {
        String idEmpleado = txtID.getText();
        LocalTime horaEntrada = LocalTime.now();
        LocalTime horaSalida = LocalTime.now();
        Date fecha = new Date();

        marcarAsistencia(idEmpleado, horaEntrada, horaSalida, fecha);
    }
    private static final String INSERT_ASISTENCIA_SQL = "INSERT INTO asistencia (id_empleado, h_entrada, h_salida, fecha) VALUES (?, ?, ?, ?)";

    public void marcarAsistencia(String idEmpleado, LocalTime horaEntrada, LocalTime horaSalida, Date fecha) {
        try {
            if (!verificarIdEmpleado(idEmpleado)) {
                JOptionPane.showMessageDialog(null, "La ID del empleado no existe", "ERROR", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Connection conexion = ConexionMySQL.obtenerConexion();
            PreparedStatement preparedStatement = conexion.prepareStatement(INSERT_ASISTENCIA_SQL);
            preparedStatement.setString(1, idEmpleado);

            String horaEntradaFormateada = horaEntrada.toString();
            String horaSalidaFormateada = horaSalida.toString();
            String tipoPonche = obtenerTipoPonche();
            if (tipoPonche.equals("Entrada")) {
                preparedStatement.setString(2, horaEntradaFormateada);
                preparedStatement.setNull(3, Types.TIMESTAMP);
            } else if(tipoPonche.equals("Salida")){
                preparedStatement.setNull(2, Types.TIMESTAMP);
                preparedStatement.setString(3, horaSalidaFormateada);
            }

            preparedStatement.setDate(4, new java.sql.Date(fecha.getTime()));
            preparedStatement.executeUpdate();
            conexion.close();
            JOptionPane.showMessageDialog(null, "Datos guardados con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private boolean verificarIdEmpleado(String idEmpleado) {
        try {
            Connection conexion = ConexionMySQL.obtenerConexion();
            String consultaSQL = "SELECT id_empleado FROM Empleados WHERE id_empleado = ?";
            PreparedStatement preparedStatement = conexion.prepareStatement(consultaSQL);
            preparedStatement.setString(1, idEmpleado);
            ResultSet resultSet = preparedStatement.executeQuery();
            boolean empleadoRegistrado = resultSet.next();
            conexion.close();
            return empleadoRegistrado;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    public DefaultTableModel obtenerDatosAsistencia() {
        DefaultTableModel modeloPonche = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        modeloPonche.addColumn("ID REGISTRO");
        modeloPonche.addColumn("ID EMPLEADO");
        modeloPonche.addColumn("H. Entrada");
        modeloPonche.addColumn("H. Salida");
        modeloPonche.addColumn("Fecha");



        Connection conexion = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            conexion = DatabaseConnection.getConnection();
            String sql = "SELECT id_registro, id_empleado, h_entrada, h_salida, fecha FROM asistencia";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Object[] fila = new Object[5];
                fila[0] = resultSet.getString("id_registro");
                fila[1] = resultSet.getString("id_empleado");
                fila[2] = resultSet.getString("h_entrada");
                fila[3] = resultSet.getString("h_salida");
                fila[4] = resultSet.getString("fecha");
                modeloPonche.addRow(fila);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (conexion != null) conexion.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return modeloPonche;
    }
    public void crearMenuAgrEm(){
        menuAgregarEmpleado.setBounds(20, 20, 800, 600 );
        menuAgregarEmpleado.setBackground(Color.white);

        NombreEm.setBounds(40, 40, 200, 50);
        NombreEm.setFont(new Font("Roboto", Font.BOLD, 24));

        txtNombre.setBounds(40, 80, 380, 50);
        txtNombre.setForeground(Color.lightGray);
        txtNombre.setFont(new Font("Roboto", Font.PLAIN, 14));
        Border emptyBorder = BorderFactory.createEmptyBorder();
        txtNombre.setBorder(emptyBorder);

        txtNombre.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txtNombre.getText().equals("Ingrese el nombre del empleado")) {
                    txtNombre.setText("");
                    txtNombre.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txtNombre.getText().isEmpty()) {
                    txtNombre.setText("Ingrese el nombre del empleado");
                    txtNombre.setForeground(Color.LIGHT_GRAY);
                }
            }
        });

        separador1.setBounds(40, 120, 400, 20);

        ApellidoEm.setBounds(40, 140, 300, 50);
        ApellidoEm.setFont(new Font("Roboto", Font.BOLD, 24));


        txtApellido.setBounds(40, 180, 380, 50);
        txtApellido.setForeground(Color.LIGHT_GRAY);
        txtApellido.setFont(new Font("Roboto", Font.PLAIN, 14));
        Border emptyBorder2 = BorderFactory.createEmptyBorder();
        txtApellido.setBorder(emptyBorder2);

        separador2.setBounds(40, 220, 400, 20);

        SueldoEm.setBounds(40, 240, 300, 50);
        SueldoEm.setFont(new Font("Roboto", Font.BOLD, 24));

        txtSueldo.setBounds(40, 280, 260, 50);
        txtSueldo.setForeground(Color.lightGray);
        txtSueldo.setFont(new Font("Roboto", Font.PLAIN, 14));
        Border emptyBorder3 = BorderFactory.createEmptyBorder();
        txtSueldo.setBorder(emptyBorder3);

        separador3.setBounds(40, 320, 480, 20);

        TipoDeSuedolbl.setBounds(320, 240, 300, 50);
        TipoDeSuedolbl.setFont(new Font("Roboto", Font.BOLD, 24));

        BotonsueldoPorHora.setBounds(320, 290, 100, 30);
        BotonsueldoPorHora.setBackground(Color.white);
        BotonsueldoPorHora.setFont(new Font("Roboto", Font.BOLD, 16));
        grupo.add(BotonsueldoPorHora);

        BotonsueldoMensual.setBounds(420, 290, 100, 30);
        BotonsueldoMensual.setBackground(Color.white);
        BotonsueldoMensual.setFont(new Font("Roboto", Font.BOLD, 16));
        grupo.add(BotonsueldoMensual);

        DepartEm.setBounds(40, 340, 200, 50);
        DepartEm.setFont(new Font("Roboto", Font.BOLD, 24));

        cboDep = new JComboBox<>(new String[]{"Cobros", "Auditoria", "RRHH", "Ventas", "TI", "Departamento legal", "Servicio al cliente"});
        cboDep.setBounds(40, 400, 200, 30);







        menuAgregarEmpleado.setLayout(null);

        menuAgregarEmpleado.add(NombreEm);
        menuAgregarEmpleado.add(ApellidoEm);
        menuAgregarEmpleado.add(DepartEm);
        menuAgregarEmpleado.add(cboDep);
        menuAgregarEmpleado.add(txtNombre);
        menuAgregarEmpleado.add(txtApellido);
        menuAgregarEmpleado.add(separador1);
        menuAgregarEmpleado.add(separador2);
        menuAgregarEmpleado.add(txtSueldo);
        menuAgregarEmpleado.add(SueldoEm);
        menuAgregarEmpleado.add(separador3);
        menuAgregarEmpleado.add(TipoDeSuedolbl);
        menuAgregarEmpleado.add(BotonsueldoMensual);
        menuAgregarEmpleado.add(BotonsueldoPorHora);
        panelBGAdmin.add(menuAgregarEmpleado);
    }
    public void iconos(){
        ImageIcon imageIcon1 = new ImageIcon("D:\\admin.png");
        Image image1 = imageIcon1.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        ImageIcon icon1 = new ImageIcon(image1);
        imagenTrabajador.setIcon(icon1);
        imagenTrabajador.setBounds(600, 0, 200, 200);
        menuAgregarEmpleado.add(imagenTrabajador);

        ImageIcon imageIcon3 = new ImageIcon("D:\\470216.png");
        Image image3 = imageIcon3.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon icon3 = new ImageIcon(image3);
        imagenEditar.setIcon(icon3);
        imagenEditar.setBounds(125, 40, 100, 100);
        menuEditarEmpleado.add(imagenEditar);

        ImageIcon imageIcon4 = new ImageIcon("D:\\5258411.png");
        Image image4 = imageIcon4.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon icon4 = new ImageIcon(image4);
        imagenBasurero.setIcon(icon4);
        imagenBasurero.setBounds(375, 40, 100, 100);
        menuEditarEmpleado.add(imagenBasurero);

        ImageIcon imageIcon5 = new ImageIcon("D:\\lupa.png");
        Image image5 = imageIcon5.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon icon5 = new ImageIcon(image5);
        imagenLupa.setIcon(icon5);
        imagenLupa.setBounds(595, 40, 100, 100);
        menuEditarEmpleado.add(imagenLupa);
    }
    public void iniciarReloj() {
        TimerTask actualizarHoraTask = new TimerTask() {
            @Override
            public void run() {
                actualizarHora();
            }
        };

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(actualizarHoraTask, 0, 1000);
    }

    private void actualizarHora() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        TimeZone timeZone = TimeZone.getTimeZone("America/Santo_Domingo");
        sdf.setTimeZone(timeZone);
        String horaActual = sdf.format(new Date(System.currentTimeMillis()));
        relojLabel.setText(horaActual);
    }
    public void txtNombreMousePressed(MouseEvent evt) {
        txtNombre.setText("");
        txtNombre.setForeground(Color.BLACK);
        if (txtApellido.getText().isEmpty()) {
            txtApellido.setText("Ingrese el apellido del empleado");
            txtApellido.setForeground(Color.LIGHT_GRAY);
        } else if(txtSueldo.getText().isEmpty()){
            txtSueldo.setText("Ingrese el sueldo del empleado");
            txtSueldo.setForeground(Color.lightGray);
        }

    }

    public void txtApellidoMousePressed(MouseEvent evt) {
        txtApellido.setText("");
        txtApellido.setForeground(Color.BLACK);
        if (txtNombre.getText().isEmpty()) {
            txtNombre.setText("Ingrese el nombre del empleado");
            txtNombre.setForeground(Color.LIGHT_GRAY);
        }else if(txtSueldo.getText().isEmpty()){
            txtSueldo.setText("Ingrese el sueldo del empleado");
            txtSueldo.setForeground(Color.lightGray);
        }

    }
    public void txtSueldoMousePressed(MouseEvent evt) {
        txtSueldo.setText("");
        txtSueldo.setForeground(Color.BLACK);
        if (txtApellido.getText().isEmpty()) {
            txtApellido.setText("Ingrese el apellido del empleado");
            txtApellido.setForeground(Color.LIGHT_GRAY);
        } else if(txtNombre.getText().isEmpty()){
            txtNombre.setText("Ingrese el nombre del empleado");
            txtNombre.setForeground(Color.lightGray);
        }

    }
    public void limpiar(){
        txtNombre.setText("Ingrese el nombre del empleado");
        txtNombre.setForeground(Color.lightGray);
        txtApellido.setText("Ingrese el apellido del empleado");
        txtApellido.setForeground(Color.lightGray);
        txtSueldo.setText("Ingrese el sueldo del empleado");
        txtSueldo.setForeground(Color.lightGray);
        grupo.clearSelection();
    }
    public void crearPanelAgregarEmpleado(){
        panelAgregarEmpleado.setBounds(40, 450, 150, 40);
        panelAgregarEmpleado.setBackground(new Color(50, 158, 253));

        BtnAgregar.setBounds(45,0,150, 30);
        BtnAgregar.setFont(new Font("Roboto", Font.BOLD, 16));
        BtnAgregar.setForeground(Color.white);

        panelAgregarEmpleado.setLayout(null);

        menuAgregarEmpleado.add(panelAgregarEmpleado);
        panelAgregarEmpleado.add(BtnAgregar);
    }
    private String obtenerIdDepartamento(String nombreDepartamento) {

        if (nombreDepartamento.equals("Cobros")) {
            return "01";
        } else if (nombreDepartamento.equals("Auditoria")) {
            return "02";
        } else if (nombreDepartamento.equals("RRHH")) {
            return "03";
        } else if (nombreDepartamento.equals("Ventas")) {
            return "04";
        } else if (nombreDepartamento.equals("TI")) {
            return "05";
        } else if (nombreDepartamento.equals("Departamento legal")) {
            return "06";
        } else if (nombreDepartamento.equals("Servicio al cliente")) {
            return "07";
        }

        return "";
    }
    private int obtenerUltimoNumeroEmpleado(int year, String idDepartamento) {
        Connection conexion = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            conexion = DatabaseConnection.getConnection();
            String sql = "SELECT MAX(CAST(SUBSTRING(id_empleado, 9) AS UNSIGNED)) AS max_id FROM Empleados WHERE id_empleado LIKE ?";
            statement = conexion.prepareStatement(sql);
            statement.setString(1, year + "-" + idDepartamento + "-%");
            resultSet = statement.executeQuery();

            if (resultSet != null && resultSet.next()) {
                int maxId = resultSet.getInt("max_id");
                return maxId;
            } else {
                return 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (conexion != null) conexion.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public void insertarUsuario(String NombreEm, String ApellidoEm, String DepaEm, float sueldo, String tipoSueldo, int año_contratacion, String ID_Departamento) {
        Connection conexion = null;
        PreparedStatement statement = null;

        try {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);

            String idDepartamento = obtenerIdDepartamento(DepaEm);

            int ultimoNumeroEmpleado = obtenerUltimoNumeroEmpleado(year, idDepartamento);

            String numeroEmpleado = String.format("%04d", ultimoNumeroEmpleado + 1);

            String idEmpleado = year + "-" + idDepartamento + "-" + numeroEmpleado;
            conexion = DatabaseConnection.getConnection();

            String sql = "INSERT INTO Empleados (id_empleado, NombreEm, ApellidoEm, DepaEm, sueldo, tipoSueldo, año_contratacion, ID_Departamento) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            statement = conexion.prepareStatement(sql);
            statement.setString(1, idEmpleado);
            statement.setString(2, NombreEm);
            statement.setString(3, ApellidoEm);
            statement.setString(4, DepaEm);
            statement.setFloat(5, sueldo);
            statement.setString(6, tipoSueldo);
            statement.setInt(7, año_contratacion);
            statement.setString(8, ID_Departamento);

            statement.executeUpdate();

            JOptionPane.showMessageDialog(null, "Empleado agregado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (statement != null) statement.close();
                if (conexion != null) conexion.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
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

    private void editarEmpleadoEnBaseDeDatos(String id_empleado, String nuevoNombre, String nuevoApellido, String nuevoDepartamento, float nuevoSueldo, String nuevoTipodeSueldo) {
        Connection conexion = null;
        PreparedStatement statement = null;

        try {
            conexion = DatabaseConnection.getConnection();
            String sql = "UPDATE Empleados SET NombreEm = ?, ApellidoEm = ?, DepaEm = ?, tipoSueldo = ?, sueldo = ? WHERE id_empleado = ?";
            statement = conexion.prepareStatement(sql);
            statement.setString(1, nuevoNombre);
            statement.setString(2, nuevoApellido);
            statement.setString(3, nuevoDepartamento);
            statement.setString(4, nuevoTipodeSueldo);
            statement.setFloat(5, nuevoSueldo);
            statement.setString(6, id_empleado);

            int filasActualizadas = statement.executeUpdate();

            if (filasActualizadas > 0) {
                JOptionPane.showMessageDialog(null, "Empleado actualizado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo actualizar el empleado", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar empleado: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) statement.close();
                if (conexion != null) conexion.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void panelEditar(){
        int filaSeleccionada =
                tablaEmpleados.getSelectedRow();
        if (filaSeleccionada >= 0) {
            nombreActual = (String) tablaEmpleados.getValueAt(filaSeleccionada, 0);
            apellidoActual = (String) tablaEmpleados.getValueAt(filaSeleccionada, 1);
            departamentoActual = (String) tablaEmpleados.getValueAt(filaSeleccionada, 2);
            id_empleado = (String) tablaEmpleados.getValueAt(filaSeleccionada, 3);
            sueldoActual = Float.parseFloat(tablaEmpleados.getValueAt(filaSeleccionada, 4).toString());
            tipoDeSueldoActual = (String) tablaEmpleados.getValueAt(filaSeleccionada, 5);
            EditorVentana editar = new EditorVentana(PaginaPrincipal.this, nombreActual, apellidoActual, departamentoActual, sueldoActual, tipoDeSueldoActual);
            editar.setVisible(true);
            nuevoNombre = editar.getNuevoNombre();
            nuevoApellido = editar.getNuevoApellido();
            nuevoDepartamento = editar.getNuevoDepartamento();
            nuevoSueldo = editar.getNuevoSueldo();
            nuevoTipodeSueldo = editar.getNuevoTipoDeSueldo();
            if (nuevoNombre != null && nuevoApellido != null && nuevoDepartamento != null && nuevoTipodeSueldo != null) {
                tablaEmpleados.setValueAt(nuevoNombre, filaSeleccionada, 0);
                tablaEmpleados.setValueAt(nuevoApellido, filaSeleccionada, 1);
                tablaEmpleados.setValueAt(nuevoDepartamento, filaSeleccionada, 2);
                tablaEmpleados.setValueAt(nuevoSueldo, filaSeleccionada, 4);
                tablaEmpleados.setValueAt(nuevoTipodeSueldo, filaSeleccionada, 5);

            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione un empleado para editar", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    public void crearPanelEliminarEmpleado(){
        menuEditarEmpleado.setBounds(20, 20, 800, 600 );
        menuEditarEmpleado.setBackground(Color.white);



            tablaEmpleados = new JTable(obtenerDatos());
            TableStyler.styleTable(tablaEmpleados);
            JScrollPane scrollPane = new JScrollPane(tablaEmpleados);
            scrollPane.setBounds(10, 200, 780, 330);

            Border border = BorderFactory.createLineBorder(Color.BLACK);
            scrollPane.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(15, 15, 15, 15)));
            scrollPane.setWheelScrollingEnabled(true);


            menuEditarEmpleado.add(scrollPane);


        menuEditarEmpleado.setLayout(null);
        panelBGAdmin.add(menuEditarEmpleado);
    }
    public void crearPanelEditEmpleado(){
        panelEditEmpleado.setBounds(820, 70, 180, 40);
        panelEditEmpleado.setBackground(new Color(50, 158, 253));

        labelEditEm.setFont(new Font("Roboto", Font.BOLD, 16));
        labelEditEm.setForeground(Color.white);

        panelEditEmpleado.add(labelEditEm);

        panelBGAdmin.add(panelEditEmpleado);
    }
    public void crearPanelBtnRegistro(){
        panelBtnRegistro.setBounds(820, 120, 180, 40);
        panelBtnRegistro.setBackground(new Color(50, 158, 253));

        labelVerRegistro.setFont(new Font("Roboto", Font.BOLD, 16));
        labelVerRegistro.setForeground(Color.white);

        panelBtnRegistro.add(labelVerRegistro);

        panelBGAdmin.add(panelBtnRegistro);
    }
    public void crearPanelFuncionesAdmin(){
        panelFuncionesAdmin.setBounds(820, 70, 180, 40);
        panelFuncionesAdmin.setBackground(new Color(50, 158, 253));

        labelFuncionesAdmin.setFont(new Font("Roboto", Font.BOLD, 16));
        labelFuncionesAdmin.setForeground(Color.white);

        panelFuncionesAdmin.add(labelFuncionesAdmin);

        panelBGEmpleado.add(panelFuncionesAdmin);
    }
    public void crearMenuRegistro(){
        menuRegistro.setBounds(20, 20, 800, 600 );
        menuRegistro.setBackground(Color.white);

        lblRegistroAsist.setBounds(250, 40, 400, 50);
        lblRegistroAsist.setFont(new Font("Roboto", Font.BOLD, 32));
        lblRegistroAsist.setForeground(new Color(50, 158, 253));


            tablaAsistencia = new JTable(obtenerDatosAsistencia());
            TableStyler.styleTable(tablaAsistencia);
            JScrollPane scrollPane = new JScrollPane(tablaAsistencia);
            scrollPane.setBounds(10, 200, 780, 330);

            Border border = BorderFactory.createLineBorder(Color.BLACK);
            scrollPane.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(15, 15, 15, 15)));

            menuRegistro.add(scrollPane);



        menuRegistro.setLayout(null);

        menuRegistro.add(lblRegistroAsist);
    }
    public DefaultTableModel obtenerDatos() {
        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        modelo.addColumn("Nombre");
        modelo.addColumn("Apellido");
        modelo.addColumn("Departamento");
        modelo.addColumn("ID");
        modelo.addColumn("Sueldo");
        modelo.addColumn("Tipo de Sueldo");


        Connection conexion = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            conexion = DatabaseConnection.getConnection();
            String sql = "SELECT NombreEm, ApellidoEm, DepaEm, id_empleado, sueldo, tipoSueldo FROM Empleados";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Object[] fila = new Object[6];
                fila[0] = resultSet.getString("NombreEm");
                fila[1] = resultSet.getString("ApellidoEm");
                fila[2] = resultSet.getString("DepaEm");
                fila[3] = resultSet.getString("id_empleado");
                fila[4] = resultSet.getString("sueldo");
                fila[5] = resultSet.getString("tipoSueldo");
                modelo.addRow(fila);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (conexion != null) conexion.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return modelo;
    }
    public void eliminarEmpleado(String idEmpleado) {
        Connection conexion = null;
        PreparedStatement statement = null;

        try {
            conexion = DatabaseConnection.getConnection();
            String sqlDeleteAsistencia = "DELETE FROM asistencia WHERE id_empleado = ?";
            statement = conexion.prepareStatement(sqlDeleteAsistencia);
            statement.setString(1, idEmpleado);
            statement.executeUpdate();

            String sqlDeleteEmpleado = "DELETE FROM Empleados WHERE id_empleado = ?";
            statement = conexion.prepareStatement(sqlDeleteEmpleado);
            statement.setString(1, idEmpleado);
            statement.executeUpdate();

            JOptionPane.showMessageDialog(null, "Empleado eliminado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar empleado: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) statement.close();
                if (conexion != null) conexion.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public void crearBtnEdit(){
        panelBtnEdit.setBounds(100, 150, 200, 40);
        panelBtnEdit.setBackground(new Color(50, 158, 253));

        int panelWidth = panelBtnEdit.getWidth();
        int panelHeight = panelBtnEdit.getHeight();
        int buttonWidth = 150;
        int buttonHeight = 30;


        int x = (panelWidth - buttonWidth) / 2;
        int y = (panelHeight - buttonHeight) / 2;


        BtnEdit.setBounds(x, y, buttonWidth, buttonHeight);
        BtnEdit.setFont(new Font("Roboto", Font.BOLD, 16));
        BtnEdit.setForeground(Color.white);

        panelBtnEdit.setLayout(null);

        menuEditarEmpleado.add(panelBtnEdit);
        panelBtnEdit.add(BtnEdit);
    }
    public void crearBtnBorrar(){
        panelBtnBorrar.setBounds(325, 150, 200, 40);
        panelBtnBorrar.setBackground(new Color(50, 158, 253));

        int panelWidth = panelBtnBorrar.getWidth();
        int panelHeight = panelBtnBorrar.getHeight();
        int buttonWidth = 150;
        int buttonHeight = 30;


        int x = (panelWidth - buttonWidth) / 2;
        int y = (panelHeight - buttonHeight) / 2;


        BtnBorrar.setBounds(x, y, buttonWidth, buttonHeight);
        BtnBorrar.setFont(new Font("Roboto", Font.BOLD, 16));
        BtnBorrar.setForeground(Color.white);

        panelBtnBorrar.setLayout(null);

        menuEditarEmpleado.add(panelBtnBorrar);
        panelBtnBorrar.add(BtnBorrar);

    }
    public void crearBtnBuscar(){
        panelBtnBuscar.setBounds(550, 150, 200, 40);
        panelBtnBuscar.setBackground(new Color(50, 158, 253));

        int panelWidth = panelBtnBuscar.getWidth();
        int panelHeight = panelBtnBuscar.getHeight();
        int buttonWidth = 150;
        int buttonHeight = 30;


        int x = (panelWidth - buttonWidth) / 2;
        int y = (panelHeight - buttonHeight) / 2;


        BtnBuscar.setBounds(x, y, buttonWidth, buttonHeight);
        BtnBuscar.setFont(new Font("Roboto", Font.BOLD, 16));
        BtnBuscar.setForeground(Color.white);

        panelBtnBuscar.setLayout(null);

        menuEditarEmpleado.add(panelBtnBuscar);
        panelBtnBuscar.add(BtnBuscar);
    }
    public static void buscarEmpleadoPorId(JTable table, String id) {

        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql;
            PreparedStatement statement;


            if (id.contains("-")) {

                sql = "SELECT NombreEm, ApellidoEm, DepaEm, id_empleado, sueldo, tipoSueldo FROM Empleados WHERE id_empleado = ?";
                statement = conn.prepareStatement(sql);
                statement.setString(1, id);
            } else if (!id.equals("")){
                sql = "SELECT NombreEm, ApellidoEm, DepaEm, id_empleado, sueldo, tipoSueldo FROM Empleados WHERE ID_Departamento = ?";
                statement = conn.prepareStatement(sql);
                statement.setString(1, id);
            } else{
                sql = "SELECT NombreEm, ApellidoEm, DepaEm, id_empleado, sueldo, tipoSueldo FROM Empleados";
                statement = conn.prepareStatement(sql);
            }

            ResultSet resultSet = statement.executeQuery();

            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);

            if (!resultSet.isBeforeFirst()) {
                JOptionPane.showMessageDialog(null, "No se encontraron empleados para la ID proporcionada.", "ERROR", JOptionPane.ERROR_MESSAGE);
                return;
            }

            while (resultSet.next()) {
                String nombre = resultSet.getString("NombreEm");
                String apellido = resultSet.getString("ApellidoEm");
                String departamento = resultSet.getString("DepaEm");
                String id_empleado = resultSet.getString("id_empleado");
                double sueldo = resultSet.getDouble("sueldo");
                String tipoSueldo = resultSet.getString("tipoSueldo");

                model.addRow(new Object[]{nombre, apellido, departamento, id_empleado, sueldo, tipoSueldo});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void crearPanelPoncharEmpleado(){
        panelPoncharEmpleado.setBounds(820, 20, 180, 40);
        panelPoncharEmpleado.setBackground(new Color(50, 158, 253));

        labelPoncharEm.setFont(new Font("Roboto", Font.BOLD, 16));
        labelPoncharEm.setForeground(Color.white);

        panelPoncharEmpleado.add(labelPoncharEm);

        panelBGEmpleado.add(panelPoncharEmpleado);
    }

    public void mostrarPanelAdmin() {
        panelBGEmpleado.setVisible(false);
        panelBGAdmin.setVisible(true);
    }

    public void crearMenuNomina(){
        menuNomina.setBounds(20, 20, 800, 600 );
        menuNomina.setBackground(Color.white);

        lblInstruccionesNomina.setBounds(15,20,500,20);
        lblInstruccionesNomina.setFont(new Font("Roboto", Font.BOLD, 18));


        cboNom.setBounds(40, 120, 200, 30);



        menuNomina.setLayout(null);

        menuNomina.add(lblInstruccionesNomina);
        menuNomina.add(cboNom);
        panelBGAdmin.add(menuNomina);
    }

    public void crearPanelBtnNomina(){
        panelBtnNomina.setBounds(820, 170, 180, 40);
        panelBtnNomina.setBackground(new Color(50, 158, 253));

        labelNomina.setFont(new Font("Roboto", Font.BOLD, 16));
        labelNomina.setForeground(Color.white);

        panelBtnNomina.add(labelNomina);

        panelBGAdmin.add(panelBtnNomina);
    }

    public DefaultTableModel obtenerDatosEmpleadosPorDepartamentoNomina(String IDDep) {
        DefaultTableModel modeloNomina = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        modeloNomina.addColumn("Nombre");
        modeloNomina.addColumn("Apellido");
        modeloNomina.addColumn("ID");
        modeloNomina.addColumn("Sueldo");
        modeloNomina.addColumn("Tipo de Sueldo");

        Connection conexion = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            conexion = DatabaseConnection.getConnection();
            String sql = "SELECT NombreEm, ApellidoEm, id_empleado, sueldo, tipoSueldo FROM Empleados WHERE DepaEm = ?";
            statement = conexion.prepareStatement(sql);
            statement.setString(1, IDDep);
            resultSet = statement.executeQuery();
            System.out.println("hhola");
            while (resultSet.next()) {
                Object[] fila = new Object[5];
                fila[0] = resultSet.getString("NombreEm");
                fila[1] = resultSet.getString("ApellidoEm");
                fila[2] = resultSet.getString("id_empleado");
                fila[3] = resultSet.getString("sueldo");
                fila[4] = resultSet.getString("tipoSueldo");
                modeloNomina.addRow(fila);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (conexion != null) conexion.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return modeloNomina;
    }
    private void cargarDepNom() {
        try (Connection conexion = DatabaseConnection.getConnection();
             Statement statement = conexion.createStatement();
             ResultSet resulset = statement.executeQuery("SELECT DISTINCT DepaEm FROM Empleados")) {
            while (resulset.next()) {
                cboNom.addItem(resulset.getString("DepaEm"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar departamentos: " + e.getMessage(), "Error de Base de Datos", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarTablaNomina(String IDDep) {
        if (IDDep == null) return;

        DefaultTableModel modeloNomina = (DefaultTableModel) tablaPagos.getModel();
        modeloNomina.setRowCount(0);

        try (Connection conexion = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conexion.prepareStatement("SELECT NombreEm, ApellidoEm, id_empleado, sueldo, tipoSueldo FROM Empleados WHERE DepaEm = ?")) {
            pstmt.setString(1, IDDep);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                modeloNomina.addRow(new Object[]{
                        rs.getString("NombreEm"),
                        rs.getString("ApellidoEm"),
                        rs.getString("id_empleado"),
                        rs.getDouble("sueldo"),
                        rs.getString("tipoSueldo")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar empleados: " + e.getMessage(), "Error de Base de Datos", JOptionPane.ERROR_MESSAGE);
        }
    }
    public void inicializarTablaPagos() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Nombre");
        modelo.addColumn("Apellido");
        modelo.addColumn("ID");
        modelo.addColumn("Sueldo");
        modelo.addColumn("Tipo de Sueldo");

        tablaPagos = new JTable(modelo);
        TableStyler.styleTable(tablaPagos);
        JScrollPane scrollPane = new JScrollPane(tablaPagos);
        Border border = BorderFactory.createLineBorder(Color.BLACK);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(15, 15, 15, 15)));
        scrollPane.setWheelScrollingEnabled(true);
        scrollPane.setBounds(10, 250, 780, 280);
        menuNomina.add(scrollPane);
    }

    public void cargarDatosEnTablaPagos() {
        DefaultTableModel model = (DefaultTableModel) tablaPagos.getModel();
        model.setRowCount(0);
    }

    public void mostrarMenuNomina() {
        inicializarTablaPagos();
        cargarDatosEnTablaPagos();
        menuNomina.setVisible(true);
    }

    public static void main(String[] args) {
        PaginaPrincipal paginaPrincipal = new PaginaPrincipal();
    }
}
