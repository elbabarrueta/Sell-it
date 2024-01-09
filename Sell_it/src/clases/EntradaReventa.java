package clases;

public class EntradaReventa {
	    private int codigoEntrada;
	    private double precioReventa;
	    private String correoUsuario;

	    public EntradaReventa(int codigoEntrada, double precioReventa, String correoUsuario) {
	        this.codigoEntrada = codigoEntrada;
	        this.precioReventa = precioReventa;
	        this.correoUsuario = correoUsuario;
	    }

		public int getCodigoEntrada() {
			return codigoEntrada;
		}

		public double getPrecioReventa() {
			return precioReventa;
		}

		public String getCorreoUsuario() {
			return correoUsuario;
		}


	    // Métodos getter y setter
		
		
	    

}
