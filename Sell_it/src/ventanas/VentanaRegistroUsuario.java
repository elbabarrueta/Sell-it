package ventanas;

import java.awt.BorderLayout;
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

import clases.Datos;
import clases.Ussuario;

public class VentanaRegistroUsuario extends JFrame {

	
	
	

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public  VentanaRegistroUsuario() {
		
		
		this.setBounds(300,300, 400, 400);
		this.setTitle("Registro de Usuario");
		
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		
		JPanel panelRegistroUsuario = new JPanel(new BorderLayout());
		JPanel panelSur = new JPanel(new BorderLayout());
		JPanel panelNorte = new JPanel(new BorderLayout());
		JPanel panelCentro = new JPanel(new GridLayout(1,10));
		panelRegistroUsuario.add(panelCentro,BorderLayout.CENTER);
		panelRegistroUsuario.add(panelNorte,BorderLayout.NORTH);
		panelRegistroUsuario.add(panelSur,BorderLayout.SOUTH);
		panelNorte.setLayout(new FlowLayout(FlowLayout.CENTER));
		panelSur.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		
		this.add(panelRegistroUsuario);
		
		
		JButton btnRegistro = new JButton("Registrarse");
		panelSur.add(btnRegistro);
		
		
		JPasswordField txtContrasenia = new JPasswordField();
		
		
		JTextField txtNombre = new JTextField();
		JTextField txtID = new JTextField();
		JTextField txtDireccion = new JTextField();
		JTextField txtCodigoPostal = new JTextField();
		
		
		
		
		JLabel lblNombre = new JLabel("Nombre:");
		JLabel lblID = new JLabel("DNI:");
		JLabel lblDireccion = new JLabel("Direccion:");
		JLabel lblCodigoPostal = new JLabel("CodigoPostal:");
		JLabel lblContrasenia = new JLabel("Contrasenia");
		JLabel lblPanelNorte = new JLabel("Rellene las casillas");
		
		panelNorte.add(lblPanelNorte,BorderLayout.NORTH);
		
		
		panelCentro.add(lblID);
		panelCentro.add(txtID);
		panelCentro.add(lblContrasenia);
		panelCentro.add(txtContrasenia);
		panelCentro.add(lblNombre);
		panelCentro.add(txtNombre);
		panelCentro.add(lblDireccion);
		panelCentro.add(txtDireccion);
		panelCentro.add(lblCodigoPostal);
		panelCentro.add(txtCodigoPostal);
		
		
	
	
		//Eventos
		
		
		btnRegistro.addActionListener((e)->{
			
			
			String iD = txtID.getText();
			String contrasenia = txtContrasenia.getText();
			String nombre = txtNombre.getText();
			String direccion =txtDireccion.getText();
			String codigoPostal = txtCodigoPostal.getText();
			
			
			
			Ussuario u = new Ussuario(iD,contrasenia,nombre,direccion, codigoPostal);
			
			if(Datos.buscarUsuario(iD)== null) {
				Datos.aniadirUsuario(u);
				JOptionPane.showMessageDialog(null, "Bienvenido a Sell-IT");
			}
			else {
				JOptionPane.showMessageDialog(null, "Usuario existente");
			}
			
			//limpiarCampos();
			
			
		});
		
		
		/*private void limpiarCampos() {
			txtID.setText("");
			txtNombre.setText("");
			txtApellido.setText("");
			txtContrasenia.setText("");
		}

		
	*/

}}
