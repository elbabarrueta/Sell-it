package clases;

public class Ussuario {
	private String id ;
	private String contrasenia ;
	private String nombre ;
	private String direccion ;
	
	public Ussuario() {
		super();
	}
	public String getId() {
		return id;
	}
	public Ussuario(String id, String contrasenia, String nombre, String direccion, String codigoPostal) {
		super();
		this.id = id;
		this.contrasenia = contrasenia;
		this.nombre = nombre;
		this.direccion = direccion;
		this.codigoPostal = codigoPostal;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getContrasenia() {
		return contrasenia;
	}
	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getCodigoPostal() {
		return codigoPostal;
	}
	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}
	private String codigoPostal ;
	@Override
	public String toString() {
		return "Ussuario [id=" + id + ", contrasenia=" + contrasenia + ", nombre=" + nombre + ", direccion=" + direccion
				+ ", codigoPostal=" + codigoPostal + "]";
	}
	
	public int ComparteTo(Ussuario o) {
		return this.id.compareTo(o.id);
	}
	

}
