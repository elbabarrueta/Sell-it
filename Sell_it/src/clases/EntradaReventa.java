package clases;

public class EntradaReventa {
	    private int codigoEntrada;
	    private double precioReventa;
	    private String correoUsuario;
	    private String info;

	    public EntradaReventa(int codigoEntrada, double precioReventa, String correoUsuario, String info) {
	        this.codigoEntrada = codigoEntrada;
	        this.precioReventa = precioReventa;
	        this.correoUsuario = correoUsuario;
	        this.info = info;
	    }

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

		@Override
		public String toString() {
			return "EntradaReventa [codigoEntrada=" + codigoEntrada + ", precioReventa=" + precioReventa
					+ ", correoUsuario=" + correoUsuario + ", info=" + info + "]";
		}
	    

}
