package clases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Entrada{
	//Atributos
	private static int cod; //codigo unico
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
	public Entrada(int cod, Evento eventoAsociado, Usuario propietario, double precio) {
		super();
		this.cod = cod;
//		this.desc = desc;
//		this.fecha = fecha;
		this.eventoAsociado = eventoAsociado;
		this.propietario = propietario;
		this.precio = precio;
	}
	
	public Entrada() {
        super();
        this.cod = 0; // Puedes establecer el valor predeterminado que desees
        this.eventoAsociado = null; // Puedes establecer el valor predeterminado que desees
        this.propietario = null; // Puedes establecer el valor predeterminado que desees
        this.precio = 0.0; // Puedes establecer el valor predeterminado que desees
    }
	
	
	@Override
	public String toString() {
		return "Entrada [codigo=" + cod + ",eventoAsociado= "+ eventoAsociado + ",propietario= "+ propietario + ",precio=" + precio + "]";
	}
}
