package clases;

import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Evento {
	//Atributos
	public static int codigo = obtenerCod();
	private String nombre;
	private String desc;
	private String fecha;
	private String ubicacion;
	private int nEntradas;
//	private double precio;
	private String rutaImg;
	
	
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
//	public double getPrecio() {
//		return precio;
//	}
//	public void setPrecio(double precio) {
//		this.precio = precio;
//	}
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
	
	//Constructores
	public Evento(String nombre, String desc, String fecha, String ubicacion, int nEntradas) {
		super();
		this.codigo = obtenerCod();
		this.nombre = nombre;
		this.desc = desc;
		this.fecha = fecha;
		this.ubicacion = ubicacion;
		this.nEntradas = nEntradas;
//		this.entradasDisponibles = entradasDisponibles;
//		this.precio = precio;
	}
	
	@Override
	public String toString() {
		return "Evento [nombre=" + nombre + ", desc=" + desc + ", fecha=" + fecha + ", ubicacion=" + ubicacion
				+ ", nEntradas=" + nEntradas;
	}
	
	private static int obtenerCod() {
		int ultimoCodigo = 0;

        String url = "jdbc:sqlite:usuarios.db";

        try (Connection connection = DriverManager.getConnection(url);
             Statement statement = connection.createStatement()) {

            String query = "SELECT MAX(codigo) AS ultimoCodigo FROM Evento";
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                ultimoCodigo = resultSet.getInt("ultimoCodigo");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ultimoCodigo + 1;
    }
}
