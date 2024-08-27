import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class StoredProcedures {
	private static final String urlEscuela = "jdbc:mysql://localhost:3306/Escuela";
    private static final String usr = "root";
    private static final String pwd = "admin";
    
    Connection con;
    
    public static void main(String[] arg) throws IOException {
    	StoredProcedures SP = new StoredProcedures();
    	SP.CrearConexion();
    	SP.BorrarMaestra();
    }
    
    public void BorrarMaestra() {
    	
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
