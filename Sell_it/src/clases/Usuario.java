package clases;

public class Usuario {

	private String nombreUsuario;
	private String correoUsuario;
	private String tipoUsuario;
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
	
	//
	public Usuario() {
		super();
		this.nombreUsuario = " ";
		this.correoUsuario = " ";
		this.tipoUsuario = " ";
	}
	public Usuario(String nombreUsuario, String correoUsuario, String tipoUsuario) {
		super();
		this.nombreUsuario = nombreUsuario;
		this.correoUsuario = correoUsuario;
		this.tipoUsuario = tipoUsuario;
	}
	//
	@Override
	public String toString() {
		return "Usuario [nombreUsuario=" + nombreUsuario + ", correoUsuario="
				+ correoUsuario + ", tipoUsuario=" + tipoUsuario + "]";
	}
	
	
}
