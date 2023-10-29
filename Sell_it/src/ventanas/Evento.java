package ventanas;

public class Evento {
	
	private String nomEvento;
	private int nEntradas;
	private String localizacion;
	private int codigo;
	
	public String getNomEvento() {
		return nomEvento;
	}
	public void setNomEvento(String nomEvento) {
		this.nomEvento = nomEvento;
	}
	public int getnEntradas() {
		return nEntradas;
	}
	public void setnEntradas(int nEntradas) {
		this.nEntradas = nEntradas;
	}
	public String getLocalizacion() {
		return localizacion;
	}
	public void setLocalizacion(String localizacion) {
		this.localizacion = localizacion;
	}
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	
	public Evento(String nomEvento, int nEntradas, String localizacion, int codigo) {
		super();
		this.nomEvento = nomEvento;
		this.nEntradas = nEntradas;
		this.localizacion = localizacion;
		this.codigo = codigo;
	}
	@Override
	public String toString() {
		return "Quedan " + nEntradas + ", para el evento " + nomEvento + " que será en " + localizacion
				+ ". [" + codigo + "]";
	}

}
