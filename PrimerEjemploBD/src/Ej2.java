import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Properties;

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

public class Ej2 {

		Connection con = null;
		Properties prop = null;
		
		private static final String url = "jdbc:mysql://localhost:3306";
		private static final String usr = "root";
		private static final String pwd = "admin";
		
		public static void main (String [] args) {
			Ej2 nuevaDB = new Ej2();
			nuevaDB.CreateConnection();
			nuevaDB.CreateDatabaseAndTables();
			
		}
		
		void CreateDatabaseAndTables() {
			
			String createDB = "CREATE DATABASE IF NOT EXISTS Escuela";
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
			lista.add(createDB);
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
				}
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
		
		void CreateConnection() {
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
