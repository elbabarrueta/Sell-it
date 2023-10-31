package ventanas;

import java.io.IOException;

import clases.Usuario;



public class Main {
	
	private static DataSetUsuario dataset;
	private static VentanaInicio ventana1;
	public static void main(String[] args) {
		ventana1 = new VentanaInicio();
		ventana1.setVisible(true);
		cargaUsuarios();
		ventana1.cargarUsuariosInicio(dataset);
		
		
	}
	private static void cargaUsuarios() {
		try {
			dataset = new DataSetUsuario( "usuariosBase.txt" );
			System.out.println( "Cargados usuarios:" );
			for (Usuario u : dataset.getUsuariosGuardados() ) {
				System.out.println( "\t" + u );
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
	
	public static VentanaInicio getVentanaInicio() {
        return ventana1;
    }
}
	
