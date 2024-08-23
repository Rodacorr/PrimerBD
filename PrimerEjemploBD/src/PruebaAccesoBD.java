import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.Naming;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;


public class PruebaAccesoBD
{
	public static void main (String[] args)
	{
		try
		{
			
			String driver = "com.mysql.jdbc.Driver";
			Class.forName(driver);

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
	
			String url = prop.getProperty("url");

			String usr = prop.getProperty("usr");
			
			String pwd = prop.getProperty("pwd");
			
			System.out.println ("Login en servidor...");
			
			Connection con = DriverManager.getConnection(url, usr, pwd);
			
			System.out.println ("Login completo...");
	
			String insert = "INSERT INTO Personas" + "(Nombre)" + "VALUES (?)";
			PreparedStatement pstmt = con.prepareStatement(insert);
			pstmt.setString(1, "Jose");

			int cant = pstmt.executeUpdate();
			pstmt.close();
			System.out.print("Resultado de " + insert + ": ");
			System.out.println(cant + " filas afectadas");

			Statement stmt = con.createStatement();
			String query = "SELECT * FROM Personas";

			ResultSet rs = stmt.executeQuery(query);
			System.out.println("Resultado de " + query);
			while (rs.next())
			{
				System.out.println("Nombre = " + rs.getString("nombre").trim());
			}
			rs.close();
			stmt.close();

			con.close();
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}	
	}
}
