package Practico3;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

class Examen{
	private String codigo;
	private String materia;
	private String periodo;
	
	public Examen(String codigo, String materia, String periodo) {
		this.codigo = codigo;
		this.materia = materia;
		this.periodo = periodo;
	}
	
	public String getCodigo() {
		return codigo;
	}
	
	public String getMateria() {
		return materia;
	}
	
	public String getPeriodo() {
		return periodo;
	}
}

class Resultado{
	private int cedula;
	private String codigo;
	private int calificacion;
	
	public Resultado(int cedula, String codigo, int calificacion) {
		this.cedula = cedula;
		this.codigo = codigo;
		this.calificacion = calificacion;
	}
	
	public int getCedula() {
		return cedula;
	}
	
	public String getCodigo() {
		return codigo;
	}
	
	public int getCalificacion() {
		return calificacion;
	}
}

public class AccesoBD {
	private Consultas consultas = new Consultas();
	
	public List <Examen> listarExamenes (Connection con){
		List<Examen> examenes = new ArrayList<>();
		String consulta = consultas.listarExamenes();
		
		try {
			PreparedStatement stmt = con.prepareStatement(consulta);
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				String codigo = rs.getString("codigo");
				String materia = rs.getString("materia");
				String periodo = rs.getString("periodo");
				
				Examen examen = new Examen(codigo, materia, periodo);
				examenes.add(examen);
			}
			rs.close();
			stmt.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return examenes;
	}
	
	public void ingresarResultado (Connection con, Resultado resu) {
		String insertar = consultas.insertarResultado();
		
		try {
			PreparedStatement pstmt = con.prepareStatement(insertar);
			pstmt.setInt(1, resu.getCedula());
			pstmt.setString(2, resu.getCodigo());
			pstmt.setInt(3, resu.getCalificacion());
			pstmt.executeUpdate();
			pstmt.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
