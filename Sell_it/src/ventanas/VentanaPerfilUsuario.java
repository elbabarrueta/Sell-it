package ventanas;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;


public class VentanaPerfilUsuario extends JFrame{

	private JPanel panelBotones;
	private JPanel panelPrincipal;
	private JPanel panelInformacion;
	
	private Usuario usuario;
	
	public VentanaPerfilUsuario(Usuario usuario) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(600, 500);
		setLocationRelativeTo(null);
		setTitle("Mi perfil");
		
		panelPrincipal = new JPanel();
		add(panelPrincipal, BorderLayout.CENTER);
		
		panelInformacion = new JPanel();
		add(panelInformacion, BorderLayout.NORTH);
		panelInformacion.setLayout(new BorderLayout() );
		
		JLabel labelDescrip = new JLabel("Descripcion:");
		JTextField textoDescrip = new JTextField(50);
		
		JPanel panelInf = new JPanel();
		JPanel panelInfC = new JPanel();
		panelInfC.setLayout(new GridLayout(2,1));
		
		panelBotones = new JPanel();
		add(panelBotones, BorderLayout.SOUTH);
		
		JButton botonInformacion = new JButton("+ Informacion");
		JButton botonValoraciones = new JButton("Valoraciones");
		JButton botonEnVenta = new JButton("En venta"); 
		
		JLabel labelNombre = new JLabel(usuario.getNombreUsuario());
		JLabel labelMail = new JLabel("Email: " + usuario.getCorreoUsuario());
		
		panelPrincipal.add(labelDescrip);
		panelPrincipal.add(textoDescrip);
		panelInformacion.add(panelInfC, BorderLayout.CENTER);
		panelInfC.add(labelNombre);
		panelInfC.add(labelMail);
		panelInformacion.add(panelInf, BorderLayout.SOUTH);
		panelInf.add(botonEnVenta);
		panelInf.add(botonValoraciones);
		panelInf.add(botonInformacion);
		
		JButton botonGuardar = new JButton("Guardar");
		panelBotones.add(botonGuardar);
		
		setVisible(true);
	}
	
	public static void main(String[] args) {
		Usuario usuarioNormal = new Usuario("Lucas Gomez Lopez", "lucas.gomez@gmail.com", "Usuario Corriente");
		
		VentanaPerfilUsuario vent = new VentanaPerfilUsuario(usuarioNormal);
		
	}

}
