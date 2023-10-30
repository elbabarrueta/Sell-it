package clases;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;



public class Datos {
	
	
	private static List<Ussuario> usuarios = new ArrayList<>();
	
	
	public static Ussuario buscarUsuario(String iD) {
		boolean enc = false;
		int pos = 0;
		Ussuario u = null;
		while(!enc&&pos<usuarios.size()) {
			u = usuarios.get(pos);
			if (u.getId().equals(iD)) {
				enc = true;
			}else {
				pos++;
			}
		}  if (enc) {
			return u;
		}else {
			return null;
		}
	}
	
	
	
	
	
	public static void aniadirUsuario(Ussuario u) {
		if (buscarUsuario(u.getId()== null)) {
			
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
				String id = partes[0];
				String contrasenia = partes[1];
				String nombre = partes[2];
				String direccion = partes[3];
				String codigoPostal = partes[4];
				Ussuario u = new Ussuario(id,contrasenia,nombre,direccion,codigoPostal);
				if (buscarUsuario(id)==null) {
					usuarios.add(u);
				}
				sc.close();
			}}  catch (FileNotFoundException e) {
				e.printStackTrace();
				
			}
			
		}
	public static void guardarListaUsuariosEnLista(String nomfich) {
		try {
			PrintWriter pw = new PrintWriter(nomfich);
			for(Ussuario u: usuarios) {
				pw.println(u.getId()+";"+u.getContrasenia()+";"+u.getNombre()+";"+u.getDireccion()+";"+u.getCodigoPostal());
			}
			pw.flush();
			pw.close();
			
		
	}catch (FileNotFoundException e) {
		e.printStackTrace();}
	

}}
	
	


