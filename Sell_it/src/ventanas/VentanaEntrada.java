package ventanas;

import java.awt.*;
import javax.swing.*;

import clases.Entrada;

public class VentanaEntrada extends JFrame{
	
	private JTextField tfCantidad = new JTextField(); //Se puede cambiar por un JComboBox 
	
	public VentanaEntrada(Entrada e) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(600, 500);
		setLocationRelativeTo(null);
		setTitle("Entrada");
		this.setLayout(new GridLayout(4,1));
		
		JLabel lNombre = new JLabel(e.getCod());
		this.add(lNombre);
		JLabel lFecha = new JLabel(e.getFecha());
		this.add(lFecha);
		JLabel lDesc = new JLabel(e.getDesc());
		this.add(lDesc);
		
		JPanel pCantidad = new JPanel(new GridLayout(1,4));
		JLabel lCantidad = new JLabel("Cantidad");
		pCantidad.add(lCantidad);
		pCantidad.add(tfCantidad);
		JLabel lPrecio = new JLabel("Precio Total");
		pCantidad.add(lPrecio);
		JLabel lTotal = new JLabel();
		pCantidad.add(lTotal);
		this.add(pCantidad);
		
	}
	
	public static void main(String[] args) {
		Entrada e = new Entrada("Moma Halloween", "Entradas para Moma en Halloween con una consumición. 00:00-05:30", "31 de octubre", 20);
		VentanaEntrada v = new VentanaEntrada(e);
		v.setVisible(true);
	}

}
