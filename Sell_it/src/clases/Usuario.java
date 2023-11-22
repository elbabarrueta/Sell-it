package clases;

import java.time.LocalDate;

public class Usuario {
	//Hay que cambiar los atributos
	public String nombreUsuario;
	public String correoUsuario;
	public String tipoUsuario;
	public String contrasena;
	public String imgPerfil;
	
	// Fecha de registro
	private LocalDate fechaRegistro;
    private LocalDate ultimaCambioContrasena;
	
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
	public final String getImgPerfil() {
		return imgPerfil;
	}
	public final void setImgPerfil(String imgPerfil) {
		this.imgPerfil = imgPerfil;
	}
	public final String getContrasena() {
		return contrasena;
	}
	public final void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}
	//
	public final LocalDate getUltimaCambioContrasena() {
		return ultimaCambioContrasena;
	}
	public final void setUltimaCambioContrasena(LocalDate ultimaCambioContrasena) {
		this.ultimaCambioContrasena = ultimaCambioContrasena;
	}
	
	//
	public Usuario() {
		super();
		this.nombreUsuario = " ";
		this.correoUsuario = " ";
		this.tipoUsuario = " ";
		this.contrasena = " ";
		this.imgPerfil = null;
	}
	public Usuario(String nombreUsuario, String correoUsuario, String tipoUsuario, String contrasena) {
		super();
		this.nombreUsuario = nombreUsuario;
		this.correoUsuario = correoUsuario;
		this.tipoUsuario = tipoUsuario;
		this.contrasena = contrasena;
		this.imgPerfil = null;
	}
	//
	@Override
	public String toString() {
		return "Usuario [nombreUsuario=" + nombreUsuario + ", correoUsuario="
				+ correoUsuario + ", tipoUsuario=" + tipoUsuario +", contraseña:"+ contrasena+ "]";
	}
	
	//Metodos
	public void cambiarContrasena(String nuevaContrasena) {
        // Código para cambiar la contraseña
        ultimaCambioContrasena = LocalDate.now();
		this.contrasena = nuevaContrasena;
    }
	
}
