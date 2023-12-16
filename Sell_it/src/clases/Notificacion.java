package src.clases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Notificacion {
	private int id;
	private String mensaje;
    private boolean leido;
    
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public boolean isLeido() {
		return leido;
	}
	public void setLeido(boolean leido) {
		this.leido = leido;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Notificacion(String mensaje, boolean leido) {
		super();
		this.id = obtenerId();
		this.mensaje = mensaje;
		this.leido = leido;
	}
	
	@Override
	public String toString() {
		return "Notificacion [mensaje=" + mensaje + ", leido=" + leido + "]";
	}
	
	private static int obtenerId() {
		int ultimoId = 0;

        String url = "jdbc:sqlite:usuarios.db";

        try (Connection connection = DriverManager.getConnection(url);
             Statement statement = connection.createStatement()) {

            String query = "SELECT MAX(id) AS ultimoId FROM Notificacion";
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
            	ultimoId = resultSet.getInt("ultimoId");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ultimoId + 1;
    }
}
