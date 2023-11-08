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

public class VentanaRegistroEntidad extends JFrame{
	
	private JPasswordField txtContrasenia;
	private JTextField txtNombre,txtCorreo;
	private JComboBox comboTipo;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public VentanaRegistroEntidad() {
		
		this.setBounds(300,400,400,300);
		this.setTitle("Registro Entidad");
		
		
		JPanel panelRegistroEntidad = new JPanel(new BorderLayout());
		JPanel panelNorte = new JPanel(new BorderLayout());
		JPanel panelSur = new JPanel(new BorderLayout());
		JPanel panelCentro = new JPanel(new GridLayout(5,2));
		
		this.add(panelRegistroEntidad);
		
		
		JLabel lblNombre = new JLabel("Nombre de la Empresa:");
		JLabel lblContrasenia = new JLabel("Contraseña:");
		JLabel lblCorreo = new JLabel("Correo de la empresa:");

		JLabel lblPanelNorte = new JLabel("Rellene las casillas");

		JLabel lblTipo = new JLabel("Selecciona tu tipo de usuario");
		
		String[] tipoUsu = {"Usuario entidad", "Usuario corriente"};
		comboTipo = new JComboBox<>(tipoUsu);
		

		txtNombre = new JTextField();
		txtCorreo = new JTextField();

		
		txtContrasenia = new JPasswordField();
		
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
		panelCentro.add(lblCorreo);
		panelCentro.add(txtCorreo);
		panelCentro.add(lblContrasenia);
		panelCentro.add(txtContrasenia);
		panelCentro.add(lblTipo);
		panelCentro.add(comboTipo);

		
		
		
		
		//Eventos
		
		
		btnRegistrarse.addActionListener((e)->{
			
			String contrasenia = txtContrasenia.getText();
			String nombre = txtNombre.getText();
			String correo = txtCorreo.getText();
			String tipo = (String) comboTipo.getSelectedItem();
			
			
			Usuario u = new Usuario(nombre,correo,tipo,contrasenia);
			if( Datos.buscarUsuario(correo)== null) {
				Datos.aniadirUsuario(u);
				JOptionPane.showMessageDialog(null, "Bienvenido a Sell-IT");
				
				// Cerrar la ventana actual
				VentanaInicio v = new VentanaInicio();
				dispose();
		        v.setVisible(true);
			}
			else {
				JOptionPane.showMessageDialog(null, "Usuario existente");
			}
			//limpiarCampos();
		});
		
		
		
		

		
	}
	private void limpiarCampos() {
		
		
		txtNombre.setText("");
		txtCorreo.setText("");
		comboTipo.setSelectedIndex(-1);		
		txtContrasenia.setText("");
		
		
		
	
	}
		


}
