import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class Ej4 {
    
	Connection con = null;
	
    public static void main(String[] args) {
        Ej4 consulta = new Ej4();
        try {
        	consulta.con = consulta.CrearConexion();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        System.out.println("");
        System.out.println("PARTE A");
        try {
        	consulta.ParteA();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        System.out.println("");
        System.out.println("PARTE C");
        try {
        	consulta.ParteC();
        }catch (SQLException e) {
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
        	Properties prop = new Properties();
			String nomArch = "src/config/datos.dat";
			try {
				prop.load(new FileInputStream(nomArch));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
			String urlEscuela = prop.getProperty("urlEscuela");

			String usr = prop.getProperty("usr");
			
			String pwd = prop.getProperty("pwd");
			
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
  
    public Connection CrearConexion() {
		String driver = "com.mysql.jdbc.Driver";
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println ("Login en servidor...");
		
		try {
			Properties prop = new Properties();
			String nomArch = "src/config/datos.dat";
			try {
				prop.load(new FileInputStream(nomArch));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
			String urlEscuela = prop.getProperty("urlEscuela");
			String usr = prop.getProperty("usr");
			String pwd = prop.getProperty("pwd");
			
			con = DriverManager.getConnection(urlEscuela, usr, pwd);
			System.out.println ("Login completo...");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return con;
	}
    
    void ParteC() throws SQLException {

        try {
            con.setAutoCommit(false);
            
            String SQLstmt = "SELECT cedula FROM Maestra";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(SQLstmt);

            String cedulaMayor = "";
            int maxAlumnos = 0;

            while (rs.next()) {
                String cedulaMaestra = rs.getString("cedula");
                
                String SQLstmt2 = "SELECT COUNT(*) AS nalumnos FROM Alumnos WHERE cedulaMaestra = ?";
                PreparedStatement stmt2 = con.prepareStatement(SQLstmt2);
                stmt2.setString(1, cedulaMaestra);
                ResultSet rs2 = stmt2.executeQuery();

                if (rs2.next()) {
                    int numAlumnos = rs2.getInt("nalumnos");
                    if (numAlumnos > maxAlumnos) {
                        maxAlumnos = numAlumnos;
                        cedulaMayor = cedulaMaestra;
                    }
                }
                rs2.close();
                stmt2.close();
            }
            rs.close();
            stmt.close();

            if (!cedulaMayor.equals("")) {
            	
            	String SQLstmt3 = "SELECT nombre, apellido FROM Personas WHERE cedula = ?";
                PreparedStatement stmt3 = con.prepareStatement(SQLstmt3);
                stmt3.setString(1, cedulaMayor);
                ResultSet rs3 = stmt3.executeQuery();

                if (rs3.next()) {
                    String nombre = rs3.getString("nombre");
                    String apellido = rs3.getString("apellido");
                    System.out.println("La maestra con más alumnos es: " + nombre + " " + apellido);
                }
                rs3.close();
                stmt3.close();
            }

            con.commit();

        } catch (SQLException e) {
            if (con != null) {
                try {
                    con.rollback();
                    con.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            e.printStackTrace();
        }
    }

}
