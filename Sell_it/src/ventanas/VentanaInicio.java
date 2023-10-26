package ventanas;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class VentanaInicio extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public VentanaInicio() {
		super();
		
		//Características de la ventana principal
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setBounds(500,300, 400, 400);
		this.setTitle("Sell-It");
		
		//Ceracion de paneles 
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
		
		
		
		
		//Creacion de los JTextFields, JLabels, JButtons y JPasswordField
		
		JTextField txtUsuario = new JTextField();
		JPasswordField txtContrasenia = new JPasswordField();
		JLabel etiquetaUsuario = new JLabel("Usuario:");
		JLabel etiquetaContrasenia = new JLabel("Contrasenia:");
		
		
		
		
		
		
		
		JButton botonRegistroEntidad = new JButton("Registro Entidad");
		JButton botonRegistroUsuario = new JButton("Registro Usuario");
		JButton botonIniciarSesion = new JButton("Iniciar Sesion");
		
		JLabel etiquetaBienvenido = new JLabel("Bienvenido a Sell-It");
		
		//Añadimos los elementos previamente creados a los paneles
		
		panelNorte.add(etiquetaBienvenido,BorderLayout.NORTH);
		panelSur.add(botonRegistroEntidad);
		panelSur.add(botonRegistroUsuario);
		panelSur.add(botonIniciarSesion);
		
		panelCentro.add(etiquetaUsuario);
		panelCentro.add(txtUsuario);
		panelCentro.add(etiquetaContrasenia);
		panelCentro.add(txtContrasenia);
		
		//Eventos
		
		
		botonRegistroUsuario.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				VEntanaRegistroUsuario vEntanaRegistroUsuario = new VEntanaRegistroUsuario();
				vEntanaRegistroUsuario.setVisible(true);
				
			}
			
		});
		
		botonRegistroEntidad.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				VentanaRegistroEntidad ventanaRegistroEntidad = new VentanaRegistroEntidad();
				ventanaRegistroEntidad.setVisible(true);
				
			}
			
		});
		
		
		botonIniciarSesion.addActionListener((e)->{
			String iD = txtUsuario.getText();
			String contrasenia = txtContrasenia.getText();
			
			Cliente c = Datos.buscarCliente(iD);
			if(c == null) {
				JOptionPane.showMessageDialog(null, "Usuario incorrecto");
			}
			else if(c.getContrasenia().equals(contrasenia)) {
				JOptionPane.showMessageDialog(null, "Bienvenido de nuevo "+ c.get(Nombre));
				ventanaPrincipal.setVisible(true);
				
				
			}
			
			
			
		});
		
		
		
		
		
		
	
		

	
	}

}
