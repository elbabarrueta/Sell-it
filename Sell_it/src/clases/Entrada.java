package clases;

public class Entrada{
	//Atributos
	private int cod; //codigo unico 
	private Evento eventoAsociado;
	private Usuario propietario;
	private double precio;
	
	//Getters y Setters
	public int getCod() {
		return cod;
	}

	public Evento getEventoAsociado() {
		return eventoAsociado;
	}
	public void setEventoAsociado(Evento eventoAsociado) {
		this.eventoAsociado = eventoAsociado;
	}
	public Usuario getPropietario() {
		return propietario;
	}
	public void setPropietario(Usuario propietario) {
		this.propietario = propietario;
	}
	public double getPrecio() {
		return precio;
	}
	public void setPrecio(double precio) {
		this.precio = precio;
	}
	
	//Constructores
	/**
     * Constructor de la clase Entrada.
     * @param cod Código único de la entrada.
     * @param eventoAsociado Evento al que está asociada la entrada.
     * @param propietario Propietario de la entrada.
     * @param precio Precio de la entrada.
     */
	public Entrada(int cod, Evento eventoAsociado, Usuario propietario, double precio) {
		super();
		this.cod = cod;
		this.eventoAsociado = eventoAsociado;
		this.propietario = propietario;
		this.precio = precio;
	}
	
	/**
     * Constructor por defecto de la clase Entrada.
     */
	public Entrada() {
        super();
        this.cod = 0; 
        this.eventoAsociado = null; 
        this.propietario = null; 
        this.precio = 0.0; 
    }
	
	@Override
	public String toString() {
		return "Entrada [codigo=" + cod + ",eventoAsociado= "+ eventoAsociado + ",propietario= "+ propietario + ",precio=" + precio + "]";
	}
}
