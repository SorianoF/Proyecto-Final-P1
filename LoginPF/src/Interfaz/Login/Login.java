package Interfaz.Login;

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
import java.sql.ResultSet;


public class Login extends JFrame {
    JPanel panelbgLogin = new JPanel();
    JPanel panelLogoLogin = new JPanel();
    JPanel panelPrincipalLogin = new JPanel();
    JPanel panelENTRARLogin = new JPanel();
    JPanel panelREGISTRARSELogin = new JPanel();
    JPanel panelCREARLogin = new JPanel();
    JPanel panelVOLVERLogin = new JPanel();
    JLabel imagen1 = new JLabel();
    JLabel SRH = new JLabel("Sistema RH");

    JLabel Nombre = new JLabel("USUARIO");
    JLabel log = new JLabel("INICIAR SESIÓN");
    JLabel Contra = new JLabel("CONTRASEÑA");
    JLabel botonENTRAR = new JLabel("ENTRAR");
    JLabel botonREGISTRARSE = new JLabel("REGISTRARSE");
    JLabel botonCREAR = new JLabel("CREAR");
    JLabel botonVOLVER = new JLabel("<-");
    JTextField txtNombre = new JTextField("Ingrese su nombre de usuario");
    JPasswordField txtContra = new JPasswordField("********");
    JLabel separador1 = new JLabel("———————————————————————————————");
    JLabel separador2 = new JLabel("———————————————————————————————");
    private static final String URL = "jdbc:mysql://localhost:3306/BDUsuarios";
    private static final String USER = "root";
    private static final String PASSWORD = "Mpepen01234*";

