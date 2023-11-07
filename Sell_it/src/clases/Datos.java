package clases;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;



public class Datos {
	
	
	private static List<Usuario> usuarios = new ArrayList<>();
	
	
	public static Usuario buscarUsuario(String correo) {
		boolean enc = false;
		int pos = 0;
		Usuario u = null;
		while(!enc && pos<usuarios.size()) {
			u = usuarios.get(pos);
			if(u.getCorreoUsuario().equals(correo)) {
				enc = true;
			}else {
				pos++;
			}
		}
		if(enc) {
			return u;
		}else {
			return null;
		}
	}
	
	
	
	public static void aniadirUsuario(Usuario u) {
	    if (buscarUsuario(u.getCorreoUsuario()) == null) {
	        usuarios.add(u);
	    }
	}
	
	
	public static void cargarFicherosUsuariosEnLista(String nomfich) {
		
		try {
			Scanner sc = new Scanner(new FileReader(nomfich));
			String linea;
			while(sc.hasNext()) {
				linea = sc.nextLine();
				String [] partes = linea.split(";");
				String nombre = partes[0];
				String correo = partes[1];
				String tipo = partes[2];
				String contrasena = partes[3];
				Usuario u = new Usuario(nombre,correo,tipo,contrasena);
				if (buscarUsuario(correo)==null) {
					usuarios.add(u);
				}
				
			}sc.close();}  catch (FileNotFoundException e) {
				e.printStackTrace();
				
			}
			
		}
	public static void guardarListaUsuariosEnLista(String nomfich) {
	    try  {
	    	PrintWriter pw = new PrintWriter(nomfich);
	        for (Usuario u : usuarios) {
	            pw.println(u.getNombreUsuario() + ";" + u.getCorreoUsuario() + ";" + u.getTipoUsuario() + ";" + u.getContrasena());
	            pw.flush();
	            pw.close();}
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    }
	}
}
	
	


