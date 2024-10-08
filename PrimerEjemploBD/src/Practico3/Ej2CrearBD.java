package Practico3;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

public class Ej2CrearBD {

	public static void main(String[] args) throws IOException, ClassNotFoundException{
		ConexionBD conexionBD = new ConexionBD();
	    try {
	    	
	    	Ej2CrearBD crearBD = new Ej2CrearBD();
            crearBD.CrearTablas(conexionBD.getConnection(), crearBD.ConsultaSQL());
            
            crearBD.CargarDatos(conexionBD.getConnection(), crearBD.InsertarSQL());
	    	
            conexionBD.close();
	    }catch(SQLException e) {
	    	e.printStackTrace();
	    }
	    
	 }
	
	public ArrayList<String> ConsultaSQL() {
		/*
		Examenes (codigo VARCHAR(45), materia VARCHAR(45), periodo VARCHAR(45))
		Resultados (cedula INT, codigo VARCHAR(45), calificacion INT)
		*/
		
		String crearDB = "CREATE DATABASE IF NOT EXISTS Bedelia";
		
		String useDB = "USE Bedelia";
		
		String crearTableExamenes = "CREATE TABLE IF NOT EXISTS Examenes (" +
									"codigo VARCHAR(45)," +
									"materia VARCHAR(50)," +
									"periodo VARCHAR(50)," +
									"PRIMARY KEY (codigo)" +
									");";
		
		String crearTableResultados= "CREATE TABLE IF NOT EXISTS Resultados (" +
									"cedula INT," +
									"codigo VARCHAR(45)," +
									"calificacion INT," +
									"PRIMARY KEY (cedula)," +
									"FOREIGN KEY (codigo) REFERENCES Examenes(codigo)" +
									");";
		
		ArrayList<String> lista = new ArrayList<>();
		lista.add(crearDB);
		lista.add(useDB);
		lista.add(crearTableExamenes);
		lista.add(crearTableResultados);
		
		return lista;
		
	}
	
	void CrearTablas(Connection con, ArrayList<String> lista) throws SQLException {
		
		try {
			Statement stmt = con.createStatement();
			for (String sql : lista) {
				stmt.executeUpdate(sql);
				System.out.println("Ejecutado: " + sql);
			}
			stmt.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<String> InsertarSQL() {
		/*
		INSERT INTO Examenes (codigo, materia, periodo) VALUES ('MD2020Dic', 'Matemática discreta', 'Diciembre 2020');
		INSERT INTO Examenes (codigo, materia, periodo) VALUES ('P12020Dic', 'Programación 1', 'Diciembre 2020');
		INSERT INTO Examenes (codigo, materia, periodo) VALUES ('BD2020Dic', 'Bases de datos', 'Diciembre 2020');
		INSERT INTO Examenes (codigo, materia, periodo) VALUES ('MD2021Feb', 'Matemática discreta', 'Febrero 2021');
		INSERT INTO Examenes (codigo, materia, periodo) VALUES ('SO2021Feb', 'Sistemas Operativos', 'Febrero 2021');
		*/
		String insertDatos1 = "INSERT INTO examenes (codigo, materia, periodo) VALUES ('MD2020Dic', 'Matemática discreta', 'Diciembre 2020');";
		String insertDatos2 = "INSERT INTO examenes (codigo, materia, periodo) VALUES ('P12020Dic', 'Programación 1', 'Diciembre 2020');";
		String insertDatos3 = "INSERT INTO examenes (codigo, materia, periodo) VALUES ('BD2020Dic', 'Bases de datos', 'Diciembre 2020');";
		String insertDatos4 = "INSERT INTO examenes (codigo, materia, periodo) VALUES ('MD2021Feb', 'Matemática discreta', 'Febrero 2021');";
		String insertDatos5 = "INSERT INTO examenes (codigo, materia, periodo) VALUES ('SO2021Feb', 'Sistemas Operativos', 'Febrero 2021');";
		
		ArrayList<String> lista = new ArrayList<>();
		lista.add(insertDatos1);
		lista.add(insertDatos2);
		lista.add(insertDatos3);
		lista.add(insertDatos4);
		lista.add(insertDatos5);
		
		return lista;
	}
	
	void CargarDatos(Connection con, ArrayList<String> lista) throws SQLException {
		try {
			for(String sql : lista) {
				PreparedStatement pstmt = con.prepareStatement(sql);
				pstmt.executeUpdate();
				System.out.println("Ejecutado: " + sql);
				pstmt.close();
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
}
