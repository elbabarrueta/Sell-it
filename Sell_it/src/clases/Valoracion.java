package clases;

public class Valoracion {
	private int id;
    private String comentario;
    private int calificacion;
    private String usuarioCorreo;

    public Valoracion(String comentario, int calificacion, String usuarioCorreo) {
        this.comentario = comentario;
        this.calificacion = calificacion;
        this.usuarioCorreo = usuarioCorreo;
    }

	public final int getId() {
		return id;
	}

	public final void setId(int id) {
		this.id = id;
	}

	public final String getComentario() {
		return comentario;
	}

	public final void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public final int getCalificacion() {
		return calificacion;
	}

	public final void setCalificacion(int calificacion) {
		this.calificacion = calificacion;
	}

	public final String getUsuarioCorreo() {
		return usuarioCorreo;
	}

	public final void setUsuarioCorreo(String usuarioCorreo) {
		this.usuarioCorreo = usuarioCorreo;
	}
    
}
