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

import clases.Entrada;

public class DataSetEntrada {
	
	private Entrada entrada; //-----nuevo

	private static HashMap <String, Entrada> mapaEntrada = new HashMap<>(); //-----nuevo
	
	public HashMap<String, Entrada> getMapaEvento() {
		return mapaEntrada;
	}

	public DataSetEntrada( String nombreFichero ) throws IOException {
		File ficEntrada = new File( nombreFichero );
		Scanner lecturaFic = null;
		if (ficEntrada.exists()) {
			lecturaFic = new Scanner( ficEntrada );
		} else {
			lecturaFic = new Scanner( DataSetEntrada.class.getResourceAsStream( nombreFichero ) );
		}
		int numLinea = 0;
		while (lecturaFic.hasNextLine()) {
			numLinea++;
			String linea = lecturaFic.nextLine();
			String[] partes = linea.split( "," );
			try {
				String cod = partes[0];
				String desc = partes[1];
				String fecha = partes[2];
				double precio = Double.parseDouble(partes[3]);
//				Entrada entrada = new Entrada(cod, desc, fecha, precio);
				mapaEntrada.put(cod, entrada); //----- aqui uso a descripcion como clave unica
			} catch (IndexOutOfBoundsException | NumberFormatException e) {
				System.err.println( "Error en lectura de línea " + numLinea );
			}
		}
	}
	
	public final HashMap<String, Entrada> getEventosGuardados() { //-----nuevo
		return mapaEntrada;
		
	}
	
	public final void setEventosGuardados(HashMap<String, Entrada> mapaEntrada) { //-----nuevo
		this.mapaEntrada = mapaEntrada;
	}
	
	public static void anyadirEntrada(Entrada e) {
//		mapaEntrada.put(e.getCod(), e);
	}
	public static Entrada buscarEntrada(String cod) {
		return mapaEntrada.get(cod);
	}
	
	public Entrada getEntradaPorCodigo(String cod) {
		if(mapaEntrada.containsKey(cod)) {
			return mapaEntrada.get(cod);
		}
		else {
			return null;
		}
	}
	public boolean existeEvento(int cod) {
		for (Entrada entrada : mapaEntrada.values()) {
	        if (entrada.getCod() == cod) {	            
	                System.out.println("La entrada coincide con otra entrada (" + entrada.getCod() +").");
	        }
	            return true;
		}
		return false;
    }
	public void guardarMapaEntradaEnFichero(String nomfich) {
        try {
            PrintWriter pw = new PrintWriter(nomfich);
            for (Entrada e: mapaEntrada.values() ) {
//                pw.println(e.getCod() + "," + e.getDesc() + "," + e.getFecha() + "," + e.getPrecio());
            }
            pw.flush();
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}