    public Login(){
        crearPanelBG();
        crearPanelLOGO();
        crearPanelPRINCIPAL();
        crearPanelENTRAR();
        agregarEventos();
        iconos();
        frameConfig();

    }
    public void crearPanelBG(){
        panelbgLogin.setBounds(0,0,800, 500);
        panelbgLogin.setBackground(Color.white);
    }
    public void crearPanelLOGO(){
        panelLogoLogin.setBounds(500,0, 300, 500);
        panelLogoLogin.setBackground(new Color(50, 158, 253));



        SRH.setBounds(80, 200, 300, 100);
        SRH.setFont(new Font("Roboto", Font.BOLD, 24));
        SRH.setForeground(Color.white);



        panelLogoLogin.setLayout(null);
        panelLogoLogin.add(SRH);
    }
    public void crearPanelPRINCIPAL(){
        panelPrincipalLogin.setBounds(0, 0, 500, 500);
        panelPrincipalLogin.setBackground(Color.white);

        log.setBounds(40, 60, 350, 50);
        log.setFont(new Font("Roboto", Font.BOLD, 30));

        Nombre.setBounds(40, 130, 200, 50);
        Nombre.setFont(new Font("Roboto", Font.BOLD, 24));

        txtNombre.setBounds(40, 170, 380, 50);
        txtNombre.setForeground(Color.lightGray);
        txtNombre.setFont(new Font("Roboto", Font.PLAIN, 14));
        Border emptyBorder = BorderFactory.createEmptyBorder();
        txtNombre.setBorder(emptyBorder);

        txtNombre.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txtNombre.getText().equals("Ingrese su nombre de usuario")) {
                    txtNombre.setText("");
                    txtNombre.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txtNombre.getText().isEmpty()) {
                    txtNombre.setText("Ingrese su nombre de usuario");
                    txtNombre.setForeground(Color.LIGHT_GRAY);
                }
            }
        });

        separador1.setBounds(40, 210, 400, 20);

        Contra.setBounds(40, 230, 300, 50);
        Contra.setFont(new Font("Roboto", Font.BOLD, 24));


        txtContra.setBounds(40, 270, 380, 50);
        txtContra.setForeground(Color.LIGHT_GRAY);
        txtContra.setFont(new Font("Roboto", Font.PLAIN, 14));
        Border emptyBorder2 = BorderFactory.createEmptyBorder();
        txtContra.setBorder(emptyBorder2);

        separador2.setBounds(40, 310, 400, 20);


        panelPrincipalLogin.setLayout(null);

        panelPrincipalLogin.add(log);
        panelPrincipalLogin.add(Nombre);
        panelPrincipalLogin.add(txtNombre);
        panelPrincipalLogin.add(separador1);
        panelPrincipalLogin.add(separador2);
        panelPrincipalLogin.add(Contra);
        panelPrincipalLogin.add(txtContra);

    }
    public void limpiar(){
        txtNombre.setText("Ingrese su nombre de usuario");
        txtNombre.setForeground(Color.lightGray);
        txtContra.setText("********");
        txtContra.setForeground(Color.lightGray);
    }


    public void crearPanelENTRAR() {

        panelENTRARLogin.setBounds(40, 360, 150, 50);
        panelENTRARLogin.setBackground(new Color(50, 158, 253));


        botonENTRAR.setForeground(Color.white);
        botonENTRAR.setFont(new Font("Roboto", Font.BOLD, 24));
        botonENTRAR.setBounds(8, 40, 60, 40);


        panelENTRARLogin.add(botonENTRAR);
        panelPrincipalLogin.add(panelENTRARLogin);
    }
   /* public void crearPanelREGISTRARSE() {

        panelREGISTRARSELogin.setBounds(228, 360, 200, 50);
        panelREGISTRARSELogin.setBackground(new Color(50, 158, 253));


        botonREGISTRARSE.setForeground(Color.white);
        botonREGISTRARSE.setFont(new Font("Roboto", Font.BOLD, 24));
        botonREGISTRARSE.setBounds(8, 40, 60, 40);


        panelREGISTRARSELogin.add(botonREGISTRARSE);
        panelPrincipalLogin.add(panelREGISTRARSELogin);
    }

    public void crearPanelCREAR() {

        panelCREARLogin.setBounds(40, 360, 150, 50);
        panelCREARLogin.setBackground(new Color(50, 158, 253));


        botonCREAR.setForeground(Color.white);
        botonCREAR.setFont(new Font("Roboto", Font.BOLD, 24));
        botonCREAR.setBounds(8, 40, 60, 40);


        panelCREARLogin.add(botonCREAR);
        panelPrincipalLogin.add(panelCREARLogin);
    }
    public void crearPanelVOLVER() {

        panelVOLVERLogin.setBounds(0, 0, 40, 40);
        panelVOLVERLogin.setBackground(new Color(255, 255, 255));


        botonVOLVER.setForeground(Color.BLACK);
        botonVOLVER.setFont(new Font("Roboto", Font.BOLD, 24));
        botonVOLVER.setBounds(10, 0, 60, 40);


        panelVOLVERLogin.add(botonVOLVER);
        panelPrincipalLogin.add(panelVOLVERLogin);
    }*/
    public void frameConfig(){
        setTitle("Interfaz.Login.Login");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);

        setLayout(null);
        add(panelLogoLogin);
        add(panelPrincipalLogin);
        add(panelbgLogin);

        setVisible(true);
    }

    public void txtNombreMousePressed(MouseEvent evt) {
        txtNombre.setText("");
        txtNombre.setForeground(Color.BLACK);
        if (txtContra.getText().isEmpty()) {
            txtContra.setText("********");
            txtContra.setForeground(Color.LIGHT_GRAY);
        }

    }

    public void txtContraMousePressed(MouseEvent evt) {
        txtContra.setText("");
        txtContra.setForeground(Color.BLACK);
        if (txtNombre.getText().isEmpty()) {
            txtNombre.setText("Ingrese su nombre de usuario");
            txtNombre.setForeground(Color.LIGHT_GRAY);
        }

    }

    public void agregarEventos() {
        txtNombre.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                txtNombreMousePressed(evt);
            }

        });

        txtContra.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                txtContraMousePressed(evt);
            }
        });
        panelENTRARLogin.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                panelENTRARLogin.setBackground(new Color(0, 139, 207));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                panelENTRARLogin.setBackground(new Color(50, 158, 253));
            }
            public void mouseClicked(MouseEvent e) {
                if(txtNombre.getText().isEmpty() || txtNombre.getText().equals("Ingrese su nombre de usuario") || txtContra.getText().isEmpty() || txtContra.getText().equals("********")) {
                    JOptionPane.showMessageDialog(null, "Llenar todos los campos", "ERROR", JOptionPane.ERROR_MESSAGE);
                } else {
                    String nombre = txtNombre.getText();
                    String contrasena = String.valueOf(txtContra.getPassword());

                    if (verificarCredenciales(nombre, contrasena)) {
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Usuario inexistente", "Error de autenticación", JOptionPane.ERROR_MESSAGE);
                        txtNombre.setText("");
                        txtContra.setText("");
                    }
                }


            }
        });


      /*  panelREGISTRARSELogin.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                panelREGISTRARSELogin.setBackground(new Color(0, 139, 207));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                panelREGISTRARSELogin.setBackground(new Color(50, 158, 253));
            }
            public void mouseClicked(MouseEvent e) {
                log.setText("REGISTRARSE");
                panelPrincipalLogin.remove(panelENTRARLogin);
                panelPrincipalLogin.remove(panelREGISTRARSELogin);
                crearPanelVOLVER();
                crearPanelCREAR();
                panelPrincipalLogin.revalidate();
                panelPrincipalLogin.repaint();

            }
        });

        panelCREARLogin.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                panelCREARLogin.setBackground(new Color(0, 139, 207));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                panelCREARLogin.setBackground(new Color(50, 158, 253));
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if(txtNombre.getText().isEmpty() || txtNombre.getText().equals("Ingrese su nombre de usuario")){
                    JOptionPane.showMessageDialog(null, "Llenar todos los campos", "ERROR", JOptionPane.ERROR_MESSAGE);
                }else if(txtContra.getText().isEmpty() || txtContra.getText().equals("********")){
                    JOptionPane.showMessageDialog(null, "Llenar todos los campos", "ERROR", JOptionPane.ERROR_MESSAGE);
                }else{
                    String nombre = txtNombre.getText();
                    String contrasena = String.valueOf(txtContra.getPassword());

                    insertarUsuario(nombre, contrasena);
                }
                limpiar();  

            }
        });

        panelVOLVERLogin.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                panelVOLVERLogin.setBackground(new Color(255, 0, 0));
                botonVOLVER.setForeground(Color.white);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                panelVOLVERLogin.setBackground(new Color(255, 255, 255));
                botonVOLVER.setForeground(Color.black);
            }
            public void mouseClicked(MouseEvent e) {
                log.setText("INICIAR SESIÓN");
                panelPrincipalLogin.remove(panelCREARLogin);
                panelPrincipalLogin.remove(panelVOLVERLogin);
                panelPrincipalLogin.add(panelREGISTRARSELogin);
                panelPrincipalLogin.add(panelENTRARLogin);
                panelPrincipalLogin.revalidate();
                panelPrincipalLogin.repaint();

            }
        });*/

    }
    public boolean verificarCredenciales(String nombre, String contrasena) {
        Connection conexion = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        boolean credencialesValidas = false;

        try {
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            String sql = "SELECT * FROM Usuarios WHERE nombre = ? AND contrasena = ?";
            statement = conexion.prepareStatement(sql);
            statement.setString(1, nombre);
            statement.setString(2, contrasena);
            resultSet = statement.executeQuery();

            credencialesValidas = resultSet.next();
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

        return credencialesValidas;
    }


   /* public void insertarUsuario(String nombre, String contrasena) {
        Connection conexion = null;
        PreparedStatement statement = null;



            try {
                conexion = DriverManager.getConnection(URL, USER, PASSWORD);

                String sql = "INSERT INTO Usuarios (nombre, contrasena) VALUES (?, ?)";
                statement = conexion.prepareStatement(sql);
                statement.setString(1, nombre);
                statement.setString(2, contrasena);

                statement.executeUpdate();

                JOptionPane.showMessageDialog(null, "Usuario creado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error al insertar usuario: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } finally {
                try {
                    if (statement != null) statement.close();
                    if (conexion != null) conexion.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

    }
*/

    public void iconos(){
        ImageIcon imageIcon1 = new ImageIcon("D:\\logo.png");
        Image image1 = imageIcon1.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        ImageIcon icon1 = new ImageIcon(image1);
        imagen1.setIcon(icon1);
        imagen1.setBounds(70, 40, 200, 200);
        panelLogoLogin.add(imagen1);
    }
    public String getNombreUsuario() {
        return txtNombre.getText();
    }

    // Método para obtener la contraseña ingresada
    public String getContrasena() {
        return String.valueOf(txtContra.getPassword());
    }


}
