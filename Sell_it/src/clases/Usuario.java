package clases;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import BasesDeDatos.BaseDeDatos;
import ventanas.Main;
//Vamos a añadir un usuario admin
public class Usuario {
	//Hay que cambiar los atributos
	public String nombreUsuario;
	public String correoUsuario;
	public String tipoUsuario;
	public String contrasena;
	public String imgPerfil;
	private List<Notificacion> notificaciones;

	
//	private String rutaImagenPerfil;
	
	// Fecha de registro
	private LocalDate fechaRegistro;
    private LocalDate ultimaCambioContrasena;
	
	//
    
//    public String getRutaImagenPerfil() {
//        return rutaImagenPerfil;
//    }
//
//    public void setRutaImagenPerfil(String rutaImagenPerfil) {
//        this.rutaImagenPerfil = rutaImagenPerfil;
//    }
    
    
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
	public List<Notificacion> getNotificaciones() {
        return notificaciones;
    }
	
	//
	public Usuario() {
		super();
		this.nombreUsuario = " ";
		this.correoUsuario = " ";
		this.tipoUsuario = " ";
		this.contrasena = " ";
		this.imgPerfil = "Sell_it/src/imagenes/perfil.png";
        this.notificaciones = new ArrayList<>();
	}
	public Usuario(String nombreUsuario, String correoUsuario, String tipoUsuario, String contrasena, String imgPerfil) {
		super();
		this.nombreUsuario = nombreUsuario;
		this.correoUsuario = correoUsuario;
		this.tipoUsuario = tipoUsuario;
		this.contrasena = contrasena;
		this.imgPerfil = imgPerfil;
        this.notificaciones = new ArrayList<>();
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
	
	public void agregarNotificacion(Notificacion notificacion) {
        notificaciones.add(notificacion);
        BaseDeDatos.guardarNotificacion(this, notificacion);
    }
	public static HashMap<String, Usuario> getMapaUsuarios() {
    	return Main.getVentanaInicio().mapaUsu;
    }
	public static void distribuirNotificacion(Notificacion notificacion, Usuario usuarioActual) {
        HashMap<String, Usuario> mapaUsuarios = getMapaUsuarios();
        for (Usuario usuario : mapaUsuarios.values()) {
            if (!usuario.equals(usuarioActual)) {
                usuario.agregarNotificacion(notificacion);
            }
        }
    }
    public void cargarNotificacionesDesdeBD() {
        // Lógica para cargar notificaciones desde la base de datos
        List<Notificacion> notificacionesDesdeBD = BaseDeDatos.obtenerNotificacionesPorUsuario(this);
        notificaciones.addAll(notificacionesDesdeBD);
    }
	
}
