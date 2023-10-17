package ventanas;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class VentanaInicio extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public VentanaInicio() {
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setBounds(500,300, 400, 400);
		this.setTitle("Sell-It");
		
		
		JPanel panelVentanaInicio = new JPanel(new BorderLayout());
		JPanel panelSur = new JPanel(new BorderLayout());
		JPanel panelNorte = new JPanel(new BorderLayout());
		JPanel panelCentro = new JPanel(new GridLayout(1,4));
		panelVentanaInicio.add(panelSur, BorderLayout.SOUTH);
		panelVentanaInicio.add(panelNorte, BorderLayout.NORTH);
		panelVentanaInicio.add(panelCentro,BorderLayout.CENTER);
		panelNorte.setLayout(new FlowLayout(FlowLayout.CENTER));
		panelSur.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		
		
		
		this.getContentPane().add(panelVentanaInicio);
		
		
		
		
		
		
		JTextField txtUsuario = new JTextField();
		JPasswordField txtContrasenia = new JPasswordField();
		JLabel etiquetaUsuario = new JLabel("Usuario:");
		JLabel etiquetaContrasenia = new JLabel("Contrasenia:");
		
		
		
		
		
		
		
		JButton botonRegistro = new JButton("Registrarse");
		JButton botonIniciarSesion = new JButton("Iniciar Sesion");
		
		JLabel etiquetaBienvenido = new JLabel("Bienvenido a Sell-It");
		
		panelNorte.add(etiquetaBienvenido,BorderLayout.NORTH);
		panelSur.add(botonRegistro);
		panelSur.add(botonIniciarSesion);
		
		panelCentro.add(etiquetaUsuario);
		panelCentro.add(txtUsuario);
		panelCentro.add(etiquetaContrasenia);
		panelCentro.add(txtContrasenia);
		
		Dimension tamanioJTxt = new Dimension(100,20);
		txtUsuario.setPreferredSize(tamanioJTxt);
		txtContrasenia.setPreferredSize(tamanioJTxt);
		
	
		

	
	}

}
