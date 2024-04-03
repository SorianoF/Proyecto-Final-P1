import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

public class PaginaPrincipal extends JFrame {
    JPanel panelBG = new JPanel();

    JPanel panelMenu = new JPanel();
    JPanel menuAgregarEmpleado = new JPanel();
    JPanel panelCrearEmpleado = new JPanel();
    JPanel panelAgregarEmpleado = new JPanel();
    JPanel panelVerEmpleados = new JPanel();
    JLabel imagen1 = new JLabel();
    JLabel imagen2 = new JLabel();
    JLabel BtnCREM = new JLabel("Agregar Empleado");
    JLabel BtnAgregar = new JLabel("Agregar");
    JLabel NombreEm = new JLabel("Nombre");
    JLabel ApellidoEm = new JLabel("Apellido");
    JLabel DepartEm = new JLabel("Departamento");
    JComboBox<String> cboDep;
    JTextField txtNombre = new JTextField("Ingrese el nombre del empleado");
    JTextField txtApellido = new JTextField("Ingrese el apellido del empleado");
    JLabel separador1 = new JLabel("———————————————————————————————");
    JLabel separador2 = new JLabel("———————————————————————————————");
    JLabel relojLabel = new JLabel();
    private static final String URL = "jdbc:mysql://localhost:3306/BDEmpleados";
    private static final String USER = "root";
    private static final String PASSWORD = "Mpepen01234*";

    public PaginaPrincipal(){
        crearPanelBG();
        crearPanelMenu();
        frameConfig();
        crearPanelCREM();
        iconos();
        agregarEventos();
        iniciarReloj();

    }
    public void frameConfig(){
        setTitle("Menu");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        setLayout(null);
        add(panelMenu);
        add(panelBG);
        setVisible(true);
    }
    public void crearPanelBG(){
        panelBG.setSize(1000, 600);
        panelBG.setBackground(new Color(50, 158, 253));

        panelBG.setLayout(null);
    }
    public void crearPanelCREM(){
        panelCrearEmpleado.setBounds(820, 20, 180, 40);
        panelCrearEmpleado.setBackground(new Color(50, 158, 253));

        BtnCREM.setFont(new Font("Roboto", Font.BOLD, 16));
        BtnCREM.setForeground(Color.white);

        panelCrearEmpleado.add(BtnCREM);

        panelBG.add(panelCrearEmpleado);
    }
    public void agregarEventos(){
        panelCrearEmpleado.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                panelMenu.setVisible(false);
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
        txtNombre.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                txtNombreMousePressed(evt);
            }

        });

        txtApellido.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                txtContraMousePressed(evt);
            }
        });
        panelAgregarEmpleado.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(txtNombre.getText().isEmpty() || txtNombre.getText().equals("Ingrese el nombre del empleado")){
                    JOptionPane.showMessageDialog(null, "Llenar todos los campos", "ERROR", JOptionPane.ERROR_MESSAGE);
                }else if(txtApellido.getText().isEmpty() || txtApellido.getText().equals("Ingrese el apellido del empleado")){
                    JOptionPane.showMessageDialog(null, "Llenar todos los campos", "ERROR", JOptionPane.ERROR_MESSAGE);
                }else{
                    String NombreEm = txtNombre.getText();
                    String ApellidoEm = txtApellido.getText();
                    String DepaEm = (String) cboDep.getSelectedItem();

                    insertarUsuario(NombreEm, ApellidoEm, DepaEm);

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
    }

    public void crearPanelMenu(){
        panelMenu.setBounds(20, 20, 800, 600 );
        panelMenu.setBackground(Color.white);
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

        DepartEm.setBounds(40, 240, 200, 50);
        DepartEm.setFont(new Font("Roboto", Font.BOLD, 24));

        cboDep = new JComboBox<>(new String[]{"Cobros", "Auditoria", "RRHH", "Ventas", "TI", "Departamento legal", "Servicio al cliente"});
        cboDep.setBounds(40, 300, 200, 30);

        relojLabel.setBounds(650, 485, 300, 50);
        relojLabel.setFont(new Font("Roboto", Font.BOLD, 32));





        menuAgregarEmpleado.setLayout(null);

        menuAgregarEmpleado.add(NombreEm);
        menuAgregarEmpleado.add(ApellidoEm);
        menuAgregarEmpleado.add(DepartEm);
        menuAgregarEmpleado.add(cboDep);
        menuAgregarEmpleado.add(txtNombre);
        menuAgregarEmpleado.add(txtApellido);
        menuAgregarEmpleado.add(separador1);
        menuAgregarEmpleado.add(separador2);
        menuAgregarEmpleado.add(relojLabel);
        panelBG.add(menuAgregarEmpleado);
    }
    public void iconos(){
        ImageIcon imageIcon1 = new ImageIcon("D:\\admin.png");
        Image image1 = imageIcon1.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        ImageIcon icon1 = new ImageIcon(image1);
        imagen1.setIcon(icon1);
        imagen1.setBounds(600, 0, 200, 200);
        menuAgregarEmpleado.add(imagen1);

        ImageIcon imageIcon2 = new ImageIcon("D:\\109613.png");
        Image image2 = imageIcon2.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        ImageIcon icon2 = new ImageIcon(image2);
        imagen2.setIcon(icon2);
        imagen2.setBounds(575, 475, 60, 60);
        menuAgregarEmpleado.add(imagen2);
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
        }

    }

    public void txtContraMousePressed(MouseEvent evt) {
        txtApellido.setText("");
        txtApellido.setForeground(Color.BLACK);
        if (txtNombre.getText().isEmpty()) {
            txtNombre.setText("Ingrese el nombre del empleado");
            txtNombre.setForeground(Color.LIGHT_GRAY);
        }

    }
    public void limpiar(){
        txtNombre.setText("Ingrese el nombre del empleado");
        txtNombre.setForeground(Color.lightGray);
        txtApellido.setText("Ingrese el apellido del empleado");
        txtApellido.setForeground(Color.lightGray);
    }
    public void crearPanelAgregarEmpleado(){
        panelAgregarEmpleado.setBounds(40, 380, 150, 40);
        panelAgregarEmpleado.setBackground(new Color(50, 158, 253));

        BtnAgregar.setBounds(45,0,150, 30);
        BtnAgregar.setFont(new Font("Roboto", Font.BOLD, 16));
        BtnAgregar.setForeground(Color.white);

        panelAgregarEmpleado.setLayout(null);

        menuAgregarEmpleado.add(panelAgregarEmpleado);
        panelAgregarEmpleado.add(BtnAgregar);
    }
    public void insertarUsuario(String NombreEm, String ApellidoEm, String DepaEm) {
        Connection conexion = null;
        PreparedStatement statement = null;



        try {
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);

            String sql = "INSERT INTO Empleados (NombreEm, ApellidoEm, DepaEm) VALUES (?, ?, ?)";
            statement = conexion.prepareStatement(sql);
            statement.setString(1, NombreEm);
            statement.setString(2, ApellidoEm);
            statement.setString(3, DepaEm);

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


    public static void main(String[] args) {
        PaginaPrincipal paginaPrincipal = new PaginaPrincipal();
    }
}
