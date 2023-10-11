package ventanas;

import java.awt.*;
import javax.swing.*;

public class VentanaVentaEntidad extends JFrame{
	public VentanaVentaEntidad() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(600, 500);
		setLocationRelativeTo(null);
		setTitle("Venta Entidad");
		
		
		JPanel pCentral = new JPanel(new GridLayout(4,2));
		this.add(pCentral, BorderLayout.CENTER);
		
		JPanel pInferior = new JPanel(new BorderLayout());
		this.add(pInferior, BorderLayout.SOUTH);
		
	
		JLabel lDesc = new JLabel("Descripción del evento");
		pCentral.add(lDesc);
		JTextField tfDesc = new JTextField();
		pCentral.add(tfDesc);
		
		JLabel lFecha = new JLabel("Fecha del evento");
		pCentral.add(lFecha);
		JTextField tfFecha = new JTextField();
		pCentral.add(tfFecha);
		
		JLabel lCant = new JLabel("Cantidad de entradas disponibles");
		pCentral.add(lCant);
		JTextField tfCant = new JTextField();
		pCentral.add(tfCant);
		
		JLabel lPrecio = new JLabel("Precio por entrada");
		pCentral.add(lPrecio);
		JTextField tfPrecio = new JTextField();
		pCentral.add(tfPrecio);
		
		
	
		JButton bSubir = new JButton("Subir entrada");
		pInferior.add(bSubir, BorderLayout.EAST);
		
		
	}
	public static void main(String[] args) {
		VentanaVentaEntidad v = new VentanaVentaEntidad();
		v.setVisible(true);
	}
}
