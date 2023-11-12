package ventanas;

import java.io.IOException;
import java.util.Map;

import javax.swing.JFrame;

import clases.Usuario;



public class Main {
	
	private static DataSetUsuario dataset;
	private static VentanaInicio ventana1;
	public static void main(String[] args) {

		ventana1 = new VentanaInicio();
		ventana1.setVisible(true);
		cargaUsuarios();
		VentanaInicio.cargarUsuariosInicio(dataset);
//		ventana1.cargarUsuariosInicio(dataset);
		
		
	}
	private static void cargaUsuarios() {
		try {
			dataset = new DataSetUsuario( "usuariosBase.txt" );
			System.out.println( "Cargados usuarios:" );
			for (Map.Entry<String, Usuario> entry : dataset.getMapaUsu().entrySet()) {
	            Usuario usu = entry.getValue();
	            System.out.println( "\t" + usu);
	        }
	/**		 
			ventanaDatos = new Ventana( ventana );
			ventanaDatos.setDatos( dataset );
			ventanaDatos.setVisible( true );
	**/		
			
		} catch (IOException e) {
			System.err.println( "Error en carga de usuarios" );
		}
	}
	
	public static void setVentanaInicio(VentanaInicio v) {
		ventana1 = v;
	}
	public static VentanaInicio getVentanaInicio() {
        return ventana1;
    }
	
}
	
