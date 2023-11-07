package ventanas;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import clases.Evento;

public class EventosDisponibles {
	
	private List<Evento> lEventos = new ArrayList<Evento>();
	
	public EventosDisponibles( String nombreFichero ) throws IOException {
		File ficheroEventos = new File( nombreFichero );
		Scanner lecturaFic = null;
		if (ficheroEventos.exists()) {
			lecturaFic = new Scanner( ficheroEventos );
		} else {
			lecturaFic = new Scanner( EventosDisponibles.class.getResourceAsStream( nombreFichero ) );
		}
		int numLinea = 0;
		while (lecturaFic.hasNextLine()) {
			numLinea++;
			String linea = lecturaFic.nextLine();
			String[] partes = linea.split( "\t" );
			try {
				String nombreEvento = partes[0];
				int numEntradas = Integer.parseInt(partes[1]);
				String localizacionEvento = partes[2];
				int codigoEvento = Integer.parseInt(partes[3]);
				//Evento e = new Evento(nombreEvento, numEntradas, localizacionEvento, codigoEvento);
				//lEventos.add(e);
			} catch (IndexOutOfBoundsException | NumberFormatException e) {
				System.err.println( "Error en lectura de línea " + numLinea );
			}
		}
	}

	/** Devuelve la lista de eventos
	 * @return	Lista de eventos
	 */
	public List<Evento> getListaEventos() {
		return lEventos;
	}
	
	/** Añade un evento al final
	 * @param e 	evento a añadir
	 */
	public void anyadir( Evento e ) {
		lEventos.add( e );
	}
	
	/** Añade un evento en un punto dado
	 * @param e 	Evento a añadir
	 * @param posicion	Posición relativa del evento a añadir (de 0 a n)
	 */
	public void anyadir( Evento e, int posicion ) {
		lEventos.add( posicion, e );
	}
	
	/** Quita un evento
	 * @param codigoEvento	Código del evento a eliminar
	 */
	public void quitar( int codigoEvento ) {
		for (int i=0; i<lEventos.size(); i++) {
			if (lEventos.get(i).getCodigo() == codigoEvento) {
				lEventos.remove(i);
				return;
			}
		}
	}
}
