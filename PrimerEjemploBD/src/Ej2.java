import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Properties;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;


public class Ej2 {

		Connection con;
		Properties prop;
		
		private static final String url = "jdbc:mysql://localhost:3306";
		private static final String usr = "root";
		private static final String pwd = "admin";
		private static final String urlEscuela = "jdbc:mysql://localhost:3306/Escuela";
		
		public static void main (String [] args) {
			Ej2 nuevaDB = new Ej2();
			nuevaDB.CrearConexion();
			//nuevaDB.CrearBaseDeDatosYTablas();
			nuevaDB.UpdateTabla();
			
		}
		
		void UpdateTabla() {
			String agregarFKMaestra = "ALTER TABLE Maestra " 
                    + "ADD CONSTRAINT fk_maestra_persona "
                    + "FOREIGN KEY (cedula) REFERENCES Personas(cedula);"; 
			
			String agregarFKAlumnosCedula = "ALTER TABLE Alumnos " 
                    + "ADD CONSTRAINT fk_alumnos_persona "
                    + "FOREIGN KEY (cedula) REFERENCES Personas(cedula);";
			
			String agregarFKAlumnosMaestra = "ALTER TABLE Alumnos " 
                    + "ADD CONSTRAINT fk_alumnos_maestra "
                    + "FOREIGN KEY (cedulaMaestra) REFERENCES Maestra(cedula);";
			
			ArrayList<String> lista = new ArrayList<>();
		    lista.add(agregarFKMaestra);
		    lista.add(agregarFKAlumnosCedula);
		    lista.add(agregarFKAlumnosMaestra);
		    
		    try (Connection tempCon = DriverManager.getConnection(urlEscuela, usr, pwd)) {
		        Statement stmt = tempCon.createStatement();

		        for (String sql : lista) {
		            stmt.executeUpdate(sql);
		            System.out.println("FK" + sql + "agregada.");
		        }

		        stmt.close();
		        tempCon.close();
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		}
		
		void CrearBaseDeDatosYTablas() {
			
			String crearDB = "CREATE DATABASE IF NOT EXISTS Escuela";
	        String useDB = "USE Escuela";
			
	        String cPersona = "CREATE TABLE IF NOT EXISTS Personas (" 
	        				+ "cedula INT PRIMARY KEY, "
	        				+ "Nombre VARCHAR(45), " 
	        				+ "apellido VARCHAR(45)" 
	        				+ ");";
    
	        String cMaestra = "CREATE TABLE IF NOT EXISTS Maestra (" 
                    		+ "cedula INT PRIMARY KEY, " 
                    		+ "grupo VARCHAR(45)" 
                    		+ ");";
    
	        String cAlumnos = "CREATE TABLE IF NOT EXISTS Alumnos (" 
		                    + "cedula INT PRIMARY KEY, " 
		                    + "cedulaMaestra INT" 
		                    + ");";
					
			ArrayList<String> lista = new ArrayList<>();
			lista.add(crearDB);
	        lista.add(useDB);
			lista.add(cPersona);
			lista.add(cMaestra);
			lista.add(cAlumnos);
			
			try {
				try (
				Connection tempCon = DriverManager.getConnection(url,usr,pwd)) {
				Statement stmt = tempCon.createStatement();
					
				for (String sql : lista) {
				    stmt.executeUpdate(sql);
				    System.out.println("Ejecutado: " + sql);
				}
					
				stmt.close();
				tempCon.close();
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
			
			prop = new Properties();
			
			System.out.println ("Login en servidor...");
			
			try {
				con = DriverManager.getConnection(url, usr, pwd);
				System.out.println ("Login completo...");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
}
