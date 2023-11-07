package ventanas;

import java.io.File;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import clases.Usuario;




public class DataSetUsuario {
	
	private Usuario usuario; //-----nuevo

	//private List<Usuario> usuariosGuardados = new ArrayList<Usuario>();
	private HashMap <String, Usuario> mapaUsu = new HashMap<>(); //-----nuevo
	
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
				Usuario usu = new Usuario(nombre, correo, tipoUsu, contrasena );
				//usuariosGuardados.add( usu );
				mapaUsu.put(usuario.getCorreoUsuario(), usu); //----- aqui uso el correo como clave unica
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

//	public final void setUsuariosGuardados(List<Usuario> usuariosGuardados) {
//		this.usuariosGuardados = usuariosGuardados;
//	}
	
	public final void setUsuariosGuardados(HashMap<String, Usuario> mapaUsu) { //-----nuevo
		this.mapaUsu = mapaUsu;
	}
	
	public void anyadirUsuario(Usuario u) {
		mapaUsu.put(u.getContrasena(), u);
	}
}
