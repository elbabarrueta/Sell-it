package clases;

public class Evento {
	//Atributos
	private String prueba;
	private String nombre;
	private String desc;
	private String fecha;
	private double precio;
	private String ubicacion;
	
	//Getters y Setters
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
	public double getPrecio() {
		return precio;
	}
	public void setPrecio(double precio) {
		this.precio = precio;
	}
	public String getUbicacion() {
		return ubicacion;
	}
	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}
	
	//Constructores
	public Evento(String nombre, String desc, String fecha, double precio, String ubicacion) {
		super();
		this.nombre = nombre;
		this.desc = desc;
		this.fecha = fecha;
		this.precio = precio;
		this.ubicacion = ubicacion;
	}
	
	@Override
	public String toString() {
		return "Evento [nombre=" + nombre + ", desc=" + desc + ", fecha=" + fecha + ", precio=" + precio
				+ ", ubicacion=" + ubicacion + "]";
	}
	
	
}
