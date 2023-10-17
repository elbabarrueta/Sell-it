package ventanas;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class VentanaRegistroEntidad extends JFrame{
	

	
	
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public VentanaRegistroEntidad() {
		
		this.setBounds(600,600,300,300);
		this.setTitle("Registro Entidad");
		
		
		JPanel panelRegistroEntidad = new JPanel(new BorderLayout());
		JPanel panelNorte = new JPanel(new BorderLayout());
		JPanel panelSur = new JPanel(new BorderLayout());
		JPanel panelCentro = new JPanel(new GridLayout(1,10));
		
		this.add(panelRegistroEntidad);
		
		JLabel lblMail = new JLabel("Correo electronico:");
		JLabel lblNombre = new JLabel("Nombre de la Empresa:");
		JLabel lblContrasenia = new JLabel("Contrasenia:");
		JLabel lblDireccion = new JLabel("Direccion de la sede:");
		JLabel lblCP = new JLabel("Codigo Postal:");
		JLabel lblPanelNorte = new JLabel("Rellene las casillas");
		
		JTextField txtMail = new JTextField();
		JTextField txtNombre = new JTextField();
		JTextField txtDireccion = new JTextField();
		JTextField txtCP = new JTextField();
		
		JPasswordField txtPassword = new JPasswordField();
		
		JButton btnRegistrarse = new JButton("Registrarse");
		
		panelRegistroEntidad.add(panelCentro,BorderLayout.CENTER);
		panelRegistroEntidad.add(panelSur,BorderLayout.SOUTH);
		panelRegistroEntidad.add(panelNorte,BorderLayout.NORTH);
		
		panelNorte.setLayout(new FlowLayout(FlowLayout.CENTER));
		panelSur.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		panelNorte.add(lblPanelNorte);
		panelSur.add(btnRegistrarse);
		
		panelCentro.add(lblNombre);
		panelCentro.add(txtNombre);
		panelCentro.add(lblDireccion);
		panelCentro.add(txtDireccion);
		panelCentro.add(lblCP);
		panelCentro.add(txtCP);
		panelCentro.add(lblMail);
		panelCentro.add(txtMail);
		panelCentro.add(lblContrasenia);
		panelCentro.add(txtPassword);
		
		
	
		
	}
		


}
