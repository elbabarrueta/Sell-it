package ventanas;

import java.io.IOException;


import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import BasesDeDatos.BaseDeDatos;

import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.logging.Handler;
import java.util.logging.FileHandler;
import java.util.logging.StreamHandler;
import java.util.logging.SimpleFormatter;
import clases.Usuario;

public class Main {
	
    private static Logger logger = Logger.getLogger(Main.class.getName());

	static HashMap<String, Usuario> mapaUsu;
	private static VentanaInicio ventana1;
	static BaseDeDatos base; 
	private static VentanaLogoPrincipal ventanaLogo;

	public static void main(String[] args) {

		try {
			// Crear e inicializar la ventana de presentación (logo)
			ventanaLogo = new VentanaLogoPrincipal();
			ventanaLogo.setVisible(true);
			ventanaLogo.setExtendedState(JFrame.MAXIMIZED_BOTH);
			// Dormir el hilo principa durante 5 segundos
			Thread.sleep(5000);
			// Crear e inicializar la ventana principal de la aplicación
			ventana1 = new VentanaInicio();
			ventana1.setVisible(true);
			ventana1.toFront();

		} catch (Exception e) {
			// Manejo de excepciones severas durante la inicialización
			logger.log(Level.SEVERE, "Error en el main.", e);
			JOptionPane.showMessageDialog(ventana1, "Error grave: contacta con los informáticos.", "ERROR", JOptionPane.ERROR_MESSAGE);
		}

		// Configuración del registro logging
		logger.setLevel(Level.ALL);
		try {
			// Configuración de manejadores de registro (consola y archivo)
		    Handler consoleHandler = new StreamHandler(System.out, new SimpleFormatter());
		    consoleHandler.setLevel(Level.FINEST);

		    Handler fileHandler = new FileHandler("Main.log.xml");
		    fileHandler.setLevel(Level.ALL);

		    logger.addHandler(consoleHandler);
		    logger.addHandler(fileHandler);
		} catch (Exception e) {
			// Manejo de excepciones relacionadas con la configuracion del registro
		    logger.log(Level.SEVERE, e.toString(), e);
		}
		
	}

	// Establecer la ventana de inicio actual
	public static void setVentanaInicio(VentanaInicio v) {
		ventana1 = v;
	}
	// Obtener la ventana de inicio actual
	public static VentanaInicio getVentanaInicio() {
        return ventana1;
    }
	
}
	
