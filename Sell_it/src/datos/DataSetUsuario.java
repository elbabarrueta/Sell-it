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

import clases.Usuario;

public class DataSetUsuario {
	
	private Usuario usuario; //-----nuevo

	//private List<Usuario> usuariosGuardados = new ArrayList<Usuario>();
	private static HashMap <String, Usuario> mapaUsu = new HashMap<>(); //-----nuevo
	
	public HashMap<String, Usuario> getMapaUsu() {
		return mapaUsu;
	}

	public DataSetUsuario( String nombreFichero ) throws IOException {
		File ficUsuarios = new File( nombreFichero );
		Scanner lecturaFic = null;
		if (ficUsuarios.exists()) {
			lecturaFic = new Scanner( ficUsuarios );
		} else {
			lecturaFic = new Scanner( DataSetUsuario.class.getResourceAsStream( nombreFichero ) );
		}
		int numLinea = 0;
		while (lecturaFic.hasNextLine()) {
			numLinea++;
			String linea = lecturaFic.nextLine();
			String[] partes = linea.split( "," );
			try {
				String nombre = partes[0];
				String correo = partes[1];
				String tipoUsu = partes[2];
				String contrasena = partes[3];
				Usuario usu = new Usuario(nombre, correo, tipoUsu, contrasena, contrasena );
				//usuariosGuardados.add( usu );
				usu.setUltimaCambioContrasena(LocalDate.of(2000, 1, 1));
			//	usu.cambiarContrasena("contrasenaInicial");
				
				mapaUsu.put(correo, usu); //----- aqui uso el correo como clave unica
			} catch (IndexOutOfBoundsException | NumberFormatException e) {
				System.err.println( "Error en lectura de línea " + numLinea );
			}
		}
	}

//	public final List<Usuario> getUsuariosGuardados() {
//		return usuariosGuardados;
//	}
	
	public final HashMap<String, Usuario> getUsuariosGuardados() { //-----nuevo
		return mapaUsu;
		
	}
	
	public final void setUsuariosGuardados(HashMap<String, Usuario> mapaUsu) { //-----nuevo
		this.mapaUsu = mapaUsu;
	}
	
	public static void anyadirUsuario(Usuario u) {
		mapaUsu.put(u.getCorreoUsuario(), u);
	}
	public static Usuario buscarUsu(String correo) {
		return mapaUsu.get(correo);
	}
	
//	public Usuario getUsuarioPorCorreo(String correo) {
//		if(mapaUsu.containsKey(correo)) {
//			return mapaUsu.get(correo);
//		}
//		else {
//			return null;
//		}
//	}
	public boolean existeUsuario(String nombre, String correo) {
		for (Usuario usuario : mapaUsu.values()) {
	        if (usuario.getNombreUsuario().equals(nombre) || usuario.getCorreoUsuario().equals(correo)) {
	            if (usuario.getNombreUsuario().equals(nombre)) {
	                System.out.println("El nombre coincide para el usuario: " + usuario.getNombreUsuario());
	            }
	            if (usuario.getCorreoUsuario().equals(correo)) {
	                System.out.println("El correo coincide para el usuario: " + usuario.getCorreoUsuario());
	            }
	            return true;
	        }
	    }
	    return false; 
    }
	public void guardarMapaUsuariosEnFichero(String nomfich) {
        try {
            PrintWriter pw = new PrintWriter(nomfich);
            for (Usuario u: mapaUsu.values() ) {
                pw.println(u.getNombreUsuario() + "," + u.getCorreoUsuario() + "," + u.getTipoUsuario() + "," + u.getContrasena());
            }
            pw.flush();
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
	

	
}
