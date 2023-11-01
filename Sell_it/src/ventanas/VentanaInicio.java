package ventanas;

import java.awt.BorderLayout;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import clases.Usuario;

public class VentanaInicio extends JFrame {
	
	/**
	 * 
	 */
	private DataSetUsuario dataSetUsuario;
	
	private Usuario usuarioActual;
	
	private static final long serialVersionUID = 1L;

	public VentanaInicio() {
		super();
		//Características de la ventana principal
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setBounds(500,300, 400, 400);
		this.setTitle("Sell-It");
		
		//Ceracion de paneles 
		JPanel panelVentanaInicio = new JPanel(new BorderLayout());
		JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JPanel panelNorte = new JPanel(new BorderLayout());
		JPanel panelCentro = new JPanel(new GridLayout(3,2));
		
		panelVentanaInicio.add(panelSur, BorderLayout.SOUTH);
		panelVentanaInicio.add(panelNorte, BorderLayout.NORTH);
		panelVentanaInicio.add(panelCentro,BorderLayout.CENTER);
		
		panelNorte.setLayout(new FlowLayout(FlowLayout.CENTER));
		panelSur.setLayout(new FlowLayout(FlowLayout.CENTER));

		
		this.getContentPane().add(panelVentanaInicio);

		//Creacion de los JTextFields, JLabels, JButtons y JPasswordField
		
		JTextField txtUsuario = new JTextField();
		JPasswordField txtContrasenia = new JPasswordField();
		JLabel etiquetaUsuario = new JLabel("Usuario:");
		JLabel etiquetaContrasenia = new JLabel("Contrasenia:");

		
		JButton botonRegistroEntidad = new JButton("Registro Entidad");
		JButton botonRegistroUsuario = new JButton("Registro Usuario");
		JButton botonIniciarSesion = new JButton("Iniciar Sesion");
		
		JLabel etiquetaBienvenido = new JLabel("Bienvenido a Sell-It");
		
		JLabel etiquetaPregunta = new JLabel("¿No tienes cuenta?");
		
		
		//Añadimos los elementos previamente creados a los paneles
		
		panelNorte.add(etiquetaBienvenido,BorderLayout.NORTH);
		panelSur.add(etiquetaPregunta);
		panelSur.add(botonRegistroEntidad);
		panelSur.add(botonRegistroUsuario);
	//	panelSur.add(botonIniciarSesion);
		
		panelCentro.add(etiquetaUsuario);
		panelCentro.add(txtUsuario);
		panelCentro.add(etiquetaContrasenia);
		panelCentro.add(txtContrasenia);
		
		JPanel panel = new JPanel();
		panelCentro.add(panel);
		panel.add(botonIniciarSesion);
	
		
	
		
		//Eventos
		
		
		botonRegistroUsuario.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				VentanaRegistroUsuario VentanaRegistroUsuario = new VentanaRegistroUsuario();
				VentanaRegistroUsuario.setVisible(true);	
			}
		});
		
		botonRegistroEntidad.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				VentanaRegistroEntidad ventanaRegistroEntidad = new VentanaRegistroEntidad();
				ventanaRegistroEntidad.setVisible(true);
			}
		});
	
		botonIniciarSesion.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String iD = txtUsuario.getText();
				String contrasenia = txtContrasenia.getText();
				
				Usuario usuari = obtenerNombreUsuario(iD, contrasenia);
				 if (verificarCredenciales(iD, contrasenia)) {
				       JOptionPane.showMessageDialog(null, "Bienvenido de nuevo " + iD);
				       		// ahora se cierra esta ventana y se abre la principal
				       usuarioActual = usuari;
				       dispose();
				       VentanaPrincipal principV = new VentanaPrincipal();
				       principV.setVisible(true);
				        // Realiza acciones adicionales cuando el inicio de sesión sea exitoso
				 } else {
				       JOptionPane.showMessageDialog(null, "Usuario incorrecto");
				 }
			}
		});
	
	}
	
	private boolean verificarCredenciales(String iD, String contrasenia) {
		 List<Usuario> usuarios = dataSetUsuario.getUsuariosGuardados(); // Obtén la lista de usuarios cargados desde el archivo

		 for (Usuario usuario : usuarios) {
			 if (usuario.getNombreUsuario().equals(iD) && usuario.getContrasena().equals(contrasenia)) {
				 return true; // Las credenciales coinciden
			 }
		 }
		 return false;
	    
	}
	public Usuario getUsuarioActual() {
		return usuarioActual;
	}
	
	public Usuario obtenerNombreUsuario(String iD, String contrasena) {
		List<Usuario> usuarios = dataSetUsuario.getUsuariosGuardados(); // Obtiene la lista de usuarios

	    for (Usuario usuario : usuarios) {
	        if (usuario.getNombreUsuario().equals(iD)) {
	            return usuario;
	        }
	    }
	    return null;
	}
	
	public void cargarUsuariosInicio(DataSetUsuario dataset) {
		this.dataSetUsuario = dataset;
	}
	
	
}
