package clases;

import java.util.ArrayList;
import java.util.Objects;

import ventanas.VentanaPrincipal;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Evento implements Serializable{
	//Atributos
	private int codigo;
	private String nombre;
	private String desc;
	private String fecha;
	private String ubicacion;
	private int nEntradas;
	private String rutaImg;
	private String creador;
	
	
	//Getters y Setters
	public int getCodigo() {
		return codigo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getUbicacion() {
		return ubicacion;
	}
	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}
	public int getnEntradas() {
		return nEntradas;
	}
	public void setnEntradas(int nEntradas) {
		this.nEntradas = nEntradas;
	}
	public String getRutaImg() {
		return rutaImg;
	}
	public void setRutaImg(String rutaImg) {
		this.rutaImg = rutaImg;
	}
	public String getCreador() {
		return creador;
	}
	public void setCreador(String creador) {
		this.creador = creador;
	}
	
	// Constructores
	
	/**
     * Constructor con parámetros para inicializar un evento.
     */
	public Evento(String nombre, String desc, String fecha, String ubicacion, int nEntradas, String rutaImg, String creador) {
		super();
		this.codigo = obtenerCod();
		this.nombre = nombre;
		this.desc = desc;
		this.fecha = fecha;
		this.ubicacion = ubicacion;
		this.nEntradas = nEntradas;
		this.rutaImg = rutaImg;
		this.creador = creador;
	}
	/**
     * Constructor con parámetros para inicializar un evento con un código específico.
     */
	public Evento(int codigo, String nombre, String desc, String fecha, String ubicacion, int nEntradas, String rutaImg, String creador) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
		this.desc = desc;
		this.fecha = fecha;
		this.ubicacion = ubicacion;
		this.nEntradas = nEntradas;
		this.rutaImg = rutaImg;
		this.creador = creador;
	}
	/**
     * Constructor sin parámetros para inicializar un evento con valores predeterminados.
     */
	public Evento() {
		super();
		this.codigo = obtenerCod();
		this.nombre = "";
		this.desc = "";
		this.fecha = "";
		this.ubicacion = "";
		this.nEntradas = 1;
		this.rutaImg = null;
		this.creador = "";
	}
	
	@Override
	public String toString() {
		return "Evento [codigo=" + codigo + ", nombre=" + nombre + ", desc=" + desc + ", fecha=" + fecha + ", ubicacion=" + ubicacion
				+ ", nEntradas=" + nEntradas + ", creador=" + creador;
	}

    /**
     * Compara dos eventos para determinar si son iguales basándose en el código.
     * @param obj Objeto a comparar con el evento actual.
     * @return true si los eventos son iguales, false en caso contrario.
     */
	@Override
	public boolean equals(Object obj) {
	    if (this == obj) return true;
	    if (obj == null || getClass() != obj.getClass()) return false;
	    Evento evento = (Evento) obj;
	    return codigo == evento.codigo;
	}
	
	/**
     * Genera un código hash único para el evento basándose en su código.
     * @return Código hash del evento.
     */
	@Override
	public int hashCode() {
	    return Objects.hash(codigo);
	}
	
	/**
     * Método privado para obtener el código para un nuevo evento desde la base de datos.
     * @return Nuevo código para un evento.
     */
	private static int obtenerCod() {
		int ultimoCodigo = 0;

        String url = "jdbc:sqlite:usuarios.db";

        try (Connection connection = DriverManager.getConnection(url);
             Statement statement = connection.createStatement()) {

            // Consulta para obtener el último código de evento registrado en la base de datos
            String query = "SELECT MAX(codigo) AS ultimoCodigo FROM Evento";
            ResultSet resultSet = statement.executeQuery(query);

            // Si hay un resultado, obtener el último código
            if (resultSet.next()) {
                ultimoCodigo = resultSet.getInt("ultimoCodigo");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Incrementar el último código para el nuevo evento
        return ultimoCodigo + 1;
    }

}
