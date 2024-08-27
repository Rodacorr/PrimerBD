import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class Ej4 {
    private static final String urlEscuela = "jdbc:mysql://localhost:3306/Escuela";
    private static final String usr = "root";
    private static final String pwd = "admin";
    
    Connection con;
    
    public static void main(String[] args) {
        Ej4 consulta = new Ej4();
        consulta.CrearConexion();
        try {
            consulta.ParteA();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    void ParteA() throws SQLException {
        String consultaSQL = "SELECT p.cedula, p.nombre, p.apellido " +
                             "FROM Maestra m " +
                             "JOIN Personas p ON m.cedula = p.cedula " +
                             "JOIN Alumnos a ON m.cedula = a.cedulaMaestra " +
                             "GROUP BY p.cedula, p.nombre, p.apellido " +
                             "ORDER BY COUNT(a.cedula) DESC " +
                             "LIMIT 1";
        
        try {
        	Connection con = DriverManager.getConnection(urlEscuela, usr, pwd);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(consultaSQL);
            
            if (rs.next()) {
                int cedula = rs.getInt("cedula");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                
                System.out.println("Maestra con más alumnos:");
                System.out.println("Cédula: " + cedula);
                System.out.println("Nombre: " + nombre);
                System.out.println("Apellido: " + apellido);
                
                rs.close();
                stmt.close();
                con.close();
            } else {
                System.out.println("No se encontraron maestras con alumnos.");
                rs.close();
                stmt.close();
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    void CrearConexion() {
		String driver = "com.mysql.jdbc.Driver";
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println ("Login en servidor...");
		
		try {
			con = DriverManager.getConnection(urlEscuela, usr, pwd);
			System.out.println ("Login completo...");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
