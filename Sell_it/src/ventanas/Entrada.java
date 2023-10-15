package ventanas;

public class Entrada {
	//Atributos
	private String nombre;
	private String desc;
	private String fecha;
	private double precio;
	
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
	
	//Constructores
	public Entrada(String nombre, String desc, String fecha, double precio) {
		super();
		this.nombre = nombre;
		this.desc = desc;
		this.fecha = fecha;
		this.precio = precio;
	}
	
	@Override
	public String toString() {
		return "Entrada [nombre=" + nombre + ", desc=" + desc + ", fecha=" + fecha + ", precio=" + precio + "]";
	}
	
}
