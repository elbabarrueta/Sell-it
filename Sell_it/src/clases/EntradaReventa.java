package clases;

public class EntradaReventa {
    // Atributos de la entrada de reventa
	private int codigoEntrada;
	private double precioReventa;
	private String correoUsuario;
	private String info;

	/**
     * Constructor de la clase.
     * @param codigoEntrada Código único de la entrada de reventa.
     * @param precioReventa Precio de reventa asociado a la entrada.
     * @param correoUsuario Correo del usuario vendedor de la entrada.
     * @param info Información adicional sobre la entrada.
     */
	public EntradaReventa(int codigoEntrada, double precioReventa, String correoUsuario, String info) {
		this.codigoEntrada = codigoEntrada;
		this.precioReventa = precioReventa;
		this.correoUsuario = correoUsuario;
		this.info = info;
	}

    // Métodos de acceso para los atributos

	public final int getCodigoEntrada() {
		return codigoEntrada;
	}
	public final void setCodigoEntrada(int codigoEntrada) {
		this.codigoEntrada = codigoEntrada;
	}
	public final double getPrecioReventa() {
		return precioReventa;
	}
	public final void setPrecioReventa(double precioReventa) {
		this.precioReventa = precioReventa;
	}
	public final String getCorreoUsuario() {
		return correoUsuario;
	}
	public final void setCorreoUsuario(String correoUsuario) {
		this.correoUsuario = correoUsuario;
	}
	public final String getInfo() {
		return info;
	}
	public final void setInfo(String info) {
		this.info = info;
	}

	/**
     * Devuelve una representación en cadena de la entrada de reventa.
     * @return Cadena que representa la entrada de reventa.
     */
	@Override
	public String toString() {
		return "EntradaReventa [codigoEntrada=" + codigoEntrada + ", precioReventa=" + precioReventa
				+ ", correoUsuario=" + correoUsuario + ", info=" + info + "]";
	}
	    
}
