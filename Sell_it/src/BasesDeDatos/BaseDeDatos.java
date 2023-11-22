package BasesDeDatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.sql.Statement;

public class BaseDeDatos {
	public static void main(String args[]) {
		//Decidimos el driver
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			System.out.println("No esta conectada la libreria");
			return;
		}
		//Creamos la conexion
		try {
			Connection connection = DriverManager.getConnection("jdbc:sqlite:usuarios.db");
			//Crear tantas statements como necesitemos
			Statement s = connection.createStatement();
			
			//s.executeUpdate("create table usuario (nombreUsuario string, correoUsuario string, tipoUsuario string, contrasena string)");
			s.executeUpdate("insert into usuario values(Laura Lopez,laura.lopez@gmail.com,Usuario corriente,abcABC33)");
			
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error en gestion de base de datos");
		}
	}
}