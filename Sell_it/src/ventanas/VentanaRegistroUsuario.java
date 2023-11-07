package ventanas;

import java.awt.BorderLayout;

import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import clases.Datos;
import clases.Usuario;

public class VentanaRegistroUsuario extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DataSetUsuario dataSetUsuario;

	
	public VentanaRegistroUsuario() {
		this.setBounds(900,300,400,400);
		this.setTitle("Registro de Usuario");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		JPanel panelRegistroUsuario = new JPanel(new BorderLayout());
		JPanel panelSur = new JPanel(new BorderLayout());
		JPanel panelNorte = new JPanel(new BorderLayout());
		JPanel panelCentro = new JPanel(new GridLayout(5,2));
		panelRegistroUsuario.add(panelCentro,BorderLayout.CENTER);
		panelRegistroUsuario.add(panelSur,BorderLayout.SOUTH);
		panelRegistroUsuario.add(panelNorte,BorderLayout.NORTH);
		panelNorte.setLayout(new FlowLayout(FlowLayout.CENTER));
		panelSur.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		
		this.add(panelRegistroUsuario);
		
		JButton btnRegistro = new JButton("Registrarse");
		panelSur.add(btnRegistro);
		
		JPasswordField txtContrasenia = new JPasswordField();
		
		JTextField txtNombre = new JTextField();
//		JTextField txtID = new JTextField();
//		JTextField txtDireccion = new JTextField();
//		JTextField txtCodigoPostal = new JTextField();
		JTextField txtCorreo = new JTextField();
		
		JLabel lblNombre = new JLabel("Nombre:");
//		JLabel lblID = new JLabel("DNI:");
		JLabel lblContrasenia = new JLabel("Contrasenia");
//		JLabel lblCodigoPostal = new JLabel("Codigo Postal:");
//		JLabel lblDireccion = new JLabel("Direccion:");
		JLabel lblPanelNorte = new JLabel("Rellene las Casillas:");
		JLabel lblCorreo = new JLabel("Direccion de correo:");
		JLabel lblTipo = new JLabel("Elige tu tipo de usuario");
		
		panelNorte.add(lblPanelNorte,BorderLayout.NORTH);
		
		String[] tipoU = {"Usuario Normal", "Usuario Entidad"};
        JComboBox<String> comboTipoU = new JComboBox<>(tipoU);
		
//		panelCentro.add(lblID);
//		panelCentro.add(txtID);
		panelCentro.add(lblNombre);
		panelCentro.add(txtNombre);
		panelCentro.add(lblContrasenia);
		panelCentro.add(txtContrasenia);
		panelCentro.add(lblCorreo);
		panelCentro.add(txtCorreo);
		panelCentro.add(lblTipo);
		panelCentro.add(comboTipoU);
		
//		panelCentro.add(lblDireccion);
//		panelCentro.add(txtDireccion);
//		panelCentro.add(lblCodigoPostal);
//		panelCentro.add(txtCodigoPostal);
		
		
		//Eventos
		
		
		btnRegistro.addActionListener((e)->{
			
			String contrasenia = txtContrasenia.getText();
			String nombre = txtNombre.getText();
			String correo = txtCorreo.getText();
//			String codigoPostal = txtCodigoPostal.getText();
			String tipo = (String) comboTipoU.getSelectedItem();
			
			Usuario u = new Usuario(nombre,correo,tipo,contrasenia);
			
			if (Datos.buscarUsuario(correo)==null) {
				Datos.aniadirUsuario(u);
				JOptionPane.showMessageDialog(null,"Bienvenido a Sell-IT");
				//dataSetUsuario.anyadirUsuario(u);  hay que elegir una clase Usuario
			}else {
				JOptionPane.showMessageDialog(null,"Usuario existente, compruebe los datos");
				
			}
		});
		
		/*Aqui ahora hay qque hacer un metodo que 
		 * limpie de las casillas los datos al registrase
		 */
		
		
		
		
		
		
		
		
	}
	
	
	
	
	

}
