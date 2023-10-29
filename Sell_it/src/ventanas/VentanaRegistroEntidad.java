package ventanas;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
		JPanel panelCentro = new JPanel(new GridLayout(1,12));
		
		this.add(panelRegistroEntidad);
		
		
		JLabel lblNombre = new JLabel("Nombre de la Empresa:");
		JLabel lblContrasenia = new JLabel("Contrasenia:");
		JLabel lblDireccion = new JLabel("Direccion de la sede:");
		JLabel lblCP = new JLabel("Codigo Postal:");
		JLabel lblPanelNorte = new JLabel("Rellene las casillas");
		JLabel lblID = new JLabel("ID de la Empresa:");
		
		JTextField txtID = new JTextField();
	
		JTextField txtNombre = new JTextField();
		JTextField txtDireccion = new JTextField();
		JTextField txtCP = new JTextField();
		
		JPasswordField txtContrasenia = new JPasswordField();
		
		JButton btnRegistrarse = new JButton("Registrarse");
		
		panelRegistroEntidad.add(panelCentro,BorderLayout.CENTER);
		panelRegistroEntidad.add(panelSur,BorderLayout.SOUTH);
		panelRegistroEntidad.add(panelNorte,BorderLayout.NORTH);
		
		panelNorte.setLayout(new FlowLayout(FlowLayout.CENTER));
		panelSur.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		panelNorte.add(lblPanelNorte);
		panelSur.add(btnRegistrarse);
		
		panelCentro.add(lblID);
		panelCentro.add(txtID);
		panelCentro.add(lblNombre);
		panelCentro.add(txtNombre);
		panelCentro.add(lblDireccion);
		panelCentro.add(txtDireccion);
		panelCentro.add(lblCP);
		panelCentro.add(txtCP);
		
		panelCentro.add(lblContrasenia);
		panelCentro.add(txtContrasenia);
		
		
		//Eventos
		
		
		/*btnRegistrarse.addActionListener((e)->{
			
			String iD = txtID.getText();
			String nombre = txtNombre.getText();
			String direccion = txtDireccion.getText();
			String codigoPostal = txtCP.getText();
			String contrasenia = txtContrasenia.getText();
			
			Cliente c = new Cliente(iD,nombre,direccion,codigoPostal,contrasenia);
			if( Datos.buscarCliente(ID)== null) {
				Datos.aniadirCliente(c);
				JOptionPane.showMessageDialog(null, "Bienvenido a Sell-IT");
				
			}
			else {
				JOptionPane.showMessageDialog(null, "Usuario existente");
			}
			limpiarCampos();
		});
		
		
		private void limpiarCampos() {
			
			txtID.setText("");
			txtNombre.setText("");
			txtDireccion.setText("");
			txtCP.setText("");
			txtContrasenia.setText("");
			
			
			
		
		}*/
		

		
	}
		


}
