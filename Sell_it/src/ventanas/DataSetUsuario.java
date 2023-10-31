package ventanas;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import clases.Usuario;

public class DataSetUsuario {

	private List<Usuario> usuariosGuardados = new ArrayList<Usuario>();

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
				usuariosGuardados.add( usu );
			} catch (IndexOutOfBoundsException | NumberFormatException e) {
				System.err.println( "Error en lectura de línea " + numLinea );
			}
		}
	}

	public final List<Usuario> getUsuariosGuardados() {
		return usuariosGuardados;
	}

	public final void setUsuariosGuardados(List<Usuario> usuariosGuardados) {
		this.usuariosGuardados = usuariosGuardados;
	}
}
