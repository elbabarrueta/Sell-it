package clases;

public class Usuario {
	//Hay que cambiar los atributos
	private String nombreUsuario;
	private String correoUsuario;
	private String tipoUsuario;
	private String contrasena;
	
	//
	public final String getNombreUsuario() {
		return nombreUsuario;
	}
	public final void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
	public final String getCorreoUsuario() {
		return correoUsuario;
	}
	public final void setCorreoUsuario(String correoUsuario) {
		this.correoUsuario = correoUsuario;
	}
	public final String getTipoUsuario() {
		return tipoUsuario;
	}
	public final void setTipoUsuario(String tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}
	public final String getContrasena() {
		return contrasena;
	}
	public final void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}
	//
	public Usuario() {
		super();
		this.nombreUsuario = " ";
		this.correoUsuario = " ";
		this.tipoUsuario = " ";
		this.contrasena = " ";
	}
	public Usuario(String nombreUsuario, String correoUsuario, String tipoUsuario, String contrasena) {
		super();
		this.nombreUsuario = nombreUsuario;
		this.correoUsuario = correoUsuario;
		this.tipoUsuario = tipoUsuario;
		this.contrasena = contrasena;
	}
	//
	@Override
	public String toString() {
		return "Usuario [nombreUsuario=" + nombreUsuario + ", correoUsuario="
				+ correoUsuario + ", tipoUsuario=" + tipoUsuario +", contraseña:"+ contrasena+ "]";
	}
	
	//Metodos
	public void cambiarContrasena(String nuevaContrasena) {
		this.contrasena = nuevaContrasena;
	}
	
}
