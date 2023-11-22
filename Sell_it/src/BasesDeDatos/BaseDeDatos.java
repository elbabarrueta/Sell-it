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
			s.executeUpdate("insert into usuario values(Miguel Diaz,mdiaz@gmail.com,Usuario corriente,mMiaz45#g)");
			s.executeUpdate("insert into usuario values(Kepa Galindo,k10galindo@gmail.com,Usuario corriente,GK842aeiou)");
			s.executeUpdate("insert into usuario values(Discoteca Moma,moma@gmail.com,Usuario entidad,MmMon345627#)");
			
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error en gestion de base de datos");
		}
	}
}