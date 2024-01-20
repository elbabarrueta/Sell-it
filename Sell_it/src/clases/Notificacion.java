package clases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Notificacion {
    // Atributos
	private int id;
	private String mensaje;
    private boolean leido;
    
    // Getters y Setters
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
	
	/**
     * Constructor de la clase.
     * @param mensaje Contenido del mensaje de la notificación.
     * @param leido Indica si la notificación ha sido leída o no.
     */
	public Notificacion(String mensaje, boolean leido) {
		super();
		this.id = obtenerId();	// Asigna el ID obtenido desde la base de datos
		this.mensaje = mensaje;
		this.leido = leido;
	}
	
    // Métodos adicionales

	@Override
	public String toString() {
		return "Notificacion [mensaje=" + mensaje + ", leido=" + leido + "]";
	}
	
	/**
     * Método privado para obtener el ID para una nueva notificación desde la base de datos.
     * @return Nuevo ID para una notificación.
     */
	private static int obtenerId() {
		int ultimoId = 0;

        String url = "jdbc:sqlite:usuarios.db";

        try (Connection connection = DriverManager.getConnection(url);
             Statement statement = connection.createStatement()) {

            // Consulta para obtener el último ID de notificación registrado en la base de datos
            String query = "SELECT MAX(id) AS ultimoId FROM Notificacion";
            ResultSet resultSet = statement.executeQuery(query);

            // Si hay un resultado, obtener el último ID
            if (resultSet.next()) {
            	ultimoId = resultSet.getInt("ultimoId");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ultimoId + 1;
    }
}
