package Practico3;

public class Consultas {
	
	public String listarExamenes() {
		String consulta ="SELECT * FROM bedelia.examenes e;";
		return consulta;
	}
	
	public String insertarResultado() {
		String insertar ="INSERT INTO bedelia.resultados (cedula, codigo, calificacion)" +
						"VALUES (?,?,?);";
		return insertar;
	}
	
	public String listarResultados() {
		String consulta = "SELECT * FROM bedelia.examenes e;";
		return consulta;
	}
}
