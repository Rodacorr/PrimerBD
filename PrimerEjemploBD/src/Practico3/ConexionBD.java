package Practico3;
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

    public ConexionBD() throws ClassNotFoundException {
        Conectar();
    }

    private void Conectar() throws ClassNotFoundException {

        Properties prop = new Properties();
        String nomArch = "src/config/datos.dat";

        try {
            prop.load(new FileInputStream(nomArch));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String urlBD = prop.getProperty("urlBedelia");
        String usr = prop.getProperty("usr");
        String pwd = prop.getProperty("pwd");
        String driver = prop.getProperty("driver");

        try {
        	Class.forName(driver);
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
        connection.close();
        System.out.println("Conexión cerrada...");
    }
}