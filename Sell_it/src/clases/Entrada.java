package clases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Entrada {
	//Atributos
	private static int cod = obtenerCod(); //codigo unico
//	private String desc;
//	private String fecha; 
	private Evento eventoAsociado;
	private Usuario propietario;
	private double precio;
	
	
	//Getters y Setters
	public int getCod() {
		return cod;
	}
//	public void setCod(String cod) {
//		this.cod = cod;
//	}
//	public String getDesc() {
//		return desc;
//	}
//	public void setDesc(String desc) {
//		this.desc = desc;
//	}
//	public String getFecha() {
//		return fecha;
//	}
//	public void setFecha(String fecha) {
//		this.fecha = fecha;
//	}
	public Evento getEventoAsociado() {
		return eventoAsociado;
	}
	public void setEventoAsociado(Evento eventoAsociado) {
		this.eventoAsociado = eventoAsociado;
	}
	public Usuario getPropietario() {
		return propietario;
	}
	public void setPropietario(Usuario propietario) {
		this.propietario = propietario;
	}
	public double getPrecio() {
		return precio;
	}
	public void setPrecio(double precio) {
		this.precio = precio;
	}
	
	//Constructores
	public Entrada(Evento eventoAsociado, Usuario propietario, double precio) {
		super();
		this.cod = cod++;
//		this.desc = desc;
//		this.fecha = fecha;
		this.eventoAsociado = eventoAsociado;
		this.propietario = propietario;
		this.precio = precio;
	}
	
	@Override
	public String toString() {
		return "Entrada [nombre=" + cod + ",eventoAsociado= "+ eventoAsociado + ",propietario= "+ propietario + ",precio=" + precio + "]";
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
