package clases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Valoracion {
	private String usuarioRevisor;
    private String usuarioValorado;
    private int puntuacion;
    private String comentario;

    public Valoracion(String usuarioRevisor, String usuarioValorado, int puntuacion, String comentario) {
        this.usuarioRevisor = usuarioRevisor;
        this.usuarioValorado = usuarioValorado;
        this.puntuacion = puntuacion;
        this.comentario = comentario;
    }

	public final String getUsuarioRevisor() {
		return usuarioRevisor;
	}

	public final void setUsuarioRevisor(String usuarioRevisor) {
		this.usuarioRevisor = usuarioRevisor;
	}

	public final String getUsuarioValorado() {
		return usuarioValorado;
	}

	public final void setUsuarioValorado(String usuarioValorado) {
		this.usuarioValorado = usuarioValorado;
	}

	public final int getPuntuacion() {
		return puntuacion;
	}

	public final void setPuntuacion(int puntuacion) {
		this.puntuacion = puntuacion;
	}

	public final String getComentario() {
		return comentario;
	}

	public final void setComentario(String comentario) {
		this.comentario = comentario;
	}
	@Override
    public String toString() {
        return "Valoración [Revisor: " + usuarioRevisor + ", Puntuación: " + puntuacion + ", Comentario: " + comentario + "]";
    }
	public static int obtenerId() {
		int ultimoId = 0;

        String url = "jdbc:sqlite:usuarios.db";

        try (Connection connection = DriverManager.getConnection(url);
             Statement statement = connection.createStatement()) {

            String query = "SELECT MAX(id) AS ultimoId FROM Valoracion";
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
