package ventanas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class VentanaPerfilUsuario extends JFrame{

	private JPanel panelBotones;
	private JPanel panelPrincipal;
	
	public VentanaPerfilUsuario() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(600, 500);
		setLocationRelativeTo(null);
		setTitle("Mi perfil");
		
		panelPrincipal = new JPanel();
		add(panelPrincipal, BorderLayout.CENTER);
		panelPrincipal.setLayout(new GridLayout(4,1) );
		
		panelBotones = new JPanel();
		add(panelBotones, BorderLayout.SOUTH);
		
		JTextField textoNombre = new JTextField(20);
		JLabel labelNombre = new JLabel("Nombre:");
		JTextField textoApellido = new JTextField(20);
		JLabel labelApellido = new JLabel("Apellidos:");
		JTextField textoMail = new JTextField(20);
		JLabel labelMail = new JLabel("Direccion de correo electronico:");
		
		panelPrincipal.add(labelNombre);
		panelPrincipal.add(textoNombre);
		panelPrincipal.add(labelApellido);
		panelPrincipal.add(textoApellido);
		panelPrincipal.add(labelMail);
		panelPrincipal.add(textoMail);
		
		JButton botonGuardar = new JButton("Guardar");
		panelBotones.add(botonGuardar);
		
		setVisible(true);
	}
	
	public static void main(String[] args) {
		VentanaPerfilUsuario vent = new VentanaPerfilUsuario();
		
	}

}
