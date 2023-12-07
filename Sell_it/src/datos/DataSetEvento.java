package datos;

import java.io.File;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import clases.Evento;

public class DataSetEvento {
	
	private Evento evento; //-----nuevo

	private static HashMap <String, Evento> mapaEvento = new HashMap<>(); //-----nuevo
	
	public static HashMap<String, Evento> getMapaEvento() {
		return mapaEvento;
	}

	public DataSetEvento( String nombreFichero ) throws IOException {
		File ficEvento = new File( nombreFichero );
		Scanner lecturaFic = null;
		if (ficEvento.exists()) {
			lecturaFic = new Scanner( ficEvento );
		} else {
			lecturaFic = new Scanner( DataSetEvento.class.getResourceAsStream( nombreFichero ) );
		}
		int numLinea = 0;
		while (lecturaFic.hasNextLine()) {
			numLinea++;
			String linea = lecturaFic.nextLine();
			String[] partes = linea.split( "," );
			try {
				String nombre = partes[0];
				String desc = partes[1];
				String fecha = partes[2];
				String ubicacion = partes[3];
				int nEntradas = Integer.parseInt(partes[4]);
				double precio = Double.parseDouble(partes[5]);
				Evento evento = new Evento(nombre, desc, fecha, ubicacion, nEntradas );
				mapaEvento.put(desc, evento); //----- aqui uso a descripcion como clave unica
			} catch (IndexOutOfBoundsException | NumberFormatException e) {
				System.err.println( "Error en lectura de línea " + numLinea );
			}
		}
	}
	
	public final HashMap<String, Evento> getEventosGuardados() { //-----nuevo
		return mapaEvento;
		
	}
	
	public final void setEventosGuardados(HashMap<String, Evento> mapaEvento) { //-----nuevo
		this.mapaEvento = mapaEvento;
	}
	
	public static void anyadirEvento(Evento e) {
		mapaEvento.put(e.getDesc(), e);
	}
	public static Evento buscarEvento(String nombre) {
		return mapaEvento.get(nombre);
	}
	
	public Evento getEventoPorDescripcion(String desc) {
		if(mapaEvento.containsKey(desc)) {
			return mapaEvento.get(desc);
		}
		else {
			return null;
		}
	}
	public boolean existeEvento(String fecha, String ubicacion, String nombre) {
		for (Evento evento : mapaEvento.values()) {
	        if (evento.getFecha().equals(fecha) & evento.getUbicacion().equals(ubicacion) & evento.getNombre().equals(nombre)) {	            
	                System.out.println("El evento coincide con el evento " + evento.getNombre() +", de fecha " + evento.getFecha() + " y de ubicacion " + evento.getUbicacion() + ".");
	        }
	            return true;
		}
		return false;
    }
	public void guardarMapaEventosEnFichero(String nomfich) {
        try {
            PrintWriter pw = new PrintWriter(nomfich);
            for (Evento e: mapaEvento.values() ) {
//                pw.println(e.getNombre() + "," + e.getDesc() + "," + e.getFecha() + "," + e.getUbicacion() + "," + e.getnEntradas() + "," + e.getPrecio());
            }
            pw.flush();
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
	

	
}
