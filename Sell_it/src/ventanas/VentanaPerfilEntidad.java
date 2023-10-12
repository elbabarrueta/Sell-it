package ventanas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class VentanaPerfilEntidad extends JFrame{

	private JPanel panelPentidad;
	private JPanel panelBentidad;
	
	public VentanaPerfilEntidad(){
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(600, 500);
		setTitle("Mi perfil");
		
		panelPentidad = new JPanel();
		add(panelPentidad, BorderLayout.CENTER);
		
		panelBentidad = new JPanel();
		add(panelBentidad, BorderLayout.SOUTH);
		
		
		setVisible(true);
	}
	public static void main(String[] args) {
		VentanaPerfilEntidad vent = new VentanaPerfilEntidad();
		
	}
	
}
