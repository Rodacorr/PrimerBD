import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.sql.*;
import java.io.*;

public class Ej6DatabaseMetaData {

	public static void main (String[] args) throws IOException {
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
		
		 String urlBD = prop.getProperty("urlBD");
	     String usr = prop.getProperty("usr");
	     String pwd = prop.getProperty("pwd");
	     
	     try {
	    	 Connection con = DriverManager.getConnection(urlBD,usr,pwd);
	    	 System.out.println ("Login completo...");
	    	 
	    	 DatabaseMetaData Data = con.getMetaData();
	    	 System.out.println("Bases de datos existentes:");
	    	 ResultSet rs = Data.getCatalogs();
	    	 while(rs.next()) {
	    		 String nom = rs.getString(1);
	    		 System.out.println(nom);
	    	 }
	    	 rs.close();
	    	 
	    	 BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		     System.out.println("");
		     System.out.print("Ingrese nombre de la BD a consutlar: ");
		     String nombre = br.readLine().toLowerCase().trim();
		     
		     con.setCatalog(nombre);
		     
		     System.out.println("Tablas de la BD seleccionada:");
		     
		     ResultSet rsTabla = Data.getTables(nombre, null, "%", new String[]{"TABLE"});
		     
		     while (rsTabla.next()) {
		    	 String nomTabla = rsTabla.getString("TABLE_NAME");
		    	 System.out.println("Tabla -> " + nomTabla);
		    	 
		    	 ResultSet rsColumna = Data.getColumns(nombre, null, nomTabla, "%");
		    	 System.out.print("Columnas -> ");
		    	 while (rsColumna.next()) {
	                    String nombreColumna = rsColumna.getString("COLUMN_NAME");
	                    String tipoColumna = rsColumna.getString("TYPE_NAME");
	                    int tamanoColumna = rsColumna.getInt("COLUMN_SIZE");
	                    System.out.print(nombreColumna + " (" + tipoColumna + " " + tamanoColumna + ") ");
	             }
		    	 rsColumna.close();
	             System.out.println();
	         }
		     rsTabla.close();  
	    	 
	     }catch(SQLException e) {
	    	 e.printStackTrace();
	     }  	     
	}
}
