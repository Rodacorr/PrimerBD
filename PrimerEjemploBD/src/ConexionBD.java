import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import Practico3.Ej2CrearBD;

public class ConexionBD {
    private Connection connection;

    public void ConexionBD() {
        Conectar();
    }

    private void Conectar() {
        String driver = "com.mysql.jdbc.Driver";
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Properties prop = new Properties();
        String nomArch = "src/config/datos.dat";

        try {
            prop.load(new FileInputStream(nomArch));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String urlBD = prop.getProperty("urlEscuela");
        String usr = prop.getProperty("usr");
        String pwd = prop.getProperty("pwd");

        try {
            connection = DriverManager.getConnection(urlBD, usr, pwd);
            System.out.println("Conexión establecida...");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void close() throws SQLException {
        try {
        	connection.close();
        	System.out.println("Conexión cerrada...");
        }catch(SQLException e) {
        	e.printStackTrace();
        }
    }
}