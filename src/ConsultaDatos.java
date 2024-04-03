import java.sql.*;

public class ConsultaDatos {
    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "SELECT * FROM Usuarios";

            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String nombre = resultSet.getString("nombre");
                    String contraseña = resultSet.getString("contrasena");

                    System.out.println("ID: " + id + ", Nombre: " + nombre + ", Contraseña: " + contraseña);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static final String URL = "jdbc:mysql://localhost:3306/BDUsuarios";
    private static final String USER = "root";
    private static final String PASSWORD = "Mpepen01234*";
}
