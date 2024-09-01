import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StoredProcedures {
	private static final String urlEscuela = "jdbc:mysql://localhost:3306/Escuela";
    private static final String usr = "root";
    private static final String pwd = "admin";
    
    Connection con;
    
    public static void main(String[] arg) throws IOException {
    	try {
    		Connection con = DriverManager.getConnection(urlEscuela, usr, pwd);
    		StoredProcedures SP = new StoredProcedures();
    		SP.BorrarMaestra(con, 12345678);
    	}catch(SQLException e) {
    		e.printStackTrace();
    	}

    	//SP.CrearConexion();    	
    }
    
    public void BorrarMaestra(Connection con, int ced) {
    /* CREATE PROCEDURE Escuela.BorrarMaestra (ced INT)
		BEGIN
		DECLARE cant INT DEFAULT 0;
		SET cant = (SELECT COUNT(cedula) FROM Maestras m WHERE m.cedula = ced);
		IF cant = 1 THEN
		 SELECT a.cedula FROM Alumnos a WHERE a.cedulaMaestra = ced;
		 SELECT grupo FROM Maestras m WHERE m.cedula = ced;
		 SELECT nombre, apellido FROM Personas p WHERE p.cedula = ced;
		 DELETE FROM Alumnos WHERE cedulaMaestra = ced;
		 DELETE FROM Maestras WHERE cedula = ced;
		 DELETE FROM Personas WHERE cedula = ced;
		END IF;
		END */	
    	
    	String SQLSP = "CREATE PROCEDURE Escuela.BorrarMaestra (ced INT) "
	                + "BEGIN "
	                + "DECLARE cant INT DEFAULT 0; "
	                + "SET cant = (SELECT COUNT(cedula) FROM Maestra WHERE cedula = ced); "
	                + "IF cant = 1 THEN "
	                + " SELECT a.cedula FROM Alumnos a WHERE a.cedulaMaestra = ced; "
	                + " SELECT grupo FROM Maestra WHERE cedula = ced; "
	                + " SELECT nombre, apellido FROM Personas WHERE cedula = ced; "
	                + " DELETE FROM Alumnos WHERE cedulaMaestra = ced; "
	                + " DELETE FROM Maestra WHERE cedula = ced; "
	                + " DELETE FROM Personas WHERE cedula = ced; "
	                + "END IF; "
	                + "END";
   
	   try {
		   CallableStatement stmt = con.prepareCall("{CALL BorrarMaestra(?)}");
	       stmt.setInt(1, ced);
	       boolean hasRS = stmt.execute();
	       int RSnumber = 1;
	       while (hasRS) {
	           ResultSet rs = stmt.getResultSet();
	           System.out.println("ResultSet #" + RSnumber + ":");
	           
	           switch(RSnumber) {
	               case 1:
	                   while (rs.next()) {
	                       int CIAlumno = rs.getInt("cedula");
	                       System.out.println("Cédula del alumno: " + CIAlumno);
	                   }
	                   break;
	               case 2:
	                   while (rs.next()) {
	                       String grupo = rs.getString("grupo");
	                       System.out.println("Grupo: " + grupo);
	                   }
	                   break;
	               case 3:
	                   while (rs.next()) {
	                       String nombre = rs.getString("nombre");
	                       String apellido = rs.getString("apellido");
	                       System.out.println("Nombre: " + nombre);
	                       System.out.println("Apellido: " + apellido);
	                   }
	                   break;
	           }
	           
	           RSnumber++;
	           hasRS = stmt.getMoreResults();
	       }
	       
	       System.out.println("Procedimiento almacenado BorrarMaestra ejecutado correctamente.");
	   } catch (SQLException e) {
	       e.printStackTrace();
	   }
	}

}