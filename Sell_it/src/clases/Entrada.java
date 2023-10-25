package clases;

public class Entrada {
	//Atributos
	private String cod;
	private String desc;
	private String fecha;
	private double precio;
	private Evento evento;
	
	//Getters y Setters
	public String getCod() {
		return cod;
	}
	public void setCod(String cod) {
		this.cod = cod;
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
	public Entrada(String cod, String desc, String fecha, double precio) {
		super();
		this.cod = cod;
		this.desc = desc;
		this.fecha = fecha;
		this.precio = precio;
	}
	
	@Override
	public String toString() {
		return "Entrada [nombre=" + cod + ", desc=" + desc + ", fecha=" + fecha + ", precio=" + precio + "]";
	}
	
}
