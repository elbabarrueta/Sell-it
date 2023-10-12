package ventanas;

import java.awt.*;
import javax.swing.*;

public class VentanaVentaEntidad extends JFrame{
	
	private JTextField tfDesc = new JTextField();
	private JTextField tfFecha = new JTextField();
	private JTextField tfCant = new JTextField();
	private JTextField tfPrecio = new JTextField();
	
	public VentanaVentaEntidad() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(600, 500);
		setLocationRelativeTo(null);
		setTitle("Venta Entidad");
		
		//Creamos los paneles
		JPanel pSuperior = new JPanel(new BorderLayout());
		this.add(pSuperior, BorderLayout.NORTH);
		
		JPanel pCentral = new JPanel(new GridLayout(4,2));
		this.add(pCentral, BorderLayout.CENTER);
		
		JPanel pInferior = new JPanel(new BorderLayout());
		this.add(pInferior, BorderLayout.SOUTH);
		
		//Panel Superior
		JButton bMiperfil = new JButton("MI PERFIL");
		pSuperior.add(bMiperfil, BorderLayout.EAST);
		
		//Panel Cental
		JLabel lDesc = new JLabel("Descripción del evento");
		pCentral.add(lDesc);
		pCentral.add(tfDesc);
		
		JLabel lFecha = new JLabel("Fecha del evento");
		pCentral.add(lFecha);
		pCentral.add(tfFecha);
		
		JLabel lCant = new JLabel("Cantidad de entradas disponibles");
		pCentral.add(lCant);
		pCentral.add(tfCant);
		
		JLabel lPrecio = new JLabel("Precio por entrada");
		pCentral.add(lPrecio);
		pCentral.add(tfPrecio);
		
		//Panel Inferior
		JButton bSubir = new JButton("Subir entrada");
		pInferior.add(bSubir, BorderLayout.EAST);
		JButton bVprincipal = new JButton("Ventana Principal");
		pInferior.add(bVprincipal, BorderLayout.WEST);
		
		
	}
	public static void main(String[] args) {
		VentanaVentaEntidad v = new VentanaVentaEntidad();
		v.setVisible(true);
	}
}
