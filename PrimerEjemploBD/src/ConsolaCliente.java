import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConsolaCliente {
	
	private static final String urlEscuela = "jdbc:mysql://localhost:3306/Escuela";
    private static final String usr = "root";
    private static final String pwd = "admin";
	
    public static void main(String[] arg) throws IOException {
    	try {
    		Connection con = DriverManager.getConnection(urlEscuela, usr, pwd);
            InputStreamReader is = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(is);
    		
			String com = "";
			
			while(!(com.equals("exit"))) {
				System.out.print("Ingrese comando: ");
				com = br.readLine();
				
				if(com.equals("exit")) {
					System.out.print("Hasta la próxima");
					break;
				}
				
				try{
					Statement stmt = con.createStatement();
					int filasAfectadas = stmt.executeUpdate(com);
    				System.out.println("Cantidad de filas afectadas: " + filasAfectadas);
    				stmt.close();
				}catch (SQLException e) {
					System.out.println("Error: " + e.getMessage());
				}
				
			}
			con.close();
    	} catch (SQLException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    }
}
